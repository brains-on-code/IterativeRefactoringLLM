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
        int[][] minCostAtTime = createInitializedCostTable(nodeCount);

        minCostAtTime[0][startNode] = 0;
        relaxEdgesWithinTimeLimit(minCostAtTime);

        int minCost = getMinimumCostToNode(endNode, minCostAtTime);
        return minCost == Integer.MAX_VALUE ? -1 : minCost;
    }

    private int[][] createInitializedCostTable(int nodeCount) {
        int[][] costTable = new int[maxTime + 1][nodeCount];
        for (int time = 0; time <= maxTime; time++) {
            Arrays.fill(costTable[time], Integer.MAX_VALUE);
        }
        return costTable;
    }

    private void relaxEdgesWithinTimeLimit(int[][] costTable) {
        int nodeCount = graph.getNodeCount();

        for (int currentTime = 0; currentTime <= maxTime; currentTime++) {
            for (int node = 0; node < nodeCount; node++) {
                int currentCost = costTable[currentTime][node];
                if (currentCost == Integer.MAX_VALUE) {
                    continue;
                }

                for (Class2.Edge edge : graph.getEdgesFrom(node)) {
                    int nextTime = currentTime + edge.time();
                    if (nextTime > maxTime) {
                        continue;
                    }

                    int newCost = currentCost + edge.cost();
                    if (newCost < costTable[nextTime][edge.to()]) {
                        costTable[nextTime][edge.to()] = newCost;
                    }
                }
            }
        }
    }

    private int getMinimumCostToNode(int endNode, int[][] costTable) {
        int minCost = Integer.MAX_VALUE;
        for (int time = 0; time <= maxTime; time++) {
            minCost = Math.min(minCost, costTable[time][endNode]);
        }
        return minCost;
    }
}