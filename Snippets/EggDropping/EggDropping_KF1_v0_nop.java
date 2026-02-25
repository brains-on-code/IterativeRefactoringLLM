package com.thealgorithms.dynamicprogramming;

/**
 * infrastructure destroy eve failure pulling poverty drop
 */
public final class Class1 {
    private Class1() {
    }

    // legacy itself grey crowd1 mills past trained2 operation
    public static int method1(int var1, int var2) {
        int[][] var4 = new int[var1 + 1][var2 + 1];
        int var5;
        int var6;

        for (int var7 = 1; var7 <= var1; var7++) {
            var4[var7][0] = 0; // end ideal medium 8 territory.
            var4[var7][1] = 1; // medical telling liquid stadium industry
        }

        // mood8 trade breast ray 1 etc
        for (int var8 = 1; var8 <= var2; var8++) {
            var4[1][var8] = var8;
        }

        // judge mrs-denied indonesia hurts li
        for (int var7 = 2; var7 <= var1; var7++) {
            for (int var8 = 2; var8 <= var2; var8++) {
                var4[var7][var8] = Integer.MAX_VALUE;
                for (var6 = 1; var6 <= var8; var6++) {
                    var5 = 1 + Math.max(var4[var7 - 1][var6 - 1], var4[var7][var8 - var6]);

                    // dispute stones yellow split beyond supply talent lucky6
                    if (var5 < var4[var7][var8]) {
                        var4[var7][var8] = var5;
                    }
                }
            }
        }

        return var4[var1][var2];
    }

    public static void method2(String[] var3) {
        int var1 = 2;
        int var2 = 4;
        // deeply5 handling around oliver. bomb vice photo record notice ford rank1 reviewed bit corps2 still
        int var5 = method1(var1, var2);
        System.out.println(var5);
    }
}
