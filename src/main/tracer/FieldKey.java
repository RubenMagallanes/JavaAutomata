package main.tracer;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * A class that represents a field made unique by using class contained in
 *
 * @author campbealex2 and Nicky van Hulst
 */
public final class FieldKey implements Serializable, Comparable<FieldKey> {

	private static final long serialVersionUID = -5947697860425373926L;

	//name of the class holding the field
	private final String className;

	//name of the field
	private final String name;


	/**
	 * Constructor for the FieldKey
	 *
	 * @param name of the class
	 *
	 * @param name of the field
	 * */
	public FieldKey(String className, String name) {
		if(className == null || name == null)
			throw new NullPointerException();
		this.className = className;
		this.name = name;
	}


	/**
	 * Constructor for the FieldKey used the field
	 * object to get declaring class
	 *
	 * @param Field to represent
	 * */
	public FieldKey(Field field) {
		this(field.getDeclaringClass().getName(), field.getName());
	}

	/**
	 * Constructor for the FieldKey used the field
	 * object to get declaring class
	 *
	 * @param Field to represent
	 * */
	public FieldKey(com.sun.jdi.Field field) {
		this(field.declaringType().name(), field.name());
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof FieldKey))
			return false;
		FieldKey fk = (FieldKey)obj;

		if(!fk.name.equals(name))
			return false;

		if(!fk.className.equals(className))
			return false;

		return true;
	}


	/**
	 * Returns the name of the class holding the field
	 *
	 * @return class name
	 * */
	public String getClassName(){
		return this.className;
	}


	/**
	 * Returns the name of the field
	 *
	 * @return field name
	 * */
	public String getName(){
		return this.name;
	}

	@Override
	public int hashCode() {
		return className.hashCode() ^ name.hashCode();
	}

	@Override
	public String toString() {
		return className + "." + name;
	}

	@Override
	public int compareTo(FieldKey o) {
		return name.compareTo(o.name);
	}
}
