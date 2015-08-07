package main.ui;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import main.Main;
import main.load.JarData;
import javafx.scene.Node;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SelectionPane extends TreeView {

	TreeItem<String> rootItem;
	MenuPane parent;

	public SelectionPane(MenuPane parent) {
		this.parent = parent;
	}

	public void clearTreeView() {
		if (rootItem != null) {
			this.setRoot(null);
		}
		rootItem = null;
	}

	/**
	 * Clears the old tree graph and makes a new one.
	 */
	public void makeNewTree() {
		clearTreeView();
		System.out.println("IS WORKING");
		JarData data = Main.getJarData();

		rootItem = new CheckBoxTreeItem<String>(data.getName());
		rootItem.setExpanded(true);

		// Getting the list of classes for the program
		List<Class<?>> classList = data.getClasses();
		// Makeing a factory for the checkbox's
		this.setCellFactory(CheckBoxTreeCell.<String> forTreeView());

		//Goes through all the classes and added them to the list.
		for (Class<?> tempClass : classList) {
			Node classIcon = new ImageView(new Image(getClass()
					.getResourceAsStream("icons/class_obj.gif")));
			final CheckBoxTreeItem<String> checkBoxTreeItem = new CheckBoxTreeItem<String>(
					tempClass.getName(), classIcon);
			try {
				//Trys to add methods and fields as children.
				addMethods(tempClass, checkBoxTreeItem);
				addFields(tempClass, checkBoxTreeItem);

			} catch (NoClassDefFoundError exc) {
			}
			//Adds them to the parent.
			checkBoxTreeItem.setExpanded(false);
			rootItem.getChildren().add(checkBoxTreeItem);
		}
		//Sets the root of the tree
		this.setRoot(rootItem);
	}

	/**
	 * Loads in the Methods into the tree and Attaches the apropret icons to
	 * them.
	 *
	 * @param tempClass
	 *            - class the methods are being loaded in from.
	 * @param checkBoxTreeItem
	 *            - the parent node of the tree.
	 * @throws NoClassDefFoundError
	 *             - error thrown when there is not class defintion found.
	 */
	private void addMethods(Class<?> tempClass,
			CheckBoxTreeItem<String> checkBoxTreeItem)
			throws NoClassDefFoundError {
		for (Method method : tempClass.getDeclaredMethods()) {
			final CheckBoxTreeItem<String> checkBoxMethod = new CheckBoxTreeItem<String>(
					method.getName());
			if (method.toString().contains("public")) {
				Node methodIcon = new ImageView(new Image(getClass()
						.getResourceAsStream("icons/methpub_obj.gif")));
				checkBoxMethod.setGraphic(methodIcon);
			} else if (method.toString().contains("protected")) {
				Node methodIcon = new ImageView(new Image(getClass()
						.getResourceAsStream("icons/methpro_obj.gif")));
				checkBoxMethod.setGraphic(methodIcon);
			} else if (method.toString().contains("private")) {
				Node methodIcon = new ImageView(new Image(getClass()
						.getResourceAsStream("icons/methpri_obj.gif")));
				checkBoxMethod.setGraphic(methodIcon);
			} else {
				Node methodIcon = new ImageView(new Image(getClass()
						.getResourceAsStream("icons/methdef_obj.gif")));
				checkBoxMethod.setGraphic(methodIcon);
			}
			checkBoxTreeItem.getChildren().add(checkBoxMethod);
		}
	}

	/**
	 * Loads in the fields into the tree and Attaches the apropret icons to
	 * them.
	 *
	 * @param tempClass
	 *            - class the fields are being loaded in from.
	 * @param checkBoxTreeItem
	 *            - the parent node of the tree.
	 * @throws NoClassDefFoundError
	 *             - error thrown when there is not class defintion found.
	 */
	private void addFields(Class<?> tempClass,
			CheckBoxTreeItem<String> checkBoxTreeItem)
			throws NoClassDefFoundError {
		for (Field field : tempClass.getDeclaredFields()) {
			final CheckBoxTreeItem<String> checkBoxField = new CheckBoxTreeItem<String>(
					field.getName());
			if (field.isEnumConstant()) {
				Node methodIcon = new ImageView(new Image(getClass()
						.getResourceAsStream("icons/enum_obj.gif")));
				checkBoxField.setGraphic(methodIcon);
			} else if (field.toString().contains("public")) {
				Node methodIcon = new ImageView(new Image(getClass()
						.getResourceAsStream("icons/field_public_obj.gif")));
				checkBoxField.setGraphic(methodIcon);
			} else if (field.toString().contains("protected")) {
				Node methodIcon = new ImageView(new Image(getClass()
						.getResourceAsStream("icons/field_protected_obj.gif")));
				checkBoxField.setGraphic(methodIcon);
			} else if (field.toString().contains("private")) {
				Node methodIcon = new ImageView(new Image(getClass()
						.getResourceAsStream("icons/field_private_obj.gif")));
				checkBoxField.setGraphic(methodIcon);
			} else {
				Node methodIcon = new ImageView(new Image(getClass()
						.getResourceAsStream("icons/field_default_obj.gif")));
				checkBoxField.setGraphic(methodIcon);
			}

			checkBoxTreeItem.getChildren().add(checkBoxField);
		}
	}
}
