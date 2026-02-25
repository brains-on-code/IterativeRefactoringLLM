package com.thealgorithms.searches;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class MonteCarloTreeSearch {

    public class Node {

        Node parent;
        ArrayList<Node> children;
        boolean isPlayersTurn;
        boolean playerWon;
        int score;
        int visitCount;

        public Node() {}

        public Node(Node parent, boolean isPlayersTurn) {
            this.parent = parent;
            this.children = new ArrayList<>();
            this.isPlayersTurn = isPlayersTurn;
            this.playerWon = false;
            this.score = 0;
            this.visitCount = 0;
        }
    }

    private static final int WIN_SCORE = 10;
    private static final int TIME_LIMIT_MILLIS = 500;

    public Node runSearch(Node rootNode) {
        addChildNodes(rootNode, 10);

        long endTime = System.currentTimeMillis() + TIME_LIMIT_MILLIS;

        while (System.currentTimeMillis() < endTime) {
            Node selectedNode = selectPromisingNode(rootNode);

            if (selectedNode.children.isEmpty()) {
                addChildNodes(selectedNode, 10);
            }

            simulateRandomPlayout(selectedNode);
        }

        Node bestChild = getBestChild(rootNode);
        printChildScores(rootNode);
        System.out.format("%nThe optimal node is: %02d%n", rootNode.children.indexOf(bestChild) + 1);

        return bestChild;
    }

    public void addChildNodes(Node parentNode, int numberOfChildren) {
        for (int i = 0; i < numberOfChildren; i++) {
            parentNode.children.add(new Node(parentNode, !parentNode.isPlayersTurn));
        }
    }

    public Node selectPromisingNode(Node rootNode) {
        Node currentNode = rootNode;

        while (!currentNode.children.isEmpty()) {
            double bestUctValue = Double.MIN_VALUE;
            int bestChildIndex = 0;

            for (int i = 0; i < currentNode.children.size(); i++) {
                Node child = currentNode.children.get(i);

                if (child.visitCount == 0) {
                    bestChildIndex = i;
                    break;
                }

                double exploitation = (double) child.score / child.visitCount;
                double exploration = 1.41 * Math.sqrt(Math.log(currentNode.visitCount) / (double) child.visitCount);
                double uctValue = exploitation + exploration;

                if (uctValue > bestUctValue) {
                    bestUctValue = uctValue;
                    bestChildIndex = i;
                }
            }

            currentNode = currentNode.children.get(bestChildIndex);
        }

        return currentNode;
    }

    public void simulateRandomPlayout(Node startNode) {
        Random random = new Random();
        Node currentNode = startNode;

        startNode.playerWon = (random.nextInt(6) == 0);
        boolean isPlayerWinner = startNode.playerWon;

        while (currentNode != null) {
            currentNode.visitCount++;

            boolean isCurrentPlayersWin =
                (currentNode.isPlayersTurn && isPlayerWinner) ||
                (!currentNode.isPlayersTurn && !isPlayerWinner);

            if (isCurrentPlayersWin) {
                currentNode.score += WIN_SCORE;
            }

            currentNode = currentNode.parent;
        }
    }

    public Node getBestChild(Node rootNode) {
        return Collections.max(rootNode.children, Comparator.comparing(child -> child.score));
    }

    public void printChildScores(Node rootNode) {
        System.out.println("N.\tScore\t\tVisits");

        for (int i = 0; i < rootNode.children.size(); i++) {
            Node child = rootNode.children.get(i);
            System.out.printf("%02d\t%d\t\t%d%n", i + 1, child.score, child.visitCount);
        }
    }
}