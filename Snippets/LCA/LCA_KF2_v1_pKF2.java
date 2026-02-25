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

        int[] parent = new int[vertexCount];
        int[] depth = new int[vertexCount];

        // Build parent and depth arrays assuming root at 0
        depth[0] = 0;
        parent[0] = -1;
        dfs(adjacencyList, 0, -1, parent, depth);

        int v1 = SCANNER.nextInt();
        int v2 = SCANNER.nextInt();

        System.out.println(getLCA(v1, v2, depth, parent));
    }

    private static void dfs(
            List<List<Integer>> adjacencyList,
            int current,
            int parentNode,
            int[] parent,
            int[] depth
    ) {
        for (int neighbor : adjacencyList.get(current)) {
            if (neighbor == parentNode) {
                continue;
            }
            parent[neighbor] = current;
            depth[neighbor] = depth[current] + 1;
            dfs(adjacencyList, neighbor, current, parent, depth);
        }
    }

    private static int getLCA(int v1, int v2, int[] depth, int[] parent) {
        // Ensure v1 is the deeper (or equal depth) node
        if (depth[v1] < depth[v2]) {
            int temp = v1;
            v1 = v2;
            v2 = temp;
        }

        // Lift v1 up until both nodes are at the same depth
        while (depth[v1] > depth[v2]) {
            v1 = parent[v1];
        }

        // If they meet, that's the LCA
        if (v1 == v2) {
            return v1;
        }

        // Lift both nodes up together until their parents match
        while (v1 != v2) {
            v1 = parent[v1];
            v2 = parent[v2];
        }

        return v1;
    }
}