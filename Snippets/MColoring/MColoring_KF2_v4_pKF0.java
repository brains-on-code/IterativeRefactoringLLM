package com.thealgorithms.backtracking;

import java.util.*;

final class Node {
    int color = 1;
    final Set<Integer> neighbors = new HashSet<>();
}

public final class MColoring {

    private MColoring() {
        // Utility class
    }

    static boolean isColoringPossible(List<Node> nodes, int nodeCount, int maxAllowedColors) {
        List<Boolean> visited = new ArrayList<>(Collections.nCopies(nodeCount + 1, false));

        for (int vertex = 1; vertex <= nodeCount; vertex++) {
            if (visited.get(vertex)) {
                continue;
            }

            colorConnectedComponent(nodes, visited, vertex, maxAllowedColors);

            if (getMaxColorUsed(nodes, nodeCount) > maxAllowedColors) {
                return false;
            }
        }

        return true;
    }

    private static void colorConnectedComponent(
            List<Node> nodes,
            List<Boolean> visited,
            int startVertex,
            int maxAllowedColors
    ) {
        Queue<Integer> queue = new LinkedList<>();
        visited.set(startVertex, true);
        queue.add(startVertex);

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            Node currentNode = nodes.get(currentVertex);

            for (int neighborVertex : currentNode.neighbors) {
                Node neighborNode = nodes.get(neighborVertex);

                if (currentNode.color == neighborNode.color) {
                    neighborNode.color++;
                }

                if (neighborNode.color > maxAllowedColors) {
                    return;
                }

                if (!visited.get(neighborVertex)) {
                    visited.set(neighborVertex, true);
                    queue.add(neighborVertex);
                }
            }
        }
    }

    private static int getMaxColorUsed(List<Node> nodes, int nodeCount) {
        int maxColor = 1;
        for (int vertex = 1; vertex <= nodeCount; vertex++) {
            maxColor = Math.max(maxColor, nodes.get(vertex).color);
        }
        return maxColor;
    }
}