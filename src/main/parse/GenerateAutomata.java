package main.parse;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GenerateAutomata {
	
	private String[] types = new String[]{"int", "String","float","double"};
	private String[] fieldNames = new String[]{"a","b","c","x","y","z"};
	private String[] strings = new String[]{"Chris","David","Will","Nicky","Shane","Ruben"};
	private String[] methodNames = new String[]{"toString","mutate","clone","add","minus","divide","multiply"};
	
	private Map <String,String> fieldsToTypes;// map from fields to their types
	
	private int count;
	private Set<AutomataState> automatastates;
	private Set<AutomataLink> automatalinks;
	
	private String filename;
	
	
	public GenerateAutomata(int count,String filename){
		this.count = count;
		this.automatastates = new LinkedHashSet<AutomataState>();
		this.automatalinks = new LinkedHashSet<AutomataLink>();
		this.fieldsToTypes = new HashMap<String, String>();
		this.filename = filename;
		generate();
	}
	
	public void generate(){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		generateMap();
		for(int i = 0; i<count; i++){
			automatastates.add(new AutomataState(generateFields(),i));
		}
		generateLinks();	
		GeneralFormatToAutomata g = new GeneralFormatToAutomata(automatastates, automatalinks);	
		String automata = g.parseAutomata();
		System.out.println(automata);	
		
		try {
			PrintWriter out = new PrintWriter(filename);
			out.println(automata);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}
			
	private void generateLinks() {
		for(int i = 0; i<count; i++){
			List<String> methodNamesList =  new ArrayList<String>(Arrays.asList(methodNames));
			int numberOfLinks = (int) (Math.random()*methodNames.length); //number of links from the current source			
			for(int j =0; j<numberOfLinks; j++){
				int target = (int) (Math.random()*count);
				int index = (int) (Math.random()*methodNamesList.size());
				String methodName = methodNamesList.remove(index);
				automatalinks.add(new AutomataLink(methodName,i,target));
			}
		}		
	}
	
	private void generateMap(){
		for(String names : fieldNames){
			int index = (int) (Math.random()*types.length);
			fieldsToTypes.put(names,types[index]);
		}
	}	
	
	private List<AutomataField> generateFields(){
		List <AutomataField> f1 = new ArrayList<AutomataField>();		
		for(String fieldName: fieldsToTypes.keySet()){
			String type = fieldsToTypes.get(fieldName);
			f1.add(new AutomataField(fieldName, type,generateRandomNumber(type)));			
		}		
		return f1;		
	}
	
	private String generateRandomNumber(String type) {
		if(type.equals("int")){
			return (int) (Math.random()*1000000) + "";
		}
		else if(type.equals("double")){
			return (Math.random()*1000000) + "";
		}
		else if(type.equals("float")){
			return (Math.random()*1000000) + "f";
		}
		else if(type.equals("String")){
			int index = (int) (Math.random()*strings.length);
			return strings[index];
		}
		else{
			return "null";
		}			
	}

	public static void main(String[] args) {
		if(args.length<2){
			GenerateAutomata g = new GenerateAutomata(15,"automata.json");
		}
		else{
			GenerateAutomata g = new GenerateAutomata(Integer.parseInt(args[0]),args[1]);
		}
		
	}
}
