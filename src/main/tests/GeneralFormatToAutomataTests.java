package main.tests;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.parse.Automata;
import main.parse.AutomataField;
import main.parse.AutomataLink;
import main.parse.AutomataState;
import main.parse.AutomataToVisualisation;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class GeneralFormatToAutomataTests {

	/**
	 * This tests GeneralFormatToAutomata without filters
	 * this test output should be identical to GeneralFormatToAutomata1.json
	 */
	@Test
	public void testJava(){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		AutomataLink l= new AutomataLink("hi",1,2);
		List <AutomataField> f = new ArrayList<AutomataField>();
		f.add(new AutomataField("int", "count", "7"));
		f.add(new AutomataField("string", "name", "chris"));
		AutomataState s = new AutomataState(f,1);
		
		Set<AutomataState> automatastates = new HashSet<AutomataState>();
		automatastates.add(s);
		automatastates.add(s);
		
		Set<AutomataLink> automatalinks = new HashSet<AutomataLink>();
		automatalinks.add(l);
		automatalinks.add(l);		
		
		AutomataToVisualisation g = new AutomataToVisualisation(new Automata(automatastates, automatalinks));		
//		System.out.println(g.parseAutomata());	
	}
	
	/**
	 * This test checks whether the filters work
	 * The json should only include the count field and toString()
	 * This test output should be identical to GeneralFormatToAutomataTest2.json
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
		
		AutomataToVisualisation g = new AutomataToVisualisation(new Automata(automatastates, automatalinks), methods, fields);		
		System.out.println(g.parseAutomata());		
	}
}
