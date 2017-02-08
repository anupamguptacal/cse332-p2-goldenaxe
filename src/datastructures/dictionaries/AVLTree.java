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
        int child = -1;
        AVLNode problemNodeParent = null;
        
        while (current != null) {
            path.add(current);
            direction = Integer.signum(key.compareTo(current.key));
            if (direction == 0) { // Look, the key's already here
                return current;
            }
            // direction will be -1 or 1
            // direction + 1 = {0, 2} -> {0, 1}
            child = Integer.signum(direction + 1);
            current = (AVLNode)current.children[child];
        }
        
        current = new AVLNode(key, null);
        this.size++;
        AVLNode parent = path.peek();
        path.add(current);
        parent.children[child] = current;
        if (parent.children[1 - child] != null) {
            parent.heightDiff += direction;
        } else {
            problemNodeParent = this.updateHeightDiffs(path);
        }
        if (problemNodeParent != null) {
            if (problemNodeParent.key.compareTo(this.root.key) == 0) {
                this.root = this.rotate(path);
            } else {
                int subTreeDirection = Integer.signum(key.compareTo(problemNodeParent.key));
                int subTree = Integer.signum(subTreeDirection + 1);
                problemNodeParent.children[subTree] = this.rotate(path);
            }
        }
        return current;
    }
    
    private void printTree(AVLNode node) {
        System.err.print(node.key + " ");
        System.err.println(node.heightDiff);
        if (node.children[0] != null) {
            this.printTree((AVLNode)node.children[0]);
        }
        if (node.children[1] != null) {
            this.printTree((AVLNode)node.children[1]);
        }
    }
    
    private AVLNode rotate(ArrayStack<AVLNode> path) {
        AVLNode grandchild = path.next();
        AVLNode child = path.next();
        AVLNode parent = path.next();
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
        // if direction == -1, child.heightDiff++; and parent.heightDiff + 2
        // if direction == 1, child.heightDiff--; and parent.heightDiff - 2
        child.heightDiff += (direction * -1);
        parent.heightDiff += (direction * -2);
        return child;
    }
    
    // need to fix this to reflect +/-
    private AVLNode updateHeightDiffs(ArrayStack<AVLNode> path) {
        AVLNode parent = null;
        AVLNode child = null;
        AVLNode grandchild = null;
        while (path.size() > 1) {
            grandchild = child;
            child = path.next();
            parent = path.peek();
            if (child == parent.children[1]) {
                parent.heightDiff++;
            } else {
                parent.heightDiff--;
            }
            if (Math.abs(parent.heightDiff) == 2) {
                path.next();
                AVLNode parentOfProblem = (AVLNode)this.root;
                if (path.size() > 0) {
                    parentOfProblem = path.peek();
                }
                path.clear();
                path.add(parent);
                path.add(child);
                path.add(grandchild);
                return parentOfProblem;
            }
        }
        return null;
    }
    
    private boolean checkKinkCase(K edge1, K middle, K edge2) {
        return edge1.compareTo(middle) < 0 && middle.compareTo(edge2) < 0;
    }
}
