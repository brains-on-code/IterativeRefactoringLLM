package com.thealgorithms.datastructures.graphs;

/**
 * Implements Prim's algorithm to find the Minimum Spanning Tree (MST)
 * for a graph represented by an adjacency matrix.
 */
public class Class1 {

    private static final int VERTEX_COUNT = 5;
    private static final int NO_PARENT = -1;
    private static final int INF = Integer.MAX_VALUE;

    /**
     * Finds the index of the vertex with the minimum key value from the set
     * of vertices not yet included in the MST.
     *
     * @param key      array of key values
     * @param inMst    array indicating whether a vertex is included in MST
     * @return index of the vertex with the minimum key value
     */
    private int getMinKeyVertex(int[] key, boolean[] inMst) {
        int minKey = INF;
        int minIndex = -1;

        for (int vertex = 0; vertex < VERTEX_COUNT; vertex++) {
            if (!inMst[vertex] && key[vertex] < minKey) {
                minKey = key[vertex];
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
        boolean[] inMst = new boolean[VERTEX_COUNT];

        for (int i = 0; i < VERTEX_COUNT; i++) {
            key[i] = INF;
            inMst[i] = false;
        }

        key[0] = 0;
        parent[0] = NO_PARENT;

        for (int i = 0; i < VERTEX_COUNT - 1; i++) {
            int u = getMinKeyVertex(key, inMst);
            inMst[u] = true;

            for (int v = 0; v < VERTEX_COUNT; v++) {
                int weight = graph[u][v];
                if (weight != 0 && !inMst[v] && weight < key[v]) {
                    parent[v] = u;
                    key[v] = weight;
                }
            }
        }

        return parent;
    }
}