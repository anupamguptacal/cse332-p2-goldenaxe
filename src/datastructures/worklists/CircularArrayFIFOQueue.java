package datastructures.worklists;

import cse332.interfaces.worklists.FixedSizeFIFOWorkList;
import java.util.NoSuchElementException;
import cse332.exceptions.NotYetImplementedException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E> extends FixedSizeFIFOWorkList<E> {
	public E[] array;
	private int read;
	private int size;
	
    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        this.array = (E[])new Comparable[capacity];
        this.read = 0;
        this.size = 0;
    }

    @Override
    // read refers to the first element in the array
    public void add(E work) {
    	if (this.isFull()) {
    		throw new IllegalStateException();
    	}
    	this.array[(this.read + this.size) % this.array.length] = work;
    	this.size++;
    }
    

    @Override
    public E peek() {
    	return this.peek(0);
    }
    
    @Override
    public E peek(int i) {
    	this.sizeCheck();
    	this.boundsCheck(i);
    	return this.array[(this.read + i) % this.array.length];
    }
    
    @Override
    public E next() {
    	this.sizeCheck();
    	E element = this.array[this.read];
    	this.read = (this.read + 1) % this.array.length;
    	this.size--;
    	return element;
    	
    }
    
    @Override
    public void update(int i, E value) {
    	this.sizeCheck();
    	this.boundsCheck(i);
    	this.array[(this.read + i) % this.array.length] = value;
    }
    
    @Override
    public int size() {
    	return this.size;
        
    }
    
    @Override
    public void clear() {
    	this.size = 0;
    	this.read = 0;
    	this.array = (E[])new Comparable[this.capacity()];
    }
    
    private void sizeCheck() {
    	if (this.size <= 0) {
    		throw new NoSuchElementException();
    	}
    }
    
    private void boundsCheck(int i) {
    	if (i < 0 || i >= this.size) {
    		throw new IndexOutOfBoundsException();
    	}
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        // You will implement this method in p2. Leave this method unchanged for p1.
        throw new NotYetImplementedException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in p2. Leave this method unchanged for p1.
        if (this == obj) {
            return true;
        }
        else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        }
        else {
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;

            // Your code goes here

            throw new NotYetImplementedException();
        }
    }

    @Override
    public int hashCode() {
        // You will implement this method in p2. Leave this method unchanged for p1.
        throw new NotYetImplementedException();
    }
}
