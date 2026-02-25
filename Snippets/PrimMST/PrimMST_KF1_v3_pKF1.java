package com.thealgorithms.datastructures.graphs;

/**
 * Implements Prim's algorithm to find the Minimum Spanning Tree (MST)
 * for a graph represented by an adjacency matrix.
 */
public class PrimMinimumSpanningTree {

    private static final int VERTEX_COUNT = 5;

    /**
     * Finds the index of the vertex with the minimum key value from
     * the set of vertices not yet included in the MST.
     */
    int getMinKeyVertex(int[] key, boolean[] inMST) {
        int minKey = Integer.MAX_VALUE;
        int minVertexIndex = -1;

        for (int vertex = 0; vertex < VERTEX_COUNT; vertex++) {
            if (!inMST[vertex] && key[vertex] < minKey) {
                minKey = key[vertex];
                minVertexIndex = vertex;
            }
        }

        return minVertexIndex;
    }

    /**
     * Constructs and returns the parent array representing the MST
     * of a graph given its adjacency matrix.
     *
     * @param adjacencyMatrix adjacency matrix of the graph
     * @return parent array where parent[i] is the parent of vertex i in the MST
     */
    public int[] primMST(int[][] adjacencyMatrix) {
        int[] parent = new int[VERTEX_COUNT];
        int[] key = new int[VERTEX_COUNT];
        boolean[] inMST = new boolean[VERTEX_COUNT];

        for (int vertex = 0; vertex < VERTEX_COUNT; vertex++) {
            key[vertex] = Integer.MAX_VALUE;
            inMST[vertex] = false;
        }

        key[0] = 0;
        parent[0] = -1;

        for (int i = 0; i < VERTEX_COUNT - 1; i++) {
            int u = getMinKeyVertex(key, inMST);
            inMST[u] = true;

            for (int v = 0; v < VERTEX_COUNT; v++) {
                if (adjacencyMatrix[u][v] != 0
                        && !inMST[v]
                        && adjacencyMatrix[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = adjacencyMatrix[u][v];
                }
            }
        }

        return parent;
    }
}