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
        List<Integer> visited = new ArrayList<>(Collections.nCopies(n + 1, 0));
        int maxColorsUsed = 1;

        for (int startVertex = 1; startVertex <= n; startVertex++) {
            if (visited.get(startVertex) != 0) {
                continue;
            }

            Queue<Integer> queue = new LinkedList<>();
            visited.set(startVertex, 1);
            queue.add(startVertex);

            while (!queue.isEmpty()) {
                int current = queue.poll();
                Node currentNode = nodes.get(current);

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

                    if (visited.get(neighborIndex) == 0) {
                        visited.set(neighborIndex, 1);
                        queue.add(neighborIndex);
                    }
                }
            }
        }

        return true;
    }
}