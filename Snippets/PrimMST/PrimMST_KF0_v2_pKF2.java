package com.thealgorithms.datastructures.graphs;

/**
 * Implementation of Prim's algorithm to compute the Minimum Spanning Tree (MST)
 * of a weighted, undirected graph represented by an adjacency matrix.
 */
public class PrimMST {

    /** Number of vertices in the graph. */
    private static final int V = 5;

    /**
     * Returns the index of the vertex with the smallest key value among the
     * vertices that are not yet included in the MST.
     *
     * @param key   array of key values for each vertex
     * @param inMst array indicating whether a vertex is already in the MST
     * @return index of the vertex with the minimum key value
     */
    int minKey(int[] key, boolean[] inMst) {
        int minKeyValue = Integer.MAX_VALUE;
        int minKeyIndex = -1;

        for (int vertex = 0; vertex < V; vertex++) {
            boolean isCandidate = !inMst[vertex];
            boolean hasSmallerKey = key[vertex] < minKeyValue;

            if (isCandidate && hasSmallerKey) {
                minKeyValue = key[vertex];
                minKeyIndex = vertex;
            }
        }

        return minKeyIndex;
    }

    /**
     * Computes the MST of a graph using Prim's algorithm.
     *
     * @param graph adjacency matrix of the graph; graph[u][v] is the weight of
     *              the edge between vertices u and v, or 0 if no edge exists
     * @return an array where parent[v] is the parent of vertex v in the MST;
     *         parent[0] is -1 as it is the root of the MST
     */
    public int[] primMST(int[][] graph) {
        int[] parent = new int[V];
        int[] key = new int[V];
        boolean[] inMst = new boolean[V];

        initializeKeysAndMstState(key, inMst);

        key[0] = 0;
        parent[0] = -1;

        for (int edgesInMst = 0; edgesInMst < V - 1; edgesInMst++) {
            int u = minKey(key, inMst);
            inMst[u] = true;

            updateAdjacentVertices(graph, parent, key, inMst, u);
        }

        return parent;
    }

    private void initializeKeysAndMstState(int[] key, boolean[] inMst) {
        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            inMst[i] = false;
        }
    }

    private void updateAdjacentVertices(
        int[][] graph,
        int[] parent,
        int[] key,
        boolean[] inMst,
        int u
    ) {
        for (int v = 0; v < V; v++) {
            int weight = graph[u][v];
            boolean hasEdge = weight != 0;
            boolean notInMst = !inMst[v];
            boolean isBetterEdge = weight < key[v];

            if (hasEdge && notInMst && isBetterEdge) {
                parent[v] = u;
                key[v] = weight;
            }
        }
    }
}