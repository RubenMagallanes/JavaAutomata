package main.tracer;

public class TraceManager {
	
	private Trace[] traces; 
	
	
	/**
	 * Constructor for TraceManager 
	 * */
	public TraceManager(Trace[] traces){
		this.traces = traces;
	}
	
	
	/**
	 * Applies a filter to the traces 
	 * */
	public void applyFilter(TraceFilter filter){
		for(Trace t : traces){
			t.applyFilter(filter);
		}
	}
	
	
	/**
	 * Converts the traces to a JSON file
	 * 
	 * @param file path to save the file
	 * */
	public void traceToFile(String filePath, String name){
		for(Trace t : traces){
			t.constructJSONFile(name);
		}
	}
	
	
	/**
	 * Returns the array of traces
	 * */
	public Trace[] getTraces(){
		return this.traces;
	}
	
	
	
	
}
