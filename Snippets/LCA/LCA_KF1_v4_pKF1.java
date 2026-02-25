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

        int numberOfNodes = INPUT_SCANNER.nextInt();
        int numberOfEdges = numberOfNodes - 1;

        for (int node = 0; node < numberOfNodes; node++) {
            adjacencyList.add(new ArrayList<>());
        }

        for (int edge = 0; edge < numberOfEdges; edge++) {
            int u = INPUT_SCANNER.nextInt();
            int v = INPUT_SCANNER.nextInt();

            adjacencyList.get(u).add(v);
            adjacencyList.get(v).add(u);
        }

        int[] parent = new int[numberOfNodes];
        int[] depth = new int[numberOfNodes];

        buildParentAndDepth(adjacencyList, 0, -1, parent, depth);

        int queryNodeA = INPUT_SCANNER.nextInt();
        int queryNodeB = INPUT_SCANNER.nextInt();

        System.out.println(findLowestCommonAncestor(queryNodeA, queryNodeB, depth, parent));
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

    private static int findLowestCommonAncestor(
            int nodeA,
            int nodeB,
            int[] depth,
            int[] parent
    ) {
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