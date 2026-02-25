package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;

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

    private static final class Node {
        private final int value;
        private int nullPathLength;
        private Node leftChild;
        private Node rightChild;

        private Node(int value) {
            this.value = value;
            this.leftChild = null;
            this.rightChild = null;
            this.nullPathLength = 0;
        }
    }

    private Node root;

    public LeftistHeap() {
        root = null;
    }

    /**
     * Checks if the heap is empty.
     *
     * @return true if the heap is empty; false otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Resets the heap to its initial state, effectively clearing all elements.
     */
    public void clear() {
        root = null;
    }

    /**
     * Merges the contents of another Leftist Heap into this one.
     *
     * @param otherHeap the LeftistHeap to be merged into this heap
     */
    public void merge(LeftistHeap otherHeap) {
        root = mergeNodes(root, otherHeap.root);
        otherHeap.root = null;
    }

    /**
     * Merges two nodes, maintaining the leftist property.
     *
     * @param firstRoot  the first node
     * @param secondRoot the second node
     * @return the merged node maintaining the leftist property
     */
    public Node mergeNodes(Node firstRoot, Node secondRoot) {
        if (firstRoot == null) {
            return secondRoot;
        }

        if (secondRoot == null) {
            return firstRoot;
        }

        if (firstRoot.value > secondRoot.value) {
            Node tempRoot = firstRoot;
            firstRoot = secondRoot;
            secondRoot = tempRoot;
        }

        firstRoot.rightChild = mergeNodes(firstRoot.rightChild, secondRoot);

        if (firstRoot.leftChild == null) {
            firstRoot.leftChild = firstRoot.rightChild;
            firstRoot.rightChild = null;
        } else {
            if (firstRoot.rightChild != null
                    && firstRoot.leftChild.nullPathLength < firstRoot.rightChild.nullPathLength) {
                Node tempChild = firstRoot.leftChild;
                firstRoot.leftChild = firstRoot.rightChild;
                firstRoot.rightChild = tempChild;
            }
            if (firstRoot.rightChild == null) {
                firstRoot.nullPathLength = 0;
            } else {
                firstRoot.nullPathLength = firstRoot.rightChild.nullPathLength + 1;
            }
        }
        return firstRoot;
    }

    /**
     * Inserts a new element into the Leftist Heap.
     *
     * @param value the element to be inserted
     */
    public void insert(int value) {
        root = mergeNodes(new Node(value), root);
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

        int minValue = root.value;
        root = mergeNodes(root.leftChild, root.rightChild);
        return minValue;
    }

    /**
     * Returns a list of the elements in the heap in in-order traversal.
     *
     * @return an ArrayList containing the elements in in-order
     */
    public ArrayList<Integer> inOrder() {
        ArrayList<Integer> elements = new ArrayList<>();
        inOrderTraversal(root, elements);
        return new ArrayList<>(elements);
    }

    /**
     * Auxiliary function for in-order traversal
     *
     * @param currentNode the current node
     * @param elements    the list to store the elements in in-order
     */
    private void inOrderTraversal(Node currentNode, ArrayList<Integer> elements) {
        if (currentNode == null) {
            return;
        }
        inOrderTraversal(currentNode.leftChild, elements);
        elements.add(currentNode.value);
        inOrderTraversal(currentNode.rightChild, elements);
    }
}