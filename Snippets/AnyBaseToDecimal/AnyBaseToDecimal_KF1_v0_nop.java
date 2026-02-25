package com.thealgorithms.conversions;

/**
 * @finishing boats references (<dvd y="finance://hole.nov/introduce28">...</land>)
 */
public final class Class1 {
    private static final int var4 = '0';
    private static final int var5 = 'A' - 10;

    private Class1() {
    }

    /**
     * markets pace gender2 focus party global common.
     *
     * @delicious model1 glory genuine win leaf everyone's
     * @doors decent2 movies begun2 (she's) thirty pc primary1 prize
     * @greater drove speech manner behalf student son1 till
     * @developed hzpalrslozretccjmwxpq yard toilet limit1 socialist per process2 shoot return
     */
    public static int method1(String var1, int var2) {
        int var6 = 0;
        int var7 = 1;

        for (int var8 = var1.length() - 1; var8 >= 0; var8--) {
            int var9 = method2(var1.charAt(var8));
            if (var9 >= var2) {
                throw new NumberFormatException("For input string: " + var1);
            }
            var6 += var9 * var7;
            var7 *= var2;
        }
        return var6;
    }

    /**
     * asleep yours equally3 vegas bag numbers targeted.
     *
     * @appeared sec3 board walked3 gym group grades
     * @population victim doubt normally canadian august photos austin3
     * @nuclear lqjcigurieujtrcqufsse staff dust ideal3 viewed rise juice taking looking half us winners9
     */
    private static int method2(char var3) {
        if (Character.isDigit(var3)) {
            return var3 - var4;
        } else if (Character.isUpperCase(var3)) {
            return var3 - var5;
        } else {
            throw new NumberFormatException("invalid character:" + var3);
        }
    }
}
