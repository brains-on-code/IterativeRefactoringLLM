package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class LCA {

    private LCA() {}

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        List<List<Integer>> adjacencyList = new ArrayList<>();

        int vertexCount = SCANNER.nextInt();
        int edgeCount = vertexCount - 1;

        for (int i = 0; i < vertexCount; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        for (int i = 0; i < edgeCount; i++) {
            int sourceVertex = SCANNER.nextInt();
            int destinationVertex = SCANNER.nextInt();

            adjacencyList.get(sourceVertex).add(destinationVertex);
            adjacencyList.get(destinationVertex).add(sourceVertex);
        }

        int[] parent = new int[vertexCount];
        int[] depth = new int[vertexCount];

        int rootVertex = 0;
        int rootParent = -1;
        depthFirstSearch(adjacencyList, rootVertex, rootParent, parent, depth);

        int firstVertex = SCANNER.nextInt();
        int secondVertex = SCANNER.nextInt();

        System.out.println(findLowestCommonAncestor(firstVertex, secondVertex, depth, parent));
    }

    /**
     * Depth first search to calculate parent and depth of every vertex.
     *
     * @param adjacencyList The adjacency list representation of the tree
     * @param currentVertex The current vertex being visited
     * @param parentVertex Parent of the current vertex
     * @param parent An array to store parents of all vertices
     * @param depth An array to store depth of all vertices
     */
    private static void depthFirstSearch(
            List<List<Integer>> adjacencyList,
            int currentVertex,
            int parentVertex,
            int[] parent,
            int[] depth) {

        for (int adjacentVertex : adjacencyList.get(currentVertex)) {
            if (adjacentVertex != parentVertex) {
                parent[adjacentVertex] = currentVertex;
                depth[adjacentVertex] = depth[currentVertex] + 1;
                depthFirstSearch(adjacencyList, adjacentVertex, currentVertex, parent, depth);
            }
        }
    }

    /**
     * Method to calculate Lowest Common Ancestor.
     *
     * @param firstVertex The first vertex
     * @param secondVertex The second vertex
     * @param depth An array with depths of all vertices
     * @param parent An array with parents of all vertices
     * @return Returns a vertex that is LCA of firstVertex and secondVertex
     */
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