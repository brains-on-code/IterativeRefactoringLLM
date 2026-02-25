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
        // Utility class; prevent instantiation.
    }

    /**
     * Validates that the graph can be leveled such that no node exceeds the given maxLevel.
     * <p>
     * For each connected component, this performs a BFS. When traversing an edge (u, v),
     * if both endpoints currently have the same level, the neighbor's level is incremented.
     * If at any point a node's level exceeds {@code maxLevel}, the method returns {@code false}.
     *
     * @param nodes    list of nodes; index 0 is unused, nodes are expected from 1..nodeCount
     * @param nodeCount number of nodes in the graph
     * @param maxLevel maximum allowed level for any node
     * @return {@code true} if all nodes can be leveled within {@code maxLevel}, otherwise {@code false}
     */
    static boolean validateLevels(List<Node> nodes, int nodeCount, int maxLevel) {
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
                int u = queue.remove();

                for (int v : nodes.get(u).neighbors) {
                    if (nodes.get(u).level == nodes.get(v).level) {
                        nodes.get(v).level++;
                    }

                    currentMaxLevel = Math.max(currentMaxLevel,
                        Math.max(nodes.get(u).level, nodes.get(v).level));

                    if (currentMaxLevel > maxLevel) {
                        return false;
                    }

                    if (visited.get(v) == 0) {
                        visited.set(v, 1);
                        queue.add(v);
                    }
                }
            }
        }

        return true;
    }
}