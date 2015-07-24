package main.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import main.load.JarData;
import main.load.JarLoader;

import org.junit.Test;

/**
 * Tests to ensure that the JarLoader class is working as intended.
 *
 * @author David Sheridan
 *
 */
public class JarLoaderTests {

	private final String DATA_PATH = "data" + File.separatorChar + "tests" + File.separatorChar;

	/**
	 * Tests that the JarLoader class can load a test jar called "TestJar1.jar"
	 * <p>
	 * TestJar1.jar contains the classes:
	 * <ul>
	 * <li>	A.class
	 * <li>	B.class
	 * </ul>
	 */
	@Test public void testCorrectJarLoad_1(){
		String name = "TestJar1.jar";
		String[] classNames = {"A", "B"};
		JarData data = JarLoader.loadJarFile(new File(DATA_PATH + name));

		// test that names match
		if(!name.equals(data.getName())){
			fail(name + " is not equivalent to " + data.getName());
		}

		List<Class<?>> classes = data.getClasses();

		// check that the number of classes is correct
		if(classes.size() != 2){
			fail("There should be two classes in " + name);
		}

		// check that the class names match
		for(int i = 0; i < classNames.length; i++){
			boolean isPresent = false;
			for(Class<?> c : classes){
				if(classNames[i].equals(c.getName())){
					isPresent = true;
					break;
				}
			}
			if(!isPresent){
				fail("No class was found in file with the name \"" + classNames[i]+"\"");
			}
		}
	}

	/**
	 * Tests that the JarLoader can load a test jar called "TestJar2.jar"
	 * <p>
	 * TestJar2.jar contains no class files.
	 */
	@Test public void testCorrectJarLoad_2(){
		String name = "TestJar2.jar";
		JarData data = JarLoader.loadJarFile(new File(DATA_PATH + name));

		// test that names match
		if(!name.equals(data.getName())){
			fail(name + " is not equivalent to " + data.getName());
		}

		List<Class<?>> classes = data.getClasses();

		// check that the number of classes is correct
		if(classes.size() != 0){
			fail("There should be no classes in " + name);
		}
	}

	/**
	 * Test that the JarLoader cannot load a jar that does not exist.
	 */
	@Test public void testIncorrectJarLoad(){
		try{
			JarData data = JarLoader.loadJarFile(null);
			fail("null value passed as argument, should have thrown an IllegalArgumentException.");
			data.getFile(); // should not be reached
		}catch(IllegalArgumentException e){
			// test should reach here
		}
	}

}
