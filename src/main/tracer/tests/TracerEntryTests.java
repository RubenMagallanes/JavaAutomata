package main.tracer.tests;

import static org.junit.Assert.*;
import main.tracer.MethodKey;
import main.tracer.TraceEntry;

import org.junit.Test;

public class TracerEntryTests {

	@Test
	public void constructerTest() {
		getTraceEntry(getMethodKey());
	}

	@Test
	public void getLongMethodNameTestOne(){
		assertTrue(getTraceEntry(getMethodKey(1))
				.getLongMethodName()
				.equals("main.tracer.tests.TracerEntryTests getMethodKey(int)"));
	}

	@Test
	public void getLongMethodNameTestTwo(){
		assertTrue(getTraceEntry(getMethodKey())
				.getLongMethodName()
				.equals("main.tracer.tests.TracerEntryTests getMethodKey()"));
	}

	private TraceEntry getTraceEntry(MethodKey methodKey){
		TraceEntry tr = new TraceEntry();
		tr.setMethod(methodKey);
		//tr.setIsExit(false);
		return tr;
	}

	/**
	 * No params
	 * @return methodKey
	 */
	private MethodKey getMethodKey(){
		try {
			return new MethodKey(this.getClass().getDeclaredMethod("getMethodKey"));
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * One param
	 * @param one
	 * @return
	 */
	private MethodKey getMethodKey(int one){
		try {
			return new MethodKey(this.getClass().
					getDeclaredMethod("getMethodKey", int.class));
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
