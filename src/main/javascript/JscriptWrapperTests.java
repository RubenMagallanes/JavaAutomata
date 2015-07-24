package main.javascript;

import junit.framework.TestCase;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class JscriptWrapperTests extends TestCase {

	protected static final String filename = "tests.js";
	

	protected void setup() {
		System.out.println("ayy lmao");
		//scriptWrapper = new JavascriptFileWrapper(filename);
		System.out.println("filename: " + filename);
		
	}

	// check load correctly
	@Test
	public void testCorrectFilename() {
		JavascriptFileWrapper scriptWrapper = new JavascriptFileWrapper(filename);
		System.out.println("script wrapper is null: "+scriptWrapper  == null);
		assertEquals(filename, scriptWrapper.filename());
	}
	//TODO system cant find file tests.js - sort it 
	//rename test.js to avoid confusion
	//add tests
	//@Test
	/*public void testFunctionReturns(){
		Object ret = scriptWrapper.invokeJavascriptMethod("returnZero", (Object[]) null);
		assertTrue(ret instanceof Integer);
		int retInt = 1;
		if (ret instanceof Integer)
			 retInt = (int) ret; // overwrites retInt to 0 if succesfull
		assertEquals(0, retInt);
	}*/
	// check return values from javascript functions
	// check calls functions in javascript changes variables in jscr file,
	// seperate method returns vars, check if changed
	// check something that takes ages to complete in javascript


	
}
