package main.tracer.state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

import main.tracer.FieldKey;
import main.tracer.TraceFilter;

/**
 * A tracer state representing an {@code Object} in Java.
 */
public class ObjectState extends State {

	private static final long serialVersionUID = 1L;
	private static final String NAME = "name";
	private static final String VALUE = "value";

	// fields
	private String className; // the name of the class
	private Map<FieldKey, State> fields = new HashMap<>(); // a map of all the fields the class holds
	private Map<FieldKey, State> originalFields = new HashMap<>();

	/**
	 * Constructs a new instance of {@code ObjectState} with the specified
	 * class name.
	 *
	 * @param className
	 * 		- name of class
	 */
	public ObjectState(String className) {
		this.className = className;
	}

	/**
	 * Returns the class name of the {@code Object} represented by
	 * this {@code ObjectState}.
	 *
	 * @return
	 * 		- class name of object
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * Returns the fields of the {@code Object} represented by this
	 * {@code ObjectState}.
	 *
	 * @return
	 * 		- fields of object
	 */
	public Map<FieldKey, State> getFields() {
		return fields;
	}

	/**
	 * Sets the fields associated with this {@code ObjectState} with the specified
	 * fields.
	 *
	 * @param fields
	 * 		- fields to assign
	 */
	public void setFields(Map<FieldKey, State> fields) {
		for (Map.Entry<FieldKey, State> m : fields.entrySet()){
			originalFields.put(m.getKey(), m.getValue());
		}
		this.fields = fields;
	}

	/**
	 * Filter the visible fields of this {@code ObjectState} based on the
	 * specified {@code TraceFilter}.
	 */
	public void filterFields(TraceFilter f) {
		//System.out.println(origonalFields);
		fields.clear();

		for (Map.Entry<FieldKey, State> m : originalFields.entrySet()){
			fields.put(m.getKey(), m.getValue());
		}

		ArrayList<Map.Entry<FieldKey, State>> array = new ArrayList<>(getFields().entrySet());

		for(int i = 0; i < array.size(); i++){
			if(!f.isFieldTraced(array.get(i).getKey())){
				fields.remove(array.get(i).getKey());//new
			}
			else {
				array.get(i).getValue().filterFields(f);
			}
		}
	}

	/**
	 * Returns a JSON object representing this {@code ObjectState}.
	 */
	public JSONArray toJSON(){
		JSONArray state = new JSONArray();

		List<FieldKey> sortedFields = new ArrayList<FieldKey>(fields.keySet());
		Collections.sort(sortedFields);

		for(int i = 0; i < sortedFields.size(); i++){
			JSONObject field = new JSONObject();
			field.put(NAME, sortedFields.get(i).getName());
			field.put(VALUE, fields.get(sortedFields.get(i)));
			state.put(field);
		}

		return state;
	}

	/**
	 * Generates a hash code for this {@code ObjectState}.
	 */
	public int hashCode() {
		return 0; // TODO
	}

	/**
	 * Returns true if this {@code ObjectState} is equivalent to the
	 * specified {@code Object}, otherwise returns false.
	 */
	public boolean equals(Object obj) {
		return obj instanceof ObjectState && ((ObjectState)obj).getFields().equals(getFields());
	}

	/**
	 * Returns a {@code String} representation of this {@code ObjectState}.
	 */
	public String toString() {
		return toString(new IdentityHashMap<State, String>());
	}

	/**
	 * Returns a {@code String} representation of this {@code ObjectState}
	 * based on whether this {@code ObjectState} has already been seen or not.
	 */
	public String toString(Map<State, String> alreadySeenObjects) {

		// check if this object has already been seen
		if(alreadySeenObjects.containsKey(this)){
			return alreadySeenObjects.get(this);
		}else{
			// otherwise add this object to already seen objects
			alreadySeenObjects.put(this, "OBJ" + alreadySeenObjects.size());
		}

		StringBuilder result = new StringBuilder();
		result.append('{');

		// sort the fields
		List<FieldKey> sortedFields = new ArrayList<FieldKey>(fields.keySet());
		Collections.sort(sortedFields, new Comparator<FieldKey>() {
			@Override
			public int compare(FieldKey o1, FieldKey o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		// add fields to the string builder
		boolean first = true;
		for(FieldKey fk : sortedFields) {
			if(first) first = false;
			else result.append(',');

			State value = fields.get(fk);
			result.append(fk.getName());
			result.append('=');
			result.append(value.toString(alreadySeenObjects));
		}
		result.append('}');

		return result.toString();
	}
}