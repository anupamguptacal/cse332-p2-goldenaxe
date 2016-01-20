package tests.gitlab.ckpt2;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.Dictionary;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.MoveToFrontList;
import tests.TestsUtility;

public class HashTableTests extends TestsUtility {
	
	public static void main(String[] args) {
		new HashTableTests().run();
	}
	
	@Override
	protected void run() {
        SHOW_TESTS = true;

		test("testHugeHashTable");	
		finish();
	}

	private static void incCount(Dictionary<String, Integer> list, String key) {
		Integer find = list.find(key);
		if (find == null)
			list.insert(key, 1);
		else
			list.insert(key, 1 + find);
	}
	public static int testHugeHashTable() {
		ChainingHashTable<String, Integer> list = new ChainingHashTable<>(() -> new MoveToFrontList<>());
		
		int n = 1000;
		
		// Add them
		for (int i = 0; i < 5 * n; i++) {
			int k = (i % n) * 37 % n;
			String str = String.format("%05d", k);
			for (int j = 0; j < k + 1; j ++)
				incCount(list, str);
		}

		// Delete them all
		boolean passed = true;
		int totalCount = 0;
		for (Item<String, Integer> dc : list) {
			passed &= (Integer.parseInt(dc.key) + 1) * 5 == dc.value;
//			System.out.println(((Integer.parseInt(dc.data) + 1) * 5 == dc.count) + ": " + ((Integer.parseInt(dc.data) + 1) * 5) + "==" + dc.count);
			totalCount += dc.value;
		}

		passed &= totalCount == (n * (n + 1)) / 2 * 5;
		passed &= list.size() == n;
		passed &= list.find("00851") == 4260;
		
		return passed ? 1 : 0;
	}
}
