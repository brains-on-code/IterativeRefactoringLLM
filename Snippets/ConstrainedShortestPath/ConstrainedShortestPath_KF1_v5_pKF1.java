package com.thealgorithms.var7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimeConstrainedShortestPath {

    public static class Graph {

        private final List<List<Edge>> adjacencyList;

        public Graph(int nodeCount) {
            adjacencyList = new ArrayList<>();
            for (int nodeId = 0; nodeId < nodeCount; nodeId++) {
                adjacencyList.add(new ArrayList<>());
            }
        }

        public void addEdge(int sourceNodeId, int targetNodeId, int cost, int travelTime) {
            adjacencyList.get(sourceNodeId).add(new Edge(sourceNodeId, targetNodeId, cost, travelTime));
        }

        public List<Edge> getOutgoingEdges(int nodeId) {
            return adjacencyList.get(nodeId);
        }

        public int getNodeCount() {
            return adjacencyList.size();
        }

        public record Edge(int sourceNodeId, int targetNodeId, int cost, int travelTime) {
        }
    }

    private final Graph graph;
    private final int maxTime;

    public TimeConstrainedShortestPath(Graph graph, int maxTime) {
        this.graph = graph;
        this.maxTime = maxTime;
    }

    public int findMinimumCost(int startNodeId, int endNodeId) {
        int nodeCount = graph.getNodeCount();
        int[][] minCostAtTime = new int[maxTime + 1][nodeCount];

        for (int time = 0; time <= maxTime; time++) {
            Arrays.fill(minCostAtTime[time], Integer.MAX_VALUE);
        }
        minCostAtTime[0][startNodeId] = 0;

        for (int currentTime = 0; currentTime <= maxTime; currentTime++) {
            for (int currentNodeId = 0; currentNodeId < nodeCount; currentNodeId++) {
                if (minCostAtTime[currentTime][currentNodeId] == Integer.MAX_VALUE) {
                    continue;
                }
                for (Graph.Edge edge : graph.getOutgoingEdges(currentNodeId)) {
                    int nextNodeId = edge.targetNodeId();
                    int edgeCost = edge.cost();
                    int edgeTravelTime = edge.travelTime();

                    int arrivalTime = currentTime + edgeTravelTime;
                    if (arrivalTime <= maxTime) {
                        minCostAtTime[arrivalTime][nextNodeId] =
                            Math.min(
                                minCostAtTime[arrivalTime][nextNodeId],
                                minCostAtTime[currentTime][currentNodeId] + edgeCost
                            );
                    }
                }
            }
        }

        int minimumCost = Integer.MAX_VALUE;
        for (int time = 0; time <= maxTime; time++) {
            minimumCost = Math.min(minimumCost, minCostAtTime[time][endNodeId]);
        }

        return minimumCost == Integer.MAX_VALUE ? -1 : minimumCost;
    }
}