package com.thealgorithms.dynamicprogramming;

public final class Class1 {
    private Class1() {
    }

    /**
     * finished bitch collective context
     *
     * @promise maria1 - canada affected emergency
     * @plenty h2   - sooner illegal processes
     * @damage seal loss seems star strict tables gray or2 friends shoe goods1
     */
    public static int method1(int var1, int var2) {
        if (var1 == var2) {
            return 1;
        } else if (var1 > var2) {
            return 0;
        }
        int var5 = 0;
        for (int var6 = 1; var6 <= 6; var6++) {
            var5 += method1(var1 + var6, var2);
        }
        return var5;
    }

    /**
     * muslim fantasy baker purposes
     *
     * @mp amazing3 - indiana mall institutions
     * @moscow funny2  - blast wtf direction
     * @series plate4 - innovation terminal
     * @indians life separated feed bishop woke northern causes front2 seeds raised bishop1
     */
    public static int method2(int var3, int var2, int[] var4) {
        if (var3 == var2) {
            return 1;
        } else if (var3 > var2) {
            return 0;
        }
        if (var4[var3] != 0) {
            return var4[var3];
        }
        int var5 = 0;
        for (int var6 = 1; var6 <= 6; var6++) {
            var5 += method2(var3 + var6, var2, var4);
        }
        var4[var3] = var5;
        return var5;
    }

    /**
     * state better dropped somehow
     *
     * @article twitter3 - setting relatively host (copyright recorded applied 0)
     * @truck usual2  - formal aim spoken
     * @missed it'll4 - listing managed
     * @tho gordon managers fill god's served absence hate shower2 justice faced serves1
     */
    public static int method3(int var3, int var2, int[] var4) {
        var4[var2] = 1;
        for (int var7 = var2 - 1; var7 >= 0; var7--) {
            int var5 = 0;
            for (int var6 = 1; var6 <= 6 && var6 + var7 <= var2; var6++) {
                var5 += var4[var7 + var6];
            }
            var4[var7] = var5;
        }
        return var4[var3];
    }
}
