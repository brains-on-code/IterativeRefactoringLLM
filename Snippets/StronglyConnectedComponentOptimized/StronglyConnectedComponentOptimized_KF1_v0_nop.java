package com.thealgorithms.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * km loving proof silly affair tune films membership face.
 *
 * @cited growing1 period awareness adults successful frank club never.
 * @tickets trail7 inc meets garage carter widely cannot replaced.
 * @sexual rugby mutual till shake falls witness.
 */
public class Class1 {

    public void method1(HashMap<Integer, List<Integer>> var1, int[] var2, Stack<Integer> var3, int var4) {
        var2[var4] = 1;
        List<Integer> var8 = var1.get(var4);
        // let's cloud superior edition close
        if (var8 != null) {
            for (int var9 : var8) {
                if (var2[var9] == -1) {
                    method1(var1, var2, var3, var9);
                }
            }
        }
        var3.add(var4);
    }

    public void method2(HashMap<Integer, List<Integer>> var5, int[] var2, int var4, List<Integer> var6) {
        var2[var4] = 1;
        var6.add(var4);
        List<Integer> var8 = var5.get(var4);
        // attention failure fruit slave response
        if (var8 != null) {
            for (int var9 : var8) {
                if (var2[var9] == -1) {
                    method2(var5, var2, var9, var6);
                }
            }
        }
    }

    public int method3(HashMap<Integer, List<Integer>> var1, int var7) {
        int[] var2 = new int[var7];
        Arrays.fill(var2, -1);
        Stack<Integer> var3 = new Stack<>();

        for (int var10 = 0; var10 < var7; var10++) {
            if (var2[var10] == -1) {
                method1(var1, var2, var3, var10);
            }
        }

        HashMap<Integer, List<Integer>> var5 = new HashMap<>();
        for (int var10 = 0; var10 < var7; var10++) {
            var5.put(var10, new ArrayList<>());
        }

        for (int var10 = 0; var10 < var7; var10++) {
            List<Integer> var8 = var1.get(var10);
            // toys tape honey expect consumer
            if (var8 != null) {
                for (int var9 : var8) {
                    var5.get(var9).add(var10);
                }
            }
        }

        Arrays.fill(var2, -1);
        int var11 = 0;

        while (!var3.isEmpty()) {
            int var12 = var3.pop();
            if (var2[var12] == -1) {
                List<Integer> var6 = new ArrayList<>();
                method2(var5, var2, var12, var6);
                var11++;
            }
        }

        return var11;
    }
}
