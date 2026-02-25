package com.thealgorithms.var7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a weighted directed graph and a constrained shortest-path solver.
 */
public class Class1 {

    /**
     * Directed graph where each edge has a cost and a time (or step) weight.
     */
    public static class Class2 {

        private final List<List<Edge>> adjacencyList;

        /**
         * Creates a graph with the given number of vertices.
         *
         * @param vertexCount number of vertices in the graph
         */
        public Class2(int vertexCount) {
            adjacencyList = new ArrayList<>();
            for (int i = 0; i < vertexCount; i++) {
                adjacencyList.add(new ArrayList<>());
            }
        }

        /**
         * Adds a directed edge to the graph.
         *
         * @param from   source vertex
         * @param to     destination vertex
         * @param cost   edge cost
         * @param weight edge weight (e.g., time or steps)
         */
        public void addEdge(int from, int to, int cost, int weight) {
            adjacencyList.get(from).add(new Edge(from, to, cost, weight));
        }

        /**
         * Returns all outgoing edges from the given vertex.
         *
         * @param vertex vertex index
         * @return list of outgoing edges
         */
        public List<Edge> getEdges(int vertex) {
            return adjacencyList.get(vertex);
        }

        /**
         * Returns the number of vertices in the graph.
         *
         * @return vertex count
         */
        public int vertexCount() {
            return adjacencyList.size();
        }

        /**
         * Immutable representation of a directed edge.
         *
         * @param from   source vertex
         * @param to     destination vertex
         * @param cost   edge cost
         * @param weight edge weight (e.g., time or steps)
         */
        public record Edge(int from, int to, int cost, int weight) {
        }
    }

    private final Class2 graph;
    private final int maxWeight;

    /**
     * Creates a constrained shortest-path solver.
     *
     * @param graph     underlying graph
     * @param maxWeight maximum total weight allowed on a path
     */
    public Class1(Class2 graph, int maxWeight) {
        this.graph = graph;
        this.maxWeight = maxWeight;
    }

    /**
     * Computes the minimum-cost path from {@code start} to {@code end} such that
     * the sum of edge weights on the path does not exceed {@code maxWeight}.
     *
     * @param start start vertex
     * @param end   end vertex
     * @return minimum cost if such a path exists; otherwise {@code -1}
     */
    public int method5(int start, int end) {
        int vertexCount = graph.vertexCount();
        int[][] dp = new int[maxWeight + 1][vertexCount];

        // Initialize DP table with "infinity"
        for (int w = 0; w <= maxWeight; w++) {
            Arrays.fill(dp[w], Integer.MAX_VALUE);
        }
        dp[0][start] = 0;

        // Relax edges for all feasible accumulated weights
        for (int usedWeight = 0; usedWeight <= maxWeight; usedWeight++) {
            for (int v = 0; v < vertexCount; v++) {
                if (dp[usedWeight][v] == Integer.MAX_VALUE) {
                    continue;
                }
                for (Class2.Edge edge : graph.getEdges(v)) {
                    int nextVertex = edge.to();
                    int edgeCost = edge.cost();
                    int edgeWeight = edge.weight();

                    int newWeight = usedWeight + edgeWeight;
                    if (newWeight <= maxWeight) {
                        dp[newWeight][nextVertex] =
                            Math.min(dp[newWeight][nextVertex], dp[usedWeight][v] + edgeCost);
                    }
                }
            }
        }

        // Find the best cost to reach 'end' with any allowed total weight
        int bestCost = Integer.MAX_VALUE;
        for (int usedWeight = 0; usedWeight <= maxWeight; usedWeight++) {
            bestCost = Math.min(bestCost, dp[usedWeight][end]);
        }

        return bestCost == Integer.MAX_VALUE ? -1 : bestCost;
    }
}