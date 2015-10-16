package main.ui;

import javafx.scene.control.TextArea;


/**
 * Created by Shane on 21/08/15.
 */
public class ConsoleLogPane extends TextArea{
    public ConsoleLogPane (){
        this.setPrefHeight(GUIFrame.height/4);
        this.setPrefWidth(GUIFrame.width);
        this.setEditable(false);;
    }
}
