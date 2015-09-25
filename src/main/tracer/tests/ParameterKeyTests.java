package main.tracer.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import main.tracer.MethodKey;
import main.tracer.ParameterKey;

public class ParameterKeyTests {

	@Test
	public void constructorTest(){
		assertTrue(getParameterKey() != null);
	}

	@Test
	public void getIndexTest(){
		assertTrue(getParameterKey().getIndex() == 0);
	}

	private ParameterKey getParameterKey(){
		return new ParameterKey(getMethodKey(1), 0);
	}

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
