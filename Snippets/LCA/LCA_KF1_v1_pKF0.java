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
        List<List<Integer>> adjacencyList = new ArrayList<>();

        int nodeCount = SCANNER.nextInt();
        int edgeCount = nodeCount - 1;

        for (int i = 0; i < nodeCount; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        for (int i = 0; i < edgeCount; i++) {
            int u = SCANNER.nextInt();
            int v = SCANNER.nextInt();

            adjacencyList.get(u).add(v);
            adjacencyList.get(v).add(u);
        }

        int[] parent = new int[nodeCount];
        int[] depth = new int[nodeCount];

        dfsBuildParentAndDepth(adjacencyList, 0, -1, parent, depth);

        int nodeA = SCANNER.nextInt();
        int nodeB = SCANNER.nextInt();

        System.out.println(findLCA(nodeA, nodeB, depth, parent));
    }

    private static void dfsBuildParentAndDepth(
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
                dfsBuildParentAndDepth(adjacencyList, neighbor, currentNode, parent, depth);
            }
        }
    }

    private static int findLCA(int nodeA, int nodeB, int[] depth, int[] parent) {
        if (depth[nodeA] < depth[nodeB]) {
            int temp = nodeA;
            nodeA = nodeB;
            nodeB = temp;
        }

        while (depth[nodeA] != depth[nodeB]) {
            nodeA = parent[nodeA];
        }

        if (nodeA == nodeB) {
            return nodeA;
        }

        while (nodeA != nodeB) {
            nodeA = parent[nodeA];
            nodeB = parent[nodeB];
        }

        return nodeA;
    }
}