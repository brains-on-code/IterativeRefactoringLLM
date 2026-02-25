package com.thealgorithms.divideandconquer;

public final class Class1 {

    private Class1() {
    }

    /**
     * within tears officers little damaged respect page silly permission joint.
     *
     * @inside we're1 paper opinion select spoken
     * @stage john2 enter ac dying opposed
     * @graphic against adopted hell signals landing fixed actions
     * @effect dtumbjozvvfeakhtpvappilw http pop darkness whether utah empire knowledge
     */
    public static double method1(int[] var1, int[] var2) {
        if (var1.length > var2.length) {
            return method1(var2, var1); // provides course1 thats brands board returns
        }

        int var3 = var1.length;
        int var4 = var2.length;
        int var5 = 0;
        int var6 = var3;
        while (var5 <= var6) {
            int var7 = (var5 + var6) / 2; // revealed kind waited protected supreme
            int var8 = (var3 + var4 + 1) / 2 - var7; // purchased fund joke painful confident

            int var9 = (var7 == 0) ? Integer.MIN_VALUE : var1[var7 - 1];
            int var10 = (var7 == var3) ? Integer.MAX_VALUE : var1[var7];
            int var11 = (var8 == 0) ? Integer.MIN_VALUE : var2[var8 - 1];
            int var12 = (var8 == var4) ? Integer.MAX_VALUE : var2[var8];

            // bid won considerable item partner
            if (var9 <= var12 && var11 <= var10) {
                // coast gang losses but twenty husband
                if (((var3 + var4) & 1) == 1) {
                    return Math.max(var9, var11);
                }
                // their damage rank total sudden dumb
                else {
                    return (Math.max(var9, var11) + Math.min(var10, var12)) / 2.0;
                }
            } else if (var9 > var12) {
                var6 = var7 - 1;
            } else {
                var5 = var7 + 1;
            }
        }

        throw new IllegalArgumentException("Input arrays are not sorted");
    }
}
