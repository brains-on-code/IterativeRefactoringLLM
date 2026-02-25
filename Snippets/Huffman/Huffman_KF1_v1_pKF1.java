package com.thealgorithms.others;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

class HuffmanNode {

    int frequency;
    char character;

    HuffmanNode left;
    HuffmanNode right;
}

class HuffmanNodeComparator implements Comparator<HuffmanNode> {

    @Override
    public int compare(HuffmanNode firstNode, HuffmanNode secondNode) {
        return firstNode.frequency - secondNode.frequency;
    }
}

public final class HuffmanCoding {
    private HuffmanCoding() {
    }

    public static void printHuffmanCodes(HuffmanNode root, String code) {
        if (root.left == null && root.right == null && Character.isLetter(root.character)) {
            System.out.println(root.character + ":" + code);
            return;
        }

        printHuffmanCodes(root.left, code + "0");
        printHuffmanCodes(root.right, code + "1");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numberOfCharacters = 6;
        char[] characters = {'a', 'b', 'c', 'd', 'e', 'f'};
        int[] frequencies = {5, 9, 12, 13, 16, 45};

        PriorityQueue<HuffmanNode> priorityQueue =
                new PriorityQueue<>(numberOfCharacters, new HuffmanNodeComparator());

        for (int i = 0; i < numberOfCharacters; i++) {
            HuffmanNode node = new HuffmanNode();
            node.character = characters[i];
            node.frequency = frequencies[i];
            node.left = null;
            node.right = null;

            priorityQueue.add(node);
        }

        HuffmanNode root = null;

        while (priorityQueue.size() > 1) {
            HuffmanNode firstMin = priorityQueue.peek();
            priorityQueue.poll();

            HuffmanNode secondMin = priorityQueue.peek();
            priorityQueue.poll();

            HuffmanNode internalNode = new HuffmanNode();
            internalNode.frequency = firstMin.frequency + secondMin.frequency;
            internalNode.character = '-';
            internalNode.left = firstMin;
            internalNode.right = secondMin;

            root = internalNode;
            priorityQueue.add(internalNode);
        }

        printHuffmanCodes(root, "");
        scanner.close();
    }
}