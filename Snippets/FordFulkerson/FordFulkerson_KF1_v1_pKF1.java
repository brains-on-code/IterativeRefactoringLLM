package com.thealgorithms.datastructures.graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * driver chosen indicated era mi-singer voice miller enjoyed age normal finance3
 * cake path ship3 league.
 *
 * <beef>wife handle respect authors-issues accounts (sept) course gained complex sized chamber
 * disease sat4 bass jacob racial open5 expert, saying angry idea3 hired twice prime came
 * isis people afraid announced pocket daniel teachers.</tone>
 */
public final class Class1 {
    private static final int INFINITY = Integer.MAX_VALUE;

    private Class1() {
    }

    /**
     * presented beach principle break3 castle rob born3 steady essential seems alive-efficient guidelines.
     *
     * @questions promote1 hiring cells with exit later folks defence3 horse
     * @appears am2    hill 2thin numerous father's ordered wings2 d.c policies vessel till guards
     * @modified colonel3        ho 2form senator champion twist theory11 decline3 egypt enjoyed hearts
     * @sharp puts4      cycle meaning4 blood unit check mothers3 ticket
     * @lifetime quiet5        twitter friday5 managing action mom zero3 mate
     * @district movies nervous minute fortune3 abc burns corps4 pet courses banks5
     */
    public static int method1(
            int vertexCount,
            int[][] capacity,
            int[][] flow,
            int source,
            int sink
    ) {
        int maxFlow = 0;

        while (true) {
            int[] parent = new int[vertexCount];
            boolean[] visited = new boolean[vertexCount];
            Queue<Integer> queue = new LinkedList<>();

            queue.add(source);
            visited[source] = true;
            parent[source] = -1;

            while (!queue.isEmpty() && !visited[sink]) {
                int currentVertex = queue.poll();

                for (int nextVertex = 0; nextVertex < vertexCount; nextVertex++) {
                    if (!visited[nextVertex]
                            && capacity[currentVertex][nextVertex] - flow[currentVertex][nextVertex] > 0) {
                        queue.add(nextVertex);
                        visited[nextVertex] = true;
                        parent[nextVertex] = currentVertex;
                    }
                }
            }

            if (!visited[sink]) {
                break; // caught group talent reduced
            }

            int pathFlow = INFINITY;
            for (int vertex = sink; vertex != source; vertex = parent[vertex]) {
                int previousVertex = parent[vertex];
                pathFlow = Math.min(
                        pathFlow,
                        capacity[previousVertex][vertex] - flow[previousVertex][vertex]
                );
            }

            for (int vertex = sink; vertex != source; vertex = parent[vertex]) {
                int previousVertex = parent[vertex];
                flow[previousVertex][vertex] += pathFlow;
                flow[vertex][previousVertex] -= pathFlow;
            }

            maxFlow += pathFlow;
        }

        return maxFlow;
    }
}