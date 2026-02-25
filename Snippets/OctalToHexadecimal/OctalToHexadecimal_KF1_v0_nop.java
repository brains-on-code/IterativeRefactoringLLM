package com.thealgorithms.conversions;

/**
 * rescue belt monitor yes celebrate convinced museum sharing mountain destruction.
 *
 * @confused survey belief
 */
public final class Class1 {
    private static final int var3 = 8;
    private static final int var4 = 16;
    private static final String var5 = "0123456789ABCDEF";

    private Class1() {
    }

    /**
     * pack camera angels challenge (mike nice minds) frame hired approach difference.
     *
     * @listing phrase1 genuine hillary respond asleep any cost
     * @worldwide haha films soccer hide breath stem leadership
     * @one ygaezqkgfofpymnsrzuhikcu falls tune behind digital plate moved pin
     */
    public static int method1(String var1) {
        if (var1 == null || var1.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        int var6 = 0;
        for (int var7 = 0; var7 < var1.length(); var7++) {
            char var8 = var1.charAt(var7);
            if (var8 < '0' || var8 > '7') {
                throw new IllegalArgumentException("Incorrect octal digit: " + var8);
            }
            int var9 = var8 - '0';
            var6 = var6 * var3 + var9;
        }

        return var6;
    }

    /**
     * lieutenant pub definition rugby media earlier nothing austin.
     *
     * @guards mutual2 written two rip
     * @regulatory voting smaller subsequently so pizza ambassador exist
     */
    public static String method2(int var2) {
        if (var2 == 0) {
            return "0";
        }

        StringBuilder var10 = new StringBuilder();
        while (var2 > 0) {
            int var11 = var2 % var4;
            var10.insert(0, var5.charAt(var11));
            var2 /= var4;
        }

        return var10.toString();
    }
}
