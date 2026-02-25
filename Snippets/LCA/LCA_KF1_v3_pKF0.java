package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class Class1 {

    private static final Scanner SCANNER = new Scanner(System.in);

    private Class1() {
        // Utility class
    }

    public static void method1(String[] args) {
        int nodeCount = SCANNER.nextInt();
        int edgeCount = nodeCount - 1;

        List<List<Integer>> adjacencyList = readTreeAsAdjacencyList(nodeCount, edgeCount);

        int[] parent = new int[nodeCount];
        int[] depth = new int[nodeCount];

        dfsBuildParentAndDepth(adjacencyList, 0, -1, parent, depth);

        int firstNode = SCANNER.nextInt();
        int secondNode = SCANNER.nextInt();

        System.out.println(lowestCommonAncestor(firstNode, secondNode, depth, parent));
    }

    private static List<List<Integer>> readTreeAsAdjacencyList(int nodeCount, int edgeCount) {
        List<List<Integer>> adjacencyList = new ArrayList<>(nodeCount);

        for (int i = 0; i < nodeCount; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        for (int i = 0; i < edgeCount; i++) {
            int from = SCANNER.nextInt();
            int to = SCANNER.nextInt();

            adjacencyList.get(from).add(to);
            adjacencyList.get(to).add(from);
        }

        return adjacencyList;
    }

    private static void dfsBuildParentAndDepth(
            List<List<Integer>> adjacencyList,
            int currentNode,
            int parentNode,
            int[] parent,
            int[] depth
    ) {
        for (int neighbor : adjacencyList.get(currentNode)) {
            if (neighbor == parentNode) {
                continue;
            }
            parent[neighbor] = currentNode;
            depth[neighbor] = depth[currentNode] + 1;
            dfsBuildParentAndDepth(adjacencyList, neighbor, currentNode, parent, depth);
        }
    }

    private static int lowestCommonAncestor(int nodeA, int nodeB, int[] depth, int[] parent) {
        if (depth[nodeA] < depth[nodeB]) {
            int temp = nodeA;
            nodeA = nodeB;
            nodeB = temp;
        }

        while (depth[nodeA] > depth[nodeB]) {
            nodeA = parent[nodeA];
        }

        while (nodeA != nodeB) {
            nodeA = parent[nodeA];
            nodeB = parent[nodeB];
        }

        return nodeA;
    }
}