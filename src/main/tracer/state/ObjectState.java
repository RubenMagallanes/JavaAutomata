package main.tracer.state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.tracer.FieldKey;
import main.tracer.MethodKey;
import main.tracer.TraceFilter;

public class ObjectState extends State {

	private static final long serialVersionUID = 1L;

	// fields
	@SuppressWarnings("unused")
	private String className;
	private Map<FieldKey, State> fields = new HashMap<>();
	private Map<FieldKey, State> origonalFields = new HashMap<>();

	public ObjectState(String className) {
		this.className = className;
	}

	public String toString() {
		return toString(new IdentityHashMap<State, String>());
	}

	public String toString(Map<State, String> alreadySeenObjects) {
		if(alreadySeenObjects.containsKey(this)){
			return alreadySeenObjects.get(this);
		}
		alreadySeenObjects.put(this, "OBJ"+alreadySeenObjects.size());

		StringBuilder result = new StringBuilder();
		result.append("\"state\": {");
		List<FieldKey> sortedFields = new ArrayList<FieldKey>(getFields().keySet());
		Collections.sort(sortedFields, new Comparator<FieldKey>() {
			@Override
			public int compare(FieldKey o1, FieldKey o2) {
				return o1.name.compareTo(o2.name);
			}
		});

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
			result.append("\"" + fk.name + "\": ");
			result.append(value.toString(alreadySeenObjects));
		}

		result.append("\n      }\n");

		return result.toString();
	}

	@Override
	public void filterFields(TraceFilter f) {
		//System.out.println(origonalFields);
		fields.clear();
		for (Map.Entry<FieldKey, State> m : origonalFields.entrySet()){
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

	@Override
	public int hashCode() {
		return 0; // TODO
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof ObjectState && ((ObjectState)obj).getFields().equals(getFields());
	}

	public String getClassName() {
		return className;
	}

	public Map<FieldKey, State> getFields() {
		return fields;
	}

	public void setFields(Map<FieldKey, State> fields) {
		for (Map.Entry<FieldKey, State> m : fields.entrySet()){
			origonalFields.put(m.getKey(), m.getValue());
		}
		this.fields = fields;
	}
}
