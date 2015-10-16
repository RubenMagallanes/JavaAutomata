package main.tracer.state;

import java.io.Serializable;
import java.util.Map;

import org.json.JSONArray;

import main.tracer.TraceFilter;

/**
 * Base state object which other state objects extend.
 *
 */
public abstract class State implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Filter the visible fields of this {@code State} based on the
	 * specified {@code TraceFilter}.
	 */
	public void filterFields(TraceFilter f) {}

	/**
	 * Returns a {@code String} representation of this {@code ObjectState}
	 * based on whether this {@code State} has already been seen or not.
	 */
	public String toString(Map<State, String> alreadySeenObjects) {
		return toString();
	}

	/**
	 * Returns a JSON object representing this {@code ObjectState}.
	 */
	public JSONArray toJSON(){
		return new JSONArray();
	}
}
