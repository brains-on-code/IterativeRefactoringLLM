package com.thealgorithms.searches;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Class1 {

    private static final int REWARD = 10;
    private static final int TIME_LIMIT_MS = 500;
    private static final double EXPLORATION_CONSTANT = 1.41;
    private static final int INITIAL_CHILDREN = 10;
    private static final Random RANDOM = new Random();

    public static class Node {

        private final List<Node> children;
        private final boolean maxPlayer;

        private Node parent;
        private boolean win;
        private int score;
        private int visits;

        public Node(Node parent, boolean maxPlayer) {
            this.parent = parent;
            this.maxPlayer = maxPlayer;
            this.children = new ArrayList<>();
        }

        public Node getParent() {
            return parent;
        }

        public List<Node> getChildren() {
            return children;
        }

        public boolean isMaxPlayer() {
            return maxPlayer;
        }

        public boolean isWin() {
            return win;
        }

        public void setWin(boolean win) {
            this.win = win;
        }

        public int getScore() {
            return score;
        }

        public int getVisits() {
            return visits;
        }

        public void incrementVisits() {
            visits++;
        }

        public void addScore(int value) {
            score += value;
        }
    }

    public Node runSearch(Node root) {
        expand(root, INITIAL_CHILDREN);

        long endTime = System.currentTimeMillis() + TIME_LIMIT_MS;

        while (System.currentTimeMillis() < endTime) {
            Node selected = select(root);

            if (selected.getChildren().isEmpty()) {
                expand(selected, INITIAL_CHILDREN);
            }

            simulateAndBackpropagate(selected);
        }

        Node bestChild = getBestChild(root);
        printStats(root);
        System.out.format("%nThe optimal node is: %02d%n", root.getChildren().indexOf(bestChild) + 1);

        return bestChild;
    }

    public void expand(Node node, int numberOfChildren) {
        for (int i = 0; i < numberOfChildren; i++) {
            node.getChildren().add(new Node(node, !node.isMaxPlayer()));
        }
    }

    public Node select(Node root) {
        Node current = root;

        while (!current.getChildren().isEmpty()) {
            current = selectBestChildByUct(current);
        }

        return current;
    }

    private Node selectBestChildByUct(Node node) {
        double bestValue = Double.NEGATIVE_INFINITY;
        Node bestChild = null;

        for (Node child : node.getChildren()) {
            if (child.getVisits() == 0) {
                return child;
            }

            double uctValue = calculateUctValue(node, child);

            if (uctValue > bestValue) {
                bestValue = uctValue;
                bestChild = child;
            }
        }

        return bestChild;
    }

    private double calculateUctValue(Node parent, Node child) {
        double averageReward = (double) child.getScore() / child.getVisits();
        double explorationTerm =
                EXPLORATION_CONSTANT * Math.sqrt(Math.log(parent.getVisits()) / (double) child.getVisits());
        return averageReward + explorationTerm;
    }

    public void simulateAndBackpropagate(Node node) {
        boolean result = RANDOM.nextInt(6) == 0;
        node.setWin(result);

        Node current = node;
        while (current != null) {
            current.incrementVisits();

            boolean isWinningForCurrent =
                    (current.isMaxPlayer() && result) || (!current.isMaxPlayer() && !result);

            if (isWinningForCurrent) {
                current.addScore(REWARD);
            }

            current = current.getParent();
        }
    }

    public Node getBestChild(Node root) {
        return Collections.max(root.getChildren(), Comparator.comparing(Node::getScore));
    }

    public void printStats(Node root) {
        System.out.println("N.\tScore\t\tVisits");

        for (int i = 0; i < root.getChildren().size(); i++) {
            Node child = root.getChildren().get(i);
            System.out.printf("%02d\t%d\t\t%d%n", i + 1, child.getScore(), child.getVisits());
        }
    }
}