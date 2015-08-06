package main.parse;

import java.util.List;

public class AutomataState {

	// fields
	private List<AutomataField> fields;
	private int id;

	public AutomataState(List<AutomataField> fields,int id){
		this.fields = fields;
		this.id = id;
	}

	public List<AutomataField> getFields(){
		return fields;
	}
	
	public int getId(){
		return id;
	}
}
