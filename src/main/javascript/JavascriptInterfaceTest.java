package main.javascript;

import java.io.File;
import java.io.FileNotFoundException;

import main.util.DesktopApi;

public class JavascriptInterfaceTest { // CURRENTLY FAILS
	private String index = "src/web/index.html";
	private String script = "src/web/index.html";
	
	public JavascriptInterfaceTest(){		
		DesktopApi.browse(new File(index).toURI());
		
		try {
			JavascriptFileWrapper jw = new JavascriptFileWrapper(script);
			
			jw.invokeJavascriptMethod("automata.update", "{states: [{ x: 5,y: 10,z: 3},"
					+ "{x: 3,y: 12,z: 6},{x: 2,y: 15,z: 7}],"
					+ "links: [{source: 0,target: 1},{source: 0,target: 2}],}");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {	
		JavascriptInterfaceTest jt = new JavascriptInterfaceTest();
	}

}
