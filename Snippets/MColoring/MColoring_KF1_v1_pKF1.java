package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

class Node {
    int level = 1;
    Set<Integer> neighbors = new HashSet<>();
}

public final class GraphLevelValidator {

    private GraphLevelValidator() {
    }

    static boolean validateLevels(ArrayList<Node> graph, int nodeCount, int maxAllowedLevel) {

        ArrayList<Integer> visited = new ArrayList<>();
        for (int i = 0; i < nodeCount + 1; i++) {
            visited.add(0);
        }

        int maxLevel = 1;

        for (int nodeIndex = 1; nodeIndex <= nodeCount; nodeIndex++) {
            if (visited.get(nodeIndex) > 0) {
                continue;
            }

            visited.set(nodeIndex, 1);
            Queue<Integer> queue = new LinkedList<>();
            queue.add(nodeIndex);

            while (!queue.isEmpty()) {
                int currentNodeIndex = queue.peek();
                queue.remove();

                for (int neighborIndex : graph.get(currentNodeIndex).neighbors) {

                    if (graph.get(currentNodeIndex).level == graph.get(neighborIndex).level) {
                        graph.get(neighborIndex).level += 1;
                    }

                    maxLevel =
                        Math.max(
                            maxLevel,
                            Math.max(
                                graph.get(currentNodeIndex).level,
                                graph.get(neighborIndex).level
                            )
                        );

                    if (maxLevel > maxAllowedLevel) {
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