package com.thealgorithms.searches;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Monte Carlo Tree Search (MCTS) is a heuristic search algorithm used in
 * decision-taking problems, especially games.
 *
 * See more:
 * https://en.wikipedia.org/wiki/Monte_Carlo_tree_search
 * https://www.baeldung.com/java-monte-carlo-tree-search
 */
public class MonteCarloTreeSearch {

    private static final int WIN_SCORE = 10;
    /** Time the algorithm will be running for (in milliseconds). */
    private static final int TIME_LIMIT_MS = 500;
    private static final double EXPLORATION_CONSTANT = 1.41;
    private static final Random RANDOM = new Random();

    public class Node {

        Node parent;
        ArrayList<Node> childNodes;
        boolean isPlayersTurn; // True if it is the player's turn.
        boolean playerWon;     // True if the player won; false if the opponent won.
        int score;
        int visitCount;

        public Node() {
            this(null, true);
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

    /**
     * Explores a game tree using Monte Carlo Tree Search (MCTS) and returns the
     * most promising node.
     *
     * @param rootNode Root node of the game tree.
     * @return The most promising child of the root node.
     */
    public Node monteCarloTreeSearch(Node rootNode) {
        // Expand the root node.
        addChildNodes(rootNode, 10);

        long endTime = System.currentTimeMillis() + TIME_LIMIT_MS;

        // Explore the tree until the time limit is reached.
        while (System.currentTimeMillis() < endTime) {
            Node promisingNode = getPromisingNode(rootNode);

            // Expand the promising node if it has no children.
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

    public void addChildNodes(Node node, int childCount) {
        for (int i = 0; i < childCount; i++) {
            node.childNodes.add(new Node(node, !node.isPlayersTurn));
        }
    }

    /**
     * Uses UCT (Upper Confidence bounds applied to Trees) to find a promising
     * child node to be explored.
     *
     * @param rootNode Root node of the tree.
     * @return The most promising node according to UCT.
     */
    public Node getPromisingNode(Node rootNode) {
        Node currentNode = rootNode;

        // Iterate until a node that hasn't been expanded is found.
        while (!currentNode.childNodes.isEmpty()) {
            currentNode = selectBestChildByUct(currentNode);
        }

        return currentNode;
    }

    private Node selectBestChildByUct(Node node) {
        Node bestChild = null;
        double bestUctValue = Double.NEGATIVE_INFINITY;

        for (Node child : node.childNodes) {
            // If child node has never been visited, it will have the highest UCT value.
            if (child.visitCount == 0) {
                return child;
            }

            double exploitation = (double) child.score / child.visitCount;
            double exploration = Math.sqrt(Math.log(node.visitCount) / (double) child.visitCount);
            double uctValue = exploitation + EXPLORATION_CONSTANT * exploration;

            if (uctValue > bestUctValue) {
                bestUctValue = uctValue;
                bestChild = child;
            }
        }

        return bestChild;
    }

    /**
     * Simulates a random play from a node's current state and back-propagates
     * the result.
     *
     * @param promisingNode Node that will be simulated.
     */
    public void simulateRandomPlay(Node promisingNode) {
        Node tempNode = promisingNode;

        // The following line randomly determines whether the simulated play is a win or loss.
        // To use the MCTS algorithm correctly this should be a simulation of the node's current
        // state of the game until it finishes (if possible) and use an evaluation function to
        // determine how good or bad the play was.
        promisingNode.playerWon = (RANDOM.nextInt(6) == 0);
        boolean isPlayerWinner = promisingNode.playerWon;

        // Back propagation of the random play.
        while (tempNode != null) {
            tempNode.visitCount++;

            // Add winning scores to both player and opponent depending on the turn.
            if ((tempNode.isPlayersTurn && isPlayerWinner)
                    || (!tempNode.isPlayersTurn && !isPlayerWinner)) {
                tempNode.score += WIN_SCORE;
            }

            tempNode = tempNode.parent;
        }
    }

    public Node getWinnerNode(Node rootNode) {
        return Collections.max(rootNode.childNodes, Comparator.comparingInt(c -> c.score));
    }

    public void printScores(Node rootNode) {
        System.out.println("N.\tScore\t\tVisits");
        for (int i = 0; i < rootNode.childNodes.size(); i++) {
            Node child = rootNode.childNodes.get(i);
            System.out.printf("%02d\t%d\t\t%d%n", i + 1, child.score, child.visitCount);
        }
    }
}