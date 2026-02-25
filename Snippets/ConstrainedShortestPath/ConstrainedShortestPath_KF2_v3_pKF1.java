package com.thealgorithms.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConstrainedShortestPath {

    public static class Graph {

        private final List<List<Edge>> adjacencyList;

        public Graph(int nodeCount) {
            adjacencyList = new ArrayList<>();
            for (int nodeIndex = 0; nodeIndex < nodeCount; nodeIndex++) {
                adjacencyList.add(new ArrayList<>());
            }
        }

        public void addEdge(int source, int destination, int cost, int resource) {
            adjacencyList.get(source).add(new Edge(source, destination, cost, resource));
        }

        public List<Edge> getOutgoingEdges(int node) {
            return adjacencyList.get(node);
        }

        public int getNodeCount() {
            return adjacencyList.size();
        }

        public record Edge(int source, int destination, int cost, int resource) {
        }
    }

    private final Graph graph;
    private final int maxResource;

    public ConstrainedShortestPath(Graph graph, int maxResource) {
        this.graph = graph;
        this.maxResource = maxResource;
    }

    public int solve(int startNode, int targetNode) {
        int nodeCount = graph.getNodeCount();
        int[][] minCostByResourceAndNode = new int[maxResource + 1][nodeCount];

        for (int resourceUsed = 0; resourceUsed <= maxResource; resourceUsed++) {
            Arrays.fill(minCostByResourceAndNode[resourceUsed], Integer.MAX_VALUE);
        }
        minCostByResourceAndNode[0][startNode] = 0;

        for (int resourceUsed = 0; resourceUsed <= maxResource; resourceUsed++) {
            for (int currentNode = 0; currentNode < nodeCount; currentNode++) {
                int currentCost = minCostByResourceAndNode[resourceUsed][currentNode];
                if (currentCost == Integer.MAX_VALUE) {
                    continue;
                }
                for (Graph.Edge edge : graph.getOutgoingEdges(currentNode)) {
                    int nextNode = edge.destination();
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

        int minCostToTarget = Integer.MAX_VALUE;
        for (int resourceUsed = 0; resourceUsed <= maxResource; resourceUsed++) {
            minCostToTarget = Math.min(minCostToTarget, minCostByResourceAndNode[resourceUsed][targetNode]);
        }

        return minCostToTarget == Integer.MAX_VALUE ? -1 : minCostToTarget;
    }
}