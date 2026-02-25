package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;

/**
 * Leftist Heap implementation.
 *
 * <p>A leftist heap is a priority queue implemented as a binary tree that
 * satisfies:
 * <ul>
 *   <li>Heap property: each node's key is less than or equal to its children.</li>
 *   <li>Leftist property: for every node, the null-path length (npl) of the
 *       left child is at least as large as that of the right child.</li>
 * </ul>
 *
 * <p>This implementation is a min-heap.
 */
public class Class1 {

    /**
     * Node of the leftist heap.
     */
    private static final class Class2 {
        /** Key stored in this node. */
        private final int key;

        /** Null-path length (rank) of this node. */
        private int npl;

        /** Left child. */
        private Class2 left;

        /** Right child. */
        private Class2 right;

        private Class2(int key) {
            this.key = key;
            this.left = null;
            this.right = null;
            this.npl = 0;
        }
    }

    /** Root of the heap. */
    private Class2 root;

    /** Constructs an empty leftist heap. */
    public Class1() {
        root = null;
    }

    /** Returns {@code true} if the heap is empty. */
    public boolean method1() {
        return root == null;
    }

    /** Removes all elements from the heap. */
    public void method2() {
        root = null;
    }

    /**
     * Melds (merges) another heap into this heap.
     * The other heap will be emptied after the operation.
     *
     * @param other the heap to meld into this heap
     */
    public void method4(Class1 other) {
        root = method4(root, other.root);
        other.root = null;
    }

    /**
     * Melds (merges) two heap roots and returns the new root.
     *
     * @param h1 root of the first heap
     * @param h2 root of the second heap
     * @return root of the merged heap
     */
    public Class2 method4(Class2 h1, Class2 h2) {
        if (h1 == null) {
            return h2;
        }

        if (h2 == null) {
            return h1;
        }

        // Ensure h1 has the smaller key so it becomes the new root.
        if (h1.key > h2.key) {
            Class2 temp = h1;
            h1 = h2;
            h2 = temp;
        }

        // Recursively merge h2 into the right subtree of h1.
        h1.right = method4(h1.right, h2);

        // Maintain leftist property: left child must have >= npl than right child.
        if (h1.left == null) {
            h1.left = h1.right;
            h1.right = null;
        } else {
            if (h1.left.npl < h1.right.npl) {
                Class2 temp = h1.left;
                h1.left = h1.right;
                h1.right = temp;
            }
            h1.npl = h1.right.npl + 1;
        }
        return h1;
    }

    /**
     * Inserts a new key into the heap.
     *
     * @param key the value to insert
     */
    public void method5(int key) {
        root = method4(new Class2(key), root);
    }

    /**
     * Removes and returns the minimum key from the heap.
     *
     * @return the minimum key, or {@code -1} if the heap is empty
     */
    public int method6() {
        if (method1()) {
            return -1;
        }

        int min = root.key;
        root = method4(root.left, root.right);
        return min;
    }

    /**
     * Returns all keys in the heap in sorted (non-decreasing) order.
     * This does not modify the heap.
     *
     * @return a list of keys in sorted order
     */
    public ArrayList<Integer> method7() {
        ArrayList<Integer> result = new ArrayList<>();
        method8(root, result);
        return new ArrayList<>(result);
    }

    /**
     * In-order traversal to collect keys.
     *
     * @param node   current node
     * @param result list to collect keys into
     */
    private void method8(Class2 node, ArrayList<Integer> result) {
        if (node == null) {
            return;
        }
        method8(node.left, result);
        result.add(node.key);
        method8(node.right, result);
    }
}