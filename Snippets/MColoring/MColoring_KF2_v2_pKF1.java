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
            Queue<Integer> bfsQueue = new LinkedList<>();
            bfsQueue.add(startNode);

            while (!bfsQueue.isEmpty()) {
                int currentNode = bfsQueue.poll();

                for (int neighborNode : graph.get(currentNode).neighbors) {

                    if (graph.get(currentNode).color == graph.get(neighborNode).color) {
                        graph.get(neighborNode).color += 1;
                    }

                    maxColorsUsed = Math.max(
                        maxColorsUsed,
                        Math.max(graph.get(currentNode).color, graph.get(neighborNode).color)
                    );

                    if (maxColorsUsed > maxColors) {
                        return false;
                    }

                    if (visited.get(neighborNode) == 0) {
                        visited.set(neighborNode, 1);
                        bfsQueue.add(neighborNode);
                    }
                }
            }
        }

        return true;
    }
}