package experiment;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import cse332.datastructures.containers.Item;
import cse332.datastructures.trees.BinarySearchTree;
import datastructures.dictionaries.AVLTree;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.MoveToFrontList;
import cse332.interfaces.misc.Dictionary;
import tests.TestsUtility;

public class WriteUpHashTableTests {
    public static final int n = 30;
    public static final int number = 1000;
    public static void main(String[] args) {
        //ChainingHashTable<Integer, Integer> list = new ChainingHashTable<>(() -> new MoveToFrontList<>());
        ChainingHashTable<Integer, Integer> list = new ChainingHashTable<>(() -> new MoveToFrontList<>());
        ChainingHashTable<Integer, Integer> list12 = new ChainingHashTable<>(() -> new AVLTree<Integer, Integer>());
        ChainingHashTable<Integer, Integer> list13 = new ChainingHashTable<>(() -> new BinarySearchTree<Integer, Integer>());
        specificCase(list, list12, list13);
        
        ChainingHashTable<Integer, Integer> list1 = new ChainingHashTable<>(() -> new MoveToFrontList<>());
        ChainingHashTable<Integer, Integer> list121 = new ChainingHashTable<>(() -> new AVLTree<Integer, Integer>());
        ChainingHashTable<Integer, Integer> list131 = new ChainingHashTable<>(() -> new BinarySearchTree<Integer, Integer>());
        randomTest(list1, list121, list131);
    }

    private static void specificCase(ChainingHashTable<Integer, Integer> list, ChainingHashTable<Integer, Integer> list2, ChainingHashTable<Integer, Integer> list3) {
        long insertTimeMTF = 0;
        long findTimeMTF = 0;
        long insertTimeAVL = 0;
        long findTimeAVL = 0;
        long insertTimeBST = 0;
        long findTimeBST = 0;
        for(int k = 0; k < number; k ++)  {
            list = new ChainingHashTable<>(() -> new MoveToFrontList<>());
            list2 = new ChainingHashTable<>(() -> new AVLTree<Integer, Integer>());
            list3 = new ChainingHashTable<>(() -> new BinarySearchTree<Integer, Integer>());
            // TODO Auto-generated method stub
            long start = System.currentTimeMillis();
            for(int i = 0; i < n; i ++) {
                list.insert(i, 1);
            }
            insertTimeMTF += System.currentTimeMillis() - start;
            
            start = System.currentTimeMillis();
            for(int i = 0; i < n; i ++) {
                list2.insert(i, 1);
            }
            insertTimeAVL += System.currentTimeMillis() - start;
            
            start = System.currentTimeMillis();
            for(int i = 0; i < n; i ++) {
                list3.insert(i, 1);
            }
            insertTimeBST += System.currentTimeMillis() - start;
            //System.out.println("Insert took ");
            //System.out.println(System.currentTimeMillis() - start);
            
            //System.out.println("Steps for insert" + list.totalSteps());
            //list.resetCount();
            start = System.currentTimeMillis();
            for(int i = n - 1; i >= 0; i --) {
                list.find(i);
            }
            findTimeMTF += System.currentTimeMillis() - start;
            
            start = System.currentTimeMillis();
            for(int i = n - 1; i >= 0; i --) {
                list2.find(i);
            }
            findTimeAVL += System.currentTimeMillis() - start;
            
            start = System.currentTimeMillis();
            for(int i = n - 1; i >= 0; i --) {
                list3.find(i);
            }
            findTimeBST += System.currentTimeMillis() - start;
            
        }
        System.out.println("MTF = ");
        System.out.println("Insert time1 = " + (insertTimeMTF));
        System.out.println("Find Time = " + (findTimeMTF));
        
        System.out.println("AVL = ");
        System.out.println("Insert time1 = " + (insertTimeAVL));
        System.out.println("Find Time = " + (findTimeAVL));
        
        System.out.println("BST = ");
        System.out.println("Insert time1 = " + (insertTimeBST));
        System.out.println("Find Time = " + (findTimeBST));
    }
    
    
    private static void randomTest(ChainingHashTable<Integer, Integer> list, ChainingHashTable<Integer, Integer> list2, ChainingHashTable<Integer, Integer> list3) {
        System.out.println("-------------------------------------------> Random Tests <--------------------");
        long insertTimeMTF = 0;
        long findTimeMTF = 0;
        long insertTimeAVL = 0;
        long findTimeAVL = 0;
        long insertTimeBST = 0;
        long findTimeBST = 0;
        for(int k = 0; k < number; k ++)  {
            list = new ChainingHashTable<>(() -> new MoveToFrontList<>());
            list2 = new ChainingHashTable<>(() -> new AVLTree<Integer, Integer>());
            list3 = new ChainingHashTable<>(() -> new BinarySearchTree<Integer, Integer>());
           Random index = new Random();           
           
           Set<Integer> valuesMTF = new HashSet<Integer>();
           Set<Integer> valuesAVL = new HashSet<Integer>();
           Set<Integer> valuesBST = new HashSet<Integer>();
           
           long start = System.currentTimeMillis();       
           for(int i = 0; i < n; i ++) {
             int value =  index.nextInt() * 10000;
             //int returned = list.find(value);
                if(list.insert(value, 1) == null) {
                    valuesMTF.add(value);
                }                        
           }
           insertTimeMTF += System.currentTimeMillis() - start;
           
           start = System.currentTimeMillis();       
           for(int i = 0; i < n; i ++) {
             int value =  index.nextInt() * 10000;
             //int returned = list.find(value);
                if(list2.insert(value, 1) == null) {
                    valuesAVL.add(value);
                }                        
           }
           insertTimeAVL += System.currentTimeMillis() - start;
           
           start = System.currentTimeMillis();       
           for(int i = 0; i < n; i ++) {
             int value =  index.nextInt() * 10000;
             //int returned = list.find(value);
                if(list3.insert(value, 1) == null) {
                    valuesBST.add(value);
                }                        
           }
           insertTimeBST += System.currentTimeMillis() - start;
           
           start = System.currentTimeMillis();  
           //System.out.println(values.size());
           for(int number : valuesMTF) {
               list.find(number);
           }
           findTimeMTF += System.currentTimeMillis() - start;
           
           start = System.currentTimeMillis();  
           //System.out.println(values.size());
           for(int number : valuesAVL) {
               list2.find(number);
           }
           findTimeAVL += System.currentTimeMillis() - start;
           
           start = System.currentTimeMillis();  
           //System.out.println(values.size());
           for(int number : valuesBST) {
               list3.find(number);
           }
           findTimeBST += System.currentTimeMillis() - start;
        }
        System.out.println("MTF = ");
        System.out.println("Insert time1 = " + (insertTimeMTF));
        System.out.println("Find Time = " + (findTimeMTF));
        
        System.out.println("AVL = ");
        System.out.println("Insert time1 = " + (insertTimeAVL));
        System.out.println("Find Time = " + (findTimeAVL));
        
        System.out.println("BST = ");
        System.out.println("Insert time1 = " + (insertTimeBST));
        System.out.println("Find Time = " + (findTimeBST));
    }

}