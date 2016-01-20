package tests.gitlab.ckpt2;

import datastructures.worklists.CircularArrayFIFOQueue;
import tests.TestsUtility;

public class CircularArrayHashCodeTests extends TestsUtility {	
	public static void main(String[] args) {
		new CircularArrayHashCodeTests().run();
	}
	
	@Override
	protected void run() {
        SHOW_TESTS = true;
		test("equality");
		test("ineq1");
		test("ineq2");
		test("ineq3");
		test("equality_consistent_with_hashcode");
		finish();
	}
	
	protected static CircularArrayFIFOQueue<String> init() {
		return new CircularArrayFIFOQueue<String>(10);
	}

	public static int equality() {
		CircularArrayFIFOQueue<String> l1 = init();
		CircularArrayFIFOQueue<String> l2 = init();
		for (int i = 0; i < 3; i++) {
			l1.add("a");
			l2.add("a");
		}
		return l1.hashCode() == l2.hashCode() ? 1 : 0;
	}
	public static int ineq1() {
		CircularArrayFIFOQueue<String> l1 = init();
		CircularArrayFIFOQueue<String> l2 = init();
		l1.add("a");
		l1.add("a");
		l1.add("b");
		l2.add("a");
		l2.add("a");
		l2.add("a");
		return l1.hashCode() != l2.hashCode() ? 1 : 0;
	}
	public static int ineq2() {
		CircularArrayFIFOQueue<String> l1 = init();
		CircularArrayFIFOQueue<String> l2 = init();
		l1.add("a");
		l1.add("a");
		l1.add("a");
		l1.add("a");
		l2.add("a");
		l2.add("a");
		l2.add("a");
		return l1.hashCode() != l2.hashCode() ? 1 : 0;
	}
	public static int ineq3() {
		CircularArrayFIFOQueue<String> l1 = init();
		CircularArrayFIFOQueue<String> l2 = init();
		l1.add("a");
		l1.add("b");
		l1.add("c");
		l2.add("c");
		l2.add("b");
		l2.add("a");
		return l1.hashCode() != l2.hashCode() ? 1 : 0;
	}
	
	public static int equality_consistent_with_hashcode() {
    	CircularArrayFIFOQueue<String> l1 = init();
		CircularArrayFIFOQueue<String> l2 = init();
		l1.add("a");
    	l1.add("b");
    	l2.add("a");
    	l2.add("b");
    	return l1.equals(l2) && l1.hashCode() == l2.hashCode() ? 1 : 0;
    }
}
