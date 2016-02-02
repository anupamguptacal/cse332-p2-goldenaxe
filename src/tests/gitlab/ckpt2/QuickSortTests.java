package tests.gitlab.ckpt2;

import java.util.Comparator;

import p2.sorts.QuickSort;
import tests.TestsUtility;

public class QuickSortTests extends TestsUtility {
	public static void main(String[] args) {
		new QuickSortTests().run();
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
		QuickSort.sort(arr, new Comparator<Integer>() {
			@Override
			public int compare(Integer e1, Integer e2) {
				return e1.compareTo(e2);
			}
		});
		for(int i = 0; i < arr.length; i++) {
			if(!arr[i].equals(arr_sorted[i]))
				return 0;
		}
		return 1;
	}

	public static int integer_random() {
		Integer[] arr = {3, 1, 4, 5, 9, 2, 6, 7, 8};
		Integer[] arr_sorted = {1, 2, 3, 4, 5, 6, 7, 8, 9};
		QuickSort.sort(arr, new Comparator<Integer>() {
			@Override
			public int compare(Integer e1, Integer e2) {
				return e1.compareTo(e2);
			}
		});
		for(int i = 0; i < arr.length; i++) {
			if(!arr[i].equals(arr_sorted[i]))
				return 0;
		}
		return 1;
	}
}