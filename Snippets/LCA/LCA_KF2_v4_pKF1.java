package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class LCA {

    private LCA() {}

    private static final Scanner INPUT_SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        int vertexCount = INPUT_SCANNER.nextInt();
        int edgeCount = vertexCount - 1;

        List<List<Integer>> adjacencyList = new ArrayList<>();
        for (int vertex = 0; vertex < vertexCount; vertex++) {
            adjacencyList.add(new ArrayList<>());
        }

        for (int edgeIndex = 0; edgeIndex < edgeCount; edgeIndex++) {
            int sourceVertex = INPUT_SCANNER.nextInt();
            int targetVertex = INPUT_SCANNER.nextInt();

            adjacencyList.get(sourceVertex).add(targetVertex);
            adjacencyList.get(targetVertex).add(sourceVertex);
        }

        int[] parentOf = new int[vertexCount];
        int[] depthOf = new int[vertexCount];

        depthFirstSearch(adjacencyList, 0, -1, parentOf, depthOf);

        int firstQueryVertex = INPUT_SCANNER.nextInt();
        int secondQueryVertex = INPUT_SCANNER.nextInt();

        System.out.println(findLowestCommonAncestor(firstQueryVertex, secondQueryVertex, depthOf, parentOf));
    }

    private static void depthFirstSearch(
            List<List<Integer>> adjacencyList,
            int currentVertex,
            int parentVertex,
            int[] parentOf,
            int[] depthOf) {

        for (int neighborVertex : adjacencyList.get(currentVertex)) {
            if (neighborVertex != parentVertex) {
                parentOf[neighborVertex] = currentVertex;
                depthOf[neighborVertex] = depthOf[currentVertex] + 1;
                depthFirstSearch(adjacencyList, neighborVertex, currentVertex, parentOf, depthOf);
            }
        }
    }

    private static int findLowestCommonAncestor(
            int firstVertex,
            int secondVertex,
            int[] depthOf,
            int[] parentOf) {

        if (depthOf[firstVertex] < depthOf[secondVertex]) {
            int tempVertex = firstVertex;
            firstVertex = secondVertex;
            secondVertex = tempVertex;
        }

        while (depthOf[firstVertex] != depthOf[secondVertex]) {
            firstVertex = parentOf[firstVertex];
        }

        if (firstVertex == secondVertex) {
            return firstVertex;
        }

        while (firstVertex != secondVertex) {
            firstVertex = parentOf[firstVertex];
            secondVertex = parentOf[secondVertex];
        }

        return firstVertex;
    }
}