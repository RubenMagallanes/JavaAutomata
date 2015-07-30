package main.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GeneralFormatToAutomata {
	
	Set<AutomataState> states;
	Set<AutomataLink> links;
	String fileName;
	List<String> methods;
	List<String> fields;
	public GeneralFormatToAutomata(Set<AutomataState> states, Set<AutomataLink> links, String fileName,List<String> methods, List<String> fields){
		this.states = states;
		this.links = links;
		this.fileName = fileName;
		this.methods = methods;
		this.fields = fields;
		
		
		parseAutomata();
		
	}
	
	public void parseAutomata(){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();		
		System.out.print("states:");
		System.out.println(gson.toJson(states) +",");
		System.out.println("links:");
		System.out.println(gson.toJson(links));
		
	}
	public static void main(String[] args){
		
	}
	   
}
