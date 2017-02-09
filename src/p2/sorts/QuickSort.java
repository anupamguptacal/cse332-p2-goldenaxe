package p2.sorts;

import java.util.Comparator;


public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        sort(array, comparator, 0, array.length);
    }
    
    private static <E> void sort(E[] array, Comparator<E> comparator, int from, int to) {
        if (to - from >= 2) {
            E pivot = array[to - 1];
            int divide = from - 1;
            for (int i = from; i < to; i++) {
                if (comparator.compare(array[i], pivot) <= 0) {
                    divide++;
                    E temp = array[divide];
                    array[divide] = array[i];
                    array[i] = temp;
                }
            }
            sort(array, comparator, from, divide);
            sort(array, comparator, divide + 1, to);
        }
    }
}
