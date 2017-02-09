package p2.sorts;

import java.util.Comparator;

import datastructures.worklists.MinFourHeap;

public class HeapSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        MinFourHeap<E> sortingHeap = new MinFourHeap<E>(comparator);
        for (E item : array) {
            sortingHeap.add(item);
        }
        int i = 0;
        while (sortingHeap.size() > 0) {
            array[i] = sortingHeap.next();
            i++;
        }
    }
}
