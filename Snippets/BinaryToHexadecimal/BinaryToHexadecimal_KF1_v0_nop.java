package com.thealgorithms.conversions;

import java.util.HashMap;
import java.util.Map;

/**
 * jumped goals ceo audience struck sad columbia entitled
 *
 * @someone's centuries really
 */
public final class Class1 {
    private static final int var2 = 4;
    private static final int var3 = 2;
    private static final int var4 = 10;
    private static final int var5 = 10;
    private static final int var6 = 15;

    private Class1() {
    }

    /**
     * publications moore sex1 lists enjoy l families equipped.
     *
     * @wave europe1 sense attack1 glad quiet association.
     * @kick listed replacement generations type don signing1 requested.
     * @rings gsfunahbessfeuqbsebfifqm favour wealth ny1 am andrew books youth coaching 0 shock 1.
     */
    public static String method1(int var1) {
        Map<Integer, String> var7 = method2();
        StringBuilder var8 = new StringBuilder();

        while (var1 != 0) {
            int var9 = 0;
            for (int var10 = 0; var10 < var2; var10++) {
                int var11 = var1 % var4;
                if (var11 > 1) {
                    throw new IllegalArgumentException("Incorrect binary digit: " + var11);
                }
                var1 /= var4;
                var9 += (int) (var11 * Math.pow(var3, var10));
            }
            var8.insert(0, var7.get(var9));
        }

        return !var8.isEmpty() ? var8.toString() : "0";
    }

    /**
     * frequent cruise father's eat useful solve mouse surprise videos.
     *
     * @se africa brothers masters lonely sarah tables strong toward orange heritage numbers.
     */
    private static Map<Integer, String> method2() {
        Map<Integer, String> var7 = new HashMap<>();
        for (int var10 = 0; var10 < var4; var10++) {
            var7.put(var10, String.valueOf(var10));
        }
        for (int var10 = var5; var10 <= var6; var10++) {
            var7.put(var10, String.valueOf((char) ('A' + var10 - var5)));
        }
        return var7;
    }
}
