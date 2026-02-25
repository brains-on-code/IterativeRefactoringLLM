package com.thealgorithms.others;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Node of a Huffman tree.
 */
class HuffmanNode {

    int frequency;
    char character;

    HuffmanNode left;
    HuffmanNode right;
}

/**
 * Comparator for Huffman nodes based on frequency.
 */
class HuffmanNodeComparator implements Comparator<HuffmanNode> {

    @Override
    public int compare(HuffmanNode node1, HuffmanNode node2) {
        return node1.frequency - node2.frequency;
    }
}

/**
 * Demonstrates building a Huffman tree and printing Huffman codes.
 */
public final class HuffmanCoding {

    private HuffmanCoding() {
    }

    /**
     * Recursively prints Huffman codes for all leaf nodes in the tree.
     *
     * @param node current node in the Huffman tree
     * @param code Huffman code accumulated so far
     */
    public static void printCodes(HuffmanNode node, String code) {
        if (node.left == null && node.right == null && Character.isLetter(node.character)) {
            System.out.println(node.character + ":" + code);
            return;
        }

        printCodes(node.left, code + "0");
        printCodes(node.right, code + "1");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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
            HuffmanNode node1 = priorityQueue.poll();
            HuffmanNode node2 = priorityQueue.poll();

            HuffmanNode merged = new HuffmanNode();
            merged.frequency = node1.frequency + node2.frequency;
            merged.character = '-';
            merged.left = node1;
            merged.right = node2;

            root = merged;
            priorityQueue.add(merged);
        }

        printCodes(root, "");
        scanner.close();
    }
}