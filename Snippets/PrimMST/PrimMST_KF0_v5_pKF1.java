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
    int findMinKeyVertex(int[] minEdgeWeights, boolean[] isVertexInMst) {
        int minWeight = Integer.MAX_VALUE;
        int minWeightVertexIndex = -1;

        for (int vertexIndex = 0; vertexIndex < VERTEX_COUNT; vertexIndex++) {
            if (!isVertexInMst[vertexIndex] && minEdgeWeights[vertexIndex] < minWeight) {
                minWeight = minEdgeWeights[vertexIndex];
                minWeightVertexIndex = vertexIndex;
            }
        }

        return minWeightVertexIndex;
    }

    /**
     * Constructs the MST for a graph represented using an adjacency matrix.
     *
     * @param adjacencyMatrix the adjacency matrix of the graph
     * @return an array where each index represents a vertex and the value at that
     *         index represents its parent in the MST
     */
    public int[] primMST(int[][] adjacencyMatrix) {
        int[] parent = new int[VERTEX_COUNT];          // Stores constructed MST
        int[] minEdgeWeights = new int[VERTEX_COUNT];  // Minimum edge weight to connect each vertex
        boolean[] inMst = new boolean[VERTEX_COUNT];   // Tracks vertices included in MST

        // Initialize all keys as INFINITE and inMst[] as false
        for (int vertex = 0; vertex < VERTEX_COUNT; vertex++) {
            minEdgeWeights[vertex] = Integer.MAX_VALUE;
            inMst[vertex] = false;
        }

        // Always include the first vertex in MST
        minEdgeWeights[0] = 0;   // Make key 0 to pick the first vertex
        parent[0] = -1;          // First node is always root of MST

        // The MST will have VERTEX_COUNT vertices
        for (int edgeCount = 0; edgeCount < VERTEX_COUNT - 1; edgeCount++) {
            // Pick the minimum key vertex not yet included in MST
            int currentVertex = findMinKeyVertex(minEdgeWeights, inMst);
            inMst[currentVertex] = true;

            // Update key value and parent index of adjacent vertices of the picked vertex
            for (int neighbor = 0; neighbor < VERTEX_COUNT; neighbor++) {
                int edgeWeight = adjacencyMatrix[currentVertex][neighbor];

                if (edgeWeight != 0
                        && !inMst[neighbor]
                        && edgeWeight < minEdgeWeights[neighbor]) {
                    parent[neighbor] = currentVertex;
                    minEdgeWeights[neighbor] = edgeWeight;
                }
            }
        }

        return parent; // Return the MST parent array
    }
}