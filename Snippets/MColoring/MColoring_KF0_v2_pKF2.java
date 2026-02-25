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
        // Prevent instantiation.
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
        // visited[i] == true means node i has been processed.
        ArrayList<Boolean> visited = new ArrayList<>(n + 1);
        visited.add(false); // dummy for 0 index to keep 1-based indexing
        for (int i = 1; i <= n; i++) {
            visited.add(false);
        }

        int maxColorsUsed = 1;

        // Process all components (graph may be disconnected).
        for (int startVertex = 1; startVertex <= n; startVertex++) {
            if (visited.get(startVertex)) {
                continue;
            }

            visited.set(startVertex, true);
            Queue<Integer> queue = new LinkedList<>();
            queue.add(startVertex);

            // BFS over the current component.
            while (!queue.isEmpty()) {
                int current = queue.remove();

                for (int neighbor : nodes.get(current).edges) {
                    // If neighbor has the same color, increment neighbor's color.
                    if (nodes.get(current).color == nodes.get(neighbor).color) {
                        nodes.get(neighbor).color++;
                    }

                    // Track the maximum color used so far.
                    maxColorsUsed = Math.max(
                        maxColorsUsed,
                        Math.max(nodes.get(current).color, nodes.get(neighbor).color)
                    );

                    // If we exceed the allowed number of colors, coloring is not possible.
                    if (maxColorsUsed > m) {
                        return false;
                    }

                    // Enqueue unvisited neighbors.
                    if (!visited.get(neighbor)) {
                        visited.set(neighbor, true);
                        queue.add(neighbor);
                    }
                }
            }
        }

        return true;
    }
}