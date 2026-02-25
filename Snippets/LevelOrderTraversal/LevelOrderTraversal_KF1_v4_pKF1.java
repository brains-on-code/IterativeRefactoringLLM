package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public final class LevelOrderTraversal {

    private LevelOrderTraversal() {
    }

    public static List<List<Integer>> getLevelOrderValues(BinaryTree.Node root) {
        if (root == null) {
            return List.of();
        }

        List<List<Integer>> levelOrderValues = new ArrayList<>();
        Queue<BinaryTree.Node> nodeQueue = new LinkedList<>();
        nodeQueue.add(root);

        while (!nodeQueue.isEmpty()) {
            int nodesInCurrentLevel = nodeQueue.size();
            List<Integer> currentLevelValues = new LinkedList<>();

            for (int i = 0; i < nodesInCurrentLevel; i++) {
                BinaryTree.Node currentNode = nodeQueue.poll();
                currentLevelValues.add(currentNode.data);

                if (currentNode.left != null) {
                    nodeQueue.add(currentNode.left);
                }

                if (currentNode.right != null) {
                    nodeQueue.add(currentNode.right);
                }
            }

            levelOrderValues.add(currentLevelValues);
        }

        return levelOrderValues;
    }

    public static void printNodesAtLevel(BinaryTree.Node root, int targetLevel) {
        if (root == null) {
            System.out.println("Root node must not be null! Exiting.");
            return;
        }

        if (targetLevel == 1) {
            System.out.print(root.data + " ");
        } else if (targetLevel > 1) {
            printNodesAtLevel(root.left, targetLevel - 1);
            printNodesAtLevel(root.right, targetLevel - 1);
        }
    }
}