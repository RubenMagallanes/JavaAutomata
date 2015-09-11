package main.tracer;

import java.io.Serializable;

/**
 * We might be using multiple different traces to generate an automaton.
 *
 * ExecutionData contains the data that is different for each program execution
 * (e.g. command-line arguments).
 *
 * @author campbealex2 and Nicky van Hulst
 */
public class ExecutionData implements Serializable, Cloneable {

	private static final long serialVersionUID = -9163374641745446124L;

	//the command line arguments of the execution
	private String commandLineArguments = "";

	@Override
	public String toString() {
		return "Args: "+commandLineArguments;
	}

	@Override
	public ExecutionData clone() {
		try {
			return (ExecutionData)super.clone();
		} catch(CloneNotSupportedException e) {
			throw new AssertionError(e);
		}
	}


	/**
	 * Returns the command line arguments of the execution
	 *
	 * @return the arguments
	 * */
	public String getCommandLineArguments(){
		return this.commandLineArguments;
	}
}
