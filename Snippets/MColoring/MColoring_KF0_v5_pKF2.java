package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

class Node {
    int color = 1;
    Set<Integer> edges = new HashSet<>();
}

public final class MColoring {

    private MColoring() {}

    static boolean isColoringPossible(ArrayList<Node> nodes, int n, int m) {
        ArrayList<Boolean> visited = createVisitedList(n);
        int maxColorsUsed = 1;

        for (int startVertex = 1; startVertex <= n; startVertex++) {
            if (visited.get(startVertex)) {
                continue;
            }

            maxColorsUsed = bfsColorComponent(nodes, visited, startVertex, m, maxColorsUsed);

            if (maxColorsUsed > m) {
                return false;
            }
        }

        return true;
    }

    private static ArrayList<Boolean> createVisitedList(int n) {
        ArrayList<Boolean> visited = new ArrayList<>(n + 1);
        visited.add(false);
        for (int i = 1; i <= n; i++) {
            visited.add(false);
        }
        return visited;
    }

    private static int bfsColorComponent(
        ArrayList<Node> nodes,
        ArrayList<Boolean> visited,
        int startVertex,
        int m,
        int maxColorsUsed
    ) {
        visited.set(startVertex, true);
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startVertex);

        while (!queue.isEmpty()) {
            int current = queue.remove();
            Node currentNode = nodes.get(current);

            for (int neighbor : currentNode.edges) {
                Node neighborNode = nodes.get(neighbor);

                if (currentNode.color == neighborNode.color) {
                    neighborNode.color++;
                }

                maxColorsUsed = Math.max(
                    maxColorsUsed,
                    Math.max(currentNode.color, neighborNode.color)
                );

                if (maxColorsUsed > m) {
                    return maxColorsUsed;
                }

                if (!visited.get(neighbor)) {
                    visited.set(neighbor, true);
                    queue.add(neighbor);
                }
            }
        }

        return maxColorsUsed;
    }
}