package com.thealgorithms.others;

import java.util.Comparator;
import java.util.PriorityQueue;

class HuffmanNode {

    int frequency;
    char symbol;

    HuffmanNode left;
    HuffmanNode right;
}

class HuffmanNodeFrequencyComparator implements Comparator<HuffmanNode> {

    @Override
    public int compare(HuffmanNode firstNode, HuffmanNode secondNode) {
        return Integer.compare(firstNode.frequency, secondNode.frequency);
    }
}

public final class Huffman {

    private Huffman() {}

    public static void printHuffmanCodes(HuffmanNode node, String code) {
        if (node.left == null && node.right == null && Character.isLetter(node.symbol)) {
            System.out.println(node.symbol + ":" + code);
            return;
        }

        printHuffmanCodes(node.left, code + "0");
        printHuffmanCodes(node.right, code + "1");
    }

    public static void main(String[] args) {
        int symbolCount = 6;
        char[] symbols = {'a', 'b', 'c', 'd', 'e', 'f'};
        int[] frequencies = {5, 9, 12, 13, 16, 45};

        PriorityQueue<HuffmanNode> minHeap =
            new PriorityQueue<>(symbolCount, new HuffmanNodeFrequencyComparator());

        for (int i = 0; i < symbolCount; i++) {
            HuffmanNode leaf = new HuffmanNode();
            leaf.symbol = symbols[i];
            leaf.frequency = frequencies[i];
            leaf.left = null;
            leaf.right = null;
            minHeap.add(leaf);
        }

        HuffmanNode root = null;

        while (minHeap.size() > 1) {
            HuffmanNode lowestFrequencyNode = minHeap.poll();
            HuffmanNode secondLowestFrequencyNode = minHeap.poll();

            HuffmanNode parent = new HuffmanNode();
            parent.frequency =
                lowestFrequencyNode.frequency + secondLowestFrequencyNode.frequency;
            parent.symbol = '-';
            parent.left = lowestFrequencyNode;
            parent.right = secondLowestFrequencyNode;

            root = parent;
            minHeap.add(parent);
        }

        printHuffmanCodes(root, "");
    }
}