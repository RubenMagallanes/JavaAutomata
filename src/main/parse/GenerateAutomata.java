package main.parse;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Generates a random Automata and writes to a json file. This class is intended for 
 * debugging the visualisation
 * @author Chris
 *
 */
public class GenerateAutomata {
	
	private String[] types = new String[]{"int", "String","float","double"};
	private String[] fieldNames = new String[]{"a","b","c","x","y","z"};
	private String[] strings = new String[]{"Chris","David","Will","Nicky","Shane","Ruben"};
	private String[] methodNames = new String[]{"toString","mutate","clone","add","minus","divide","multiply"};
	
	private Map <String,String> fieldsToTypes;// map from fields to their types	
	private int numberOfStates;
	private Set<AutomataState> automatastates;
	private Set<AutomataLink> automatalinks;	
	private String filename;	
	
	/**
	 * Generates a random automata with the given number of states 
	 * @param numberOfStates the number of states to generate
	 * @param filename the file name to save the json to
	 */
	public GenerateAutomata(int numberOfStates,String filename){
		this.numberOfStates = numberOfStates;
		this.automatastates = new LinkedHashSet<AutomataState>();
		this.automatalinks = new LinkedHashSet<AutomataLink>();
		this.fieldsToTypes = new HashMap<String, String>();
		this.filename = filename;
		generateMap();
		for(int i = 0; i<numberOfStates; i++){
			automatastates.add(new AutomataState(generateFields(),i));
		}
		generateLinks();	
		GeneralFormatToAutomata g = new GeneralFormatToAutomata(automatastates, automatalinks);	
		String automata = g.parseAutomata();
		System.out.println(automata);
		
		//writes json to file
		try {
			PrintWriter out = new PrintWriter(filename);
			out.println(automata);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Fills the automatalinks with random links
	 */
	private void generateLinks() {
		for(int i = 0; i<numberOfStates; i++){
			List<String> methodNamesList =  new ArrayList<String>(Arrays.asList(methodNames));
			int numberOfLinks = (int) (Math.random()*methodNames.length); //number of links from the current source			
			for(int j =0; j<numberOfLinks; j++){
				int target = (int) (Math.random()*(numberOfStates-i) + i);
				if(target == i)
					continue;
				int index = (int) (Math.random()*methodNamesList.size());
				String methodName = methodNamesList.remove(index);
				automatalinks.add(new AutomataLink(methodName,i,target));
			}
		}		
	}
	
	/**
	 * Generates a random map from fields to types to randomise the types
	 */
	private void generateMap(){
		for(String names : fieldNames){
			int index = (int) (Math.random()*types.length);
			fieldsToTypes.put(names,types[index]);
		}
	}	
	
	/**
	 * Generates a list of AutomataField from the map of fields
	 * @return
	 */
	private List<AutomataField> generateFields(){
		List <AutomataField> f1 = new ArrayList<AutomataField>();		
		for(String fieldName: fieldsToTypes.keySet()){
			String type = fieldsToTypes.get(fieldName);
			f1.add(new AutomataField(fieldName, type,generateRandomValue(type)));			
		}		
		return f1;		
	}
	
	/**
	 * Generates a random value of the given type
	 * @param type type of variable 
	 * @return String of the randomly generated value
	 */
	private String generateRandomValue(String type) {
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

	/**
	 * Takes the two commandline arguments - int numberOfStates, String filename
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length<2){
			GenerateAutomata g = new GenerateAutomata(15,"automata.json");
		}
		else{
			GenerateAutomata g = new GenerateAutomata(Integer.parseInt(args[0]),args[1]);
		}		
	}
}
