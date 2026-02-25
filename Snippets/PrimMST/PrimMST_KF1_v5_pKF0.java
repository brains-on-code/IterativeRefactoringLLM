package com.thealgorithms.datastructures.graphs;

/**
 * Implements Prim's algorithm to find the Minimum Spanning Tree (MST)
 * for a graph represented by an adjacency matrix.
 */
public class PrimMST {

    private static final int NO_PARENT = -1;
    private static final int INF = Integer.MAX_VALUE;

    /**
     * Finds the index of the vertex with the minimum key value from the set
     * of vertices not yet included in the MST.
     *
     * @param key   array of key values
     * @param inMst array indicating whether a vertex is included in MST
     * @return index of the vertex with the minimum key value
     */
    private int getMinKeyVertex(int[] key, boolean[] inMst) {
        int minKey = INF;
        int minIndex = NO_PARENT;

        for (int vertex = 0; vertex < key.length; vertex++) {
            boolean isCandidate = !inMst[vertex] && key[vertex] < minKey;
            if (isCandidate) {
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
        int vertexCount = graph.length;
        int[] parent = new int[vertexCount];
        int[] key = new int[vertexCount];
        boolean[] inMst = new boolean[vertexCount];

        initializeKeysAndMstFlags(vertexCount, key, inMst);

        key[0] = 0;
        parent[0] = NO_PARENT;

        for (int i = 0; i < vertexCount - 1; i++) {
            int currentVertex = getMinKeyVertex(key, inMst);
            if (currentVertex == NO_PARENT) {
                break;
            }

            inMst[currentVertex] = true;
            updateAdjacentVertices(graph, currentVertex, key, parent, inMst);
        }

        return parent;
    }

    private void initializeKeysAndMstFlags(int vertexCount, int[] key, boolean[] inMst) {
        for (int i = 0; i < vertexCount; i++) {
            key[i] = INF;
            inMst[i] = false;
        }
    }

    private void updateAdjacentVertices(
            int[][] graph,
            int currentVertex,
            int[] key,
            int[] parent,
            boolean[] inMst
    ) {
        int vertexCount = graph.length;

        for (int adjacentVertex = 0; adjacentVertex < vertexCount; adjacentVertex++) {
            int weight = graph[currentVertex][adjacentVertex];

            boolean isEdgePresent = weight != 0;
            boolean isNotInMst = !inMst[adjacentVertex];
            boolean isBetterKey = weight < key[adjacentVertex];

            if (isEdgePresent && isNotInMst && isBetterKey) {
                parent[adjacentVertex] = currentVertex;
                key[adjacentVertex] = weight;
            }
        }
    }
}