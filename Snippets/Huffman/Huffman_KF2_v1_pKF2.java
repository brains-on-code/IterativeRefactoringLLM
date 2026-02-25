package com.thealgorithms.others;

import java.util.Comparator;
import java.util.PriorityQueue;

class HuffmanNode {
    int frequency;
    char character;
    HuffmanNode left;
    HuffmanNode right;
}

class HuffmanNodeComparator implements Comparator<HuffmanNode> {
    @Override
    public int compare(HuffmanNode first, HuffmanNode second) {
        return Integer.compare(first.frequency, second.frequency);
    }
}

public final class Huffman {
    private Huffman() {
        // Utility class
    }

    public static void printCode(HuffmanNode root, String code) {
        if (root == null) {
            return;
        }

        boolean isLeaf = root.left == null && root.right == null;
        if (isLeaf && Character.isLetter(root.character)) {
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

        PriorityQueue<HuffmanNode> priorityQueue =
            new PriorityQueue<>(n, new HuffmanNodeComparator());

        for (int i = 0; i < n; i++) {
            HuffmanNode node = new HuffmanNode();
            node.character = characters[i];
            node.frequency = frequencies[i];
            node.left = null;
            node.right = null;
            priorityQueue.add(node);
        }

        HuffmanNode root = null;

        while (priorityQueue.size() > 1) {
            HuffmanNode firstMin = priorityQueue.poll();
            HuffmanNode secondMin = priorityQueue.poll();

            HuffmanNode merged = new HuffmanNode();
            merged.frequency = firstMin.frequency + secondMin.frequency;
            merged.character = '-';
            merged.left = firstMin;
            merged.right = secondMin;

            root = merged;
            priorityQueue.add(merged);
        }

        printCode(root, "");
    }
}