package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Represents a graph node with a color and a set of adjacent nodes.
 */
class Node {
    /** Current color of the node (1-based). */
    int color = 1;

    /** Adjacent nodes represented by their indices. */
    Set<Integer> edges = new HashSet<>();
}

/**
 * Utility class for solving the M-Coloring problem:
 * determine whether a graph can be colored with at most M colors
 * such that no two adjacent nodes share the same color.
 */
public final class MColoring {

    private MColoring() {
        // Utility class; prevent instantiation.
    }

    /**
     * Determines whether it is possible to color the graph using at most {@code m} colors.
     *
     * @param nodes list of nodes representing the graph (1-based indexing is assumed)
     * @param n     total number of nodes in the graph
     * @param m     maximum number of allowed colors
     * @return {@code true} if the graph can be colored using at most {@code m} colors,
     *         {@code false} otherwise
     */
    static boolean isColoringPossible(ArrayList<Node> nodes, int n, int m) {
        // visited[i] == 1 means node i has been processed.
        ArrayList<Integer> visited = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            visited.add(0);
        }

        // Tracks the maximum color index used so far.
        int maxColors = 1;

        // Process all components (graph may be disconnected).
        for (int startVertex = 1; startVertex <= n; startVertex++) {
            if (visited.get(startVertex) > 0) {
                continue;
            }

            visited.set(startVertex, 1);
            Queue<Integer> queue = new LinkedList<>();
            queue.add(startVertex);

            // BFS over the current component.
            while (!queue.isEmpty()) {
                int current = queue.remove();

                for (int neighbor : nodes.get(current).edges) {
                    // Resolve color conflict with neighbor if needed.
                    if (nodes.get(current).color == nodes.get(neighbor).color) {
                        nodes.get(neighbor).color++;
                    }

                    // Update maximum color used.
                    maxColors = Math.max(
                        maxColors,
                        Math.max(nodes.get(current).color, nodes.get(neighbor).color)
                    );

                    // Exceeded allowed colors.
                    if (maxColors > m) {
                        return false;
                    }

                    // Visit neighbor if not already processed.
                    if (visited.get(neighbor) == 0) {
                        visited.set(neighbor, 1);
                        queue.add(neighbor);
                    }
                }
            }
        }

        return true;
    }
}