package main.tracer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.tracer.state.ArrayState;
import main.tracer.state.EnumState;
import main.tracer.state.NullState;
import main.tracer.state.ObjectState;
import main.tracer.state.SimpleState;
import main.tracer.state.State;
import main.tracer.state.StringState;

import com.sun.jdi.ArrayReference;
import com.sun.jdi.ArrayType;
import com.sun.jdi.Bootstrap;
import com.sun.jdi.ClassType;
import com.sun.jdi.Field;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.StringReference;
import com.sun.jdi.Type;
import com.sun.jdi.Value;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.LaunchingConnector;

/**
 * Main interface class for the tracing subsystem.
 */
public class Tracer{

	/**
	 * Starts a program and traces it, asynchronously.
	 * This method returns once the program is running.
	 *
	 * @param vmOptions The arguments passed into the Virtual Machine
	 * @param mainClass The Main class of the given application
	 * @param filter The trace filter to use
	 * @param consumer The trace consumer
	 * @return A string representation of the program trace
	 * @throws Exception This becomes your problem if thrown
	 */
	public static void launchAndTraceAsync(String vmOptions, String mainClass, TraceFilter filter, RealtimeTraceConsumer consumer) throws Exception{
		VirtualMachine vm = launchTracee(mainClass, vmOptions);
		TraceAsync(vm, filter, consumer);
	}

	/**
	 * Starts tracing the given VM in a separate thread.
	 *
	 * This method traces asynchronously. Trace lines are delivered to the given consumer.
	 *
	 * @param vm The VM to trace.
	 * @param filter The trace filter to use.
	 * @param consumer The consumer that trace lines will be sent to.
	 */
	public static void TraceAsync(final VirtualMachine vm, final TraceFilter filter, final RealtimeTraceConsumer consumer) {
		Thread thread = new TraceThread(vm, filter, consumer);
		thread.setName("Tracer thread");
		thread.setDaemon(true);
		thread.start();
	}

	/**
	 * Returns a string containing the relevant state of an object, in some human-readable format.
	 */
	private static State objectToState(TraceFilter filter, ObjectReference object, Map<ObjectReference, State> alreadySeenObjects) {

		if(alreadySeenObjects.containsKey(object)){
			return alreadySeenObjects.get(object);
		}

		Type type = object.type();

		if(type instanceof ClassType) {

			if(((ClassType)object.type()).isEnum()) {
				boolean fullyInitialized = true;
				for(Field f : ((ClassType)object.type()).allFields()) {
					if(f.isEnumConstant()) {
						Value value = object.getValue(f);
						if(value != null && object.getValue(f).equals(object))
							return new EnumState(f.name());
						if(value == null)
							fullyInitialized = false;
					}
				}
				if(!fullyInitialized){
					return new EnumState("<uninitialized-enum>"); // TODO should this be a separate class?
				}
				throw new AssertionError("failed to find enum constant name");
			}

			if(object instanceof StringReference){
				return new StringState(((StringReference)object).value());
			}

			ObjectState state = new ObjectState(object.type().name());
			alreadySeenObjects.put(object, state);

			List<Field> fields = ((ClassType)object.type()).allFields();

			for(int i = 0; i < fields.size(); i++) {
				Field field = fields.get(i);
				FieldKey fieldKey = new FieldKey(field);

				if(!filter.isFieldTraced(fieldKey)){
					continue;
				}
				state.getFields().put(fieldKey, valueToState(filter, object.getValue(field), alreadySeenObjects));
				state.setFields(state.getFields());
			}

			return state;

		}
		else if(type instanceof ArrayType) {
			//TODO Fix this area
			ArrayState state = new ArrayState();
			alreadySeenObjects.put(object, state);

			List<Value> values = ((ArrayReference)object).getValues();

			for(Value v : values) {
				state.values.add(valueToState(filter, v, alreadySeenObjects));
			}
			return state;
		}
		else{
			throw new AssertionError("Unsupported type "+type.name());
		}
	}

	/**
	 * Returns a string containing the relevant state of any value, in some human-readable format.
	 */
	public static State valueToState(TraceFilter filter, Value value, Map<ObjectReference, State> alreadySeenObjects) {
		if(value == null){
			return new NullState();
		}
		if(value instanceof ObjectReference){
			return objectToState(filter, (ObjectReference)value, alreadySeenObjects);
		}
		return new SimpleState(value.toString());
	}

	private static VirtualMachine launchTracee(String mainClass, String jvmOptions) throws Exception {

		// Find the command-line LaunchingConnector
		LaunchingConnector processConnector = null;
		for(LaunchingConnector ac : Bootstrap.virtualMachineManager().launchingConnectors()) {
			if(ac.name().equals("com.sun.jdi.CommandLineLaunch")) {
				processConnector = ac;
				break;
			}
		}
		if(processConnector == null){
			throw new Exception("didn't find CommandLineLaunch connector");
		}

		// Launch the program, initially suspended
		// Possible Java bug: with suspend=true, processConnector.launch throws an exception
		Map<String, Connector.Argument> args = processConnector.defaultArguments();
		args.get("main").setValue(mainClass);
		args.get("options").setValue(jvmOptions);
		args.get("suspend").setValue("true");
		return processConnector.launch(args);
	}
}

class ObjectReferenceGenerator {
	private Map<Object, String> map = new HashMap<>();

	public String get(Object obj) {
		return map.get(obj);
	}

	private int nextID = 0;
	public void put(Object obj) {
		map.put(obj, "REF"+String.valueOf(nextID++));
	}
}