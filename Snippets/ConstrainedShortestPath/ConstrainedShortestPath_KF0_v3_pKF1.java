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
            for (int nodeIndex = 0; nodeIndex < nodeCount; nodeIndex++) {
                adjacencyList.add(new ArrayList<>());
            }
        }

        /**
         * Adds an edge to the graph.
         *
         * @param fromNode the starting node
         * @param toNode the ending node
         * @param cost the cost of the edge
         * @param resource the resource required to traverse the edge
         */
        public void addEdge(int fromNode, int toNode, int cost, int resource) {
            adjacencyList.get(fromNode).add(new Edge(fromNode, toNode, cost, resource));
        }

        /**
         * Gets the edges that are adjacent to a given node.
         *
         * @param node the node to get the edges for
         * @return the list of edges adjacent to the node
         */
        public List<Edge> getOutgoingEdges(int node) {
            return adjacencyList.get(node);
        }

        /**
         * Gets the number of nodes in the graph.
         *
         * @return the number of nodes
         */
        public int getNodeCount() {
            return adjacencyList.size();
        }

        public record Edge(int fromNode, int toNode, int cost, int resource) {
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
     * @param startNode  the starting node
     * @param targetNode the target node
     * @return the minimum cost to reach the target node within the resource constraint,
     *         or -1 if no valid path exists
     */
    public int solve(int startNode, int targetNode) {
        int nodeCount = graph.getNodeCount();
        int[][] minCostByResourceAndNode = new int[maxResource + 1][nodeCount];

        // Initialize DP table with maximum values
        for (int resourceUsed = 0; resourceUsed <= maxResource; resourceUsed++) {
            Arrays.fill(minCostByResourceAndNode[resourceUsed], Integer.MAX_VALUE);
        }
        minCostByResourceAndNode[0][startNode] = 0;

        // Dynamic Programming: Iterate over resources and nodes
        for (int resourceUsed = 0; resourceUsed <= maxResource; resourceUsed++) {
            for (int currentNode = 0; currentNode < nodeCount; currentNode++) {
                int currentCost = minCostByResourceAndNode[resourceUsed][currentNode];
                if (currentCost == Integer.MAX_VALUE) {
                    continue;
                }
                for (Graph.Edge edge : graph.getOutgoingEdges(currentNode)) {
                    int nextNode = edge.toNode();
                    int edgeCost = edge.cost();
                    int edgeResource = edge.resource();

                    int totalResourceUsed = resourceUsed + edgeResource;
                    if (totalResourceUsed <= maxResource) {
                        int newCost = currentCost + edgeCost;
                        minCostByResourceAndNode[totalResourceUsed][nextNode] =
                            Math.min(minCostByResourceAndNode[totalResourceUsed][nextNode], newCost);
                    }
                }
            }
        }

        // Find the minimum cost to reach the target node
        int minCostToTarget = Integer.MAX_VALUE;
        for (int resourceUsed = 0; resourceUsed <= maxResource; resourceUsed++) {
            minCostToTarget = Math.min(minCostToTarget, minCostByResourceAndNode[resourceUsed][targetNode]);
        }

        return minCostToTarget == Integer.MAX_VALUE ? -1 : minCostToTarget;
    }
}