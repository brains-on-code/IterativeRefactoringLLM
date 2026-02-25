package com.thealgorithms.others;

import java.util.Comparator;
import java.util.PriorityQueue;

class HuffmanNode {

    int frequency;
    char character;

    HuffmanNode left;
    HuffmanNode right;
}

class HuffmanNodeFrequencyComparator implements Comparator<HuffmanNode> {

    @Override
    public int compare(HuffmanNode firstNode, HuffmanNode secondNode) {
        return Integer.compare(firstNode.frequency, secondNode.frequency);
    }
}

public final class HuffmanCoding {

    private HuffmanCoding() {}

    public static void printHuffmanCodes(HuffmanNode node, String code) {
        if (node.left == null && node.right == null && Character.isLetter(node.character)) {
            System.out.println(node.character + ":" + code);
            return;
        }

        printHuffmanCodes(node.left, code + "0");
        printHuffmanCodes(node.right, code + "1");
    }

    public static void main(String[] args) {
        int symbolCount = 6;
        char[] characters = {'a', 'b', 'c', 'd', 'e', 'f'};
        int[] frequencies = {5, 9, 12, 13, 16, 45};

        PriorityQueue<HuffmanNode> minHeap =
                new PriorityQueue<>(symbolCount, new HuffmanNodeFrequencyComparator());

        for (int i = 0; i < symbolCount; i++) {
            HuffmanNode leafNode = new HuffmanNode();
            leafNode.character = characters[i];
            leafNode.frequency = frequencies[i];
            leafNode.left = null;
            leafNode.right = null;

            minHeap.add(leafNode);
        }

        HuffmanNode root = null;

        while (minHeap.size() > 1) {
            HuffmanNode lowestFrequencyNode = minHeap.poll();
            HuffmanNode secondLowestFrequencyNode = minHeap.poll();

            HuffmanNode parentNode = new HuffmanNode();
            parentNode.frequency =
                    lowestFrequencyNode.frequency + secondLowestFrequencyNode.frequency;
            parentNode.character = '-';
            parentNode.left = lowestFrequencyNode;
            parentNode.right = secondLowestFrequencyNode;

            root = parentNode;
            minHeap.add(parentNode);
        }

        printHuffmanCodes(root, "");
    }
}