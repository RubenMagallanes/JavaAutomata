package main.parse;

import java.util.List;

public class AutomataState {

	// fields
	private List<AutomataField> fields;
	

	public AutomataState(List<AutomataField> fields){
		this.fields = fields;
	}

	public List<AutomataField> getFields(){
		return fields;
	}
}
