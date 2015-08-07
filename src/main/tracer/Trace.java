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

	@Override
	public String toString(){
		String toReturn = "[\n";


		for(int i = 0; i < lines.size(); i++){
			toReturn += lines.get(i).toString();
			toReturn += (i < lines.size() - 1) ? ",\n" : "\n";
//			System.out.println("Line");
//			toReturn.concat(" Class :" + line.getClass());
//			toReturn.concat("Method Name :" + line.getLongMethodName());
//			toReturn.concat("State :" + line.state);
//			toReturn.concat("Arguments : " + line.arguments);
//
//
//			System.out.println("Method Name :" +line.getLongMethodName());
//			System.out.println("State :" + line.state);
//			System.out.println("Arguments :" + line.arguments);

		}
//		System.out.println("To return " + toReturn);

		toReturn += "]";
		return toReturn;
	}
}
