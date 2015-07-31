package main.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GeneralFormatToAutomata {
	
	Set<AutomataState> states;
	Set<AutomataLink> links;
	List<String> methods;
	List<String> fields;
	
	/**
	 * Converts the general format of the trace to a json which can be used to draw an automata
	 * This includes the methods and fields parameter which filters which ones the user wants to display
	 * @param states
	 * @param links
	 * @param methods
	 * @param fields
	 */
	public GeneralFormatToAutomata(Set<AutomataState> states, Set<AutomataLink> links,List<String> methods, List<String> fields){
		this.states = states;
		this.links = links;
		this.methods = methods;
		this.fields = fields;
		filter();		
	}
	
	/**
	 * Converts the general format of the trace to a json which can be used to draw an automata
	 * @param states
	 * @param links
	 */
	public GeneralFormatToAutomata(Set<AutomataState> states, Set<AutomataLink> links){
		this.states = states;
		this.links = links;	
	}
	
	private void filter(){
		for(AutomataState s: states){
			List <AutomataField> currentfields = s.getFields();
			List <AutomataField> removefields = new ArrayList <AutomataField>();
			
			for(AutomataField currentfield : currentfields){
				if(!fields.contains(currentfield.getName())){
					removefields.add(currentfield);
				}
			}
			
			currentfields.removeAll(removefields);
		}
		
		List <AutomataLink> removelinks = new ArrayList <AutomataLink>();
		for(AutomataLink l: links){
			if(!methods.contains(l.getName())){
				removelinks.add(l);
			}
		}
		links.removeAll(removelinks);
	}
	
	public String parseAutomata(){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();	
		String s = "states:" + gson.toJson(states) +",links:" +gson.toJson(links); 
		System.out.println(s);
		return s;		
	}	   
}
