package experiment;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;

//import datastructures.dictionaries.ChainingHashTable;
//import datastructures.dictionaries.MoveToFrontList;

public class ChainingHashTableComparisonTest {
    
    public static final int n = 5000;
    public static final int number = 1000;
    public static class InternalSupplier implements Supplier<MoveToFrontList<Integer, Integer>> {
        public InternalSupplier() {
            super();
        }
        
        @Override
        public MoveToFrontList<Integer, Integer> get() {
            return new MoveToFrontList<Integer, Integer>();
        }
    }
    
    
    public static void main(String[] args) {
        InternalSupplier a = new InternalSupplier();
        ChainingHashTableCopy<Integer, Integer> table = new ChainingHashTableCopy(a);
        
        InternalSupplier b = new InternalSupplier();
        ChainingHashTableChangedHashFunction<Integer, Integer> changed = new ChainingHashTableChangedHashFunction(b);
        
        sequentialTests(table, changed);
        randomTests(table, changed);
        
    }

    private static void randomTests(ChainingHashTableCopy<Integer, Integer> table, ChainingHashTableChangedHashFunction<Integer, Integer> changed) {
      System.out.println("-------------------------------------------->RANDOM<------------------------");
        long insertTimenormal = 0;
        long insertTimechanged = 0;
        long findTimechanged = 0;
        long findTimenormal = 0;
        long insertStepsnormal = 0;
        long insertStepsChanged = 0;
        long resizingCountsnormal = 0;
        long resizingCountschanged = 0;
        for(int k = 0; k < number; k ++)  {
            InternalSupplier a = new InternalSupplier();
            table = new ChainingHashTableCopy(a);
            
            InternalSupplier b = new InternalSupplier();
            changed = new ChainingHashTableChangedHashFunction(b);
            Random index = new Random();
            Set<Integer> valuesnormal = new HashSet<Integer>();
            Set<Integer> valueschanged = new HashSet<Integer>();
            
            long start = System.currentTimeMillis();       
            for(int i = 0; i < n; i ++) {
              int value =  index.nextInt() * 10000;
              //int returned = list.find(value);
                 table.insert(value, 1);
                     valuesnormal.add(value);
                                        
            }
            insertTimenormal += System.currentTimeMillis() - start;
            
            start = System.currentTimeMillis();       
            for(int i = 0; i < n; i ++) {
              int value =  index.nextInt() * 10000;
              //int returned = list.find(value);
                  changed.insert(value, 1);
                     valueschanged.add(value);
                       
            }
            insertTimechanged += System.currentTimeMillis() - start;
            resizingCountsnormal += table.returnSpaceCounter();
            resizingCountschanged += changed.returnSpaceCounter();
            
            
            insertStepsnormal += table.totalSteps();
            insertStepsChanged += changed.totalSteps();
            
            start = System.currentTimeMillis();  
            //System.out.println(valuesnormal.size());
            for(int number : valuesnormal) {
                table.find(number);
            }
            findTimenormal += System.currentTimeMillis() - start;
            
            start = System.currentTimeMillis();  
            //System.out.println(values.size());
            for(int number : valueschanged) {
                changed.find(number);
            }
            findTimechanged += System.currentTimeMillis() - start;           
    }
        System.out.println("Insert time normal = " + insertTimenormal);
        System.out.println("Insert time changed = " + insertTimechanged);
        System.out.println("Find time normal = " + findTimenormal);
        System.out.println("Find time changed = " + findTimechanged);
        System.out.println("Resizing Counts normal = " + (resizingCountsnormal/number));
        System.out.println("Resizing Counts changed = " + (resizingCountschanged/number));
        System.out.println("Insert Steps normal = " + (insertStepsnormal/number));
        System.out.println("Insert Steps changed = " + (insertStepsChanged/number)); 
        
    }

    private static void sequentialTests(ChainingHashTableCopy<Integer, Integer> table, ChainingHashTableChangedHashFunction<Integer, Integer> changed) {
        long insertTimenormal = 0;
        long insertTimechanged = 0;
        long findTimechanged = 0;
        long findTimenormal = 0;
        long insertStepsnormal = 0;
        long insertStepsChanged = 0;
        long resizingCountsnormal = 0;
        long resizingCountschanged = 0;
        for(int k = 0; k < number; k ++)  {
            InternalSupplier a = new InternalSupplier();
            table = new ChainingHashTableCopy(a);
            
            InternalSupplier b = new InternalSupplier();
            changed = new ChainingHashTableChangedHashFunction(b);
                    
            long start = System.currentTimeMillis();
            for(int i = 0; i < n; i ++) {
                table.insert(i, 1);
            }
            insertTimenormal += System.currentTimeMillis() - start;
            
            start = System.currentTimeMillis();
            for(int i = 0; i < n; i ++) {
                changed.insert(i, 1);
            }
            insertTimechanged += System.currentTimeMillis() - start;
            
            resizingCountsnormal += table.returnSpaceCounter();
            resizingCountschanged += changed.returnSpaceCounter();
            
            
            insertStepsnormal += table.totalSteps();
            insertStepsChanged += changed.totalSteps();
            /*System.out.println("Resizing counts normal = " + table.returnSpaceCounter());
            System.out.println("Resizing counts changed = " + changed.returnSpaceCounter());
            
            System.out.println("Steps for insert normal = " + table.totalSteps());
            System.out.println("Steps for insert changed = " + changed.totalSteps());
            
            */
            start = System.currentTimeMillis();
            for(int i = n - 1; i >= 0; i --) {
                table.find(i);
            }
            findTimenormal += System.currentTimeMillis() - start;
            
            start = System.currentTimeMillis();
            for(int i = n - 1; i >= 0; i --) {
                changed.find(i);
            }
            findTimechanged += System.currentTimeMillis() - start;
            
            
        }
        System.out.println("Insert time normal = " + insertTimenormal);
        System.out.println("Insert time changed = " + insertTimechanged);
        System.out.println("Find time normal = " + findTimenormal);
        System.out.println("Find time changed = " + findTimechanged);
        System.out.println("Resizing Counts normal = " + (resizingCountsnormal/number));
        System.out.println("Resizing Counts changed = " + (resizingCountschanged/number));
        System.out.println("Insert Steps normal = " + (insertStepsnormal/number));
        System.out.println("Insert Steps changed = " + (insertStepsChanged/number));      
    }
}
