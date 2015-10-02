package main.tracer;

import java.util.ArrayList;
import java.util.List;

public class TraceManager {

	//the original trace output from the program
	private Trace trace;

	//a copy of the traces that can be filtered
	private Trace traceFiltered;


	/**
	 * Constructor for TraceManager
	 * */
	public TraceManager(Trace trace){
		this.trace = trace;
		this.copyToFilter();
	}

	/**
	 * Copies the traces to a new array used for filtering
	 * */
	private  void copyToFilter(){

			Trace newTrace = new Trace();
			List<TraceEntry> temp = new ArrayList<TraceEntry>();
			for (TraceEntry t : trace.getLines()){
				TraceEntry newT = new TraceEntry();
				newT.setState(t.getState());
				newT.setArguments(t.getArguments());
				newT.setMethod(t.getMethod());
				newT.setIsExit(t.isExit());
				temp.add(newT);
			}
			newTrace.setLines(temp);
			this.traceFiltered = newTrace;
		}



	/**
	 * Applies a filter to the traces
	 * */
	public void applyFilter(TraceFilter filter){
		copyToFilter();
		traceFiltered.applyFilter(filter);
	}


	/**
	 * Converts the traces to a JSON file
	 *
	 * @param file path to save the file
	 * */
	public void traceToFile(String filePath, String name){
			traceFiltered.constructTraceFile(name);
	}

	/**
	 * returns the json representation of the trace in this manager
	 * @return
	 */
	public String getJson (){
		return trace.toString();
	}


	/**
	 * Returns the a copy of the array of traces
	 * */
	public Trace getTraces(){
		return traceFiltered;
	}




}
