package experiment;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.DeletelessDictionary;


/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. You must implement a generic chaining hashtable. You may not
 *    restrict the size of the input domain (i.e., it must accept 
 *    any key) or the number of inputs (i.e., it must grow as necessary).
 * 3. Your HashTable should rehash as appropriate (use load factor as
 *    shown in class).
 * 5. HashTable should be able to grow at least up to 200,000 elements. 
 * 6. We suggest you hard code some prime numbers. You can use this
 *    list: http://primes.utm.edu/lists/small/100000.txt 
 *    NOTE: Do NOT copy the whole list!
 */
public class ChainingHashTableChangedHashFunction<K, V> extends DeletelessDictionary<K, V>{
    private final Supplier<MoveToFrontList<K, V>> newChain;  
    private double loadFactor;
    private MoveToFrontList<K,V>[] array;
    private final int[] sizes = {17, 37, 79, 163, 331, 673, 1361, 2729, 5471, 10949, 21911, 43853, 87719, 175447, 350899, 701819};
    private int starting;
    private double count;
    private int counter;
    private int spaceCounter;
    private int steps;
    //private int iteratorStarter;
    
    public ChainingHashTableChangedHashFunction(Supplier<MoveToFrontList<K, V>> newChain) {
        //////Systemerr.println("Constructor called");
        this.newChain = newChain;
        loadFactor = 0.0;
        array = new MoveToFrontList[7];
        for(int i = 0; i < 7; i ++) {
            array[i] = newChain.get();
        }
        starting = 0;
        //iteratorStarter = 0;
        count = 0.0;
        counter = 0;
        this.steps++;
    }
    

    public int size() {
        System.out.println("returning " + counter);
        this.steps++;
        return counter;
    }
    
    public V insert(K key, V value) {
        this.steps++;
        if(loadFactor >= 1) {
            this.array = resize(array);        
        } 
          int index = Math.abs(key.hashCode() % array.length);  
          if(index > 5) {
              index = 1;
          } else {
              index = 0;
          }
          if(index >= 0) {
              this.steps++;
              if(array[index] == null) {
                  array[index] = newChain.get();
              }
              if(this.find(key) == null) {
                  counter ++;
              }
              array[index].insert(key, value);
              loadFactor = (++count) / array.length;
            return value;
          } else {
              return null;
          }
    }

    public void resetCount() {
        this.steps++;
        for(int i = 0; i < this.array.length; i ++) {
            array[i].steps = 0; 
        }
    }
    public long totalSteps() {
        this.steps++;
        long result = 0;
        for(int i = 0; i < this.array.length; i ++) {
            if(array[i] != null)
                result += array[i].getSteps();
        }
        return result + this.steps;
    }
    
    public V find(K key) {
        this.steps++;
        
        int index = Math.abs(key.hashCode() % array.length);
        if(index > 5) {
            index = 1;
        } else {
            index = 0;
        }
        
        if(index >= 0) {
            if(array[index] == null) {
                array[index] = newChain.get();
                return null;
            }
            return array[index].find(key);
        } else {
            return null;
        }
    }

    public int returnSpaceCounter() {
        this.steps++;
        return spaceCounter;
    }
    

    public Iterator<Item<K, V>> iterator() {
        this.steps++;
        if(array[0] == null) {
            array[0] = newChain.get();
        }
        //Iterator<Item<K,V>> iteratorConsideration = array[0].iterator();
        Iterator<Item<K,V>> it = new Iterator<Item<K,V>>() { 
            private int iteratorStarter = 0;
            
            Iterator<Item<K,V>> iteratorConsideration = array[0].iterator();
            
            @Override
            public boolean hasNext() {
                if(iteratorStarter < array.length && !iteratorConsideration.hasNext()) {
                   if(array[iteratorStarter + 1] == null) {
                       iteratorStarter++;
                       
                        while(array[iteratorStarter ] == null) {
                              iteratorStarter ++;
                              if(iteratorStarter >= array.length) {
                                  return false;
                              }
                        }
                       } else {
                       iteratorStarter ++;
                   }
                   if(iteratorStarter < array.length) {
                       iteratorConsideration = array[iteratorStarter].iterator();
                   }
                }
                if(iteratorStarter >= array.length) {
                    return false;
                } else {
                    return iteratorConsideration.hasNext();
                }
            }

            @Override
            public Item<K, V> next() {
                if(!hasNext()) {
                    throw new NoSuchElementException();
                }
                return iteratorConsideration.next();
            }
        };
        return it;
    }
    
   
    private MoveToFrontList<K,V>[] resize(MoveToFrontList<K,V> arrayChange[]) {
        this.steps++;
        for(int i = 0; i < arrayChange.length; i ++) {
            if(arrayChange[i] == null || arrayChange[i].isEmpty()) {
                spaceCounter++;
            }
        }
        MoveToFrontList<K,V>[] changedMoveToFrontList;
        if(starting > 15) {
            changedMoveToFrontList = new MoveToFrontList[arrayChange.length * 2];
        } else {
           changedMoveToFrontList = new MoveToFrontList[sizes[starting]];
        }
        for(int i = 0; i < arrayChange.length; i++) {
            if(arrayChange[i] != null) {
                this.steps++;
                for(Item<K,V> item : arrayChange[i]) {
                    int index = Math.abs(item.key.hashCode() % changedMoveToFrontList.length);
                    if(index > 5) {
                        index = 1;
                    } else {
                        index = 0;
                    }
                    if(index >= 0) {
                        if(changedMoveToFrontList[index] == null) {
                            changedMoveToFrontList[index] = newChain.get();
                        }
                        changedMoveToFrontList[index].insert(item.key, item.value);
                    } else {
                    return new MoveToFrontList[0];
                }
            }
        }
        }
        starting ++;
        return changedMoveToFrontList;
        
    }
    }


 

