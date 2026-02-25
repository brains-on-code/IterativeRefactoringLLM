package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Dijkstra's algorithm for finding the shortest path from a single source vertex
 * to all other vertices in a graph.
 */
public class DijkstraOptimizedAlgorithm {

    private final int vertexCount;

    /**
     * Constructs a Dijkstra object with the given number of vertices.
     *
     * @param vertexCount The number of vertices in the graph.
     */
    public DijkstraOptimizedAlgorithm(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    /**
     * Executes Dijkstra's algorithm on the provided graph to find the shortest
     * paths from the source vertex to all other vertices.
     *
     * The graph is represented as an adjacency matrix where {@code graph[i][j]}
     * represents the weight of the edge from vertex {@code i} to vertex {@code j}.
     * A value of 0 indicates no edge exists between the vertices.
     *
     * @param graph  The graph represented as an adjacency matrix.
     * @param source The source vertex.
     * @return An array where the value at each index {@code i} represents the
     *         shortest distance from the source vertex to vertex {@code i}.
     * @throws IllegalArgumentException if the source vertex is out of range.
     */
    public int[] run(int[][] graph, int source) {
        validateSource(source);

        int[] distances = initializeDistances(source);
        boolean[] visited = new boolean[vertexCount];
        Set<Pair<Integer, Integer>> priorityQueue = new TreeSet<>();

        priorityQueue.add(Pair.of(0, source));

        while (!priorityQueue.isEmpty()) {
            int currentVertex = extractMinVertex(priorityQueue);
            visited[currentVertex] = true;
            relaxNeighbors(graph, currentVertex, distances, visited, priorityQueue);
        }

        return distances;
    }

    private void validateSource(int source) {
        if (source < 0 || source >= vertexCount) {
            throw new IllegalArgumentException("Source vertex is out of range: " + source);
        }
    }

    private int[] initializeDistances(int source) {
        int[] distances = new int[vertexCount];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;
        return distances;
    }

    private int extractMinVertex(Set<Pair<Integer, Integer>> priorityQueue) {
        Pair<Integer, Integer> distanceAndVertex = priorityQueue.iterator().next();
        priorityQueue.remove(distanceAndVertex);
        return distanceAndVertex.getRight();
    }

    private void relaxNeighbors(
        int[][] graph,
        int currentVertex,
        int[] distances,
        boolean[] visited,
        Set<Pair<Integer, Integer>> priorityQueue
    ) {
        for (int neighbor = 0; neighbor < vertexCount; neighbor++) {
            if (isRelaxationBeneficial(graph, currentVertex, neighbor, distances, visited)) {
                relaxEdge(currentVertex, neighbor, graph, distances, priorityQueue);
            }
        }
    }

    private boolean isRelaxationBeneficial(
        int[][] graph,
        int from,
        int to,
        int[] distances,
        boolean[] visited
    ) {
        int edgeWeight = graph[from][to];
        if (visited[to] || edgeWeight == 0 || distances[from] == Integer.MAX_VALUE) {
            return false;
        }
        int newDistance = distances[from] + edgeWeight;
        return newDistance < distances[to];
    }

    private void relaxEdge(
        int from,
        int to,
        int[][] graph,
        int[] distances,
        Set<Pair<Integer, Integer>> priorityQueue
    ) {
        int oldDistance = distances[to];
        if (oldDistance != Integer.MAX_VALUE) {
            priorityQueue.remove(Pair.of(oldDistance, to));
        }

        int newDistance = distances[from] + graph[from][to];
        distances[to] = newDistance;
        priorityQueue.add(Pair.of(newDistance, to));
    }
}