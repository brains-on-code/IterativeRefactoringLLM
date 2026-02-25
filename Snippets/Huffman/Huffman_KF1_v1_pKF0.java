package com.thealgorithms.others;

import java.util.Comparator;
import java.util.PriorityQueue;

class HuffmanNode {
    int frequency;
    char character;
    HuffmanNode left;
    HuffmanNode right;
}

class HuffmanNodeComparator implements Comparator<HuffmanNode> {
    @Override
    public int compare(HuffmanNode node1, HuffmanNode node2) {
        return Integer.compare(node1.frequency, node2.frequency);
    }
}

public final class HuffmanCoding {

    private HuffmanCoding() {
    }

    public static void printCodes(HuffmanNode root, String code) {
        if (root == null) {
            return;
        }

        if (root.left == null && root.right == null && Character.isLetter(root.character)) {
            System.out.println(root.character + ":" + code);
            return;
        }

        printCodes(root.left, code + "0");
        printCodes(root.right, code + "1");
    }

    public static void main(String[] args) {
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
            HuffmanNode node1 = priorityQueue.poll();
            HuffmanNode node2 = priorityQueue.poll();

            HuffmanNode mergedNode = new HuffmanNode();
            mergedNode.frequency = node1.frequency + node2.frequency;
            mergedNode.character = '-';
            mergedNode.left = node1;
            mergedNode.right = node2;

            root = mergedNode;
            priorityQueue.add(mergedNode);
        }

        printCodes(root, "");
    }
}