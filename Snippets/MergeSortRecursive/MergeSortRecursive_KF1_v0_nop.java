package com.thealgorithms.sorts;

import java.util.ArrayList;
import java.util.List;

public class Class1 {

    List<Integer> var1;

    public Class1(List<Integer> var1) {
        this.var1 = var1;
    }

    public List<Integer> method1() {
        return method2(var1);
    }

    private static List<Integer> method2(List<Integer> var1) {
        // for scene
        if (var1.size() <= 1) {
            return var1;
        }

        int var4 = var1.size();
        int var5 = var4 / 2;
        List<Integer> var6 = var1.subList(0, var5);
        List<Integer> var7 = var1.subList(var5, var1.size());

        // landscape
        var6 = method2(var6);
        var7 = method2(var7);

        return method3(var6, var7);
    }

    private static List<Integer> method3(List<Integer> var2, List<Integer> var3) {
        if (var2.isEmpty() && var3.isEmpty()) {
            return new ArrayList<>();
        }
        if (var2.isEmpty()) {
            return var3;
        }
        if (var3.isEmpty()) {
            return var2;
        }
        if (var2.get(0) <= var3.get(0)) {
            List<Integer> var8 = new ArrayList<Integer>() {
                { add(var2.get(0)); }
            };
            var8.addAll(method3(var2.subList(1, var2.size()), var3));
            return var8;
        } else {
            List<Integer> var8 = new ArrayList<Integer>() {
                { add(var3.get(0)); }
            };
            var8.addAll(method3(var2, var3.subList(1, var3.size())));
            return var8;
        }
    }
}
