package main.ui;

import java.io.File;
import java.net.MalformedURLException;


import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


class Browser extends Region {

	final WebView browser = new WebView();
	final WebEngine webEngine = browser.getEngine();

	String fileUrl = "src/web/HelloWorld.html";

	// String fileUrl = "http://www.google.com";
	public Browser() {
		// apply the styles
		getStyleClass().add("browser");

		//test weather the url we supplied is valid
		File f = new File(fileUrl);
		
		if (!(f.exists() && !f.isDirectory())) {
			System.out.println("Error locating file: " + fileUrl);
		} else {
			//System.out.println("located " + fileUrl );
			//System.out.println("attempting to load page");
			try {
				webEngine.load(f.toURI().toURL().toString());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		// add the web view to the scene
		getChildren().add(browser);

	}
	
	public Object executeCommand(String command){
		if(!webEngine.isJavaScriptEnabled())
			return null;
		
		webEngine.executeScript(command);
		return null;
	}
	public void toggleDisplay(){
		webEngine.executeScript("document.getElementById('body').innerHTML = 'changed'");
	}

	private Node createSpacer() {
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		return spacer;
	}

	@Override
	protected void layoutChildren() {
		double w = getWidth();
		double h = getHeight();
		layoutInArea(browser, 0, 0, w, h, 0, HPos.CENTER, VPos.CENTER);
	}

	@Override
	protected double computePrefWidth(double height) {
		return 750;
	}

	@Override
	protected double computePrefHeight(double width) {
		return 500;
	}
}