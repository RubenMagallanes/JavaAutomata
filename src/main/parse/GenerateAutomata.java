package main.parse;

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
	Set<AutomataState> automatastates;
	Set<AutomataLink> automatalinks;
	
	public GenerateAutomata(int count){
		this.count = count;
		this.automatastates = new LinkedHashSet<AutomataState>();
		this.automatalinks = new LinkedHashSet<AutomataLink>();
		this.fieldsToTypes = new HashMap<String, String>();
		generateMap();

		for(String k: fieldsToTypes.keySet()){
			System.out.println(k + " : " + fieldsToTypes.get(k));
		}		
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
		System.out.println(g.parseAutomata());	
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
	
	public void generateMap(){
		for(String names : fieldNames){
			int index = (int) (Math.random()*types.length);
			fieldsToTypes.put(names,types[index]);
		}
	}	
	
	public List<AutomataField> generateFields(){
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
		GenerateAutomata g = new GenerateAutomata(15);
	}
}
