package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Computes the Lowest Common Ancestor (LCA) of two vertices in a tree.
 *
 * <p>Input format (from standard input):
 * <pre>
 * n
 * (n - 1) lines of edges: u v
 * two vertices: a b
 * </pre>
 *
 * Vertices are assumed to be 0-indexed.
 */
public final class LCA {

    private static final Scanner SCANNER = new Scanner(System.in);

    private LCA() {
        // Prevent instantiation
    }

    public static void main(String[] args) {
        int vertexCount = SCANNER.nextInt();
        int edgeCount = vertexCount - 1;

        List<List<Integer>> adjacencyList = readTreeAsAdjacencyList(vertexCount, edgeCount);

        int[] parent = new int[vertexCount];
        int[] depth = new int[vertexCount];

        initializeRoot(parent, depth);
        depthFirstSearch(adjacencyList, 0, -1, parent, depth);

        int firstVertex = SCANNER.nextInt();
        int secondVertex = SCANNER.nextInt();

        System.out.println(findLowestCommonAncestor(firstVertex, secondVertex, depth, parent));
    }

    /**
     * Reads an undirected tree from standard input and builds its adjacency list.
     */
    private static List<List<Integer>> readTreeAsAdjacencyList(int vertexCount, int edgeCount) {
        List<List<Integer>> adjacencyList = new ArrayList<>(vertexCount);
        for (int i = 0; i < vertexCount; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        for (int i = 0; i < edgeCount; i++) {
            int u = SCANNER.nextInt();
            int v = SCANNER.nextInt();
            adjacencyList.get(u).add(v);
            adjacencyList.get(v).add(u);
        }

        return adjacencyList;
    }

    /**
     * Initializes the root of the tree (assumed to be vertex 0).
     */
    private static void initializeRoot(int[] parent, int[] depth) {
        parent[0] = -1;
        depth[0] = 0;
    }

    /**
     * Populates parent and depth arrays using DFS starting from the root.
     */
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

    /**
     * Returns the Lowest Common Ancestor of two vertices in the tree.
     */
    private static int findLowestCommonAncestor(int v1, int v2, int[] depth, int[] parent) {
        // Ensure v1 is the deeper (or equal depth) vertex
        if (depth[v1] < depth[v2]) {
            int temp = v1;
            v1 = v2;
            v2 = temp;
        }

        // Lift v1 up until both vertices are at the same depth
        while (depth[v1] > depth[v2]) {
            v1 = parent[v1];
        }

        // If they meet, that's the LCA
        if (v1 == v2) {
            return v1;
        }

        // Lift both vertices up together until their parents match
        while (v1 != v2) {
            v1 = parent[v1];
            v2 = parent[v2];
        }

        return v1;
    }
}