package com.thealgorithms.backtracking;

import java.util.*;

/**
 * Represents a node in a graph with a level and adjacency list.
 */
class Node {
    int level = 1;
    Set<Integer> neighbors = new HashSet<>();
}

/**
 * Utility class containing graph-related backtracking methods.
 */
public final class GraphLevelValidator {

    private GraphLevelValidator() {
        // Prevent instantiation
    }

    /**
     * Validates that the maximum level assigned in the graph does not exceed maxAllowedLevel.
     * Levels are propagated using BFS: if two connected nodes share the same level,
     * the neighbor's level is incremented.
     *
     * @param nodes           list of nodes (1-based indexing is assumed)
     * @param nodeCount       number of nodes to process
     * @param maxAllowedLevel maximum allowed level
     * @return true if all levels are within the allowed limit; false otherwise
     */
    static boolean validateLevels(List<Node> nodes, int nodeCount, int maxAllowedLevel) {
        List<Integer> visited = new ArrayList<>(Collections.nCopies(nodeCount + 1, 0));
        int currentMaxLevel = 1;

        for (int start = 1; start <= nodeCount; start++) {
            if (visited.get(start) > 0) {
                continue;
            }

            visited.set(start, 1);
            Queue<Integer> queue = new LinkedList<>();
            queue.add(start);

            while (!queue.isEmpty()) {
                int current = queue.poll();

                for (int neighbor : nodes.get(current).neighbors) {
                    Node currentNode = nodes.get(current);
                    Node neighborNode = nodes.get(neighbor);

                    if (currentNode.level == neighborNode.level) {
                        neighborNode.level++;
                    }

                    currentMaxLevel = Math.max(currentMaxLevel,
                        Math.max(currentNode.level, neighborNode.level));

                    if (currentMaxLevel > maxAllowedLevel) {
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