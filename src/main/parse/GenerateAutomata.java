package main.parse;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Generates a random Automata and writes to a json file. This class is intended for
 * debugging the visualisation
 * @author Chris Chin
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
		generateMap();
		for(int i = 0; i<numberOfStates; i++){
			automatastates.add(new AutomataState(generateFields(),i, false));
		}
		generateLinks();
		AutomataToVisualisation g = new AutomataToVisualisation(new Automata(automatastates, automatalinks));
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

			//Adds a link from i to i+1
			if(i != numberOfStates-1){
				generateLink(methodNamesList, i, i+1);
			}

			//Creates a list of random indices from i+1 to the last
			ArrayList<Integer> indexList = new ArrayList<Integer>();
	        for (int ranIndex=i+1; ranIndex<numberOfStates; ranIndex++) {
	            indexList.add(new Integer(ranIndex));
	        }
	        Collections.shuffle(indexList);

	        //Adds a link from i to a random larger index
	        for (int ind=0; ind<methodNamesList.size() && ind<indexList.size(); ind++) {
	        	generateLink(methodNamesList, i, indexList.remove(ind));
	        }
		}
	}

	/**
	 * Generates a random Link
	 * @param methodNamesList - list of method names
	 * @param source - source id
	 * @param target - target id
	 */
	private void generateLink(List<String> methodNamesList, int source, int target){
		int index = (int) (Math.random()*methodNamesList.size());
		String methodName = methodNamesList.remove(index);
		automatalinks.add(new AutomataLink(methodName,source,target));
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
			f1.add(new AutomataField(type, fieldName,generateRandomValue(type)));
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
	 * Takes the two command line arguments - numberOfStates, filename
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length !=2){
			System.out.println("Please enter 2 args - number of states and the filename");
		}
		else{
			new GenerateAutomata(Integer.parseInt(args[0]),args[1]);
		}
	}
}
