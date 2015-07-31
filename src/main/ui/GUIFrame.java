package main.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GUIFrame extends Application{

	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Automita");

		MenuPane menu = new MenuPane();

		primaryStage.setScene(new Scene(menu, 400, 400));
		primaryStage.show();

	}

	public static void main(String[] args){
		launch(args);
	}
}
