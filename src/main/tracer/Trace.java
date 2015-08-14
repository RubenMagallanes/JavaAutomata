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

	public void setLines(List<TraceEntry> lines) {
		this.lines = lines;
	}

	public void applyFilter(TraceFilter f) {
		Iterator<TraceEntry> it = lines.iterator();

		for(int i = 0; i < lines.size(); i++){

			MethodKey meth = lines.get(i).method;

			if(!f.isMethodTraced(meth)){
				System.out.println("Removed " + lines.get(i).method);
				lines.remove(i);//new
				i--;
			}
		}

//		while(it.hasNext()) {
//			MethodKey meth = it.next().method;
//			System.out.println("Tracer Apply :"+meth.name);
//			if(!f.isMethodTraced(meth)){
//				lines.remove(lines.indexOf(meth));//new
//			}
//		}

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
