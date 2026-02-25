package com.thealgorithms.others;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Node of a Huffman tree.
 */
class HuffmanNode {
    int data;      // frequency
    char c;        // character
    HuffmanNode left;
    HuffmanNode right;
}

/**
 * Comparator for HuffmanNode based on frequency.
 */
class HuffmanNodeComparator implements Comparator<HuffmanNode> {
    @Override
    public int compare(HuffmanNode x, HuffmanNode y) {
        return Integer.compare(x.data, y.data);
    }
}

public final class Huffman {
    private Huffman() {
        // utility class
    }

    /**
     * Recursively prints Huffman codes for all leaf nodes in the tree.
     *
     * @param root current node
     * @param code code accumulated so far
     */
    public static void printCode(HuffmanNode root, String code) {
        if (root == null) {
            return;
        }

        // Leaf node: print character and its code
        if (root.left == null && root.right == null && Character.isLetter(root.c)) {
            System.out.println(root.c + ":" + code);
            return;
        }

        // Traverse left with '0' and right with '1'
        printCode(root.left, code + "0");
        printCode(root.right, code + "1");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = 6;
        char[] charArray = {'a', 'b', 'c', 'd', 'e', 'f'};
        int[] charfreq = {5, 9, 12, 13, 16, 45};

        // Min-heap priority queue ordered by frequency
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>(n, new HuffmanNodeComparator());

        // Initialize priority queue with leaf nodes
        for (int i = 0; i < n; i++) {
            HuffmanNode node = new HuffmanNode();
            node.c = charArray[i];
            node.data = charfreq[i];
            node.left = null;
            node.right = null;
            queue.add(node);
        }

        HuffmanNode root = null;

        // Build Huffman tree
        while (queue.size() > 1) {
            HuffmanNode x = queue.poll(); // smallest frequency
            HuffmanNode y = queue.poll(); // second smallest frequency

            HuffmanNode parent = new HuffmanNode();
            parent.data = x.data + y.data;
            parent.c = '-';
            parent.left = x;
            parent.right = y;

            root = parent;
            queue.add(parent);
        }

        // Print Huffman codes
        printCode(root, "");
        scanner.close();
    }
}