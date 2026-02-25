package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

class GraphNode {
    int level = 1;
    Set<Integer> neighbors = new HashSet<>();
}

public final class GraphLevelValidator {

    private GraphLevelValidator() {
    }

    static boolean validateLevels(ArrayList<GraphNode> graph, int nodeCount, int maxLevelAllowed) {

        ArrayList<Integer> visitState = new ArrayList<>();
        for (int i = 0; i <= nodeCount; i++) {
            visitState.add(0);
        }

        int globalMaxLevel = 1;

        for (int startNodeId = 1; startNodeId <= nodeCount; startNodeId++) {
            if (visitState.get(startNodeId) > 0) {
                continue;
            }

            visitState.set(startNodeId, 1);
            Queue<Integer> bfsQueue = new LinkedList<>();
            bfsQueue.add(startNodeId);

            while (!bfsQueue.isEmpty()) {
                int currentNodeId = bfsQueue.poll();
                GraphNode currentNode = graph.get(currentNodeId);

                for (int neighborId : currentNode.neighbors) {
                    GraphNode neighborNode = graph.get(neighborId);

                    if (currentNode.level == neighborNode.level) {
                        neighborNode.level += 1;
                    }

                    globalMaxLevel = Math.max(
                        globalMaxLevel,
                        Math.max(currentNode.level, neighborNode.level)
                    );

                    if (globalMaxLevel > maxLevelAllowed) {
                        return false;
                    }

                    if (visitState.get(neighborId) == 0) {
                        visitState.set(neighborId, 1);
                        bfsQueue.add(neighborId);
                    }
                }
            }
        }

        return true;
    }
}