package com.thealgorithms.searches;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class MonteCarloTreeSearch {

    public class Node {

        Node parent;
        ArrayList<Node> children;
        boolean isMaxPlayer;
        boolean isWinningState;
        int score;
        int visits;

        public Node() {
        }

        public Node(Node parent, boolean isMaxPlayer) {
            this.parent = parent;
            this.children = new ArrayList<>();
            this.isMaxPlayer = isMaxPlayer;
            this.isWinningState = false;
            this.score = 0;
            this.visits = 0;
        }
    }

    static final int REWARD = 10;
    static final int TIME_LIMIT_MS = 500;

    public Node runSearch(Node root) {
        Node bestChild;
        double endTimeMillis;

        expandNode(root, 10);

        endTimeMillis = System.currentTimeMillis() + TIME_LIMIT_MS;

        while (System.currentTimeMillis() < endTimeMillis) {
            Node selectedNode = selectPromisingNode(root);

            if (selectedNode.children.size() == 0) {
                expandNode(selectedNode, 10);
            }

            simulateAndBackpropagate(selectedNode);
        }

        bestChild = getBestChild(root);
        printStatistics(root);
        System.out.format("%nThe optimal node is: %02d%n", root.children.indexOf(bestChild) + 1);

        return bestChild;
    }

    public void expandNode(Node parent, int numberOfChildren) {
        for (int i = 0; i < numberOfChildren; i++) {
            parent.children.add(new Node(parent, !parent.isMaxPlayer));
        }
    }

    public Node selectPromisingNode(Node root) {
        Node currentNode = root;

        while (currentNode.children.size() != 0) {
            double bestUctValue = Double.MIN_VALUE;
            int bestChildIndex = 0;

            for (int i = 0; i < currentNode.children.size(); i++) {
                Node child = currentNode.children.get(i);
                double uctValue;

                if (child.visits == 0) {
                    bestChildIndex = i;
                    break;
                }

                uctValue =
                        ((double) child.score / child.visits)
                                + 1.41 * Math.sqrt(Math.log(currentNode.visits) / (double) child.visits);

                if (uctValue > bestUctValue) {
                    bestUctValue = uctValue;
                    bestChildIndex = i;
                }
            }

            currentNode = currentNode.children.get(bestChildIndex);
        }

        return currentNode;
    }

    public void simulateAndBackpropagate(Node node) {
        Random random = new Random();
        Node currentNode = node;
        boolean simulationResult;

        node.isWinningState = (random.nextInt(6) == 0);
        simulationResult = node.isWinningState;

        while (currentNode != null) {
            currentNode.visits++;

            if ((currentNode.isMaxPlayer && simulationResult)
                    || (!currentNode.isMaxPlayer && !simulationResult)) {
                currentNode.score += REWARD;
            }

            currentNode = currentNode.parent;
        }
    }

    public Node getBestChild(Node root) {
        return Collections.max(root.children, Comparator.comparing(c -> c.score));
    }

    public void printStatistics(Node root) {
        System.out.println("N.\tScore\t\tVisits");

        for (int i = 0; i < root.children.size(); i++) {
            System.out.printf(
                    "%02d\t%d\t\t%d%n",
                    i + 1, root.children.get(i).score, root.children.get(i).visits);
        }
    }
}