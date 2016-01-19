package datastructures.dictionaries;

import java.util.Iterator;

import cse332.datastructures.containers.*;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;

/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. The list is typically not sorted.
 * 2. Add new items to the front oft he list.
 * 3. Whenever find is called on an item, move it to the front of the 
 *    list. This means you remove the node from its current position 
 *    and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 *    elements to the front.  The iterator should return elements in
 *    the order they are stored in the list, starting with the first
 *    element in the list.
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {
    @Override
    public V insert(K key, V value) {
        throw new NotYetImplementedException();
    }

    @Override
    public V find(K key) {
        throw new NotYetImplementedException();
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        throw new NotYetImplementedException();
    }
}
