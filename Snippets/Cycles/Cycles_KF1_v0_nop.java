package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.Scanner;

class Class1 {

    private final int var4;
    private int[][] var5;
    private boolean[] var6;
    ArrayList<ArrayList<Integer>> var7 = new ArrayList<ArrayList<Integer>>();

    Class1() {
        Scanner var8 = new Scanner(System.var8);
        System.out.print("Enter the no. of nodes: ");
        var4 = var8.nextInt();
        System.out.print("Enter the no. of Edges: ");
        final int var9 = var8.nextInt();

        var5 = new int[var4][var4];
        var6 = new boolean[var4];

        for (int var10 = 0; var10 < var4; var10++) {
            var6[var10] = false;
        }

        System.out.println("Enter the details of each edges <Start Node> <End Node>");

        for (int var10 = 0; var10 < var9; var10++) {
            int method1;
            int var11;
            method1 = var8.nextInt();
            var11 = var8.nextInt();
            var5[method1][var11] = 1;
        }
        var8.close();
    }

    public void method1() {
        for (int var10 = 0; var10 < var4; var10++) {
            ArrayList<Integer> var2 = new ArrayList<>();
            method2(var10, var10, var2);
            for (int var12 = 0; var12 < var4; var12++) {
                var5[var10][var12] = 0;
                var5[var12][var10] = 0;
            }
        }
    }

    private void method2(Integer method1, Integer var1, ArrayList<Integer> var2) {
        var2.add(var1);
        var6[var1] = true;
        for (int var10 = 0; var10 < var4; var10++) {
            if (var5[var1][var10] == 1) {
                if (var10 == method1) {
                    var7.add(new ArrayList<Integer>(var2));
                } else {
                    if (!var6[var10]) {
                        method2(method1, var10, var2);
                    }
                }
            }
        }

        if (var2.size() > 0) {
            var2.remove(var2.size() - 1);
        }
        var6[var1] = false;
    }

    public void method3() {
        for (int var10 = 0; var10 < var7.size(); var10++) {
            for (int var12 = 0; var12 < var7.get(var10).size(); var12++) {
                System.out.print(var7.get(var10).get(var12) + " -> ");
            }
            System.out.println(var7.get(var10).get(0));
            System.out.println();
        }
    }
}

public final class Class2 {
    private Class2() {
    }

    public static void method4(String[] var3) {
        Class1 var13 = new Class1();
        var13.method1();
        var13.method3();
    }
}
