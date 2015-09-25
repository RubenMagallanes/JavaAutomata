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
	private int count;

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
		count = 1;
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

	/**
	 * Returns a count of the number of times this {@code AutomataLink} occurs within
	 * an {@code Automata}.
	 *
	 * @return
	 * 		- count
	 */
	public int getCount(){
		return count;
	}

	/**
	 * Increments the number of times this {@code AutomataLink} occurs within an {@code Automata}
	 * by one.
	 */
	public void incrementCount(){
		count++;
	}

	/**
	 * Generates a hash code for this {@code AutomataLink}.
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((methodName == null) ? 0 : methodName.hashCode());
		result = prime * result + source;
		result = prime * result + target;
		return result;
	}

	/**
	 * Returns true if the specified {@code Object is identical to this
	 * {@code AutomataLink}, otherwise returns false.
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		AutomataLink other = (AutomataLink) obj;
		if (methodName == null) {
			if (other.methodName != null)
				return false;
		} else if (!methodName.equals(other.methodName))
			return false;
		if (source != other.source)
			return false;
		if (target != other.target)
			return false;
		return true;
	}

	public String toString(){
		return "Link: { Method Name = " + methodName + ", sourceID = " + source + ", targetID = " + target + "}";
	}
}
