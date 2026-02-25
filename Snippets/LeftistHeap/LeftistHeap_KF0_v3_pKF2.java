package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;

/**
 * Leftist Heap implementation (min-heap variant).
 *
 * <p>A Leftist Heap is a priority queue that supports efficient merging.
 * It maintains the leftist property: for every node, the null-path length
 * (npl) of the left child is at least as large as that of the right child.
 *
 * <p>Reference:
 * <a href="https://iq.opengenus.org/leftist-heap/">OpenGenus: Leftist Heap</a>
 */
public class LeftistHeap {

    /**
     * Node of a Leftist Heap.
     *
     * <p>Fields:
     * <ul>
     *   <li>{@code element}: stored key</li>
     *   <li>{@code npl}: null-path length (distance to nearest null child)</li>
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

    /** Creates an empty Leftist Heap. */
    public LeftistHeap() {
        root = null;
    }

    /**
     * Returns whether the heap is empty.
     *
     * @return {@code true} if the heap has no elements; {@code false} otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }

    /** Removes all elements from the heap. */
    public void clear() {
        root = null;
    }

    /**
     * Merges another heap into this heap. The other heap is emptied.
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
     * Merges two subheaps rooted at {@code a} and {@code b}, preserving the leftist property.
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

        // Ensure that 'a' has the smaller root element.
        if (a.element > b.element) {
            Node temp = a;
            a = b;
            b = temp;
        }

        // Merge 'b' into the right subtree of 'a'.
        a.right = merge(a.right, b);

        // Maintain leftist property.
        if (a.left == null) {
            a.left = a.right;
            a.right = null;
        } else {
            if (a.right != null && a.left.npl < a.right.npl) {
                Node temp = a.left;
                a.left = a.right;
                a.right = temp;
            }
            a.npl = (a.right == null ? 0 : a.right.npl + 1);
        }

        return a;
    }

    /**
     * Inserts a new element into the heap.
     *
     * @param value the element to insert
     */
    public void insert(int value) {
        root = merge(new Node(value), root);
    }

    /**
     * Removes and returns the minimum element in the heap.
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
     * Returns the elements of the heap using in-order traversal.
     *
     * @return a list of elements in in-order
     */
    public ArrayList<Integer> inOrder() {
        ArrayList<Integer> result = new ArrayList<>();
        inOrderAux(root, result);
        return result;
    }

    /**
     * Recursive helper for in-order traversal.
     *
     * @param node current node
     * @param result list to collect elements
     */
    private void inOrderAux(Node node, ArrayList<Integer> result) {
        if (node == null) {
            return;
        }
        inOrderAux(node.left, result);
        result.add(node.element);
        inOrderAux(node.right, result);
    }
}