package com.thealgorithms.others;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

class HuffmanNode {

    int frequency;
    char character;

    HuffmanNode leftChild;
    HuffmanNode rightChild;
}

class HuffmanNodeFrequencyComparator implements Comparator<HuffmanNode> {

    @Override
    public int compare(HuffmanNode firstNode, HuffmanNode secondNode) {
        return firstNode.frequency - secondNode.frequency;
    }
}

public final class Huffman {
    private Huffman() {
    }

    public static void printHuffmanCodes(HuffmanNode currentNode, String currentCode) {
        if (currentNode.leftChild == null
                && currentNode.rightChild == null
                && Character.isLetter(currentNode.character)) {
            System.out.println(currentNode.character + ":" + currentCode);
            return;
        }

        printHuffmanCodes(currentNode.leftChild, currentCode + "0");
        printHuffmanCodes(currentNode.rightChild, currentCode + "1");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numberOfCharacters = 6;
        char[] characters = {'a', 'b', 'c', 'd', 'e', 'f'};
        int[] frequencies = {5, 9, 12, 13, 16, 45};

        PriorityQueue<HuffmanNode> minHeap =
            new PriorityQueue<>(numberOfCharacters, new HuffmanNodeFrequencyComparator());

        for (int index = 0; index < numberOfCharacters; index++) {
            HuffmanNode leafNode = new HuffmanNode();
            leafNode.character = characters[index];
            leafNode.frequency = frequencies[index];
            leafNode.leftChild = null;
            leafNode.rightChild = null;
            minHeap.add(leafNode);
        }

        HuffmanNode rootNode = null;

        while (minHeap.size() > 1) {
            HuffmanNode lowestFrequencyNode = minHeap.poll();
            HuffmanNode secondLowestFrequencyNode = minHeap.poll();

            HuffmanNode internalNode = new HuffmanNode();
            internalNode.frequency =
                lowestFrequencyNode.frequency + secondLowestFrequencyNode.frequency;
            internalNode.character = '-';
            internalNode.leftChild = lowestFrequencyNode;
            internalNode.rightChild = secondLowestFrequencyNode;

            rootNode = internalNode;
            minHeap.add(internalNode);
        }

        printHuffmanCodes(rootNode, "");
        scanner.close();
    }
}