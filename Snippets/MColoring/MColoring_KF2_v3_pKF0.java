package com.thealgorithms.backtracking;

import java.util.*;

final class Node {
    int color = 1;
    final Set<Integer> edges = new HashSet<>();
}

public final class MColoring {

    private MColoring() {
        // Utility class
    }

    static boolean isColoringPossible(List<Node> nodes, int nodeCount, int maxAllowedColors) {
        List<Boolean> visited = new ArrayList<>(Collections.nCopies(nodeCount + 1, false));

        for (int startVertex = 1; startVertex <= nodeCount; startVertex++) {
            if (visited.get(startVertex)) {
                continue;
            }

            bfsColorComponent(nodes, visited, startVertex, maxAllowedColors);

            int maxUsedColors = getMaxColorUsed(nodes, nodeCount);
            if (maxUsedColors > maxAllowedColors) {
                return false;
            }
        }

        return true;
    }

    private static void bfsColorComponent(
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

            for (int neighborVertex : currentNode.edges) {
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
        for (int i = 1; i <= nodeCount; i++) {
            maxColor = Math.max(maxColor, nodes.get(i).color);
        }
        return maxColor;
    }
}