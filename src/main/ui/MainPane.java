package main.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import main.Main;
import main.load.JarData;
import main.load.JarLoader;
import main.tracer.TraceLauncher;
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
				TraceLauncher tracer = new TraceLauncher(Main.getJarData().getFile().getAbsolutePath());
				Trace[] tr = tracer.run();
				System.out.println(tr == null);
				TraceManager manager = new TraceManager(tr);
				Main.setManager(manager);
				manager.traceToFile("", "timmy");
			}
		});
		this.add(btn, 1, 1);
		GridPane.setHgrow(btn, Priority.ALWAYS);

	}

	// convertTraceToJson() TODO

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
				if (fileName.equals("")) {
				} else {
					Main.getManager().traceToFile("data/traces/", fileName);
				}
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
				// grab the traces
				//data/traces/
				
				/*
				 * File f = new File("data/traces/test.json");//TODO Automata
				 * auto = JSONToAutomata.generateAutomata(f);
				 * GeneralFormatToAutomata g = new
				 * GeneralFormatToAutomata(auto); String json =
				 * g.parseAutomata();
				 */
				
				File fi = new File("src/web/test/automata1.json");
				Scanner scan;
				String str = "";
				try {
					scan = new Scanner(fi);
					while (scan.hasNextLine()) {
						str += scan.nextLine();
					}
				} catch (FileNotFoundException e1) {

					e1.printStackTrace();
				}

				BrowserBox bb = new BrowserBox(str);
				browserWindows.put(count++, bb);// add cb to hash map
				// bb.visualizeTrace(str); now handled internally
			}
		});
		this.add(btn, 0, 4);
	}

	
	
}
