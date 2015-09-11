package main.tracer.tests;

import main.tracer.ExecutionData;

import org.junit.Test;

public class ExecutionDataTests {

	@Test
	public void constructorTest(){
		getExcutionData();
	}

	private ExecutionData getExcutionData(){
		return new ExecutionData("SOMEDATA");
	}
}
