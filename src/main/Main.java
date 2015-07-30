package main;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import main.ui.*;

public class Main {

	public static void main(String[] args) {
		 File htmlFile = new File("src/web/index.html");
		 try {
			Desktop.getDesktop().browse(htmlFile.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
		JarFileChooser.chooseAndLoad();
	}
}
