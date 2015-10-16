package main.parse;

/**
 * Class representing a field in an {@code AuotomataState}.
 *
 * @author Chris Chin
 * @author David Sheridan
 *
 */
public class AutomataField {

	// fields
	private String type;
	private String name;
	private String value;

	/**
	 * Constructs an instance of an {@code AutomataField} from the specified
	 * type, name and value.
	 *
	 * @param type
	 * 		data type of this field
	 * @param name
	 * 		name of this field
	 * @param value
	 * 		value stored in this field
	 */
	public AutomataField(String type, String name, String value){
		this.type = type;
		this.name = name;
		this.value = value;
	}

	/**
	 * Returns the data type of this {@code AutomataField}.
	 *
	 * @return
	 * 		data type of this field
	 */
	public String getType(){
		return type;
	}

	/**
	 * Returns the name of this {@code AutomataField}.
	 *
	 * @return
	 * 		name of this field
	 */
	public String getName(){
		return name;
	}

	/**
	 * Returns the value stored in this {@code AutomataField}.
	 *
	 * @return
	 * 		value stored in this field
	 */
	public String getValue(){
		return value;
	}

	/**
	 * Returns true if ths {@code AutomataField} is identical to the
	 * specified {@code Object}, otherwise returns false.
	 */
	public boolean equals(Object o){
		if(o == null){
			return false;
		}
		if(o instanceof AutomataField){
			AutomataField other = (AutomataField)o;
			if(!name.equals(other.getName())){
				return false;
			}
			else if(!value.equals(other.getValue())){
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * Returns a {@code String} representation of this {@code AutomataField}.
	 */
	public String toString(){
		return "{ type = " + type + ", name = " + name + ", value = " + value + "}";
	}

}
