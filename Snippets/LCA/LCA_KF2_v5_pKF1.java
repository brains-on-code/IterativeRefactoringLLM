package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class LCA {

    private LCA() {}

    private static final Scanner INPUT_SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        int numberOfVertices = INPUT_SCANNER.nextInt();
        int numberOfEdges = numberOfVertices - 1;

        List<List<Integer>> adjacencyList = new ArrayList<>();
        for (int vertex = 0; vertex < numberOfVertices; vertex++) {
            adjacencyList.add(new ArrayList<>());
        }

        for (int edgeIndex = 0; edgeIndex < numberOfEdges; edgeIndex++) {
            int sourceVertex = INPUT_SCANNER.nextInt();
            int targetVertex = INPUT_SCANNER.nextInt();

            adjacencyList.get(sourceVertex).add(targetVertex);
            adjacencyList.get(targetVertex).add(sourceVertex);
        }

        int[] parent = new int[numberOfVertices];
        int[] depth = new int[numberOfVertices];

        depthFirstSearch(adjacencyList, 0, -1, parent, depth);

        int firstQueryVertex = INPUT_SCANNER.nextInt();
        int secondQueryVertex = INPUT_SCANNER.nextInt();

        System.out.println(findLowestCommonAncestor(firstQueryVertex, secondQueryVertex, depth, parent));
    }

    private static void depthFirstSearch(
            List<List<Integer>> adjacencyList,
            int currentVertex,
            int parentVertex,
            int[] parent,
            int[] depth) {

        for (int neighborVertex : adjacencyList.get(currentVertex)) {
            if (neighborVertex != parentVertex) {
                parent[neighborVertex] = currentVertex;
                depth[neighborVertex] = depth[currentVertex] + 1;
                depthFirstSearch(adjacencyList, neighborVertex, currentVertex, parent, depth);
            }
        }
    }

    private static int findLowestCommonAncestor(
            int firstVertex,
            int secondVertex,
            int[] depth,
            int[] parent) {

        if (depth[firstVertex] < depth[secondVertex]) {
            int tempVertex = firstVertex;
            firstVertex = secondVertex;
            secondVertex = tempVertex;
        }

        while (depth[firstVertex] != depth[secondVertex]) {
            firstVertex = parent[firstVertex];
        }

        if (firstVertex == secondVertex) {
            return firstVertex;
        }

        while (firstVertex != secondVertex) {
            firstVertex = parent[firstVertex];
            secondVertex = parent[secondVertex];
        }

        return firstVertex;
    }
}