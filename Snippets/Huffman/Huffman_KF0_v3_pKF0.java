package com.thealgorithms.others;

import java.util.Comparator;
import java.util.PriorityQueue;

final class HuffmanNode {
    final int frequency;
    final char character;
    final HuffmanNode left;
    final HuffmanNode right;

    HuffmanNode(char character, int frequency) {
        this(character, frequency, null, null);
    }

    HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
        this('-', frequency, left, right);
    }

    private HuffmanNode(char character, int frequency, HuffmanNode left, HuffmanNode right) {
        this.character = character;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    boolean isLeaf() {
        return left == null && right == null;
    }
}

final class HuffmanNodeComparator implements Comparator<HuffmanNode> {
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

        if (root.isLeaf() && Character.isLetter(root.character)) {
            System.out.println(root.character + ":" + code);
            return;
        }

        printCode(root.left, code + "0");
        printCode(root.right, code + "1");
    }

    private static HuffmanNode buildHuffmanTree(char[] characters, int[] frequencies) {
        validateInput(characters, frequencies);

        PriorityQueue<HuffmanNode> priorityQueue =
            new PriorityQueue<>(characters.length, new HuffmanNodeComparator());

        for (int i = 0; i < characters.length; i++) {
            priorityQueue.add(new HuffmanNode(characters[i], frequencies[i]));
        }

        while (priorityQueue.size() > 1) {
            HuffmanNode firstMin = priorityQueue.poll();
            HuffmanNode secondMin = priorityQueue.poll();

            if (firstMin == null || secondMin == null) {
                throw new IllegalStateException("Priority queue returned null while building Huffman tree.");
            }

            HuffmanNode merged =
                new HuffmanNode(firstMin.frequency + secondMin.frequency, firstMin, secondMin);
            priorityQueue.add(merged);
        }

        return priorityQueue.peek();
    }

    private static void validateInput(char[] characters, int[] frequencies) {
        if (characters == null || frequencies == null) {
            throw new IllegalArgumentException("Characters and frequencies must be non-null.");
        }
        if (characters.length == 0) {
            throw new IllegalArgumentException("Characters and frequencies must be non-empty.");
        }
        if (characters.length != frequencies.length) {
            throw new IllegalArgumentException("Characters and frequencies must be of equal length.");
        }
    }

    public static void main(String[] args) {
        char[] characters = {'a', 'b', 'c', 'd', 'e', 'f'};
        int[] frequencies = {5, 9, 12, 13, 16, 45};

        HuffmanNode root = buildHuffmanTree(characters, frequencies);
        printCode(root, "");
    }
}