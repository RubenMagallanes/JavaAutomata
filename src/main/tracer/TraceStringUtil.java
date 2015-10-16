package main.tracer;

import java.util.HashSet;
import java.util.Set;

public class TraceStringUtil {

	private Trace trace;

	public TraceStringUtil(Trace trace){
		this.trace = trace;
	}


	/**
	 * Returns The name of all of the methods traced
	 *
	 * @return The set of methods traced
	 * */
	public Set<String> getDisplayMethods(){
		//the set of method names
		Set<String> methods = new HashSet<>();

		//place the names in set to remove duplicates

		for(TraceEntry te : trace.getLines()){
			methods.add(te.getMethod().getName());
		}

		return methods;
	}


	/**
	 * Returns all of fields of the program traced
	 *
	 * @return String containing the traced fields
	 * */
	public String getFields(){
		String fields  = "Fields: \n";

		//the set of method names
		Set<String> fieldSet = new HashSet<>();


		//place the names in set to remove duplicates

			for(TraceEntry te : trace.getLines()){
				if(te.getState() != null)fieldSet.add(te.getState().toString());
		}

		//add each of the unique field names to the string
		for(String s : fieldSet){
				fields += "\t " + s + "\n";
		}
		return fields;
	}


	/**
	 * Returns a String containing the method names of all the methodEntry events.
	 *
	 * @param String representing method entry events
	 * */
	public String methodEntryEvent(){
			//the string to return
				String entryEvents  = "Entry Events: \n";

				//the set of method names
				Set<String> methods = new HashSet<>();


				//place the names in set to remove duplicates

					for(TraceEntry te : trace.getLines()){
						if(!te.isExit())methods.add(te.getLongMethodName());
					}

				//add each of the unique method names to the string
				for(String s : methods){
						entryEvents += "\t " + s + "\n";
				}
				return entryEvents;
	}


	/**
	 * Returns a String containing the method names of all the methodExit events.
	 *
	 * @return String representing method exit events
	 * */
	public String methodExitEvent(){
			//the string to return
			String exitEvents  = "Exit Events: \n";

			//the set of method names
			Set<String> methods = new HashSet<>();


			//place the names in set to remove duplicates
			for(TraceEntry te : trace.getLines()){
				if(te.isExit())methods.add(te.getLongMethodName());
			}

			//add each of the unique method names to the string
			for(String s : methods){
					exitEvents += "\t " + s + "\n";
			}
			return exitEvents;
	}
}
