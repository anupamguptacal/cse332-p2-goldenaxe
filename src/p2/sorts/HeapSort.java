package p2.sorts;

import java.util.Comparator;
import cse332.exceptions.NotYetImplementedException;

public class HeapSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        throw new NotYetImplementedException();
    }
}
