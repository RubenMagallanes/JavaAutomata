package main.tests;

import static org.junit.Assert.*;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import main.javascript.JavascriptFileWrapper;

import org.junit.Test;


public class JavaWrapperTests {


	@Test
	public void testJava() throws ScriptException, NoSuchMethodException, IOException{
		File htmlFile = new File("src/main/javascript/test.html");
	    Desktop.getDesktop().browse(htmlFile.toURI());
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
		engine.eval(new FileReader("src/main/javascript/test.js"));
		Invocable invocable = (Invocable) engine;
		Object o = invocable.invokeFunction("test");
		assertTrue(o.equals("greetings bob"));
	}

	 public static String fun1(String name) {
		 return "greetings "+ name;
	 }
}
