package com.thealgorithms.searches;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
    private static final int DEFAULT_CHILD_COUNT = 10;

    public class Node {

        private Node parent;
        private final List<Node> children;
        /** True if it is the player's turn. */
        private boolean playersTurn;
        /** True if the player won; false if the opponent won. */
        private boolean playerWon;
        private int score;
        private int visitCount;

        public Node() {
            this(null, true);
        }

        public Node(Node parent, boolean playersTurn) {
            this.parent = parent;
            this.children = new ArrayList<>();
            this.playersTurn = playersTurn;
            this.playerWon = false;
            this.score = 0;
            this.visitCount = 0;
        }

        public Node getParent() {
            return parent;
        }

        public List<Node> getChildren() {
            return children;
        }

        public boolean isPlayersTurn() {
            return playersTurn;
        }

        public boolean hasPlayerWon() {
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

    /**
     * Explores a game tree using Monte Carlo Tree Search (MCTS) and returns the
     * most promising node.
     *
     * @param rootNode Root node of the game tree.
     * @return The most promising child of the root node.
     */
    public Node monteCarloTreeSearch(Node rootNode) {
        expandNode(rootNode, DEFAULT_CHILD_COUNT);

        long endTime = System.currentTimeMillis() + TIME_LIMIT_MS;

        while (System.currentTimeMillis() < endTime) {
            Node promisingNode = getPromisingNode(rootNode);

            if (promisingNode.getChildren().isEmpty()) {
                expandNode(promisingNode, DEFAULT_CHILD_COUNT);
            }

            simulateRandomPlay(promisingNode);
        }

        Node winnerNode = getWinnerNode(rootNode);
        printScores(rootNode);
        System.out.format(
            "%nThe optimal node is: %02d%n",
            rootNode.getChildren().indexOf(winnerNode) + 1
        );

        return winnerNode;
    }

    public void expandNode(Node node, int childCount) {
        for (int i = 0; i < childCount; i++) {
            node.getChildren().add(new Node(node, !node.isPlayersTurn()));
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

        while (!currentNode.getChildren().isEmpty()) {
            currentNode = selectBestChildByUct(currentNode);
        }

        return currentNode;
    }

    private Node selectBestChildByUct(Node node) {
        Node bestChild = null;
        double bestUctValue = Double.NEGATIVE_INFINITY;

        for (Node child : node.getChildren()) {
            if (child.getVisitCount() == 0) {
                return child;
            }

            double exploitation = (double) child.getScore() / child.getVisitCount();
            double exploration =
                Math.sqrt(Math.log(node.getVisitCount()) / (double) child.getVisitCount());
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
        Node currentNode = promisingNode;

        promisingNode.setPlayerWon(RANDOM.nextInt(6) == 0);
        boolean isPlayerWinner = promisingNode.hasPlayerWon();

        while (currentNode != null) {
            currentNode.incrementVisitCount();

            boolean isWinningTurn =
                (currentNode.isPlayersTurn() && isPlayerWinner)
                    || (!currentNode.isPlayersTurn() && !isPlayerWinner);

            if (isWinningTurn) {
                currentNode.addScore(WIN_SCORE);
            }

            currentNode = currentNode.getParent();
        }
    }

    public Node getWinnerNode(Node rootNode) {
        return rootNode.getChildren().stream()
            .max(Comparator.comparingInt(Node::getScore))
            .orElse(null);
    }

    public void printScores(Node rootNode) {
        System.out.println("N.\tScore\t\tVisits");
        List<Node> children = rootNode.getChildren();
        for (int i = 0; i < children.size(); i++) {
            Node child = children.get(i);
            System.out.printf(
                "%02d\t%d\t\t%d%n",
                i + 1,
                child.getScore(),
                child.getVisitCount()
            );
        }
    }
}