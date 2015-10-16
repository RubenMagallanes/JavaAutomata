package main.tracer.state;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import main.tracer.TraceFilter;

/**
 * State representing an array in a Java program.
 */
public class ArrayState extends State {

	private static final long serialVersionUID = 1L;

	// fields
	private List<State> values = new ArrayList<State>();

	/**
	 * Constructs a new empty instance of {@code ArrayState}.
	 */
	public ArrayState(){
		this.values = new ArrayList<State>();
	}

	/**
	 * Returns the list of {@code States} represented by this
	 * {@code ArrayState}.
	 *
	 * @return
	 * 		- list of states
	 */
	public List<State> getValues(){
		return values;
	}

	/**
	 * Filter the visible fields of this {@code ArrayState} based on the
	 * specified {@code TraceFilter}.
	 */
	public void filterFields(TraceFilter f) {
		for(State s : values)
			s.filterFields(f);
	}

	/**
	 * Generates a hash code for this {@code ArrayState}.
	 */
	public int hashCode() {
		return 0; // TODO
	}

	/**
	 * Returns true if this {@code ArrayState} is equivalent to the
	 * specified {@code Object}, otherwise returns false.
	 */
	public boolean equals(Object obj) {
		return obj instanceof ArrayState && ((ArrayState)obj).values.equals(values);
	}

	/**
	 * Returns a {@code String} representation of this {@code ArrayState}.
	 */
	public String toString() {
		Map<State, String> alreadySeenObjects = new IdentityHashMap<State, String>();

		if(alreadySeenObjects.containsKey(this))
			return alreadySeenObjects.get(this);
		alreadySeenObjects.put(this, "OBJ"+alreadySeenObjects.size());

		StringBuilder result = new StringBuilder();
		result.append('[');
		boolean first = true;
		for(State v : values) {
			if(!first) result.append(',');
			else first = false;
			result.append(v.toString(alreadySeenObjects));
		}
		result.append(']');
		return result.toString();
	}
}
