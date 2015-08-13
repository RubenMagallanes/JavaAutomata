package main.parse;

/**
 * Class representing an directional edge between two {@code AutomataState}s
 * in an {@code Automata}.
 *
 * @author Chris Chin
 * @author David Sheridan
 *
 */
public class AutomataLink {

	// fields
	private String methodName;
	private int source;
	private int target;

	/**
	 * Constructs a new instance of an {@code AutomataLink} between the specified
	 * source and target {@code AutomataState}s.
	 *
	 * @param methodName
	 * 		method called which resulted in state change
	 * @param source
	 * 		unique integer id for source {@code AutomataState}
	 * @param target
	 * 		unique integer id for target {@code AutomataState}
	 */
	public AutomataLink(String methodName, int source, int target){
		this.methodName = methodName;
		this.source = source;
		this.target = target;
	}

	/**
	 * Returns the name of the method call which this {@code AutomataLink}
	 * represents.
	 *
	 * @return
	 * 		method name
	 */
	public String getName(){
		return methodName;
	}

	/**
	 * Returns the unique integer ID referencing the source {@code AutomataState}.
	 *
	 * @return
	 * 		source id
	 */
	public int getSource(){
		return source;
	}

	/**
	 * Returns the unique integer ID referencing the target {@code AutomataState}.
	 *
	 * @return
	 * 		target id
	 */
	public int getTarget(){
		return target;
	}
}
