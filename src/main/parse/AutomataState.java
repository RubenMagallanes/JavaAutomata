package main.parse;

import java.util.List;

/**
 * Class representing a vertex in an {@code Automata}.
 *
 * @author Chris Chin
 * @author David Sheridan
 *
 */
public class AutomataState implements Comparable<AutomataState>{

	// fields
	private int id;
	private boolean startState;
	private List<AutomataField> fields;

	/**
	 * Constructs an instance of an {@code AutomataState} from the specified
	 * list of {@code AutomataField}s and a unique integer ID. Sets start state
	 * to false.
	 *
	 * @param fields
	 * 		- list of fields
	 * @param id
	 * 		- unique integer id
	 */
	/*public AutomataState(List<AutomataField> fields, int id){
		this.fields = fields;
		this.id = id;
		startState = false;
	}*/

	/**
	 * Constructs an instance of an {@code AutomataState} from the specified
	 * list of {@code AutomataField}s, unique integer ID and whether or not
	 * this {@code AutomataSate} is a start state.
	 *
	 * @param fields
	 * 		- list of fields
	 * @param id
	 * 		- unique integer id
	 * @param startState
	 * 		- true if state is start state, otherwise false
	 */
	public AutomataState(List<AutomataField> fields, int id, boolean startState){
		this.fields = fields;
		this.id = id;
		this.startState = startState;
	}

	/**
	 * Constructs an instance of an {@code AutomataState} from the specified
	 * list of {@code AutomataFields}. The unique integer ID is set to zero.
	 *
	 * @param fields
	 * 		- list of fields
	 */
	public AutomataState(List<AutomataField> fields){
		this.fields = fields;
		id = 0;
	}

	/**
	 * Returns the unique integer ID for this {@code AutomataState}.
	 *
	 * @return
	 * 		- unique integer id
	 */
	public int getId(){
		return id;
	}

	/**
	 * Returns the list of {@code AutomataField}s associated with this
	 * {@code AutomataState}.
	 *
	 * @return
	 * 		- list of fields
	 */
	public List<AutomataField> getFields(){
		return fields;
	}

	/**
	 * Generates a hash code for this {@code AutomataState}.
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
		return result;
	}

	/**
	 * Returns true if this {@code AutomataState} is equivalent to the
	 * specified {@code Object}, otherwise returns false.
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AutomataState other = (AutomataState) obj;
		if (fields == null) {
			if (other.fields != null)
				return false;
		} else if (!fields.equals(other.fields))
			return false;
		return true;
	}

	/**
	 * Compares this {@code AutomataState} with the specified {@code AutomataState}
	 * for order. Returns a negative integer if this {@code AutomataState} should be ordered first,
	 * a positive integer if the specified {@code AutomataState} should be ordered first or 0 if both
	 * {@code AutomataState} are equivalent.
	 */
	public int compareTo(AutomataState o){
		return id - o.getId();
	}

	/**
	 * Returns a {@code String} representation of this {@code AutomataState}.
	 */
	public String toString(){
		String string = "State(ID = " + id + "): {";
		for(int i = 0; i < fields.size(); i++){
			string += fields.get(i);
			if(i < fields.size() - 1){
				string += ", ";
			}
		}
		return string += "}";
	}
}
