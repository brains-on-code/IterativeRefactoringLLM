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
    int findMinKeyVertex(int[] keyValues, Boolean[] isInMst) {
        int minKeyValue = Integer.MAX_VALUE;
        int minKeyVertexIndex = -1;

        for (int vertexIndex = 0; vertexIndex < VERTEX_COUNT; vertexIndex++) {
            if (!isInMst[vertexIndex] && keyValues[vertexIndex] < minKeyValue) {
                minKeyValue = keyValues[vertexIndex];
                minKeyVertexIndex = vertexIndex;
            }
        }

        return minKeyVertexIndex;
    }

    /**
     * Constructs the MST for a graph represented using an adjacency matrix.
     *
     * @param adjacencyMatrix the adjacency matrix of the graph
     * @return an array where each index represents a vertex and the value at that
     *         index represents its parent in the MST
     */
    public int[] primMST(int[][] adjacencyMatrix) {
        int[] parent = new int[VERTEX_COUNT]; // Stores constructed MST
        int[] keyValues = new int[VERTEX_COUNT]; // Key values to pick minimum weight edge
        Boolean[] isInMst = new Boolean[VERTEX_COUNT]; // Tracks vertices included in MST

        // Initialize all keys as INFINITE and isInMst[] as false
        for (int vertexIndex = 0; vertexIndex < VERTEX_COUNT; vertexIndex++) {
            keyValues[vertexIndex] = Integer.MAX_VALUE;
            isInMst[vertexIndex] = Boolean.FALSE;
        }

        // Always include the first vertex in MST
        keyValues[0] = 0; // Make key 0 to pick the first vertex
        parent[0] = -1; // First node is always root of MST

        // The MST will have VERTEX_COUNT vertices
        for (int edgeCount = 0; edgeCount < VERTEX_COUNT - 1; edgeCount++) {
            // Pick the minimum key vertex not yet included in MST
            int currentVertex = findMinKeyVertex(keyValues, isInMst);
            isInMst[currentVertex] = Boolean.TRUE;

            // Update key value and parent index of adjacent vertices of the picked vertex
            for (int adjacentVertex = 0; adjacentVertex < VERTEX_COUNT; adjacentVertex++) {
                if (adjacencyMatrix[currentVertex][adjacentVertex] != 0
                        && !isInMst[adjacentVertex]
                        && adjacencyMatrix[currentVertex][adjacentVertex] < keyValues[adjacentVertex]) {
                    parent[adjacentVertex] = currentVertex;
                    keyValues[adjacentVertex] = adjacencyMatrix[currentVertex][adjacentVertex];
                }
            }
        }

        return parent; // Return the MST parent array
    }
}