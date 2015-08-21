package main.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GUIFrame extends Application{


	private static Stage primaryStage;
	final static int width = 1000;
	final static int diffrence = 100;
	final static int consoleSize = 100;
	final static int height = 500;

	@Override
	public void start(Stage primaryStage) throws Exception {
		GUIFrame.primaryStage = primaryStage;
		primaryStage.setTitle("Automata");

		MenuPane menu = new MenuPane();

		primaryStage.setScene(new Scene(menu, GUIFrame.width, GUIFrame.height));
		primaryStage.show();
	}

	public static void main(String[] args){
		launch(args);
	}

}
