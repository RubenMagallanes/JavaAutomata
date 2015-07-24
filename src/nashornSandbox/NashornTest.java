package nashornSandbox;

import java.util.Arrays;

import javax.script.*;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

/**
 * from the oracle tutorial demo for using javascript in java, heavily commented
 *
 */
public class NashornTest {

	public static void main(String[] args) throws ScriptException, NoSuchMethodException {
		/*
		 * A ScriptEngineManager object is used to instantiate ScriptEngine
		 * objects and maintain global variable values shared by them.
		 */
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		/*
		 * To get an instance of the Nashorn engine, pass in "nashorn".
		 * Alternatively, you can use any of the following: "Nashorn",
		 * "javascript", "JavaScript", "js", "JS", "ecmascript", "ECMAScript".
		 */
		ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");

		// executes script , returns null
		Object ret = nashorn.eval("load('src/nashornSandbox/helloScript.js')");
		Object ret2 = nashorn.eval(
				"var fun1 = function (object){print('n javascript file');print('this is function 1 ');return 'this is from javascript';};");

		// cast to invocable in order to call functions
		Invocable invoke = (Invocable) nashorn;

		/* calls function arg1 with parameters argv.
		 * throws ScriptException, NoSuchMethodException.
		 * returns whatever the Jscript function returns, saved in result
		 */
		Object result = invoke.invokeFunction("fun1", "hi");

		
		System.out.println("from java code");
		System.out.println(result);
		System.out.println(result.getClass());
	}
	
	public static void javaFunction1(ScriptObjectMirror mirror)
	{
		//getownkeys returns atrray of string of all property keys associated with it
		System.out.println(mirror.getClassName() + ": "+ Arrays.toString(mirror.getOwnKeys(true)));
		
	}

}
