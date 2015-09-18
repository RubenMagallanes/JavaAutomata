package main.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Menu
 * Can tab through differing views
 * Makes the views that it tabs through.
 * @author brewershan
 *
 */
public class MenuPane extends VBox {

	private SelectionPane selection;

	/**
	 * Constructs the menu pane and assigns tabs.
	 */
	public MenuPane(){
		//Make a hozontal box to store the two hrozontal attribues
		
		
		HBox temp = new HBox();
		temp.setPrefWidth(GUIFrame.width / 2);
		GridPane grid = setUpMainPane(this);
		temp.getChildren().add(grid);

		selection = new SelectionPane(this);
		temp.getChildren().add(selection);

		this.setPrefWidth(GUIFrame.width / 2);

		this.getChildren().add(temp);
		this.getChildren().add(new ConsoleLogPane());
		
		//this prints a welcome message to the console.
		//we need to do it here because the MainPane can't yet 'see' the ConsolePane
		for (javafx.scene.Node n: this.getChildren()){
			if (n instanceof ConsoleLogPane){//find console
				ConsoleLogPane cl = (ConsoleLogPane) n;
				cl.appendText("To start, Load in a Jar to trace (Load Jar), or Load a "
						+ "Trace you want to visualise (Load Trace).\n");
			}		
		}
	}

	/**
	 * Sets up the button menu.
	 * @return - a new MainPane that is set up.
	 */
	private GridPane setUpMainPane(MenuPane parent){
		GridPane grid = new MainPane(parent);
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25));
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(50);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(50);
		return grid;
	}

	public SelectionPane getSelectionPane(){
		return selection;
	}
}
