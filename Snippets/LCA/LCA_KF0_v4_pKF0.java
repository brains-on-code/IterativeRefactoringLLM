package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class LCA {

    private static final Scanner SCANNER = new Scanner(System.in);

    private LCA() {
        // Utility class
    }

    public static void main(String[] args) {
        int vertexCount = SCANNER.nextInt();
        int edgeCount = vertexCount - 1;

        List<List<Integer>> adjacencyList = createEmptyAdjacencyList(vertexCount);
        readEdges(adjacencyList, edgeCount);

        int[] parent = new int[vertexCount];
        int[] depth = new int[vertexCount];

        int root = 0;
        parent[root] = -1;
        depth[root] = 0;

        depthFirstSearch(adjacencyList, root, -1, parent, depth);

        int firstVertex = SCANNER.nextInt();
        int secondVertex = SCANNER.nextInt();

        int lca = findLowestCommonAncestor(firstVertex, secondVertex, depth, parent);
        System.out.println(lca);
    }

    private static List<List<Integer>> createEmptyAdjacencyList(int vertexCount) {
        List<List<Integer>> adjacencyList = new ArrayList<>(vertexCount);
        for (int i = 0; i < vertexCount; i++) {
            adjacencyList.add(new ArrayList<>());
        }
        return adjacencyList;
    }

    private static void readEdges(List<List<Integer>> adjacencyList, int edgeCount) {
        for (int i = 0; i < edgeCount; i++) {
            int from = SCANNER.nextInt();
            int to = SCANNER.nextInt();
            adjacencyList.get(from).add(to);
            adjacencyList.get(to).add(from);
        }
    }

    /**
     * Depth first search to calculate parent and depth of every vertex.
     *
     * @param adjacencyList The adjacency list representation of the tree
     * @param current       The current vertex
     * @param parentVertex  Parent of current
     * @param parent        An array to store parents of all vertices
     * @param depth         An array to store depth of all vertices
     */
    private static void depthFirstSearch(
            List<List<Integer>> adjacencyList,
            int current,
            int parentVertex,
            int[] parent,
            int[] depth
    ) {
        for (int neighbor : adjacencyList.get(current)) {
            if (neighbor == parentVertex) {
                continue;
            }
            parent[neighbor] = current;
            depth[neighbor] = depth[current] + 1;
            depthFirstSearch(adjacencyList, neighbor, current, parent, depth);
        }
    }

    /**
     * Method to calculate Lowest Common Ancestor.
     *
     * @param firstVertex  The first vertex
     * @param secondVertex The second vertex
     * @param depth        An array with depths of all vertices
     * @param parent       An array with parents of all vertices
     * @return The vertex that is LCA of firstVertex and secondVertex
     */
    private static int findLowestCommonAncestor(
            int firstVertex,
            int secondVertex,
            int[] depth,
            int[] parent
    ) {
        int deeperVertex = firstVertex;
        int shallowerVertex = secondVertex;

        if (depth[deeperVertex] < depth[shallowerVertex]) {
            deeperVertex = secondVertex;
            shallowerVertex = firstVertex;
        }

        deeperVertex = liftToSameDepth(deeperVertex, shallowerVertex, depth, parent);

        if (deeperVertex == shallowerVertex) {
            return deeperVertex;
        }

        while (deeperVertex != shallowerVertex) {
            deeperVertex = parent[deeperVertex];
            shallowerVertex = parent[shallowerVertex];
        }

        return deeperVertex;
    }

    private static int liftToSameDepth(
            int deeperVertex,
            int shallowerVertex,
            int[] depth,
            int[] parent
    ) {
        while (depth[deeperVertex] > depth[shallowerVertex]) {
            deeperVertex = parent[deeperVertex];
        }
        return deeperVertex;
    }
}