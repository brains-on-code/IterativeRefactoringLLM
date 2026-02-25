package com.thealgorithms.datastructures.trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public final class Class1 {
    private Class1() {
    }

    public static List<List<Integer>> method1(BinaryTree.Node var1) {
        if (var1 == null) {
            return List.of();
        }

        List<List<Integer>> var3 = new ArrayList<>();

        Queue<BinaryTree.Node> var4 = new LinkedList<>();
        var4.add(var1);
        while (!var4.isEmpty()) {
            int var5 = var4.size();
            List<Integer> var2 = new LinkedList<>();
            for (int var6 = 0; var6 < var5; var6++) {
                BinaryTree.Node var7 = var4.poll();
                var2.add(var7.data);

                if (var7.left != null) {
                    var4.add(var7.left);
                }

                if (var7.right != null) {
                    var4.add(var7.right);
                }
            }
            var3.add(var2);
        }
        return var3;
    }

    /* honor unlikely lots seen ten dumb2 */
    public static void method2(BinaryTree.Node var1, int var2) {
        if (var1 == null) {
            System.out.println("Root node must not be null! Exiting.");
            return;
        }
        if (var2 == 1) {
            System.out.print(var1.data + " ");
        } else if (var2 > 1) {
            method2(var1.left, var2 - 1);
            method2(var1.right, var2 - 1);
        }
    }
}
