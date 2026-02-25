package com.thealgorithms.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class implements a solution for the Constrained Shortest Path Problem (CSPP),
 * also known as Shortest Path Problem with Resource Constraints (SPPRC).
 * The goal is to find the shortest path between two nodes while ensuring that
 * the resource constraint is not exceeded.
 *
 * @author <a href="https://github.com/DenizAltunkapan">Deniz Altunkapan</a>
 */
public class ConstrainedShortestPath {

    /**
     * Represents a graph using an adjacency list.
     * This graph is designed for the Constrained Shortest Path Problem (CSPP).
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
         * Adds a directed edge to the graph.
         *
         * @param from     the starting node
         * @param to       the ending node
         * @param cost     the cost of the edge
         * @param resource the resource required to traverse the edge
         */
        public void addEdge(int from, int to, int cost, int resource) {
            adjacencyList.get(from).add(new Edge(from, to, cost, resource));
        }

        /**
         * Gets the edges that are adjacent to a given node.
         *
         * @param node the node to get the edges for
         * @return the list of edges adjacent to the node
         */
        public List<Edge> getEdges(int node) {
            return adjacencyList.get(node);
        }

        /**
         * Gets the number of nodes in the graph.
         *
         * @return the number of nodes
         */
        public int getNumNodes() {
            return adjacencyList.size();
        }

        public record Edge(int from, int to, int cost, int resource) {}
    }

    private final Graph graph;
    private final int maxResource;

    /**
     * Constructs a ConstrainedShortestPath solver with the given graph and maximum
     * resource constraint.
     *
     * @param graph       the graph representing the problem
     * @param maxResource the maximum allowable resource
     */
    public ConstrainedShortestPath(Graph graph, int maxResource) {
        this.graph = graph;
        this.maxResource = maxResource;
    }

    /**
     * Solves the CSP to find the shortest path from the start node to the target node
     * without exceeding the resource constraint.
     *
     * @param start  the starting node
     * @param target the target node
     * @return the minimum cost to reach the target node within the resource constraint,
     *         or -1 if no valid path exists
     */
    public int solve(int start, int target) {
        int numNodes = graph.getNumNodes();
        int[][] dp = new int[maxResource + 1][numNodes];

        initializeDpTable(dp, start);
        fillDpTable(dp, numNodes);
        return findMinCostToTarget(dp, target);
    }

    private void initializeDpTable(int[][] dp, int start) {
        for (int resource = 0; resource <= maxResource; resource++) {
            Arrays.fill(dp[resource], Integer.MAX_VALUE);
        }
        dp[0][start] = 0;
    }

    private void fillDpTable(int[][] dp, int numNodes) {
        for (int resourceUsed = 0; resourceUsed <= maxResource; resourceUsed++) {
            for (int node = 0; node < numNodes; node++) {
                int currentCost = dp[resourceUsed][node];
                if (currentCost == Integer.MAX_VALUE) {
                    continue;
                }
                relaxOutgoingEdges(dp, resourceUsed, node, currentCost);
            }
        }
    }

    private void relaxOutgoingEdges(int[][] dp, int resourceUsed, int node, int currentCost) {
        for (Graph.Edge edge : graph.getEdges(node)) {
            int nextNode = edge.to();
            int edgeCost = edge.cost();
            int edgeResource = edge.resource();

            int newResourceUsed = resourceUsed + edgeResource;
            if (newResourceUsed > maxResource) {
                continue;
            }

            int newCost = currentCost + edgeCost;
            if (newCost < dp[newResourceUsed][nextNode]) {
                dp[newResourceUsed][nextNode] = newCost;
            }
        }
    }

    private int findMinCostToTarget(int[][] dp, int target) {
        int minCost = Integer.MAX_VALUE;
        for (int resourceUsed = 0; resourceUsed <= maxResource; resourceUsed++) {
            minCost = Math.min(minCost, dp[resourceUsed][target]);
        }
        return minCost == Integer.MAX_VALUE ? -1 : minCost;
    }
}