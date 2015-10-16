package main.tracer.state;

/**
 * State representing an enum in a Java program.
 */
public class EnumState extends State {

	private static final long serialVersionUID = 1L;

	// field
	private String value;

	/**
	 * Constructs a new instance of {@code EnumState} from the specified
	 * {@code String} value of an enum.
	 *
	 * @param value
	 * 		- enum value
	 */
	public EnumState(String value) {
		this.value = value;
	}

	/**
	 * Generates a hash code for this {@code EnumState}.
	 */
	public int hashCode() {
		return value.hashCode();
	}

	/**
	 * Returns true if this {@code EnumState} is equivalent to the
	 * specified {@code Object}, otherwise returns false.
	 */
	public boolean equals(Object obj) {
		return obj instanceof EnumState && ((EnumState)obj).value.equals(value);
	}

	/**
	 * Returns a {@code String} representation of this {@code EnumState}.
	 */
	public String toString() {
		return value;
	}
}
