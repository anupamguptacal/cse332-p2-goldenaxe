package datastructures.worklists;
import java.util.NoSuchElementException;

import cse332.interfaces.worklists.LIFOWorkList;
/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {
	private E[] array;
	private int top;
    
	public ArrayStack() {
    	this.array = (E[])new Object[10];
    	this.top = -1;
    }
	
    @Override
    public void add(E work) {
    	if (this.top + 1 == this.array.length) {
    		E[] arrayCopy = (E[])new Object[array.length * 2];
    		for (int i = 0; i < this.array.length; i++) {
    			arrayCopy[i] = this.array[i];
    		}
    		this.array = arrayCopy;
    	}
    	this.array[++this.top] = work;
    }
    
    @Override
    public E peek() {
    	if (this.top == -1) {
    		throw new NoSuchElementException();
    	} else {
    		return this.array[this.top];
    	}
    }
    
    @Override
    public E next() {
    	if (this.top == -1) {
    		throw new NoSuchElementException();
    	} else {
    		this.top--;
    		return array[this.top + 1];
    	}
    }
    
    @Override
    public int size() {
    	return this.top + 1;
    }
    
    @Override
    public void clear() {
        this.top =  -1;
        this.array = (E[])new Object[10];
    }
}
