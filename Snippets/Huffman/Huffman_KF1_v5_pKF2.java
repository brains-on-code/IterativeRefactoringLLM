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
 * Comparator for Huffman nodes based on frequency.
 */
class HuffmanNodeComparator implements Comparator<HuffmanNode> {

    @Override
    public int compare(HuffmanNode firstNode, HuffmanNode secondNode) {
        return Integer.compare(firstNode.frequency, secondNode.frequency);
    }
}

/**
 * Builds a Huffman tree and prints Huffman codes for a fixed example.
 */
public final class HuffmanCoding {

    private HuffmanCoding() {}

    /**
     * Recursively prints Huffman codes for all leaf nodes in the tree.
     *
     * @param node current node in the Huffman tree
     * @param code Huffman code accumulated so far
     */
    public static void printCodes(HuffmanNode node, String code) {
        if (node == null) {
            return;
        }

        if (node.isLeaf() && Character.isLetter(node.character)) {
            System.out.println(node.character + ":" + code);
            return;
        }

        printCodes(node.left, code + "0");
        printCodes(node.right, code + "1");
    }

    public static void main(String[] args) {
        char[] characters = {'a', 'b', 'c', 'd', 'e', 'f'};
        int[] frequencies = {5, 9, 12, 13, 16, 45};

        PriorityQueue<HuffmanNode> priorityQueue =
            new PriorityQueue<>(characters.length, new HuffmanNodeComparator());

        for (int i = 0; i < characters.length; i++) {
            priorityQueue.add(new HuffmanNode(characters[i], frequencies[i]));
        }

        HuffmanNode root = buildHuffmanTree(priorityQueue);
        printCodes(root, "");
    }

    /**
     * Builds a Huffman tree from the given priority queue of nodes.
     *
     * @param priorityQueue queue ordered by node frequency
     * @return root of the constructed Huffman tree
     */
    private static HuffmanNode buildHuffmanTree(PriorityQueue<HuffmanNode> priorityQueue) {
        HuffmanNode root = null;

        while (priorityQueue.size() > 1) {
            HuffmanNode firstNode = priorityQueue.poll();
            HuffmanNode secondNode = priorityQueue.poll();

            HuffmanNode mergedNode =
                new HuffmanNode(firstNode.frequency + secondNode.frequency, firstNode, secondNode);

            root = mergedNode;
            priorityQueue.add(mergedNode);
        }

        return root;
    }
}