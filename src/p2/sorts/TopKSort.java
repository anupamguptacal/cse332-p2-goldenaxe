package p2.sorts;

import java.util.Comparator;
import datastructures.worklists.MinFourHeap;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        MinFourHeap<E> sortingHeap = new MinFourHeap<E>(comparator);
        for (int i = 0; i < k; i++) {
            sortingHeap.add(array[i]);
        }
        for (int i = k; i < array.length; i++) {
            if (comparator.compare(array[i], sortingHeap.peek()) > 0) {
                sortingHeap.next();
                sortingHeap.add(array[i]);
            }
            array[i] = null;
        }
        for (int i = 0; i < k; i++) {
            array[i] = sortingHeap.next();
        }
    }
}
