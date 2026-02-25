package com.thealgorithms.var7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Solver for the constrained shortest-path problem on a weighted directed graph.
 *
 * <p>Each edge has:
 * <ul>
 *   <li>a cost (what we minimize), and</li>
 *   <li>a weight (e.g., time, steps, or resource usage) that is constrained by a maximum.</li>
 * </ul>
 */
public class Class1 {

    /**
     * Directed graph with a cost and a weight on each edge.
     */
    public static class Class2 {

        private final List<List<Edge>> adjacencyList;

        /**
         * Constructs a directed graph with the specified number of vertices.
         *
         * @param vertexCount number of vertices in the graph
         */
        public Class2(int vertexCount) {
            adjacencyList = new ArrayList<>(vertexCount);
            for (int i = 0; i < vertexCount; i++) {
                adjacencyList.add(new ArrayList<>());
            }
        }

        /**
         * Adds a directed edge to the graph.
         *
         * @param from   source vertex
         * @param to     destination vertex
         * @param cost   edge cost (used in the objective)
         * @param weight edge weight (constrained quantity, e.g., time or steps)
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
         */
        public record Edge(int from, int to, int cost, int weight) {}
    }

    private final Class2 graph;
    private final int maxWeight;

    /**
     * Constructs a constrained shortest-path solver.
     *
     * @param graph     underlying directed graph
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
     * <p>Dynamic programming state:
     * <pre>
     * dp[w][v] = minimum cost to reach vertex v using total weight exactly w
     * </pre>
     *
     * @param start start vertex
     * @param end   end vertex
     * @return minimum cost if such a path exists; otherwise {@code -1}
     */
    public int method5(int start, int end) {
        int vertexCount = graph.vertexCount();
        int[][] dp = new int[maxWeight + 1][vertexCount];

        for (int w = 0; w <= maxWeight; w++) {
            Arrays.fill(dp[w], Integer.MAX_VALUE);
        }
        dp[0][start] = 0;

        for (int usedWeight = 0; usedWeight <= maxWeight; usedWeight++) {
            for (int vertex = 0; vertex < vertexCount; vertex++) {
                int currentCost = dp[usedWeight][vertex];
                if (currentCost == Integer.MAX_VALUE) {
                    continue;
                }
                for (Class2.Edge edge : graph.getEdges(vertex)) {
                    int nextVertex = edge.to();
                    int edgeCost = edge.cost();
                    int edgeWeight = edge.weight();

                    int newWeight = usedWeight + edgeWeight;
                    if (newWeight <= maxWeight) {
                        int newCost = currentCost + edgeCost;
                        dp[newWeight][nextVertex] =
                            Math.min(dp[newWeight][nextVertex], newCost);
                    }
                }
            }
        }

        int bestCost = Integer.MAX_VALUE;
        for (int usedWeight = 0; usedWeight <= maxWeight; usedWeight++) {
            bestCost = Math.min(bestCost, dp[usedWeight][end]);
        }

        return bestCost == Integer.MAX_VALUE ? -1 : bestCost;
    }
}