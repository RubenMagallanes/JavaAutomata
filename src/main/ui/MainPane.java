package main.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import main.Main;
import main.load.JarData;
import main.load.JarLoader;

import main.parse.Automata;
import main.parse.GeneralFormatToAutomata;
import main.parse.JSONToAutomata;
import main.parse.JSONToAutomataException;

import main.tracer.TraceLauncher;
import main.tracer.Trace;
import main.tracer.TraceManager;
import sun.awt.image.GifImageDecoder;

/**
 * First menu people see when loading the program.
 *
 * @author brewershan
 *
 */
public class MainPane extends GridPane {

	private int count;// number of browser windows currently open.
	//holds reference to browser windows
	Map<Integer, BrowserBox> browserWindows = new HashMap<Integer, BrowserBox>();

	private MenuPane parent;
	private TextField loadDisplay;

	/**
	 * Constructs the menu Pane
	 */
	public MainPane(MenuPane parent) {
		this.setPrefWidth(GUIFrame.width/2 - GUIFrame.diffrence);
		this.setPrefHeight(GUIFrame.height - GUIFrame.consoleSize);
		count = 0;
		this.parent = parent;
		setUpLoadMenu();
		setUpSaveMenu();
		setUpViewMenu();
		setUpDynamic();

		this.prefWidth(Double.MAX_VALUE);


	}

	public void printToConsole(String text){
		/*tp the code to change the message first printed to the consolePane when 
		the program is started is in MenuPane */
		for (javafx.scene.Node n: parent.getChildrenUnmodifiable()){
			if (n instanceof ConsoleLogPane){
				ConsoleLogPane clp = (ConsoleLogPane) n ;
				clp.appendText(text + "\n");
			}
		}
	}
//======================================================================
	/**
	 *
	 * should only be called if the previous button's action was
	 * followed through with
	 */

	private void buttonClicked(String buttonName) {
		//printToConsole(buttonName + " selected");

		if (buttonName.equalsIgnoreCase("Load Jar")) {
			printToConsole("jar loaded. \n"
					+ "Select what you want to trace with the UI to the right \n"
					+ "then click 'Run trace' to trace the Jar you have loaded in");
			
		} else if (buttonName.equalsIgnoreCase("Run Trace")) {
			printToConsole("Trace is now in memory, you can either: \n" 
					+ "\tenter a name in the text field and then save it to the disk (Save Trace), \n"
					+ "\tor load the visualisation with (Load view)");
			
		} else if (buttonName.equalsIgnoreCase("Load Trace")) {
			printToConsole("Trace loaded from disk. \n" 
					+ "to visualise it select \"Load View\")");
			
		} else if (buttonName.equalsIgnoreCase("Save Trace")) {
			printToConsole("Trace saved to disk. \n");//TODO maybe add more text?
			
		} else if (buttonName.equalsIgnoreCase("Load View")) {
			printToConsole("Visualisation loaded. \n");
			
		} else if (buttonName.equalsIgnoreCase("DYNAMIC :^)")) {
		}



	}

//=======================================================================
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
		btn.setOnAction((ActionEvent e)-> {

				File file = JarFileChooser.chooseJarFile();
				if (file != null) {
					loadDisplay.setText(file.getName());
					JarData jarData = JarLoader.loadJarFile(file);
					Main.setJarData(jarData);
					parent.getSelectionPane().makeNewTree();
					this.buttonClicked("Load Jar");
				} else {
					loadDisplay.setText("");
				}
		});

		Tooltip tooltip = new Tooltip();
		tooltip.setText(
			    "Load in a *.jar file to be traced.\n" +
			    "This is the first step in using this tool.\n"
			    + "Use 'run trace' next to generate a trace file"
			    + "from the *.jar."  );
		btn.setTooltip(tooltip);

		this.add(btn, 0, 0);
		GridPane.setHgrow(btn, Priority.ALWAYS);

		// Sets up the Load Trace Program.
		btn = new Button();
		btn.setMaxWidth(Double.MAX_VALUE);
		btn.setText("Load Trace");
		btn.setOnAction((ActionEvent e) ->{
				// TODO: Set up trace loading.
				System.out.println("TODO: load trace");
				this.buttonClicked("Load Trace");
			});
		Tooltip tooltip2 = new Tooltip();
		tooltip2.setText(
			    "Load a Trace for displaying.\n" +
			    "Use this if you have previously loaded \n"
			    + " a jar and already outputted a trace file.\n"  );
		btn.setTooltip(tooltip2);

		this.add(btn, 0, 1);
		GridPane.setHgrow(btn, Priority.ALWAYS);

		btn = new Button();
		btn.setMaxWidth(Double.MAX_VALUE);

		btn.setText("Run Trace");


		btn.setOnAction((ActionEvent e)-> {
				TraceLauncher tracer = new TraceLauncher(Main.getJarData().getFile().getAbsolutePath());
				Trace tr = tracer.run();
				TraceManager manager = new TraceManager(new Trace[]{tr});//TODO Change trace manager
				Main.setManager(manager);
				this.buttonClicked("Run Trace");
		});
		Tooltip tooltip3 = new Tooltip();
		tooltip3.setText(
			    "Generate a trace from the *.jar you selected.\n" +
			    "You should save the trace afterwards. \n");

		btn.setTooltip(tooltip3);
		this.add(btn, 1, 1);
		GridPane.setHgrow(btn, Priority.ALWAYS);

	}


	/**
	 * Sets up the Save section of the menu
	 */
	private void setUpSaveMenu() {
		Button btn = new Button();

		// Text field for user input.
		loadDisplay = new TextField();
		this.add(loadDisplay, 1, 3);

		// Sets up the save button
		btn.setMaxWidth(Double.MAX_VALUE);
		btn.setText("Save Trace");
		btn.setOnAction((ActionEvent e)-> {
				String fileName = loadDisplay.getText();
				if (fileName.equals("")) {
					this.printToConsole("To save a trace you need to supply a filename in the text box.");
				} else {// TODO change so it may load from obj or file
					Main.getManager().traceToFile("data/traces/", fileName);
					this.buttonClicked("Save Trace");
				}
		});
		Tooltip tooltip = new Tooltip();
		tooltip.setText(
			    "Save the trace currently in memory to the disk.\n" +
			    "This allows you to visualise it later without \n"
			    + "having to rerun the trace again."  );
		btn.setTooltip(tooltip);
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
		btn.setOnAction((ActionEvent e) -> {
				Automata auto = null;
				try {
					auto = JSONToAutomata.generateAutomata(new File("data/traces/" + loadDisplay.getText() + ".json"));

					GeneralFormatToAutomata g = new GeneralFormatToAutomata(auto);
					String json = g.parseAutomata();

					//this starts the thread that takes care of the browser window and visualization within
					BrowserBox bb = new BrowserBox(json);
					browserWindows.put(count++, bb);// add bb to hash map if we want to reference it later
					this.buttonClicked("Load View");
				} catch (JSONToAutomataException error) {
					System.out.println("error\n");
					error. printStackTrace();
					System.out.println("error\n");

				}

		});
		Tooltip tooltip = new Tooltip();
		tooltip.setText(
			    "Visualise a trace. \n"
			    + "This requires either a trace to be in memory\n"
			    + "from the 'Run Trace' button, or you to specify\n"
			    + "one saved on the disk to load."  );
		btn.setTooltip(tooltip);
		this.add(btn, 0, 4);



	}

	private void setUpDynamic(){
		Button btn2 = new Button();
		// Sets up the load view button
		btn2.setMaxWidth(Double.MAX_VALUE);
		btn2.setText("DYNAMIC :^)");
		btn2.setOnAction((ActionEvent e) -> {
				//YOUR CODE GOES HERE

			this.buttonClicked("DYNAMIC :^)");
		});
		Tooltip tooltip2 = new Tooltip();
		tooltip2.setText(
			    "ayy lmao"  );
		btn2.setTooltip(tooltip2);
		this.add(btn2, 0, 5);
	}

	/*btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) */
}
