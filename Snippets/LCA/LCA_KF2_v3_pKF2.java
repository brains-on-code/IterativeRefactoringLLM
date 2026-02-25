package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class LCA {

    private static final Scanner SCANNER = new Scanner(System.in);

    private LCA() {
        // Utility class; prevent instantiation
    }

    public static void main(String[] args) {
        int vertexCount = SCANNER.nextInt();
        int edgeCount = vertexCount - 1;

        List<List<Integer>> adjacencyList = buildAdjacencyList(vertexCount, edgeCount);

        int[] parent = new int[vertexCount];
        int[] depth = new int[vertexCount];

        initializeRoot(parent, depth);
        depthFirstSearch(adjacencyList, 0, -1, parent, depth);

        int firstVertex = SCANNER.nextInt();
        int secondVertex = SCANNER.nextInt();

        System.out.println(findLowestCommonAncestor(firstVertex, secondVertex, depth, parent));
    }

    private static List<List<Integer>> buildAdjacencyList(int vertexCount, int edgeCount) {
        List<List<Integer>> adjacencyList = new ArrayList<>(vertexCount);
        for (int i = 0; i < vertexCount; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        for (int i = 0; i < edgeCount; i++) {
            int to = SCANNER.nextInt();
            int from = SCANNER.nextInt();
            adjacencyList.get(to).add(from);
            adjacencyList.get(from).add(to);
        }

        return adjacencyList;
    }

    private static void initializeRoot(int[] parent, int[] depth) {
        parent[0] = -1;
        depth[0] = 0;
    }

    private static void depthFirstSearch(
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
            depthFirstSearch(adjacencyList, neighbor, currentNode, parent, depth);
        }
    }

    private static int findLowestCommonAncestor(int v1, int v2, int[] depth, int[] parent) {
        if (depth[v1] < depth[v2]) {
            int temp = v1;
            v1 = v2;
            v2 = temp;
        }

        while (depth[v1] > depth[v2]) {
            v1 = parent[v1];
        }

        if (v1 == v2) {
            return v1;
        }

        while (v1 != v2) {
            v1 = parent[v1];
            v2 = parent[v2];
        }

        return v1;
    }
}