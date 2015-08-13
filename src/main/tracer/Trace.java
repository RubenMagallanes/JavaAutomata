package main.tracer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Trace implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<TraceEntry> lines;

	public Trace(){
		lines = new ArrayList<TraceEntry>();
	}

	public List<TraceEntry> getLines(){
		return lines;
	}

	public void applyFilter(TraceFilter f) {
		Iterator<TraceEntry> it = lines.iterator();

		while(it.hasNext()) {
			if(!f.isMethodTraced(it.next().method)){
				it.remove();
			}
		}

		for(TraceEntry te : lines){
			te.filterFields(f);
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

	public void constructJSONFile(String filename){
		String path = "data" + File.separatorChar + "traces" + File.separatorChar;
		FileWriter writer;
		try {
			writer = new FileWriter(path + filename + ".json");
			writer.write(toString());
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
