package main.ui;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.Filer;

import com.sun.net.httpserver.Filter;
import com.sun.tools.internal.xjc.model.Constructor;

import main.Main;
import main.load.JarData;
import main.tracer.*;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.CheckBoxTreeItem.TreeModificationEvent;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SelectionPane extends TreeView {

	private CheckBoxTreeItem<ShortLongNames> rootItem;
	private MenuPane parent;
	private Map<ShortLongNames, MethodAndField> classesSelected = new HashMap<ShortLongNames, MethodAndField>();

	public SelectionPane(MenuPane parent) {
		this.setPrefWidth(GUIFrame.width/2 + GUIFrame.diffrence);
		this.setPrefHeight(GUIFrame.height - GUIFrame.consoleSize);
		this.setMaxHeight(GUIFrame.height - GUIFrame.consoleSize);
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
		JarData data = Main.getJarData();


		rootItem = new CheckBoxTreeItem<ShortLongNames>(new ShortLongNames(data.getName(), null));
		rootItem.setExpanded(true);

		// Getting the list of classes for the program
		List<Class<?>> classList = data.getClasses();
		// Makeing a factory for the checkbox's
		this.setCellFactory(CheckBoxTreeCell.<ShortLongNames> forTreeView());

		// Goes through all the classes and added them to the list.
		for (Class<?> tempClass : classList) {
			Node classIcon = new ImageView(new Image(getClass()
					.getResourceAsStream("icons/class_obj.gif")));
			final CheckBoxTreeItem<ShortLongNames> checkBoxTreeItem = new CheckBoxTreeItem<ShortLongNames>(
			           new ShortLongNames(tempClass.getName(), tempClass.toGenericString()), classIcon);
			try {
				// Trys to add methods and fields as children.
				addMethods(tempClass, checkBoxTreeItem);
				addFields(tempClass, checkBoxTreeItem);

			} catch (NoClassDefFoundError exc) {
			}

			// Adds them to the parent.
			checkBoxTreeItem.addEventHandler(
					CheckBoxTreeItem.checkBoxSelectionChangedEvent(),
					new EventHandler<TreeModificationEvent<ShortLongNames>>() {
						@Override
						public void handle(TreeModificationEvent<ShortLongNames> arg0) {
							ShortLongNames name = checkBoxTreeItem.getValue();
							//Checks that the selectiong was changed
							//If the parent that the event handler is part of
							//and the argument is the item we are monitoring.
							if (arg0.wasSelectionChanged()
									&& checkBoxTreeItem.isSelected()
									&& arg0.getTreeItem().equals(
											checkBoxTreeItem)) {
								//Makes sure it dosent contain the key.
								//If it dosent then it will store the class.
								if (!classesSelected.containsKey(name)) {
									classesSelected.put(name,
											new MethodAndField());
								}
							}
							//Handles if the item is deselected.
							if (arg0.wasSelectionChanged()
									&& !checkBoxTreeItem.isSelected()
									&& arg0.getTreeItem().equals(
											checkBoxTreeItem)) {
								if (classesSelected.containsKey(name)) {
									classesSelected.remove(name);
								}
							}
							//Updates the manager. If slow change implementation.

							//does not check if manager is null so we can filter before the traces is generated
							if (arg0.wasSelectionChanged() 	){
								setSelected(Main.getManager());
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
			CheckBoxTreeItem<ShortLongNames> checkBoxTreeItem)
			throws NoClassDefFoundError {
		for (Method method : tempClass.getDeclaredMethods()) {
			final CheckBoxTreeItem<ShortLongNames> checkBoxMethod = new CheckBoxTreeItem<ShortLongNames>(
					new ShortLongNames(method.getName(), new MethodKey(method).toString()));
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
					new EventHandler<TreeModificationEvent<ShortLongNames>>() {
						@Override
						public void handle(TreeModificationEvent<ShortLongNames> arg0) {
							ShortLongNames name = checkBoxMethod.getValue();
							ShortLongNames parentName = checkBoxMethod.getParent()
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
							//System.out.println(classesSelected);
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
			CheckBoxTreeItem<ShortLongNames> checkBoxTreeItem)
			throws NoClassDefFoundError {
		for (Field field : tempClass.getDeclaredFields()) {
			final CheckBoxTreeItem<ShortLongNames> checkBoxField = new CheckBoxTreeItem<ShortLongNames>(
					new ShortLongNames(field.getName(), new FieldKey(field).toString()));
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
					new EventHandler<TreeModificationEvent<ShortLongNames>>() {
						@Override
						public void handle(TreeModificationEvent<ShortLongNames> arg0) {
							ShortLongNames name = checkBoxField.getValue();
							ShortLongNames parentName = checkBoxField.getParent()
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
							//System.out.println(classesSelected);
						}
					});
			checkBoxTreeItem.getChildren().add(checkBoxField);

		}
	}

	public void setSelected(TraceManager traceManager) {
		TraceFilterSelector filter = new TraceFilterSelector();
		List<String> classNames = new ArrayList<String>();
		for (ShortLongNames snc: classesSelected.keySet()){
			classNames.add(snc.name);
		}
		filter.addClassToFilter(classNames);
		for (MethodAndField mf : classesSelected.values()){
			List<String> fieldNames = new ArrayList<>();
			for (ShortLongNames SLN : mf.fieldList){
				fieldNames.add(SLN.nameLong);
			}
			filter.addFieldToFilter(fieldNames);
			List<String> methodNames = new ArrayList<>();
			for (ShortLongNames SLN : mf.methodList){
				methodNames.add(SLN.nameLong);
			}
			filter.addMethodsToFilter(methodNames);
		}

		if(traceManager != null){//trace manager might be null becuase no trace has been generated yet
			traceManager.applyFilter(filter.getFilter());
		}
		Main.setFilter(filter.getFilter());//sets the filter that will be used by the program to generate the traces
	}

	private class MethodAndField {
		public List<ShortLongNames> methodList = new ArrayList<ShortLongNames>();
		public List<ShortLongNames> fieldList = new ArrayList<ShortLongNames>();

		@Override
		public String toString() {
			return "MethodAndField [methodList=" + methodList + ", fieldList="
					+ fieldList + "]";
		}
	}

	/**
	 * Used for naming each field in the tree ensuring that the
	 * fields and methods don't have clashes.
	 */
	private class ShortLongNames {
		public String name;
		public String nameLong;

		public ShortLongNames (String name, String nameLong){
			this.name = name;
			this.nameLong = nameLong;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			ShortLongNames that = (ShortLongNames) o;

			if (name != null ? !name.equals(that.name) : that.name != null) return false;
			return !(nameLong != null ? !nameLong.equals(that.nameLong) : that.nameLong != null);

		}

		@Override
		public int hashCode() {
			int result = name != null ? name.hashCode() : 0;
			result = 31 * result + (nameLong != null ? nameLong.hashCode() : 0);
			return result;
		}

		@Override
		public String toString() {
			return  name;
		}
	}

}
