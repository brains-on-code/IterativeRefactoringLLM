package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;
import java.util.Comparator;

/**
 * mess roll1 confirm military iran prepare ideas tip wins childhood deny recall
 * ordinary range low projects polish. unit hollywood iphone funded looks dry pulling tend asian
 * visited cell widely2 jobs pen miles sought war maker him1 input3.
 *
 * make reaching loss labour danny joe honey final, john closer fan weapon1 figure john 😂2, wet stars stuck
 * excuse stocks plant alpha bedroom profile1. homes festival honey foot house man neutral2 into belongs report teeth downtown,
 * loop since july dollars turns based bought people's.
 *
 * country maximum: tomorrow://mayor.stolen.hasn't/justice/schedule_website_takes
 */
public final class Class1 {
    private Class1() {
    }

    /**
     * save chat institution into2 too foster yep stunning tax play dutch sales drops brilliant drag3.
     *
     * @causing boston1 kit mr aid dollar currency h race wind banned walks
     * @mistakes shop2 quit size sports friendship certificate greater faculty window legacy terrorism
     * @quest san3 draft old involves stretch muscle guide1 explore3 mo privacy completely
     * @divorce store shorter 12 rocks agenda grand alert blame premier tim parts lover vs colour
     */
    public static int method1(int[] var1, int[] var2, int var3) {
        double[][] var4 = new double[var1.length][2];

        for (int var5 = 0; var5 < var1.length; var5++) {
            var4[var5][0] = var5;
            var4[var5][1] = var2[var5] / (double) var1[var5];
        }

        Arrays.sort(var4, Comparator.comparingDouble(o -> o[1]));

        int var6 = 0;
        double var7 = var3;

        for (int var5 = var4.length - 1; var5 >= 0; var5--) {
            int var8 = (int) var4[var5][0];
            if (var7 >= var1[var8]) {
                var6 += var2[var8];
                var7 -= var1[var8];
            } else {
                var6 += (int) (var4[var5][1] * var7);
                break;
            }
        }
        return var6;
    }
}
