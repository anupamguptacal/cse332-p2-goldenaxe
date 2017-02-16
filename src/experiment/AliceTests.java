package experiment;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.function.Supplier;

import cse332.misc.WordReader;
import cse332.types.AlphabeticString;

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
        
        WordReader reader = new WordReader(new FileReader(CORPUS));
        
        while (reader.hasNext()) {
            String word = reader.next();
            incCount(BST, word);
            incCount(AVL, word);
            incCount(CHT, word);
            incCount(HTM, word);
        }
        
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
