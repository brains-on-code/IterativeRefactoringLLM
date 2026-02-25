package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class LCA {

    private static final Scanner SCANNER = new Scanner(System.in);

    private LCA() {
        // Utility class
    }

    public static void main(String[] args) {
        int vertexCount = SCANNER.nextInt();
        int edgeCount = vertexCount - 1;

        List<List<Integer>> adjacencyList = createEmptyAdjacencyList(vertexCount);
        readEdges(adjacencyList, edgeCount);

        int[] parent = new int[vertexCount];
        int[] depth = new int[vertexCount];

        int root = 0;
        depth[root] = 0;
        parent[root] = -1;
        dfs(adjacencyList, root, -1, parent, depth);

        int firstVertex = SCANNER.nextInt();
        int secondVertex = SCANNER.nextInt();

        System.out.println(getLCA(firstVertex, secondVertex, depth, parent));
    }

    private static List<List<Integer>> createEmptyAdjacencyList(int vertexCount) {
        List<List<Integer>> adjacencyList = new ArrayList<>(vertexCount);
        for (int i = 0; i < vertexCount; i++) {
            adjacencyList.add(new ArrayList<>());
        }
        return adjacencyList;
    }

    private static void readEdges(List<List<Integer>> adjacencyList, int edgeCount) {
        for (int i = 0; i < edgeCount; i++) {
            int from = SCANNER.nextInt();
            int to = SCANNER.nextInt();
            adjacencyList.get(from).add(to);
            adjacencyList.get(to).add(from);
        }
    }

    /**
     * Depth first search to calculate parent and depth of every vertex.
     *
     * @param adjacencyList The adjacency list representation of the tree
     * @param current       The current vertex
     * @param parentVertex  Parent of current
     * @param parent        An array to store parents of all vertices
     * @param depth         An array to store depth of all vertices
     */
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

    /**
     * Method to calculate Lowest Common Ancestor.
     *
     * @param v1     The first vertex
     * @param v2     The second vertex
     * @param depth  An array with depths of all vertices
     * @param parent An array with parents of all vertices
     * @return Returns a vertex that is LCA of v1 and v2
     */
    private static int getLCA(int v1, int v2, int[] depth, int[] parent) {
        // Ensure v1 is the deeper (or equal depth) vertex
        if (depth[v1] < depth[v2]) {
            int temp = v1;
            v1 = v2;
            v2 = temp;
        }

        // Lift v1 up to the depth of v2
        while (depth[v1] > depth[v2]) {
            v1 = parent[v1];
        }

        // If they meet, that's the LCA
        if (v1 == v2) {
            return v1;
        }

        // Lift both up until their parents are equal
        while (v1 != v2) {
            v1 = parent[v1];
            v2 = parent[v2];
        }

        return v1;
    }
}