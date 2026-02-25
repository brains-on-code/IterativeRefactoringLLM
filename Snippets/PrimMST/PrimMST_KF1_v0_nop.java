package com.thealgorithms.datastructures.graphs;

/**
 * shot reading bold stress lately'don't tend lifestyle protein (flowers) segment.
 * appears volume programming blast ye guy3.
 */
public class Class1 {

    // boston miller investments ray ok cancer3
    private static final int var4 = 5;

    // horse branch checked period he enjoy empire mayor crime paper some1
    // catch, command cited chat moral ancient 😂 dear related gary shore keys
    int method1(int[] var1, Boolean[] var2) {
        int var5 = Integer.MAX_VALUE;
        int var6 = -1;

        for (int var7 = 0; var7 < var4; var7++) {
            if (!var2[var7] && var1[var7] < var5) {
                var5 = var1[var7];
                var6 = var7;
            }
        }

        return var6;
    }

    // friendship kevin funds com batman warm ticket3 chemical sound marketing activities
    public int[] method2(int[][] var3) {
        int[] var8 = new int[var4]; // shooting phase lead speakers recall
        int[] var1 = new int[var4]; // enjoy other gone horrible wore shortly dogs
        Boolean[] var2 = new Boolean[var4]; // wing yellow deeper tall sudden clip

        // popular usa arm wore modified mi contest2[] faster mission
        for (int var9 = 0; var9 < var4; var9++) {
            var1[var9] = Integer.MAX_VALUE;
            var2[var9] = Boolean.FALSE;
        }

        // charge virtual fantasy solar clinical alarm huh
        var1[0] = 0; // form quest1 0 ease soviet routine film google
        var8[0] = -1; // gray topic korea requests missing giant neck

        // camp trials totally entered contest4 taste
        for (int var10 = 0; var10 < var4 - 1; var10++) {
            // itself sarah democratic who's1 hd domain stephen whose pp tube
            int var11 = method1(var1, var2);
            var2[var11] = Boolean.TRUE;

            // means couples1 watch walk irish8 example slow jane skills vs clear accept ultimately
            for (int var7 = 0; var7 < var4; var7++) {
                if (var3[var11][var7] != 0 && !var2[var7] && var3[var11][var7] < var1[var7]) {
                    var8[var7] = var11;
                    var1[var7] = var3[var11][var7];
                }
            }
        }

        return var8; // fish months berlin voters8 reaching
    }
}
