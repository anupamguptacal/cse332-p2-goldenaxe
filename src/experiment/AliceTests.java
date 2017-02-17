package experiment;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.TreeSet;
import java.util.function.Supplier;
import cse332.misc.WordReader;
import cse332.types.AlphabeticString;
import p2.sorts.QuickSort;

public class AliceTests {

    public static void main(String[] args) throws FileNotFoundException {
        final String CORPUS = "alice.txt";
        
        class InternalSupplier implements Supplier<MoveToFrontList<String, Integer>> {
            public InternalSupplier() {
                super();
            }
            
            public MoveToFrontList<String, Integer> get() {
                return new MoveToFrontList<String, Integer>();
            }
        }
        
        InternalSupplier a = new InternalSupplier();
        BinarySearchTree<String, Integer> BST = new BinarySearchTree<String, Integer>();
        AVLTree<String, Integer> AVL = new AVLTree<String, Integer>();
        ChainingHashTableCopy<String, Integer> CHT = new ChainingHashTableCopy(a);
        HashTrieMap<Character, AlphabeticString, Integer> HTM = new HashTrieMap<>(AlphabeticString.class);
        
        String[] words = new String[27551];
        TreeSet<String> uniqueWords = new TreeSet<String>();
        WordReader reader = new WordReader(new FileReader(CORPUS));
        
        int count = 0;
        while (reader.hasNext()) {
            String word = reader.next();
            words[count] = word;
            uniqueWords.add(word);
            incCount(BST, word);
            incCount(AVL, word);
            incCount(CHT, word);
            incCount(HTM, word);
            count++;
        }

        QuickSort.sort(words);
        //for (int i = 0; i < count; i++) {
            //System.out.println(words[i] + " ");
        //}
        
        for (String word : uniqueWords) {
            System.out.println(word);
        }
        
        System.out.println(count);
        System.out.println(BST.getSteps());
        System.out.println(AVL.getSteps());
        System.out.println(CHT.getSteps());
        System.out.println(HTM.getSteps());
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
}
