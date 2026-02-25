package com.thealgorithms.searches;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Simple Monte Carlo Tree Search (MCTS) example.
 *
 * Runs rollouts from a root node for a fixed time, then selects and prints
 * the best child node according to its score.
 */
public class Class1 {

    /**
     * Node in the search tree.
     */
    public static class Node {

        /** Parent node (null for root). */
        Node parent;

        /** Children of this node. */
        ArrayList<Node> children = new ArrayList<>();

        /**
         * Player to move at this node.
         * true  - player one
         * false - player two
         */
        boolean isPlayerOne;

        /**
         * Outcome of the simulation from this node.
         * true  - win
         * false - loss
         */
        boolean isWin;

        /** Accumulated score for this node. */
        int score;

        /** Number of visits to this node. */
        int visits;

        public Node() {}

        public Node(Node parent, boolean isPlayerOne) {
            this.parent = parent;
            this.isPlayerOne = isPlayerOne;
        }
    }

    /** Reward added for a successful outcome. */
    private static final int REWARD = 10;

    /** Time budget for search in milliseconds. */
    private static final int TIME_LIMIT_MS = 500;

    /** Number of children to create when expanding a node. */
    private static final int DEFAULT_CHILD_COUNT = 10;

    /** Exploration constant used in the UCT formula. */
    private static final double UCT_EXPLORATION_CONSTANT = 1.41;

    private final Random random = new Random();

    /**
     * Runs a Monte Carlo Tree Search from the given root node.
     *
     * @param root the root node of the search tree
     * @return the best child of the root after search
     */
    public Node runSearch(Node root) {
        expand(root, DEFAULT_CHILD_COUNT);

        long endTime = System.currentTimeMillis() + TIME_LIMIT_MS;

        while (System.currentTimeMillis() < endTime) {
            Node selected = select(root);

            if (selected.children.isEmpty()) {
                expand(selected, DEFAULT_CHILD_COUNT);
            }

            simulateAndBackpropagate(selected);
        }

        Node bestChild = bestChild(root);
        printStats(root);
        System.out.format("%nThe optimal node is: %02d%n", root.children.indexOf(bestChild) + 1);

        return bestChild;
    }

    /**
     * Expands a node by adding the given number of children.
     *
     * @param node     node to expand
     * @param numChild number of children to add
     */
    public void expand(Node node, int numChild) {
        for (int i = 0; i < numChild; i++) {
            node.children.add(new Node(node, !node.isPlayerOne));
        }
    }

    /**
     * Selects a leaf node using the UCT (Upper Confidence Bound applied to Trees) rule.
     *
     * @param root starting node for selection
     * @return selected leaf node
     */
    public Node select(Node root) {
        Node current = root;

        while (!current.children.isEmpty()) {
            current = selectBestChildByUct(current);
        }

        return current;
    }

    private Node selectBestChildByUct(Node node) {
        double bestValue = Double.NEGATIVE_INFINITY;
        Node bestChild = null;

        for (Node child : node.children) {
            if (child.visits == 0) {
                return child;
            }

            double uctValue = uctValue(node, child);

            if (uctValue > bestValue) {
                bestValue = uctValue;
                bestChild = child;
            }
        }

        return bestChild;
    }

    private double uctValue(Node parent, Node child) {
        double exploitation = (double) child.score / child.visits;
        double exploration =
                UCT_EXPLORATION_CONSTANT * Math.sqrt(Math.log(parent.visits) / (double) child.visits);
        return exploitation + exploration;
    }

    /**
     * Performs a random simulation from the given node and backpropagates the result.
     *
     * @param node starting node for simulation
     */
    public void simulateAndBackpropagate(Node node) {
        boolean outcome = random.nextInt(6) == 0;
        node.isWin = outcome;

        Node current = node;
        while (current != null) {
            current.visits++;

            if (isSuccessfulOutcomeForNode(current, outcome)) {
                current.score += REWARD;
            }

            current = current.parent;
        }
    }

    private boolean isSuccessfulOutcomeForNode(Node node, boolean outcome) {
        return (node.isPlayerOne && outcome) || (!node.isPlayerOne && !outcome);
    }

    /**
     * Returns the child of the root with the highest score.
     *
     * @param root root node
     * @return best child node
     */
    public Node bestChild(Node root) {
        return Collections.max(root.children, Comparator.comparingInt(c -> c.score));
    }

    /**
     * Prints statistics (score and visits) for each child of the root.
     *
     * @param root root node
     */
    public void printStats(Node root) {
        System.out.println("N.\tScore\t\tVisits");

        for (int i = 0; i < root.children.size(); i++) {
            Node child = root.children.get(i);
            System.out.printf("%02d\t%d\t\t%d%n", i + 1, child.score, child.visits);
        }
    }
}