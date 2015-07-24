package main.javascript;

import junit.framework.TestCase;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class JscriptWrapperTests extends TestCase {

	protected static final String filename = "tests.js";
	JavascriptFileWrapper scriptWrapper;

	protected void setup() {
		scriptWrapper = new JavascriptFileWrapper(filename);
	}

	// check load correctly
	@Test
	public void testCorrectFilename() {
		assertEquals(filename, scriptWrapper.filename());
	}

	@Test
	public void testFunctionReturns(){
		Object ret = scriptWrapper.invokeJavascriptMethod("returnZero", (Object[]) null);
		assertTrue(ret instanceof Integer);
		int retInt = 1;
		if (ret instanceof Integer)
			 retInt = (int) ret; // overwrites retInt to 0 if succesfull
		assertEquals(0, retInt);
	}
	// check return values from javascript functions
	// check calls functions in javascript changes variables in jscr file,
	// seperate method returns vars, check if changed
	// check something that takes ages to complete in javascript

	// public void testAdd(){
	// double result= value1 + value2;
	// assertTrue(result == 6);
	// }

	//public static void main(String[] args) {

	//}
}
