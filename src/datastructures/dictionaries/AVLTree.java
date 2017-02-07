package datastructures.dictionaries;

import cse332.datastructures.trees.BinarySearchTree;
import cse332.datastructures.trees.BinarySearchTree.BSTNode;
import datastructures.worklists.ArrayStack;

/**
 * TODO: Replace this comment with your own as appropriate.
 *
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
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
 *    private methods that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 */

public class AVLTree<K extends Comparable<K>, V> extends BinarySearchTree<K, V>  {
    
    private class AVLNode extends BSTNode {
        private int heightDiff;

        public AVLNode(K key, V value) {
            super(key, value);
            this.heightDiff = 0;
            // Where do we want to cast the children to AVLNodes?
        } 
    }
    
    public AVLTree() {
        super();
    }
    
    // need to add rotation method for fixing tree after insertion
    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        // here is where we need to make our own find
        AVLNode current = this.findForInsert(key);
        V oldValue = current.value;
        current.value = value;
        return oldValue;
    }
    
    private AVLNode findForInsert(K key) {
        ArrayStack<AVLNode> path = new ArrayStack<AVLNode>();
        if (this.root == null) {
            this.root = new AVLNode(key, null);
            this.size++;
            return (AVLNode)this.root;
        }
        AVLNode current = (AVLNode)this.root;
        int direction = 0;
        AVLNode prev = null;
        int child = -1;
        AVLNode problemNode = null;
        while (current != null) {
            direction = Integer.signum(key.compareTo(current.key));
            if (direction == 0) {
                this.updateHeightDiffs(path, prev);
                return current;
            }
            path.add(current);
            // direction will be -1 or 1
            // direction + 1 = {0, 2} -> {0, 1}
            child = Integer.signum(direction + 1);
            if (current.heightDiff == 1) {
                problemNode = current;
            }
            current = (AVLNode)current.children[child];
        }
        current = new AVLNode(key, null);
        this.size++;
        if (problemNode == null) {
            this.updateHeightDiffs(path, prev);
        } else { // Now we know we have to rotate
            problemNode = this.rotate(problemNode, path);
        }
        return current;
    }
    
    private AVLNode rotate(AVLNode problemNode, ArrayStack<AVLNode> path) {
        AVLNode grandchild = path.next();
        AVLNode child = path.next();
        AVLNode parent = path.next();
        while (parent != problemNode) {
            grandchild = child;
            child = parent;
            parent = path.next();
        }
        K first = parent.key;
        K second = child.key;
        K third = grandchild.key;
        int direction = Integer.signum(third.compareTo(first));
        // direction + 1 = {0, 2} -> {0, 1}
        int side = Integer.signum(direction + 1);
        AVLNode remainder = null;
        // First, we account for possible kink case
        if (checkKinkCase(first, third, second) || checkKinkCase(second, third, first)) {
            parent.children[side] = grandchild;
            remainder = (AVLNode)grandchild.children[side];
            child.children[1 - side] = remainder;
            grandchild.children[side] = child;
            AVLNode temp = child;
            child = grandchild;
            grandchild = temp;
            child.heightDiff++;
            grandchild.heightDiff--;
        }
        // Now we know for sure we have the straight case
        remainder = (AVLNode)child.children[1 - side];
        parent.children[side] = remainder;
        child.children[1 - side] = parent;
        child.heightDiff--;
        parent.heightDiff = parent.heightDiff - 2;
        return child;
    }
    
    private void updateHeightDiffs(ArrayStack<AVLNode> path, AVLNode prev) {
        while (path.size() != 0) {
            prev = path.next();
            prev.heightDiff++;
        }
    }
    
    private boolean checkKinkCase(K edge1, K middle, K edge2) {
        return edge1.compareTo(middle) < 0 && middle.compareTo(edge2) < 0;
    }
    
    // Note to self: need to make sure that key != null when calling
    // find(key, value) because it doesn't throw an exception for that
    
    // Do we want to check the height starting from the root or starting
    // from the node we just inserted? We could have O(n)?? time and save
    // n*sizeof(AVLNode) space or we could have O(lgn) time and store the
    // parent pointer in each node...
    // Is the former O(n) though? Because if we know the key of the node
    // we just inserted, we don't have to check the whole tree. We can
    // follow the path down using compareTo...
    
    // Idea: what if we make a different find that will stop at (and return)
    // the node where we have to rotate from, and then just do a regular BST
    // find from there?
    
    
}
