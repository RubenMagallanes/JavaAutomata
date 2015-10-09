package main.tracer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import main.parse.Automata;
import main.parse.AutomataToVisualisation;
import main.parse.JSONToAutomataException;
import main.parse.TraceToAutomata;
import main.parse.TraceToJSON;
import main.tracer.tree.TraceEntryTree;

public class Trace implements Serializable {

	private static final long serialVersionUID = 1L;

	//A list of states of the program
	private List<TraceEntry> lines;


	/**
	 * Constructs a Trace object initializes the list
	 * of TraceEntrys
	 * */
	public Trace(){
		lines = new ArrayList<TraceEntry>();
	}


	/**
	 * Returns a list of TraceEntry which represent the states
	 * of the traced program
	 *
	 * @return list of TraceEntry
	 * */
	public List<TraceEntry> getLines(){
		return lines;
	}


	/**
	 * Sets the list of TraceEntry objects
	 *
	 * @param the list of TraceEntry
	 * */
	public void setLines(List<TraceEntry> lines) {
		this.lines = lines;
	}


	/**
	 *Removes Items from the Trace if it is not filtered
	 *
	 *@param the filter to use on the trace
	 * */
	public void applyFilter(TraceFilter f) {

		for(int i = 0; i < lines.size(); i++){

			MethodKey meth = lines.get(i).getMethod();

			if(!f.isMethodTraced(meth)){
				lines.remove(i);//new
				i--;
			}
		}

		for(TraceEntry te : lines){
			te.filterFields(f);
		}
	}


	/**
	 * Writes the Trace to a JSONFile
	 *
	 * @param The name of the file to write the trace to
	 * */
	public void constructTraceFile(String filename){
		TraceEntryTree tree = TraceEntryTree.generateTraceEntryTree(lines);
		String tracePath = "data" + File.separatorChar + "traces" + File.separatorChar;
		FileWriter writer;
		try {
			String json = TraceToJSON.generateJSON(tree);
			writer = new FileWriter(tracePath + filename + ".trace");
			writer.write(json);
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}




	@Override
	public String toString(){
		String toReturn = "[\n";


		for(int i = 0; i < lines.size(); i++){
			toReturn += lines.get(i).toString();
			toReturn += (i < lines.size() - 1) ? ",\n" : "\n";
		}

		toReturn += "]";
		return toReturn;
	}
}
