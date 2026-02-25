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

        // minCostWithResource[r][v] = minimum cost to reach node v using exactly r resource units
        int[][] minCostWithResource = new int[maxResource + 1][numNodes];

        // Initialize all states as unreachable
        for (int resource = 0; resource <= maxResource; resource++) {
            Arrays.fill(minCostWithResource[resource], Integer.MAX_VALUE);
        }

        // Starting point: cost 0 at start node with 0 resource used
        minCostWithResource[0][start] = 0;

        // Dynamic programming over resource usage
        for (int usedResource = 0; usedResource <= maxResource; usedResource++) {
            for (int node = 0; node < numNodes; node++) {
                int currentCost = minCostWithResource[usedResource][node];
                if (currentCost == Integer.MAX_VALUE) {
                    continue; // state not reachable
                }

                // Relax all outgoing edges from current node
                for (Graph.Edge edge : graph.getEdges(node)) {
                    int nextNode = edge.to();
                    int edgeCost = edge.cost();
                    int edgeResource = edge.resource();

                    int newResource = usedResource + edgeResource;
                    if (newResource > maxResource) {
                        continue; // resource constraint violated
                    }

                    int newCost = currentCost + edgeCost;
                    if (newCost < minCostWithResource[newResource][nextNode]) {
                        minCostWithResource[newResource][nextNode] = newCost;
                    }
                }
            }
        }

        // Find the minimum cost to reach target with any allowed resource usage
        int minCost = Integer.MAX_VALUE;
        for (int usedResource = 0; usedResource <= maxResource; usedResource++) {
            minCost = Math.min(minCost, minCostWithResource[usedResource][target]);
        }

        return minCost == Integer.MAX_VALUE ? -1 : minCost;
    }
}