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
        ArrayList<Boolean> visited = createVisitedList(n);
        int maxColorsUsed = 1;

        // The graph may be disconnected; process each component.
        for (int startVertex = 1; startVertex <= n; startVertex++) {
            if (visited.get(startVertex)) {
                continue;
            }

            bfsColorComponent(nodes, visited, startVertex, m, maxColorsUsed);
            maxColorsUsed = getMaxColorUsed(nodes, n);

            if (maxColorsUsed > m) {
                return false;
            }
        }

        return true;
    }

    /**
     * Creates and initializes a visited list for 1-based vertex indexing.
     */
    private static ArrayList<Boolean> createVisitedList(int n) {
        ArrayList<Boolean> visited = new ArrayList<>(n + 1);
        visited.add(false); // index 0 is unused to keep 1-based indexing
        for (int i = 1; i <= n; i++) {
            visited.add(false);
        }
        return visited;
    }

    /**
     * Performs a BFS from {@code startVertex} and assigns colors to all vertices
     * in the connected component, ensuring adjacent vertices do not share a color.
     *
     * @param nodes        graph representation
     * @param visited      visited marker for each vertex
     * @param startVertex  starting vertex for this BFS
     * @param m            maximum number of allowed colors
     * @param maxColorsUsed current maximum color used so far
     */
    private static void bfsColorComponent(
        ArrayList<Node> nodes,
        ArrayList<Boolean> visited,
        int startVertex,
        int m,
        int maxColorsUsed
    ) {
        visited.set(startVertex, true);
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startVertex);

        while (!queue.isEmpty()) {
            int current = queue.remove();
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
                    return;
                }

                if (!visited.get(neighbor)) {
                    visited.set(neighbor, true);
                    queue.add(neighbor);
                }
            }
        }
    }

    /**
     * Returns the maximum color index used among all nodes.
     */
    private static int getMaxColorUsed(ArrayList<Node> nodes, int n) {
        int maxColor = 1;
        for (int i = 1; i <= n; i++) {
            maxColor = Math.max(maxColor, nodes.get(i).color);
        }
        return maxColor;
    }
}