package main.tracer.state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import main.tracer.FieldKey;
import main.tracer.TraceFilter;

public class ObjectState extends State {

	private static final long serialVersionUID = 1L;

	private static final String NAME = "name";
	private static final String VALUE = "value";

	//The name of the class
	private String className;

	//A map of all the fields the class holds
	private Map<FieldKey, State> fields = new HashMap<>();


	private Map<FieldKey, State> originalFields = new HashMap<>();

	public ObjectState(String className) {
		this.className = className;
	}


	@Override
	public String toString() {
		Map<State, String> alreadySeenObjects = new IdentityHashMap<State, String>();

		if(alreadySeenObjects.containsKey(this)){
			return alreadySeenObjects.get(this);
		}
		alreadySeenObjects.put(this, "OBJ"+alreadySeenObjects.size());

		StringBuilder result = new StringBuilder();
		result.append("\"state\": {");
		List<FieldKey> sortedFields = new ArrayList<FieldKey>(getFields().keySet());
		Collections.sort(sortedFields);

		boolean first = true;
		for(FieldKey fk : sortedFields) {
			if(first){
				first = false;
			}
			else{
				result.append(',');
			}

			State value = fields.get(fk);
			result.append("\n\t\t");
			result.append("\"" + fk.getName() + "\": ");
			result.append(value.toString(alreadySeenObjects));
		}

		result.append("\n      }\n");

		return result.toString();
	}


	/**
	 * Returns a JSON object representing the object
	 * */
	public JSONObject toJSON(){
		JSONArray state = new JSONArray();

		List<FieldKey> sortedFields = new ArrayList<FieldKey>(fields.keySet());
		Collections.sort(sortedFields);

		for(int i = 0; i < sortedFields.size(); i++){
			JSONObject field = new JSONObject();
			field.append(NAME, sortedFields.get(i).getName());
			field.append(VALUE, fields.get(sortedFields.get(i)));
			state.put(field);
		}

		return new JSONObject(state);
	}


	@Override
	public void filterFields(TraceFilter f) {
		//System.out.println(origonalFields);
		fields.clear();
		for (Map.Entry<FieldKey, State> m : originalFields.entrySet()){
			fields.put(m.getKey(), m.getValue());
		}
		ArrayList<Map.Entry<FieldKey, State>> array = new ArrayList<>(getFields().entrySet());

		for(int i = 0; i < array.size(); i++){
			if(!f.isFieldTraced(array.get(i).getKey())){
				System.out.println(array.get(i).getKey());
				fields.remove(array.get(i).getKey());//new
			}
			else {
				array.get(i).getValue().filterFields(f);
			}
		}
	}


	/**
	 * Returns the class name of the object
	 * */
	public String getClassName() {
		return className;
	}


	/**
	 * Returns the fields of the object
	 * */
	public Map<FieldKey, State> getFields() {
		return fields;
	}


	/**
	 * Sets the fields of the object
	 * */
	public void setFields(Map<FieldKey, State> fields) {
		for (Map.Entry<FieldKey, State> m : fields.entrySet()){
			originalFields.put(m.getKey(), m.getValue());
		}
		this.fields = fields;
	}


	@Override
	public int hashCode() {
		return 0; // TODO
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof ObjectState && ((ObjectState)obj).getFields().equals(getFields());
	}
}
