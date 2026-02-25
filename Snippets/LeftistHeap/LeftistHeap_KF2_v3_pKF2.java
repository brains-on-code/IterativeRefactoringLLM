package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;
import java.util.List;

/**
 * A min leftist heap implementation.
 *
 * <p>A leftist heap is a priority queue implemented as a binary tree that
 * satisfies:
 * <ul>
 *   <li>Heap property: each node's key is less than or equal to its children.</li>
 *   <li>Leftist property: for every node, the null-path length (npl) of the
 *       left child is at least that of the right child.</li>
 * </ul>
 *
 * <p>The null-path length (npl) of a node is the length of the shortest path
 * from that node to a node that has at least one null child. A null child has
 * npl = -1 by convention, so a leaf has npl = 0.
 */
public class LeftistHeap {

    /**
     * Node of a leftist heap.
     *
     * <p>Fields:
     * <ul>
     *   <li>{@code element}: stored key</li>
     *   <li>{@code npl}: null-path length</li>
     *   <li>{@code left}, {@code right}: children</li>
     * </ul>
     */
    private static final class Node {
        private final int element;
        private int npl;
        private Node left;
        private Node right;

        private Node(int element) {
            this.element = element;
            this.npl = 0;
        }
    }

    private Node root;

    public LeftistHeap() {
        this.root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void clear() {
        root = null;
    }

    /**
     * Merges another heap into this heap.
     *
     * <p>After merging, the {@code other} heap becomes empty.
     *
     * @param other the heap to merge into this heap
     */
    public void merge(LeftistHeap other) {
        if (other == null || other.root == null) {
            return;
        }
        root = merge(root, other.root);
        other.root = null;
    }

    /**
     * Merges two heap roots and returns the new root.
     *
     * <p>Both {@code a} and {@code b} must be roots of valid leftist heaps.
     *
     * @param a root of the first heap
     * @param b root of the second heap
     * @return root of the merged heap
     */
    private Node merge(Node a, Node b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }

        if (a.element > b.element) {
            Node temp = a;
            a = b;
            b = temp;
        }

        a.right = merge(a.right, b);

        if (a.left == null) {
            a.left = a.right;
            a.right = null;
            a.npl = 0;
        } else {
            if (a.right != null && a.left.npl < a.right.npl) {
                Node temp = a.left;
                a.left = a.right;
                a.right = temp;
            }
            a.npl = (a.right == null) ? 0 : a.right.npl + 1;
        }

        return a;
    }

    /**
     * Inserts a value into the heap.
     *
     * @param value the value to insert
     */
    public void insert(int value) {
        root = merge(new Node(value), root);
    }

    /**
     * Removes and returns the minimum element (the root).
     *
     * @return the minimum element, or {@code -1} if the heap is empty
     */
    public int extractMin() {
        if (isEmpty()) {
            return -1;
        }

        int min = root.element;
        root = merge(root.left, root.right);
        return min;
    }

    /**
     * Returns an in-order traversal of the heap.
     *
     * <p>Note: in-order traversal does not reflect heap order; it is provided
     * only for structural inspection.
     *
     * @return list of elements in in-order traversal
     */
    public List<Integer> inOrder() {
        List<Integer> result = new ArrayList<>();
        inOrder(root, result);
        return new ArrayList<>(result);
    }

    private void inOrder(Node node, List<Integer> result) {
        if (node == null) {
            return;
        }
        inOrder(node.left, result);
        result.add(node.element);
        inOrder(node.right, result);
    }
}