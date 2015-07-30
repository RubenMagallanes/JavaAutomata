package main.tests;

import static org.junit.Assert.*;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import main.javascript.JavascriptFileWrapper;

import org.junit.Test;


public class JavaWrapperTests {

	public JavaWrapperTests(){
		File htmlFile = new File("src/main/javascript/test.html");
	    try {
			Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	        
	}

	/**
	 * Tests the strings from javascript to java
	 * @throws ScriptException
	 * @throws NoSuchMethodException
	 * @throws IOException
	 */
	@Test
	public void testJava() throws ScriptException, NoSuchMethodException, IOException{
	    JavascriptFileWrapper j = new JavascriptFileWrapper("src/main/javascript/javawrapper.js");
	    Object o =j.invokeJavascriptMethod("test");
		assertTrue(o.equals("greetings bob"));
	}
	
	/**
	 * Tests the Arrays from javascript to java
	 * @throws ScriptException
	 * @throws NoSuchMethodException
	 * @throws IOException
	 */
	@Test
	public void testJava2() throws ScriptException, NoSuchMethodException, IOException{
	    JavascriptFileWrapper j = new JavascriptFileWrapper("src/main/javascript/javawrapper.js");
	    Object o =j.invokeJavascriptMethod("test2");
	    String expected = "[foo, bar]";
	    String actual = Arrays.toString((String[])o);
		assertTrue(actual.equals(expected));
	}

	 public static String fun1(String name) {
		 return "greetings "+ name;
	 }
	 
	 public static String[] fun3(ScriptObjectMirror mirror) {
		    return mirror.getOwnKeys(true);
	 }
}
