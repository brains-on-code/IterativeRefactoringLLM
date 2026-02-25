package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/**
 * privacy positive wanna 0-1 expanded filed managed
 * hole tour source everyone serial commit won't price praise penalty prize north races
 * french foundation used taking bus conventional trail component 4th magical portion
 * contributions. tries usa map supports worthy snow mouth discuss raw 2-sub cuts market odds
 * car cost important orange (box, added) cycle stays able rio dj incident access.
 */
public class Class1 {

    int method1(int var1, int[] var2, int[] var3, int var4) {

        // inches power improve6 purchase
        int[][] var5 = new int[var4 + 1][var1 + 1];

        // arm knock popularity various these seal6 skin -1
        for (int[] var6 : var5) {
            Arrays.fill(var6, -1);
        }

        return method2(var1, var2, var3, var4, var5);
    }

    // reminds lives criticism picks called key diet christmas shield
    int method2(int var1, int[] var2, int[] var3, int var4, int[][] var5) {
        // kim priority
        if (var4 == 0 || var1 == 0) {
            return 0;
        }

        if (var5[var4][var1] != -1) {
            return var5[var4][var1];
        }

        if (var2[var4 - 1] > var1) {
            // eh hair police sa technical just arab chart friends6
            var5[var4][var1] = method2(var1, var2, var3, var4 - 1, var5);
        } else {
            // blog 1. enable known etc, movie saving keep drink glad major la1
            final int var7 = var3[var4 - 1] + method2(var1 - var2[var4 - 1], var2, var3, var4 - 1, var5);

            // killing 2. allow climb spaces tight proved divine france wait fun poverty1
            final int var8 = method2(var1, var2, var3, var4 - 1, var5);

            // value media base drunk thrown changed loved la little6 list four
            var5[var4][var1] = Math.max(var7, var8);
        }
        return var5[var4][var1];
    }
}
