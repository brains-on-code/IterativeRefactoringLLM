package com.thealgorithms.datastructures.graphs;

public class PrimMST {

    private static final int VERTEX_COUNT = 5;

    /**
     * Returns the index of the vertex with the minimum key value
     * from the set of vertices not yet included in the MST.
     */
    private int findMinKeyVertex(int[] key, boolean[] inMST) {
        int minKey = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int vertex = 0; vertex < VERTEX_COUNT; vertex++) {
            if (!inMST[vertex] && key[vertex] < minKey) {
                minKey = key[vertex];
                minIndex = vertex;
            }
        }

        return minIndex;
    }

    /**
     * Computes the parent array representing the Minimum Spanning Tree (MST)
     * of a connected, undirected, weighted graph using Prim's algorithm.
     *
     * @param graph adjacency matrix representation of the graph
     * @return array where parent[i] is the parent of vertex i in the MST
     */
    public int[] primMST(int[][] graph) {
        int[] parent = new int[VERTEX_COUNT];   // Stores constructed MST
        int[] key = new int[VERTEX_COUNT];      // Key values used to pick minimum weight edge
        boolean[] inMST = new boolean[VERTEX_COUNT]; // Tracks vertices included in MST

        // Initialize all keys as infinite and inMST[] as false
        for (int i = 0; i < VERTEX_COUNT; i++) {
            key[i] = Integer.MAX_VALUE;
            inMST[i] = false;
        }

        // Start from the first vertex: make its key 0 so it is picked first
        key[0] = 0;
        parent[0] = -1; // Root of MST has no parent

        // MST will have VERTEX_COUNT - 1 edges
        for (int count = 0; count < VERTEX_COUNT - 1; count++) {
            int u = findMinKeyVertex(key, inMST);
            inMST[u] = true;

            // Update key and parent for adjacent vertices of the picked vertex
            for (int v = 0; v < VERTEX_COUNT; v++) {
                boolean hasEdge = graph[u][v] != 0;
                boolean notInMST = !inMST[v];
                boolean isBetterEdge = graph[u][v] < key[v];

                if (hasEdge && notInMST && isBetterEdge) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }

        return parent;
    }
}