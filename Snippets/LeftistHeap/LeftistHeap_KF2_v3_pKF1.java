package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;
import java.util.List;

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

    private Node rootNode;

    public LeftistHeap() {
        this.rootNode = null;
    }

    public boolean isEmpty() {
        return rootNode == null;
    }

    public void clear() {
        rootNode = null;
    }

    public void merge(LeftistHeap otherHeap) {
        rootNode = merge(rootNode, otherHeap.rootNode);
        otherHeap.rootNode = null;
    }

    public Node merge(Node firstRootNode, Node secondRootNode) {
        if (firstRootNode == null) {
            return secondRootNode;
        }

        if (secondRootNode == null) {
            return firstRootNode;
        }

        if (firstRootNode.value > secondRootNode.value) {
            Node tempNode = firstRootNode;
            firstRootNode = secondRootNode;
            secondRootNode = tempNode;
        }

        firstRootNode.rightChild = merge(firstRootNode.rightChild, secondRootNode);

        if (firstRootNode.leftChild == null) {
            firstRootNode.leftChild = firstRootNode.rightChild;
            firstRootNode.rightChild = null;
        } else {
            if (firstRootNode.rightChild != null
                    && firstRootNode.leftChild.nullPathLength < firstRootNode.rightChild.nullPathLength) {
                Node tempNode = firstRootNode.leftChild;
                firstRootNode.leftChild = firstRootNode.rightChild;
                firstRootNode.rightChild = tempNode;
            }
            firstRootNode.nullPathLength =
                    (firstRootNode.rightChild == null ? 0 : firstRootNode.rightChild.nullPathLength) + 1;
        }
        return firstRootNode;
    }

    public void insert(int value) {
        rootNode = merge(new Node(value), rootNode);
    }

    public int extractMin() {
        if (isEmpty()) {
            return -1;
        }

        int minValue = rootNode.value;
        rootNode = merge(rootNode.leftChild, rootNode.rightChild);
        return minValue;
    }

    public List<Integer> inOrder() {
        List<Integer> traversalResult = new ArrayList<>();
        inOrderTraversal(rootNode, traversalResult);
        return new ArrayList<>(traversalResult);
    }

    private void inOrderTraversal(Node currentNode, List<Integer> traversalResult) {
        if (currentNode == null) {
            return;
        }
        inOrderTraversal(currentNode.leftChild, traversalResult);
        traversalResult.add(currentNode.value);
        inOrderTraversal(currentNode.rightChild, traversalResult);
    }
}