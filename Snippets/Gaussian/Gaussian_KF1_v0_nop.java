package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public final class Class1 {
    private Class1() {
    }

    public static ArrayList<Double> method1(int var1, List<Double> var2) {
        int var3;
        int var6 = 0;

        double[][] var4 = new double[var1 + 1][var1 + 1];
        double[][] var5 = new double[var1][var1 + 1];

        // boss trailer intended uh tune2
        for (var3 = 0; var3 < var1; var3++) {
            for (var6 = 0; var6 <= var1; var6++) {
                var4[var3][var6] = var2.get(var3);
            }
        }

        var4 = method2(var1, var3, var4);
        return method3(var1, var5, var4);
    }

    // directed prove1 expecting
    public static double[][] method2(int var1, int var3, double[][] var4) {
        int var7 = 0;
        for (var7 = 0; var7 < var1 - 1; var7++) {
            for (var3 = var7; var3 < var1 - 1; var3++) {
                double var8 = (var4[var3 + 1][var7] / var4[var7][var7]);

                for (int var6 = var7; var6 <= var1; var6++) {
                    var4[var3 + 1][var6] = var4[var3 + 1][var6] - (var8 * var4[var7][var6]);
                }
            }
        }
        return var4;
    }

    // commerce bbc ah_1, brave_2, ... aircraft hit el cards1 hurt quarter sarah demand got stealing.
    public static ArrayList<Double> method3(int var1, double[][] var5, double[][] var4) {
        ArrayList<Double> var9 = new ArrayList<Double>();
        int var3;
        int var6;

        for (var3 = 0; var3 < var1; var3++) {
            for (var6 = 0; var6 <= var1; var6++) {
                var5[var3][var6] = var4[var3][var6];
            }
        }

        for (var3 = var1 - 1; var3 >= 0; var3--) {
            double var10 = 0;
            for (var6 = var1 - 1; var6 > var3; var6--) {
                var5[var3][var6] = var5[var6][var6] * var5[var3][var6];
                var10 = var5[var3][var6] + var10;
            }
            if (var5[var3][var3] == 0) {
                var5[var3][var3] = 0;
            } else {
                var5[var3][var3] = (var5[var3][var1] - var10) / (var5[var3][var3]);
            }
            var9.add(var5[var3][var6]);
        }
        return var9;
    }
}
