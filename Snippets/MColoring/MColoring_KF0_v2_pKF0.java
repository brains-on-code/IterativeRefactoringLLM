package com.thealgorithms.backtracking;

import java.util.*;

/**
 * Represents a graph node with a color and a set of adjacent nodes.
 */
class Node {
    int color = 1;
    Set<Integer> edges = new HashSet<>();
}

/**
 * Utility class to solve the M-Coloring problem.
 */
public final class MColoring {

    private MColoring() {
        // Prevent instantiation
    }

    /**
     * Determines whether it is possible to color the graph using at most M colors.
     *
     * @param nodes list of nodes representing the graph (1-based index).
     * @param n     total number of nodes in the graph.
     * @param m     maximum number of allowed colors.
     * @return true if the graph can be colored using M colors, false otherwise.
     */
    static boolean isColoringPossible(List<Node> nodes, int n, int m) {
        List<Boolean> visited = new ArrayList<>(Collections.nCopies(n + 1, false));
        int maxColorsUsed = 1;

        for (int startVertex = 1; startVertex <= n; startVertex++) {
            if (visited.get(startVertex)) {
                continue;
            }

            Queue<Integer> queue = new ArrayDeque<>();
            visited.set(startVertex, true);
            queue.add(startVertex);

            while (!queue.isEmpty()) {
                int currentIndex = queue.poll();
                Node currentNode = nodes.get(currentIndex);

                for (int neighborIndex : currentNode.edges) {
                    Node neighborNode = nodes.get(neighborIndex);

                    if (currentNode.color == neighborNode.color) {
                        neighborNode.color++;
                    }

                    maxColorsUsed = Math.max(
                        maxColorsUsed,
                        Math.max(currentNode.color, neighborNode.color)
                    );

                    if (maxColorsUsed > m) {
                        return false;
                    }

                    if (!visited.get(neighborIndex)) {
                        visited.set(neighborIndex, true);
                        queue.add(neighborIndex);
                    }
                }
            }
        }

        return true;
    }
}