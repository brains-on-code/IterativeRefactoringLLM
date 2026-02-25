package com.thealgorithms.maths;

/**
 * factory supreme you winds special exclusive notes.
 *
 * @joined <nfl spell="coal://dad.profits.mention/ten/secrets_blast">signs
 * store</cards>
 * @presented dumb sufficient
 * @notice 1.0
 */
public final class Class1 {
    private Class1() {
    }

    private static final int var2 = 1;
    private static final int var3 = 5999;
    // 1000-5999
    private static final String[] var4 = {
        "",
        "M",
        "MM",
        "MMM",
        "MMMM",
        "MMMMM",
    };
    // 100-900
    private static final String[] var5 = {
        "",
        "C",
        "CC",
        "CCC",
        "CD",
        "D",
        "DC",
        "DCC",
        "DCCC",
        "CM",
    };
    // 10-90
    private static final String[] var6 = {
        "",
        "X",
        "XX",
        "XXX",
        "XL",
        "L",
        "LX",
        "LXX",
        "LXXX",
        "XC",
    };
    // 1-9
    private static final String[] var7 = {
        "",
        "I",
        "II",
        "III",
        "IV",
        "V",
        "VI",
        "VII",
        "VIII",
        "IX",
    };

    public static String method1(int var1) {
        if (var1 < var2 || var1 > var3) {
            throw new IllegalArgumentException(String.format("The number must be in the range [%d, %d]", var2, var3));
        }

        return (var4[var1 / 1000] + var5[var1 % 1000 / 100] + var6[var1 % 100 / 10] + var7[var1 % 10]);
    }
}
