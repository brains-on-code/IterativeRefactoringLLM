package com.thealgorithms.datastructures.graphs;

/**
 * Prim's algorithm for computing the Minimum Spanning Tree (MST)
 * of a graph represented by an adjacency matrix.
 */
public class Class1 {

    /** Number of vertices in the graph. */
    private static final int VERTEX_COUNT = 5;

    /**
     * Selects the vertex with the smallest key value among those
     * not yet included in the MST.
     *
     * @param key   current best edge weights to each vertex
     * @param inMST whether each vertex is already in the MST
     * @return index of the vertex with the minimum key value
     */
    int getMinKeyVertex(int[] key, boolean[] inMST) {
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
     * Computes the MST of a graph using Prim's algorithm.
     *
     * @param graph adjacency matrix of the graph
     * @return parent array where parent[i] is the parent of vertex i in the MST
     */
    public int[] primMST(int[][] graph) {
        int[] parent = new int[VERTEX_COUNT];
        int[] key = new int[VERTEX_COUNT];
        boolean[] inMST = new boolean[VERTEX_COUNT];

        initializeKeysAndMSTFlags(key, inMST);

        // Start from vertex 0
        key[0] = 0;
        parent[0] = -1;

        for (int i = 0; i < VERTEX_COUNT - 1; i++) {
            int u = getMinKeyVertex(key, inMST);
            inMST[u] = true;
            updateAdjacentVertices(graph, parent, key, inMST, u);
        }

        return parent;
    }

    /**
     * Initializes all key values to infinity and marks all vertices
     * as not yet included in the MST.
     *
     * @param key   key values for each vertex
     * @param inMST MST inclusion flags for each vertex
     */
    private void initializeKeysAndMSTFlags(int[] key, boolean[] inMST) {
        for (int i = 0; i < VERTEX_COUNT; i++) {
            key[i] = Integer.MAX_VALUE;
            inMST[i] = false;
        }
    }

    /**
     * Relaxes edges from the current vertex to its adjacent vertices,
     * updating their key and parent values when a better edge is found.
     *
     * @param graph  adjacency matrix of the graph
     * @param parent parent array of the MST
     * @param key    current best edge weights to each vertex
     * @param inMST  whether each vertex is already in the MST
     * @param u      current vertex being processed
     */
    private void updateAdjacentVertices(
        int[][] graph,
        int[] parent,
        int[] key,
        boolean[] inMST,
        int u
    ) {
        for (int v = 0; v < VERTEX_COUNT; v++) {
            boolean hasEdge = graph[u][v] != 0;
            boolean notInMST = !inMST[v];
            boolean betterEdge = graph[u][v] < key[v];

            if (hasEdge && notInMST && betterEdge) {
                parent[v] = u;
                key[v] = graph[u][v];
            }
        }
    }
}