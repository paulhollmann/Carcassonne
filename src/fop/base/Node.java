package fop.base;

/**
 * This class represents a node from type <T>
 * 
 * @param <T>
 */
public class Node<T> {

	private T value;

	/**
	 * creates a node with the value from type T
	 * 
	 * @param value
	 */
	public Node(T value) {
		this.value = value;
	}

	/**
	 * returns the object T
	 * 
	 * @return T
	 */
	public T getValue() {
		return value;
	}

	/**
	 * Returns the string value from the object T
	 * 
	 * @return string
	 */
	public String toString() {
		return value.toString();
	}
}
