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
    public static List<Integer> method3(BinaryTree.Node var1) {
        List<Integer> var2 = new ArrayList<>();
        method3(var1, var2);
        return var2;
    }

    public static List<Integer> method2(BinaryTree.Node var1) {
        LinkedList<Integer> var2 = new LinkedList<>();
        if (var1 == null) {
            return var2;
        }

        Deque<BinaryTree.Node> var3 = new ArrayDeque<>();
        var3.push(var1);
        while (!var3.isEmpty()) {
            BinaryTree.Node var4 = var3.pop();
            var2.addFirst(var4.data);
            if (var4.left != null) {
                var3.push(var4.left);
            }
            if (var4.right != null) {
                var3.push(var4.right);
            }
        }

        return var2;
    }

    private static void method3(BinaryTree.Node var1, List<Integer> var2) {
        if (var1 == null) {
            return;
        }
        method3(var1.left, var2);
        method3(var1.right, var2);
        var2.add(var1.data);
    }
}
