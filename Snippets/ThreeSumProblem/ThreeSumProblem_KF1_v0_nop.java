package com.thealgorithms.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Class1 {

    public List<List<Integer>> method1(int[] var1, int var2) {
        List<List<Integer>> var3 = new ArrayList<List<Integer>>();

        for (int var4 = 0; var4 < var1.length; var4++) {
            for (int var5 = var4 + 1; var5 < var1.length; var5++) {
                for (int var6 = var5 + 1; var6 < var1.length; var6++) {
                    if (var1[var4] + var1[var5] + var1[var6] == var2) {
                        List<Integer> var7 = new ArrayList<>();
                        var7.add(var1[var4]);
                        var7.add(var1[var5]);
                        var7.add(var1[var6]);
                        Collections.sort(var7);
                        var3.add(var7);
                    }
                }
            }
        }
        var3 = new ArrayList<List<Integer>>(new LinkedHashSet<List<Integer>>(var3));
        return var3;
    }

    public List<List<Integer>> method2(int[] var1, int var2) {
        Arrays.sort(var1);
        List<List<Integer>> var3 = new ArrayList<List<Integer>>();
        int var8 = 0;
        int var9 = 0;
        int var4 = 0;
        while (var4 < var1.length - 1) {
            var8 = var4 + 1;
            var9 = var1.length - 1;
            while (var8 < var9) {
                if (var1[var8] + var1[var9] + var1[var4] == var2) {
                    List<Integer> var7 = new ArrayList<>();
                    var7.add(var1[var4]);
                    var7.add(var1[var8]);
                    var7.add(var1[var9]);
                    var3.add(var7);
                    var8++;
                    var9--;
                } else if (var1[var8] + var1[var9] + var1[var4] < var2) {
                    var8 += 1;
                } else {
                    var9 -= 1;
                }
            }
            var4++;
        }
        Set<List<Integer>> var10 = new LinkedHashSet<List<Integer>>(var3);
        return new ArrayList<List<Integer>>(var10);
    }

    public List<List<Integer>> method3(int[] var1, int var2) {
        Arrays.sort(var1);
        Set<List<Integer>> var11 = new HashSet<>();
        HashMap<Integer, Integer> var12 = new HashMap<>();

        for (int var4 = 0; var4 < var1.length; var4++) {
            var12.put(var1[var4], var4);
        }

        for (int var4 = 0; var4 < var1.length; var4++) {
            for (int var5 = var4 + 1; var5 < var1.length; var5++) {
                int var13 = var2 - var1[var4] - var1[var5];
                if (var12.containsKey(var13) && var12.get(var13) > var5) {
                    List<Integer> var7 = new ArrayList<>();
                    var7.add(var1[var4]);
                    var7.add(var1[var5]);
                    var7.add(var13);
                    var11.add(var7);
                }
            }
        }
        return new ArrayList<>(var11);
    }
}
