package datastructures.dictionaries;

import cse332.datastructures.trees.BinarySearchTree;

/**
 * TODO: Replace this comment with your own as appropriate.
 *
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and callst o superclass methodst o avoid unnecessary
 * duplication or copying of functionality.
 *
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 *    instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 *    children array or left and right fields in AVLNode.  This will 
 *    instead mask the super-class fields (i.e., the resulting node 
 *    would actually have multiple copies of the node fields, with 
 *    code accessing one pair or the other depending on the type of 
 *    the references used to access the instance).  Such masking will 
 *    lead to highly perplexing and erroneous behavior. Instead, 
 *    continue using the existing BSTNode children array.
 * 4. If this class has redundant methods, your score will be heavily
 *    penalized.
 * 5. Cast children array to AVLNode whenever necessary in your
 *    AVLTree. This will result a lot of casts, so we recommend you make
 *    private methodst hat encapsulatet hose casts.
 * 6. Do NOT override the toString method. It is used for grading.
 */

public class AVLTree<K extends Comparable<K>, V> extends BinarySearchTree<K, V>  {
    // TODO: Implement me!
}
