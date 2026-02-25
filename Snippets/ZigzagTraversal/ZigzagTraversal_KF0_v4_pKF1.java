package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public final class ZigzagTraversal {

    private ZigzagTraversal() {
    }

    public static List<List<Integer>> traverse(BinaryTree.Node root) {
        if (root == null) {
            return List.of();
        }

        List<List<Integer>> zigzagOrder = new ArrayList<>();
        Deque<BinaryTree.Node> queue = new ArrayDeque<>();
        queue.offer(root);

        boolean traverseLeftToRight = false;

        while (!queue.isEmpty()) {
            int levelNodeCount = queue.size();
            List<Integer> levelValues = new LinkedList<>();

            for (int i = 0; i < levelNodeCount; i++) {
                BinaryTree.Node node = queue.poll();

                if (traverseLeftToRight) {
                    levelValues.add(0, node.data);
                } else {
                    levelValues.add(node.data);
                }

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            traverseLeftToRight = !traverseLeftToRight;
            zigzagOrder.add(levelValues);
        }

        return zigzagOrder;
    }
}