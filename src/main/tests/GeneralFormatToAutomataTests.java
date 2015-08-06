package main.tests;

import static org.junit.Assert.*;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import main.javascript.JavascriptFileWrapper;
import main.parse.AutomataField;
import main.parse.AutomataLink;
import main.parse.AutomataState;
import main.parse.GeneralFormatToAutomata;

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
		AutomataState s = new AutomataState(f,1);
		
		List<AutomataState> automatastates = new ArrayList<AutomataState>();
		automatastates.add(s);
		automatastates.add(s);
		
		List<AutomataLink> automatalinks = new ArrayList<AutomataLink>();
		automatalinks.add(l);
		automatalinks.add(l);		
		
//		System.out.print("states:");
//		System.out.println(gson.toJson(automatastates) +",");
//		System.out.println("links:");
//		System.out.println(gson.toJson(automatalinks));
	}
	
	/**
	 * This test checks whether the filters work
	 * The json should only include the count field and toString()
	 */
	@Test
	public void testJavaFilter(){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		List <AutomataField> f1 = new ArrayList<AutomataField>();
		f1.add(new AutomataField("int", "count", "7"));
		AutomataState s1 = new AutomataState(f1,1);
		
		List <AutomataField> f2 = new ArrayList<AutomataField>();
		f2.add(new AutomataField("string", "name", "chris"));
		AutomataState s2 = new AutomataState(f2,2);		

		AutomataLink l1= new AutomataLink("toString()",1,2);
		AutomataLink l2= new AutomataLink("countDuplicates()",1,2);		
		
		Set<AutomataState> automatastates = new HashSet<AutomataState>();
		automatastates.add(s1);
		automatastates.add(s2);
		
		Set<AutomataLink> automatalinks = new HashSet<AutomataLink>();
		automatalinks.add(l1);
		automatalinks.add(l2);		
		
		List<String> methods = new ArrayList<String>();
		methods.add("toString()");
		
		List<String> fields = new ArrayList<String>();
		fields.add("count");
		
		GeneralFormatToAutomata g = new GeneralFormatToAutomata(automatastates, automatalinks, methods, fields);		
		System.out.println(g.parseAutomata());
		
	}
	

}
