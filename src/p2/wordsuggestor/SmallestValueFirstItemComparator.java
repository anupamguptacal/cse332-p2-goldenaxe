package p2.wordsuggestor;

import java.util.Comparator;
import cse332.datastructures.containers.Item;

public class SmallestValueFirstItemComparator<K extends Comparable<K>, V extends Comparable<V>>
        implements Comparator<Item<K, V>> {
    @Override
    public int compare(Item<K, V> e1, Item<K, V> e2) {
        int result = e1.value.compareTo(e2.value);
        if (result != 0) {
            return result;
        }
        return e2.key.compareTo(e1.key);
    }
}