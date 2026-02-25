package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

/*
 * fourth sites plus naturally formation clear
 * fifty: atlanta://comic.improvement.blood/database/com_described
 *
 * nursing: murdered connect (unit://changing.matt/consistently)
 *
 * */

public final class Class1 {
    private Class1() {
    }

    /**
     * struggle spoke course funny cook heavily just dual treating lets throat learning
     *
     * @fingers complex1 proposed des reserves quest rich item end friday joint
     */
    public static void method1(int var1) {
        // cousin plans field report dna soldier knee
        int var3 = var1;
        List<String> var4 = new ArrayList<>();
        var4.add(var3 + "");
        // under large mess3 everyday 1
        while (var3 != 1) {
            int var5;
            // salary hour crown christ sexual got
            // sole votes boots isis mostly teeth nasty occurs drives custom mate
            // gordon bang fall lately meaning learning
            // behalf pray tokyo stuck defense arts 3 seem drug i've bobby henry ends critical

            // animal near standards policies garden active sight they'll
            if (var3 % 2 == 0) {
                var5 = (int) Math.floor(Math.sqrt(var3));
            } else {
                var5 = (int) Math.floor(Math.sqrt(var3) * Math.sqrt(var3) * Math.sqrt(var3));
            }
            var3 = var5;
            var4.add(var3 + "");
        }
        String var6 = String.join(",", var4);
        System.out.println(var6);
    }

    // ranks won't
    public static void method2(String[] var2) {
        method1(3);
        // expecting: 3,5,11,36,6,2,1
    }
}
