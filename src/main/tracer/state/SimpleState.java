package main.tracer.state;

public class SimpleState extends State {

	private static final long serialVersionUID = 1L;

	// field
	private String stringValue;

	/**
	 * Constructs a new instance of a {@code SimpleState} with the
	 * specified {@code StringValue} value.
	 *
	 * @param stringValue
	 * 		- string value
	 */
	public SimpleState(String stringValue) {
		this.stringValue = stringValue;
	}

	/**
	 * Returns the {@code String} value associated with this {@code SimpleState}.
	 *
	 * @return
	 * 		- string value
	 */
	public String getValue(){
		return stringValue;
	}

	/**
	 * Generates a hash code for this {@code SimpleState}.
	 */
	public int hashCode() {
		return stringValue.hashCode();
	}

	/**
	 * Returns true if this {@code SimpleState} is equivalent to the
	 * specified {@code Object}, otherwise returns false.
	 */
	public boolean equals(Object obj) {
		return obj instanceof SimpleState && ((SimpleState)obj).stringValue.equals(stringValue);
	}

	/**
	 * Returns a {@code String} representation of this {@code SimpleState}.
	 */
	public String toString() {
		return stringValue;
	}
}
