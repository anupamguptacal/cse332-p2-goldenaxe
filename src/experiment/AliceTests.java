package experiment;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;
import java.util.function.Supplier;
import cse332.misc.WordReader;
import cse332.types.AlphabeticString;
import p2.sorts.QuickSort;

public class AliceTests {
    public static final int ALICE_SIZE = 18000;
    public static final int NUM_TESTS = 500;

    public static void main(String[] args) throws FileNotFoundException {
        final String CORPUS = "alice.txt";
        
        ArrayList<Long> BSTresults = new ArrayList<Long>();
        ArrayList<Long> AVLresults = new ArrayList<Long>();
        ArrayList<Long> CHTresults = new ArrayList<Long>();
        ArrayList<Long> HTMresults = new ArrayList<Long>();
        
        String[] words = new String[ALICE_SIZE];
        WordReader reader = new WordReader(new FileReader(CORPUS));
        
        int count = 0;
        while (reader.hasNext()) {
            String word = reader.next();
            words[count] = word;
            count++;
            if (count >= ALICE_SIZE) {
                break;
            }
        }
        
        randomTests(words, BSTresults, AVLresults, CHTresults, HTMresults);
        
        long BSTavgSteps = average(BSTresults);
        long AVLavgSteps = average(AVLresults);
        long CHTavgSteps = average(CHTresults);
        long HTMavgSteps = average(HTMresults);
        
        System.out.println("Random Entry Test:");
        System.out.println("Average num of BST steps: " + BSTavgSteps);
        System.out.println("Average num of AVL steps: " + AVLavgSteps);
        System.out.println("Average num of CHT steps: " + CHTavgSteps);
        System.out.println("Average num of HTM steps: " + HTMavgSteps);
        
        BSTresults.clear();
        AVLresults.clear();
        CHTresults.clear();
        HTMresults.clear();

        QuickSort.sort(words);
        minMaxTest(words, BSTresults, AVLresults, CHTresults, HTMresults);
        
        BSTavgSteps = average(BSTresults);
        AVLavgSteps = average(AVLresults);
        CHTavgSteps = average(CHTresults);
        HTMavgSteps = average(HTMresults);
        
        System.out.println("Somewhat Random Entry Test:");
        System.out.println("Average num of BST steps: " + BSTavgSteps);
        System.out.println("Average num of AVL steps: " + AVLavgSteps);
        System.out.println("Average num of CHT steps: " + CHTavgSteps);
        System.out.println("Average num of HTM steps: " + HTMavgSteps);
        
        BSTresults.clear();
        AVLresults.clear();
        CHTresults.clear();
        HTMresults.clear();
        
        sequentialTest(words, BSTresults, AVLresults, CHTresults, HTMresults);
        
        System.out.println("Sequential Entry Test:");
        System.out.println("Num of BST steps: " + BSTresults.get(0));
        System.out.println("Num of AVL steps: " + AVLresults.get(0));
        System.out.println("Num of CHT steps: " + CHTresults.get(0));
        System.out.println("Num of HTM steps: " + HTMresults.get(0));
    }
    
    public static void randomTests(String[] words, ArrayList<Long> BSTresults, ArrayList<Long> AVLresults, 
                                   ArrayList<Long> CHTresults, ArrayList<Long> HTMresults) {
        
        class InternalSupplier implements Supplier<MoveToFrontList<String, Integer>> {
            public InternalSupplier() {
                super();
            }
            
            public MoveToFrontList<String, Integer> get() {
                return new MoveToFrontList<String, Integer>();
            }
        }
        
        InternalSupplier a = new InternalSupplier();
        Random rand = new Random();
        int index = 0;
        String word = "";
        
        for (int i = 0; i < NUM_TESTS; i++) {
            BinarySearchTree<String, Integer> BST = new BinarySearchTree<String, Integer>();
            AVLTree<String, Integer> AVL = new AVLTree<String, Integer>();
            ChainingHashTableCopy<String, Integer> CHT = new ChainingHashTableCopy(a);
            HashTrieMap<Character, AlphabeticString, Integer> HTM = new HashTrieMap<>(AlphabeticString.class);
            for (int j = 0; j < ALICE_SIZE; j++) {
                index = rand.nextInt(ALICE_SIZE);
                word = words[index];
                incCount(BST, word);
                incCount(AVL, word);
                incCount(CHT, word);
                incCount(HTM, word);
            }
            BSTresults.add(BST.getSteps());
            AVLresults.add(AVL.getSteps());
            CHTresults.add(CHT.getSteps());
            HTMresults.add(HTM.getSteps());
        }
    }
    
    public static void minMaxTest(String[] words, ArrayList<Long> BSTresults, ArrayList<Long> AVLresults,
                                  ArrayList<Long> CHTresults, ArrayList<Long> HTMresults) {
        
        class InternalSupplier implements Supplier<MoveToFrontList<String, Integer>> {
            public InternalSupplier() {
                super();
            }
            
            public MoveToFrontList<String, Integer> get() {
                return new MoveToFrontList<String, Integer>();
            }
        }
        
        InternalSupplier a = new InternalSupplier();
        Random rand = new Random();
        int index = 0;
        String word = "";
        
        for (int i = 0; i < NUM_TESTS; i++) {
            BinarySearchTree<String, Integer> BST = new BinarySearchTree<String, Integer>();
            AVLTree<String, Integer> AVL = new AVLTree<String, Integer>();
            ChainingHashTableCopy<String, Integer> CHT = new ChainingHashTableCopy(a);
            HashTrieMap<Character, AlphabeticString, Integer> HTM = new HashTrieMap<>(AlphabeticString.class);
            for (int j = 0; j < ALICE_SIZE; j++) {
                index = rand.nextInt(ALICE_SIZE);
                if (j % 2 == 0) {
                    index = Math.min(j, index);
                } else {
                    index = Math.max(j, index);
                }
                word = words[index];
                incCount(BST, word);
                incCount(AVL, word);
                incCount(CHT, word);
                incCount(HTM, word);
            }
            BSTresults.add(BST.getSteps());
            AVLresults.add(AVL.getSteps());
            CHTresults.add(CHT.getSteps());
            HTMresults.add(HTM.getSteps());
        }
    }
    
    public static void sequentialTest(String[] words, ArrayList<Long> BSTresults, ArrayList<Long> AVLresults,
                                      ArrayList<Long> CHTresults, ArrayList<Long> HTMresults) {
        
        class InternalSupplier implements Supplier<MoveToFrontList<String, Integer>> {
            public InternalSupplier() {
                super();
            }
            
            public MoveToFrontList<String, Integer> get() {
                return new MoveToFrontList<String, Integer>();
            }
        }
        
        InternalSupplier a = new InternalSupplier();
        String word = "";
        
        BinarySearchTree<String, Integer> BST = new BinarySearchTree<String, Integer>();
        AVLTree<String, Integer> AVL = new AVLTree<String, Integer>();
        ChainingHashTableCopy<String, Integer> CHT = new ChainingHashTableCopy(a);
        HashTrieMap<Character, AlphabeticString, Integer> HTM = new HashTrieMap<>(AlphabeticString.class);
        
        for (int j = 0; j < ALICE_SIZE; j++) {
            word = words[j];
            incCount(BST, word);
            incCount(AVL, word);
            incCount(CHT, word);
            incCount(HTM, word);
        }
        
        BSTresults.add(BST.getSteps());
        AVLresults.add(AVL.getSteps());
        CHTresults.add(CHT.getSteps());
        HTMresults.add(HTM.getSteps());
    }

    private static void incCount(HashTrieMap<Character, AlphabeticString, Integer> dict,
            String key) {
        
        AlphabeticString al = new AlphabeticString(key);
        Integer value = dict.find(al);
        if (value == null) {
            dict.insert(al, 1);
        } else {
            dict.insert(al, value + 1);
        }
    }

    private static void incCount(Dictionary<String, Integer> dict, String key) {
        Integer value = dict.find(key);
        if (value == null) {
            dict.insert(key, 1);
        } else {
            dict.insert(key, value + 1);
        }
    }
    
    public static long average(ArrayList<Long> list) {
        long avg = 0;
        for (int i = 0; i < list.size(); i++) {
            avg += list.get(i);
        }
        return avg/list.size();
    }
}
