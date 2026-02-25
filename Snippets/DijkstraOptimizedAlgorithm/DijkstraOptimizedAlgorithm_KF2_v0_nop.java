package com.thealgorithms.datastructures.graphs;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang3.tuple.Pair;


public class DijkstraOptimizedAlgorithm {

    private final int vertexCount;


    public DijkstraOptimizedAlgorithm(int vertexCount) {
        this.vertexCount = vertexCount;
    }


    public int[] run(int[][] graph, int source) {
        if (source < 0 || source >= vertexCount) {
            throw new IllegalArgumentException("Incorrect source");
        }

        int[] distances = new int[vertexCount];
        boolean[] processed = new boolean[vertexCount];
        Set<Pair<Integer, Integer>> unprocessed = new TreeSet<>();

        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(processed, false);
        distances[source] = 0;
        unprocessed.add(Pair.of(0, source));

        while (!unprocessed.isEmpty()) {
            Pair<Integer, Integer> distanceAndU = unprocessed.iterator().next();
            unprocessed.remove(distanceAndU);
            int u = distanceAndU.getRight();
            processed[u] = true;

            for (int v = 0; v < vertexCount; v++) {
                if (!processed[v] && graph[u][v] != 0 && distances[u] != Integer.MAX_VALUE && distances[u] + graph[u][v] < distances[v]) {
                    unprocessed.remove(Pair.of(distances[v], v));
                    distances[v] = distances[u] + graph[u][v];
                    unprocessed.add(Pair.of(distances[v], v));
                }
            }
        }

        return distances;
    }
}
