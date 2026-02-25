package com.thealgorithms.others;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Represents a node in a Huffman tree.
 */
class HuffmanNode {
    int frequency;
    char character;
    HuffmanNode left;
    HuffmanNode right;

    HuffmanNode() {}

    HuffmanNode(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
    }

    HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
        this.character = '-';
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    boolean isLeaf() {
        return left == null && right == null;
    }
}

/**
 * Comparator for HuffmanNode based on frequency.
 */
class HuffmanNodeComparator implements Comparator<HuffmanNode> {
    @Override
    public int compare(HuffmanNode first, HuffmanNode second) {
        return Integer.compare(first.frequency, second.frequency);
    }
}

public final class Huffman {
    private Huffman() {}

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

        if (root.isLeaf() && Character.isLetter(root.character)) {
            System.out.println(root.character + ":" + code);
            return;
        }

        printCode(root.left, code + "0");
        printCode(root.right, code + "1");
    }

    public static void main(String[] args) {
        int n = 6;
        char[] characters = {'a', 'b', 'c', 'd', 'e', 'f'};
        int[] frequencies = {5, 9, 12, 13, 16, 45};

        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>(n, new HuffmanNodeComparator());

        for (int i = 0; i < n; i++) {
            queue.add(new HuffmanNode(characters[i], frequencies[i]));
        }

        HuffmanNode root = null;

        while (queue.size() > 1) {
            HuffmanNode firstMin = queue.poll();
            HuffmanNode secondMin = queue.poll();

            HuffmanNode parent =
                    new HuffmanNode(firstMin.frequency + secondMin.frequency, firstMin, secondMin);

            root = parent;
            queue.add(parent);
        }

        printCode(root, "");
    }
}