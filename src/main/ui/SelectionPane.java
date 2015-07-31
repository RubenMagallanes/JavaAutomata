package main.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class SelectionPane extends TreeView{

	public SelectionPane (){
		TreeItem<String> rootItem = new TreeItem<String> ("Temp");
		TreeItem<String> test1 = new TreeItem<String>("Dam");
		rootItem.getChildren().add(test1);
		this.setRoot(rootItem);
	}
}
