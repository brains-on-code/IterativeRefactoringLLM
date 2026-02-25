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
        boolean isPlayersTurn; // True if it is the player's turn.
        boolean playerWon; // True if the player won; false if the opponent won.
        int score;
        int visitCount;

        public Node() {
        }

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
    private static final int TIME_LIMIT_MILLIS = 500; // Time the algorithm will be running for (in milliseconds).

    /**
     * Explores a game tree using Monte Carlo Tree Search (MCTS) and returns the
     * most promising node.
     *
     * @param rootNode Root node of the game tree.
     * @return The most promising child of the root node.
     */
    public Node monteCarloTreeSearch(Node rootNode) {
        Node bestChildNode;

        // Expand the root node.
        addChildNodes(rootNode, 10);

        double searchEndTimeMillis = System.currentTimeMillis() + TIME_LIMIT_MILLIS;

        // Explore the tree until the time limit is reached.
        while (System.currentTimeMillis() < searchEndTimeMillis) {
            Node promisingNode = selectPromisingNode(rootNode);

            // Expand the promising node.
            if (promisingNode.children.isEmpty()) {
                addChildNodes(promisingNode, 10);
            }

            simulateRandomPlayout(promisingNode);
        }

        bestChildNode = getBestChildNode(rootNode);
        printChildScores(rootNode);
        System.out.format("%nThe optimal node is: %02d%n", rootNode.children.indexOf(bestChildNode) + 1);

        return bestChildNode;
    }

    public void addChildNodes(Node parentNode, int numberOfChildren) {
        for (int i = 0; i < numberOfChildren; i++) {
            parentNode.children.add(new Node(parentNode, !parentNode.isPlayersTurn));
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

        // Iterate until a node that hasn't been expanded is found.
        while (!currentNode.children.isEmpty()) {
            double bestUctValue = Double.MIN_VALUE;
            int bestChildIndex = 0;

            // Iterate through child nodes and pick the most promising one
            // using UCT (Upper Confidence bounds applied to Trees).
            for (int i = 0; i < currentNode.children.size(); i++) {
                Node childNode = currentNode.children.get(i);

                // If child node has never been visited
                // it will have the highest UCT value.
                if (childNode.visitCount == 0) {
                    bestChildIndex = i;
                    break;
                }

                double exploitationTerm = (double) childNode.score / childNode.visitCount;
                double explorationTerm =
                    1.41 * Math.sqrt(Math.log(currentNode.visitCount) / (double) childNode.visitCount);
                double uctValue = exploitationTerm + explorationTerm;

                if (uctValue > bestUctValue) {
                    bestUctValue = uctValue;
                    bestChildIndex = i;
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

        // The following line randomly determines whether the simulated play is a win or loss.
        // To use the MCTS algorithm correctly this should be a simulation of the node's current
        // state of the game until it finishes (if possible) and use an evaluation function to
        // determine how good or bad the play was.
        // e.g. Play tic tac toe choosing random squares until the game ends.
        startNode.playerWon = (random.nextInt(6) == 0);

        boolean isPlayerWinner = startNode.playerWon;

        // Back propagation of the random play.
        while (currentNode != null) {
            currentNode.visitCount++;

            // Add winning scores to both player and opponent depending on the turn.
            boolean isPlayersTurnAndPlayerWon = currentNode.isPlayersTurn && isPlayerWinner;
            boolean isOpponentsTurnAndOpponentWon = !currentNode.isPlayersTurn && !isPlayerWinner;

            if (isPlayersTurnAndPlayerWon || isOpponentsTurnAndOpponentWon) {
                currentNode.score += WIN_SCORE;
            }

            currentNode = currentNode.parent;
        }
    }

    public Node getBestChildNode(Node rootNode) {
        return Collections.max(rootNode.children, Comparator.comparing(child -> child.score));
    }

    public void printChildScores(Node rootNode) {
        System.out.println("N.\tScore\t\tVisits");

        for (int i = 0; i < rootNode.children.size(); i++) {
            Node childNode = rootNode.children.get(i);
            System.out.printf("%02d\t%d\t\t%d%n", i + 1, childNode.score, childNode.visitCount);
        }
    }
}