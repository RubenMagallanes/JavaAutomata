package main.tracer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicky van Hulst
 * */
public class TraceFilterSelector {

	TraceFilter customFilter;

	//filtered items
	private ArrayList<String> fieldNames;
	private ArrayList<String> methodNames;
	private ArrayList<String> paramatersNames;
	private ArrayList<String> classNames;


	public TraceFilterSelector(){
		this.fieldNames = new ArrayList<>();
		this.methodNames = new ArrayList<>();
		this.paramatersNames= new ArrayList<>();
		this.classNames = new ArrayList<>();
	}

	/**
	 * Adds the methods to the filter specified by the name
	 *
	 * @param list of methods to filter
	 * */
	public void addMethodsToFilter(List<String> methodNamesToFilter){
		for(String s : methodNamesToFilter){
			this.methodNames.add(s);
		}
		updateFilter();
	}


	/**
	 * Adds the fields to filter specified by the name
	 *
	 * @param list of fields to filter
	 * */
	public void addFieldToFilter(List<String> fieldNamesToFilter){
		for(String s : fieldNamesToFilter){
			this.fieldNames.add(s);
		}
		updateFilter();
	}


	/**
	 * Adds the Classes to filter specified by the name
	 *
	 * @param list of classes to filter
	 * */
	public void addClassToFilter(List<String> classNamesToFilter){
		for(String s : classNamesToFilter){
			this.classNames.add(s);
		}
		updateFilter();
	}


	/**
	 * Clear the filter of methods
	 * */
	public void clearMethods(){
		methodNames.clear();
		updateFilter();
	}


	/**
	 * Clear the filter of Classes
	 * */
	public void clearClasses(){
		classNames.clear();
		updateFilter();
	}

	/**
	 * Clear the filter of fields
	 * */
	public void clearFields(){
		fieldNames.clear();
		updateFilter();
	}


	/**
	 * Updates the filter with the current strings in the lists of methods, fields, paramaters, classes
	 * to be filtered
	 * */
	private void updateFilter(){
		customFilter = new TraceFilter() {

			@Override
			public boolean isParameterTraced(ParameterKey p) {
				for(String s : paramatersNames){
					//if(p.)
					//p.
				}
				return false;
			}

			@Override
			public boolean isMethodTraced(MethodKey m) {
				for(String s : methodNames){
					if(m.name.equals(s))return true;
				}
				return false;
			}

			@Override
			public boolean isFieldTraced(FieldKey f) {
				for(String s : fieldNames){
					if(f.name.equals(s))return true;
				}
				return false;
			}
		};
	}


	/**
	 * Returns the custom trace filter
	 * */
	public TraceFilter getFilter(){
		return this.customFilter;
	}
}
