package main.ui;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.Main;
import main.load.JarData;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.CheckBoxTreeItem.TreeModificationEvent;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SelectionPane extends TreeView {

	private CheckBoxTreeItem<String> rootItem;
	private MenuPane parent;
	private Map<String, MethodAndField> classesSelected = new HashMap<String, MethodAndField>();

	public SelectionPane(MenuPane parent) {
		this.parent = parent;
	}

	// Clears the old graph
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

		// Goes through all the classes and added them to the list.
		for (Class<?> tempClass : classList) {
			Node classIcon = new ImageView(new Image(getClass()
					.getResourceAsStream("icons/class_obj.gif")));
			final CheckBoxTreeItem<String> checkBoxTreeItem = new CheckBoxTreeItem<String>(
					tempClass.getName(), classIcon);
			try {
				// Trys to add methods and fields as children.
				addMethods(tempClass, checkBoxTreeItem);
				addFields(tempClass, checkBoxTreeItem);

			} catch (NoClassDefFoundError exc) {
			}

			// Adds them to the parent.
			checkBoxTreeItem.addEventHandler(
					CheckBoxTreeItem.checkBoxSelectionChangedEvent(),
					new EventHandler<TreeModificationEvent<String>>() {
						@Override
						public void handle(TreeModificationEvent<String> arg0) {
							String name = checkBoxTreeItem.getValue();
							if (arg0.wasSelectionChanged()
									&& checkBoxTreeItem.isSelected()
									&& arg0.getTreeItem().equals(
											checkBoxTreeItem)) {

								if (!classesSelected.containsKey(name)) {
									classesSelected.put(name,
											new MethodAndField());
								}
								System.out.println(classesSelected);
							}
							if (arg0.wasSelectionChanged()
									&& !checkBoxTreeItem.isSelected()
									&& arg0.getTreeItem().equals(
											checkBoxTreeItem)) {
								if (classesSelected.containsKey(name)) {
									classesSelected.remove(name);
								}
								System.out.println(classesSelected);
							}

						}

					});
			checkBoxTreeItem.setExpanded(false);
			rootItem.getChildren().add(checkBoxTreeItem);
		}
		// Sets the root of the tree
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

			/*
			 * This event handler handles adding and removeing
			 * Methods from the list of currently selected methods.
			 */
			checkBoxMethod.addEventHandler(
					CheckBoxTreeItem.checkBoxSelectionChangedEvent(),
					new EventHandler<TreeModificationEvent<String>>() {
						@Override
						public void handle(TreeModificationEvent<String> arg0) {
							String name = checkBoxMethod.getValue();
							String parentName = checkBoxMethod.getParent()
									.getValue();
							if (arg0.wasSelectionChanged()
									&& checkBoxMethod.isSelected()) {
								if (classesSelected.containsKey(parentName)) {
									if (!classesSelected.get(parentName).methodList
											.contains(name)) {
										classesSelected.get(parentName).methodList
												.add(name);
									}
								} else {
									classesSelected.put(parentName,
											new MethodAndField());
									classesSelected.get(parentName).methodList
											.add(name);
								}
							} else if (arg0.wasSelectionChanged()
									&& !checkBoxMethod.isSelected()) {
								if (classesSelected.containsKey(parentName)) {
									if (classesSelected.get(parentName).methodList
											.contains(name)) {
										classesSelected.get(parentName).methodList
												.remove(name);
									}
								}
							}
						}
					});
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
			checkBoxField.addEventHandler(
					CheckBoxTreeItem.checkBoxSelectionChangedEvent(),
					new EventHandler<TreeModificationEvent<String>>() {
						@Override
						public void handle(TreeModificationEvent<String> arg0) {
							String name = checkBoxField.getValue();
							String parentName = checkBoxField.getParent()
									.getValue();
							if (arg0.wasSelectionChanged()
									&& checkBoxField.isSelected()) {

								if (classesSelected.containsKey(parentName)) {
									if (!classesSelected.get(parentName).fieldList
											.contains(name)) {
										classesSelected.get(parentName).fieldList
												.add(name);
									}
								} else {
									classesSelected.put(parentName,
											new MethodAndField());
									classesSelected.get(parentName).fieldList
											.add(name);
								}
							} else if (arg0.wasSelectionChanged()
									&& !checkBoxField.isSelected()) {
								if (classesSelected.containsKey(parentName)) {
									if (classesSelected.get(parentName).fieldList
											.contains(name)) {
										classesSelected.get(parentName).fieldList
												.remove(name);
									}
								}
							}
						}
					});
			checkBoxTreeItem.getChildren().add(checkBoxField);

		}
	}

	public void getSelected() {

	}

	private class MethodAndField {
		public List<String> methodList = new ArrayList<String>();
		public List<String> fieldList = new ArrayList<String>();

		@Override
		public String toString() {
			return "MethodAndField [methodList=" + methodList + ", fieldList="
					+ fieldList + "]";
		}
	}

}
