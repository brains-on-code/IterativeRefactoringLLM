package com.thealgorithms.searches;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class MonteCarloTreeSearch {

    private static final int WIN_SCORE = 10;
    private static final int TIME_LIMIT_MS = 500;
    private static final int DEFAULT_CHILD_COUNT = 10;
    private static final double EXPLORATION_PARAMETER = 1.41;
    private static final int WIN_PROBABILITY_DENOMINATOR = 6;

    public class Node {

        private Node parent;
        private final ArrayList<Node> children;
        private final boolean isPlayersTurn;
        private boolean playerWon;
        private int score;
        private int visitCount;

        public Node() {
            this(null, true);
        }

        public Node(Node parent, boolean isPlayersTurn) {
            this.parent = parent;
            this.children = new ArrayList<>();
            this.isPlayersTurn = isPlayersTurn;
            this.playerWon = false;
            this.score = 0;
            this.visitCount = 0;
        }

        public Node getParent() {
            return parent;
        }

        public ArrayList<Node> getChildren() {
            return children;
        }

        public boolean isPlayersTurn() {
            return isPlayersTurn;
        }

        public boolean isPlayerWon() {
            return playerWon;
        }

        public void setPlayerWon(boolean playerWon) {
            this.playerWon = playerWon;
        }

        public int getScore() {
            return score;
        }

        public void addScore(int delta) {
            this.score += delta;
        }

        public int getVisitCount() {
            return visitCount;
        }

        public void incrementVisitCount() {
            this.visitCount++;
        }
    }

    public Node monteCarloTreeSearch(Node rootNode) {
        addChildNodes(rootNode, DEFAULT_CHILD_COUNT);

        long endTime = System.currentTimeMillis() + TIME_LIMIT_MS;

        while (System.currentTimeMillis() < endTime) {
            Node promisingNode = getPromisingNode(rootNode);

            if (promisingNode.getChildren().isEmpty()) {
                addChildNodes(promisingNode, DEFAULT_CHILD_COUNT);
            }

            simulateRandomPlay(promisingNode);
        }

        Node winnerNode = getWinnerNode(rootNode);
        printScores(rootNode);
        System.out.format("%nThe optimal node is: %02d%n", rootNode.getChildren().indexOf(winnerNode) + 1);

        return winnerNode;
    }

    public void addChildNodes(Node node, int childCount) {
        for (int i = 0; i < childCount; i++) {
            node.getChildren().add(new Node(node, !node.isPlayersTurn()));
        }
    }

    public Node getPromisingNode(Node rootNode) {
        Node currentNode = rootNode;

        while (!currentNode.getChildren().isEmpty()) {
            currentNode = selectBestChildByUCT(currentNode);
        }

        return currentNode;
    }

    private Node selectBestChildByUCT(Node node) {
        Node bestChild = null;
        double bestUctValue = Double.NEGATIVE_INFINITY;

        for (Node child : node.getChildren()) {
            if (child.getVisitCount() == 0) {
                return child;
            }

            double exploitation = (double) child.getScore() / child.getVisitCount();
            double exploration = Math.sqrt(Math.log(node.getVisitCount()) / (double) child.getVisitCount());
            double uctValue = exploitation + EXPLORATION_PARAMETER * exploration;

            if (uctValue > bestUctValue) {
                bestUctValue = uctValue;
                bestChild = child;
            }
        }

        return bestChild;
    }

    public void simulateRandomPlay(Node promisingNode) {
        Random random = new Random();
        boolean isPlayerWinner = random.nextInt(WIN_PROBABILITY_DENOMINATOR) == 0;
        promisingNode.setPlayerWon(isPlayerWinner);

        Node currentNode = promisingNode;
        while (currentNode != null) {
            currentNode.incrementVisitCount();

            boolean isCurrentPlayerWinner =
                (currentNode.isPlayersTurn() && isPlayerWinner) ||
                (!currentNode.isPlayersTurn() && !isPlayerWinner);

            if (isCurrentPlayerWinner) {
                currentNode.addScore(WIN_SCORE);
            }

            currentNode = currentNode.getParent();
        }
    }

    public Node getWinnerNode(Node rootNode) {
        return Collections.max(
            rootNode.getChildren(),
            Comparator.comparingInt(Node::getScore)
        );
    }

    public void printScores(Node rootNode) {
        System.out.println("N.\tScore\t\tVisits");
        for (int i = 0; i < rootNode.getChildren().size(); i++) {
            Node child = rootNode.getChildren().get(i);
            System.out.printf("%02d\t%d\t\t%d%n", i + 1, child.getScore(), child.getVisitCount());
        }
    }
}