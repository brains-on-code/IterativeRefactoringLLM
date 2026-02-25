package com.thealgorithms.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConstrainedShortestPath {

    public static class Graph {

        private final List<List<Edge>> adjacencyList;

        public Graph(int numNodes) {
            adjacencyList = new ArrayList<>();
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

        int[][] minCostWithResource = new int[maxResource + 1][numNodes];

        for (int resource = 0; resource <= maxResource; resource++) {
            Arrays.fill(minCostWithResource[resource], Integer.MAX_VALUE);
        }

        minCostWithResource[0][start] = 0;

        for (int usedResource = 0; usedResource <= maxResource; usedResource++) {
            for (int node = 0; node < numNodes; node++) {
                int currentCost = minCostWithResource[usedResource][node];
                if (currentCost == Integer.MAX_VALUE) {
                    continue;
                }

                for (Graph.Edge edge : graph.getEdges(node)) {
                    int nextNode = edge.to();
                    int edgeCost = edge.cost();
                    int edgeResource = edge.resource();

                    int newResource = usedResource + edgeResource;
                    if (newResource > maxResource) {
                        continue;
                    }

                    int newCost = currentCost + edgeCost;
                    if (newCost < minCostWithResource[newResource][nextNode]) {
                        minCostWithResource[newResource][nextNode] = newCost;
                    }
                }
            }
        }

        int minCost = Integer.MAX_VALUE;
        for (int usedResource = 0; usedResource <= maxResource; usedResource++) {
            minCost = Math.min(minCost, minCostWithResource[usedResource][target]);
        }

        return minCost == Integer.MAX_VALUE ? -1 : minCost;
    }
}