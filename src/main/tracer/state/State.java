package main.tracer.state;

import java.io.Serializable;
import java.util.Map;

import main.tracer.TraceFilter;

public class State implements Serializable {
	private static final long serialVersionUID = 1L;

	public void filterFields(TraceFilter f) {
	}

	public String toString(Map<State, String> alreadySeenObjects) {
		return toString();
	}
}
