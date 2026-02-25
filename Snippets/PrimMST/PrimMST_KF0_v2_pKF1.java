package com.thealgorithms.datastructures.graphs;

/**
 * A Java program for Prim's Minimum Spanning Tree (MST) algorithm.
 * Adjacency matrix representation of the graph.
 */
public class PrimMST {

    // Number of vertices in the graph
    private static final int VERTEX_COUNT = 5;

    /**
     * Finds the vertex with the minimum key value from the set of vertices
     * not yet included in the MST.
     */
    int findMinKeyVertex(int[] key, boolean[] inMst) {
        int minKey = Integer.MAX_VALUE;
        int minVertex = -1;

        for (int vertex = 0; vertex < VERTEX_COUNT; vertex++) {
            if (!inMst[vertex] && key[vertex] < minKey) {
                minKey = key[vertex];
                minVertex = vertex;
            }
        }

        return minVertex;
    }

    /**
     * Constructs the MST for a graph represented using an adjacency matrix.
     *
     * @param adjacencyMatrix the adjacency matrix of the graph
     * @return an array where each index represents a vertex and the value at that
     *         index represents its parent in the MST
     */
    public int[] primMST(int[][] adjacencyMatrix) {
        int[] parent = new int[VERTEX_COUNT];      // Stores constructed MST
        int[] key = new int[VERTEX_COUNT];         // Key values to pick minimum weight edge
        boolean[] inMst = new boolean[VERTEX_COUNT]; // Tracks vertices included in MST

        // Initialize all keys as INFINITE and inMst[] as false
        for (int vertex = 0; vertex < VERTEX_COUNT; vertex++) {
            key[vertex] = Integer.MAX_VALUE;
            inMst[vertex] = false;
        }

        // Always include the first vertex in MST
        key[0] = 0;     // Make key 0 to pick the first vertex
        parent[0] = -1; // First node is always root of MST

        // The MST will have VERTEX_COUNT vertices
        for (int i = 0; i < VERTEX_COUNT - 1; i++) {
            // Pick the minimum key vertex not yet included in MST
            int currentVertex = findMinKeyVertex(key, inMst);
            inMst[currentVertex] = true;

            // Update key value and parent index of adjacent vertices of the picked vertex
            for (int adjacentVertex = 0; adjacentVertex < VERTEX_COUNT; adjacentVertex++) {
                int edgeWeight = adjacencyMatrix[currentVertex][adjacentVertex];

                if (edgeWeight != 0
                        && !inMst[adjacentVertex]
                        && edgeWeight < key[adjacentVertex]) {
                    parent[adjacentVertex] = currentVertex;
                    key[adjacentVertex] = edgeWeight;
                }
            }
        }

        return parent; // Return the MST parent array
    }
}