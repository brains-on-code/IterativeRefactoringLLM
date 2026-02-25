package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

class GraphNode {
    int level = 1;
    Set<Integer> adjacentNodes = new HashSet<>();
}

public final class GraphLevelValidator {

    private GraphLevelValidator() {
    }

    static boolean validateLevels(ArrayList<GraphNode> graph, int totalNodes, int maxAllowedLevel) {

        ArrayList<Integer> visitedStatus = new ArrayList<>();
        for (int i = 0; i <= totalNodes; i++) {
            visitedStatus.add(0);
        }

        int currentMaxLevel = 1;

        for (int startNodeIndex = 1; startNodeIndex <= totalNodes; startNodeIndex++) {
            if (visitedStatus.get(startNodeIndex) > 0) {
                continue;
            }

            visitedStatus.set(startNodeIndex, 1);
            Queue<Integer> bfsQueue = new LinkedList<>();
            bfsQueue.add(startNodeIndex);

            while (!bfsQueue.isEmpty()) {
                int currentNodeIndex = bfsQueue.poll();

                for (int neighborIndex : graph.get(currentNodeIndex).adjacentNodes) {

                    GraphNode currentNode = graph.get(currentNodeIndex);
                    GraphNode neighborNode = graph.get(neighborIndex);

                    if (currentNode.level == neighborNode.level) {
                        neighborNode.level += 1;
                    }

                    currentMaxLevel = Math.max(
                        currentMaxLevel,
                        Math.max(currentNode.level, neighborNode.level)
                    );

                    if (currentMaxLevel > maxAllowedLevel) {
                        return false;
                    }

                    if (visitedStatus.get(neighborIndex) == 0) {
                        visitedStatus.set(neighborIndex, 1);
                        bfsQueue.add(neighborIndex);
                    }
                }
            }
        }

        return true;
    }
}