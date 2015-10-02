package main.tracer;

import java.util.ArrayList;
import java.util.List;

public class TraceManager {

	//the original traces output from the program
	private Trace[] traces;

	//a copy of the traces that can be filtered
	private Trace[] tracesFiltered;


	/**
	 * Constructor for TraceManager
	 * */
	public TraceManager(Trace[] traces){
		this.traces = traces;
		this.copyToFilter();
	}

	/**
	 * Copies the traces to a new array used for filtering
	 * */
	private  void copyToFilter(){
		tracesFiltered = new Trace[traces.length];

		for(int i = 0; i < traces.length; i++){
			tracesFiltered[i] = new Trace();
			List<TraceEntry> temp = new ArrayList<TraceEntry>();
			for (TraceEntry t : traces[i].getLines()){
				TraceEntry newT = new TraceEntry();
				newT.setState(t.getState());
				newT.setArguments(t.getArguments());
				newT.setMethod(t.getMethod());
				newT.setIsExit(t.isExit());
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
		System.out.println(filePath);
		System.out.println(name);
		for(Trace t : tracesFiltered){
			t.constructTraceFile(name);
		}
	}

	/**
	 * returns the json representation of the trace in this manager
	 * @return
	 */
	public String getJson (){
		String ret = "";
		for (int i = 0; i< traces.length; i++){
			ret += traces[i].toString();
		}
		return ret;

	}


	/**
	 * Returns the a copy of the array of traces
	 * */
	public Trace[] getTraces(){
		return this.tracesFiltered;
	}




}
