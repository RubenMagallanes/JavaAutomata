package main.ui;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class JarFileChooser {

	/**
	 * Chooses a file and sets it to preper for loading.
	 * @return - true if the file chosen is valid false otherwise.
	 */
	public static String chooseAndLoad(){
		File file = chooseJarFile();
		if (file == null){
			return null;
		}
		else{
			//ToDo
			return file.getName();
		}
	}

	/**
	 * Open the file chooser and allows the user to select a jar file.
	 * @return File or null if the file is bad or none was picked.
	 */
	private static File chooseJarFile(){

		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("jar");

		chooser.setFileFilter(filter);

		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION){
			return chooser.getSelectedFile();
		}
		return null;
	}
}
