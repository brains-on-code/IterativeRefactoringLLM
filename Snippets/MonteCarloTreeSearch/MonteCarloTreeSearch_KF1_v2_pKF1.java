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

    static final int REWARD = 10;
    static final int TIME_LIMIT_MS = 500;

    public Node runSearch(Node rootNode) {
        expandNode(rootNode, 10);

        double searchEndTimeMillis = System.currentTimeMillis() + TIME_LIMIT_MS;

        while (System.currentTimeMillis() < searchEndTimeMillis) {
            Node selectedNode = selectPromisingNode(rootNode);

            if (selectedNode.children.isEmpty()) {
                expandNode(selectedNode, 10);
            }

            simulateAndBackpropagate(selectedNode);
        }

        Node bestChildNode = getBestChild(rootNode);
        printStatistics(rootNode);
        System.out.format("%nThe optimal node is: %02d%n", rootNode.children.indexOf(bestChildNode) + 1);

        return bestChildNode;
    }

    public void expandNode(Node parentNode, int numberOfChildren) {
        for (int i = 0; i < numberOfChildren; i++) {
            parentNode.children.add(new Node(parentNode, !parentNode.isMaximizingPlayer));
        }
    }

    public Node selectPromisingNode(Node rootNode) {
        Node currentNode = rootNode;

        while (!currentNode.children.isEmpty()) {
            double bestUctValue = Double.MIN_VALUE;
            int bestChildIndex = 0;

            for (int i = 0; i < currentNode.children.size(); i++) {
                Node childNode = currentNode.children.get(i);

                if (childNode.visitCount == 0) {
                    bestChildIndex = i;
                    break;
                }

                double averageReward = (double) childNode.totalScore / childNode.visitCount;
                double explorationTerm =
                        1.41 * Math.sqrt(Math.log(currentNode.visitCount) / (double) childNode.visitCount);
                double uctValue = averageReward + explorationTerm;

                if (uctValue > bestUctValue) {
                    bestUctValue = uctValue;
                    bestChildIndex = i;
                }
            }

            currentNode = currentNode.children.get(bestChildIndex);
        }

        return currentNode;
    }

    public void simulateAndBackpropagate(Node startNode) {
        Random random = new Random();
        Node currentNode = startNode;

        startNode.isWinningState = (random.nextInt(6) == 0);
        boolean simulationResultIsWin = startNode.isWinningState;

        while (currentNode != null) {
            currentNode.visitCount++;

            boolean isResultGoodForCurrentPlayer =
                    (currentNode.isMaximizingPlayer && simulationResultIsWin)
                            || (!currentNode.isMaximizingPlayer && !simulationResultIsWin);

            if (isResultGoodForCurrentPlayer) {
                currentNode.totalScore += REWARD;
            }

            currentNode = currentNode.parent;
        }
    }

    public Node getBestChild(Node rootNode) {
        return Collections.max(rootNode.children, Comparator.comparing(child -> child.totalScore));
    }

    public void printStatistics(Node rootNode) {
        System.out.println("N.\tScore\t\tVisits");

        for (int i = 0; i < rootNode.children.size(); i++) {
            Node childNode = rootNode.children.get(i);
            System.out.printf("%02d\t%d\t\t%d%n", i + 1, childNode.totalScore, childNode.visitCount);
        }
    }
}