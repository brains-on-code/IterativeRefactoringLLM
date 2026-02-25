package com.thealgorithms.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConstrainedShortestPath {

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

    public ConstrainedShortestPath(Graph graph, int maxResource) {
        this.graph = graph;
        this.maxResource = maxResource;
    }

    public int solve(int start, int target) {
        int numNodes = graph.getNumNodes();
        int[][] minCostForResource = createInitializedCostTable(numNodes);

        minCostForResource[0][start] = 0;
        relaxAllEdges(numNodes, minCostForResource);

        int minCost = getMinCostToTarget(target, minCostForResource);
        return minCost == Integer.MAX_VALUE ? -1 : minCost;
    }

    private int[][] createInitializedCostTable(int numNodes) {
        int[][] costTable = new int[maxResource + 1][numNodes];
        for (int resource = 0; resource <= maxResource; resource++) {
            Arrays.fill(costTable[resource], Integer.MAX_VALUE);
        }
        return costTable;
    }

    private void relaxAllEdges(int numNodes, int[][] costTable) {
        for (int usedResource = 0; usedResource <= maxResource; usedResource++) {
            for (int node = 0; node < numNodes; node++) {
                int currentCost = costTable[usedResource][node];
                if (currentCost == Integer.MAX_VALUE) {
                    continue;
                }
                relaxOutgoingEdges(node, usedResource, currentCost, costTable);
            }
        }
    }

    private void relaxOutgoingEdges(int node, int usedResource, int currentCost, int[][] costTable) {
        for (Graph.Edge edge : graph.getEdges(node)) {
            int nextNode = edge.to();
            int edgeCost = edge.cost();
            int edgeResource = edge.resource();

            int newResourceUsage = usedResource + edgeResource;
            if (newResourceUsage > maxResource) {
                continue;
            }

            int newCost = currentCost + edgeCost;
            if (newCost < costTable[newResourceUsage][nextNode]) {
                costTable[newResourceUsage][nextNode] = newCost;
            }
        }
    }

    private int getMinCostToTarget(int target, int[][] costTable) {
        int minCost = Integer.MAX_VALUE;
        for (int usedResource = 0; usedResource <= maxResource; usedResource++) {
            minCost = Math.min(minCost, costTable[usedResource][target]);
        }
        return minCost;
    }
}