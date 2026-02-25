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

        for (int vertex = 0; vertex < vertexCount; vertex++) {
            adjacencyList.add(new ArrayList<>());
        }

        for (int edge = 0; edge < edgeCount; edge++) {
            int fromVertex = SCANNER.nextInt();
            int toVertex = SCANNER.nextInt();

            adjacencyList.get(fromVertex).add(toVertex);
            adjacencyList.get(toVertex).add(fromVertex);
        }

        int[] parent = new int[vertexCount];
        int[] depth = new int[vertexCount];

        int root = 0;
        int rootParent = -1;
        depthFirstSearch(adjacencyList, root, rootParent, parent, depth);

        int queryVertexA = SCANNER.nextInt();
        int queryVertexB = SCANNER.nextInt();

        System.out.println(findLowestCommonAncestor(queryVertexA, queryVertexB, depth, parent));
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

        for (int neighbor : adjacencyList.get(currentVertex)) {
            if (neighbor != parentVertex) {
                parent[neighbor] = currentVertex;
                depth[neighbor] = depth[currentVertex] + 1;
                depthFirstSearch(adjacencyList, neighbor, currentVertex, parent, depth);
            }
        }
    }

    /**
     * Method to calculate Lowest Common Ancestor.
     *
     * @param vertexA The first vertex
     * @param vertexB The second vertex
     * @param depth An array with depths of all vertices
     * @param parent An array with parents of all vertices
     * @return Returns a vertex that is LCA of vertexA and vertexB
     */
    private static int findLowestCommonAncestor(
            int vertexA, int vertexB, int[] depth, int[] parent) {

        if (depth[vertexA] < depth[vertexB]) {
            int temp = vertexA;
            vertexA = vertexB;
            vertexB = temp;
        }

        while (depth[vertexA] != depth[vertexB]) {
            vertexA = parent[vertexA];
        }

        if (vertexA == vertexB) {
            return vertexA;
        }

        while (vertexA != vertexB) {
            vertexA = parent[vertexA];
            vertexB = parent[vertexB];
        }

        return vertexA;
    }
}