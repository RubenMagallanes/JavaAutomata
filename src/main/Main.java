package main;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import main.load.JarData;
import main.load.JarLoader;
import main.ui.*;
import main.util.DesktopApi;

public class Main {

	String filename;
	static JarData jardata;

	public Main(){
		File htmlFile = new File("src/web/index.html");
		DesktopApi.browse(htmlFile.toURI());
		filename = JarFileChooser.chooseAndLoad();
		jardata = JarLoader.loadJarFile(new File(filename));

	}

	public static void main(String[] args) {
		GUIFrame gui = new GUIFrame();
		Main m = new Main();
	}

	public static void setJarData (JarData jarData){
		jardata = jarData;
	}
}
