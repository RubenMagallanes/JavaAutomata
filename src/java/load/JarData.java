package java.load;

import java.io.File;
import java.util.List;
import java.util.jar.Manifest;

public class JarData {

	// fields
	private File file;
	private Manifest manifest;
	private List<Class<?>> classes;

	public JarData(File file, Manifest manifest, List<Class<?>> classes){
		this.file = file;
		this.manifest = manifest;
		this.classes = classes;
	}

	/**
	 * Returns the name of the jar file.
	 *
	 * @return - jar file name
	 */
	public String getName(){
		return file.getName();
	}

	/**
	 * Returns an instance of the jar file.
	 *
	 * @return - jar file
	 */
	public File getFile(){
		return file;
	}

	/**
	 * Returns the manifest associated with the jar file
	 * @return
	 */
	public Manifest getManifest(){
		return manifest;
	}

	/**
	 * Returns a list of the classes contained in the jar file.
	 * @return
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
