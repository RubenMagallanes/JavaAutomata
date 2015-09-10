package main.tracer;

import java.io.Serializable;
import java.util.List;

import main.tracer.state.State;

public class TraceEntry implements Serializable{
	private static final long serialVersionUID = 1L;

	public boolean isReturn;

	/** The state of the object before the method was called (or before it returned).
	 * Null if the method is static. */
	public State state;

	/**
	 * The arguments to the current method call.
	 * Null if the method is native, or if this is a return entry.
	 * Contains nulls in place of parameters that aren't recorded.
	 */
	public List<State> arguments;


	public MethodKey method;


	/**
	 * Returns the method name and parameters in the format: packagename.ClassName methodName(p1type,p2type,p3type)
	 */
	public String getLongMethodName() {
		StringBuilder sb = new StringBuilder();
		sb.append(method.getClassName());
		sb.append(' ');
		sb.append(method.getName());
		sb.append('(');
		for(String s : method.getArgTypes()) {
			sb.append(s);
			sb.append(',');
		}
		if(method.getArgTypes().length > 0)
			sb.setLength(sb.length() - 1);
		sb.append(')');
		return sb.toString();
	}

	/**
	 * Returns the method names used for comparing
	 * */
	public String getMethodName(){
		return method.toString();
	}


	public void filterFields(TraceFilter f) {
		if(f == null)System.out.println("Null Filter");
		if(state == null){
			System.out.println("Null state");
			return;
		}
		state.filterFields(f);
	}

	@Override
	public String toString(){
		String isEntry = (isReturn) ? "  { \"exitMethod\": {\n" : "  { \"enterMethod\": {\n";
		String string = isEntry + "      \"methodName\": \"" + method.toString().substring(5) + "\"";
		if(state != null){
			string += ",\n";
			string += "      " + state;
		}
		else{
			string += ",\n      \"state\": {}\n";
		}

		string += "    }\n";
		string += "  }";
		return string;

	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<State> getArguments() {
		return arguments;
	}

	public void setArguments(List<State> arguments) {
		this.arguments = arguments;
	}

	public MethodKey getMethod() {
		return method;
	}

	public boolean isReturn(){
		return isReturn;
	}

	public void setMethod(MethodKey method) {
		this.method = method;
	}

}
