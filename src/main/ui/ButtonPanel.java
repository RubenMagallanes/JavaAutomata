package main.ui;


import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.Main;
import main.load.JarData;
import main.load.JarLoader;
import main.util.DesktopApi;

public class ButtonPanel extends Scene{


	public ButtonPanel (GridPane grid, double height , double width){
		super((Parent) grid, height, width);
		Button btn = new Button();
		btn.setMaxWidth(Double.MAX_VALUE);
		btn.setText("LoadJar");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				File file = JarFileChooser.chooseJarFile();
				if (file != null){
					JarData jarData = JarLoader.loadJarFile(file);
					Main.setJarData(jarData);
				}

			}

		});
		grid.add(btn, 0, 0);
		GridPane.setHgrow(btn, Priority.ALWAYS);
		btn = new Button();
		btn.setMaxWidth(Double.MAX_VALUE);
		btn.setText("Open View");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				DesktopApi.browse(new File("src/web/index.html").toURI());
			}
		});
		grid.add(btn, 0, 1);
		GridPane.setHgrow(btn, Priority.ALWAYS);
		grid.prefWidth(Double.MAX_VALUE);
	}
}
