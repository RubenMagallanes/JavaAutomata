package main.load;

import java.io.File;
import java.util.List;
import java.util.jar.Manifest;


/**
 * JarData is an Object that contains data from within a jar file.
 * <p>
 * This consists of:
 * <ul>
 * <li>	The jar file
 * <li>	The file manifest
 * <li> A List of classes in the jar file
 *
 * @author David Sheridan
 *
 */
public class JarData {

	// fields
	private File file;
	private Manifest manifest;
	private List<Class<?>> classes;

	/**
	 * Constructs a new instance of a JarFile with the specified parameters.
	 *
	 * @param file
	 * 		-- the jar file
	 * @param manifest
	 * 		-- the file manifest
	 * @param classes
	 * 		-- list of classes in file
	 */
	public JarData(File file, Manifest manifest, List<Class<?>> classes){
		this.file = file;
		this.manifest = manifest;
		this.classes = classes;
	}

	/**
	 * Returns the name of the jar file.
	 *
	 * @return jar file name
	 */
	public String getName(){
		return file.getName();
	}

	/**
	 * Returns an instance of the jar file.
	 *
	 * @return jar file
	 */
	public File getFile(){
		return file;
	}

	/**
	 * Returns the manifest associated with the jar file
	 *
	 * @return manifest
	 */
	public Manifest getManifest(){
		return manifest;
	}

	/**
	 * Returns a list of the classes contained in the jar file.
	 *
	 * @return list of classes
	 */
	public List<Class<?>> getClasses(){
		return classes;
	}

	/**
	 * Returns a String representation of the jar file.
	 */
	public String toString(){
		String data = file.getName()+"{\n";
		for(Class<?> c : classes){
			data += "  " + c.getName()+"\n";
		}
		data += "}";
		return data;
	}
}
