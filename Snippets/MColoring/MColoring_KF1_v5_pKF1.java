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

    static boolean validateLevels(ArrayList<GraphNode> graph, int nodeCount, int maxLevel) {

        ArrayList<Integer> visitStates = new ArrayList<>();
        for (int nodeId = 0; nodeId <= nodeCount; nodeId++) {
            visitStates.add(0);
        }

        int highestLevel = 1;

        for (int startNodeId = 1; startNodeId <= nodeCount; startNodeId++) {
            if (visitStates.get(startNodeId) > 0) {
                continue;
            }

            visitStates.set(startNodeId, 1);
            Queue<Integer> queue = new LinkedList<>();
            queue.add(startNodeId);

            while (!queue.isEmpty()) {
                int currentNodeId = queue.poll();
                GraphNode currentNode = graph.get(currentNodeId);

                for (int neighborId : currentNode.neighbors) {
                    GraphNode neighborNode = graph.get(neighborId);

                    if (currentNode.level == neighborNode.level) {
                        neighborNode.level += 1;
                    }

                    highestLevel = Math.max(
                        highestLevel,
                        Math.max(currentNode.level, neighborNode.level)
                    );

                    if (highestLevel > maxLevel) {
                        return false;
                    }

                    if (visitStates.get(neighborId) == 0) {
                        visitStates.set(neighborId, 1);
                        queue.add(neighborId);
                    }
                }
            }
        }

        return true;
    }
}