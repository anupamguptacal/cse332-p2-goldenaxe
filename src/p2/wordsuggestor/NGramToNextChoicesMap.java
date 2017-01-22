package p2.wordsuggestor;

import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Supplier;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.Dictionary;
import cse332.misc.LargeValueFirstItemComparator;
import cse332.sorts.InsertionSort;
import cse332.types.AlphabeticString;
import cse332.types.NGram;

public class NGramToNextChoicesMap {
    private final Dictionary<NGram, Dictionary<AlphabeticString, Integer>> map;
    private final Supplier<Dictionary<AlphabeticString, Integer>> newInner;

    public NGramToNextChoicesMap(
            Supplier<Dictionary<NGram, Dictionary<AlphabeticString, Integer>>> newOuter,
            Supplier<Dictionary<AlphabeticString, Integer>> newInner) {
        this.map = newOuter.get();
        this.newInner = newInner;
    }

    /**
     * Increments the count of word after the particular NGram ngram.
     */
    public void seenWordAfterNGram(NGram ngram, String word) {
        if (ngram == null || word == null) {
            throw new IllegalArgumentException();
        }
        if (this.map.find(ngram) == null) {
            this.map.insert(ngram, this.newInner.get());
        }
        AlphabeticString seenWord = new AlphabeticString(word);
        Dictionary<AlphabeticString, Integer> ngramCounts = this.map.find(ngram);
        if (ngramCounts.find(seenWord) == null) {
            ngramCounts.insert(seenWord, 1);
        } else {
            int newCount = ngramCounts.find(seenWord) + 1;
            ngramCounts.insert(seenWord, newCount);
        }
    }

    /**
     * Returns an array of the DataCounts for this particular ngram. Order is
     * not specified.
     *
     * @param ngram
     *            the ngram we want the counts for
     * 
     * @return An array of all the Items for the requested ngram.
     */
    public Item<String, Integer>[] getCountsAfter(NGram ngram) {
        if(ngram == null) {
            throw new NullPointerException();
        }
       
        Dictionary<AlphabeticString, Integer> ngramCounts = this.map.find(ngram);
        Item<String, Integer>[] array = (Item<String, Integer>[])new Item[ngramCounts.size()];
        Iterator<Item<AlphabeticString, Integer>> iteratorPhase = ngramCounts.iterator();
        int i = 0;
        while(iteratorPhase.hasNext()) {
            Item<AlphabeticString, Integer> point = iteratorPhase.next();
            String passed = point.key.toString();
            Item<String, Integer> puruse= new Item<String, Integer>(passed, point.value);            
            array[i++] = puruse;           
        }
        return array;
    }

    public String[] getWordsAfter(NGram ngram, int k) {
        Item<String, Integer>[] afterNGrams = getCountsAfter(ngram);

        Comparator<Item<String, Integer>> comp = new LargeValueFirstItemComparator<String, Integer>();
        if (k < 0) {
            InsertionSort.sort(afterNGrams, comp);
        }
        else {
            // You must fix this line toward the end of the project
            throw new NotYetImplementedException();
        }

        String[] nextWords = new String[k < 0 ? afterNGrams.length : k];
        for (int l = 0; l < afterNGrams.length && l < nextWords.length
                && afterNGrams[l] != null; l++) {
            nextWords[l] = afterNGrams[l].key;
        }
        return nextWords;
    }

    @Override
    public String toString() {
        return this.map.toString();
    }
}
