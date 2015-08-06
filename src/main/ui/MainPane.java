package main.ui;

import java.io.File;

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
import main.util.DesktopApi;

/**
 * First menu people see when loading the program.
 * @author brewershan
 *
 */
public class MainPane extends GridPane {

	/**
	 * Constructs the menu Pane
	 */
	public MainPane() {
		setUpLoadMenu();
		setUpSaveMenu();
		setUpViewMenu();
		//added for browser testing
		setUpBrowserButton();
		this.prefWidth(Double.MAX_VALUE);
	}
	private void setUpBrowserButton(){
		Button btn = new Button();
		btn.setMaxWidth(Double.MAX_VALUE);
		btn.setText("Open Browser");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				new CreateBrowser();
			}
		});
		this.add(btn, 0, 6);
		GridPane.setHgrow(btn, Priority.ALWAYS);
	
	}
	
	private class CreateBrowser{			
		private Scene scene;	
		/**
		 * creates a new Stage that contains a Scene that contains the Browser
		 * that displays the visualization 
		 */
		public CreateBrowser() {			
			Stage stage = new Stage();
			stage.setTitle("Visualization");
			scene = new Scene(new Browser(),750,500, Color.web("#666970"));
			stage.setScene(scene);
			scene.getStylesheets().add("webviewsample/BrowserToolbar.css");        
			stage.show();
		}
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
	 * Sets up the view buttons
	 */
	private void setUpViewMenu() {
		Button btn = new Button();
		// Sets up the load view button
		btn.setMaxWidth(Double.MAX_VALUE);
		btn.setText("Load View");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				DesktopApi.browse(new File("src/web/index.html").toURI());
			}

		});
		this.add(btn, 0, 4);
	}
}
