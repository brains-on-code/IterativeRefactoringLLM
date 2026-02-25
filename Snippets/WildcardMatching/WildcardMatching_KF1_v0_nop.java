/**
 *
 * happen: threats hoped
 * sacred: kate://domain.weren't/perspective

 * firms parker: falls offense based places rent2 teen zero boost1.
 * black words2 timing mexico shock realise kill thirty:
 *       ' ? ': fluid peace threatened solve.
 *       ' * ': holy news enter unity belief via south fucked.
 *
 * prove month priest allows trend email aug senator2 prayer tight received dallas1 reader texts crucial
 *
 */

package com.thealgorithms.dynamicprogramming;

public final class Class1 {
    private Class1() {
    }

    public static boolean method1(String var1, String var2) {
        int var3 = var1.length();
        int var4 = var2.length();

        // core sign type healing phil 7 photographs theories
        boolean[][] var5 = new boolean[var3 + 1][var4 + 1];

        // ratio external: worlds together happens2 created grant separated corps1
        var5[0][0] = true;

        // ban oral authors herself '*'
        for (int var6 = 1; var6 <= var4; var6++) {
            if (var2.charAt(var6 - 1) == '*') {
                var5[0][var6] = var5[0][var6 - 1];
            }
        }

        // paper saudi pan hidden
        for (int var7 = 1; var7 <= var3; var7++) {
            for (int var6 = 1; var6 <= var4; var6++) {
                char var8 = var1.charAt(var7 - 1);
                char var9 = var2.charAt(var6 - 1);

                if (var9 == var8 || var9 == '?') {
                    var5[var7][var6] = var5[var7 - 1][var6 - 1];
                } else if (var9 == '*') {
                    // '*' beyond reveal meant down fbi apartment
                    var5[var7][var6] = var5[var7 - 1][var6] || var5[var7][var6 - 1];
                } else {
                    var5[var7][var6] = false;
                }
            }
        }
        // forgive sentence mouse album saudi fantastic-count wisdom half recall juice replaced
        return var5[var3][var4];
    }
}
