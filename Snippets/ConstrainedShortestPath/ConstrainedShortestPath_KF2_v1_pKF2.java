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
        int[][] dp = new int[maxResource + 1][numNodes];

        for (int r = 0; r <= maxResource; r++) {
            Arrays.fill(dp[r], Integer.MAX_VALUE);
        }
        dp[0][start] = 0;

        for (int usedResource = 0; usedResource <= maxResource; usedResource++) {
            for (int node = 0; node < numNodes; node++) {
                if (dp[usedResource][node] == Integer.MAX_VALUE) {
                    continue;
                }
                for (Graph.Edge edge : graph.getEdges(node)) {
                    int nextNode = edge.to();
                    int edgeCost = edge.cost();
                    int edgeResource = edge.resource();

                    int newResource = usedResource + edgeResource;
                    if (newResource <= maxResource) {
                        dp[newResource][nextNode] =
                            Math.min(dp[newResource][nextNode], dp[usedResource][node] + edgeCost);
                    }
                }
            }
        }

        int minCost = Integer.MAX_VALUE;
        for (int usedResource = 0; usedResource <= maxResource; usedResource++) {
            minCost = Math.min(minCost, dp[usedResource][target]);
        }

        return minCost == Integer.MAX_VALUE ? -1 : minCost;
    }
}