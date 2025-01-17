package fop.model.interfaces;

public interface Observer<T> {

	/**
	 * updates the Observer with given object o from type t
	 * 
	 * @param o
	 */
	public void update(T o);

}
