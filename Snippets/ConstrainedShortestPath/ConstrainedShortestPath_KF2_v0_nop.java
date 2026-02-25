package com.thealgorithms.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ConstrainedShortestPath {


    public static class Graph {

        private List<List<Edge>> adjacencyList;

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

        public record Edge(int from, int to, int cost, int resource) {
        }
    }

    private Graph graph;
    private int maxResource;


    public ConstrainedShortestPath(Graph graph, int maxResource) {
        this.graph = graph;
        this.maxResource = maxResource;
    }


    public int solve(int start, int target) {
        int numNodes = graph.getNumNodes();
        int[][] dp = new int[maxResource + 1][numNodes];

        for (int i = 0; i <= maxResource; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        dp[0][start] = 0;

        for (int r = 0; r <= maxResource; r++) {
            for (int u = 0; u < numNodes; u++) {
                if (dp[r][u] == Integer.MAX_VALUE) {
                    continue;
                }
                for (Graph.Edge edge : graph.getEdges(u)) {
                    int v = edge.to();
                    int cost = edge.cost();
                    int resource = edge.resource();

                    if (r + resource <= maxResource) {
                        dp[r + resource][v] = Math.min(dp[r + resource][v], dp[r][u] + cost);
                    }
                }
            }
        }

        int minCost = Integer.MAX_VALUE;
        for (int r = 0; r <= maxResource; r++) {
            minCost = Math.min(minCost, dp[r][target]);
        }

        return minCost == Integer.MAX_VALUE ? -1 : minCost;
    }
}
