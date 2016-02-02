package tests.gitlab.ckpt2;

import p2.sorts.HeapSort;
import tests.TestsUtility;

public class HeapSortTests extends TestsUtility {
	public static void main(String[] args) {
		new HeapSortTests().run();
	}

	@Override
	protected void run() {
		SHOW_TESTS = true;
		test("integer_sorted");
		test("integer_random");
		finish();
	}

	public static int integer_sorted() {
		Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		Integer[] arr_sorted = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		HeapSort.sort(arr, (i1, i2) -> i1.compareTo(i2));
		for(int i = 0; i < arr.length; i++) {
			if(!arr[i].equals(arr_sorted[i]))
				return 0;
		}
		return 1;
	}

	public static int integer_random() {
		Integer[] arr = {3, 1, 4, 5, 9, 2, 6, 7, 8};
		Integer[] arr_sorted = {1, 2, 3, 4, 5, 6, 7, 8, 9};
		HeapSort.sort(arr, (i1, i2) -> i1.compareTo(i2));
		for(int i = 0; i < arr.length; i++) {
			if(!arr[i].equals(arr_sorted[i]))
				return 0;
		}
		return 1;
	}
}
