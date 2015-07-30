package main.javascript;

import junit.framework.TestCase;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;

public class JscriptWrapperTests extends TestCase {

	protected static final String filename = "src/main/javascript/tests.js";
	

	protected void setup() {
		
	}
	
	/**
	 * create and return a new instance of a JavascriptFileWrapper around the test 
	 * javascript file (src/main/javascript/tests.js).
	 * 
	 * @return 
	 */
	private JavascriptFileWrapper initScriptEngine(){
		
		JavascriptFileWrapper scriptWrapper = null;
		try {
			scriptWrapper = new JavascriptFileWrapper(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {		
			e.printStackTrace();
		}
		
		return scriptWrapper;
	}

	// check load correctly
	@Test
	public void testCorrectFilename() {
		JavascriptFileWrapper scriptWrapper = initScriptEngine();
		assertNotNull(scriptWrapper);
		assertEquals(filename, scriptWrapper.filename());
	}

	@Test
	public void testBasicReturn(){
		JavascriptFileWrapper scriptWrapper = initScriptEngine();
		assertEquals(scriptWrapper.invokeJavascriptMethod("returnZero", (Object[]) null), new Integer(0));
	} 
	
	
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
