package main.tracer.tests;

import static org.junit.Assert.*;
import main.tracer.TraceFilterSelector;

import org.junit.Test;

public class TraceFilterSelectorTests {

	@Test
	public void constructorTest() {
		TraceFilterSelector selector = getSelector();

	}

	private TraceFilterSelector getSelector(){
		return new TraceFilterSelector();
	}

}
