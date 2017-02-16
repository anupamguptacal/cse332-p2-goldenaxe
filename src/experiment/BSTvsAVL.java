package experiment;

import java.util.ArrayList;
import java.util.Random;

public class BSTvsAVL {
    private static final int DATA_SIZE = 50000;
    private static final int NUM_OF_TESTS = 1000;

    public static void main(String[] args) {
        ArrayList<Long> BSTresults = new ArrayList<Long>();
        ArrayList<Long> AVLresults = new ArrayList<Long>();
        randomNumsTest(BSTresults, AVLresults);
        long BSTavgSteps = average(BSTresults);
        long AVLavgSteps = average(AVLresults);
        
        System.out.println("Random Nums Test:");
        System.out.println("Average num of BST steps: " + BSTavgSteps);
        System.out.println("Average num of AVL steps: " + AVLavgSteps);
        
        BSTresults.clear();
        AVLresults.clear();
        
        minMaxTest(BSTresults, AVLresults);
        BSTavgSteps = average(BSTresults);
        AVLavgSteps = average(AVLresults);
        
        System.out.println("Somewhat Random Nums Test:");
        System.out.println("Num of BST steps: " + BSTavgSteps);
        System.out.println("Num of AVL steps: " + AVLavgSteps);
        
        BSTresults.clear();
        AVLresults.clear();
        
        numSequenceTest();
        // results printed from method
        
    }
    
    public static long average(ArrayList<Long> list) {
        long avg = 0;
        for (int i = 0; i < list.size(); i++) {
            avg += list.get(i);
        }
        return avg/list.size();
    }
    
    public static void randomNumsTest(ArrayList<Long> BSTresults, ArrayList<Long> AVLresults) {
        for (int i = 0; i <= NUM_OF_TESTS; i++) {
            BinarySearchTree<Integer, Integer> BST = new BinarySearchTree<Integer, Integer>();
            AVLTree<Integer, Integer> AVL = new AVLTree<Integer, Integer>();
            Random rand = new Random();
            for (int j = 0; j <= DATA_SIZE; j++) {
                int randomNum = rand.nextInt(DATA_SIZE);
                incCount(BST, randomNum);
                incCount(AVL, randomNum);
            }
            BSTresults.add(BST.getSteps());
            AVLresults.add(AVL.getSteps());
        }
    }
    
    public static void minMaxTest(ArrayList<Long> BSTresults, ArrayList<Long> AVLresults) {
        for (int i = 0; i <= NUM_OF_TESTS; i++) {
            BinarySearchTree<Integer, Integer> BST = new BinarySearchTree<Integer, Integer>();
            AVLTree<Integer, Integer> AVL = new AVLTree<Integer, Integer>();
            Random rand = new Random();
            int num;
            for (int j = 0; j <= DATA_SIZE; j++) {
                int randomNum = rand.nextInt(DATA_SIZE);
                if (j % 2 == 0) {
                    num = Math.min(j, randomNum);
                } else {
                    num = Math.max(j, randomNum);
                }
                incCount(BST, num);
                incCount(AVL, num);
            }
            BSTresults.add(BST.getSteps());
            AVLresults.add(AVL.getSteps());
        }
    }
    
    public static void numSequenceTest() {
        // No need to take the average of many trials, because this gets the same result every time
        BinarySearchTree<Integer, Integer> BST = new BinarySearchTree<Integer, Integer>();
        AVLTree<Integer, Integer> AVL = new AVLTree<Integer, Integer>();
        for (int j = 0; j <= DATA_SIZE; j++) {
            incCount(BST, j);
            incCount(AVL, j);
        }
        System.out.println("Num Sequence Test:");
        System.out.println("Num of BST steps: " + BST.getSteps());
        System.out.println("Num of AVL steps: " + AVL.getSteps());
    }
    
    private static void incCount(Dictionary<Integer, Integer> tree, int key) {
        Integer value = tree.find(key);
        if (value == null) {
            tree.insert(key, 1);
        } else {
            tree.insert(key, value + 1);
        }
    }
}
