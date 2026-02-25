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

    HuffmanNode(char character, int frequency, HuffmanNode left, HuffmanNode right) {
        this.character = character;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    boolean isLeaf() {
        return left == null && right == null;
    }
}

public final class HuffmanCoding {

    private HuffmanCoding() {
        // Utility class
    }

    public static void printCodes(HuffmanNode root, String code) {
        if (root == null) {
            return;
        }

        if (root.isLeaf() && Character.isLetter(root.character)) {
            System.out.println(root.character + ":" + code);
            return;
        }

        printCodes(root.left, code + "0");
        printCodes(root.right, code + "1");
    }

    public static void main(String[] args) {
        char[] characters = {'a', 'b', 'c', 'd', 'e', 'f'};
        int[] frequencies = {5, 9, 12, 13, 16, 45};

        PriorityQueue<HuffmanNode> priorityQueue = createPriorityQueue(characters, frequencies);
        HuffmanNode root = buildHuffmanTree(priorityQueue);
        printCodes(root, "");
    }

    private static PriorityQueue<HuffmanNode> createPriorityQueue(char[] characters, int[] frequencies) {
        PriorityQueue<HuffmanNode> priorityQueue =
            new PriorityQueue<>(characters.length, Comparator.comparingInt(node -> node.frequency));

        for (int i = 0; i < characters.length; i++) {
            priorityQueue.add(new HuffmanNode(characters[i], frequencies[i]));
        }

        return priorityQueue;
    }

    private static HuffmanNode buildHuffmanTree(PriorityQueue<HuffmanNode> priorityQueue) {
        while (priorityQueue.size() > 1) {
            HuffmanNode leftNode = priorityQueue.poll();
            HuffmanNode rightNode = priorityQueue.poll();

            if (leftNode == null || rightNode == null) {
                break;
            }

            HuffmanNode mergedNode = new HuffmanNode(
                '-',
                leftNode.frequency + rightNode.frequency,
                leftNode,
                rightNode
            );

            priorityQueue.add(mergedNode);
        }
        return priorityQueue.poll();
    }
}