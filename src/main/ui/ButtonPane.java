package main.ui;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import main.Main;
import main.load.JarData;
import main.load.JarLoader;
import main.parse.Automata;
import main.tracer.TraceLauncher;
import main.tracer.Trace;
import main.tracer.TraceManager;
import main.util.DesktopApi;

/**
 * First menu people see when loading the program.
 *
 * @author brewershan
 *
 */
public class ButtonPane extends GridPane {

	private GUIContainer parent;

	/**
	 * Constructs the menu Pane
	 */
	public ButtonPane(GUIContainer parent) {
		Main.setMainPane(this);
		this.setPrefWidth(GUIFrame.width/2 - GUIFrame.diffrence);
		this.setPrefHeight(GUIFrame.height - GUIFrame.consoleSize);
		this.parent = parent;
		setupLoadJar();
		setupRunTrace();
		setupSaveMenu();
		setUpViewMenu();

		this.prefWidth(Double.MAX_VALUE);
	}

	/**
	 * sends text to be printed to the programs console
	 * @param i to be printed
	 */
	public void printToConsole(String i){
		/*btw the code to change the message first printed to the consolePane when
		the program is started is in MenuPane */
		for (javafx.scene.Node n: parent.getChildrenUnmodifiable()){
			if (n instanceof ConsoleLogPane){
				ConsoleLogPane clp = (ConsoleLogPane) n ;
				clp.clear();
				clp.appendText(i + "\n");
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
					+ "Select what you want to trace with the UI to the right "
					+ "then click 'Run trace' to trace the Jar you have loaded in.\n"
					+ "Optionally, put any args for when running the trace in the "
					+ "text field next to the 'run trace' button");

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
		loadDisplay.setPromptText("jar filename");
		this.add(loadDisplay, 1, 1);

		// Sets up the Jar Load button.
		btn.setMaxWidth(Double.MAX_VALUE);
		btn.setText("Load Jar");
		btn.setOnAction((ActionEvent e)-> {

				File file = chooseJarFile();
				if (file != null) {
					loadDisplay.setText(file.getName());
					JarData jarData = JarLoader.loadJarFile(file);
					Main.setJarData(jarData);
					parent.getSelectionPane().makeNewTree();
					this.buttonClicked("Load Jar");
				} else {
					printToConsole("loading cancelled");
				}
		});
		Tooltip tooltip = new Tooltip();
		tooltip.setText(
			    "Load in a *.jar file to be traced.\n" +
			    "This is the first step in using this tool.\n"
			    + "Use 'run trace' next to generate a trace file"
			    + "from the *.jar."  );
		btn.setTooltip(tooltip);

		this.add(btn, 0, 1);
		GridPane.setHgrow(btn, Priority.ALWAYS);
	}

	/**
	 * set up run trace button
	 */
	private void setupRunTrace(){
		Button btn = new Button();

		TextField argsBox = new TextField();
		argsBox.setPromptText("enter trace cmd line args");
		this.add(argsBox, 1, 2);

		btn.setMaxWidth(Double.MAX_VALUE);
		btn.setText("Run Trace");
		btn.setOnAction((ActionEvent e)-> {
				TraceLauncher tracer = new TraceLauncher(Main.getJarData().getFile().getAbsolutePath());
				tracer.setFilter(Main.getFilter());
				String args = argsBox.getText();
				tracer.setCommandLineArguments(args);
				Trace tr = tracer.run();
				TraceManager manager = new TraceManager(tr);
				Main.setManager(manager);
				this.buttonClicked("Run Trace");
		});
		Tooltip tooltip3 = new Tooltip();
		tooltip3.setText(
			    "Generate a trace from the *.jar you selected.\n" +
			    "You should save the trace afterwards. \n");

		btn.setTooltip(tooltip3);
		this.add(btn, 0, 2);
		GridPane.setHgrow(btn, Priority.ALWAYS);
	}


	/**
	 * Sets up the Save section of the menu
	 */
	private void setupSaveMenu() {
		Button btn = new Button();

		// Text field for user input.
		TextField loadDisplay = new TextField();
		loadDisplay.setPromptText("save trace as.. ");
		this.add(loadDisplay, 1, 3);

		btn.setMaxWidth(Double.MAX_VALUE);
		btn.setText("Save Trace");
		btn.setOnAction((ActionEvent e)-> {
				String fileName = loadDisplay.getText();
				if (fileName.equals("")) {
					this.printToConsole("To save a trace you need to supply a filename in the text box.");
				} else {
					try {
					Main.getManager().traceToFile("data/traces/", fileName);
					this.buttonClicked("Save Trace");
					} catch (NullPointerException npe){
						printToConsole("Uh oh! NullPointerException. \n "
								+ "you probably forgot to click the 'Run Trace' button "
								+ "to generate a trace to save. ");
					}
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
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}


	}
	private File chooseJarFile(){
		FileChooser fc = new FileChooser();
		fc.setTitle("choose a .jar file");
		fc.setInitialDirectory(
	            new File(System.getProperty("user.home"))
	        );
		fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JAR files", "*.jar")
            );
		File file = fc.showOpenDialog(null);
		return file;
	}


}
