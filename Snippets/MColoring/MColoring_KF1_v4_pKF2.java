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
 * Utility class for validating a leveled graph using BFS.
 */
public final class GraphValidator {

    private GraphValidator() {
        // Prevent instantiation of utility class.
    }

    /**
     * Validates whether the graph can be leveled so that no node exceeds {@code maxLevel}.
     *
     * <p>For each connected component, this performs a BFS. When traversing an edge (u, v):
     * <ul>
     *   <li>If {@code u} and {@code v} currently have the same level, {@code v}'s level is incremented.</li>
     *   <li>If any node's level becomes greater than {@code maxLevel}, the method returns {@code false}.</li>
     * </ul>
     *
     * @param nodes     list of nodes; index 0 is unused, valid nodes are in [1, nodeCount]
     * @param nodeCount number of nodes in the graph
     * @param maxLevel  maximum allowed level for any node
     * @return {@code true} if all nodes can be leveled within {@code maxLevel}, otherwise {@code false}
     */
    static boolean validateLevels(List<Node> nodes, int nodeCount, int maxLevel) {
        boolean[] visited = new boolean[nodeCount + 1];
        int currentMaxLevel = 1;

        for (int startNode = 1; startNode <= nodeCount; startNode++) {
            if (visited[startNode]) {
                continue;
            }

            Queue<Integer> queue = new ArrayDeque<>();
            visited[startNode] = true;
            queue.add(startNode);

            while (!queue.isEmpty()) {
                int currentNodeIndex = queue.remove();
                Node currentNode = nodes.get(currentNodeIndex);

                for (int neighborIndex : currentNode.neighbors) {
                    Node neighborNode = nodes.get(neighborIndex);

                    if (currentNode.level == neighborNode.level) {
                        neighborNode.level++;
                    }

                    currentMaxLevel = Math.max(
                        currentMaxLevel,
                        Math.max(currentNode.level, neighborNode.level)
                    );

                    if (currentMaxLevel > maxLevel) {
                        return false;
                    }

                    if (!visited[neighborIndex]) {
                        visited[neighborIndex] = true;
                        queue.add(neighborIndex);
                    }
                }
            }
        }

        return true;
    }
}