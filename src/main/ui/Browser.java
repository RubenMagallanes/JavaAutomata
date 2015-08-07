package main.ui;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import main.Main;

class Browser extends Region {
	 
    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();
    
    String fileUrl = "src/web/HelloWorld.html";
     
    public Browser() {
        //apply the styles
        getStyleClass().add("browser");

        File f = new File(fileUrl);
        if(!(f.exists() && !f.isDirectory())) {
        	System.out.println("Error locating file: " + fileUrl + "\n"); 
        }
        else {
        	System.out.println("located " +fileUrl + "\n");

        	try {
				webEngine.load(f.toURI().toURL().toString());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        
        
        //add the web view to the scene
        if(webEngine.getDocument() == null){
        	System.out.println("document failed to load");
        } else {
        	getChildren().add(browser);
        }
        
 
    }
    private Node createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }
 
    @Override protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
    }
 
    @Override protected double computePrefWidth(double height) {
        return 750;
    }
 
    @Override protected double computePrefHeight(double width) {
        return 500;
    }
}