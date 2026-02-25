package com.thealgorithms.searches;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Monte Carlo Tree Search (MCTS) is a heuristic search algorithm used in
 * decision taking problems especially games.
 *
 * See more: https://en.wikipedia.org/wiki/Monte_Carlo_tree_search,
 * https://www.baeldung.com/java-monte-carlo-tree-search
 */
public class MonteCarloTreeSearch {

    public class Node {

        Node parent;
        ArrayList<Node> children;
        boolean isPlayerTurn;
        boolean isPlayerWin;
        int totalScore;
        int visitCount;

        public Node() {
        }

        public Node(Node parent, boolean isPlayerTurn) {
            this.parent = parent;
            this.children = new ArrayList<>();
            this.isPlayerTurn = isPlayerTurn;
            this.isPlayerWin = false;
            this.totalScore = 0;
            this.visitCount = 0;
        }
    }

    private static final int WIN_SCORE = 10;
    private static final int TIME_LIMIT_MILLIS = 500;
    private static final int DEFAULT_CHILDREN_COUNT = 10;
    private static final double EXPLORATION_CONSTANT = 1.41;

    /**
     * Explores a game tree using Monte Carlo Tree Search (MCTS) and returns the
     * most promising node.
     *
     * @param rootNode Root node of the game tree.
     * @return The most promising child of the root node.
     */
    public Node runSearch(Node rootNode) {
        Node bestChildNode;

        expandNode(rootNode, DEFAULT_CHILDREN_COUNT);

        double searchEndTimeMillis = System.currentTimeMillis() + TIME_LIMIT_MILLIS;

        while (System.currentTimeMillis() < searchEndTimeMillis) {
            Node promisingNode = selectPromisingNode(rootNode);

            if (promisingNode.children.isEmpty()) {
                expandNode(promisingNode, DEFAULT_CHILDREN_COUNT);
            }

            simulateRandomPlayout(promisingNode);
        }

        bestChildNode = getBestChildNode(rootNode);
        printChildScores(rootNode);
        System.out.format("%nThe optimal node is: %02d%n", rootNode.children.indexOf(bestChildNode) + 1);

        return bestChildNode;
    }

    public void expandNode(Node parentNode, int numberOfChildren) {
        for (int childIndex = 0; childIndex < numberOfChildren; childIndex++) {
            parentNode.children.add(new Node(parentNode, !parentNode.isPlayerTurn));
        }
    }

    /**
     * Uses UCT to find a promising child node to be explored.
     *
     * UCT: Upper Confidence bounds applied to Trees.
     *
     * @param rootNode Root node of the tree.
     * @return The most promising node according to UCT.
     */
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

                double averageScore = (double) childNode.totalScore / childNode.visitCount;
                double explorationTerm =
                    EXPLORATION_CONSTANT * Math.sqrt(Math.log(currentNode.visitCount) / (double) childNode.visitCount);
                double uctValue = averageScore + explorationTerm;

                if (uctValue > bestUctValue) {
                    bestUctValue = uctValue;
                    bestChildIndex = childIndex;
                }
            }

            currentNode = currentNode.children.get(bestChildIndex);
        }

        return currentNode;
    }

    /**
     * Simulates a random play from a node's current state and back propagates
     * the result.
     *
     * @param startNode Node that will be simulated.
     */
    public void simulateRandomPlayout(Node startNode) {
        Random random = new Random();
        Node currentNode = startNode;

        startNode.isPlayerWin = (random.nextInt(6) == 0);

        boolean isPlayerWinner = startNode.isPlayerWin;

        while (currentNode != null) {
            currentNode.visitCount++;

            boolean isPlayersTurnAndPlayerWon = currentNode.isPlayerTurn && isPlayerWinner;
            boolean isOpponentsTurnAndOpponentWon = !currentNode.isPlayerTurn && !isPlayerWinner;

            if (isPlayersTurnAndPlayerWon || isOpponentsTurnAndOpponentWon) {
                currentNode.totalScore += WIN_SCORE;
            }

            currentNode = currentNode.parent;
        }
    }

    public Node getBestChildNode(Node rootNode) {
        return Collections.max(rootNode.children, Comparator.comparing(child -> child.totalScore));
    }

    public void printChildScores(Node rootNode) {
        System.out.println("N.\tScore\t\tVisits");

        for (int childIndex = 0; childIndex < rootNode.children.size(); childIndex++) {
            Node childNode = rootNode.children.get(childIndex);
            System.out.printf("%02d\t%d\t\t%d%n", childIndex + 1, childNode.totalScore, childNode.visitCount);
        }
    }
}