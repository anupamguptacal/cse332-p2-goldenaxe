package p2.sorts;

import java.util.Comparator;
import cse332.exceptions.NotYetImplementedException;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        throw new NotYetImplementedException();
    }
}
