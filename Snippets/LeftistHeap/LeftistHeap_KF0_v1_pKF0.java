package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements a Leftist Heap, which is a type of priority queue
 * that follows similar operations to a binary min-heap but allows for
 * unbalanced structures based on the leftist property.
 *
 * <p>
 * A Leftist Heap maintains the leftist property, which ensures that the
 * left subtree is heavier than the right subtree based on the
 * null-path length (npl) values. This allows for efficient merging
 * of heaps and supports operations like insertion, extraction of
 * the minimum element, and in-order traversal.
 * </p>
 *
 * <p>
 * For more information on Leftist Heaps, visit:
 * <a href="https://iq.opengenus.org/leftist-heap/">OpenGenus</a>
 * </p>
 */
public class LeftistHeap {

    /** Node class representing each element in the Leftist Heap. */
    private static final class Node {
        private final int element;
        private int npl; // null-path length
        private Node left;
        private Node right;

        private Node(int element) {
            this.element = element;
            this.left = null;
            this.right = null;
            this.npl = 0;
        }
    }

    private Node root;

    /** Creates an empty Leftist Heap. */
    public LeftistHeap() {
        this.root = null;
    }

    /**
     * Checks if the heap is empty.
     *
     * @return true if the heap is empty; false otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }

    /** Clears all elements from the heap. */
    public void clear() {
        root = null;
    }

    /**
     * Merges the contents of another Leftist Heap into this one.
     *
     * @param other the LeftistHeap to be merged into this heap
     */
    public void merge(LeftistHeap other) {
        if (other == null || other.root == null) {
            return;
        }
        root = merge(root, other.root);
        other.root = null;
    }

    /**
     * Merges two nodes, maintaining the leftist property.
     *
     * @param first  the first node
     * @param second the second node
     * @return the merged node maintaining the leftist property
     */
    private Node merge(Node first, Node second) {
        if (first == null) {
            return second;
        }
        if (second == null) {
            return first;
        }

        // Ensure that the smaller element is at the root
        if (first.element > second.element) {
            Node temp = first;
            first = second;
            second = temp;
        }

        first.right = merge(first.right, second);

        // If left child is null, move right child to left
        if (first.left == null) {
            first.left = first.right;
            first.right = null;
            first.npl = 0;
        } else {
            // Maintain leftist property: left.npl >= right.npl
            if (first.right != null && first.left.npl < first.right.npl) {
                Node temp = first.left;
                first.left = first.right;
                first.right = temp;
            }
            first.npl = (first.right == null ? 0 : first.right.npl) + 1;
        }

        return first;
    }

    /**
     * Inserts a new element into the Leftist Heap.
     *
     * @param value the element to be inserted
     */
    public void insert(int value) {
        root = merge(new Node(value), root);
    }

    /**
     * Extracts and removes the minimum element from the heap.
     *
     * @return the minimum element in the heap, or -1 if the heap is empty
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
     * Returns a list of the elements in the heap in in-order traversal.
     *
     * @return a List containing the elements in in-order
     */
    public List<Integer> inOrder() {
        List<Integer> result = new ArrayList<>();
        inOrderAux(root, result);
        return result;
    }

    /**
     * Auxiliary function for in-order traversal.
     *
     * @param node   the current node
     * @param result the list to store the elements in in-order
     */
    private void inOrderAux(Node node, List<Integer> result) {
        if (node == null) {
            return;
        }
        inOrderAux(node.left, result);
        result.add(node.element);
        inOrderAux(node.right, result);
    }
}