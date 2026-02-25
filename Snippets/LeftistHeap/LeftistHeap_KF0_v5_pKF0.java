package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;
import java.util.List;

/**
 * Leftist Heap implementation (min-heap variant).
 *
 * <p>A Leftist Heap maintains the leftist property, which ensures that the
 * left subtree is at least as "heavy" as the right subtree based on the
 * null-path length (npl). This supports efficient merging, insertion,
 * extraction of the minimum element, and traversal.</p>
 */
public class LeftistHeap {

    /** Node class representing each element in the Leftist Heap. */
    private static final class Node {
        private final int element;
        private int nullPathLength; // null-path length (npl)
        private Node left;
        private Node right;

        private Node(int element) {
            this.element = element;
            this.nullPathLength = 0;
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
        inOrderTraversal(root, result);
        return result;
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
        return maintainLeftistProperty(first);
    }

    /**
     * Ensures the leftist property is maintained for the given node.
     *
     * @param node the node to adjust
     * @return the node with the leftist property maintained
     */
    private Node maintainLeftistProperty(Node node) {
        if (node.left == null) {
            node.left = node.right;
            node.right = null;
            node.nullPathLength = 0;
            return node;
        }

        if (node.right != null && node.left.nullPathLength < node.right.nullPathLength) {
            swapChildren(node);
        }

        node.nullPathLength = calculateNullPathLength(node.right);
        return node;
    }

    private void swapChildren(Node node) {
        Node temp = node.left;
        node.left = node.right;
        node.right = temp;
    }

    private int calculateNullPathLength(Node node) {
        return (node == null ? 0 : node.nullPathLength) + 1;
    }

    /**
     * Auxiliary function for in-order traversal.
     *
     * @param node   the current node
     * @param result the list to store the elements in in-order
     */
    private void inOrderTraversal(Node node, List<Integer> result) {
        if (node == null) {
            return;
        }
        inOrderTraversal(node.left, result);
        result.add(node.element);
        inOrderTraversal(node.right, result);
    }
}