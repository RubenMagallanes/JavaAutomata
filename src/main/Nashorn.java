package main;

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
import org.xml.sax.SAXException;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

public class Nashorn {

	public static void main(String[] args) throws ScriptException, FileNotFoundException, NoSuchMethodException {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
		engine.eval(new FileReader("script.js"));

		Invocable invocable = (Invocable) engine;

		Object result = invocable.invokeFunction("fun1", "Peter Parker");
		System.out.println(result);
		System.out.println(result.getClass());
	}
	
	public static String fun1(String name) {
	    System.out.format("Hi there from Java, %s \n", name);
	    return "greetings from java";
	}
	
	public static void fun3(ScriptObjectMirror mirror) throws IOException, SAXException {
	    System.out.println(mirror.getClassName() + ": " +
	        Arrays.toString(mirror.getOwnKeys(true)));
	    File htmlFile = new File("index.html");
	    Desktop.getDesktop().browse(htmlFile.toURI());
	}
	

	
	static void fun4(ScriptObjectMirror person) throws IOException {
		System.out.println("Full Name is: " + person.callMember("getFullName"));
	   
	}

}
