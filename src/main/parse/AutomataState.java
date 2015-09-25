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
	private List<AutomataField> fields;

	/**
	 * Constructs an instance of an {@code AutomataState} from the specified
	 * list of {@code AutomataField}s and a unique integer ID.
	 *
	 * @param fields
	 * 		list of fields
	 * @param id
	 * 		unique integer id
	 */
	public AutomataState(List<AutomataField> fields,int id){
		this.fields = fields;
		this.id = id;
	}

	public AutomataState(List<AutomataField> fields){
		this.fields = fields;
		id = 0;
	}

	/**
	 * Returns the unique integer ID for this {@code AutomataState}.
	 *
	 * @return
	 * 		unique integer id
	 */
	public int getId(){
		return id;
	}

	/**
	 * Returns the list of {@code AutomataField}s associated with this
	 * {@code AutomataState}.
	 *
	 * @return
	 * 		list of fields
	 */
	public List<AutomataField> getFields(){
		return fields;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
		return result;
	}

	@Override
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

	public int compareTo(AutomataState o){
		return id - o.getId();
	}

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
