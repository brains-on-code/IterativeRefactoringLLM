package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class LCA {

    private static final Scanner SCANNER = new Scanner(System.in);

    private LCA() {}

    public static void main(String[] args) {
        int vertexCount = SCANNER.nextInt();
        int edgeCount = vertexCount - 1;

        List<List<Integer>> adjacencyList = createEmptyAdjacencyList(vertexCount);
        readTreeEdges(adjacencyList, edgeCount);

        int[] parent = new int[vertexCount];
        int[] depth = new int[vertexCount];

        dfs(adjacencyList, 0, -1, parent, depth);

        int v1 = SCANNER.nextInt();
        int v2 = SCANNER.nextInt();

        System.out.println(getLCA(v1, v2, depth, parent));
    }

    private static List<List<Integer>> createEmptyAdjacencyList(int vertexCount) {
        List<List<Integer>> adjacencyList = new ArrayList<>(vertexCount);
        for (int i = 0; i < vertexCount; i++) {
            adjacencyList.add(new ArrayList<>());
        }
        return adjacencyList;
    }

    private static void readTreeEdges(List<List<Integer>> adjacencyList, int edgeCount) {
        for (int i = 0; i < edgeCount; i++) {
            int u = SCANNER.nextInt();
            int v = SCANNER.nextInt();
            adjacencyList.get(u).add(v);
            adjacencyList.get(v).add(u);
        }
    }

    private static void dfs(
            List<List<Integer>> adjacencyList,
            int current,
            int parentVertex,
            int[] parent,
            int[] depth
    ) {
        for (int neighbor : adjacencyList.get(current)) {
            if (neighbor == parentVertex) {
                continue;
            }
            parent[neighbor] = current;
            depth[neighbor] = depth[current] + 1;
            dfs(adjacencyList, neighbor, current, parent, depth);
        }
    }

    private static int getLCA(int v1, int v2, int[] depth, int[] parent) {
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