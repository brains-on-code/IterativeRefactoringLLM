package com.thealgorithms.datastructures.graphs;

/**
 * Implements Prim's algorithm to find the Minimum Spanning Tree (MST)
 * of a graph represented by an adjacency matrix.
 */
public class Class1 {

    /** Number of vertices in the graph. */
    private static final int VERTEX_COUNT = 5;

    /**
     * Returns the index of the vertex with the minimum key value from the set
     * of vertices not yet included in the MST.
     *
     * @param key      array of key values used to pick minimum weight edge
     * @param inMST    boolean array indicating whether a vertex is included in MST
     * @return index of the vertex with the minimum key value
     */
    int getMinKeyVertex(int[] key, Boolean[] inMST) {
        int minValue = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int vertex = 0; vertex < VERTEX_COUNT; vertex++) {
            if (!inMST[vertex] && key[vertex] < minValue) {
                minValue = key[vertex];
                minIndex = vertex;
            }
        }

        return minIndex;
    }

    /**
     * Constructs and returns the parent array representing the MST
     * for a graph represented by an adjacency matrix.
     *
     * @param graph adjacency matrix of the graph
     * @return parent array where parent[i] is the parent of vertex i in the MST
     */
    public int[] primMST(int[][] graph) {
        int[] parent = new int[VERTEX_COUNT];   // Stores constructed MST
        int[] key = new int[VERTEX_COUNT];      // Key values used to pick minimum weight edge
        Boolean[] inMST = new Boolean[VERTEX_COUNT]; // Tracks vertices included in MST

        // Initialize all keys as infinite and inMST[] as false
        for (int i = 0; i < VERTEX_COUNT; i++) {
            key[i] = Integer.MAX_VALUE;
            inMST[i] = Boolean.FALSE;
        }

        // Start from the first vertex
        key[0] = 0;       // Make key 0 so that this vertex is picked first
        parent[0] = -1;   // First node is always the root of MST

        // Construct MST with VERTEX_COUNT vertices
        for (int count = 0; count < VERTEX_COUNT - 1; count++) {
            int u = getMinKeyVertex(key, inMST);
            inMST[u] = Boolean.TRUE;

            // Update key and parent index of the adjacent vertices
            for (int v = 0; v < VERTEX_COUNT; v++) {
                if (graph[u][v] != 0 && !inMST[v] && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }

        return parent;
    }
}