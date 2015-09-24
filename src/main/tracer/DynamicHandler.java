package main.tracer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import main.parse.GeneralFormatToAutomata;
import main.parse.JSONToAutomata;
import main.parse.TraceToJSON;
import main.tracer.tree.TraceEntryTree;
import main.ui.BrowserBox;

public class DynamicHandler {

	private static BrowserBox bb;

	private String json;

	private static Trace   trace;

	public DynamicHandler(BrowserBox bb, Trace trace){
		DynamicHandler.bb = bb;
		DynamicHandler.trace = trace;
	}

	public void updateBrowserBox(){
		bb = new BrowserBox(json);
	}

	public static void eventOccoured(TraceEntry te){
		System.out.println("Events are happening");
		List<TraceEntry> tes = new ArrayList<TraceEntry>();
		tes.add(te);

		TraceEntryTree tree = TraceEntryTree.generateTraceEntryTree(tes);
		String json = TraceToJSON.generateJSON(tree);
		bb.visualizeTraceDynamic(json);
	}
}




