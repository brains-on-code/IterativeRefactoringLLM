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
                GraphNode currentNode = graph.get(currentNodeIndex);

                for (int adjacentNodeIndex : currentNode.adjacentNodes) {
                    GraphNode adjacentNode = graph.get(adjacentNodeIndex);

                    if (currentNode.color == adjacentNode.color) {
                        adjacentNode.color += 1;
                    }

                    maxColorsUsedSoFar =
                        Math.max(maxColorsUsedSoFar, Math.max(currentNode.color, adjacentNode.color));

                    if (maxColorsUsedSoFar > maxAllowedColors) {
                        return false;
                    }

                    if (visitedNodes.get(adjacentNodeIndex) == 0) {
                        visitedNodes.set(adjacentNodeIndex, 1);
                        bfsQueue.add(adjacentNodeIndex);
                    }
                }
            }
        }

        return true;
    }
}