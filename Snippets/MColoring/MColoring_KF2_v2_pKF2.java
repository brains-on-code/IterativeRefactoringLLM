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

                for (int neighbor : currentNode.edges) {
                    Node neighborNode = nodes.get(neighbor);

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