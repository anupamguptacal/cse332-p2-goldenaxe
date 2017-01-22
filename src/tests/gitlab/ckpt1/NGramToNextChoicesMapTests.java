package tests.gitlab.ckpt1;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.Dictionary;
import cse332.types.AlphabeticString;
import cse332.types.NGram;
import cse332.datastructures.trees.BinarySearchTree;
import p2.wordsuggestor.NGramToNextChoicesMap;
import tests.TestsUtility;

public class NGramToNextChoicesMapTests extends TestsUtility {
    private static Supplier<Dictionary<NGram, Dictionary<AlphabeticString, Integer>>> newOuter =
            () -> new BinarySearchTree();

    private static Supplier<Dictionary<AlphabeticString, Integer>> newInner =
            () -> new BinarySearchTree();
    
    public static void main(String[] args) {
        new NGramToNextChoicesMapTests().run();
    }
    
    @Override
    protected void run() {
        SHOW_TESTS = true;
        PRINT_TESTERR = true;
        DEBUG = true;

        test("testOneWordPerNGram");
        test("testMultipleWordsPerNGram");
        test("testGetNonexistentNGram");
        test("testRepeatedWordsPerNGram");
        finish();   
    }
    
    protected static NGramToNextChoicesMap init() {
        return new NGramToNextChoicesMap(newOuter, newInner);
    }
    
    public static int testOneWordPerNGram() {
        NGramToNextChoicesMap map = init();
        NGram[] ngrams = new NGram[]{
                new NGram(new String[]{"foo", "bar", "baz"}),
                new NGram(new String[]{"fee", "fi", "fo"}),
                new NGram(new String[]{"a", "s", "d"})
        };
        
        String[] words = new String[]{"bop", "fum", "f"};
        for (int i = 0; i < ngrams.length; i++) {
            map.seenWordAfterNGram(ngrams[i], words[i]);
        }
        for (int i = 0; i < ngrams.length; i++) {
            Item<String, Integer>[] items = map.getCountsAfter(ngrams[i]);
            if (items.length != 1) return 0;
            Item<String, Integer> item = items[0];
            if (!item.key.equals(words[i])) return 0;
            if (!item.value.equals(1)) return 0;
        }
        
        return 1;
    }
    
    public static int testMultipleWordsPerNGram() {
        NGramToNextChoicesMap map = init();
        NGram[] ngrams = new NGram[]{
                new NGram(new String[]{"foo", "bar", "baz"}),
                new NGram(new String[]{"fee", "fi", "fo"}),
                new NGram(new String[]{"four", "score", "and"}),
                new NGram(new String[]{"3", "2", "2"}),
                new NGram(new String[]{"a", "s", "d"})
        };
        
        String[][] words = new String[][] {
            new String[]{"bip", "bop", "bzp"},
            new String[]{"fum", "giants"},
            new String[]{"ago", "seven", "years"},
            new String[]{"new", "thrown", "uuu", "zzz"},
            new String[]{"do", "for", "while"}
        };
        
        for (int i = 0; i < ngrams.length; i++) {
            for (int j = 0; j < words[i].length; j++) {
                map.seenWordAfterNGram(ngrams[i], words[i][j]);
            }
            
        }
        for (int i = 0; i < ngrams.length; i++) {
            Item<String, Integer>[] items = map.getCountsAfter(ngrams[i]);
            String[] answer = words[i];
            if (items.length != answer.length) return 0;
            String[] itemsWithoutCounts = new String[items.length];
            for (int j = 0; j < answer.length; j++) {
                if (items[j].value != 1) return 0;
                itemsWithoutCounts[j] = items[j].key;
            }
            Arrays.sort(itemsWithoutCounts);
            for (int j = 0; j < answer.length; j++) {
                if (!itemsWithoutCounts[j].equals(answer[j])) return 0;
            }
        }
        
        return 1;
    }
    
    public static int testGetNonexistentNGram() {
        NGramToNextChoicesMap map = init();
        NGram[] ngrams = new NGram[]{
                new NGram(new String[]{"foo", "bar", "baz"}),
                new NGram(new String[]{"fee", "fi", "fo"}),
                new NGram(new String[]{"a", "s", "d"})
        };
        
        String[] words = new String[]{"bop", "fum", "f"};
        for (int i = 0; i < ngrams.length; i++) {
            map.seenWordAfterNGram(ngrams[i], words[i]);
        }
        Item<String, Integer>[] items = map.getCountsAfter(new NGram(new String[] { "yo" }));
        if (items == null || items.length != 0) {
            return 0;
        }
        
        return 1;
    }
    
    // TODO: Not finished yet
    @SuppressWarnings("unchecked")
    public static int testRepeatedWordsPerNGram() {
        NGramToNextChoicesMap map = init();
        NGram[] ngrams = new NGram[]{
                new NGram(new String[]{"foo", "bar", "baz"}),
                new NGram(new String[]{"fee", "fi", "fo"}),
                new NGram(new String[]{"four", "score", "and"}),
                new NGram(new String[]{"3", "2", "2"}),
                new NGram(new String[]{"a", "s", "d"})
        };
        
        String[][] words = new String[][] {
            new String[]{"bop", "bip", "boop", "bop", "bop"},
            new String[]{"fum", "giants", "giants"},
            new String[]{"seven", "years", "years", "ago", "ago"},
            new String[]{"throw", "throw", "throw", "throw", "throw"},
            new String[]{"for", "while", "do", "do", "while", "for"}
        };
        
        // yes this is awful, but i can't think of a better way to do it atm
        Map<NGram, Item<String, Integer>[]> answers = new HashMap<>();
        answers.put(ngrams[0], (Item<String, Integer>[]) new Item[3]);
        answers.get(ngrams[0])[0] = new Item<String, Integer>("bip", 1);
        answers.get(ngrams[0])[1] = new Item<String, Integer>("boop", 1);
        answers.get(ngrams[0])[2] = new Item<String, Integer>("bop", 3);
        answers.put(ngrams[1], (Item<String, Integer>[]) new Item[2]);
        answers.get(ngrams[1])[0] = new Item<String, Integer>("fum", 1);
        answers.get(ngrams[1])[1] = new Item<String, Integer>("giants", 2);
        answers.put(ngrams[2], (Item<String, Integer>[]) new Item[3]);
        answers.get(ngrams[2])[0] = new Item<String, Integer>("ago", 2);
        answers.get(ngrams[2])[1] = new Item<String, Integer>("seven", 1);
        answers.get(ngrams[2])[2] = new Item<String, Integer>("years", 2);
        answers.put(ngrams[3], (Item<String, Integer>[]) new Item[1]);
        answers.get(ngrams[3])[0] = new Item<String, Integer>("throw", 5);
        answers.put(ngrams[4], (Item<String, Integer>[]) new Item[3]);
        answers.get(ngrams[4])[0] = new Item<String, Integer>("do", 2);
        answers.get(ngrams[4])[1] = new Item<String, Integer>("for", 2);
        answers.get(ngrams[4])[2] = new Item<String, Integer>("while", 2);
        
        for (int i = 0; i < ngrams.length; i++) {
            for (int j = 0; j < words[i].length; j++) {
                map.seenWordAfterNGram(ngrams[i], words[i][j]);
            }
            
        }
        for (int i = 0; i < ngrams.length; i++) {
            NGram ngram = ngrams[i];
            Item<String, Integer>[] results = map.getCountsAfter(ngram);
            Arrays.sort(results, new Comparator<Object>() {

                @Override
                public int compare(Object o1, Object o2) {
                    Item<String, Integer> r1 = (Item<String, Integer>)o1;
                    Item<String, Integer> r2 = (Item<String, Integer>)o2;
                    return r1.key.compareTo(r2.key); 
                }
                
            });
            Item<String, Integer>[] expected = answers.get(ngram);
            if (results.length != expected.length) return 0;
            for (int j = 0; j < expected.length; j++) {
                if (!expected[j].key.equals(results[j].key)) {
                    return 0;
                }
                if (expected[j].value != results[j].value) {
                    return 0;
                }
            }
        }
        return 1;
    }
    

}
