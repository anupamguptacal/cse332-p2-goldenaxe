package datastructures.worklists;

import cse332.interfaces.worklists.PriorityWorkList;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeap<E extends Comparable<E>> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;
    
    public MinFourHeap() {
        this.data = (E[])new Comparable[10];
        this.size = 0;
    }

    @Override
    // returns true if the list is not empty, false otherwise
    // not sure if we're supposed to change its behavior...?
    public boolean hasWork() {
        return super.hasWork();
    }

    @Override
    // adds given element to list
    public void add(E work) {
        this.data[this.size] = work;
        // if there's only one element, no percolating needed
        if (this.size > 0) {
        	// this.size represents child's index
        	this.percolateUp(this.parent(this.size), this.size);
        }
        this.size++;
        
        if (this.size == this.data.length) {
        	this.growArray();
        }
    }

    @Override
    // returns the value with highest priority
    public E peek() {
    	if (!this.hasWork()) {
    		throw new NoSuchElementException();
    	}
    	// first element has highest priority
        return this.data[0];
    }

    @Override
    // removes and returns the value with highest priority
    public E next() {
    	if (!this.hasWork()) {
    		throw new NoSuchElementException();
    	}
    	
        E value = this.data[0];
        // last element in heap moves to the top
        this.data[0] = this.data[this.size - 1];
        this.size--;
        // if there's only one element, no percolating needed
        if (this.size > 0) {
        	// begins percolating down with root and child of root with
        	// highest priority (smallest value)
        	int firstChildIndex = this.child(0,0);
        	this.percolateDown(0, this.findMinChildIndex(firstChildIndex));
        }
        return value;
    }

    @Override
    // returns number of elements in the list
    public int size() {
        return this.size;
    }

    @Override
    // removes all elements from the list
    public void clear() {
        this.data = (E[])new Comparable[10];
        this.size = 0;
    }
    
    // doubles capacity of array
    private void growArray() {
    	E[] newArray = (E[])new Comparable[this.size * 2];
    	for (int i = 0; i < this.size; i++) {
    		newArray[i] = this.data[i];
    	}
    	this.data = newArray;
    }
    
    // returns index of parent, given index of child
    private int parent(int childIndex) {
    	return ((childIndex - 1)/4);
    }
    
    // returns index of specified nth child of given parent
    private int child(int nthChild, int parentIndex) {
    	return 4*parentIndex + nthChild + 1;
    }
    
    // starting from the bottom, switches child with parent until
    // heap requirements are met
    private void percolateUp(int parentIndex, int childIndex) {
    	// if the child is the root or the parent is of higher or
    	// equal priority than the child, we're done
    	while (childIndex != 0 && 
    		   this.data[childIndex].compareTo(this.data[parentIndex]) < 0) {
    		
    		E temp = this.data[childIndex];
    		this.data[childIndex] = this.data[parentIndex];
    		this.data[parentIndex] = temp;
    		childIndex = parentIndex;
    		parentIndex = this.parent(childIndex);
    	}
    }
    
    // starting from the root, switches parent with highest priority child
    // until heap requirements are met
    private void percolateDown(int parentIndex, int minChildIndex) {
    	// if the parent has no children or the children are all of lesser
    	// priority than the parent, we're done
    	while (minChildIndex != -1 && 
    		   this.data[parentIndex].compareTo(this.data[minChildIndex]) > 0) {
    		
    		E temp = this.data[minChildIndex];
    		this.data[minChildIndex] = this.data[parentIndex];
    		this.data[parentIndex] = temp;
    		parentIndex = minChildIndex;
    		int firstChildIndex = this.child(0, parentIndex);
    		minChildIndex = this.findMinChildIndex(firstChildIndex);
    	}
    		
    }
    
    // finds and returns the child that has the highest priority
    // returns -1 if the parent has no children (given lowest child's index
    // exceeds size of list)
    private int findMinChildIndex(int firstChild) {
    	int min;
    	if (firstChild > this.size) {
    		return -1;
    	} else {
    		min = firstChild;
    		for (int i = firstChild + 1; i < firstChild + 4; i++) {
    			if (i < this.size && this.data[i].compareTo(this.data[min]) < 0) {
    				min = i;
    			}
    		}
    	}
    	return min;
    }
}
