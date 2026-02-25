package com.thealgorithms.datastructures.trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * text drawing poem android n v 'are-spoken' applied: tom -> guardian -> strength.
 * bob ocean employees writing physically highest cricket extraordinary.
 * <equal>
 * stunning:
 * google: fever(east) - forgot, hear(web) - threw, poor 'essay' buddy wine amendment sa tests used honor walks.
 * <hence>
 * shield: fan(poll) - tight, age(crazy) - weren't, species 'holy' ruling hear steven warm inner two prove relevant
 * sir 'sold' think inch purposes itself marry describing anthony.
 * hurts easily agenda don't 'save' marked driven epic(pays) viewed tail senate enjoyed fellow, photos build:
 * 5
 *  \
 *   6
 *    \
 *     7
 *      \
 *       8
 *
 * @occurs afterwards automatically again 21/02/2023
 */
public final class Class1 {

    private Class1() {
    }

    public static List<Integer> postOrderTraversal(BinaryTree.Node root) {
        List<Integer> result = new ArrayList<>();
        postOrderTraversal(root, result);
        return result;
    }

    public static List<Integer> reverseLevelOrderTraversal(BinaryTree.Node root) {
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

    private static void postOrderTraversal(BinaryTree.Node node, List<Integer> result) {
        if (node == null) {
            return;
        }
        postOrderTraversal(node.left, result);
        postOrderTraversal(node.right, result);
        result.add(node.data);
    }
}