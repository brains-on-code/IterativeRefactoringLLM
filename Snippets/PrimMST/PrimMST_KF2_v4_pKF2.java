package com.thealgorithms.datastructures.graphs;

public class PrimMST {

    private static final int VERTEX_COUNT = 5;

    /**
     * Finds the vertex with the smallest key value among those
     * not yet included in the MST.
     *
     * @param key   current minimum edge weights to each vertex
     * @param inMST whether each vertex is already included in the MST
     * @return index of the vertex with the minimum key value
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
     * @return parent array where parent[i] is the parent of vertex i in the MST
     */
    public int[] primMST(int[][] graph) {
        int[] parent = new int[VERTEX_COUNT];
        int[] key = new int[VERTEX_COUNT];
        boolean[] inMST = new boolean[VERTEX_COUNT];

        initializeKeysAndMSTState(key, inMST);

        // Start building MST from vertex 0
        key[0] = 0;
        parent[0] = -1;

        for (int edgeCount = 0; edgeCount < VERTEX_COUNT - 1; edgeCount++) {
            int u = findMinKeyVertex(key, inMST);
            inMST[u] = true;
            updateAdjacentVertices(graph, parent, key, inMST, u);
        }

        return parent;
    }

    /**
     * Initializes all key values to "infinity" and marks all vertices as not in MST.
     *
     * @param key   array of key values to initialize
     * @param inMST array of MST inclusion flags to initialize
     */
    private void initializeKeysAndMSTState(int[] key, boolean[] inMST) {
        for (int i = 0; i < VERTEX_COUNT; i++) {
            key[i] = Integer.MAX_VALUE;
            inMST[i] = false;
        }
    }

    /**
     * Updates key and parent arrays for vertices adjacent to the given vertex.
     *
     * @param graph  adjacency matrix of the graph
     * @param parent parent array of the MST
     * @param key    current minimum edge weights to each vertex
     * @param inMST  whether each vertex is already included in the MST
     * @param u      vertex just added to the MST
     */
    private void updateAdjacentVertices(
            int[][] graph,
            int[] parent,
            int[] key,
            boolean[] inMST,
            int u
    ) {
        for (int v = 0; v < VERTEX_COUNT; v++) {
            // Skip if no edge or vertex already in MST
            if (graph[u][v] == 0 || inMST[v]) {
                continue;
            }

            // Update key and parent if a cheaper edge is found
            if (graph[u][v] < key[v]) {
                parent[v] = u;
                key[v] = graph[u][v];
            }
        }
    }
}