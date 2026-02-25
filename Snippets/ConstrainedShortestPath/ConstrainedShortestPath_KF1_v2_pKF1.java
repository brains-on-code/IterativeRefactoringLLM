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

        public void addEdge(int fromNode, int toNode, int cost, int time) {
            adjacencyList.get(fromNode).add(new Edge(fromNode, toNode, cost, time));
        }

        public List<Edge> getOutgoingEdges(int node) {
            return adjacencyList.get(node);
        }

        public int getNumberOfNodes() {
            return adjacencyList.size();
        }

        public record Edge(int fromNode, int toNode, int cost, int time) {
        }
    }

    private final Graph graph;
    private final int maxAllowedTime;

    public TimeConstrainedShortestPath(Graph graph, int maxAllowedTime) {
        this.graph = graph;
        this.maxAllowedTime = maxAllowedTime;
    }

    public int findMinimumCost(int startNode, int endNode) {
        int numberOfNodes = graph.getNumberOfNodes();
        int[][] minCostAtTime = new int[maxAllowedTime + 1][numberOfNodes];

        for (int time = 0; time <= maxAllowedTime; time++) {
            Arrays.fill(minCostAtTime[time], Integer.MAX_VALUE);
        }
        minCostAtTime[0][startNode] = 0;

        for (int currentTime = 0; currentTime <= maxAllowedTime; currentTime++) {
            for (int currentNode = 0; currentNode < numberOfNodes; currentNode++) {
                if (minCostAtTime[currentTime][currentNode] == Integer.MAX_VALUE) {
                    continue;
                }
                for (Graph.Edge edge : graph.getOutgoingEdges(currentNode)) {
                    int nextNode = edge.toNode();
                    int edgeCost = edge.cost();
                    int edgeTime = edge.time();

                    int arrivalTime = currentTime + edgeTime;
                    if (arrivalTime <= maxAllowedTime) {
                        minCostAtTime[arrivalTime][nextNode] =
                            Math.min(
                                minCostAtTime[arrivalTime][nextNode],
                                minCostAtTime[currentTime][currentNode] + edgeCost
                            );
                    }
                }
            }
        }

        int minimumCost = Integer.MAX_VALUE;
        for (int time = 0; time <= maxAllowedTime; time++) {
            minimumCost = Math.min(minimumCost, minCostAtTime[time][endNode]);
        }

        return minimumCost == Integer.MAX_VALUE ? -1 : minimumCost;
    }
}