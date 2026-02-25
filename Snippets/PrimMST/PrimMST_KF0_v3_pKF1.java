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
    int findMinKeyVertex(int[] minEdgeWeights, boolean[] isInMst) {
        int smallestWeight = Integer.MAX_VALUE;
        int smallestWeightVertex = -1;

        for (int vertex = 0; vertex < VERTEX_COUNT; vertex++) {
            if (!isInMst[vertex] && minEdgeWeights[vertex] < smallestWeight) {
                smallestWeight = minEdgeWeights[vertex];
                smallestWeightVertex = vertex;
            }
        }

        return smallestWeightVertex;
    }

    /**
     * Constructs the MST for a graph represented using an adjacency matrix.
     *
     * @param adjacencyMatrix the adjacency matrix of the graph
     * @return an array where each index represents a vertex and the value at that
     *         index represents its parent in the MST
     */
    public int[] primMST(int[][] adjacencyMatrix) {
        int[] parentVertex = new int[VERTEX_COUNT];        // Stores constructed MST
        int[] minEdgeWeights = new int[VERTEX_COUNT];      // Minimum edge weight to connect each vertex
        boolean[] isInMst = new boolean[VERTEX_COUNT];     // Tracks vertices included in MST

        // Initialize all keys as INFINITE and isInMst[] as false
        for (int vertex = 0; vertex < VERTEX_COUNT; vertex++) {
            minEdgeWeights[vertex] = Integer.MAX_VALUE;
            isInMst[vertex] = false;
        }

        // Always include the first vertex in MST
        minEdgeWeights[0] = 0;     // Make key 0 to pick the first vertex
        parentVertex[0] = -1;      // First node is always root of MST

        // The MST will have VERTEX_COUNT vertices
        for (int edgeCount = 0; edgeCount < VERTEX_COUNT - 1; edgeCount++) {
            // Pick the minimum key vertex not yet included in MST
            int currentVertex = findMinKeyVertex(minEdgeWeights, isInMst);
            isInMst[currentVertex] = true;

            // Update key value and parent index of adjacent vertices of the picked vertex
            for (int neighborVertex = 0; neighborVertex < VERTEX_COUNT; neighborVertex++) {
                int edgeWeight = adjacencyMatrix[currentVertex][neighborVertex];

                if (edgeWeight != 0
                        && !isInMst[neighborVertex]
                        && edgeWeight < minEdgeWeights[neighborVertex]) {
                    parentVertex[neighborVertex] = currentVertex;
                    minEdgeWeights[neighborVertex] = edgeWeight;
                }
            }
        }

        return parentVertex; // Return the MST parent array
    }
}