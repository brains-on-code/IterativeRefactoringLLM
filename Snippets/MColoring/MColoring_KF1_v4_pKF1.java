package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

class GraphNode {
    int level = 1;
    Set<Integer> adjacentNodeIds = new HashSet<>();
}

public final class GraphLevelValidator {

    private GraphLevelValidator() {
    }

    static boolean validateLevels(ArrayList<GraphNode> graph, int totalNodes, int maxAllowedLevel) {

        ArrayList<Integer> nodeVisitStates = new ArrayList<>();
        for (int nodeId = 0; nodeId <= totalNodes; nodeId++) {
            nodeVisitStates.add(0);
        }

        int highestLevelEncountered = 1;

        for (int startNodeId = 1; startNodeId <= totalNodes; startNodeId++) {
            if (nodeVisitStates.get(startNodeId) > 0) {
                continue;
            }

            nodeVisitStates.set(startNodeId, 1);
            Queue<Integer> bfsQueue = new LinkedList<>();
            bfsQueue.add(startNodeId);

            while (!bfsQueue.isEmpty()) {
                int currentNodeId = bfsQueue.poll();
                GraphNode currentNode = graph.get(currentNodeId);

                for (int adjacentNodeId : currentNode.adjacentNodeIds) {
                    GraphNode adjacentNode = graph.get(adjacentNodeId);

                    if (currentNode.level == adjacentNode.level) {
                        adjacentNode.level += 1;
                    }

                    highestLevelEncountered =
                        Math.max(highestLevelEncountered, Math.max(currentNode.level, adjacentNode.level));

                    if (highestLevelEncountered > maxAllowedLevel) {
                        return false;
                    }

                    if (nodeVisitStates.get(adjacentNodeId) == 0) {
                        nodeVisitStates.set(adjacentNodeId, 1);
                        bfsQueue.add(adjacentNodeId);
                    }
                }
            }
        }

        return true;
    }
}