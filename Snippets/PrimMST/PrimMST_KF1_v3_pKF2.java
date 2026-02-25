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
     * @param key   array of key values used to pick minimum weight edge
     * @param inMST boolean array indicating whether a vertex is included in MST
     * @return index of the vertex with the minimum key value
     */
    int getMinKeyVertex(int[] key, boolean[] inMST) {
        int minValue = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int vertex = 0; vertex < VERTEX_COUNT; vertex++) {
            boolean isNotInMST = !inMST[vertex];
            boolean hasSmallerKey = key[vertex] < minValue;

            if (isNotInMST && hasSmallerKey) {
                minValue = key[vertex];
                minIndex = vertex;
            }
        }

        return minIndex;
    }

    /**
     * Computes the parent array representing the MST for a graph represented
     * by an adjacency matrix.
     *
     * @param graph adjacency matrix of the graph
     * @return parent array where parent[i] is the parent of vertex i in the MST
     */
    public int[] primMST(int[][] graph) {
        int[] parent = new int[VERTEX_COUNT];
        int[] key = new int[VERTEX_COUNT];
        boolean[] inMST = new boolean[VERTEX_COUNT];

        initializeKeysAndMSTFlags(key, inMST);

        key[0] = 0;
        parent[0] = -1;

        for (int i = 0; i < VERTEX_COUNT - 1; i++) {
            int u = getMinKeyVertex(key, inMST);
            inMST[u] = true;
            updateAdjacentVertices(graph, parent, key, inMST, u);
        }

        return parent;
    }

    private void initializeKeysAndMSTFlags(int[] key, boolean[] inMST) {
        for (int i = 0; i < VERTEX_COUNT; i++) {
            key[i] = Integer.MAX_VALUE;
            inMST[i] = false;
        }
    }

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
            boolean isBetterEdge = graph[u][v] < key[v];

            if (hasEdge && notInMST && isBetterEdge) {
                parent[v] = u;
                key[v] = graph[u][v];
            }
        }
    }
}