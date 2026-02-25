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
        int globalMaxLevel = 1;

        for (int nodeIndex = 1; nodeIndex <= nodeCount; nodeIndex++) {
            if (visited[nodeIndex]) {
                continue;
            }

            int componentMaxLevel =
                bfsAndValidateComponent(nodes, nodeIndex, visited, maxAllowedLevel, globalMaxLevel);

            if (componentMaxLevel == -1) {
                return false;
            }

            globalMaxLevel = Math.max(globalMaxLevel, componentMaxLevel);
            if (globalMaxLevel > maxAllowedLevel) {
                return false;
            }
        }

        return true;
    }

    /**
     * Performs BFS on a connected component starting from startNode, updating levels and
     * validating against maxAllowedLevel.
     *
     * @return the maximum level found in this component, or -1 if validation fails
     */
    private static int bfsAndValidateComponent(
        List<Node> nodes,
        int startNode,
        boolean[] visited,
        int maxAllowedLevel,
        int initialMaxLevel
    ) {
        Queue<Integer> queue = new ArrayDeque<>();
        visited[startNode] = true;
        queue.add(startNode);

        int componentMaxLevel = initialMaxLevel;

        while (!queue.isEmpty()) {
            int currentIndex = queue.poll();
            Node currentNode = nodes.get(currentIndex);

            for (int neighborIndex : currentNode.neighbors) {
                Node neighborNode = nodes.get(neighborIndex);

                if (currentNode.level == neighborNode.level) {
                    neighborNode.level++;
                }

                componentMaxLevel =
                    Math.max(componentMaxLevel, Math.max(currentNode.level, neighborNode.level));

                if (componentMaxLevel > maxAllowedLevel) {
                    return -1;
                }

                if (!visited[neighborIndex]) {
                    visited[neighborIndex] = true;
                    queue.add(neighborIndex);
                }
            }
        }

        return componentMaxLevel;
    }
}