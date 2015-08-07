package main;

import main.load.JarData;
import main.ui.*;

public class Main {

	String filename;
	static JarData jardata;

	public static void main(String[] args) {
		GUIFrame.main(args);
	}

	public static void setJarData (JarData jarData){
		jardata = jarData;
	}

	public static JarData getJarData (){
		return jardata;
	}
}
