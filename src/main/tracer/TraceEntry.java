package main.tracer;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import com.sun.jdi.connect.Connector.Argument;

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
		sb.append(method.className);
		sb.append(' ');
		sb.append(method.name);
		sb.append('(');
		for(String s : method.argTypes) {
			sb.append(s);
			sb.append(',');
		}
		if(method.argTypes.length > 0)
			sb.setLength(sb.length() - 1);
		sb.append(')');
		return sb.toString();
	}


	public void filterFields(TraceFilter f) {
		state.filterFields(f);
	}

	@Override
	public String toString(){
		String isEntry = isReturn ? "  { \"exitMethod\": {\n" : "  { \"enterMethod\": {\n";
		String string = isEntry + "      \"methodName\": \"" + method.toString().substring(5) + "\"";
		if(state != null){
			string += ",\n";
			string += stateToText(state);
		}
		else{
			string += "\n";
		}

		/*if(arguments !=null){
			for(State arg :  arguments){
				if(arg != null){
					string += "Arg " + arg.toString();
				}
			}

		}*/

		string += "    }\n";
		string += "  }";
		return string;

	}

	private String stateToText(State state){
		String string = "      \"state\": {\n";
		String s = state.toString();
		String[] data = state.toString().substring(1, state.toString().length() - 1).split(",");
		for(int i = 0; i < data.length; i++){
			String[] temp = data[i].split(" ", 2);
			if(temp.length > 1){
				String[] fieldData = temp[1].split("=");
				string += "        \""+fieldData[0]+"\": \"" + fieldData[1].replace("\"", "") + "\"";
				string += (i < data.length - 1) ? ",\n" : "\n";
			}
		}
		string += "      }\n";
		return string;
	}
}
