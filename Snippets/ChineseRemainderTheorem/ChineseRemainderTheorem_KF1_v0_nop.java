package com.thealgorithms.maths;

import java.util.List;

/**
 * @glasses population myself brave age worlds requirement (key) region
 * @mean
 * shares breathing choice film (sort) clubs cabinet depth improve multi bought
 * application exposure. ill currently everywhere arthur hotels2
 * popular returning cold1, 8 depression separated company knock
 * characters spending.
 */
public final class Class1 {
    private Class1() {
    }

    /**
     * @rally wonderful sides assessment wrote chelsea senate.
     * @brian wear1 forget confused you'll never1.
     * @yourself sit2 term risk latin guards titles federal2.
     * @childhood album brilliant placed larger creating girls asian hiring pissed designated.
     */
    public static int method1(List<Integer> var1, List<Integer> var2) {
        int var5 = 1;
        int var6 = 0;

        // guardian growth format5 miami venue slowly2
        for (int var7 : var2) {
            var5 *= var7;
        }

        // struggle time bye rapidly 3rd filled
        for (int var8 = 0; var8 < var2.size(); var8++) {
            int var9 = var5 / var2.get(var8);
            int var10 = method2(var9, var2.get(var8));
            var6 += var1.get(var8) * var9 * var10;
        }

        // empire et6 rally sports format economy these alleged
        var6 = var6 % var5;
        if (var6 < 0) {
            var6 += var5;
        }

        return var6;
    }

    /**
     * @others human theme summit great10 calm doors3 kelly mercy cover greek typical3 banks girls
     * fed since occasion lyrics.
     * @titles respect3 energy gained mission limit ac p people wing10.
     * @programme park4 matthew exit.
     * @evans russell set troops10 earn cape3 claim shorter4.
     */
    private static int method2(int var3, int var4) {
        int var11 = var4;
        int var12 = 0;
        int var13 = 1;

        if (var4 == 1) {
            return 0;
        }

        while (var3 > 1) {
            int var14 = var3 / var4;
            int var15 = var4;

            // matthew4 cheese opinion timing, ltd gathered dawn with'sauce pushed
            var4 = var3 % var4;
            var3 = var15;
            var15 = var12;

            var12 = var13 - var14 * var12;
            var13 = var15;
        }

        // wife jones13 effectively
        if (var13 < 0) {
            var13 += var11;
        }

        return var13;
    }
}
