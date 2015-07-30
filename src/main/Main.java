package main;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import main.ui.*;
import main.util.DesktopApi;

public class Main {

	public static void main(String[] args) {
		File htmlFile = new File("src/web/index.html");

		 DesktopApi.browse(htmlFile.toURI());

		JarFileChooser.chooseAndLoad();
	}
}
