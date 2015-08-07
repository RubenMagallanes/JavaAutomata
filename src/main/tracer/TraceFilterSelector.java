package main.tracer;

import java.util.ArrayList;


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
	 * Adds the method specified by the name
	 * */
	public void addMethodToFilter(String methodName){
		methodNames.add(methodName);
		updateFilter();
	}

	
	/**
	 * Adds the Field specified by the name
	 * */
	public void addFieldToFilter(String fieldName){
		fieldNames.add(fieldName);
		updateFilter();
	}
	
	
	/**
	 * Adds the Class specified by the name
	 * */
	public void addClassToFilter(String fieldName){
		classNames.add(fieldName);
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
