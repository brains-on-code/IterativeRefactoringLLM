package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public final class PostOrderTraversal {

    private PostOrderTraversal() {}

    /**
     * Returns the post-order traversal of a binary tree using recursion.
     *
     * Post-order: left subtree, right subtree, then root.
     *
     * @param root the root node of the binary tree
     * @return a list of node values in post-order
     */
    public static List<Integer> recursivePostOrder(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        recursivePostOrder(root, result);
        return result;
    }

    /**
     * Returns the post-order traversal of a binary tree using an iterative approach.
     *
     * Approach:
     * <ul>
     *   <li>Traverse in modified pre-order: root, right, left.</li>
     *   <li>Insert each visited node at the front of the result list.</li>
     *   <li>The resulting list is in post-order: left, right, root.</li>
     * </ul>
     *
     * @param root the root node of the binary tree
     * @return a list of node values in post-order
     */
    public static List<Integer> iterativePostOrder(BinaryTree.Node root) {
        LinkedList<Integer> result = new LinkedList<>();
        if (root == null) {
            return result;
        }

        Deque<BinaryTree.Node> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            BinaryTree.Node current = stack.pop();
            result.addFirst(current.data);

            if (current.left != null) {
                stack.push(current.left);
            }
            if (current.right != null) {
                stack.push(current.right);
            }
        }

        return result;
    }

    private static void recursivePostOrder(BinaryTree.Node node, List<Integer> result) {
        if (node == null) {
            return;
        }

        recursivePostOrder(node.left, result);
        recursivePostOrder(node.right, result);
        result.add(node.data);
    }
}