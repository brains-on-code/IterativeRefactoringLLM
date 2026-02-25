package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Utility class for computing the Lowest Common Ancestor (LCA) of two nodes in a tree.
 *
 * <p>Input format (from standard input):
 * <pre>
 * n                      // number of nodes (0..n-1)
 * n-1 lines: u v         // undirected edges of the tree
 * a b                    // two nodes for which to compute the LCA
 * </pre>
 *
 * <p>Output:
 * <pre>
 * lca(a, b)
 * </pre>
 */
public final class Class1 {

    private static final Scanner INPUT = new Scanner(System.in);

    private Class1() {
        // Prevent instantiation.
    }

    public static void method1(String[] args) {
        int nodeCount = INPUT.nextInt();
        int edgeCount = nodeCount - 1;

        List<List<Integer>> adjacencyList = buildAdjacencyList(nodeCount);

        readEdges(edgeCount, adjacencyList);

        int[] parent = new int[nodeCount];
        int[] depth = new int[nodeCount];

        // Root the tree at node 0 and compute parent and depth arrays.
        dfsBuildParentAndDepth(adjacencyList, 0, -1, parent, depth);

        int nodeA = INPUT.nextInt();
        int nodeB = INPUT.nextInt();

        System.out.println(lowestCommonAncestor(nodeA, nodeB, depth, parent));
    }

    private static List<List<Integer>> buildAdjacencyList(int nodeCount) {
        List<List<Integer>> adjacencyList = new ArrayList<>();
        for (int i = 0; i < nodeCount; i++) {
            adjacencyList.add(new ArrayList<>());
        }
        return adjacencyList;
    }

    private static void readEdges(int edgeCount, List<List<Integer>> adjacencyList) {
        for (int i = 0; i < edgeCount; i++) {
            int u = INPUT.nextInt();
            int v = INPUT.nextInt();
            adjacencyList.get(u).add(v);
            adjacencyList.get(v).add(u);
        }
    }

    /**
     * Depth-first search to populate parent and depth arrays for a rooted tree.
     *
     * @param adjacencyList adjacency list of the tree
     * @param current       current node
     * @param parentNode    parent of the current node (-1 for root)
     * @param parent        array to store parent of each node
     * @param depth         array to store depth of each node
     */
    private static void dfsBuildParentAndDepth(
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
            dfsBuildParentAndDepth(adjacencyList, neighbor, current, parent, depth);
        }
    }

    /**
     * Computes the Lowest Common Ancestor (LCA) of two nodes in a tree using parent and depth arrays.
     *
     * @param a      first node
     * @param b      second node
     * @param depth  depth array
     * @param parent parent array
     * @return the LCA of nodes a and b
     */
    private static int lowestCommonAncestor(int a, int b, int[] depth, int[] parent) {
        if (depth[a] < depth[b]) {
            int temp = a;
            a = b;
            b = temp;
        }

        while (depth[a] > depth[b]) {
            a = parent[a];
        }

        if (a == b) {
            return a;
        }

        while (a != b) {
            a = parent[a];
            b = parent[b];
        }

        return a;
    }
}