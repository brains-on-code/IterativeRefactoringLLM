package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * GraphVertex represents a vertex in a graph. Each vertex has a color (initially 1)
 * and a set of adjacent vertex indices.
 */
class GraphVertex {
    int color = 1; // Initial color for each vertex
    Set<Integer> adjacentVertices = new HashSet<>(); // Adjacent vertex indices
}

/**
 * MColoring class solves the M-Coloring problem where the goal is to determine
 * if it's possible to color a graph using at most M colors such that no two
 * adjacent vertices have the same color.
 */
public final class MColoring {

    private MColoring() {
    } // Prevent instantiation of utility class

    /**
     * Determines whether it is possible to color the graph using at most maxAllowedColors colors.
     *
     * @param graphVertices    List of vertices representing the graph (1-based indexing).
     * @param vertexCount      The total number of vertices in the graph.
     * @param maxAllowedColors The maximum number of allowed colors.
     * @return true if the graph can be colored using at most maxAllowedColors colors, false otherwise.
     */
    static boolean isColoringPossible(ArrayList<GraphVertex> graphVertices, int vertexCount, int maxAllowedColors) {

        // visited[i] == 1 means vertex i has been processed.
        ArrayList<Integer> visitedVertices = new ArrayList<>();
        for (int i = 0; i <= vertexCount; i++) {
            visitedVertices.add(0); // Initialize all vertices as unvisited (0)
        }

        // The maximum color index used so far (initially 1, since all vertices start with color 1).
        int maxColorUsed = 1;

        // Process all vertices to handle disconnected graphs.
        for (int startVertexIndex = 1; startVertexIndex <= vertexCount; startVertexIndex++) {
            if (visitedVertices.get(startVertexIndex) > 0) {
                continue; // Skip vertices that are already visited
            }

            visitedVertices.set(startVertexIndex, 1);
            Queue<Integer> bfsQueue = new LinkedList<>();
            bfsQueue.add(startVertexIndex);

            // Perform BFS to process all vertices and their adjacent vertices
            while (!bfsQueue.isEmpty()) {
                int currentVertexIndex = bfsQueue.poll();
                GraphVertex currentVertex = graphVertices.get(currentVertexIndex);

                // Check all adjacent vertices of the current vertex
                for (int adjacentVertexIndex : currentVertex.adjacentVertices) {
                    GraphVertex adjacentVertex = graphVertices.get(adjacentVertexIndex);

                    // If the adjacent vertex has the same color as the current vertex, increment its color.
                    if (currentVertex.color == adjacentVertex.color) {
                        adjacentVertex.color += 1;
                    }

                    // Track the maximum color used so far
                    maxColorUsed = Math.max(maxColorUsed, Math.max(currentVertex.color, adjacentVertex.color));

                    // If the number of colors used exceeds the allowed limit, return false.
                    if (maxColorUsed > maxAllowedColors) {
                        return false;
                    }

                    // If the adjacent vertex hasn't been visited yet, mark it as visited and enqueue it.
                    if (visitedVertices.get(adjacentVertexIndex) == 0) {
                        visitedVertices.set(adjacentVertexIndex, 1);
                        bfsQueue.add(adjacentVertexIndex);
                    }
                }
            }
        }

        return true; // Possible to color the entire graph with maxAllowedColors or fewer colors.
    }
}