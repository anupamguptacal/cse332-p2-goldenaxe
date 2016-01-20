package tests.gitlab.ckpt1;

import datastructures.worklists.CircularArrayFIFOQueue;
import tests.TestsUtility;

public class CircularArrayComparatorTests extends TestsUtility {
    
    public static void main(String[] args) {
        new CircularArrayComparatorTests().run();
    }
    
    @Override
    protected void run() {
        SHOW_TESTS = true;
        PRINT_TESTERR = true;
        DEBUG = true;

        test("test_empty_empty");
        test("test_ab_ab");
        test("test_ab_abc");
        test("test_abc_ab");
        test("test_ac_abc");
        test("test_a_aa");
        test("test_equality_consistent_with_compare");
        test("test_compare_transitive");
        
        finish();
    }
    
    protected static CircularArrayFIFOQueue<String> init() {
        return new CircularArrayFIFOQueue<String>(10);
    }

    private static int result(int a, int b) {
        return Integer.signum(a) == Integer.signum(b) ? 1 : 0;
    }
    
    public static int test_empty_empty() {
        CircularArrayFIFOQueue<String> l1 = init();
        CircularArrayFIFOQueue<String> l2 = init();
        return result(l1.compareTo(l2), "".compareTo(""));
    }
    
    public static int test_ab_ab() {
        CircularArrayFIFOQueue<String> l1 = init();
        CircularArrayFIFOQueue<String> l2 = init();
        l1.add("a");
        l1.add("b");
        l2.add("a");
        l2.add("b");
        return result(l1.compareTo(l2), "ab".compareTo("ab"));
    }
    public static int test_ab_abc() {
        CircularArrayFIFOQueue<String> l1 = init();
        CircularArrayFIFOQueue<String> l2 = init();
        l1.add("a");
        l1.add("b");
        l2.add("a");
        l2.add("b");
        l2.add("c");
        return result(l1.compareTo(l2), "ab".compareTo("abc"));
    }
    
    public static int test_abc_ab() {
        CircularArrayFIFOQueue<String> l1 = init();
        CircularArrayFIFOQueue<String> l2 = init();
        l1.add("a");
        l1.add("b");
        l1.add("c");
        l2.add("a");
        l2.add("b");
        return result(l1.compareTo(l2), "abc".compareTo("ab"));
    }
    public static int test_ac_abc() {
        CircularArrayFIFOQueue<String> l1 = init();
        CircularArrayFIFOQueue<String> l2 = init();
        l1.add("a");
        l1.add("c");
        l2.add("a");
        l2.add("b");
        l2.add("c");
        return result(l1.compareTo(l2), "ac".compareTo("abc"));
    }
    public static int test_a_aa() {
        CircularArrayFIFOQueue<String> l1 = init();
        CircularArrayFIFOQueue<String> l2 = init();
        l1.add("a");
        l2.add("a");
        l2.add("a");
        return result(l1.compareTo(l2), "a".compareTo("aa"));
    }
    
    public static int test_compare_transitive() {
        CircularArrayFIFOQueue<String> l1 = init();
        CircularArrayFIFOQueue<String> l2 = init();
        CircularArrayFIFOQueue<String> l3 = init();
        
        l1.add("abc");
        l2.add("def");
        l3.add("efg");
        return l1.compareTo(l2) < 0 && l2.compareTo(l3) < 0 && l1.compareTo(l3) < 0 ? 1 : 0;
    }
    
    public static int test_equality_consistent_with_compare() {
        CircularArrayFIFOQueue<String> l1 = init();
        CircularArrayFIFOQueue<String> l2 = init();
        l1.add("a");
        l1.add("b");
        l2.add("a");
        l2.add("b");
        return l1.equals(l2) && l1.compareTo(l2) == 0 ? 1 : 0;
    }
}
