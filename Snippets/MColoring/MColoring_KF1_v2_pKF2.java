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
     * Checks whether the graph can be leveled so that no node exceeds {@code maxLevel}.
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
        // 0 = unvisited, 1 = visited
        List<Integer> visited = new ArrayList<>(Collections.nCopies(nodeCount + 1, 0));
        int currentMaxLevel = 1;

        // Process each connected component
        for (int startNode = 1; startNode <= nodeCount; startNode++) {
            if (visited.get(startNode) != 0) {
                continue;
            }

            visited.set(startNode, 1);
            Queue<Integer> queue = new LinkedList<>();
            queue.add(startNode);

            while (!queue.isEmpty()) {
                int currentNode = queue.remove();
                Node current = nodes.get(currentNode);

                for (int neighborIndex : current.neighbors) {
                    Node neighbor = nodes.get(neighborIndex);

                    // If both endpoints have the same level, increment neighbor's level
                    if (current.level == neighbor.level) {
                        neighbor.level++;
                    }

                    // Track the maximum level seen so far
                    currentMaxLevel = Math.max(
                        currentMaxLevel,
                        Math.max(current.level, neighbor.level)
                    );

                    // Early exit if we exceed the allowed maximum level
                    if (currentMaxLevel > maxLevel) {
                        return false;
                    }

                    // Standard BFS visitation
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