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
    private static final int TIME_LIMIT_MS = 500;
    private static final int INITIAL_CHILD_COUNT = 10;
    private static final double EXPLORATION_CONSTANT = 1.41;

    private final Random random = new Random();

    public Node monteCarloTreeSearch(Node rootNode) {
        addChildNodes(rootNode, INITIAL_CHILD_COUNT);

        long endTime = System.currentTimeMillis() + TIME_LIMIT_MS;

        while (System.currentTimeMillis() < endTime) {
            Node promisingNode = selectPromisingNode(rootNode);

            if (promisingNode.children.isEmpty()) {
                addChildNodes(promisingNode, INITIAL_CHILD_COUNT);
            }

            simulateRandomPlay(promisingNode);
        }

        Node winnerNode = getBestChild(rootNode);
        printScores(rootNode);
        System.out.format("%nThe optimal node is: %02d%n", rootNode.children.indexOf(winnerNode) + 1);

        return winnerNode;
    }

    public void addChildNodes(Node node, int childCount) {
        for (int i = 0; i < childCount; i++) {
            node.children.add(new Node(node, !node.isPlayersTurn));
        }
    }

    public Node selectPromisingNode(Node rootNode) {
        Node currentNode = rootNode;

        while (!currentNode.children.isEmpty()) {
            currentNode = selectBestChildByUct(currentNode);
        }

        return currentNode;
    }

    private Node selectBestChildByUct(Node parent) {
        double bestUctValue = Double.NEGATIVE_INFINITY;
        Node bestChild = null;

        for (Node child : parent.children) {
            if (child.visitCount == 0) {
                return child;
            }

            double uctValue = calculateUctValue(parent, child);

            if (uctValue > bestUctValue) {
                bestUctValue = uctValue;
                bestChild = child;
            }
        }

        return bestChild;
    }

    private double calculateUctValue(Node parent, Node child) {
        double exploitation = (double) child.score / child.visitCount;
        double exploration = Math.sqrt(Math.log(parent.visitCount) / (double) child.visitCount);
        return exploitation + EXPLORATION_CONSTANT * exploration;
    }

    public void simulateRandomPlay(Node promisingNode) {
        boolean isPlayerWinner = random.nextInt(6) == 0;
        promisingNode.playerWon = isPlayerWinner;

        Node currentNode = promisingNode;
        while (currentNode != null) {
            currentNode.visitCount++;

            boolean isCurrentPlayerWinner =
                (currentNode.isPlayersTurn && isPlayerWinner)
                    || (!currentNode.isPlayersTurn && !isPlayerWinner);

            if (isCurrentPlayerWinner) {
                currentNode.score += WIN_SCORE;
            }

            currentNode = currentNode.parent;
        }
    }

    public Node getBestChild(Node rootNode) {
        return Collections.max(rootNode.children, Comparator.comparingInt(c -> c.score));
    }

    public void printScores(Node rootNode) {
        System.out.println("N.\tScore\t\tVisits");
        for (int i = 0; i < rootNode.children.size(); i++) {
            Node child = rootNode.children.get(i);
            System.out.printf("%02d\t%d\t\t%d%n", i + 1, child.score, child.visitCount);
        }
    }
}