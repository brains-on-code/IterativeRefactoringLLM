package com.thealgorithms.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConstrainedShortestPath {

    public static class Graph {

        private final List<List<Edge>> adjacencyList;

        public Graph(int numberOfNodes) {
            adjacencyList = new ArrayList<>();
            for (int nodeIndex = 0; nodeIndex < numberOfNodes; nodeIndex++) {
                adjacencyList.add(new ArrayList<>());
            }
        }

        public void addEdge(int sourceNode, int destinationNode, int cost, int resource) {
            adjacencyList.get(sourceNode).add(new Edge(sourceNode, destinationNode, cost, resource));
        }

        public List<Edge> getOutgoingEdges(int node) {
            return adjacencyList.get(node);
        }

        public int getNumberOfNodes() {
            return adjacencyList.size();
        }

        public record Edge(int sourceNode, int destinationNode, int cost, int resource) {
        }
    }

    private final Graph graph;
    private final int maxResource;

    public ConstrainedShortestPath(Graph graph, int maxResource) {
        this.graph = graph;
        this.maxResource = maxResource;
    }

    public int solve(int startNode, int targetNode) {
        int numberOfNodes = graph.getNumberOfNodes();
        int[][] minCostForResource = new int[maxResource + 1][numberOfNodes];

        for (int resourceUsed = 0; resourceUsed <= maxResource; resourceUsed++) {
            Arrays.fill(minCostForResource[resourceUsed], Integer.MAX_VALUE);
        }
        minCostForResource[0][startNode] = 0;

        for (int resourceUsed = 0; resourceUsed <= maxResource; resourceUsed++) {
            for (int currentNode = 0; currentNode < numberOfNodes; currentNode++) {
                if (minCostForResource[resourceUsed][currentNode] == Integer.MAX_VALUE) {
                    continue;
                }
                for (Graph.Edge edge : graph.getOutgoingEdges(currentNode)) {
                    int nextNode = edge.destinationNode();
                    int edgeCost = edge.cost();
                    int edgeResource = edge.resource();

                    int totalResourceUsed = resourceUsed + edgeResource;
                    if (totalResourceUsed <= maxResource) {
                        int newCost = minCostForResource[resourceUsed][currentNode] + edgeCost;
                        minCostForResource[totalResourceUsed][nextNode] =
                            Math.min(minCostForResource[totalResourceUsed][nextNode], newCost);
                    }
                }
            }
        }

        int minCost = Integer.MAX_VALUE;
        for (int resourceUsed = 0; resourceUsed <= maxResource; resourceUsed++) {
            minCost = Math.min(minCost, minCostForResource[resourceUsed][targetNode]);
        }

        return minCost == Integer.MAX_VALUE ? -1 : minCost;
    }
}