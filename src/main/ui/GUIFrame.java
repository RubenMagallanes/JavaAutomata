package main.ui;

import javax.swing.JFrame;

public class GUIFrame extends JFrame{
	public GUIFrame (){
		ButtonPanel buttonPanel = new ButtonPanel();
		this.add(buttonPanel);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
