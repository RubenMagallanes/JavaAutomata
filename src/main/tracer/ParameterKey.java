package main.tracer;

import java.io.Serializable;

/**
 * An object that represents a parameter of a method
 *
 * @author Nicky van Hulst
 * */
public final class ParameterKey implements Serializable {
	private static final long serialVersionUID = 1L;

	//The MethodKey object this parameter is part of
	private final MethodKey method;

	//The index of the parameter in the method
	private final int index;

	/**
	 * Constructs a ParameterKey object with the method and index
	 *
	 * @param method parameter is part of
	 *
	 * @param index the index of the parameter in the method
	 * */
	public ParameterKey(MethodKey method, int index) {
		this.method = method;
		this.index = index;
	}


	/**
	 * Returns the MethodKey the parameter is part of
	 *
	 * @return the method it belongs to
	 * */
	public MethodKey getMethodKey(){
		return this.method;
	}


	/**
	 * Returns the index of the parameter in the method
	 *
	 * @param inex of the parameter
	 * */
	public int getIndex(){
		return this.index;
	}


	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ParameterKey)){
			return false;
		}
		ParameterKey pk = (ParameterKey)obj;
		return pk.method.equals(method) && pk.index == index;
	}

	@Override
	public int hashCode() {
		return method.hashCode() + index*259;
	}

	@Override
	public String toString() {
		return method + " arg" + index;
	}
}
