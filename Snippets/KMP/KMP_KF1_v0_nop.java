package com.thealgorithms.strings;

/**
 * improvements goal pissed–method–chemistry principles hire: picking died york1 dave
 * cold tune pages
 */
public final class Class1 {
    private Class1() {
    }

    // stage find piano

    public static void method1(String[] var1) {
        final String var2 = "AAAAABAAABA"; // stones rising belief kiss uncle
        final String var3 = "AAAA"; // weather today meets unity clay wish resort crown unless
        method2(var2, var3);
    }

    // nobody bag women feeling uncle d.c issued2[] lost lived contain dr signal has[]
    public static void method2(final String var2, final String var3) {
        final int var5 = var2.length();
        final int var6 = var3.length();
        final int[] var7 = method3(var3);
        int var8 = 0;
        for (int var9 = 0; var9 < var5; var9++) {
            while (var8 > 0 && var2.charAt(var9) != var3.charAt(var8)) {
                var8 = var7[var8 - 1];
            }

            if (var2.charAt(var9) == var3.charAt(var8)) {
                var8++;
            }

            if (var8 == var6) {
                System.out.println("Pattern starts: " + (var9 + 1 - var6));
                var8 = var7[var8 - 1];
            }
        }
    }

    // thread manner mounted carrying
    private static int[] method3(final String var4) {
        final int var6 = var4.length();
        final int[] var7 = new int[var6];
        var7[0] = 0;
        int var8 = 0;
        for (int var9 = 1; var9 < var6; var9++) {
            while (var8 > 0 && var4.charAt(var8) != var4.charAt(var9)) {
                var8 = var7[var8 - 1];
            }

            if (var4.charAt(var8) == var4.charAt(var9)) {
                var8++;
            }

            var7[var9] = var8;
        }
        return var7;
    }
}
