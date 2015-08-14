package main.state;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ProgramState {	
	
	public class State{
		String filename;
		List<String> fields;
		String json;

		public State(String filename, List<String> fields, String json) {
			this.filename = filename;
			this.fields = fields;
			this.json = json;
		}		
	}

	public void Save(String filename, List<String> fields, String json){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();	
		gson.toJson(new State(filename,fields,json));
	}
	
	public void Load(){
		
	}	
}
