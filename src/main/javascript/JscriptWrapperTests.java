package main.javascript;

import junit.framework.TestCase;

import org.junit.Test;
import java.io.FileNotFoundException;

public class JscriptWrapperTests extends TestCase {

	protected static final String filename = "src/main/javascript/tests.js";
	
	protected void setup() {
	}
	
	/**
	 * create and return a new instance of a JavascriptFileWrapper around the test 
	 * javascript file (src/main/javascript/tests.js).
	 * 
	 * @return JavascriptFileWrapper that handles function calls to tests.js
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
	
	@Test
	public void testFunctionsChangeVariables(){
		JavascriptFileWrapper scriptWrapper = initScriptEngine();
		assertEquals(scriptWrapper.invokeJavascriptMethod("returnHelloString", (Object[]) null), "hello world");
		
		String statement = "goodbuy world";
		scriptWrapper.invokeJavascriptMethod("changeHelloString", statement);
		assertEquals(scriptWrapper.invokeJavascriptMethod("returnHelloString", (Object[]) null), statement);
	}
	
	@Test 
	public void testSlowJscriptMethod(){
		JavascriptFileWrapper scriptWrapper = initScriptEngine();
		assertEquals(scriptWrapper.invokeJavascriptMethod("returnOurVariable", (Object[]) null), new Integer(0));
		scriptWrapper.invokeJavascriptMethod("slowFunction", (Object[]) null);// this should take a while- 10 secs
		assertEquals(scriptWrapper.invokeJavascriptMethod("returnOurVariable", (Object[]) null), new Integer(1));
		//this test confirms the java line blocks until the jscript function is complete. 
	}	
}
