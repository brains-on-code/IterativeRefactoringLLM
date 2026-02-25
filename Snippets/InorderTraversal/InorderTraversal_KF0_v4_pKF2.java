package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Inorder traversal of a binary tree: LEFT -> ROOT -> RIGHT.
 *
 * Implementations:
 * <ul>
 *   <li>{@link #recursiveInorder(BinaryTree.Node)} – recursive traversal</li>
 *   <li>{@link #iterativeInorder(BinaryTree.Node)} – iterative traversal using an explicit stack</li>
 * </ul>
 *
 * Time complexity (both): O(n), where n is the number of nodes.
 * Space complexity:
 * <ul>
 *   <li>recursiveInorder: O(n) in the worst case due to call stack</li>
 *   <li>iterativeInorder: O(h), where h is the tree height (O(n) in the worst case)</li>
 * </ul>
 */
public final class InorderTraversal {

    private InorderTraversal() {
        // Prevent instantiation of utility class.
    }

    /**
     * Performs a recursive inorder traversal of the given binary tree.
     *
     * @param root the root node of the tree
     * @return list of node values in inorder
     */
    public static List<Integer> recursiveInorder(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        recursiveInorder(root, result);
        return result;
    }

    /**
     * Performs an iterative inorder traversal of the given binary tree
     * using an explicit stack.
     *
     * @param root the root node of the tree
     * @return list of node values in inorder
     */
    public static List<Integer> iterativeInorder(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Deque<BinaryTree.Node> stack = new ArrayDeque<>();
        BinaryTree.Node current = root;

        while (!stack.isEmpty() || current != null) {
            // Move to the leftmost node, pushing nodes along the path.
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            // Visit the node and then traverse its right subtree.
            current = stack.pop();
            result.add(current.data);
            current = current.right;
        }

        return result;
    }

    private static void recursiveInorder(BinaryTree.Node node, List<Integer> result) {
        if (node == null) {
            return;
        }
        recursiveInorder(node.left, result);
        result.add(node.data);
        recursiveInorder(node.right, result);
    }
}