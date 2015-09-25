package main;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;

import main.load.JarData;
import main.tracer.TraceFilter;
import main.tracer.TraceFilterSelector;
import main.tracer.TraceManager;
import main.ui.*;

public class Main {

	String filename;
	static JarData jardata;
	private static TraceManager manager;
	private static TraceFilter filter;


	public static void main(String[] args){
		GUIFrame.main(args);
	}

	public static void setJarData (JarData jarData){
		jardata = jarData;
	}

	public static JarData getJarData (){
		return jardata;
	}

	public static TraceManager getManager() {
		return manager;
	}

	public static void setManager(TraceManager manager) {
		Main.manager = manager;
	}

	public static void setFilter(TraceFilter filter) {
		Main.filter = filter;
	}

	public  static TraceFilter getFilter() {
		return Main.filter;
	}

}
