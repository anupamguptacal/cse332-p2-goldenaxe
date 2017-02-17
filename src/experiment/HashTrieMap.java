package experiment;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.function.Supplier;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.BString;
import datastructures.worklists.ArrayStack;

/**
* See cse332/interfaces/trie/TrieMap.java
* and cse332/interfaces/misc/Dictionary.java
* for method specifications.
*/
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
  public class HashTrieNode extends TrieNode<ChainingHashTableCopy<A, HashTrieNode>, HashTrieNode> {
      public HashTrieNode() {
          this(null);
      }

      public HashTrieNode(V value) {
          class InternalSupplier implements Supplier<MoveToFrontList<K, V>> {
              public InternalSupplier() {
                  super();
              }
              
              public MoveToFrontList<K, V> get() {
                  return new MoveToFrontList<K, V>();
              }
          }
          InternalSupplier a = new InternalSupplier();
          ChainingHashTableCopy<A, HashTrieMap<A,K,V>.HashTrieNode> CHTC = new ChainingHashTableCopy(a);
          this.pointers = CHTC;
          this.value = value;
          steps++;
      }

      @Override
      public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
          ArrayStack<Entry<A, HashTrieNode>> entryValue = new ArrayStack<>();

          for(Item<A,HashTrieNode> value : this.pointers) {
              steps++;
              entryValue.add(new AbstractMap.SimpleEntry(value.key, value.value));
          }
          return entryValue.iterator();
      }
  }

  public HashTrieMap(Class<K> KClass) {
      super(KClass);
      this.root = new HashTrieNode();
      this.size = 0;
      this.steps = 1;
  }
  
  public long getSteps() {
      return this.steps;
  }
  
  public void resetCount(HashTrieNode current) {
      current.pointers.steps = 0;
  }

  @Override
  public V insert(K key, V value) {
      this.steps++;
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
              this.steps++;
              if (current.pointers.find(part) == null) {
                  current.pointers.insert(part, new HashTrieNode());
              }
              current = current.pointers.find(part);
              this.steps += current.pointers.getSteps();
              resetCount(current);
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
      this.steps++;
      if (key == null) {
          throw new IllegalArgumentException();
      }
      if (this.root == null) {
          return null;
      }
      
      HashTrieNode current = (HashTrieNode)this.root;
      for (A part: key) {
          this.steps++;
          current = current.pointers.find(part);
          if (current == null) {
              return null;
          }
          this.steps += current.pointers.getSteps();
          resetCount(current);
      }
      return current.value;
  }

  @Override
  public boolean findPrefix(K key) {
      this.steps++;
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
          this.steps += current.pointers.getSteps();
          resetCount(current);
      }
      return true;    
  }

  @Override
  public void delete(K key) {
      this.steps++;
      if (key == null) {
          throw new IllegalArgumentException();
      } 
      //HashTrieNode lastDelete = (HashTrieNode)this.root;
      A lastDeletePart = null;
      if (key.iterator().hasNext()) {
          lastDeletePart = key.iterator().next();
      }
      HashTrieNode current = (HashTrieNode)this.root;
      for (A part: key) {
          this.steps++;
          if (current == null) {
              return;
          }
          if (current.value != null || current.pointers.size() > 1) {
              //lastDelete = current;
              lastDeletePart = part;
          }
          if (!current.pointers.isEmpty()) {
              current = current.pointers.find(part);
              this.steps += current.pointers.getSteps();
              resetCount(current);
          } 
          else {
              return;
          }
      }
      if (current != null && current.value != null) {
          if (!current.pointers.isEmpty()) {
              current.value = null;
          } else if (lastDeletePart != null) {
              //lastDelete.pointers.remove(lastDeletePart);
          } else {
              this.root.value = null;
          }
          this.size--;
      }
  }

  @Override
  public void clear() {
      this.steps++;
      this.size = 0;
      this.root = new HashTrieNode();
  }
}
