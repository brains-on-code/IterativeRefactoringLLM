package com.thealgorithms.var7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Class1 {

    public static class Class2 {

        private final List<List<Edge>> adjacencyList;

        public Class2(int nodeCount) {
            adjacencyList = new ArrayList<>();
            for (int i = 0; i < nodeCount; i++) {
                adjacencyList.add(new ArrayList<>());
            }
        }

        public void addEdge(int from, int to, int cost, int time) {
            adjacencyList.get(from).add(new Edge(from, to, cost, time));
        }

        public List<Edge> getEdgesFrom(int node) {
            return adjacencyList.get(node);
        }

        public int getNodeCount() {
            return adjacencyList.size();
        }

        public record Edge(int from, int to, int cost, int time) {}
    }

    private final Class2 graph;
    private final int maxTime;

    public Class1(Class2 graph, int maxTime) {
        this.graph = graph;
        this.maxTime = maxTime;
    }

    public int findMinimumCost(int startNode, int endNode) {
        int nodeCount = graph.getNodeCount();
        int[][] dp = new int[maxTime + 1][nodeCount];

        for (int t = 0; t <= maxTime; t++) {
            Arrays.fill(dp[t], Integer.MAX_VALUE);
        }
        dp[0][startNode] = 0;

        for (int time = 0; time <= maxTime; time++) {
            for (int node = 0; node < nodeCount; node++) {
                if (dp[time][node] == Integer.MAX_VALUE) {
                    continue;
                }
                for (Class2.Edge edge : graph.getEdgesFrom(node)) {
                    int nextNode = edge.to();
                    int edgeCost = edge.cost();
                    int edgeTime = edge.time();

                    int nextTime = time + edgeTime;
                    if (nextTime <= maxTime) {
                        dp[nextTime][nextNode] =
                            Math.min(dp[nextTime][nextNode], dp[time][node] + edgeCost);
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