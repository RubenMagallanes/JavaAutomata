package main.tracer.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import main.tracer.MethodKey;

public class MethodKeyTests {

	@Test
	public void construtorTestOne(){
		assertTrue(getMethodKey() != null);
	}

	@Test
	public void constructerTestTwo (){
		assertTrue(getMethodKey(1) != null);
	}

	/**
	 * Ensure that the methodkey is picking up the
	 * parameters.
	 */
	@Test
	public void getArgTypesTestOne(){
		assertTrue(getMethodKey().getArgTypes().length == 0);
	}

	/**
	 * Ensure that the methodkey is picking up the
	 * parameters.
	 */
	@Test
	public void getArgTypesTestTwo(){
		assertTrue(getMethodKey(1).getArgTypes().length == 1);
	}
	
	@Test
	public void testClassNameOne(){
		assertTrue(getMethodKey().getClassName()
				.equals("main.tracer.tests.MethodKeyTests"));
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
