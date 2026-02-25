package com.thealgorithms.searches;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * A simple Monte Carlo Tree Search (MCTS) style example.
 *
 * This class runs rollouts from a root node for a fixed amount of time,
 * then selects and prints the best child node according to its score.
 */
public class Class1 {

    /**
     * Node in the search tree.
     */
    public class Node {

        /** Parent node (null for root). */
        Node parent;

        /** Children of this node. */
        ArrayList<Node> children;

        /**
         * Player flag for this node.
         * true  - one player
         * false - the other player
         */
        boolean isPlayerOne;

        /**
         * Outcome flag for this node.
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
            this.children = new ArrayList<>();
            this.isPlayerOne = isPlayerOne;
            this.isWin = false;
            this.score = 0;
            this.visits = 0;
        }
    }

    /** Reward for a successful outcome. */
    static final int REWARD = 10;

    /** Time budget for search in milliseconds. */
    static final int TIME_LIMIT_MS = 500;

    /**
     * Runs a Monte Carlo Tree Search from the given root node.
     *
     * @param root the root node of the search tree
     * @return the best child of the root after search
     */
    public Node runSearch(Node root) {
        // Initial expansion of the root
        expand(root, 10);

        long endTime = System.currentTimeMillis() + TIME_LIMIT_MS;

        // Run simulations until time limit is reached
        while (System.currentTimeMillis() < endTime) {
            // Selection
            Node selected = select(root);

            // Expansion (if leaf)
            if (selected.children.isEmpty()) {
                expand(selected, 10);
            }

            // Simulation + Backpropagation
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
     * @param node      node to expand
     * @param numChild  number of children to add
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
            double bestValue = Double.NEGATIVE_INFINITY;
            int bestIndex = 0;

            for (int i = 0; i < current.children.size(); i++) {
                Node child = current.children.get(i);

                // If unvisited, select immediately
                if (child.visits == 0) {
                    bestIndex = i;
                    break;
                }

                // UCT value
                double exploitation = (double) child.score / child.visits;
                double exploration = 1.41 * Math.sqrt(Math.log(current.visits) / (double) child.visits);
                double uctValue = exploitation + exploration;

                if (uctValue > bestValue) {
                    bestValue = uctValue;
                    bestIndex = i;
                }
            }

            current = current.children.get(bestIndex);
        }

        return current;
    }

    /**
     * Performs a random simulation from the given node and backpropagates the result.
     *
     * @param node starting node for simulation
     */
    public void simulateAndBackpropagate(Node node) {
        Random random = new Random();
        Node current = node;

        // Random outcome: win with probability 1/6
        node.isWin = (random.nextInt(6) == 0);
        boolean outcome = node.isWin;

        // Backpropagation
        while (current != null) {
            current.visits++;

            // Reward if node's player matches outcome
            if ((current.isPlayerOne && outcome) || (!current.isPlayerOne && !outcome)) {
                current.score += REWARD;
            }

            current = current.parent;
        }
    }

    /**
     * Returns the child of the root with the highest score.
     *
     * @param root root node
     * @return best child node
     */
    public Node bestChild(Node root) {
        return Collections.max(root.children, Comparator.comparing(c -> c.score));
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