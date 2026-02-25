package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class LCA {

    private LCA() {}

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        List<List<Integer>> treeAdjacencyList = new ArrayList<>();

        int numberOfVertices = SCANNER.nextInt();
        int numberOfEdges = numberOfVertices - 1;

        for (int vertexIndex = 0; vertexIndex < numberOfVertices; vertexIndex++) {
            treeAdjacencyList.add(new ArrayList<>());
        }

        for (int edgeIndex = 0; edgeIndex < numberOfEdges; edgeIndex++) {
            int sourceVertex = SCANNER.nextInt();
            int destinationVertex = SCANNER.nextInt();

            treeAdjacencyList.get(sourceVertex).add(destinationVertex);
            treeAdjacencyList.get(destinationVertex).add(sourceVertex);
        }

        int[] parentOfVertex = new int[numberOfVertices];
        int[] depthOfVertex = new int[numberOfVertices];

        int rootVertex = 0;
        int rootParent = -1;
        computeParentAndDepthWithDfs(treeAdjacencyList, rootVertex, rootParent, parentOfVertex, depthOfVertex);

        int firstQueryVertex = SCANNER.nextInt();
        int secondQueryVertex = SCANNER.nextInt();

        System.out.println(
                findLowestCommonAncestor(firstQueryVertex, secondQueryVertex, depthOfVertex, parentOfVertex));
    }

    /**
     * Depth first search to calculate parent and depth of every vertex.
     *
     * @param adjacencyList The adjacency list representation of the tree
     * @param currentVertex The current vertex being visited
     * @param parentVertex Parent of the current vertex
     * @param parentOfVertex An array to store parents of all vertices
     * @param depthOfVertex An array to store depth of all vertices
     */
    private static void computeParentAndDepthWithDfs(
            List<List<Integer>> adjacencyList,
            int currentVertex,
            int parentVertex,
            int[] parentOfVertex,
            int[] depthOfVertex) {

        for (int neighborVertex : adjacencyList.get(currentVertex)) {
            if (neighborVertex != parentVertex) {
                parentOfVertex[neighborVertex] = currentVertex;
                depthOfVertex[neighborVertex] = depthOfVertex[currentVertex] + 1;
                computeParentAndDepthWithDfs(
                        adjacencyList, neighborVertex, currentVertex, parentOfVertex, depthOfVertex);
            }
        }
    }

    /**
     * Method to calculate Lowest Common Ancestor.
     *
     * @param firstVertex The first vertex
     * @param secondVertex The second vertex
     * @param depthOfVertex An array with depths of all vertices
     * @param parentOfVertex An array with parents of all vertices
     * @return Returns a vertex that is LCA of firstVertex and secondVertex
     */
    private static int findLowestCommonAncestor(
            int firstVertex, int secondVertex, int[] depthOfVertex, int[] parentOfVertex) {

        if (depthOfVertex[firstVertex] < depthOfVertex[secondVertex]) {
            int temporaryVertex = firstVertex;
            firstVertex = secondVertex;
            secondVertex = temporaryVertex;
        }

        while (depthOfVertex[firstVertex] != depthOfVertex[secondVertex]) {
            firstVertex = parentOfVertex[firstVertex];
        }

        if (firstVertex == secondVertex) {
            return firstVertex;
        }

        while (firstVertex != secondVertex) {
            firstVertex = parentOfVertex[firstVertex];
            secondVertex = parentOfVertex[secondVertex];
        }

        return firstVertex;
    }
}