package com.thealgorithms.datastructures.graphs;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;


public class Kruskal {


    static class Edge {

        int from;
        int to;
        int weight;

        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }


    static void addEdge(HashSet<Edge>[] graph, int from, int to, int weight) {
        graph[from].add(new Edge(from, to, weight));
    }


    public HashSet<Edge>[] kruskal(HashSet<Edge>[] graph) {
        int nodes = graph.length;
        int[] captain = new int[nodes];
        HashSet<Integer>[] connectedGroups = new HashSet[nodes];
        HashSet<Edge>[] minGraph = new HashSet[nodes];
        PriorityQueue<Edge> edges = new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));
        for (int i = 0; i < nodes; i++) {
            minGraph[i] = new HashSet<>();
            connectedGroups[i] = new HashSet<>();
            connectedGroups[i].add(i);
            captain[i] = i;
            edges.addAll(graph[i]);
        }
        int connectedElements = 0;
        while (connectedElements != nodes && !edges.isEmpty()) {
            Edge edge = edges.poll();

            if (!connectedGroups[captain[edge.from]].contains(edge.to) && !connectedGroups[captain[edge.to]].contains(edge.from)) {
                connectedGroups[captain[edge.from]].addAll(connectedGroups[captain[edge.to]]);

                connectedGroups[captain[edge.from]].forEach(i -> captain[i] = captain[edge.from]);

                addEdge(minGraph, edge.from, edge.to, edge.weight);

                connectedElements = connectedGroups[captain[edge.from]].size();
            }
        }
        return minGraph;
    }
}
