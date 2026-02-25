package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


class AdjacencyList<E extends Comparable<E>> {

    Map<E, ArrayList<E>> adj;


    AdjacencyList() {
        adj = new LinkedHashMap<>();
    }


    void addEdge(E from, E to) {
        if (!adj.containsKey(from)) {
            adj.put(from, new ArrayList<>());
        }
        adj.get(from).add(to);
        if (!adj.containsKey(to)) {
            adj.put(to, new ArrayList<>());
        }
    }


    ArrayList<E> getAdjacents(E v) {
        return adj.get(v);
    }


    Set<E> getVertices() {
        return adj.keySet();
    }
}


class TopologicalSort<E extends Comparable<E>> {

    AdjacencyList<E> graph;
    Map<E, Integer> inDegree;


    TopologicalSort(AdjacencyList<E> graph) {
        this.graph = graph;
    }


    void calculateInDegree() {
        inDegree = new HashMap<>();
        for (E vertex : graph.getVertices()) {
            inDegree.putIfAbsent(vertex, 0);
            for (E adjacent : graph.getAdjacents(vertex)) {
                inDegree.put(adjacent, inDegree.getOrDefault(adjacent, 0) + 1);
            }
        }
    }


    ArrayList<E> topSortOrder() {
        calculateInDegree();
        Queue<E> q = new LinkedList<>();

        for (var entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                q.add(entry.getKey());
            }
        }

        ArrayList<E> answer = new ArrayList<>();
        int processedVertices = 0;

        while (!q.isEmpty()) {
            E current = q.poll();
            answer.add(current);
            processedVertices++;

            for (E adjacent : graph.getAdjacents(current)) {
                inDegree.put(adjacent, inDegree.get(adjacent) - 1);
                if (inDegree.get(adjacent) == 0) {
                    q.add(adjacent);
                }
            }
        }

        if (processedVertices != graph.getVertices().size()) {
            throw new IllegalStateException("Graph contains a cycle, topological sort not possible");
        }

        return answer;
    }
}


public final class KahnsAlgorithm {
    private KahnsAlgorithm() {
    }

    public static void main(String[] args) {
        AdjacencyList<String> graph = new AdjacencyList<>();
        graph.addEdge("a", "b");
        graph.addEdge("c", "a");
        graph.addEdge("a", "d");
        graph.addEdge("b", "d");
        graph.addEdge("c", "u");
        graph.addEdge("u", "b");

        TopologicalSort<String> topSort = new TopologicalSort<>(graph);

        for (String s : topSort.topSortOrder()) {
            System.out.print(s + " ");
        }
    }
}
