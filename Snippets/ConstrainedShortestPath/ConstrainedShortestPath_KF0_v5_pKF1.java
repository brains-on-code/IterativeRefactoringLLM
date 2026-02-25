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
 * @author  <a href="https://github.com/DenizAltunkapan">Deniz Altunkapan</a>
 */
public class ConstrainedShortestPath {

    /**
     * Represents a graph using an adjacency list.
     * This graph is designed for the Constrained Shortest Path Problem (CSPP).
     */
    public static class Graph {

        private final List<List<Edge>> adjacencyList;

        public Graph(int nodeCount) {
            adjacencyList = new ArrayList<>();
            for (int nodeId = 0; nodeId < nodeCount; nodeId++) {
                adjacencyList.add(new ArrayList<>());
            }
        }

        /**
         * Adds an edge to the graph.
         *
         * @param fromNodeId the starting node
         * @param toNodeId   the ending node
         * @param cost       the cost of the edge
         * @param resource   the resource required to traverse the edge
         */
        public void addEdge(int fromNodeId, int toNodeId, int cost, int resource) {
            adjacencyList.get(fromNodeId).add(new Edge(fromNodeId, toNodeId, cost, resource));
        }

        /**
         * Gets the edges that are adjacent to a given node.
         *
         * @param nodeId the node to get the edges for
         * @return the list of edges adjacent to the node
         */
        public List<Edge> getOutgoingEdges(int nodeId) {
            return adjacencyList.get(nodeId);
        }

        /**
         * Gets the number of nodes in the graph.
         *
         * @return the number of nodes
         */
        public int getNodeCount() {
            return adjacencyList.size();
        }

        public record Edge(int fromNodeId, int toNodeId, int cost, int resource) {
        }
    }

    private final Graph graph;
    private final int maxResource;

    /**
     * Constructs a ConstrainedShortestPath solver with the given graph and maximum resource constraint.
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
     * @param startNodeId  the starting node
     * @param targetNodeId the target node
     * @return the minimum cost to reach the target node within the resource constraint,
     *         or -1 if no valid path exists
     */
    public int solve(int startNodeId, int targetNodeId) {
        int nodeCount = graph.getNodeCount();
        int[][] minCostByResourceAndNode = new int[maxResource + 1][nodeCount];

        // Initialize DP table with maximum values
        for (int usedResource = 0; usedResource <= maxResource; usedResource++) {
            Arrays.fill(minCostByResourceAndNode[usedResource], Integer.MAX_VALUE);
        }
        minCostByResourceAndNode[0][startNodeId] = 0;

        // Dynamic Programming: Iterate over resources and nodes
        for (int usedResource = 0; usedResource <= maxResource; usedResource++) {
            for (int currentNodeId = 0; currentNodeId < nodeCount; currentNodeId++) {
                int currentCost = minCostByResourceAndNode[usedResource][currentNodeId];
                if (currentCost == Integer.MAX_VALUE) {
                    continue;
                }
                for (Graph.Edge edge : graph.getOutgoingEdges(currentNodeId)) {
                    int nextNodeId = edge.toNodeId();
                    int edgeCost = edge.cost();
                    int edgeResource = edge.resource();

                    int totalUsedResource = usedResource + edgeResource;
                    if (totalUsedResource <= maxResource) {
                        int newCost = currentCost + edgeCost;
                        minCostByResourceAndNode[totalUsedResource][nextNodeId] =
                            Math.min(minCostByResourceAndNode[totalUsedResource][nextNodeId], newCost);
                    }
                }
            }
        }

        // Find the minimum cost to reach the target node
        int minCostToTarget = Integer.MAX_VALUE;
        for (int usedResource = 0; usedResource <= maxResource; usedResource++) {
            minCostToTarget = Math.min(minCostToTarget, minCostByResourceAndNode[usedResource][targetNodeId]);
        }

        return minCostToTarget == Integer.MAX_VALUE ? -1 : minCostToTarget;
    }
}