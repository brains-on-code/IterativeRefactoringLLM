package com.thealgorithms.datastructures.graphs;

/**
 * Prim's algorithm for computing the Minimum Spanning Tree (MST)
 * of a weighted, undirected graph represented by an adjacency matrix.
 */
public class PrimMST {

    /** Number of vertices in the graph. */
    private static final int V = 5;

    /**
     * Returns the index of the vertex with the smallest key value
     * among the vertices not yet included in the MST.
     *
     * @param key   key[v] is the minimum weight edge connecting v to the MST
     * @param inMst inMst[v] is true if vertex v is already in the MST
     */
    int minKey(int[] key, boolean[] inMst) {
        int minKeyValue = Integer.MAX_VALUE;
        int minKeyIndex = -1;

        for (int vertex = 0; vertex < V; vertex++) {
            if (!inMst[vertex] && key[vertex] < minKeyValue) {
                minKeyValue = key[vertex];
                minKeyIndex = vertex;
            }
        }

        return minKeyIndex;
    }

    /**
     * Computes the MST of a graph using Prim's algorithm.
     *
     * @param graph adjacency matrix; graph[u][v] is the weight of edge (u, v),
     *              or 0 if no edge exists
     * @return parent array where parent[v] is the parent of v in the MST;
     *         parent[0] is -1 as it is the root of the MST
     */
    public int[] primMST(int[][] graph) {
        int[] parent = new int[V];
        int[] key = new int[V];
        boolean[] inMst = new boolean[V];

        initializeKeysAndMstState(key, inMst);

        // Start MST from vertex 0
        key[0] = 0;
        parent[0] = -1;

        // MST will have V - 1 edges
        for (int edgesInMst = 0; edgesInMst < V - 1; edgesInMst++) {
            int u = minKey(key, inMst);
            inMst[u] = true;

            updateAdjacentVertices(graph, parent, key, inMst, u);
        }

        return parent;
    }

    /**
     * Initializes all keys to "infinity" and marks all vertices as not in MST.
     */
    private void initializeKeysAndMstState(int[] key, boolean[] inMst) {
        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            inMst[i] = false;
        }
    }

    /**
     * Updates key and parent for vertices adjacent to u.
     *
     * @param graph  adjacency matrix of the graph
     * @param parent parent[v] will store the parent of v in the MST
     * @param key    key[v] is the minimum weight edge connecting v to the MST
     * @param inMst  inMst[v] is true if vertex v is already in the MST
     * @param u      the vertex just added to the MST
     */
    private void updateAdjacentVertices(
        int[][] graph,
        int[] parent,
        int[] key,
        boolean[] inMst,
        int u
    ) {
        for (int v = 0; v < V; v++) {
            int weight = graph[u][v];

            // Update key and parent only if:
            // 1) there is an edge from u to v,
            // 2) v is not yet in the MST, and
            // 3) the edge (u, v) is lighter than the current key[v].
            if (weight != 0 && !inMst[v] && weight < key[v]) {
                parent[v] = u;
                key[v] = weight;
            }
        }
    }
}