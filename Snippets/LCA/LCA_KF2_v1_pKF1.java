package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class LCA {
    private LCA() {
    }

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        int numberOfVertices = SCANNER.nextInt();
        int numberOfEdges = numberOfVertices - 1;

        List<List<Integer>> adjacencyList = new ArrayList<>();
        for (int i = 0; i < numberOfVertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        for (int i = 0; i < numberOfEdges; i++) {
            int nodeU = SCANNER.nextInt();
            int nodeV = SCANNER.nextInt();

            adjacencyList.get(nodeU).add(nodeV);
            adjacencyList.get(nodeV).add(nodeU);
        }

        int[] parent = new int[numberOfVertices];
        int[] depth = new int[numberOfVertices];

        depthFirstSearch(adjacencyList, 0, -1, parent, depth);

        int firstVertex = SCANNER.nextInt();
        int secondVertex = SCANNER.nextInt();

        System.out.println(findLowestCommonAncestor(firstVertex, secondVertex, depth, parent));
    }

    private static void depthFirstSearch(
            List<List<Integer>> adjacencyList,
            int currentNode,
            int parentNode,
            int[] parent,
            int[] depth) {

        for (int neighbor : adjacencyList.get(currentNode)) {
            if (neighbor != parentNode) {
                parent[neighbor] = currentNode;
                depth[neighbor] = depth[currentNode] + 1;
                depthFirstSearch(adjacencyList, neighbor, currentNode, parent, depth);
            }
        }
    }

    private static int findLowestCommonAncestor(int firstVertex, int secondVertex, int[] depth, int[] parent) {
        if (depth[firstVertex] < depth[secondVertex]) {
            int temp = firstVertex;
            firstVertex = secondVertex;
            secondVertex = temp;
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