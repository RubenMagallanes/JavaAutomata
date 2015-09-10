package main.tracer;

import java.io.Serializable;
import java.util.List;

import main.tracer.state.State;

public class TraceEntry implements Serializable{
	private static final long serialVersionUID = 1L;

	//boolean to show if it is a method exit or method entry call
	private boolean isExit;

	//the state of the object before the method was called or finished
	private State state;


	/**
	 * The arguments to the current method call.
	 * Null if the method is native, or if this is a return entry.
	 * Contains nulls in place of parameters that aren't recorded.
	 */
	private List<State> arguments;


	//The MethodKey holding this info of the method call
	private MethodKey method;


	/**
	 * Returns the method name and parameters in the format: packagename.ClassName methodName(p1type,p2type,p3type)
	 *
	 * @return the string representation of the method name
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
	 *
	 * @return the name of the method
	 * */
	public String getMethodName(){
		return method.toString();
	}


	/**
	 * Remove fields from the state if it is in the filter
	 *
	 * @param the filter to apply to the state
	 * */
	public void filterFields(TraceFilter f) {
		if(f == null || state == null){
			return;
		}
		state.filterFields(f);
	}

	/**
	 * Returns the state of the TraceEntry
	 *
	 * @return the state of the TraceEntry
	 * */
	public State getState() {
		return state;
	}


	/**
	 * Sets the state of the TraceEntry
	 *
	 * @param the state to set
	 * */
	public void setState(State state) {
		this.state = state;
	}


	/**
	 * Returns the arguments of the TraceEntry
	 *
	 *  @return the list of arguments
	 * */
	public List<State> getArguments() {
		return this.arguments;
	}

	/**
	 * Sets the arguments of the TraceEntry
	 *
	 * @param the list of arguments to set
	 * */
	public void setArguments(List<State> arguments) {
		this.arguments = arguments;
	}


	/**
	 * Returns the method key for this TraceEntry
	 *
	 * @return the MethodKey representing the method
	 * */
	public MethodKey getMethod() {
		return method;
	}


	/**
	 * Returns whether or not this method call was a method entry or exit
	 *
	 * @return boolean for exit or entry to method
	 * */
	public boolean isExit(){
		return isExit;
	}


	/**
	 * Sets whether or not this method call is a method entry or exit
	 *
	 * @param boolean for exit or entry to method
	 * */
	public void setIsExit(boolean isExit){
		this.isExit = isExit;
	}


	/**
	 * Sets the MethodKey for the TraceEntry
	 *
	 * @param the method to set
	 * */
	public void setMethod(MethodKey method) {
		this.method = method;
	}


	@Override
	public String toString(){
		String isEntry = (isExit) ? "  { \"exitMethod\": {\n" : "  { \"enterMethod\": {\n";
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
}
