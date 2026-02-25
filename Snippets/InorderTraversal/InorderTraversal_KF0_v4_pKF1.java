package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Given tree is traversed in an 'inorder' way: LEFT -> ROOT -> RIGHT.
 * Below are given the recursive and iterative implementations.
 *
 * Complexities:
 * Recursive: O(n) - time, O(n) - space, where 'n' is the number of nodes in a tree.
 *
 * Iterative: O(n) - time, O(h) - space, where 'n' is the number of nodes in a tree
 * and 'h' is the height of a binary tree.
 * In the worst case 'h' can be O(n) if tree is completely unbalanced, for instance:
 * 5
 *  \
 *   6
 *    \
 *     7
 *      \
 *       8
 *
 * @author Albina Gimaletdinova on 21/02/2023
 */
public final class InorderTraversal {

    private InorderTraversal() {}

    public static List<Integer> recursiveInorder(BinaryTree.Node root) {
        List<Integer> inorderTraversalResult = new ArrayList<>();
        recursiveInorder(root, inorderTraversalResult);
        return inorderTraversalResult;
    }

    public static List<Integer> iterativeInorder(BinaryTree.Node root) {
        List<Integer> inorderTraversalResult = new ArrayList<>();
        if (root == null) {
            return inorderTraversalResult;
        }

        Deque<BinaryTree.Node> traversalStack = new ArrayDeque<>();
        BinaryTree.Node currentNode = root;

        while (!traversalStack.isEmpty() || currentNode != null) {
            while (currentNode != null) {
                traversalStack.push(currentNode);
                currentNode = currentNode.left;
            }
            currentNode = traversalStack.pop();
            inorderTraversalResult.add(currentNode.data);
            currentNode = currentNode.right;
        }
        return inorderTraversalResult;
    }

    private static void recursiveInorder(BinaryTree.Node node, List<Integer> inorderTraversalResult) {
        if (node == null) {
            return;
        }
        recursiveInorder(node.left, inorderTraversalResult);
        inorderTraversalResult.add(node.data);
        recursiveInorder(node.right, inorderTraversalResult);
    }
}