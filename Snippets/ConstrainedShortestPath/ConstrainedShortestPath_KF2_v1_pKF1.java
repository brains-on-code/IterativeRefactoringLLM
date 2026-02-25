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

        public void addEdge(int fromNode, int toNode, int cost, int resource) {
            adjacencyList.get(fromNode).add(new Edge(fromNode, toNode, cost, resource));
        }

        public List<Edge> getEdges(int node) {
            return adjacencyList.get(node);
        }

        public int getNodeCount() {
            return adjacencyList.size();
        }

        public record Edge(int fromNode, int toNode, int cost, int resource) {
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
        int[][] minCostWithResource = new int[maxResource + 1][nodeCount];

        for (int resourceUsed = 0; resourceUsed <= maxResource; resourceUsed++) {
            Arrays.fill(minCostWithResource[resourceUsed], Integer.MAX_VALUE);
        }
        minCostWithResource[0][startNode] = 0;

        for (int resourceUsed = 0; resourceUsed <= maxResource; resourceUsed++) {
            for (int currentNode = 0; currentNode < nodeCount; currentNode++) {
                if (minCostWithResource[resourceUsed][currentNode] == Integer.MAX_VALUE) {
                    continue;
                }
                for (Graph.Edge edge : graph.getEdges(currentNode)) {
                    int nextNode = edge.toNode();
                    int edgeCost = edge.cost();
                    int edgeResource = edge.resource();

                    int newResourceUsed = resourceUsed + edgeResource;
                    if (newResourceUsed <= maxResource) {
                        int newCost = minCostWithResource[resourceUsed][currentNode] + edgeCost;
                        minCostWithResource[newResourceUsed][nextNode] =
                            Math.min(minCostWithResource[newResourceUsed][nextNode], newCost);
                    }
                }
            }
        }

        int minCost = Integer.MAX_VALUE;
        for (int resourceUsed = 0; resourceUsed <= maxResource; resourceUsed++) {
            minCost = Math.min(minCost, minCostWithResource[resourceUsed][targetNode]);
        }

        return minCost == Integer.MAX_VALUE ? -1 : minCost;
    }
}