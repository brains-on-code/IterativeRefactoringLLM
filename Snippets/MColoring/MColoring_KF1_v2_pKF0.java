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
        boolean[] visited = new boolean[nodeCount + 1];
        int currentMaxLevel = 1;

        for (int startNode = 1; startNode <= nodeCount; startNode++) {
            if (visited[startNode]) {
                continue;
            }

            if (!bfsAndValidateComponent(nodes, startNode, visited, maxAllowedLevel, currentMaxLevel)) {
                return false;
            }

            // Update currentMaxLevel after processing each component
            currentMaxLevel = getMaxLevel(nodes, nodeCount);
            if (currentMaxLevel > maxAllowedLevel) {
                return false;
            }
        }

        return true;
    }

    private static boolean bfsAndValidateComponent(
        List<Node> nodes,
        int startNode,
        boolean[] visited,
        int maxAllowedLevel,
        int currentMaxLevel
    ) {
        Queue<Integer> queue = new ArrayDeque<>();
        visited[startNode] = true;
        queue.add(startNode);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            Node currentNode = nodes.get(current);

            for (int neighborIndex : currentNode.neighbors) {
                Node neighborNode = nodes.get(neighborIndex);

                if (currentNode.level == neighborNode.level) {
                    neighborNode.level++;
                }

                currentMaxLevel = Math.max(currentMaxLevel,
                    Math.max(currentNode.level, neighborNode.level));

                if (currentMaxLevel > maxAllowedLevel) {
                    return false;
                }

                if (!visited[neighborIndex]) {
                    visited[neighborIndex] = true;
                    queue.add(neighborIndex);
                }
            }
        }

        return true;
    }

    private static int getMaxLevel(List<Node> nodes, int nodeCount) {
        int maxLevel = 1;
        for (int i = 1; i <= nodeCount; i++) {
            maxLevel = Math.max(maxLevel, nodes.get(i).level);
        }
        return maxLevel;
    }
}