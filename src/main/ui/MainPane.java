package main.ui;

import java.awt.Desktop;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;


import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import main.Main;
import main.load.JarData;
import main.load.JarLoader;
import main.parse.Automata;
import main.parse.AutomataToVisualisation;
import main.parse.JSONToAutomata;
import main.parse.JSONToAutomataException;

import main.tracer.TraceLauncher;
import main.tracer.DynamicHandler;

import main.tracer.Trace;
import main.tracer.TraceLauncher;
import main.tracer.TraceManager;

import main.util.DesktopApi;
import sun.awt.image.GifImageDecoder;


import main.tracer.Tracer;



/**
 * First menu people see when loading the program.
 *
 * @author brewershan
 *
 */
public class MainPane extends GridPane {

	 int count;// number of browser windows currently open.
	//holds reference to browser windows
	Map<Integer, BrowserBox> browserWindows = new HashMap<Integer, BrowserBox>();


	private MenuPane parent;
	//private TextField loadDisplay;

	/**
	 * Constructs the menu Pane
	 */
	public MainPane(MenuPane parent) {
		this.setPrefWidth(GUIFrame.width/2 - GUIFrame.diffrence);
		this.setPrefHeight(GUIFrame.height - GUIFrame.consoleSize);
		count = 0;
		this.parent = parent;
		setupLoadJar();
		setupLoadTrace();
		setupRunTrace();
		setupSaveMenu();
		setUpViewMenu();
		//setUpDynamic(); //button denied
		
		this.prefWidth(Double.MAX_VALUE);
	}

	/**
	 * sends text to be printed to the programs console
	 * @param text to be printed
	 */
	public void printToConsole(String text){
		/*tp the code to change the message first printed to the consolePane when
		the program is started is in MenuPane */
		for (javafx.scene.Node n: parent.getChildrenUnmodifiable()){
			if (n instanceof ConsoleLogPane){
				ConsoleLogPane clp = (ConsoleLogPane) n ;
				clp.clear();
				clp.appendText(text + "\n");


			}
		}
	}
	/**
	 * prints out text to the console when a button is clicked.
	 * what is printed changes depending on which button is clicked
	 */
	private void buttonClicked(String buttonName) {
		if (buttonName.equalsIgnoreCase("Load Jar")) {
			printToConsole("jar loaded. \n"
					+ "Select what you want to trace with the UI to the right \n"
					+ "then click 'Run trace' to trace the Jar you have loaded in");

		} else if (buttonName.equalsIgnoreCase("Run Trace")) {
			printToConsole("Trace is now in memory, you can either: \n"
					+ "\tenter a name in the text field and then save it to the disk (Save Trace), \n"
					+ "\tor load the visualisation with (Load view)");

		} else if (buttonName.equalsIgnoreCase("Load Trace")) {

			printToConsole("Trace loaded from disk. \n");

		} else if (buttonName.equalsIgnoreCase("Save Trace")) {
			printToConsole("Trace saved to disk. \n");


		} else if (buttonName.equalsIgnoreCase("Load View")) {
			printToConsole("Visualisation loaded. \n");

		} else if (buttonName.equalsIgnoreCase("DYNAMIC :^)")) {
		}



	}

//=======================================================================
	/**
	 * Sets up the button layout for the Load section of the pane.
	 */
	private void setupLoadJar() {
		Button btn = new Button();

		// Text field to be used
		TextField loadDisplay = new TextField();
		loadDisplay.setEditable(false);
		this.add(loadDisplay, 1, 2);

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

		this.add(btn, 0, 2);
		GridPane.setHgrow(btn, Priority.ALWAYS);
	}

	/**
	 * sets up load trace button
	 */
	private void setupLoadTrace(){
		Button btn = new Button();


		btn.setMaxWidth(Double.MAX_VALUE);
		btn.setText("Load Trace");
		btn.setOnAction((ActionEvent e) ->{

			Automata auto = null;
			File trace = chooseTraceFile ();
			if (trace != null)
			{
				try {
					auto = JSONToAutomata.generateAutomata(trace);
					visualise(auto);
				} catch (Exception e1) {
					printToConsole("error ");
				}
				this.buttonClicked("Load Trace");
			}
			});

		Tooltip tooltip2 = new Tooltip();
		tooltip2.setText(
			    "Load a previously generated Trace \n"
			    + "for displaying.\n" +
			    "Use this if you have previously loaded \n"
			    + " a jar and already outputted a trace file.\n"  );
		btn.setTooltip(tooltip2);

		this.add(btn, 0, 6);
		GridPane.setHgrow(btn, Priority.ALWAYS);

	}
	/**
	 * opens file chooser, with a filter on so you can only see trace files.
	 * @return user chosen Trace file
	 */
	private  File chooseTraceFile(){

		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Trace file", "json");
		chooser.setFileFilter(filter);

		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION){
			return chooser.getSelectedFile();
		}
		return null;
	}
	/**
	 * set up run trace button
	 */
	private void setupRunTrace(){
		Button btn = new Button();
		
		
		/*loadDisplay = new TextField();
		this.add(loadDisplay, 1, 3);

		btn.setMaxWidth(Double.MAX_VALUE);
		btn.setText("Save Trace");
		btn.setOnAction((ActionEvent e)-> {
				String fileName = loadDisplay.getText();
				if (fileName.equals("")) {
					this.printToConsole("To save a trace you need to supply a filename in the text box.");
				} else {
					Main.getManager().traceToFile("data/traces/", fileName);
					this.buttonClicked("Save Trace");
				}
		});*/
		btn.setMaxWidth(Double.MAX_VALUE);
		btn.setText("Run Trace");
		btn.setOnAction((ActionEvent e)-> {
				TraceLauncher tracer = new TraceLauncher(Main.getJarData().getFile().getAbsolutePath());
				//TODO get args from text box
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
		this.add(btn, 0, 3);
		GridPane.setHgrow(btn, Priority.ALWAYS);
	}


	/**
	 * Sets up the Save section of the menu
	 */
	private void setupSaveMenu() {
		Button btn = new Button();

		// Text field for user input.
		TextField loadDisplay = new TextField();
		this.add(loadDisplay, 1, 3);

		btn.setMaxWidth(Double.MAX_VALUE);
		btn.setText("Save Trace");
		btn.setOnAction((ActionEvent e)-> {
				String fileName = loadDisplay.getText();
				if (fileName.equals("")) {
					this.printToConsole("To save a trace you need to supply a filename in the text box.");
				} else {
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
		this.add(btn, 1, 4);
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
					this.visualise(auto);
					this.buttonClicked("Load View");
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

	/**
	 * displays an automata in a browser
	 * @param a automata you want to be visualised
	 */
	private void visualise (Automata a){

		 String htmlFilePath = "http://localhost:8080";

		 try {
			 DesktopApi.browse(new URL(htmlFilePath).toURI());
		} catch (IOException e1) {
			printToConsole("IOException ");
			printToConsole(e1.getMessage());
			//e1.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			//printT
			e.printStackTrace();
		}
//		GeneralFormatToAutomata g = new GeneralFormatToAutomata(a);
//		String json = g.parseAutomata();
//
//		//this starts the thread that takes care of the browser window and visualization within
//		BrowserBox bb = new BrowserBox(json);
//		this.browserWindows.put(this.count++, bb);

	}

	@Deprecated
	private void setUpDynamic(){
		Button btn2 = new Button();
		// Sets up the load view button
		btn2.setMaxWidth(Double.MAX_VALUE);
		btn2.setText("DYNAMIC :^)");
		btn2.setOnAction((ActionEvent e) -> {


			//Tracer setup
			TraceLauncher tracer = new TraceLauncher(Main.getJarData().getFile().getAbsolutePath());
			Trace tr = null;

			BrowserBox bb = new BrowserBox(null);//TODO change
			browserWindows.put(count++, bb);// add bb to hash map if we want to reference it later

			DynamicHandler dh = new DynamicHandler(bb, tr);

			tr=tracer.run();

			TraceManager manager = new TraceManager(new Trace[]{tr});//TODO Change trace manager

			Tracer.setDynamicHandler(dh);

			Main.setManager(manager);

			this.buttonClicked("DYNAMIC :^)");
		});
		Tooltip tooltip2 = new Tooltip();
		tooltip2.setText(
			    "ayy lmao"  );
		btn2.setTooltip(tooltip2);
		this.add(btn2, 0, 7);
	}


}
