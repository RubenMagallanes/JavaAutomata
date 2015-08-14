package main.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSException;
import netscape.javascript.JSObject;

/**
 * Object that creates a new window containing a browser that is used to
 * visualize out data. Usage: Create a new BrowserBox(dat), passing in the
 * data (in stringified json format) you want to visualise
 * 
 * @author rj
 *
 */
public class BrowserBox {

	private Scene scene; // (Browser) scene for calls to page
	private Stage stage; // for calls to the java- bits

	private WebEngine engine;
	private boolean loaded = false;

	private String data;

	/**
	 * creates a new Stage that contains a Scene that contains the Browser
	 * that displays the visualization
	 *
	 * after browser creation, i don't think we need to save the references
	 * to the Scene or Stage, just the Browser (for calling script on it)
	 * this may need to be changed in the future.
	 * 
	 * @param dat
	 *            data that is to be loaded in to the visualization once the
	 *            page has loaded
	 */

	public BrowserBox(String dat) {
		this.data = dat;
		scene = new Scene(new Browser(), 700, 700, Color.web("#666970"));

		stage = new Stage();
		stage.setTitle("Visualization");
		stage.setScene(scene);
		stage.show();

		/*
		 * this next piece of code adds a listener to the browser's
		 * loadworker, which changes 'loaded' variable to true when the page
		 * is loaded. after it's loaded we're allowed to call javascript on
		 * it.
		 */
		WebView wv;
		for (Object o : scene.getRoot().getChildrenUnmodifiable()) {
			if (o instanceof WebView) {
				wv = (WebView) o;
				engine = wv.getEngine(); 
				engine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) ->
				{
				    JSObject window = (JSObject) engine.executeScript("window");
				    JavaBridge bridge = new JavaBridge();
				    window.setMember("java", bridge);
				    engine.executeScript("console.log = function(message)\n" +
				        "{\n" +
				        "    java.log(message);\n" +
				        "};");
				});
				engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
					@SuppressWarnings("rawtypes")
					public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
						if (newState == State.SUCCEEDED) {
							loaded = true;
							
							visualizeTrace(data);
						}
					}
				});

			}
		}
		
		
		
	}

	/**
	 * returns a reference to the newly constructed browser
	 *
	 * @return the Browser that just got created.
	 */
	/*
	 * public Browser getReference(){ return (Browser) scene.getRoot(); }
	 */

	/**
	 * use visualizeTrace() instead
	 *
	 * @return the Browser in this scene
	 */
	@Deprecated
	public Browser Browser() {
		return (Browser) scene.getRoot();
	}

	public Stage Stage() {
		return stage;
	}

	/**
	 * gives jsonString to browser to visualise
	 * 
	 * @param jsonString
	 *            string version of Jon object with data to visualise
	 */
	public void visualizeTrace(String jsonString) {
		Browser br = (Browser) scene.getRoot();
		/*try{
	
		JSObject jsobj = (JSObject)engine.executeScript("window");
		jsobj.call("viz.automata.init","{"+ jsonString + "}");
		jsobj.call ("console.log", "hey rofl");
		}catch(JSException je){
			je.printStackTrace();
		}*/
		String arg = "viz.automata.init(JSON.stringify({" + jsonString + "}))"; 
																
		//System.out.println(arg);
		br.executeScript(arg);// TODO check this works, that this is the
		// right context to call jscript
	}
}
