package tests.gitlab.ckpt2;

import tests.GradingUtility;

public class Ckpt2Tests extends GradingUtility {
    public static void main(String[] args) {
        new Ckpt2Tests();
    }

    protected Class<?>[] getTests() {
        return new Class<?>[] {
            AVLTreeTests.class,
            HashTableTests.class,
            CircularArrayHashCodeTests.class,
            QuickSortTests.class,
            TopKSortTests.class,
            HeapSortTests.class
        };
    }
}
