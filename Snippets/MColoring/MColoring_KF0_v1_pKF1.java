package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * GraphNode represents a node in a graph. Each node has a color (initially 1)
 * and a set of adjacent node indices.
 */
class GraphNode {
    int color = 1; // Initial color for each node
    Set<Integer> adjacentNodes = new HashSet<>(); // Adjacent node indices
}

/**
 * MColoring class solves the M-Coloring problem where the goal is to determine
 * if it's possible to color a graph using at most M colors such that no two
 * adjacent nodes have the same color.
 */
public final class MColoring {

    private MColoring() {
    } // Prevent instantiation of utility class

    /**
     * Determines whether it is possible to color the graph using at most M colors.
     *
     * @param graphNodes List of nodes representing the graph (1-based indexing).
     * @param nodeCount  The total number of nodes in the graph.
     * @param maxAllowedColors The maximum number of allowed colors.
     * @return true if the graph can be colored using at most maxAllowedColors colors, false otherwise.
     */
    static boolean isColoringPossible(ArrayList<GraphNode> graphNodes, int nodeCount, int maxAllowedColors) {

        // visited[i] == 1 means node i has been processed.
        ArrayList<Integer> visited = new ArrayList<>();
        for (int i = 0; i <= nodeCount; i++) {
            visited.add(0); // Initialize all nodes as unvisited (0)
        }

        // The maximum color index used so far (initially 1, since all nodes start with color 1).
        int maxUsedColor = 1;

        // Process all nodes to handle disconnected graphs.
        for (int startNode = 1; startNode <= nodeCount; startNode++) {
            if (visited.get(startNode) > 0) {
                continue; // Skip nodes that are already visited
            }

            visited.set(startNode, 1);
            Queue<Integer> bfsQueue = new LinkedList<>();
            bfsQueue.add(startNode);

            // Perform BFS to process all nodes and their adjacent nodes
            while (!bfsQueue.isEmpty()) {
                int currentNodeIndex = bfsQueue.poll();
                GraphNode currentNode = graphNodes.get(currentNodeIndex);

                // Check all adjacent nodes of the current node
                for (int neighborIndex : currentNode.adjacentNodes) {
                    GraphNode neighborNode = graphNodes.get(neighborIndex);

                    // If the adjacent node has the same color as the current node, increment its color.
                    if (currentNode.color == neighborNode.color) {
                        neighborNode.color += 1;
                    }

                    // Track the maximum color used so far
                    maxUsedColor = Math.max(maxUsedColor, Math.max(currentNode.color, neighborNode.color));

                    // If the number of colors used exceeds the allowed limit, return false.
                    if (maxUsedColor > maxAllowedColors) {
                        return false;
                    }

                    // If the adjacent node hasn't been visited yet, mark it as visited and enqueue it.
                    if (visited.get(neighborIndex) == 0) {
                        visited.set(neighborIndex, 1);
                        bfsQueue.add(neighborIndex);
                    }
                }
            }
        }

        return true; // Possible to color the entire graph with maxAllowedColors or fewer colors.
    }
}