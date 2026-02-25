package com.thealgorithms.datastructures.heaps;

import java.util.ArrayList;

/**
 * Leftist Heap implementation.
 */
public class Class1 {

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

    public Class1() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void clear() {
        root = null;
    }

    public void merge(Class1 otherHeap) {
        root = merge(root, otherHeap.root);
        otherHeap.root = null;
    }

    public Node merge(Node heap1, Node heap2) {
        if (heap1 == null) {
            return heap2;
        }

        if (heap2 == null) {
            return heap1;
        }

        if (heap1.key > heap2.key) {
            Node temp = heap1;
            heap1 = heap2;
            heap2 = temp;
        }

        heap1.rightChild = merge(heap1.rightChild, heap2);

        if (heap1.leftChild == null) {
            heap1.leftChild = heap1.rightChild;
            heap1.rightChild = null;
        } else {
            if (heap1.leftChild.nullPathLength < heap1.rightChild.nullPathLength) {
                Node temp = heap1.leftChild;
                heap1.leftChild = heap1.rightChild;
                heap1.rightChild = temp;
            }
            heap1.nullPathLength = heap1.rightChild.nullPathLength + 1;
        }
        return heap1;
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
        ArrayList<Integer> result = new ArrayList<>();
        inOrderTraversal(root, result);
        return new ArrayList<>(result);
    }

    private void inOrderTraversal(Node node, ArrayList<Integer> result) {
        if (node == null) {
            return;
        }
        inOrderTraversal(node.leftChild, result);
        result.add(node.key);
        inOrderTraversal(node.rightChild, result);
    }
}