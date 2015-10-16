package main.tracer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicky van Hulst
 * */
public class TraceFilterSelector {

	//the filter object that holds the filtered items
	private TraceFilter customFilter;

	//filtered items
	private List<String> fieldNames;
	private List<String> methodNames;
	private List<String> classNames;


    // Class patterns for which we don't want events
    private String[] excludes = {"java", "javax", "sun",
                                 "com.sun"};

	/**
	 * Constructs the TraceFilterSector
	 * */
	public TraceFilterSelector(){
		this.fieldNames = new ArrayList<>();
		this.methodNames = new ArrayList<>();
		this.classNames = new ArrayList<>();

		updateFilter();
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
			updateFilter();
		}
	}


	/**
	 * Adds the Classes to filter specified by the name
	 *
	 * @param list of classes to filter
	 * */
	public void addClassToFilter(List<String> classNamesToFilter){
		for(String s : classNamesToFilter){
			this.classNames.add(s);
			updateFilter();
		}
	}


	/**
	 * Clear the filter of methods
	 * */
	public void clearMethods(){
		methodNames.clear();
	}


	/**
	 * Clear the filter of Classes
	 * */
	public void clearClasses(){
		classNames.clear();
	}

	/**
	 * Clear the filter of fields
	 * */
	public void clearFields(){
		fieldNames.clear();
	}


	/**
	 * Updates the filter with the current strings in the lists of methods, fields, paramaters, classes
	 * to be filtered
	 * */
	private void updateFilter(){
		customFilter = new TraceFilter() {

			@Override
			public boolean isParameterTraced(ParameterKey p) {
				return false;//Don't case about parameters
			}

			@Override
			public boolean isMethodTraced(MethodKey m) {
				for(String e : excludes){
					if(m.toString().startsWith(e)){
						return false;
					}
				}

				if(m.getName().contains("init")){
					return true;
				}

				for(String s : methodNames){
					if(m.toString().equals(s)){//TODO Update to use getName which is the longer name so we differentiate between methods in other classes
						return true;
					}
				}
				return false;
			}

			@Override
			public boolean isFieldTraced(FieldKey f) {
				for(String s : fieldNames){
					if(f.toString().equals(s))return true;
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

	public void printFilter(){
		System.out.println("Printing Filter");

		for(String m : methodNames){
			System.out.println(m);
		}

		for(String c : classNames){
			System.out.println(c);
		}

		System.out.println("Finished Printing Filter");
	}
}
