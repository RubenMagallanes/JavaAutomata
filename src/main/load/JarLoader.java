package main.load;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class JarLoader {

	private static final String CLASS_EXTENSION = ".class";

	public static JarData loadJarFile(File file){
		// initialise variables
		JarData jarData = null;
		List<Class<?>> classes = new ArrayList<Class<?>>();
		JarFile jarFile = null;

		try{
			jarFile = new JarFile(file.getAbsoluteFile());
			URLClassLoader classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()});

			// loop through jar file and find all the class files
			Enumeration<?> entries = jarFile.entries();
			while(entries.hasMoreElements()){
				ZipEntry entry = (ZipEntry)entries.nextElement();


				// check if the current entry is a class and not a directory
				if(entry.getName().endsWith(CLASS_EXTENSION) && !entry.isDirectory()){
					String name = entry.getName().replace('/', '.');

					// remove the .class file extension from the name
					name = name.substring(0, name.length() - CLASS_EXTENSION.length());

					// load class and add to list
					Class<?> c = classLoader.loadClass(name);
					classes.add(c);
				}
			}

			// construct jar data
			jarData = new JarData(file, jarFile.getManifest(), classes);

			// close files
			jarFile.close();
			classLoader.close();

		}catch(IOException | ClassNotFoundException e){
			System.err.println(e.getMessage());
		}

		return jarData;
	}

}
