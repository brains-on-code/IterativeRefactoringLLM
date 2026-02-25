package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;

/**
 * Leftist Heap implementation (min-heap).
 *
 * <p>A leftist heap is a priority queue implemented as a binary tree that
 * satisfies:
 * <ul>
 *   <li>Heap property: each node's key is less than or equal to its children.</li>
 *   <li>Leftist property: for every node, the null-path length (npl) of the
 *       left child is at least as large as that of the right child.</li>
 * </ul>
 */
public class LeftistHeap {

    /** Node of the leftist heap. */
    private static final class Node {
        /** Key stored in this node. */
        private final int key;

        /** Null-path length (rank) of this node. */
        private int npl;

        /** Left child. */
        private Node left;

        /** Right child. */
        private Node right;

        private Node(int key) {
            this.key = key;
            this.left = null;
            this.right = null;
            this.npl = 0;
        }
    }

    /** Root of the heap. */
    private Node root;

    /** Constructs an empty leftist heap. */
    public LeftistHeap() {
        root = null;
    }

    /** Returns {@code true} if the heap is empty. */
    public boolean isEmpty() {
        return root == null;
    }

    /** Removes all elements from the heap. */
    public void clear() {
        root = null;
    }

    /**
     * Melds (merges) another heap into this heap.
     * The other heap will be emptied after the operation.
     *
     * @param other the heap to meld into this heap
     */
    public void meld(LeftistHeap other) {
        root = meld(root, other.root);
        other.root = null;
    }

    /**
     * Melds (merges) two heap roots and returns the new root.
     *
     * @param h1 root of the first heap
     * @param h2 root of the second heap
     * @return root of the merged heap
     */
    private Node meld(Node h1, Node h2) {
        if (h1 == null) {
            return h2;
        }

        if (h2 == null) {
            return h1;
        }

        // Ensure h1 has the smaller key so it becomes the new root.
        if (h1.key > h2.key) {
            Node temp = h1;
            h1 = h2;
            h2 = temp;
        }

        // Recursively merge h2 into the right subtree of h1.
        h1.right = meld(h1.right, h2);

        // Maintain leftist property: left child must have >= npl than right child.
        if (h1.left == null) {
            h1.left = h1.right;
            h1.right = null;
        } else {
            if (h1.right != null && h1.left.npl < h1.right.npl) {
                Node temp = h1.left;
                h1.left = h1.right;
                h1.right = temp;
            }
            h1.npl = (h1.right == null ? 0 : h1.right.npl + 1);
        }
        return h1;
    }

    /**
     * Inserts a new key into the heap.
     *
     * @param key the value to insert
     */
    public void insert(int key) {
        root = meld(new Node(key), root);
    }

    /**
     * Removes and returns the minimum key from the heap.
     *
     * @return the minimum key, or {@code -1} if the heap is empty
     */
    public int deleteMin() {
        if (isEmpty()) {
            return -1;
        }

        int min = root.key;
        root = meld(root.left, root.right);
        return min;
    }

    /**
     * Returns all keys in the heap in sorted (non-decreasing) order.
     * This does not modify the heap.
     *
     * @return a list of keys in sorted order
     */
    public ArrayList<Integer> toSortedList() {
        ArrayList<Integer> result = new ArrayList<>();
        inOrderTraversal(root, result);
        return new ArrayList<>(result);
    }

    /**
     * In-order traversal to collect keys.
     *
     * @param node   current node
     * @param result list to collect keys into
     */
    private void inOrderTraversal(Node node, ArrayList<Integer> result) {
        if (node == null) {
            return;
        }
        inOrderTraversal(node.left, result);
        result.add(node.key);
        inOrderTraversal(node.right, result);
    }
}