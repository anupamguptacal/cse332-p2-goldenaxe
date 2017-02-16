package experiment;

import datastructures.worklists.ArrayStack;


public class AVLTree<K extends Comparable<K>, V> extends BinarySearchTree<K, V>  {
    long steps;
    
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
        this.steps = 1;
    }
    
    public long getSteps() {
        return this.steps;
    }
    
    @Override
    public V insert(K key, V value) {
        this.steps++;
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
        this.steps++;
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
            this.steps++;
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
            int subTreeDirection = Integer.signum(key.compareTo(problemNodeParent.key));
            int subTree = Integer.signum(subTreeDirection + 1);
            problemNodeParent.children[subTree] = this.rotate(path);
        }
        return current;
    }
    
    /*private void printTree(AVLNode node) {
        System.err.print(node.key + " ");
        System.err.println(node.heightDiff);
        if (node.children[0] != null) {
            this.printTree((AVLNode)node.children[0]);
        }
        if (node.children[1] != null) {
            this.printTree((AVLNode)node.children[1]);
        }
    }*/
    
    private AVLNode rotate(ArrayStack<AVLNode> path) {
        this.steps++;
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
            child.heightDiff += direction;
            grandchild.heightDiff += direction;
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
    
    private AVLNode updateHeightDiffs(ArrayStack<AVLNode> path) {
        this.steps++;
        AVLNode parent = null;
        AVLNode child = null;
        AVLNode grandchild = null;
        while (path.size() > 1) {
            this.steps++;
            grandchild = child;
            child = path.next();
            parent = path.peek();
            if (child == parent.children[1]) {
                parent.heightDiff++;
            } else {
                parent.heightDiff--;
            }
            if (Math.abs(parent.heightDiff) == 2) {
                if (path.size() == 1) {
                    this.makeRotationPath(path, parent, child, grandchild);
                    this.root = this.rotate(path);
                    return null;
                } else {
                    path.next();
                    AVLNode parentOfProblem = path.peek();
                    this.makeRotationPath(path, parent, child, grandchild);
                    return parentOfProblem;
                }
            }
        }
        return null;
    }
    
    private boolean checkKinkCase(K edge1, K middle, K edge2) {
        this.steps++;
        return edge1.compareTo(middle) < 0 && middle.compareTo(edge2) < 0;
    }
    
    private ArrayStack<AVLNode> makeRotationPath(ArrayStack<AVLNode> path, AVLNode parent, 
                                  AVLNode child, AVLNode grandchild) {
        
        this.steps++;
        path.clear();
        path.add(parent);
        path.add(child);
        path.add(grandchild);
        return path;
    }
    
    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        AVLNode result = (AVLNode)find(key, null);
        if (result == null) {
            return null;
        }
        this.steps++;
        return result.value;
    }
    
    protected AVLNode find(K key, V value) {
        AVLNode prev = null;
        AVLNode current = (AVLNode)this.root;

        int child = -1;

        while (current != null) {
            this.steps++;
            int direction = Integer.signum(key.compareTo(current.key));

            // We found the key!
            if (direction == 0) {
                return current;
            }
            else {
                // direction + 1 = {0, 2} -> {0, 1}
                child = Integer.signum(direction + 1);
                prev = current;
                current = (AVLNode)current.children[child];
            }
        }

        // If value is not null, we need to actually add in the new value
        if (value != null) {
            current = new AVLNode(key, null);
            if (this.root == null) {
                this.root = current;
            }
            else {
                assert(child >= 0); // child should have been set in the loop
                                    // above
                prev.children[child] = current;
            }
            this.size++;
        }
        this.steps++;
        return current;
    }
}
