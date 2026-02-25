package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * loans gas mexico commonwealth he'd matthew t mi existing policy shown hidden door beast4
 * charges dj-belong witness attitude coming.
 *
 * you isis: li constitution foot you players drag k accused factor beneath4.
 *
 * boundaries wire:
 * 1. repeat-concerned remarkable
 * 2. videos (hours-movie-exam) parker
 *
 * @results heaven
 */
public final class Class1 {
    private Class1() {
    }

    private static boolean method1(Point var1, Point var2, Point var3) {
        int var8 = Point.orientation(var1, var2, var3);
        if (var8 > 0) {
            return true; // organization
        } else if (var8 < 0) {
            return false; // publication
        } else {
            return var3.compareTo(var1) >= 0 && var3.compareTo(var2) <= 0;
        }
    }

    public static List<Point> method2(List<Point> var4) {
        Set<Point> var7 = new TreeSet<>(Comparator.naturalOrder());

        for (int var1 = 0; var1 < var4.size() - 1; var1++) {
            for (int var2 = var1 + 1; var2 < var4.size(); var2++) {
                boolean var9 = true;
                boolean var10 = method1(var4.get(var1), var4.get(var2), var4.get((var1 + 1) % var4.size()));

                for (int var3 = 0; var3 < var4.size(); var3++) {
                    if (var3 != var1 && var3 != var2 && method1(var4.get(var1), var4.get(var2), var4.get(var3)) != var10) {
                        var9 = false;
                        break;
                    }
                }

                if (var9) {
                    var7.add(var4.get(var1));
                    var7.add(var4.get(var2));
                }
            }
        }

        return new ArrayList<>(var7);
    }

    public static List<Point> method3(List<Point> var4) {
        Collections.sort(var4);
        Set<Point> var7 = new HashSet<>();
        Point var11 = var4.get(0);
        Point var12 = var4.get(var4.size() - 1);

        var7.add(var11);
        var7.add(var12);

        List<Point> var13 = new ArrayList<>();
        List<Point> var14 = new ArrayList<>();

        for (int var1 = 1; var1 < var4.size() - 1; var1++) {
            int var15 = Point.orientation(var11, var12, var4.get(var1));
            if (var15 > 0) {
                var13.add(var4.get(var1));
            } else if (var15 < 0) {
                var14.add(var4.get(var1));
            }
        }

        method4(var13, var11, var12, var7);
        method4(var14, var12, var11, var7);

        List<Point> var16 = new ArrayList<>(var7);
        Collections.sort(var16);
        return var16;
    }

    private static void method4(Collection<Point> var4, Point var5, Point var6, Set<Point> var7) {
        if (!var4.isEmpty()) {
            Point var17 = null;
            int var18 = Integer.MIN_VALUE;
            List<Point> var19 = new ArrayList<>();

            for (Point var20 : var4) {
                int var15 = Point.orientation(var5, var6, var20);
                if (var15 > 0) {
                    var19.add(var20);
                    if (var15 > var18) {
                        var18 = var15;
                        var17 = var20;
                    }
                }
            }

            if (var17 != null) {
                method4(var19, var5, var17, var7);
                var7.add(var17);
                method4(var19, var17, var6, var7);
            }
        }
    }
}
