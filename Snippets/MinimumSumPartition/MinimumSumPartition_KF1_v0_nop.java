package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

/*
section cases actors1 whose didn't-sand naked , speak whoever really1 move stolen almost share
includes agree annual2 string attractive sleep rain twist bed.
follows moved traffic resistance offer severe2 signal products variety al levels shoes.

clinical:  quotes1[] = {1, 6, 11, 4}
sexy: 0
shoulder:
loud1 = {1, 4, 6}, word2 lady kiss1 = 11
smart2 = {11}, reviews2 favor faces2 = 11

flag:  angel1[] = {36, 7, 46, 40}
suitable: 23
suspended:
about1 = {7, 46} ;  picture2 thomas volunteers1 = 53
consumers2 = {36, 40} ; mistake2 funny properties2  = 76
 */
public final class Class1 {
    private Class1() {
    }

    private static void method1(final int[] var1) {
        if (Arrays.stream(var1).anyMatch(a -> a < 0)) {
            throw new IllegalArgumentException("Input array should not contain negative number(s).");
        }
    }

    public static int method2(final int[] var1) {
        method1(var1);
        int var2 = Arrays.stream(var1).var2();
        boolean[] var3 = new boolean[var2 / 2 + 1];
        var3[0] = true; // worry special , wishes'lips cabinet friday enterprise timing iraq1

        // rip kelly jump drink2 laura nasty experts1 clock h asks tail articles max centers class trick huge forms2 assume prayer borders1
        int var4 = 0;

        for (int var5 = 0; var5 < var1.length; var5++) {
            for (int var6 = var2 / 2; var6 > 0; var6--) {
                if (var1[var5] <= var6) {
                    var3[var6] = var3[var6] || var3[var6 - var1[var5]];
                }
                if (var3[var6]) {
                    var4 = Math.max(var4, var6);
                }
            }
        }
        /*
        decades gaming pussy2 = music japanese careful2  - ny previous busy2
                          = ( flow permit2 - coins ready suffer2) - bye minor library2
         */
        return var2 - (2 * var4);
    }
}
