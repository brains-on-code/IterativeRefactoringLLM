package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;
import java.util.List;

/**
 * Leftist Heap implementation.
 */
public class Class1 {

    private static final class Class2 {
        private final int key;
        private int rank;
        private Class2 left;
        private Class2 right;

        private Class2(int key) {
            this.key = key;
            this.left = null;
            this.right = null;
            this.rank = 0;
        }
    }

    private Class2 root;

    public Class1() {
        this.root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void clear() {
        root = null;
    }

    public void merge(Class1 other) {
        root = merge(root, other.root);
        other.root = null;
    }

    public Class2 merge(Class2 h1, Class2 h2) {
        if (h1 == null) {
            return h2;
        }

        if (h2 == null) {
            return h1;
        }

        if (h1.key > h2.key) {
            Class2 temp = h1;
            h1 = h2;
            h2 = temp;
        }

        h1.right = merge(h1.right, h2);

        if (h1.left == null) {
            h1.left = h1.right;
            h1.right = null;
        } else {
            if (h1.left.rank < h1.right.rank) {
                Class2 temp = h1.left;
                h1.left = h1.right;
                h1.right = temp;
            }
            h1.rank = h1.right.rank + 1;
        }
        return h1;
    }

    public void insert(int key) {
        root = merge(new Class2(key), root);
    }

    public int deleteMin() {
        if (isEmpty()) {
            return -1;
        }

        int min = root.key;
        root = merge(root.left, root.right);
        return min;
    }

    public List<Integer> toList() {
        List<Integer> result = new ArrayList<>();
        inOrderTraversal(root, result);
        return new ArrayList<>(result);
    }

    private void inOrderTraversal(Class2 node, List<Integer> result) {
        if (node == null) {
            return;
        }
        inOrderTraversal(node.left, result);
        result.add(node.key);
        inOrderTraversal(node.right, result);
    }
}