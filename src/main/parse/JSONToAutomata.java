package main.parse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Static class which is used to convert either a {@code String} of JSON data
 * or a {@code File} containing JSON data into an {@code Automata}.
 *
 * @author David Sheridan
 *
 */
public class JSONToAutomata {

	/**
	 * {@code String} representing the "enterMethod" JSON key.
	 */
	private static final String ENTER_METHOD = "enterMethod";

	/**
	 * {@code String} representing the "exitMethod" JSON key.
	 */
	private static final String EXIT_METHOD = "exitMethod";

	/**
	 * {@code String} representing the "methodName" JSON key.
	 */
	private static final String METHOD_NAME = "methodName";

	/**
	 * {@code String} representing the "state" JSON key.
	 */
	private static final String STATE = "state";

	/**
	 * Parses the specified {@code String} containing JSON data and converts that
	 * into an {@code Automata}.
	 *
	 * @param json
	 * 		string containing json data
	 * @return
	 * 		automata representing trace
	 * @throws JSONToAutomataException
	 * 		thrown if format of json string is invalid
	 */
	public static Automata generateAutomata(String json) throws JSONToAutomataException{
		JSONArray data = new JSONArray(json);

		Map<Integer, AutomataState> states = new HashMap<Integer, AutomataState>();
		Set<AutomataLink> links = new HashSet<AutomataLink>();
		int id = 0;

		AutomataState previous = null;
		for(int i = 0; i < data.length(); i++){
			JSONObject object = data.getJSONObject(i);
			AutomataState current = null;

			// parse the current state
			if(object.has(ENTER_METHOD)){
				current = parseState(object.getJSONObject(ENTER_METHOD), id);
			}
			else if(object.has(EXIT_METHOD)){
				current = parseState(object.getJSONObject(EXIT_METHOD), id);
			}
			else{
				throw new JSONToAutomataException("Error: expecting either " + ENTER_METHOD + " or " + EXIT_METHOD + " as key.");
			}
			if(!states.values().contains(current)){
				states.put(id, current);
				id++; // increment id to ensure unique value
			}
			else{
				for(int j = 0; j < id; j++){
					if(states.get(j).equals(current)){
						current = states.get(j);
					}
				}
			}

			// construct link between the current and previous states
			if(previous != null){
				String methodName = parseMethodName(object);
				links.add(new AutomataLink(methodName, previous.getId(), current.getId()));
			}

			// set current state to be previous for next iteration
			previous = current;
		}

		return new Automata(new HashSet<AutomataState>(states.values()), links);
	}

	/**
	 * Parses the specified JSON file and converts that into an {@code Automata}.
	 *
	 * @param file
	 * 		json file containing trace
	 * @return
	 * 		automata representing trace
	 * @throws JSONToAutomataException
	 * 		thrown if specified file is invalid
	 */
	public static Automata generateAutomata(File file) throws JSONToAutomataException{
		String json = JSONFileToString(file);
		return generateAutomata(json);
	}

	/**
	 * Parses the {@code AutomataState} from the specified {@code JSONObject}.
	 *
	 * @param object
	 * 		the json object to be parsed
	 * @param id
	 * 		unique id for state
	 * @return
	 * 		{@code AutomataState}
	 */
	private static AutomataState parseState(JSONObject object, int id){
		JSONObject state = object.getJSONObject(STATE);
		List<AutomataField> fields = new ArrayList<AutomataField>();

		for(String key : state.keySet()){
			String value = (state.isNull(key)) ? null : state.optString(key);
			fields.add(new AutomataField(null, key, value));
		}
		return new AutomataState(fields, id);
	}

	/**
	 * Parses the method name from the specified {@code JSONObject}.
	 *
	 * @param object
	 * 		json object to be parsed
	 * @return
	 * 		method name
	 */
	private static String parseMethodName(JSONObject object){
		if(object.has(ENTER_METHOD)){
			return object.getJSONObject(ENTER_METHOD).getString(METHOD_NAME);
		}
		return object.getJSONObject(EXIT_METHOD).getString(METHOD_NAME);
	}

	/**
	 * Converts the specified JSON file into a {@code String} value and returns it.
	 *
	 * @param file
	 * 		json file to convert
	 * @return
	 * 		string representation of json file
	 * @throws JSONToAutomataException
	 * 		thrown if file is invalid
	 */
	private static String JSONFileToString(File file) throws JSONToAutomataException{
		Scanner scan = null;
		try{
			scan = new Scanner(file);
		}catch(FileNotFoundException e){throw new JSONToAutomataException("FileNotFoundException: " + e);}
		String json = "";
		while(scan.hasNextLine()){
			json += scan.nextLine();
		}
		scan.close();
		return json;
	}

	public static void main(String[] args){
		File file = new File("data/traces/TestProgram2Trace.json");
		try {
			Automata a = generateAutomata(file);
			AutomataToVisualisation json = new AutomataToVisualisation(a);
			System.out.println(json.parseAutomata());
		} catch (JSONToAutomataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}