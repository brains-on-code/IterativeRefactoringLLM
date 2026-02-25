package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class LowestCommonAncestor {
    private LowestCommonAncestor() {
    }

    private static final Scanner INPUT_SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        List<List<Integer>> adjacencyList = new ArrayList<>();

        int nodeCount = INPUT_SCANNER.nextInt();
        int edgeCount = nodeCount - 1;

        for (int nodeIndex = 0; nodeIndex < nodeCount; nodeIndex++) {
            adjacencyList.add(new ArrayList<>());
        }

        for (int edgeIndex = 0; edgeIndex < edgeCount; edgeIndex++) {
            int sourceNode = INPUT_SCANNER.nextInt();
            int destinationNode = INPUT_SCANNER.nextInt();

            adjacencyList.get(sourceNode).add(destinationNode);
            adjacencyList.get(destinationNode).add(sourceNode);
        }

        int[] parentOfNode = new int[nodeCount];
        int[] depthOfNode = new int[nodeCount];

        buildParentAndDepth(adjacencyList, 0, -1, parentOfNode, depthOfNode);

        int firstQueryNode = INPUT_SCANNER.nextInt();
        int secondQueryNode = INPUT_SCANNER.nextInt();

        System.out.println(findLowestCommonAncestor(firstQueryNode, secondQueryNode, depthOfNode, parentOfNode));
    }

    private static void buildParentAndDepth(
            List<List<Integer>> adjacencyList,
            int currentNode,
            int parentNode,
            int[] parentOfNode,
            int[] depthOfNode
    ) {
        for (int neighborNode : adjacencyList.get(currentNode)) {
            if (neighborNode != parentNode) {
                parentOfNode[neighborNode] = currentNode;
                depthOfNode[neighborNode] = depthOfNode[currentNode] + 1;
                buildParentAndDepth(adjacencyList, neighborNode, currentNode, parentOfNode, depthOfNode);
            }
        }
    }

    private static int findLowestCommonAncestor(
            int firstNode,
            int secondNode,
            int[] depthOfNode,
            int[] parentOfNode
    ) {
        if (depthOfNode[firstNode] < depthOfNode[secondNode]) {
            int tempNode = firstNode;
            firstNode = secondNode;
            secondNode = tempNode;
        }

        while (depthOfNode[firstNode] != depthOfNode[secondNode]) {
            firstNode = parentOfNode[firstNode];
        }

        if (firstNode == secondNode) {
            return firstNode;
        }

        while (firstNode != secondNode) {
            firstNode = parentOfNode[firstNode];
            secondNode = parentOfNode[secondNode];
        }

        return firstNode;
    }
}