package fop.model.gameplay;

import java.util.LinkedList; 
import java.util.List;

import fop.model.interfaces.Observer;

public class Observable<T> {

	private List<Observer<T>> observers = new LinkedList<Observer<T>>();
	
	/**
	 * Adds the given Observer to list 
	 * @param o
	 * @return
	 */
	public boolean addObserver(Observer<T> o) {
		return observers.add(o);
	}
	/**
	 * removes the given Observer
	 * @param o
	 * @return
	 */
	public boolean removeObserver(Observer<T> o) {
		return observers.remove(o);
	}
	/**
	 * pushes the object from type t in all the observers
	 * @param t
	 */
	public void push(T t) {
		for (Observer<T> o : observers)
			o.update(t);
	}
	/**
	 * returns the list of observer
	 * @return
	 */
	public List<Observer<T>> getOberserver(){
		return observers; 
	}

}