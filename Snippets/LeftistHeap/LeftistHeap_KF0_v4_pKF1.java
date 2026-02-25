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
        private final int key;
        private int nullPathLength;
        private Node leftChild;
        private Node rightChild;

        private Node(int key) {
            this.key = key;
            this.leftChild = null;
            this.rightChild = null;
            this.nullPathLength = 0;
        }
    }

    private Node rootNode;

    public LeftistHeap() {
        rootNode = null;
    }

    /**
     * Checks if the heap is empty.
     *
     * @return true if the heap is empty; false otherwise
     */
    public boolean isEmpty() {
        return rootNode == null;
    }

    /**
     * Resets the heap to its initial state, effectively clearing all elements.
     */
    public void clear() {
        rootNode = null;
    }

    /**
     * Merges the contents of another Leftist Heap into this one.
     *
     * @param otherHeap the LeftistHeap to be merged into this heap
     */
    public void merge(LeftistHeap otherHeap) {
        rootNode = mergeNodes(rootNode, otherHeap.rootNode);
        otherHeap.rootNode = null;
    }

    /**
     * Merges two nodes, maintaining the leftist property.
     *
     * @param firstRootNode  the first node
     * @param secondRootNode the second node
     * @return the merged node maintaining the leftist property
     */
    public Node mergeNodes(Node firstRootNode, Node secondRootNode) {
        if (firstRootNode == null) {
            return secondRootNode;
        }

        if (secondRootNode == null) {
            return firstRootNode;
        }

        if (firstRootNode.key > secondRootNode.key) {
            Node tempRootNode = firstRootNode;
            firstRootNode = secondRootNode;
            secondRootNode = tempRootNode;
        }

        firstRootNode.rightChild = mergeNodes(firstRootNode.rightChild, secondRootNode);

        if (firstRootNode.leftChild == null) {
            firstRootNode.leftChild = firstRootNode.rightChild;
            firstRootNode.rightChild = null;
        } else {
            if (firstRootNode.rightChild != null
                    && firstRootNode.leftChild.nullPathLength < firstRootNode.rightChild.nullPathLength) {
                Node tempChild = firstRootNode.leftChild;
                firstRootNode.leftChild = firstRootNode.rightChild;
                firstRootNode.rightChild = tempChild;
            }
            if (firstRootNode.rightChild == null) {
                firstRootNode.nullPathLength = 0;
            } else {
                firstRootNode.nullPathLength = firstRootNode.rightChild.nullPathLength + 1;
            }
        }
        return firstRootNode;
    }

    /**
     * Inserts a new element into the Leftist Heap.
     *
     * @param key the element to be inserted
     */
    public void insert(int key) {
        rootNode = mergeNodes(new Node(key), rootNode);
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

        int minKey = rootNode.key;
        rootNode = mergeNodes(rootNode.leftChild, rootNode.rightChild);
        return minKey;
    }

    /**
     * Returns a list of the elements in the heap in in-order traversal.
     *
     * @return an ArrayList containing the elements in in-order
     */
    public ArrayList<Integer> inOrder() {
        ArrayList<Integer> elements = new ArrayList<>();
        inOrderTraversal(rootNode, elements);
        return new ArrayList<>(elements);
    }

    /**
     * Auxiliary function for in-order traversal
     *
     * @param currentNode the current node
     * @param elements the list to store the elements in in-order
     */
    private void inOrderTraversal(Node currentNode, ArrayList<Integer> elements) {
        if (currentNode == null) {
            return;
        }
        inOrderTraversal(currentNode.leftChild, elements);
        elements.add(currentNode.key);
        inOrderTraversal(currentNode.rightChild, elements);
    }
}