package tests.gitlab.ckpt2;

import cse332.datastructures.containers.Item;
import cse332.datastructures.trees.BinarySearchTree.BSTNode;
import cse332.interfaces.misc.Dictionary;
import datastructures.dictionaries.AVLTree;
import tests.TestsUtility;

public class AVLTreeTests extends TestsUtility {
	
	public static void main(String[] args) {
		new AVLTreeTests().run();
	}
	
	@Override
	protected void run() {
        SHOW_TESTS = true;
        DEBUG = true;
		test("testTreeWith5Items");
		test("testHugeTree");
		test("checkStructure");
		finish();
	}

	protected static AVLTree<String, Integer> init() {
		AVLTree<String, Integer> tree = new AVLTree<>();

		return tree;
	}
	
	private static <E extends Comparable<E>> void incCount(Dictionary<E, Integer> tree, E key) {
		Integer value = tree.find(key);
		if (value == null) {
			tree.insert(key, 1);
		} else {
			tree.insert(key, value + 1);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static int checkStructure() {
		AVLTree<Integer, Integer> tree = new AVLTree<>();
		incCount(tree, 10);
		incCount(tree, 14);
		incCount(tree, 10);
		incCount(tree, 31);
		incCount(tree, 10);
		incCount(tree, 13);
		incCount(tree, 10);
		incCount(tree, 10);
		incCount(tree, 12);
		incCount(tree, 10);
		incCount(tree, 13);
		incCount(tree, 10);
		incCount(tree, 10);
		incCount(tree, 11);
		incCount(tree, 10);
		incCount(tree, 14);
		incCount(tree, 9);
		incCount(tree, 8);
		incCount(tree, 7);
		incCount(tree, 6);
		incCount(tree, 5);
		incCount(tree, 4);
		incCount(tree, 3);
		incCount(tree, 2);
		incCount(tree, 1);
		incCount(tree, 0);
//		{10, 14, 10, 31, 10, 13, 10, 10, 12, 10, 13, 10, 10, 11, 10, 14, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0}
//		{10, 14, 31, 13, 12, 11, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0}

		BSTNode root = (BSTNode) getField(tree, "root");
		
		String trueData = " [8 [4 [2 [1 [0..].] [3..]] [6 [5..] [7..]]] [12 [10 [9..] [11..]] [14 [13..] [31..]]]]";
		String trueCounts = " [1 [1 [1 [1 [1..].] [1..]] [1 [1..] [1..]]] [1 [9 [1..] [1..]] [2 [2..] [1..]]]]";
//		String trueData = " [10 [6 [2 [1 [0..].] [4 [3..] [5..]]] [8 [7..] [9..]]] [13 [12 [11..].] [14. [31..]]]]";
//		String trueCounts = " [9 [1 [1 [1 [1..].] [1 [1..] [1..]]] [1 [1..] [1..]]] [2 [1 [1..].] [2. [1..]]]]";

//		System.err.println(nestd(root));
//		System.err.println(trueData);
		return nestd(root).equals(trueData) &&
				nestc(root).equals(trueCounts) ? 1 : 0;
	}

	@SuppressWarnings("rawtypes")
	public static String nestd(BSTNode root) {
		if(root == null)
			return ".";
		return " [" + root.key + nestd(root.children[0]) + nestd(root.children[1]) + "]";
	}
	@SuppressWarnings("rawtypes")
	public static String nestc(BSTNode root) {
		if(root == null)
			return ".";
		return " [" + root.value + nestc(root.children[0]) + nestc(root.children[1]) + "]";
	}
	
	public static int testTreeWith5Items() {
		AVLTree<String, Integer> tree = init();
		String[] tests_struct = { "a", "b", "c", "d", "e" };
		String[] tests = { "b", "d", "e", "c", "a" };
		for (int i = 0; i < 5; i++) {
			String str = tests[i] + "a";
			incCount(tree, str);
		}

		boolean passed = true;
		int i = 0;
		for (Item<String, Integer> item : tree) {
			String str_heap = item.key;
			String str = tests_struct[i] + "a";
			passed &= str.equals(str_heap);
			i++;
		}

		return passed ? 1 : 0;
	}

	public static int testHugeTree() {
		AVLTree<String, Integer> tree = init();
		int n = 1000;

		// Add them
		for (int i = 0; i < 5 * n; i++) {
			int k = (i % n) * 37 % n;
			String str = String.format("%05d", k);
			for (int j = 0; j < k + 1; j ++)
				incCount(tree, str);
		}

		// Delete them all
		boolean passed = true;
		int totalCount = 0;
		for (Item<String, Integer> dc : tree) {
			passed &= (Integer.parseInt(dc.key) + 1) * 5 == dc.value;
			totalCount += dc.value;
		}
		
		// Check for accuracy
		passed &= totalCount == (n * (n + 1)) / 2 * 5;
		passed &= tree.size() == n;
		passed &= tree.find("00851") == 4260;
		
		return passed ? 1 : 0;
	}

}
