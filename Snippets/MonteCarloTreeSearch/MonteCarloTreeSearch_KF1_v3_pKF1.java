package com.thealgorithms.searches;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class MonteCarloTreeSearch {

    public class Node {

        Node parent;
        ArrayList<Node> children;
        boolean isMaximizingPlayer;
        boolean isWinningState;
        int totalScore;
        int visitCount;

        public Node() {}

        public Node(Node parent, boolean isMaximizingPlayer) {
            this.parent = parent;
            this.children = new ArrayList<>();
            this.isMaximizingPlayer = isMaximizingPlayer;
            this.isWinningState = false;
            this.totalScore = 0;
            this.visitCount = 0;
        }
    }

    private static final int REWARD_SCORE = 10;
    private static final int SEARCH_TIME_LIMIT_MS = 500;
    private static final int DEFAULT_CHILDREN_COUNT = 10;
    private static final double EXPLORATION_CONSTANT = 1.41;

    public Node runSearch(Node rootNode) {
        expandNode(rootNode, DEFAULT_CHILDREN_COUNT);

        double searchEndTimeMillis = System.currentTimeMillis() + SEARCH_TIME_LIMIT_MS;

        while (System.currentTimeMillis() < searchEndTimeMillis) {
            Node promisingNode = selectPromisingNode(rootNode);

            if (promisingNode.children.isEmpty()) {
                expandNode(promisingNode, DEFAULT_CHILDREN_COUNT);
            }

            simulateAndBackpropagate(promisingNode);
        }

        Node bestChildNode = getBestChild(rootNode);
        printStatistics(rootNode);
        System.out.format("%nThe optimal node is: %02d%n", rootNode.children.indexOf(bestChildNode) + 1);

        return bestChildNode;
    }

    public void expandNode(Node parentNode, int numberOfChildrenToCreate) {
        for (int i = 0; i < numberOfChildrenToCreate; i++) {
            parentNode.children.add(new Node(parentNode, !parentNode.isMaximizingPlayer));
        }
    }

    public Node selectPromisingNode(Node rootNode) {
        Node currentNode = rootNode;

        while (!currentNode.children.isEmpty()) {
            double bestUctValue = Double.NEGATIVE_INFINITY;
            int bestChildIndex = 0;

            for (int childIndex = 0; childIndex < currentNode.children.size(); childIndex++) {
                Node childNode = currentNode.children.get(childIndex);

                if (childNode.visitCount == 0) {
                    bestChildIndex = childIndex;
                    break;
                }

                double averageReward = (double) childNode.totalScore / childNode.visitCount;
                double explorationTerm =
                        EXPLORATION_CONSTANT
                                * Math.sqrt(Math.log(currentNode.visitCount) / (double) childNode.visitCount);
                double uctValue = averageReward + explorationTerm;

                if (uctValue > bestUctValue) {
                    bestUctValue = uctValue;
                    bestChildIndex = childIndex;
                }
            }

            currentNode = currentNode.children.get(bestChildIndex);
        }

        return currentNode;
    }

    public void simulateAndBackpropagate(Node simulationStartNode) {
        Random random = new Random();
        Node currentNode = simulationStartNode;

        simulationStartNode.isWinningState = (random.nextInt(6) == 0);
        boolean simulationResultIsWin = simulationStartNode.isWinningState;

        while (currentNode != null) {
            currentNode.visitCount++;

            boolean isResultGoodForCurrentPlayer =
                    (currentNode.isMaximizingPlayer && simulationResultIsWin)
                            || (!currentNode.isMaximizingPlayer && !simulationResultIsWin);

            if (isResultGoodForCurrentPlayer) {
                currentNode.totalScore += REWARD_SCORE;
            }

            currentNode = currentNode.parent;
        }
    }

    public Node getBestChild(Node rootNode) {
        return Collections.max(rootNode.children, Comparator.comparing(child -> child.totalScore));
    }

    public void printStatistics(Node rootNode) {
        System.out.println("N.\tScore\t\tVisits");

        for (int childIndex = 0; childIndex < rootNode.children.size(); childIndex++) {
            Node childNode = rootNode.children.get(childIndex);
            System.out.printf("%02d\t%d\t\t%d%n", childIndex + 1, childNode.totalScore, childNode.visitCount);
        }
    }
}