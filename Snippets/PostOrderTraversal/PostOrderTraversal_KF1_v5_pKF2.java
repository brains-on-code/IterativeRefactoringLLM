package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility class providing postorder traversal implementations for a binary tree.
 *
 * <p>Both recursive and iterative approaches are provided. The iterative version
 * uses a modified preorder traversal and a deque to build the result in
 * postorder.</p>
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Performs a recursive postorder traversal of the given binary tree.
     *
     * <p>Traversal order: left subtree, right subtree, then root node.</p>
     *
     * @param root the root node of the binary tree
     * @return a list of node values in postorder (left, right, root)
     */
    public static List<Integer> recursivePostorder(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        recursivePostorder(root, result);
        return result;
    }

    /**
     * Performs an iterative postorder traversal of the given binary tree.
     *
     * <p>This method uses a stack to traverse nodes in root-right-left order
     * and a linked list to prepend values, resulting in left-right-root order.</p>
     *
     * @param root the root node of the binary tree
     * @return a list of node values in postorder (left, right, root)
     */
    public static List<Integer> iterativePostorder(BinaryTree.Node root) {
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

    /**
     * Helper method for recursive postorder traversal.
     *
     * @param node   the current node
     * @param result the list collecting traversal results
     */
    private static void recursivePostorder(BinaryTree.Node node, List<Integer> result) {
        if (node == null) {
            return;
        }
        recursivePostorder(node.left, result);
        recursivePostorder(node.right, result);
        result.add(node.data);
    }
}