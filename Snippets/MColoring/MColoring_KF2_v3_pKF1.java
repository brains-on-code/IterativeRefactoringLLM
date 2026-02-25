package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

class GraphNode {
    int color = 1;
    Set<Integer> adjacentNodes = new HashSet<>();
}

public final class MColoring {

    private MColoring() {
    }

    static boolean isColoringPossible(ArrayList<GraphNode> graph, int numberOfNodes, int maxAllowedColors) {

        ArrayList<Integer> visitedNodes = new ArrayList<>();
        for (int i = 0; i <= numberOfNodes; i++) {
            visitedNodes.add(0);
        }

        int maxColorsUsedSoFar = 1;

        for (int startNodeIndex = 1; startNodeIndex <= numberOfNodes; startNodeIndex++) {
            if (visitedNodes.get(startNodeIndex) > 0) {
                continue;
            }

            visitedNodes.set(startNodeIndex, 1);
            Queue<Integer> bfsQueue = new LinkedList<>();
            bfsQueue.add(startNodeIndex);

            while (!bfsQueue.isEmpty()) {
                int currentNodeIndex = bfsQueue.poll();

                for (int neighborIndex : graph.get(currentNodeIndex).adjacentNodes) {

                    GraphNode currentNode = graph.get(currentNodeIndex);
                    GraphNode neighborNode = graph.get(neighborIndex);

                    if (currentNode.color == neighborNode.color) {
                        neighborNode.color += 1;
                    }

                    maxColorsUsedSoFar =
                        Math.max(maxColorsUsedSoFar, Math.max(currentNode.color, neighborNode.color));

                    if (maxColorsUsedSoFar > maxAllowedColors) {
                        return false;
                    }

                    if (visitedNodes.get(neighborIndex) == 0) {
                        visitedNodes.set(neighborIndex, 1);
                        bfsQueue.add(neighborIndex);
                    }
                }
            }
        }

        return true;
    }
}