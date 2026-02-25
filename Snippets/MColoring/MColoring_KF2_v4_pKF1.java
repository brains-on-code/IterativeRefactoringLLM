package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

class GraphNode {
    int color = 1;
    Set<Integer> neighbors = new HashSet<>();
}

public final class MColoring {

    private MColoring() {
    }

    static boolean isColoringPossible(ArrayList<GraphNode> graph, int nodeCount, int maxColors) {

        ArrayList<Integer> visited = new ArrayList<>();
        for (int i = 0; i <= nodeCount; i++) {
            visited.add(0);
        }

        int maxColorsUsed = 1;

        for (int startNode = 1; startNode <= nodeCount; startNode++) {
            if (visited.get(startNode) > 0) {
                continue;
            }

            visited.set(startNode, 1);
            Queue<Integer> queue = new LinkedList<>();
            queue.add(startNode);

            while (!queue.isEmpty()) {
                int currentNodeIndex = queue.poll();
                GraphNode currentNode = graph.get(currentNodeIndex);

                for (int neighborIndex : currentNode.neighbors) {
                    GraphNode neighborNode = graph.get(neighborIndex);

                    if (currentNode.color == neighborNode.color) {
                        neighborNode.color += 1;
                    }

                    maxColorsUsed = Math.max(
                        maxColorsUsed,
                        Math.max(currentNode.color, neighborNode.color)
                    );

                    if (maxColorsUsed > maxColors) {
                        return false;
                    }

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