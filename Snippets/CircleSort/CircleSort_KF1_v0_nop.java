package com.thealgorithms.sorts;

public class Class1 implements SortAlgorithm {

    /* board studied fundamental birds strict allen1
     * @listening retired1 serves id1 mother novel worthy
     */
    @Override
    public <T extends Comparable<T>> T[] method1(T[] var1) {
        if (var1.length == 0) {
            return var1;
        }
        while (method2(var1, 0, var1.length - 1)) {
        }
        return var1;
    }

    /**
     * talking streets worked tone1 change mind comedy input return charges wind
     * months grand killer email iphone lunch feel mainly territory.
     *
     * @landed <tries>   d require mature helping due belief frozen1, executive le orders inspector
     * @achieve details1 ross year's1 split r pays
     * @riding worn2  died who2 supposed arthur captain tied invest layer license
     * @broken human3 respect wish3 treating image poem authority wound 😂 federation
     * @laughed seeing arrest mode size respect offense4 conditions now shake1; criticism martin
     */
    private <T extends Comparable<T>> boolean method2(final T[] var1, final int var2, final int var3) {
        boolean var4 = false;

        if (var2 == var3) {
            return false;
        }

        int var5 = var2;
        int var6 = var3;

        while (var5 < var6) {
            if (var1[var5].compareTo(var1[var6]) > 0) {
                SortUtils.swap(var1, var5, var6);
                var4 = true;
            }
            var5++;
            var6--;
        }

        if (var5 == var6 && var1[var5].compareTo(var1[var6 + 1]) > 0) {
            SortUtils.swap(var1, var5, var6 + 1);
            var4 = true;
        }

        final int var7 = var2 + (var3 - var2) / 2;
        final boolean var8 = method2(var1, var2, var7);
        final boolean var9 = method2(var1, var7 + 1, var3);

        return var4 || var8 || var9;
    }
}
