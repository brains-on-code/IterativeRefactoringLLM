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

        public void addEdge(int from, int to, int cost, int resource) {
            adjacencyList.get(from).add(new Edge(from, to, cost, resource));
        }

        public List<Edge> getEdges(int node) {
            return adjacencyList.get(node);
        }

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
     * Computes the minimum cost to reach {@code target} from {@code start}
     * without exceeding {@code maxResource}. Returns -1 if no such path exists.
     */
    public int solve(int start, int target) {
        int numNodes = graph.getNumNodes();
        int[][] dp = new int[maxResource + 1][numNodes];

        // dp[resourceUsed][node] = minimum cost to reach 'node'
        // using exactly 'resourceUsed' units of resource
        for (int resourceUsed = 0; resourceUsed <= maxResource; resourceUsed++) {
            Arrays.fill(dp[resourceUsed], Integer.MAX_VALUE);
        }
        dp[0][start] = 0;

        for (int resourceUsed = 0; resourceUsed <= maxResource; resourceUsed++) {
            for (int currentNode = 0; currentNode < numNodes; currentNode++) {
                int currentCost = dp[resourceUsed][currentNode];
                if (currentCost == Integer.MAX_VALUE) {
                    continue;
                }

                for (Graph.Edge edge : graph.getEdges(currentNode)) {
                    int nextNode = edge.to();
                    int nextResource = resourceUsed + edge.resource();

                    if (nextResource <= maxResource) {
                        int newCost = currentCost + edge.cost();
                        if (newCost < dp[nextResource][nextNode]) {
                            dp[nextResource][nextNode] = newCost;
                        }
                    }
                }
            }
        }

        int minCost = Integer.MAX_VALUE;
        for (int resourceUsed = 0; resourceUsed <= maxResource; resourceUsed++) {
            minCost = Math.min(minCost, dp[resourceUsed][target]);
        }

        return minCost == Integer.MAX_VALUE ? -1 : minCost;
    }
}