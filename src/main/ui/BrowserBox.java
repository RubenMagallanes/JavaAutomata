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
import netscape.javascript.JSObject;

/**
 * Object that creates a new window containing a browser that is used to
 * visualize out data. Usage: Create a new BrowserBox(dat), passing in the data
 * (in stringified json format) you want to visualise
 *
 * @author rj
 *
 */
public class BrowserBox {

	private Scene scene; // (Browser) scene for calls to page, Browser is 'Root' of scene
	private Stage stage; // for calls to the java- bits

	private WebEngine engine;
	private boolean loaded = false;

	private String data;

	/**
	 * creates a new Stage that contains a Scene that contains the Browser that
	 * displays the visualization
	 *
	 * after browser creation, i don't think we need to save the references to
	 * the Scene or Stage, just the Browser (for calling script on it) this may
	 * need to be changed in the future.
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
		 * This next piece of code adds listeners to the browser's loadworker,
		 * which checks the page is loaded before allowing any script to execute on it.
		 *
		 * The first function makes javascript's console.log print to java's stdout, this
		 * is just for testing.
		 *
		 * The second function sets a boolean value to true when the page is loaded and then
		 * calls visualiseTrace(data), asking the page to visualise the tarce data which is
		 * what it's for.
		 */
		WebView wv;
		for (Object o : scene.getRoot().getChildrenUnmodifiable()) {
			if (o instanceof WebView) {
				wv = (WebView) o;
				engine = wv.getEngine();
				engine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
					JSObject window = (JSObject) engine.executeScript("window");
					JavaBridge bridge = new JavaBridge();
					window.setMember("java", bridge);
					// makes console.log print to java's stdout
					engine.executeScript(
							"console.log = function(message)\n" + "{\n" + "    java.log(message);\n" + "};");
				});
				if (this.data != null) {
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
	}

	/**
	 * use visualizeTrace() instead
	 *
	 * @return the Browser in this scene
	 */
	@Deprecated
	public Browser Browser() {
		return (Browser) scene.getRoot();
	}

	/**
	 * returns the Stage the
	 * @return
	 */
	public Stage Stage() {
		return stage;
	}

	/**
	 * gives jsonString to browser to visualize
	 * Fails if the Browser isn't loaded yet.
	 *
	 * @param jsonString
	 *            string version of JSON object with data to visualize
	 */
	public void visualizeTrace(String jsonString) {
		Browser br = (Browser) scene.getRoot();

		if (loaded) {
			String arg = "viz.automata.init(JSON.stringify(" + jsonString + "))";
			br.executeScript(arg);// TODO check this works, that this is the
		} // right context to call jscript
	}




	public void visualizeTraceDynamic(String jsonString) {
		data = jsonString;

		if (this.data != null) {
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


//		Browser br = (Browser) scene.getRoot();
//
//		if (loaded) {
//			String arg = "viz.automata.init(JSON.stringify({" + jsonString + "}))";
//			br.executeScript(arg);// TODO check this works, that this is the
//		} // right context to call jscript
	}
}
