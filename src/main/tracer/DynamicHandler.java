package main.tracer;

import java.io.File;

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

	public static void eventOccoured(){
		System.out.println("Events are happening");
		TraceEntryTree tree = TraceEntryTree.generateTraceEntryTree(trace.getLines());
		String json = TraceToJSON.generateJSON(tree);
		bb.visualizeTrace(json);
	}
}




