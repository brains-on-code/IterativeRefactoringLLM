package com.thealgorithms.others;

import java.util.Comparator;
import java.util.PriorityQueue;

final class HuffmanNode {
    int frequency;
    char character;
    HuffmanNode left;
    HuffmanNode right;

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

final class HuffmanNodeComparator implements Comparator<HuffmanNode> {
    @Override
    public int compare(HuffmanNode first, HuffmanNode second) {
        return Integer.compare(first.frequency, second.frequency);
    }
}

public final class Huffman {
    private Huffman() {
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

    public static void main(String[] args) {
        int n = 6;
        char[] characters = {'a', 'b', 'c', 'd', 'e', 'f'};
        int[] frequencies = {5, 9, 12, 13, 16, 45};

        PriorityQueue<HuffmanNode> priorityQueue =
            new PriorityQueue<>(n, new HuffmanNodeComparator());

        for (int i = 0; i < n; i++) {
            priorityQueue.add(new HuffmanNode(characters[i], frequencies[i]));
        }

        HuffmanNode root = null;

        while (priorityQueue.size() > 1) {
            HuffmanNode firstMin = priorityQueue.poll();
            HuffmanNode secondMin = priorityQueue.poll();

            if (firstMin == null || secondMin == null) {
                break;
            }

            HuffmanNode merged =
                new HuffmanNode(firstMin.frequency + secondMin.frequency, firstMin, secondMin);
            root = merged;
            priorityQueue.add(merged);
        }

        printCode(root, "");
    }
}