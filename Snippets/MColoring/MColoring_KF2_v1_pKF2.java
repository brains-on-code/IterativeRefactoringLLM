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
 * Utility class that provides a method to check if a graph can be colored
 * using at most m colors such that no two adjacent nodes share the same color.
 */
public final class MColoring {

    private MColoring() {
        // Prevent instantiation
    }

    /**
     * Determines whether the given graph can be colored with at most {@code m} colors.
     *
     * @param nodes list of nodes representing the graph; index corresponds to node id
     * @param n     number of nodes in the graph (assumes nodes are 1-based indexed up to n)
     * @param m     maximum number of colors allowed
     * @return {@code true} if a valid coloring using at most {@code m} colors is possible,
     *         {@code false} otherwise
     */
    static boolean isColoringPossible(List<Node> nodes, int n, int m) {
        // visited[i] == 1 means node i has been visited; 0 means not visited
        List<Integer> visited = new ArrayList<>(Collections.nCopies(n + 1, 0));

        int maxColorsUsed = 1;

        // Perform BFS from each unvisited node to cover all connected components
        for (int startVertex = 1; startVertex <= n; startVertex++) {
            if (visited.get(startVertex) != 0) {
                continue;
            }

            visited.set(startVertex, 1);
            Queue<Integer> queue = new LinkedList<>();
            queue.add(startVertex);

            while (!queue.isEmpty()) {
                int current = queue.poll();

                for (int neighbor : nodes.get(current).edges) {
                    // If neighbor has the same color as current, increment neighbor's color
                    if (nodes.get(current).color == nodes.get(neighbor).color) {
                        nodes.get(neighbor).color++;
                    }

                    // Track the maximum color used so far
                    maxColorsUsed = Math.max(
                        maxColorsUsed,
                        Math.max(nodes.get(current).color, nodes.get(neighbor).color)
                    );

                    // If we exceed the allowed number of colors, coloring is not possible
                    if (maxColorsUsed > m) {
                        return false;
                    }

                    // Continue BFS traversal
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