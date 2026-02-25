package com.thealgorithms.others;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

class HuffmanNode {

    int frequency;
    char symbol;

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

    public static void printCode(HuffmanNode currentNode, String codePrefix) {
        if (currentNode.leftChild == null
                && currentNode.rightChild == null
                && Character.isLetter(currentNode.symbol)) {
            System.out.println(currentNode.symbol + ":" + codePrefix);
            return;
        }

        printCode(currentNode.leftChild, codePrefix + "0");
        printCode(currentNode.rightChild, codePrefix + "1");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numberOfSymbols = 6;
        char[] symbols = {'a', 'b', 'c', 'd', 'e', 'f'};
        int[] symbolFrequencies = {5, 9, 12, 13, 16, 45};

        PriorityQueue<HuffmanNode> minHeap =
                new PriorityQueue<>(numberOfSymbols, new HuffmanNodeFrequencyComparator());

        for (int index = 0; index < numberOfSymbols; index++) {
            HuffmanNode leafNode = new HuffmanNode();
            leafNode.symbol = symbols[index];
            leafNode.frequency = symbolFrequencies[index];
            leafNode.leftChild = null;
            leafNode.rightChild = null;
            minHeap.add(leafNode);
        }

        HuffmanNode rootNode = null;

        while (minHeap.size() > 1) {
            HuffmanNode lowestFrequencyNode = minHeap.poll();
            HuffmanNode secondLowestFrequencyNode = minHeap.poll();

            HuffmanNode parentNode = new HuffmanNode();
            parentNode.frequency =
                    lowestFrequencyNode.frequency + secondLowestFrequencyNode.frequency;
            parentNode.symbol = '-';
            parentNode.leftChild = lowestFrequencyNode;
            parentNode.rightChild = secondLowestFrequencyNode;

            rootNode = parentNode;
            minHeap.add(parentNode);
        }

        printCode(rootNode, "");
        scanner.close();
    }
}