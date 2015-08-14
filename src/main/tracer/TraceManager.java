package main.tracer;

import java.util.ArrayList;
import java.util.List;

public class TraceManager {

	private Trace[] traces;

	private Trace[] tracesFiltered;


	/**
	 * Constructor for TraceManager
	 * */
	public TraceManager(Trace[] traces){
		this.traces = traces;
		copyToFilter();
	}

	public void copyToFilter(){
		tracesFiltered = new Trace[traces.length];

		for(int i = 0; i < traces.length; i++){
			tracesFiltered[i] = new Trace();
			List<TraceEntry> temp = new ArrayList<TraceEntry>();
			for (TraceEntry t : traces[i].getLines()){
				TraceEntry newT = new TraceEntry();
				newT.setState(t.getState());
				newT.setArguments(t.getArguments());
				newT.setMethod(t.getMethod());
				temp.add(newT);
			}
			tracesFiltered[i].setLines(temp);
		}
	}


	/**
	 * Applies a filter to the traces
	 * */
	public void applyFilter(TraceFilter filter){
		copyToFilter();
		System.out.println("Apply Filter");
		for(Trace t : tracesFiltered){
			t.applyFilter(filter);
		}
	}


	/**
	 * Converts the traces to a JSON file
	 *
	 * @param file path to save the file
	 * */
	public void traceToFile(String filePath, String name){

		for(Trace t : tracesFiltered){
			t.constructJSONFile(name);
		}
	}


	/**
	 * Returns the a copy of the array of traces
	 * */
	public Trace[] getTraces(){
		return this.tracesFiltered;
	}




}
