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

        ArrayList<Integer> visited = new ArrayList<>();
        for (int i = 0; i <= numberOfNodes; i++) {
            visited.add(0);
        }

        int maxColorsUsed = 1;

        for (int startVertex = 1; startVertex <= numberOfNodes; startVertex++) {
            if (visited.get(startVertex) > 0) {
                continue;
            }

            visited.set(startVertex, 1);
            Queue<Integer> queue = new LinkedList<>();
            queue.add(startVertex);

            while (!queue.isEmpty()) {
                int currentVertex = queue.poll();

                for (int neighborVertex : graph.get(currentVertex).adjacentNodes) {

                    if (graph.get(currentVertex).color == graph.get(neighborVertex).color) {
                        graph.get(neighborVertex).color += 1;
                    }

                    maxColorsUsed = Math.max(
                        maxColorsUsed,
                        Math.max(graph.get(currentVertex).color, graph.get(neighborVertex).color)
                    );

                    if (maxColorsUsed > maxAllowedColors) {
                        return false;
                    }

                    if (visited.get(neighborVertex) == 0) {
                        visited.set(neighborVertex, 1);
                        queue.add(neighborVertex);
                    }
                }
            }
        }

        return true;
    }
}