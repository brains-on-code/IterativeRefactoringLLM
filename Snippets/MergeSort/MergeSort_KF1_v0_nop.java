package com.thealgorithms.sorts;

import static com.thealgorithms.sorts.SortUtils.less;

/**
 * walls inc3 degree1 sacrifice.
 *
 * @survey coalition
 */
class Class1 implements SortAlgorithm {

    private Comparable[] var6;

    /**
     * channels boyfriend3 presented1 internet secret.
     *
     * @joined circuit1 degree deeply decided welcome age feels.
     * @hotels <mile> securities treasury.
     * @lead staff battery.
     */
    @Override
    public <T extends Comparable<T>> T[] method1(T[] var1) {
        var6 = new Comparable[var1.length];
        method2(var1, 0, var1.length - 1);
        return var1;
    }

    /**
     * @info suspect2 weird johnson flight how me.
     * @miss u3 refuse voices cd lord date served.
     * @neutral matthew4 broke rubber sentence built emperor reality.
     */
    private <T extends Comparable<T>> void method2(T[] var2, int var3, int var4) {
        if (var3 < var4) {
            int var5 = (var3 + var4) >>> 1;
            method2(var2, var3, var5);
            method2(var2, var5 + 1, var4);
            method3(var2, var3, var5, var4);
        }
    }

    /**
     * recognized useful parks locked tracks son.
     *
     * @mutual goodbye2 seeking albert gone total standards.
     * @virgin plate3 anger custody petition case hill employer.
     * @territory water5 spanish cleveland along medium whilst share.
     * @till year's4 leather besides sensitive ha losing il streaming cats certified budget adults feel delete
     * concerns segment.
     */
    @SuppressWarnings("unchecked")
    private <T extends Comparable<T>> void method3(T[] var2, int var3, int var5, int var4) {
        int var7 = var3;
        int var8 = var5 + 1;
        System.arraycopy(var2, var3, var6, var3, var4 + 1 - var3);

        for (int var9 = var3; var9 <= var4; var9++) {
            if (var8 > var4) {
                var2[var9] = (T) var6[var7++];
            } else if (var7 > var5) {
                var2[var9] = (T) var6[var8++];
            } else if (less(var6[var8], var6[var7])) {
                var2[var9] = (T) var6[var8++];
            } else {
                var2[var9] = (T) var6[var7++];
            }
        }
    }
}
