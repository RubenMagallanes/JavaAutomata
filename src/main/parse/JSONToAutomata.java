package main.parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.codemodel.internal.JArray;

public class JSONToAutomata {

	private static final String ENTER_METHOD = "enterMethod";
	private static final String EXIT_METHOD = "exitMethod";
	private static final String METHOD_NAME = "methodName";
	private static final String STATE = "state";

	/**
	 * Parses the specified JSON file and converts that into an {@code Automata}.
	 *
	 * @param file
	 * 		json file containing trace
	 * @return
	 * 		automata representing trace
	 */
	public static Automata generateAutomata(File file){
		String json = JSONFileToString(file);
		JSONArray data = new JSONArray(json);

		Set<AutomataState> states = new HashSet<AutomataState>();
		Set<AutomataLink> links = new HashSet<AutomataLink>();
		int id = 0;

		for(int i = 0; i < data.length(); i++){
			JSONObject object = data.getJSONObject(i);

			// edge cases where method entry and exit don't match up
			if(i == 0 || i == data.length() - 1){
				if(object.has(ENTER_METHOD)){
					AutomataState source = parseEnterMethod(object.getJSONObject(ENTER_METHOD), id);
					states.add(source);
					id++;
				}
				else if(object.has(EXIT_METHOD)){
					AutomataState target = parseExitMethod(object.getJSONObject(EXIT_METHOD), id);
					if(!states.contains(target)){
						states.add(target);
					}
				}
			}
			else{
				// parse states
				AutomataState source = parseEnterMethod(object.getJSONObject(ENTER_METHOD), id);
				// only add if source is a unique state
				if(!states.contains(source)){
					states.add(source);
					id++; // increment id
				}
				AutomataState target = parseExitMethod(data.getJSONObject(++i).getJSONObject(EXIT_METHOD), id);
				// only add if target is a unique state
				if(!states.contains(target)){
					states.add(target);
					id++; // increment id;
				}

				// construct link between states
				String methodName = (object.has(ENTER_METHOD)) ? object.getJSONObject(ENTER_METHOD).getString(METHOD_NAME) :
					object.getJSONObject(EXIT_METHOD).getString(METHOD_NAME);
				links.add(new AutomataLink(methodName, source.getId(), target.getId()));
			}
		}

		return new Automata(states, links);
	}

	private static AutomataState parseEnterMethod(JSONObject object, int id){
		JSONObject state = object.getJSONObject(STATE);
		List<AutomataField> fields = new ArrayList<AutomataField>();
		for(String key : state.keySet()){
			fields.add(new AutomataField(null, key, state.getString(key)));
		}

		return new AutomataState(fields, id);
	}

	private static AutomataState parseExitMethod(JSONObject object, int id){
		JSONObject state = object.getJSONObject(STATE);
		List<AutomataField> fields = new ArrayList<AutomataField>();
		for(String key : state.keySet()){
			fields.add(new AutomataField(null, key, state.getString(key)));
		}

		return new AutomataState(fields, id);
	}

	private static String JSONFileToString(File file){
		try {
			Scanner scan = new Scanner(file);
			String json = "";
			while(scan.hasNextLine()){
				json += scan.nextLine();
			}
			return json;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
