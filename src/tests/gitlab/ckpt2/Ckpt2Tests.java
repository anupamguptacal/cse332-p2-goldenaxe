package tests.gitlab.ckpt2;

import tests.GradingUtility;
import tests.gitlab.ckpt1.MoveToFrontListTests;

public class Ckpt2Tests extends GradingUtility {
    public static void main(String[] args) {
        new Ckpt2Tests();
    }

    protected Class<?>[] getTests() {
        return new Class<?>[] {
            AVLTreeTests.class,
            HashTableTests.class,
            CircularArrayHashCodeTests.class
        };
    }
}
