package com.thealgorithms.searches;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Monte Carlo Tree Search (MCTS) is a heuristic search algorithm used in
 * decision-making problems, especially games.
 *
 * References:
 * https://en.wikipedia.org/wiki/Monte_Carlo_tree_search
 * https://www.baeldung.com/java-monte-carlo-tree-search
 */
public class MonteCarloTreeSearch {

    public class Node {

        Node parent;
        ArrayList<Node> childNodes;

        /** True if it is the player's turn at this node. */
        boolean isPlayersTurn;

        /** True if the player wins in the simulated outcome from this node. */
        boolean playerWon;

        int score;
        int visitCount;

        public Node() {
        }

        public Node(Node parent, boolean isPlayersTurn) {
            this.parent = parent;
            this.childNodes = new ArrayList<>();
            this.isPlayersTurn = isPlayersTurn;
            this.playerWon = false;
            this.score = 0;
            this.visitCount = 0;
        }
    }

    static final int WIN_SCORE = 10;

    /** Time budget for the search in milliseconds. */
    static final int TIME_LIMIT = 500;

    /**
     * Runs Monte Carlo Tree Search (MCTS) starting from the given root node and
     * returns the most promising child of the root.
     *
     * @param rootNode root node of the game tree
     * @return the most promising child of the root node
     */
    public Node monteCarloTreeSearch(Node rootNode) {
        addChildNodes(rootNode, 10);

        double timeLimit = System.currentTimeMillis() + TIME_LIMIT;

        while (System.currentTimeMillis() < timeLimit) {
            Node promisingNode = getPromisingNode(rootNode);

            if (promisingNode.childNodes.isEmpty()) {
                addChildNodes(promisingNode, 10);
            }

            simulateRandomPlay(promisingNode);
        }

        Node winnerNode = getWinnerNode(rootNode);
        printScores(rootNode);
        System.out.format("%nThe optimal node is: %02d%n", rootNode.childNodes.indexOf(winnerNode) + 1);

        return winnerNode;
    }

    /**
     * Adds a fixed number of child nodes to the given node, alternating the
     * player turn.
     *
     * @param node       parent node
     * @param childCount number of children to add
     */
    public void addChildNodes(Node node, int childCount) {
        for (int i = 0; i < childCount; i++) {
            node.childNodes.add(new Node(node, !node.isPlayersTurn));
        }
    }

    /**
     * Selects a promising node for further exploration using UCT
     * (Upper Confidence bounds applied to Trees).
     *
     * @param rootNode root node of the tree
     * @return the most promising node according to UCT
     */
    public Node getPromisingNode(Node rootNode) {
        Node promisingNode = rootNode;

        while (!promisingNode.childNodes.isEmpty()) {
            double bestUctValue = Double.NEGATIVE_INFINITY;
            int bestIndex = 0;

            for (int i = 0; i < promisingNode.childNodes.size(); i++) {
                Node childNode = promisingNode.childNodes.get(i);

                if (childNode.visitCount == 0) {
                    bestIndex = i;
                    break;
                }

                double exploitation = (double) childNode.score / childNode.visitCount;
                double exploration = Math.sqrt(Math.log(promisingNode.visitCount) / (double) childNode.visitCount);
                double uctValue = exploitation + 1.41 * exploration;

                if (uctValue > bestUctValue) {
                    bestUctValue = uctValue;
                    bestIndex = i;
                }
            }

            promisingNode = promisingNode.childNodes.get(bestIndex);
        }

        return promisingNode;
    }

    /**
     * Simulates a random play from the given node and backpropagates the
     * result up the tree.
     *
     * In a real game implementation, this method should:
     * 1. Simulate the game from the node's state until termination (or depth limit).
     * 2. Evaluate the terminal state.
     * 3. Use that evaluation as the outcome instead of the random win/loss below.
     *
     * @param promisingNode node from which the simulation starts
     */
    public void simulateRandomPlay(Node promisingNode) {
        Random rand = new Random();
        Node tempNode = promisingNode;

        promisingNode.playerWon = (rand.nextInt(6) == 0);
        boolean isPlayerWinner = promisingNode.playerWon;

        while (tempNode != null) {
            tempNode.visitCount++;

            if ((tempNode.isPlayersTurn && isPlayerWinner)
                    || (!tempNode.isPlayersTurn && !isPlayerWinner)) {
                tempNode.score += WIN_SCORE;
            }

            tempNode = tempNode.parent;
        }
    }

    /**
     * Returns the child of the root node with the highest score.
     *
     * @param rootNode root node
     * @return child node with maximum score
     */
    public Node getWinnerNode(Node rootNode) {
        return Collections.max(rootNode.childNodes, Comparator.comparing(c -> c.score));
    }

    /**
     * Prints the score and visit count for each child of the root node.
     *
     * @param rootNode root node
     */
    public void printScores(Node rootNode) {
        System.out.println("N.\tScore\t\tVisits");

        for (int i = 0; i < rootNode.childNodes.size(); i++) {
            Node child = rootNode.childNodes.get(i);
            System.out.printf("%02d\t%d\t\t%d%n", i + 1, child.score, child.visitCount);
        }
    }
}