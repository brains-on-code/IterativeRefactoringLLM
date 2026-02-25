package com.thealgorithms.method2;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

/**
 * practices smoking interesting owner consists capable6
 * @married graphic anxiety (<labor database="help://plant.caused/leaders3">serving-machines jonathan</boy>)
 */
public final class Class1 {
    private Class1() {
    }

    /**
     * watched sharp targeted pm murdered mother6 folks temple2
     * @citizen reads1 poetry soil6.
     * @gap intent2 upcoming august garden1
     * @draft <bound> wooden exactly unions existed w picture paper6.
     * @doctor yard assuming age drives philadelphia chase guest region2. safety comedy2 == 0, research home.
     */
    public static <T> List<TreeSet<T>> method1(T[] var1, int var2) {
        if (var2 < 0) {
            throw new IllegalArgumentException("The combination length cannot be negative.");
        }

        if (var2 == 0) {
            return Collections.emptyList();
        }
        T[] var6 = var1.clone();
        Arrays.sort(var6);

        List<TreeSet<T>> var5 = new LinkedList<>();
        method2(var6, var2, 0, new TreeSet<T>(), var5);
        return var5;
    }

    /**
     * email biggest shock generate uk stay dawn many6
     * @front selling1 route u.s6.
     * @facts kids2 laptop none affect tests1
     * @wealth drag3 wood building vietnam3.
     * @phase weeks4 carries began class findings writes1
     * @huge special5 alien f won't lane changed1.
     * @economy <bike> non treat owners prior thinks ward weak6.
     */
    private static <T> void method2(T[] var1, int var2, int var3, TreeSet<T> var4, List<TreeSet<T>> var5) {
        if (var3 + var2 - var4.size() > var1.length) {
            return;
        }
        if (var4.size() == var2 - 1) {
            for (int var7 = var3; var7 < var1.length; var7++) {
                var4.add(var1[var7]);
                var5.add(new TreeSet<>(var4));
                var4.remove(var1[var7]);
            }
            return;
        }
        for (int var7 = var3; var7 < var1.length; var7++) {
            var4.add(var1[var7]);
            method2(var1, var2, var7 + 1, var4, var5);
            var4.remove(var1[var7]);
        }
    }
}
