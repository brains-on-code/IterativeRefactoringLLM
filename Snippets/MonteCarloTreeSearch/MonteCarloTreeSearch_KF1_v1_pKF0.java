package com.thealgorithms.searches;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Class1 {

    public class Node {

        Node parent;
        ArrayList<Node> children;
        boolean isMaxPlayer;
        boolean isWin;
        int score;
        int visits;

        public Node() {
        }

        public Node(Node parent, boolean isMaxPlayer) {
            this.parent = parent;
            this.children = new ArrayList<>();
            this.isMaxPlayer = isMaxPlayer;
            this.isWin = false;
            this.score = 0;
            this.visits = 0;
        }
    }

    private static final int REWARD = 10;
    private static final int TIME_LIMIT_MS = 500;

    public Node runSearch(Node root) {
        expand(root, 10);

        long endTime = System.currentTimeMillis() + TIME_LIMIT_MS;

        while (System.currentTimeMillis() < endTime) {
            Node selected = select(root);

            if (selected.children.isEmpty()) {
                expand(selected, 10);
            }

            simulateAndBackpropagate(selected);
        }

        Node bestChild = getBestChild(root);
        printStats(root);
        System.out.format("%nThe optimal node is: %02d%n", root.children.indexOf(bestChild) + 1);

        return bestChild;
    }

    public void expand(Node node, int numberOfChildren) {
        for (int i = 0; i < numberOfChildren; i++) {
            node.children.add(new Node(node, !node.isMaxPlayer));
        }
    }

    public Node select(Node root) {
        Node current = root;

        while (!current.children.isEmpty()) {
            double bestValue = Double.MIN_VALUE;
            int bestIndex = 0;

            for (int i = 0; i < current.children.size(); i++) {
                Node child = current.children.get(i);

                if (child.visits == 0) {
                    bestIndex = i;
                    break;
                }

                double uctValue =
                        ((double) child.score / child.visits)
                                + 1.41 * Math.sqrt(Math.log(current.visits) / (double) child.visits);

                if (uctValue > bestValue) {
                    bestValue = uctValue;
                    bestIndex = i;
                }
            }

            current = current.children.get(bestIndex);
        }

        return current;
    }

    public void simulateAndBackpropagate(Node node) {
        Random random = new Random();
        Node current = node;

        node.isWin = (random.nextInt(6) == 0);
        boolean result = node.isWin;

        while (current != null) {
            current.visits++;

            if ((current.isMaxPlayer && result) || (!current.isMaxPlayer && !result)) {
                current.score += REWARD;
            }

            current = current.parent;
        }
    }

    public Node getBestChild(Node root) {
        return Collections.max(root.children, Comparator.comparing(c -> c.score));
    }

    public void printStats(Node root) {
        System.out.println("N.\tScore\t\tVisits");

        for (int i = 0; i < root.children.size(); i++) {
            Node child = root.children.get(i);
            System.out.printf("%02d\t%d\t\t%d%n", i + 1, child.score, child.visits);
        }
    }
}