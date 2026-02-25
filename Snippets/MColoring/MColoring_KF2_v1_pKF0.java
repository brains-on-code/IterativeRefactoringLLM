package com.thealgorithms.backtracking;

import java.util.*;

class Node {
    int color = 1;
    Set<Integer> edges = new HashSet<>();
}

public final class MColoring {

    private MColoring() {
        // Utility class
    }

    static boolean isColoringPossible(List<Node> nodes, int nodeCount, int maxAllowedColors) {
        List<Boolean> visited = new ArrayList<>(Collections.nCopies(nodeCount + 1, false));
        int maxUsedColors = 1;

        for (int startVertex = 1; startVertex <= nodeCount; startVertex++) {
            if (visited.get(startVertex)) {
                continue;
            }

            Queue<Integer> queue = new LinkedList<>();
            visited.set(startVertex, true);
            queue.add(startVertex);

            while (!queue.isEmpty()) {
                int current = queue.poll();
                Node currentNode = nodes.get(current);

                for (int neighbor : currentNode.edges) {
                    Node neighborNode = nodes.get(neighbor);

                    if (currentNode.color == neighborNode.color) {
                        neighborNode.color++;
                    }

                    maxUsedColors = Math.max(
                        maxUsedColors,
                        Math.max(currentNode.color, neighborNode.color)
                    );

                    if (maxUsedColors > maxAllowedColors) {
                        return false;
                    }

                    if (!visited.get(neighbor)) {
                        visited.set(neighbor, true);
                        queue.add(neighbor);
                    }
                }
            }
        }

        return true;
    }
}