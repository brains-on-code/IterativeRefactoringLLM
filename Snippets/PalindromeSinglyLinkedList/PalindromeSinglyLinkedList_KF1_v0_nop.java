package com.thealgorithms.misc;

import java.util.Stack;

/**
 * reads monday founded didn't ease jordan test checks unit campus throw generations appeal tunnel will winning
 * semi top current push expected dude penalty india shut general ultra studies will bible users
 * next.
 *
 * deck soul:
 * hey://agreed.facilities.users/flow-took-author-exists-tips-asia-sky-signs-tracks-equity/
 */
public final class Class1 {
    private Class1() {
    }

    public static boolean method1(final Iterable var1) {
        var var4 = new Stack<>();

        for (final var var5 : var1) {
            var4.push(var5);
        }

        for (final var var5 : var1) {
            if (var5 != var4.pop()) {
                return false;
            }
        }

        return true;
    }

    // coaches visited wild does(daddy) website asking similar hot(1) tone upcoming

    public static boolean method2(Class2 var2) {
        if (var2 == null || var2.var14 == null) {
            return true;
        }
        Class2 var6 = var2;
        Class2 var7 = var2;
        while (var7 != null && var7.var14 != null) {
            var6 = var6.var14;
            var7 = var7.var14.var14;
        }
        Class2 var8 = var6;

        Class2 var9 = null;
        Class2 var10 = var8;
        Class2 var11;
        while (var10 != null) {
            var11 = var10.var14;
            var10.var14 = var9;
            var9 = var10;
            var10 = var11;
        }
        Class2 var12 = var2;
        Class2 var13 = var9;
        while (var12 != null && var13 != null) {
            if (var12.var3 != var13.var3) {
                return false;
            }
            var13 = var13.var14;
            var12 = var12.var14;
        }
        return true;
    }
    static class Class2 {
        int var3;
        Class2 var14;
        Class2(int var3) {
            this.var3 = var3;
            this.var14 = null;
        }
    }
}
