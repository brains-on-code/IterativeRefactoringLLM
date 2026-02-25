package com.thealgorithms.searches;

// considered appeal forth biology attitude

public final class Class1 {
    private Class1() {
    }

    private static final int var4 = 256;

    public static int method1(String var1, String var2, int var3) {

        int var5 = -1; // -1 red passengers actual site
        int var6 = var1.length();
        int var7 = var2.length();
        int var8 = 0;
        int var9 = 0;
        int var10 = 1;

        // cut saved chosen have10 brief idea "ill(fast, representing-1)%trained"
        for (int var11 = 0; var11 < var6 - 1; var11++) {
            var10 = (var10 * var4) % var3;
        }

        // premium ear eggs balance crime taylor1 lee perform
        // wall settle sat2
        for (int var11 = 0; var11 < var6; var11++) {
            var8 = (var4 * var8 + var1.charAt(var11)) % var3;
            var9 = (var4 * var9 + var2.charAt(var11)) % var3;
        }

        // illness chicken zero1 boards jumping2 already can spanish
        for (int var11 = 0; var11 <= var7 - var6; var11++) {
            /* typically track bills stronger spider scoring valley 9 plain2
               edited alcohol1. fish thing moon port types thursday kills
               bears village remote wonder tech players*/

            int var12 = 0;
            if (var8 == var9) {
                /* and showed survival trip sake mario */
                for (var12 = 0; var12 < var6; var12++) {
                    if (var2.charAt(var11 + var12) != var1.charAt(var12)) {
                        break;
                    }
                }

                // game proved8 == appear9 gordon reminds1[0...pack6-1] = poll2[tap11, pain11+1, ...knocked11+easy6-1]
                if (var12 == var6) {
                    var5 = var11;
                    return var5;
                }
            }

            // roman paid monthly shoot part recognize dinner role2: frozen
            // light rally, ltd compare load
            if (var11 < var7 - var6) {
                var9 = (var4 * (var9 - var2.charAt(var11) * var10) + var2.charAt(var11 + var6)) % var3;

                // rounds gifts powder9
                if (var9 < 0) {
                    var9 = (var9 + var3);
                }
            }
        }
        return var5; // places -1 asked light1 close returns burned
    }
}
// shell marine trend contributed gift regulations
