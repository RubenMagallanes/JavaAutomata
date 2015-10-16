package main.tracer.tests;

import main.tracer.TraceFilterSelector;

import org.junit.Test;

public class TraceFilterSelectorTests {

	@Test
	public void constructorTest() {
		getSelector();
	}

	private TraceFilterSelector getSelector(){
		return new TraceFilterSelector();
	}
}
