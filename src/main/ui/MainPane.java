package main.ui;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.Main;
import main.load.JarData;
import main.load.JarLoader;
import main.tracer.TestSimpleProgram;
import main.tracer.Trace;
import main.tracer.TraceManager;

/**
 * First menu people see when loading the program.
 *
 * @author brewershan
 *
 */
public class MainPane extends GridPane {

	// private Browser b; // reference to the browser for javascript calls
	private int count;// number of browser windows currently open.
	Map<Integer, BrowserBox> browserWindows = new HashMap<Integer, BrowserBox>();// holds
																					// references
																					// to
																					// diff
																					// windows

	private MenuPane parent;

	/**
	 * Constructs the menu Pane
	 */
	public MainPane(MenuPane parent) {
		count = 0;
		this.parent = parent;
		setUpLoadMenu();
		setUpSaveMenu();
		setUpViewMenu();

		this.prefWidth(Double.MAX_VALUE);
	}

	/**
	 * Sets up the button layout for the Load section of the pane.
	 */
	private void setUpLoadMenu() {
		Button btn = new Button();

		// Text field to be used
		TextField loadDisplay = new TextField();
		loadDisplay.setEditable(false);
		this.add(loadDisplay, 1, 0);

		// Sets up the Jar Load button.
		btn.setMaxWidth(Double.MAX_VALUE);
		btn.setText("Load Jar");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				File file = JarFileChooser.chooseJarFile();
				if (file != null) {
					loadDisplay.setText(file.getName());
					JarData jarData = JarLoader.loadJarFile(file);
					Main.setJarData(jarData);
					// System.out.println(parent);
					// System.out.println(parent.getSelectionPane());
					parent.getSelectionPane().makeNewTree();
				} else {
					loadDisplay.setText("");
				}

			}

		});
		this.add(btn, 0, 0);
		GridPane.setHgrow(btn, Priority.ALWAYS);

		// Sets up the Load Trace Program.
		btn = new Button();
		btn.setMaxWidth(Double.MAX_VALUE);
		btn.setText("Load Trace");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				// TODO: Set up trace loading.
				System.out.println("TODO: load trace");
			}
		});
		this.add(btn, 0, 1);
		GridPane.setHgrow(btn, Priority.ALWAYS);

		btn = new Button();
		btn.setMaxWidth(Double.MAX_VALUE);
		btn.setText("Run Trace");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				TestSimpleProgram tracer = new TestSimpleProgram(Main
						.getJarData().getFile().getAbsolutePath());
				Trace[] tr = tracer.run();
				TraceManager manager = new TraceManager(tr);
				Main.setManager(manager);
				manager.traceToFile("", "timmy");
			}
		});
		this.add(btn, 1, 1);
		GridPane.setHgrow(btn, Priority.ALWAYS);

	}

	/**
	 * Sets up the Save section of the menu
	 */
	private void setUpSaveMenu() {
		Button btn = new Button();

		// Text field for user input.
		TextField loadDisplay = new TextField();
		this.add(loadDisplay, 1, 3);

		// Sets up the save button
		btn.setMaxWidth(Double.MAX_VALUE);
		btn.setText("Save Trace");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				String fileName = loadDisplay.getText();
				// TODO: Set up Trace saving.
				System.out.println(fileName + " TODO: Trace Saveing");
			}

		});
		this.add(btn, 0, 3);
	}

	/**
	 * Sets up the view button, opens browser window to view trace
	 */
	private void setUpViewMenu() {
		Button btn = new Button();
		// Sets up the load view button
		btn.setMaxWidth(Double.MAX_VALUE);
		btn.setText("Load View");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// create new browser window
				BrowserBox cb = new BrowserBox();
				browserWindows.put(count++, cb);// add cb to hash map
			}
		});
		this.add(btn, 0, 4);
	}

	/**
	 * Object that creates a new window containing a browser that is used to
	 * visualize out data.
	 *
	 * Use: just create a new BrowserBox() and a new window will pop up in
	 * addition to the current javafx scene. use Browser() to get the browser
	 * associated so you can make javascript calls on it
	 *
	 * @author rj
	 *
	 */
	private class BrowserBox {
		private Scene scene; // (Browser) scene for calls to page//TODO make
								// geters and setters
		private Stage stage; // for calls to the java- bits

		/**
		 * creates a new Stage that contains a Scene that contains the Browser
		 * that displays the visualization
		 *
		 * after browser creation, i don't think we need to save the references
		 * to the Scene or Stage, just the Browser (for calling script on it)
		 * this may need to be changed in the future.
		 */
		public BrowserBox() {
			scene = new Scene(new Browser(), 700, 700, Color.web("#666970"));

			stage = new Stage();
			stage.setTitle("Visualization");
			stage.setScene(scene);
			stage.show();
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
		 * returns reference to Browser so you can mkae javascript calls to it
		 *
		 * @return the Browser in this scene
		 */
		public Browser Browser() {
			return (Browser) scene.getRoot();
		}

		public Stage Stage() {
			return stage;
		}
	}
}
