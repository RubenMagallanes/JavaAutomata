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
import java.util.TreeMap;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Static class which is used to convert either a {@code String} of JSON data
 * or a {@code File} containing JSON data into an {@code Automata}.
 *
 * @author David Sheridan
 *
 */
public class TraceToAutomata {

	/**
	 * {@code String} representing the "methodName" JSON key.
	 */
	private static final String METHOD = "methodName";

	/**
	 * {@code String} representing the "stateBefore" JSON key.
	 */
	private static final String STATE_BEFORE = "stateBefore";

	/**
	 * {@code String} representing the "stateAfter" JSON key.
	 */
	private static final String STATE_AFTER = "stateAfter";

	/**
	 * {@code String} representing the "children" JSON key.
	 */
	private static final String CHILDREN = "children";

	/**
	 * {@code String} representing the "name" JSON key.
	 */
	private static final String VAR_NAME = "name";

	/**
	 * {@code String} representing the "value" JSON key.
	 */
	private static final String VAR_VALUE = "value";

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
		JSONObject data = new JSONObject(json);

		Map<Integer, AutomataState> states = new TreeMap<Integer, AutomataState>();
		Set<AutomataLink> links = new TreeSet<AutomataLink>();

		JSONObjectToAutomata(data, states, links);

		// remove the empty state from states
		int id = states.size() - 1;
		states.remove(id);

		// remove any references to empty state from links
		Set<AutomataLink> remove = new HashSet<AutomataLink>();
		for(AutomataLink link : links){
			if(link.getSource() == id || link.getTarget() == id){
				remove.add(link);
			}
		}
		for(AutomataLink link : remove){
			links.remove(link);
		}

		return new Automata(new TreeSet<AutomataState>(states.values()), links);
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
	 * Parses the specified {@code JSONObject} into a series of states and links to form an {@code Automata}.
	 * Recursively calls itself to deal with nested objects within the {@code JSONObject}.
	 *
	 * @param json
	 * 		- the json object to be parsed
	 * @param id
	 * 		- the id of the current state
	 * @param states
	 * 		- map of ids to states
	 * @param links
	 * 		- edges between states
	 *
	 * @return
	 * 		- the next id value
	 */
	private static void JSONObjectToAutomata(JSONObject json, Map<Integer, AutomataState> states, Set<AutomataLink> links){
		// check if the json object has any children
		JSONArray array = json.getJSONArray(CHILDREN);

		for(int i = 0; i < array.length(); i++){
			JSONObject data = array.getJSONObject(i);
			JSONObjectToAutomata(data, states, links);
		}

		AutomataState stateBefore = parseState(json.optJSONArray(STATE_BEFORE), states.size());
		stateBefore = checkForDuplicateState(stateBefore, states);
		states.put(stateBefore.getId(), stateBefore);

		AutomataState stateAfter = parseState(json.optJSONArray(STATE_AFTER), states.size());
		stateAfter = checkForDuplicateState(stateAfter, states);
		states.put(stateAfter.getId(), stateAfter);

		AutomataLink link = new AutomataLink(json.getJSONArray(METHOD).getString(0), stateBefore.getId(), stateAfter.getId());
		// only add link if it is not already in links
		if(!links.contains(link)){
			links.add(link);
		}
		else{
			// otherwise increment count of link
			link.incrementCount();
		}
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
	private static AutomataState parseState(JSONArray state, int id){
		List<AutomataField> fields = new ArrayList<AutomataField>();

		// check that state is not null
		if(state != null){
			JSONObject current = null;
			// add all fields in state to fields list
			for(int i = 0; i < state.length(); i++){
				current = state.getJSONObject(i);
				String key = current.getString(VAR_NAME);
				String value = current.getString(VAR_VALUE);
				fields.add(new AutomataField(null, key, value));
			}
		}

		return new AutomataState(fields, id);
	}

	/**
	 * Performs a check to see if the specified {@code AutomataState} exists. If so returns the
	 * previous
	 * @param state
	 * 		- the current state
	 * @param states
	 * 		- set of all states
	 *
	 * @return
	 * 		-
	 */
	private static AutomataState checkForDuplicateState(AutomataState state, Map<Integer, AutomataState> states){
		if(states.values().contains(state)){
			for(int i : states.keySet()){
				if(states.get(i).equals(state)){
					return states.get(i);
				}
			}
		}
		return state;
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
		File file = new File("data/traces/checktrace.json");
		try {
			Automata a = generateAutomata(file);
			GeneralFormatToAutomata json = new GeneralFormatToAutomata(a);
			System.out.println(json.parseAutomata());
		} catch (JSONToAutomataException e) {
			e.printStackTrace();
		}
	}
}
