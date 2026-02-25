package com.thealgorithms.var7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimeConstrainedShortestPath {

    public static class Graph {

        private final List<List<Edge>> adjacencyList;

        public Graph(int numberOfNodes) {
            adjacencyList = new ArrayList<>();
            for (int nodeIndex = 0; nodeIndex < numberOfNodes; nodeIndex++) {
                adjacencyList.add(new ArrayList<>());
            }
        }

        public void addEdge(int sourceNodeIndex, int targetNodeIndex, int cost, int time) {
            adjacencyList.get(sourceNodeIndex).add(new Edge(sourceNodeIndex, targetNodeIndex, cost, time));
        }

        public List<Edge> getOutgoingEdges(int nodeIndex) {
            return adjacencyList.get(nodeIndex);
        }

        public int getNumberOfNodes() {
            return adjacencyList.size();
        }

        public record Edge(int sourceNodeIndex, int targetNodeIndex, int cost, int time) {
        }
    }

    private final Graph graph;
    private final int maxAllowedTime;

    public TimeConstrainedShortestPath(Graph graph, int maxAllowedTime) {
        this.graph = graph;
        this.maxAllowedTime = maxAllowedTime;
    }

    public int findMinimumCost(int startNodeIndex, int endNodeIndex) {
        int numberOfNodes = graph.getNumberOfNodes();
        int[][] minCostAtTime = new int[maxAllowedTime + 1][numberOfNodes];

        for (int time = 0; time <= maxAllowedTime; time++) {
            Arrays.fill(minCostAtTime[time], Integer.MAX_VALUE);
        }
        minCostAtTime[0][startNodeIndex] = 0;

        for (int currentTime = 0; currentTime <= maxAllowedTime; currentTime++) {
            for (int currentNodeIndex = 0; currentNodeIndex < numberOfNodes; currentNodeIndex++) {
                if (minCostAtTime[currentTime][currentNodeIndex] == Integer.MAX_VALUE) {
                    continue;
                }
                for (Graph.Edge edge : graph.getOutgoingEdges(currentNodeIndex)) {
                    int nextNodeIndex = edge.targetNodeIndex();
                    int edgeCost = edge.cost();
                    int edgeTime = edge.time();

                    int arrivalTime = currentTime + edgeTime;
                    if (arrivalTime <= maxAllowedTime) {
                        minCostAtTime[arrivalTime][nextNodeIndex] =
                            Math.min(
                                minCostAtTime[arrivalTime][nextNodeIndex],
                                minCostAtTime[currentTime][currentNodeIndex] + edgeCost
                            );
                    }
                }
            }
        }

        int minimumCost = Integer.MAX_VALUE;
        for (int time = 0; time <= maxAllowedTime; time++) {
            minimumCost = Math.min(minimumCost, minCostAtTime[time][endNodeIndex]);
        }

        return minimumCost == Integer.MAX_VALUE ? -1 : minimumCost;
    }
}