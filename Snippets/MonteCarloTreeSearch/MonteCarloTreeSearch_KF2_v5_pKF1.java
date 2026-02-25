package com.thealgorithms.searches;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class MonteCarloTreeSearch {

    public class Node {

        Node parent;
        ArrayList<Node> children;
        boolean isPlayerTurn;
        boolean hasPlayerWon;
        int totalScore;
        int visitCount;

        public Node() {}

        public Node(Node parent, boolean isPlayerTurn) {
            this.parent = parent;
            this.children = new ArrayList<>();
            this.isPlayerTurn = isPlayerTurn;
            this.hasPlayerWon = false;
            this.totalScore = 0;
            this.visitCount = 0;
        }
    }

    private static final int WIN_SCORE = 10;
    private static final int TIME_LIMIT_MILLIS = 500;
    private static final double EXPLORATION_CONSTANT = 1.41;
    private static final int INITIAL_CHILD_COUNT = 10;

    public Node runSearch(Node rootNode) {
        expandNode(rootNode, INITIAL_CHILD_COUNT);

        long searchEndTime = System.currentTimeMillis() + TIME_LIMIT_MILLIS;

        while (System.currentTimeMillis() < searchEndTime) {
            Node promisingNode = selectPromisingNode(rootNode);

            if (promisingNode.children.isEmpty()) {
                expandNode(promisingNode, INITIAL_CHILD_COUNT);
            }

            simulateRandomPlayout(promisingNode);
        }

        Node bestChildNode = getBestChild(rootNode);
        printChildScores(rootNode);
        System.out.format("%nThe optimal node is: %02d%n", rootNode.children.indexOf(bestChildNode) + 1);

        return bestChildNode;
    }

    public void expandNode(Node parentNode, int numberOfChildren) {
        for (int i = 0; i < numberOfChildren; i++) {
            parentNode.children.add(new Node(parentNode, !parentNode.isPlayerTurn));
        }
    }

    public Node selectPromisingNode(Node rootNode) {
        Node currentNode = rootNode;

        while (!currentNode.children.isEmpty()) {
            double bestUctValue = Double.NEGATIVE_INFINITY;
            int bestChildIndex = 0;

            for (int i = 0; i < currentNode.children.size(); i++) {
                Node childNode = currentNode.children.get(i);

                if (childNode.visitCount == 0) {
                    bestChildIndex = i;
                    break;
                }

                double averageScore = (double) childNode.totalScore / childNode.visitCount;
                double explorationTerm =
                    EXPLORATION_CONSTANT
                        * Math.sqrt(Math.log(currentNode.visitCount) / (double) childNode.visitCount);
                double uctValue = averageScore + explorationTerm;

                if (uctValue > bestUctValue) {
                    bestUctValue = uctValue;
                    bestChildIndex = i;
                }
            }

            currentNode = currentNode.children.get(bestChildIndex);
        }

        return currentNode;
    }

    public void simulateRandomPlayout(Node leafNode) {
        Random random = new Random();
        Node currentNode = leafNode;

        leafNode.hasPlayerWon = (random.nextInt(6) == 0);
        boolean isPlayerWinner = leafNode.hasPlayerWon;

        while (currentNode != null) {
            currentNode.visitCount++;

            boolean isCurrentPlayerWinner =
                (currentNode.isPlayerTurn && isPlayerWinner) ||
                (!currentNode.isPlayerTurn && !isPlayerWinner);

            if (isCurrentPlayerWinner) {
                currentNode.totalScore += WIN_SCORE;
            }

            currentNode = currentNode.parent;
        }
    }

    public Node getBestChild(Node rootNode) {
        return Collections.max(rootNode.children, Comparator.comparing(child -> child.totalScore));
    }

    public void printChildScores(Node rootNode) {
        System.out.println("N.\tScore\t\tVisits");

        for (int i = 0; i < rootNode.children.size(); i++) {
            Node childNode = rootNode.children.get(i);
            System.out.printf("%02d\t%d\t\t%d%n", i + 1, childNode.totalScore, childNode.visitCount);
        }
    }
}