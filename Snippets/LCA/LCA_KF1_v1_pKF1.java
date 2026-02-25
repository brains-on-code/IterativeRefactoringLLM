package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class Class1 {
    private Class1() {
    }

    private static final Scanner INPUT_SCANNER = new Scanner(System.in);

    public static void method1(String[] args) {
        List<List<Integer>> adjacencyList = new ArrayList<>();

        int nodeCount = INPUT_SCANNER.nextInt();
        int edgeCount = nodeCount - 1;

        for (int i = 0; i < nodeCount; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        int nodeU;
        int nodeV;
        for (int i = 0; i < edgeCount; i++) {
            nodeU = INPUT_SCANNER.nextInt();
            nodeV = INPUT_SCANNER.nextInt();

            adjacencyList.get(nodeU).add(nodeV);
            adjacencyList.get(nodeV).add(nodeU);
        }

        int[] parent = new int[nodeCount];
        int[] depth = new int[nodeCount];

        method2(adjacencyList, 0, -1, parent, depth);

        int queryNodeA = INPUT_SCANNER.nextInt();
        int queryNodeB = INPUT_SCANNER.nextInt();

        System.out.println(method3(queryNodeA, queryNodeB, depth, parent));
    }

    private static void method2(
            List<List<Integer>> adjacencyList,
            int currentNode,
            int parentNode,
            int[] parent,
            int[] depth
    ) {
        for (int neighbor : adjacencyList.get(currentNode)) {
            if (neighbor != parentNode) {
                parent[neighbor] = currentNode;
                depth[neighbor] = 1 + depth[currentNode];
                method2(adjacencyList, neighbor, currentNode, parent, depth);
            }
        }
    }

    private static int method3(int nodeA, int nodeB, int[] depth, int[] parent) {
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