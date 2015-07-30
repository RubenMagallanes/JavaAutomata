package main.tracer;

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
}
