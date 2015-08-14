package main.tracer;

import java.util.HashSet;
import java.util.Set;

public class TraceStringUtil {

	private Trace[] traces;

	public TraceStringUtil(Trace[] traces){
		this.traces = traces;
	}


	/**
	 * Returns all of the fields of the program traced
	 * */
	public String getMethods(){
		//the string to return
		String methodNames  = "Methods: \n";

		//the set of method names
		Set<String> methods = new HashSet<>();


		//place the names in set to remove duplicates
		for(Trace t : traces){
			for(TraceEntry te : t.getLines()){
				methods.add(te.getLongMethodName());
			}
		}

		//add each of the unique method names to the string
		for(String s : methods){
				methodNames += "\t " + s + "\n";
		}
		return methodNames;
	}


	/**
	 * Returns all of the parameters of the program traced
	 * */
	public String getFields(){
		//the string to return
		String fields  = "Fields: \n";

		//the set of method names
		Set<String> fieldSet = new HashSet<>();


		//place the names in set to remove duplicates
		for(Trace t : traces){
			for(TraceEntry te : t.getLines()){
				if(te.state != null)fieldSet.add(te.state.toString());
			}
		}

		//add each of the unique method names to the string
		for(String s : fieldSet){
				fields += "\t " + s + "\n";
		}
		return fields;
	}


	/**
	 * Returns a String containing the method names of all the methodEntry events.
	 * */
	public String methodEntryEvent(){
			//the string to return
				String entryEvents  = "Entry Events: \n";

				//the set of method names
				Set<String> methods = new HashSet<>();


				//place the names in set to remove duplicates
				for(Trace t : traces){
					for(TraceEntry te : t.getLines()){
						if(!te.isReturn)methods.add(te.getLongMethodName());
					}
				}

				//add each of the unique method names to the string
				for(String s : methods){
						entryEvents += "\t " + s + "\n";
				}
				return entryEvents;
	}


	/**
	 * Returns a String containing the method names of all the methodExit events.
	 * */
	public String methodExitEvent(){
		//the string to return
			String exitEvents  = "Exit Events: \n";

			//the set of method names
			Set<String> methods = new HashSet<>();


			//place the names in set to remove duplicates
			for(Trace t : traces){
				for(TraceEntry te : t.getLines()){
					if(te.isReturn)methods.add(te.getLongMethodName());
				}
			}

			//add each of the unique method names to the string
			for(String s : methods){
					exitEvents += "\t " + s + "\n";
			}
			return exitEvents;
}




}
