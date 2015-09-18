package main.tracer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.InternalException;
import com.sun.jdi.Method;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.StackFrame;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.Value;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.ClassPrepareEvent;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.EventSet;
import com.sun.jdi.event.MethodEntryEvent;
import com.sun.jdi.event.MethodExitEvent;
import com.sun.jdi.event.VMDeathEvent;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.MethodEntryRequest;
import com.sun.jdi.request.MethodExitRequest;
import com.sun.jdi.request.VMDeathRequest;

public class TraceThread extends Thread {

	//virtual machine to run traced program on
	private final VirtualMachine vm;

	//filter to apply to the trace
	private final TraceFilter filter;

	private final RealtimeTraceConsumer consumer;

	private Map<ReferenceType, Boolean> knownTraceableClasses;

	private Set<ThreadReference> threadsToResume;

	DynamicHandler dynamicHandler;


	/**
	 * Constructs the TraceThread object
	 *
	 * @param vm the virtual machine to run on
	 *
	 * @param filter to apply to the program
	 *
	 * @param consumer
	 * */
	public TraceThread(VirtualMachine vm, TraceFilter filter,RealtimeTraceConsumer consumer, DynamicHandler dh) {
		this.vm = vm;
		this.filter = filter;
		this.consumer = consumer;
		this.dynamicHandler = dh;
		knownTraceableClasses = new HashMap<ReferenceType, Boolean>();
		threadsToResume = new HashSet<ThreadReference>();
	}

	public void run() {
		try {
			// class -> does it have any traceable methods?
			// When a class is loaded, we need to add a MethodEntryRequest and
			// MethodExitRequest if it's traceable.
			ClassPrepareRequest classPrepareRequest = vm.eventRequestManager()
					.createClassPrepareRequest();
			classPrepareRequest
					.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
			classPrepareRequest.enable();
			VMDeathRequest deathRequest = vm.eventRequestManager()
					.createVMDeathRequest();
			deathRequest.enable();
			// Resume the program (AFTER setting up event requests)
			vm.resume();
			while (true) {
				EventSet events = vm.eventQueue().remove();
				for (Event event : events) {
					if (event instanceof ClassPrepareEvent) {
						handleClassPrepareEvent((ClassPrepareEvent)event);
					}
					if(event instanceof MethodEntryEvent) {
						handleMethodEntryEvent((MethodEntryEvent)event);
					}
					else if(event instanceof MethodExitEvent){
						handleMethodExitEvent((MethodExitEvent)event);
					}
					else if (event instanceof VMDeathEvent) {
						vm.dispose();
						return;
					}
				}
				for (ThreadReference thread : threadsToResume)
					thread.resume();
			}
		} catch (InterruptedException | RuntimeException | Error t) {
			consumer.onTracerCrash(t);
		} finally {
			consumer.onTraceFinish();
		}
	}

	private void handleClassPrepareEvent(ClassPrepareEvent event) {
		ReferenceType type = event.referenceType();
		if (!knownTraceableClasses.containsKey(type)) {
			boolean traceable = doesClassHaveTraceableMethods(
					filter, type);
			knownTraceableClasses.put(type, traceable);
			if (traceable) {
				MethodEntryRequest entryRequest = vm.eventRequestManager().createMethodEntryRequest();
				entryRequest.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
				entryRequest.addClassFilter(type);
				entryRequest.enable();

				// When a method is exited, send an event to
				// this tracer
				MethodExitRequest exitRequest = vm.eventRequestManager().createMethodExitRequest();
				exitRequest.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
				exitRequest.addClassFilter(type);
				exitRequest.enable();
			}
		}
		threadsToResume.add(event.thread());
	}


	/**
	 * Handles when a method is entered in the traced program
	 *
	 * @param the event of the entry
	 * */
	private void handleMethodEntryEvent(MethodEntryEvent event) {
		if (filter.isMethodTraced(new MethodKey(event.method()))) {
			// Handle a method entry
			StackFrame frame = null;
			try {
				frame = event.thread().frame(0);
			} catch (IncompatibleThreadStateException e1) {
				e1.printStackTrace();
			}
			ObjectReference _this = frame.thisObject();
			TraceEntry te = new TraceEntry();
			te.setMethod(new MethodKey(event.method()));


			if(_this != null) {
				te.setState(Tracer.valueToState(filter, _this, new HashMap<ObjectReference, main.tracer.state.State>()));
			}

			te.setIsExit(false);

			// Java bug; InternalException is thrown if getting
			// arguments from a native method
			// see
			// http://bugs.java.com/view_bug.do?bug_id=6810565

			if (!event.method().isNative()) {
				te.setArguments(new ArrayList<>());
				List<Value> argValues = new ArrayList<>();
				try {
					argValues = frame.getArgumentValues();
				} catch (InternalException e) {
					// https://netbeans.org/bugzilla/show_bug.cgi?id=194822
					if (!e.getMessage().equals("Unexpected JDWP Error: 35")){
						throw e;
					}
					while (argValues.size() < te.getMethod().getArgTypes().length){
						argValues.add(null);
					}
				}

				for (int k = 0; k < argValues.size(); k++) {
					Value v = argValues.get(k);
					if (filter.isParameterTraced(new ParameterKey(te.getMethod(), k))) {
						te.getArguments().add(Tracer.valueToState(filter, v, new HashMap<ObjectReference, main.tracer.state.State>()));
					} else {
						te.getArguments().add(null);
					}
				}
			}
			consumer.onTraceLine(te);

			if(dynamicHandler != null){
				dynamicHandler.eventOccoured();
			}
		}
		threadsToResume.add(event.thread());
	}



	/**
	 * Handles when a method is exited in the traced program
	 *
	 * @param the event of the exit
	 * */
	private void handleMethodExitEvent(MethodExitEvent event) {
		// Handle a method return
		if (filter.isMethodTraced(new MethodKey(event.method()))) {
			StackFrame frame = null;
			try {
				frame = event.thread().frame(0);
			} catch (IncompatibleThreadStateException e) {
				e.printStackTrace();
			}
			ObjectReference _this = frame.thisObject();
			TraceEntry te = new TraceEntry();
			te.setMethod(new MethodKey(event.method()));
			if (_this == null)
				te.setState(null);
			else {
				te.setState(Tracer.valueToState(filter, _this, new HashMap<ObjectReference, main.tracer.state.State>()));
			}
			te.setIsExit(true);
			//System.out.println(te);
			consumer.onTraceLine(te);

			if(dynamicHandler != null){
				dynamicHandler.eventOccoured();
			}
		}
		threadsToResume.add(event.thread());
	}


	/**
	 * Returns whether or not the class has methods that are traces
	 *
	 * @param filter to use
	 *
	 * @param type //TODO find out what this is
	 * */
	private static boolean doesClassHaveTraceableMethods(TraceFilter filter,
			ReferenceType type) {
		for (Method m : type.methods())
			if (filter.isMethodTraced(new MethodKey(m)))
				return true;
		return false;
	}
}
