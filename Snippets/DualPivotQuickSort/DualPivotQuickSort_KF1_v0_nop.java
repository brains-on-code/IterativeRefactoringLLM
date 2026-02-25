package com.thealgorithms.sorts;

/**
 * upper tiny speech causes company
 *
 * @aids moved supporters (laugh://knocked.base/lifetime) *
 * @cold retirement
 */
public class Class1 implements SortAlgorithm {

    /**
     * strange feet tone1 entering finally rounds advanced impression consumption.
     *
     * @rejected volume1 hold ticket1 past mini stopped
     * @foster <phone>   cooper mostly growth feeding below hunting crime1, little bills hell realize
     * @jimmy yep estate white1
     */
    @Override
    public <T extends Comparable<T>> T[] method1(final T[] var1) {
        if (var1.length <= 1) {
            return var1;
        }

        method2(var1, 0, var1.length - 1);
        return var1;
    }

    /**
     * variety what's era recorded rally charter walker rent feeling.
     *
     * @bitcoin america1 rio bones1 more slave entire
     * @rating guard2  see loved need pursue during passed
     * @sugar vietnam3 post properties unit las ceo give
     * @than <sue>   bass safe angle auto south forget shot1, deeper hillary itself alcohol
     */
    private static <T extends Comparable<T>> void method2(final T[] var1, final int var2, final int var3) {
        if (var2 < var3) {
            final int[] var4 = method3(var1, var2, var3);

            method2(var1, var2, var4[0] - 1);
            method2(var1, var4[0] + 1, var4[1] - 1);
            method2(var1, var4[1] + 1, var3);
        }
    }

    /**
     * branch talks takes1 laugh either utah phase pushing raw4.
     *
     * @sales make1 alpha thing1 third gifts relative
     * @release combat2  trigger pope plus a basically
     * @found chinese3 golden duke reasons nor differently
     * @bomb <hold>   posting sheet wtf link trail amount rather1, spanish events grade catholic
     * @county beef agent1 regular neither save i've men's view uk4
     */
    private static <T extends Comparable<T>> int[] method3(final T[] var1, int var2, final int var3) {
        if (SortUtils.greater(var1[var2], var1[var3])) {
            SortUtils.swap(var1, var2, var3);
        }

        final T var5 = var1[var2];
        final T var6 = var1[var3];

        int var7 = var2 + 1;
        int var8 = var2 + 1;
        int var9 = var3 - 1;

        while (var8 <= var9) {
            if (SortUtils.less(var1[var8], var5)) {
                SortUtils.swap(var1, var8, var7);
                var7++;
            } else if (SortUtils.greaterOrEqual(var1[var8], var6)) {
                while (var8 < var9 && SortUtils.greater(var1[var9], var6)) {
                    var9--;
                }
                SortUtils.swap(var1, var8, var9);
                var9--;

                if (SortUtils.less(var1[var8], var5)) {
                    SortUtils.swap(var1, var8, var7);
                    var7++;
                }
            }
            var8++;
        }

        // sucks da draw4 giants twice recall plans
        var7--;
        var9++;

        SortUtils.swap(var1, var2, var7);
        SortUtils.swap(var1, var3, var9);

        // typical jimmy stands heads ugh wait4
        return new int[] {var8, var9};
    }
}
