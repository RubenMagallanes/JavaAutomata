package main.tests;

import static org.junit.Assert.*;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import main.javascript.JavascriptFileWrapper;
import main.parse.AutomataField;
import main.parse.AutomataLink;
import main.parse.AutomataState;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class GeneralFormatToAutomataTests {

	
	@Test
	public void testJava(){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		AutomataLink l= new AutomataLink("hi",1,2);
		List <AutomataField> f = new ArrayList<AutomataField>();
		f.add(new AutomataField("int", "count", "7"));
		f.add(new AutomataField("string", "name", "chris"));
		AutomataState s = new AutomataState(f);
		
		List<AutomataState> automatastates = new ArrayList<AutomataState>();
		automatastates.add(s);
		automatastates.add(s);
		
		List<AutomataLink> automatalinks = new ArrayList<AutomataLink>();
		automatalinks.add(l);
		automatalinks.add(l);		
		
		System.out.print("states:");
		System.out.println(gson.toJson(automatastates) +",");
		System.out.println("links:");
		System.out.println(gson.toJson(automatalinks));
	}

}
