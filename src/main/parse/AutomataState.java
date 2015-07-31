package main.parse;

import java.lang.reflect.Field;
import java.util.List;

public class AutomataState {

	// fields
	private List<Field> fields;

	public AutomataState(List<Field> fields){
		this.fields = fields;
	}

	public List<Field> getFields(){
		return fields;
	}
}
