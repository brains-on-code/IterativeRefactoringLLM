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

    private Class1() {
        // Utility class; prevent instantiation.
    }

    private static final Scanner INPUT = new Scanner(System.in);

    public static void method1(String[] args) {
        // Read number of nodes.
        int nodeCount = INPUT.nextInt();
        int edgeCount = nodeCount - 1;

        // Adjacency list representation of the tree.
        List<List<Integer>> adjacencyList = new ArrayList<>();
        for (int i = 0; i < nodeCount; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        // Read edges and build the undirected tree.
        for (int i = 0; i < edgeCount; i++) {
            int u = INPUT.nextInt();
            int v = INPUT.nextInt();
            adjacencyList.get(u).add(v);
            adjacencyList.get(v).add(u);
        }

        // parent[i] = parent of node i in the rooted tree.
        int[] parent = new int[nodeCount];

        // depth[i] = depth of node i from the root (root has depth 0).
        int[] depth = new int[nodeCount];

        // Root the tree at node 0 and compute parent and depth arrays.
        dfsBuildParentAndDepth(adjacencyList, 0, -1, parent, depth);

        // Read the two query nodes.
        int nodeA = INPUT.nextInt();
        int nodeB = INPUT.nextInt();

        // Compute and print their Lowest Common Ancestor.
        System.out.println(lowestCommonAncestor(nodeA, nodeB, depth, parent));
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
            if (neighbor != parentNode) {
                parent[neighbor] = current;
                depth[neighbor] = depth[current] + 1;
                dfsBuildParentAndDepth(adjacencyList, neighbor, current, parent, depth);
            }
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
        // Ensure 'a' is the deeper (or equal depth) node.
        if (depth[a] < depth[b]) {
            int temp = a;
            a = b;
            b = temp;
        }

        // Lift 'a' up until both nodes are at the same depth.
        while (depth[a] != depth[b]) {
            a = parent[a];
        }

        // If they meet, that's the LCA.
        if (a == b) {
            return a;
        }

        // Move both up together until their parents match.
        while (a != b) {
            a = parent[a];
            b = parent[b];
        }

        return a;
    }
}