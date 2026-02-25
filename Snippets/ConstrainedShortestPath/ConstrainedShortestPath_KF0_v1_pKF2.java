package com.thealgorithms.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Solves the Constrained Shortest Path Problem (CSPP),
 * also known as the Shortest Path Problem with Resource Constraints (SPPRC).
 * Finds the minimum-cost path between two nodes such that the total resource
 * consumption does not exceed a given limit.
 *
 * @author <a href="https://github.com/DenizAltunkapan">Deniz Altunkapan</a>
 */
public class ConstrainedShortestPath {

    /**
     * Directed graph with edge costs and resource consumptions.
     */
    public static class Graph {

        private final List<List<Edge>> adjacencyList;

        public Graph(int numNodes) {
            adjacencyList = new ArrayList<>(numNodes);
            for (int i = 0; i < numNodes; i++) {
                adjacencyList.add(new ArrayList<>());
            }
        }

        /**
         * Adds a directed edge (from -> to) with the given cost and resource.
         */
        public void addEdge(int from, int to, int cost, int resource) {
            adjacencyList.get(from).add(new Edge(from, to, cost, resource));
        }

        /**
         * Returns all outgoing edges of the given node.
         */
        public List<Edge> getEdges(int node) {
            return adjacencyList.get(node);
        }

        /**
         * Returns the number of nodes in the graph.
         */
        public int getNumNodes() {
            return adjacencyList.size();
        }

        public record Edge(int from, int to, int cost, int resource) {}
    }

    private final Graph graph;
    private final int maxResource;

    /**
     * @param graph       problem graph
     * @param maxResource maximum allowed total resource consumption
     */
    public ConstrainedShortestPath(Graph graph, int maxResource) {
        this.graph = graph;
        this.maxResource = maxResource;
    }

    /**
     * Returns the minimum cost to reach {@code target} from {@code start}
     * without exceeding {@code maxResource}, or -1 if no such path exists.
     */
    public int solve(int start, int target) {
        int numNodes = graph.getNumNodes();
        int[][] dp = new int[maxResource + 1][numNodes];

        // dp[r][u] = minimum cost to reach node u using exactly r resource units
        for (int r = 0; r <= maxResource; r++) {
            Arrays.fill(dp[r], Integer.MAX_VALUE);
        }
        dp[0][start] = 0;

        // Relax edges for all feasible resource levels
        for (int r = 0; r <= maxResource; r++) {
            for (int u = 0; u < numNodes; u++) {
                if (dp[r][u] == Integer.MAX_VALUE) {
                    continue;
                }
                for (Graph.Edge edge : graph.getEdges(u)) {
                    int v = edge.to();
                    int cost = edge.cost();
                    int resource = edge.resource();
                    int nextResource = r + resource;

                    if (nextResource <= maxResource) {
                        dp[nextResource][v] =
                            Math.min(dp[nextResource][v], dp[r][u] + cost);
                    }
                }
            }
        }

        // Best cost to reach target with any resource usage up to maxResource
        int minCost = Integer.MAX_VALUE;
        for (int r = 0; r <= maxResource; r++) {
            minCost = Math.min(minCost, dp[r][target]);
        }

        return minCost == Integer.MAX_VALUE ? -1 : minCost;
    }
}