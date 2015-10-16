package main.tracer.state;

/**
 * State representing a {@code String} in a Java program.
 */
public class StringState extends State {

	private static final long serialVersionUID = 1L;

	// fields
	private final String stringValue;

	/**
	 * Constructs a new instance of {@code StringState} with the
	 * specified {@code String} value.
	 *
	 * @param value
	 * 		- string value
	 */
	public StringState(String value) {
		this.stringValue = value;
	}

	/**
	 * Returns the {@code String} this {@code StringState} represents.
	 * @return
	 */
	public String getValue(){
		return stringValue;
	}

	/**
	 * Generates a hash code for this {@code StringState}.
	 */
	public int hashCode() {
		return stringValue.hashCode();
	}

	/**
	 * Returns true if this {@code StringState} is equivalent to the
	 * specified {@code Object}, otherwise returns false.
	 */
	public boolean equals(Object obj) {
		return obj instanceof StringState && ((StringState)obj).stringValue.equals(stringValue);
	}

	/**
	 * Returns a {@code String} representation of this {@code StringState}.
	 */
	public String toString() {
		return stringValue.replace("\"","\\\"");
	}
}
