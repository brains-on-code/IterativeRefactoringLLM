package com.thealgorithms.datastructures.graphs;

/**
 * Implements Prim's algorithm to find the Minimum Spanning Tree (MST)
 * for a graph represented by an adjacency matrix.
 */
public class Class1 {

    private static final int VERTEX_COUNT = 5;

    /**
     * Finds the index of the vertex with the minimum key value from the set
     * of vertices not yet included in the MST.
     *
     * @param key      array of key values
     * @param included array indicating whether a vertex is included in MST
     * @return index of the vertex with the minimum key value
     */
    private int getMinKeyVertex(int[] key, boolean[] included) {
        int minValue = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int vertex = 0; vertex < VERTEX_COUNT; vertex++) {
            if (!included[vertex] && key[vertex] < minValue) {
                minValue = key[vertex];
                minIndex = vertex;
            }
        }

        return minIndex;
    }

    /**
     * Constructs and returns the parent array representing the MST
     * using Prim's algorithm.
     *
     * @param graph adjacency matrix representation of the graph
     * @return parent array where parent[i] is the parent of vertex i in the MST
     */
    public int[] primMST(int[][] graph) {
        int[] parent = new int[VERTEX_COUNT];
        int[] key = new int[VERTEX_COUNT];
        boolean[] includedInMST = new boolean[VERTEX_COUNT];

        for (int i = 0; i < VERTEX_COUNT; i++) {
            key[i] = Integer.MAX_VALUE;
            includedInMST[i] = false;
        }

        key[0] = 0;
        parent[0] = -1;

        for (int i = 0; i < VERTEX_COUNT - 1; i++) {
            int u = getMinKeyVertex(key, includedInMST);
            includedInMST[u] = true;

            for (int v = 0; v < VERTEX_COUNT; v++) {
                boolean hasEdge = graph[u][v] != 0;
                boolean notInMST = !includedInMST[v];
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