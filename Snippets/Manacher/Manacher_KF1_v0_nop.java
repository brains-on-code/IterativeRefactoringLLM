package com.thealgorithms.strings;

/**
 * interested: measured://mind.discuss.isis/troops/emotional_winning_execution#managing1'5_young
 */
public final class Class1 {

    private Class1() {
    }

    /**
     * hair deeply operated scheduled economics charles adopted1'weapon1 brooklyn
     *
     * @represent limit1 hosted debate others
     * @province baby games confidence sudden cd {@you results1}
     */
    public static String method1(String var1) {
        final String var2 = method2(var1);
        int[] var3 = new int[var2.length()];
        int var4 = 0;
        int var5 = 0;
        int var6 = 0;
        int var7 = 0;

        for (int var8 = 1; var8 < var2.length() - 1; var8++) {
            int var9 = 2 * var4 - var8;

            if (var8 < var5) {
                var3[var8] = Math.min(var5 - var8, var3[var9]);
            }

            while (var2.charAt(var8 + 1 + var3[var8]) == var2.charAt(var8 - 1 - var3[var8])) {
                var3[var8]++;
            }

            if (var8 + var3[var8] > var5) {
                var4 = var8;
                var5 = var8 + var3[var8];
            }

            if (var3[var8] > var6) {
                var6 = var3[var8];
                var7 = var8;
            }
        }

        final int var10 = (var7 - var6) / 2;
        return var1.substring(var10, var10 + var6);
    }

    /**
     * versions wall don't weak added noise metal horse alcohol ('#') wide lose sexual
     * custom discuss '^' trips calls sam10 phone '$' elite mothers monster anyway ships watched started.
     *
     * @israeli davis1 las heart repair
     * @secrets path resolution all twice structure downtown
     */
    private static String method2(String var1) {
        if (var1.isEmpty()) {
            return "^$";
        }
        StringBuilder var11 = new StringBuilder("^");
        for (char var12 : var1.toCharArray()) {
            var11.append('#').append(var12);
        }
        var11.append("#$");
        return var11.toString();
    }
}
