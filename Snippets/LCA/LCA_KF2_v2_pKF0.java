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

        List<List<Integer>> adjacencyList = createAdjacencyList(vertexCount);
        readEdges(edgeCount, adjacencyList);

        int[] parent = new int[vertexCount];
        int[] depth = new int[vertexCount];

        buildParentAndDepth(adjacencyList, parent, depth);

        int firstVertex = SCANNER.nextInt();
        int secondVertex = SCANNER.nextInt();

        System.out.println(findLCA(firstVertex, secondVertex, depth, parent));
    }

    private static List<List<Integer>> createAdjacencyList(int vertexCount) {
        List<List<Integer>> adjacencyList = new ArrayList<>(vertexCount);
        for (int i = 0; i < vertexCount; i++) {
            adjacencyList.add(new ArrayList<>());
        }
        return adjacencyList;
    }

    private static void readEdges(int edgeCount, List<List<Integer>> adjacencyList) {
        for (int i = 0; i < edgeCount; i++) {
            int from = SCANNER.nextInt();
            int to = SCANNER.nextInt();

            adjacencyList.get(from).add(to);
            adjacencyList.get(to).add(from);
        }
    }

    private static void buildParentAndDepth(
            List<List<Integer>> adjacencyList,
            int[] parent,
            int[] depth
    ) {
        int root = 0;
        parent[root] = -1;
        depth[root] = 0;
        dfs(adjacencyList, root, -1, parent, depth);
    }

    private static void dfs(
            List<List<Integer>> adjacencyList,
            int current,
            int parentNode,
            int[] parent,
            int[] depth
    ) {
        for (int neighbor : adjacencyList.get(current)) {
            if (neighbor == parentNode) {
                continue;
            }
            parent[neighbor] = current;
            depth[neighbor] = depth[current] + 1;
            dfs(adjacencyList, neighbor, current, parent, depth);
        }
    }

    private static int findLCA(int firstVertex, int secondVertex, int[] depth, int[] parent) {
        int deeperVertex = firstVertex;
        int shallowerVertex = secondVertex;

        if (depth[deeperVertex] < depth[shallowerVertex]) {
            int temp = deeperVertex;
            deeperVertex = shallowerVertex;
            shallowerVertex = temp;
        }

        while (depth[deeperVertex] > depth[shallowerVertex]) {
            deeperVertex = parent[deeperVertex];
        }

        if (deeperVertex == shallowerVertex) {
            return deeperVertex;
        }

        while (deeperVertex != shallowerVertex) {
            deeperVertex = parent[deeperVertex];
            shallowerVertex = parent[shallowerVertex];
        }

        return deeperVertex;
    }
}