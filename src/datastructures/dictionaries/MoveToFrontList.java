package datastructures.dictionaries;

import java.util.Iterator;

import cse332.datastructures.containers.*;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.SimpleIterator;

/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find is called on an item, move it to the front of the 
 *    list. This means you remove the node from its current position 
 *    and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 *    elements to the front.  The iterator should return elements in
 *    the order they are stored in the list, starting with the first
 *    element in the list.
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {
    private ListItemNode front;
    
    private class ListItemNode {
        private Item<K, V> data;
        private ListItemNode next;
        
        public ListItemNode() {
            this(null, null);
        }
        
        public ListItemNode(Item<K, V> item) {
            this(item, null);
        }
        
        public ListItemNode(Item<K, V> item, ListItemNode next) {
            // do I throw an IllegalArgumentException if item is null?
            this.data = item;
            this.next = next;
        }
    }
    
    public MoveToFrontList() {
        this(null);
    }
    
    public MoveToFrontList(Item<K, V> item) {
        this.front = new ListItemNode(item);
        // is this necessary? Is client allowed to put null values
        // with non-null keys in this dictionary?
        if (item == null || item.key == null || item.value == null) {
            this.size = 0;
        } else {
            this.size = 1;
        }
    }
    
    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        V prevVal = this.find(key);
        if (prevVal != null) {
            // Is it bad to edit existing node rather than creating new one?
            this.front.data.value = value;
        } else { // there was not a previous mapping for given key
            this.front = new ListItemNode(new Item(key, value), this.front);
            this.size++;
        }
        return prevVal;
    }

    @Override
    public V find(K key) {
        
        // I want to use the iterator, but I can't think of a way
        // to do it without a break statement :-/
        
        if (key == null) {
            throw new IllegalArgumentException();
        }
        ListItemNode current = this.front;
        V returnVal = null;
        if (current != null && current.data != null) {
            if (current.data.key.equals(key)) {
                return current.data.value;
            }
            // Will there ever be a case where the next node is not null,
            // but its data is null?
            // Don't think so: client is not allowed to insert
            // null keys and values
            while (current.next != null && current.next.data != null && 
                   !current.next.data.key.equals(key)) {
                
                current = current.next;
            }
            if (current.next != null && current.next.data != null) {
                returnVal = current.next.data.value;
                ListItemNode referenced = current.next;
                current.next = referenced.next;
                referenced.next = this.front;
                this.front = referenced;
            }
        }
        return returnVal;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new MoveToFrontListIterator();
    }
    
    private class MoveToFrontListIterator extends SimpleIterator<Item<K, V>> {
        private ListItemNode current;
        
        public MoveToFrontListIterator() {
           this.current = MoveToFrontList.this.front;
        }
        
        public boolean hasNext() {
            return current != null && current.next != null;
        }
        
        public Item<K, V> next() {
            Item<K, V> returnItem = current.data;
            current = current.next;
            return returnItem;
        }
        
    }
}
