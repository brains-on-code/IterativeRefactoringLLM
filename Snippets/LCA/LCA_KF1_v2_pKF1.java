package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class LowestCommonAncestor {
    private LowestCommonAncestor() {
    }

    private static final Scanner INPUT_SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        List<List<Integer>> adjacencyList = new ArrayList<>();

        int nodeCount = INPUT_SCANNER.nextInt();
        int edgeCount = nodeCount - 1;

        for (int i = 0; i < nodeCount; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        for (int i = 0; i < edgeCount; i++) {
            int sourceNode = INPUT_SCANNER.nextInt();
            int destinationNode = INPUT_SCANNER.nextInt();

            adjacencyList.get(sourceNode).add(destinationNode);
            adjacencyList.get(destinationNode).add(sourceNode);
        }

        int[] parent = new int[nodeCount];
        int[] depth = new int[nodeCount];

        buildParentAndDepth(adjacencyList, 0, -1, parent, depth);

        int firstQueryNode = INPUT_SCANNER.nextInt();
        int secondQueryNode = INPUT_SCANNER.nextInt();

        System.out.println(findLowestCommonAncestor(firstQueryNode, secondQueryNode, depth, parent));
    }

    private static void buildParentAndDepth(
            List<List<Integer>> adjacencyList,
            int currentNode,
            int parentNode,
            int[] parent,
            int[] depth
    ) {
        for (int neighbor : adjacencyList.get(currentNode)) {
            if (neighbor != parentNode) {
                parent[neighbor] = currentNode;
                depth[neighbor] = depth[currentNode] + 1;
                buildParentAndDepth(adjacencyList, neighbor, currentNode, parent, depth);
            }
        }
    }

    private static int findLowestCommonAncestor(int firstNode, int secondNode, int[] depth, int[] parent) {
        if (depth[firstNode] < depth[secondNode]) {
            int temp = firstNode;
            firstNode = secondNode;
            secondNode = temp;
        }

        while (depth[firstNode] != depth[secondNode]) {
            firstNode = parent[firstNode];
        }

        if (firstNode == secondNode) {
            return firstNode;
        }

        while (firstNode != secondNode) {
            firstNode = parent[firstNode];
            secondNode = parent[secondNode];
        }

        return firstNode;
    }
}