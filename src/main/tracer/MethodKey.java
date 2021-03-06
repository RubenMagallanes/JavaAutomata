package main.tracer;

import java.io.Serializable;
import java.lang.reflect.Method;

public final class MethodKey implements Serializable {
	private static final long serialVersionUID = 1L;

	//name of the class holding the method
	private final String className;

	//name of the method
	private final String name;

	//the argument types the method has
	private final String[] argTypes;


	/**
	 *Constructor for a MethodKey represents a method its name
	 *class and argument types
	 *
	 * @param class name
	 *
	 * @param method name
	 *
	 * @param arguments of the method
	 * */
	private MethodKey(String className, String name, String[] argTypes) {

		//class or method name should not be null
		if(className == null || name == null){
			throw new NullPointerException();
		}

		this.className = className;
		this.name = name;
		this.argTypes = argTypes;
	}


	/**
	 * Constructs MethodKey object with the information
	 * from the Method object
	 *
	 * @param method to get the required information from
	 * */
	public MethodKey(Method method) {
		this(method.getDeclaringClass().getName(), method.getName(), getArgTypesArray(method.getParameterTypes()));
	}


	/**
	 * Constructs MethodKey object with the information
	 * from the Method object
	 *
	 * @param method to get the required information from
	 * */
	public MethodKey(com.sun.jdi.Method method) {
		this(method.declaringType().name(), method.name(), method.argumentTypeNames().toArray(new String[0]));
	}


	/**
	 * Returns an array of the types of arguments used in the method
	 *
	 * @return string array of types of arguments
	 * */
	private static String[] getArgTypesArray(Class<?>[] types) {
		String[] stringTypes = new String[types.length];
		for(int k = 0; k < types.length; k++)
			stringTypes[k] = types[k].getName();
		return stringTypes;
	}


	/***
	 * Converts a class name into a more readable form
	 *
	 * @return string representing class name
	 * */
	private String toReadableClassName(String className) {
		if(className.equals("[B")) return "byte[]";
		if(className.equals("[C")) return "char[]";
		if(className.equals("[S")) return "short[]";
		if(className.equals("[I")) return "int[]";
		if(className.equals("[J")) return "long[]";
		if(className.equals("[F")) return "float[]";
		if(className.equals("[D")) return "double[]";
		if(className.equals("[Z")) return "boolean[]";
		if(className.startsWith("[L")) return toReadableClassName(className.substring(2, className.length()-1))+"[]";
		if(className.startsWith("["))
			return toReadableClassName(className.substring(1))+"[]";

		if(className.contains("."))
			className = className.substring(className.lastIndexOf('.') + 1);
		if(className.contains("$"))
			className = className.substring(className.lastIndexOf('$') + 1);
		return className;
	}


	/**
	 * Returns the method's arguments, in human-readable form.
	 *
	 * @return strings representing arguments
	 */
	private String getReadableArgs() {
		StringBuilder argsString = new StringBuilder();
		for(String argType : argTypes) {
			argsString.append(toReadableClassName(argType));
			argsString.append(", ");
		}
		if(argTypes.length != 0)
			argsString.setLength(argsString.length() - 2);
		return argsString.toString();
	}


	/**
	 * Returns the name of the class that holds
	 * the method
	 *
	 * @return name of the class
	 * */
	public String getClassName(){
		return this.className;
	}


	/**
	 * Returns the method name
	 *
	 * @return name of the method
	 * */
	public String getName(){
		return this.name;
	}


	/**
	 * Returns the type of arguments in the method
	 *
	 * @return arguments of the method
	 * */
	public String[] getArgTypes(){
		return this.argTypes;
	}


	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof MethodKey))
			return false;
		MethodKey mk = (MethodKey)obj;

		if(!mk.name.equals(name))
			return false;

		if(!mk.className.equals(className))
			return false;

		if(mk.argTypes.length != argTypes.length)
			return false;

		for(int k = 0; k < argTypes.length; k++)
			if(!argTypes[k].equals(mk.argTypes[k]))
				return false;

		return true;
	}

	@Override
	public int hashCode() {
		return (className.hashCode() ^ name.hashCode()) + argTypes.length;
	}

	@Override
	public String toString() {
		return className + "." + name + "(" + getReadableArgs() + ")";
	}
}
