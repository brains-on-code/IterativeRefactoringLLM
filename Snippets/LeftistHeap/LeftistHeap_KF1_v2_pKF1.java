package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;

/**
 * Leftist Heap implementation.
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

    private Node root;

    public LeftistHeap() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void clear() {
        root = null;
    }

    public void merge(LeftistHeap otherHeap) {
        root = merge(root, otherHeap.root);
        otherHeap.root = null;
    }

    private Node merge(Node firstHeapRoot, Node secondHeapRoot) {
        if (firstHeapRoot == null) {
            return secondHeapRoot;
        }

        if (secondHeapRoot == null) {
            return firstHeapRoot;
        }

        if (firstHeapRoot.key > secondHeapRoot.key) {
            Node temp = firstHeapRoot;
            firstHeapRoot = secondHeapRoot;
            secondHeapRoot = temp;
        }

        firstHeapRoot.rightChild = merge(firstHeapRoot.rightChild, secondHeapRoot);

        if (firstHeapRoot.leftChild == null) {
            firstHeapRoot.leftChild = firstHeapRoot.rightChild;
            firstHeapRoot.rightChild = null;
        } else {
            if (firstHeapRoot.leftChild.nullPathLength < firstHeapRoot.rightChild.nullPathLength) {
                Node temp = firstHeapRoot.leftChild;
                firstHeapRoot.leftChild = firstHeapRoot.rightChild;
                firstHeapRoot.rightChild = temp;
            }
            firstHeapRoot.nullPathLength = firstHeapRoot.rightChild.nullPathLength + 1;
        }
        return firstHeapRoot;
    }

    public void insert(int key) {
        root = merge(new Node(key), root);
    }

    public int deleteMin() {
        if (isEmpty()) {
            return -1;
        }

        int minKey = root.key;
        root = merge(root.leftChild, root.rightChild);
        return minKey;
    }

    public ArrayList<Integer> toSortedList() {
        ArrayList<Integer> sortedElements = new ArrayList<>();
        inOrderTraversal(root, sortedElements);
        return new ArrayList<>(sortedElements);
    }

    private void inOrderTraversal(Node currentNode, ArrayList<Integer> result) {
        if (currentNode == null) {
            return;
        }
        inOrderTraversal(currentNode.leftChild, result);
        result.add(currentNode.key);
        inOrderTraversal(currentNode.rightChild, result);
    }
}