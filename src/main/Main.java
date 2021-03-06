package main;

import main.load.JarData;
import main.tracer.TraceFilter;
import main.tracer.TraceManager;
import main.ui.GUIFrame;
import main.ui.ButtonPane;

public class Main {

	String filename;
	static JarData jardata;
	private static TraceManager manager;
	private static TraceFilter filter;
	private static ButtonPane ref;

	public static void main(String[] args){
		/* TODO perhaps use singleton pattern or a lockfile to guarantee
		 * only one instance of this program running
		 */
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
	
	public static void setMainPane(ButtonPane mp){
		Main.ref = mp;
	}
	
	public static void printToWindow(String text){
		ref.printToConsole(text);
	}

}
