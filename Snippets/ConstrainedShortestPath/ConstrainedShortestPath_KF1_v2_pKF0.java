package com.thealgorithms.var7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Class1 {

    public static class Class2 {

        private final List<List<Edge>> adjacencyList;

        public Class2(int nodeCount) {
            adjacencyList = new ArrayList<>(nodeCount);
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
        int[][] minCostAtTime = initializeDpTable(nodeCount);

        minCostAtTime[0][startNode] = 0;
        relaxEdgesOverTime(nodeCount, minCostAtTime);

        int minCost = findBestCostToEnd(endNode, minCostAtTime);
        return minCost == Integer.MAX_VALUE ? -1 : minCost;
    }

    private int[][] initializeDpTable(int nodeCount) {
        int[][] dp = new int[maxTime + 1][nodeCount];
        for (int t = 0; t <= maxTime; t++) {
            Arrays.fill(dp[t], Integer.MAX_VALUE);
        }
        return dp;
    }

    private void relaxEdgesOverTime(int nodeCount, int[][] dp) {
        for (int currentTime = 0; currentTime <= maxTime; currentTime++) {
            for (int node = 0; node < nodeCount; node++) {
                int currentCost = dp[currentTime][node];
                if (currentCost == Integer.MAX_VALUE) {
                    continue;
                }
                for (Class2.Edge edge : graph.getEdgesFrom(node)) {
                    int nextTime = currentTime + edge.time();
                    if (nextTime > maxTime) {
                        continue;
                    }
                    int newCost = currentCost + edge.cost();
                    if (newCost < dp[nextTime][edge.to()]) {
                        dp[nextTime][edge.to()] = newCost;
                    }
                }
            }
        }
    }

    private int findBestCostToEnd(int endNode, int[][] dp) {
        int minCost = Integer.MAX_VALUE;
        for (int time = 0; time <= maxTime; time++) {
            if (dp[time][endNode] < minCost) {
                minCost = dp[time][endNode];
            }
        }
        return minCost;
    }
}