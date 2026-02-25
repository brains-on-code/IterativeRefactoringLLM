package com.thealgorithms.datastructures.graphs;

/**
 * Implementation of Prim's algorithm to compute the Minimum Spanning Tree (MST)
 * of a weighted, undirected graph represented by an adjacency matrix.
 */
public class PrimMST {

    /** Number of vertices in the graph. */
    private static final int V = 5;

    /**
     * Returns the index of the vertex with the smallest key value among the
     * vertices that are not yet included in the MST.
     *
     * @param key    array of key values for each vertex
     * @param inMst  array indicating whether a vertex is already in the MST
     * @return index of the vertex with the minimum key value
     */
    int minKey(int[] key, boolean[] inMst) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int v = 0; v < V; v++) {
            if (!inMst[v] && key[v] < min) {
                min = key[v];
                minIndex = v;
            }
        }

        return minIndex;
    }

    /**
     * Computes the MST of a graph using Prim's algorithm.
     *
     * @param graph adjacency matrix of the graph; graph[u][v] is the weight of
     *              the edge between vertices u and v, or 0 if no edge exists
     * @return an array where parent[v] is the parent of vertex v in the MST;
     *         parent[0] is -1 as it is the root of the MST
     */
    public int[] primMST(int[][] graph) {
        int[] parent = new int[V];   // parent[v] stores the parent of v in the MST
        int[] key = new int[V];      // key[v] is the minimum weight edge to connect v
        boolean[] inMst = new boolean[V]; // inMst[v] is true if v is already in the MST

        // Initialize all keys as infinite and mark all vertices as not in MST
        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            inMst[i] = false;
        }

        // Start from the first vertex: make its key 0 so it is picked first
        key[0] = 0;
        parent[0] = -1; // The first vertex is the root of the MST

        // MST will have V vertices
        for (int count = 0; count < V - 1; count++) {
            // Pick the vertex with the minimum key value not yet in MST
            int u = minKey(key, inMst);
            inMst[u] = true;

            // Update key and parent for adjacent vertices of the picked vertex
            for (int v = 0; v < V; v++) {
                boolean hasEdge = graph[u][v] != 0;
                boolean notInMst = !inMst[v];
                boolean isBetterEdge = graph[u][v] < key[v];

                if (hasEdge && notInMst && isBetterEdge) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }

        return parent;
    }
}