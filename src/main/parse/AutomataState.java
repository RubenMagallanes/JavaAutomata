package main.parse;

import java.util.List;

/**
 * Class representing a vertex in an {@code Automata}.
 *
 * @author Chris Chin
 * @author David Sheridan
 *
 */
public class AutomataState {

	// fields
	private List<AutomataField> fields;
	private int id;

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

	/**
	 * Returns the unique integer ID for this {@code AutomataState}.
	 *
	 * @return
	 * 		unique integer id
	 */
	public int getId(){
		return id;
	}
}
