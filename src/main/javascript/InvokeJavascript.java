package main.javascript;

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

public class InvokeJavascript {
	
	private String filename;
	private ScriptEngine engine;
	private Invocable invocable;
	
	/**
	 * 
	 * @param filename - name of the javaScript file
	 * @throws ScriptException 
	 * @throws FileNotFoundException 
	 */
	public InvokeJavascript(String filename) throws FileNotFoundException, ScriptException{
		this.filename = filename;
		this.engine = new ScriptEngineManager().getEngineByName("nashorn");
		engine.eval(new FileReader(filename));
		this.invocable = (Invocable) engine;
	}
	
	public void invokeJavascriptMethod(String methodName, String parameter) throws NoSuchMethodException, ScriptException{		
		invocable.invokeFunction(methodName, parameter);
	}
	
	public static void main(String[] args) throws FileNotFoundException, ScriptException, NoSuchMethodException{
		InvokeJavascript i = new InvokeJavascript("src/main/javascript/script.js");
		i.invokeJavascriptMethod("fun1", "chris");
	}
	
//	public static String fun1(String name) {
//	    System.out.format("Hi there from Java, %s \n", name);
//	    return "greetings from java";
//	}
//	
//	public static void fun3(ScriptObjectMirror mirror) throws IOException, SAXException {
//	    System.out.println(mirror.getClassName() + ": " +
//	        Arrays.toString(mirror.getOwnKeys(true)));
//	    File htmlFile = new File("index.html");
//	    Desktop.getDesktop().browse(htmlFile.toURI());
//	}
//	
//
//	
//	static void fun4(ScriptObjectMirror person) throws IOException {
//		System.out.println("Full Name is: " + person.callMember("getFullName"));
//	   
//	}
	

}
