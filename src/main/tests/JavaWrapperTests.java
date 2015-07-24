package main.tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import main.javascript.JavascriptFileWrapper;

import org.junit.Test;


public class JavaWrapperTests {	

	private ScriptEngine engine;
	private Invocable invocable;

	@Test 
	public void testJava(){
		JavascriptFileWrapper i = new JavascriptFileWrapper("src/main/javascript/test.js");
	}
}
