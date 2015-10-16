package main.tracer.state;

/**
 * Represents a null value in a Java program.
 */
public class NullState extends State {

	private static final long serialVersionUID = 1L;

	/**
	 * Generates a hash code for this {@code NullState}.
	 */
	public int hashCode() {
		return 0x1274742F;
	}

	/**
	 * Returns true if this {@code NullState} is equivalent to the
	 * specified {@code Object}, otherwise returns false.
	 */
	public boolean equals(Object obj) {
		return obj instanceof NullState;
	}

	/**
	 * Returns a {@code String} representation of this {@code NullState}.
	 */
	public String toString() {
		return "null";
	}
}
