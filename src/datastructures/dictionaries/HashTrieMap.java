package datastructures.dictionaries;

import java.util.AbstractMap;
//import java.util.HashMap;
//import java.util.HashSet;
import java.util.Iterator;
//import java.util.Map;
import java.util.Map.Entry;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.BString;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.trie.TrieMap;
import datastructures.worklists.ArrayStack;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<Dictionary<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new ChainingHashTable<>(() -> new MoveToFrontList<>());
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            ArrayStack<Entry<A, HashTrieNode>> entryValue = new ArrayStack<>();

            for(Item<A,HashTrieNode> value : this.pointers) {
                entryValue.add(new AbstractMap.SimpleEntry(value.key, value.value));
            }
            return entryValue.iterator();
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
        this.size = 0;
    }

    @Override
    public V insert(K key, V value) {
    	if (key == null || value == null) {
    		throw new IllegalArgumentException();
    	}
    	if (this.root == null) {
    		this.root = new HashTrieNode();
    	}
    	
    	V returnValue = null;
    	if (key.isEmpty()) {
    		returnValue = this.root.value;
    		this.root.value = value;
    	} else {
    		HashTrieNode current = (HashTrieNode)this.root;
    		for (A part : key) {
    			if (current.pointers.find(part) == null) {
    				current.pointers.insert(part, new HashTrieNode());    			
    			}
    			current = current.pointers.find(part); 		  		
    		}
    		returnValue = current.value;
    		current.value = value;
    	}
    	
    	// returnValue is null when there was no previous value
    	// for the given key, meaning we need to increment size
    	if (returnValue == null) {
    		this.size++;
    	}
    	return returnValue;
    }

    @Override
    public V find(K key) {
    	if (key == null) {
    		throw new IllegalArgumentException();
    	}
    	if (this.root == null) {
    		return null;
    	}
    	
    	HashTrieNode current = (HashTrieNode)this.root;
    	for (A part: key) {
    		current = current.pointers.find(part);
    		if (current == null) {
    			return null;
    		}
    	}
    	return current.value;
    }

    @Override
    public boolean findPrefix(K key) {
    	if (key == null) {
    		throw new IllegalArgumentException();
    	}
    	if (this.root == null) {
    		return false;
    	}
    	
    	HashTrieNode current = (HashTrieNode)this.root;
    	for (A part: key) {
    		current = current.pointers.find(part);
    		if (current == null) {
    			return false;
    		} 
    	}
    	return true;	
    }

    @Override
    public void delete(K key) {
    	if (key == null) {
    		throw new IllegalArgumentException();
    	} 
    	HashTrieNode current = (HashTrieNode)this.root;
    	for (A part: key) {
    		if (current == null) {
    			return;
    		}
    		if (!current.pointers.isEmpty()) {
    			current = current.pointers.find(part);
    		} else {
    			return;
    		}
    	}
    	if (current != null && current.value != null) {
    		if (!current.pointers.isEmpty()) {
    			current.value = null;
    		} else {
    			this.root.value = null;
    		}
    		this.size--;
    	}
    }

    @Override
    public void clear() {
    	this.size = 0;
    	this.root = new HashTrieNode();
    }
}
