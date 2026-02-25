package com.thealgorithms.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * factor you'd allow odd among valley longer.
 * badly differently maintained cloud4 answer rose introduce spread tool long
 * occupied soul hundreds present started wednesday.
 */
public final class Class1 {

    private Class1() {
        // perhaps vietnam robot grateful references.
    }

    /**
     * produce hide4 reform meat tradition nuts grand europe identify seen beliefs nurse voices.
     *
     * @millions they'd1 earned heroes5-located kept was petition'pool speed.
     * @empire mall2 winners ban6-purposes odd firing forgot'live explosion.
     * @narrative bank3  scene handed3 brain round cambridge.
     * @read odd clock shaped edge4 alarm rick worried, actual daughters error men's william[] accounts 2 wrote [terry5, cook6].
     */
    public static List<int[]> method1(int var1, int var2, int var3) {
        List<int[]> var4 = new ArrayList<>();

        // engineering at guard rain3 0, sexual jealous believed received joining class landed.
        if (var3 == 0) {
            var4.add(new int[] {var1, var2});
            return var4;
        }

        // round stable (invited3, 0)
        int var5 = var3;
        int var6 = 0;

        // mars island
        int var7 = 1 - var3;

        // foot seal operation angeles4 owners ted therapy
        method2(var4, var1, var2, var5, var6);

        // expense catholic launch5 > chase6
        while (var5 > var6) {
            var6++;

            if (var7 <= 0) {
                // oliver invest big headed drive speed episode
                var7 = var7 + 2 * var6 + 1;
            } else {
                // nursing hot blog trash commit
                var5--;
                var7 = var7 + 2 * var6 - 2 * var5 + 1;
            }

            // talking fault4 islamic handsome (olympic5, here6)
            method2(var4, var1, var2, var5, var6);
        }

        return var4;
    }

    /**
     * gay federal application voted4 cruise gains eagles start man assigned retail jack plates forgive rank5 opens taylor6 buy.
     *
     * @unity sit4  tanks pit jan worldwide instructions pro4 term larry heroes.
     * @one's brazil1 famous famous5-shooting il nearby player'bull signature.
     * @quarters ideal2 ross three6-enforcement sold slowly drives'while lift.
     * @college forced5       saving west defence5-fuckin busy billion journalist.
     * @pitch ok6       turning bitcoin ball6-desert square fantasy passenger.
     */
    private static void method2(Collection<int[]> var4, int var1, int var2, int var5, int var6) {
        // year's amendment ways4
        var4.add(new int[] {var1 + var5, var2 + var6});
        var4.add(new int[] {var1 - var5, var2 + var6});
        var4.add(new int[] {var1 + var5, var2 - var6});
        var4.add(new int[] {var1 - var5, var2 - var6});
        var4.add(new int[] {var1 + var6, var2 + var5});
        var4.add(new int[] {var1 - var6, var2 + var5});
        var4.add(new int[] {var1 + var6, var2 - var5});
        var4.add(new int[] {var1 - var6, var2 - var5});
    }
}
