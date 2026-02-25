package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;
import java.util.List;

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

    private Node merge(Node firstRoot, Node secondRoot) {
        if (firstRoot == null) {
            return secondRoot;
        }

        if (secondRoot == null) {
            return firstRoot;
        }

        if (firstRoot.key > secondRoot.key) {
            Node temp = firstRoot;
            firstRoot = secondRoot;
            secondRoot = temp;
        }

        firstRoot.rightChild = merge(firstRoot.rightChild, secondRoot);

        if (firstRoot.leftChild == null) {
            firstRoot.leftChild = firstRoot.rightChild;
            firstRoot.rightChild = null;
        } else {
            if (firstRoot.rightChild != null
                    && firstRoot.leftChild.nullPathLength < firstRoot.rightChild.nullPathLength) {
                Node temp = firstRoot.leftChild;
                firstRoot.leftChild = firstRoot.rightChild;
                firstRoot.rightChild = temp;
            }
            firstRoot.nullPathLength =
                    (firstRoot.rightChild == null ? 0 : firstRoot.rightChild.nullPathLength) + 1;
        }
        return firstRoot;
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

    public List<Integer> toSortedList() {
        List<Integer> sortedElements = new ArrayList<>();
        inOrderTraversal(root, sortedElements);
        return new ArrayList<>(sortedElements);
    }

    private void inOrderTraversal(Node currentNode, List<Integer> result) {
        if (currentNode == null) {
            return;
        }
        inOrderTraversal(currentNode.leftChild, result);
        result.add(currentNode.key);
        inOrderTraversal(currentNode.rightChild, result);
    }
}