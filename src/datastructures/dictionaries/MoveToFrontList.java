package datastructures.dictionaries;

import java.util.Iterator;

import cse332.datastructures.containers.*;
import cse332.exceptions.NotYetImplementedException;
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
            this.data = item;
            this.next = next;
        }
    }
    
    public MoveToFrontList() {
        this(null);
    }
    
    public MoveToFrontList(Item<K, V> item) {
        this.front = new ListItemNode(item);
    }
    
    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        // iterate over list to find item with given key (use find)
        // if found, replace value with value given here and return previous value
        // else:
        this.front = new ListItemNode(new Item(key, value), this.front);
        return null;
        // need the iterator implemented for this method to work
    }

    @Override
    public V find(K key) {
        // this will just be the key/value item closest to the
        // front of the list, because we don't have delete
        // this is where we move the referenced node to the front
        throw new NotYetImplementedException();
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new MoveToFrontListIterator();
    }
    private class MoveToFrontListIterator extends SimpleIterator<Item<K,V>> {
        private ListItemNode current;
        public MoveToFrontListIterator() {
           this.current = MoveToFrontList.this.front;
        }
        public boolean hasNext() {
            return current != null && current.next != null;
        }
        public Item<K,V> next() {
            Item<K,V> returnItem = current.data;
            current = current.next;
            return returnItem;
        }
        
    }
}
