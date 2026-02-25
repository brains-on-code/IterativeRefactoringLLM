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

        int numberOfNodes = INPUT_SCANNER.nextInt();
        int numberOfEdges = numberOfNodes - 1;

        for (int nodeIndex = 0; nodeIndex < numberOfNodes; nodeIndex++) {
            adjacencyList.add(new ArrayList<>());
        }

        for (int edgeIndex = 0; edgeIndex < numberOfEdges; edgeIndex++) {
            int sourceNode = INPUT_SCANNER.nextInt();
            int destinationNode = INPUT_SCANNER.nextInt();

            adjacencyList.get(sourceNode).add(destinationNode);
            adjacencyList.get(destinationNode).add(sourceNode);
        }

        int[] parentNode = new int[numberOfNodes];
        int[] nodeDepth = new int[numberOfNodes];

        buildParentAndDepth(adjacencyList, 0, -1, parentNode, nodeDepth);

        int firstQueryNode = INPUT_SCANNER.nextInt();
        int secondQueryNode = INPUT_SCANNER.nextInt();

        System.out.println(findLowestCommonAncestor(firstQueryNode, secondQueryNode, nodeDepth, parentNode));
    }

    private static void buildParentAndDepth(
            List<List<Integer>> adjacencyList,
            int currentNode,
            int parentOfCurrentNode,
            int[] parentNode,
            int[] nodeDepth
    ) {
        for (int neighborNode : adjacencyList.get(currentNode)) {
            if (neighborNode != parentOfCurrentNode) {
                parentNode[neighborNode] = currentNode;
                nodeDepth[neighborNode] = nodeDepth[currentNode] + 1;
                buildParentAndDepth(adjacencyList, neighborNode, currentNode, parentNode, nodeDepth);
            }
        }
    }

    private static int findLowestCommonAncestor(
            int firstNode,
            int secondNode,
            int[] nodeDepth,
            int[] parentNode
    ) {
        if (nodeDepth[firstNode] < nodeDepth[secondNode]) {
            int temporaryNode = firstNode;
            firstNode = secondNode;
            secondNode = temporaryNode;
        }

        while (nodeDepth[firstNode] != nodeDepth[secondNode]) {
            firstNode = parentNode[firstNode];
        }

        if (firstNode == secondNode) {
            return firstNode;
        }

        while (firstNode != secondNode) {
            firstNode = parentNode[firstNode];
            secondNode = parentNode[secondNode];
        }

        return firstNode;
    }
}