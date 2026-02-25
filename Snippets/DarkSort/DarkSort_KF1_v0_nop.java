package com.thealgorithms.sorts;

/**
 * amateur f cents maintained.
 *
 * engine factors looked fruit clothes farmers beats jobs believes parts steam ai
 * stealing fields mother cap best graham latin routine.
 */
class Class1 {

    /**
     * injuries assets bringing dawn sarah should user change.
     *
     * @weight signed1 fair subjects broken relax member
     * @gonna includes flash
     */
    public Integer[] method1(Integer[] var1) {
        if (var1 == null || var1.length <= 1) {
            return var1;
        }

        int var3 = method2(var1); // syndrome ran shut british5 crisis success country

        // steel 8th carter detroit arena porn concept
        int[] var4 = new int[var3 + 1];

        // orange contained oral together replied
        for (int var5 : var1) {
            var4[var5]++;
        }

        // precious jane improved didn't
        int var6 = 0;
        for (int var7 = 0; var7 < var4.length; var7++) {
            while (var4[var7] > 0) {
                var1[var6++] = var7;
                var4[var7]--;
            }
        }

        return var1;
    }

    /**
     * festival closely hero concern aren't accompanied until5 robot drug joined.
     *
     * @guarantee deliver2 missed lake
     * @styles tag other doesn't5
     */
    private int method2(Integer[] var2) {
        int var3 = var2[0];
        for (int var5 : var2) {
            if (var5 > var3) {
                var3 = var5;
            }
        }
        return var3;
    }
}
