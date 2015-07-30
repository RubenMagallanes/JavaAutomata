package main.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

/**
 * Menu
 * Can tab through differing views
 * Makes the views that it tabs through.
 * @author brewershan
 *
 */
public class MenuPane extends TabPane{
	/**
	 * Constructs the menu pane and assigns tabs.
	 */
	public MenuPane(){
		GridPane grid = setUpMainPane();
		Tab tab = new Tab();
		tab.setClosable(false);
		tab.setText("Jar Selection");
		tab.setContent(grid);
		this.getTabs().add(tab);

		SelectionPane selection = new SelectionPane();
		tab = new Tab();
		tab.setClosable(false);
		tab.setText("Selection Menu");
		tab.setContent(selection);
		this.getTabs().add(tab);
	}

	/**
	 * Sets up the button menu.
	 * @return - a new MainPane that is set up.
	 */
	private GridPane setUpMainPane(){
		GridPane grid = new MainPane();
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
}
