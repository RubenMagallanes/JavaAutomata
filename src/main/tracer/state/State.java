package main.tracer.state;

import java.io.Serializable;
import java.util.Map;

import org.json.JSONArray;

import main.tracer.TraceFilter;

public class State implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String OPEN_BRACE = "{";
	public static final String CLOSE_BRACE = "}";
	public static final String OPEN_BRACKET = "[";
	public static final String CLOSE_BRACKET = "]";

	public void filterFields(TraceFilter f) {}

	public String toString(Map<State, String> alreadySeenObjects) {
		return toString();
	}

	public JSONArray toJSON(){
		return new JSONArray();
	}
}
