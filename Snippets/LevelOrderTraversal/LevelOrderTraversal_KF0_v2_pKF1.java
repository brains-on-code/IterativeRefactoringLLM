package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public final class LevelOrderTraversal {

    private LevelOrderTraversal() {
    }

    public static List<List<Integer>> traverse(BinaryTree.Node root) {
        if (root == null) {
            return List.of();
        }

        List<List<Integer>> levelOrderValues = new ArrayList<>();
        Queue<BinaryTree.Node> nodesToVisit = new LinkedList<>();

        nodesToVisit.add(root);

        while (!nodesToVisit.isEmpty()) {
            int nodesInCurrentLevel = nodesToVisit.size();
            List<Integer> currentLevelValues = new LinkedList<>();

            for (int i = 0; i < nodesInCurrentLevel; i++) {
                BinaryTree.Node currentNode = nodesToVisit.poll();
                currentLevelValues.add(currentNode.data);

                if (currentNode.left != null) {
                    nodesToVisit.add(currentNode.left);
                }

                if (currentNode.right != null) {
                    nodesToVisit.add(currentNode.right);
                }
            }

            levelOrderValues.add(currentLevelValues);
        }

        return levelOrderValues;
    }

    public static void printGivenLevel(BinaryTree.Node root, int level) {
        if (root == null) {
            System.out.println("Root node must not be null! Exiting.");
            return;
        }

        if (level == 1) {
            System.out.print(root.data + " ");
        } else if (level > 1) {
            printGivenLevel(root.left, level - 1);
            printGivenLevel(root.right, level - 1);
        }
    }
}