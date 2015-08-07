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

	private static String defaultFileUrl = "src/web/index.html";

	/**
	 * loads browser with index.html as main
	 */
	public Browser(){
		this(defaultFileUrl);
	}

	/**
	 * tries to load supplied url, defaults to index.html which is the initial visualization
	 * 
	 * @param url local url to attempt to load. 
	 */
	public Browser(String url){
		getStyleClass().add("browser");
		File f = new File(url);
		if (!(f.exists() && !f.isDirectory())) {
			System.out.println("Error locating file: " + url);
			System.out.println("reverting to default "+defaultFileUrl);
			f = new File(defaultFileUrl);
		}
		try {
			webEngine.load(f.toURI().toURL().toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		getChildren().add(browser);		
	}
	
	/**
	 * execute javascript in the context of the web page currently in view
	 * 
	 * @param script script to run on the page
	 * @return the return value of the javascript function, converted into a java object-
	 * 	either Integer, Double, String, or Boolean (or null)
	 */
	public Object executeScript(String script){
		if(!webEngine.isJavaScriptEnabled())
			return null;
		
		return webEngine.executeScript(script);
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