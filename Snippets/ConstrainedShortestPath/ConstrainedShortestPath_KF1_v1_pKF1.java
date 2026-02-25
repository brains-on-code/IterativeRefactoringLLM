package com.thealgorithms.var7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Class1 {

    public static class Graph {

        private List<List<Edge>> adjacencyList;

        public Graph(int nodeCount) {
            adjacencyList = new ArrayList<>();
            for (int i = 0; i < nodeCount; i++) {
                adjacencyList.add(new ArrayList<>());
            }
        }

        public void addEdge(int fromNode, int toNode, int cost, int time) {
            adjacencyList.get(fromNode).add(new Edge(fromNode, toNode, cost, time));
        }

        public List<Edge> getEdgesFrom(int node) {
            return adjacencyList.get(node);
        }

        public int getNodeCount() {
            return adjacencyList.size();
        }

        public record Edge(int fromNode, int toNode, int cost, int time) {
        }
    }

    private Graph graph;
    private int maxTime;

    public Class1(Graph graph, int maxTime) {
        this.graph = graph;
        this.maxTime = maxTime;
    }

    public int findMinimumCost(int startNode, int endNode) {
        int nodeCount = graph.getNodeCount();
        int[][] dp = new int[maxTime + 1][nodeCount];

        for (int time = 0; time <= maxTime; time++) {
            Arrays.fill(dp[time], Integer.MAX_VALUE);
        }
        dp[0][startNode] = 0;

        for (int time = 0; time <= maxTime; time++) {
            for (int node = 0; node < nodeCount; node++) {
                if (dp[time][node] == Integer.MAX_VALUE) {
                    continue;
                }
                for (Graph.Edge edge : graph.getEdgesFrom(node)) {
                    int nextNode = edge.toNode();
                    int edgeCost = edge.cost();
                    int edgeTime = edge.time();

                    if (time + edgeTime <= maxTime) {
                        dp[time + edgeTime][nextNode] =
                            Math.min(dp[time + edgeTime][nextNode], dp[time][node] + edgeCost);
                    }
                }
            }
        }

        int minCost = Integer.MAX_VALUE;
        for (int time = 0; time <= maxTime; time++) {
            minCost = Math.min(minCost, dp[time][endNode]);
        }

        return minCost == Integer.MAX_VALUE ? -1 : minCost;
    }
}