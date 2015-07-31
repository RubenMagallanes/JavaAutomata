package main.parse;

public class AutomataField {

	// fields
	private String type;
	private String name;
	private String value;

	public AutomataField(String type, String name, String value){
		this.type = type;
		this.name = name;
		this.value = value;
	}

	public String getType(){
		return type;
	}

	public String getName(){
		return name;
	}

	public String getValue(){
		return value;
	}

}
