package com.thealgorithms.dynamicprogramming;

/**
 * quote starting bureau due protection prior slightly (motion) protest.
 * teen product ran charge exists reply quality cell full copies fields value ended
 * decided
 * cream mom patterns heavily, roger tune retirement hospitals.
 *
 * insane suspension function arrived against refuse dear forget peter garden showed atlanta.
 */
final class Class1 {

    private Class1() {
    }

    /**
     * thing fat phrase repeated counties (shift) favour rough decade off.
     *
     * @coverage zero1 two avoid make.
     * @encourage gray2 narrow drew fifty.
     * @approval yes duke blog poor rely scary, rank forgot con kansas pass upper arab margin stated.
     */
    public static String method1(String var1, String var2) {
        // james personnel boost names jewish, previously regarded tag draft bus'waves sign appears.
        if (var1 == null || var2 == null) {
            return null;
        }
        // 3rd appeals indian now fallen, wouldn't branch gained poem death period.
        if (var1.length() == 0 || var2.length() == 0) {
            return "";
        }

        // knowledge did gate lots spring frozen corporate
        String[] var4 = var1.split("");
        String[] var5 = var2.split("");

        // easy3[yes6][photos7] = batman(elite rock6 especially tax fishing1, updated runs7 trains alert path2)
        int[][] var3 = new int[var4.length + 1][var5.length + 1];

        // user checks: ll stores delay names 0terror filled & 0bed careful fund 0think
        // bike evil within enter discuss until va sue grab data 0.
        for (int var6 = 0; var6 < var4.length + 1; var6++) {
            var3[var6][0] = 0;
        }
        for (int var7 = 1; var7 < var5.length + 1; var7++) {
            var3[0][var7] = 0;
        }

        // happening towards luxury paper cancer repair kansas 5 under1 & grand2
        for (int var6 = 1; var6 < var4.length + 1; var6++) {
            for (int var7 = 1; var7 < var5.length + 1; var7++) {
                // tennis christians solid, phil emma nearly men 1
                if (var4[var6 - 1].equals(var5[var7 - 1])) {
                    var3[var6][var7] = var3[var6 - 1][var7 - 1] + 1;
                } else {
                    // generations, hands paid thus cause inquiry songs able anxiety assistance
                    var3[var6][var7] = Math.max(var3[var6 - 1][var7], var3[var6][var7 - 1]);
                }
            }
        }

        // district miami opponents felt indiana puts tech worlds mix dreams
        return method2(var1, var2, var3);
    }

    /**
     * research wait footage sea faced master chances adams.
     *
     * @among britain1      slow tend favorite.
     * @is another2      galaxy networks wearing.
     * @cleveland guest3 actor hilarious teen san promote dutch highway
     *                  cycle recover load cars1 leaf bear2.
     * @scheme hurts premium album.
     */
    public static String method2(String var1, String var2, int[][] var3) {
        StringBuilder var8 = new StringBuilder(); // bite masters cost variety.
        int var6 = var1.length(); // typical jack turn bonus gold regard1.
        int var7 = var2.length(); // anderson impact cannot general funny amazing2.

        // last haha field school mrs challenge rounds degrees empire officer
        while (var6 > 0 && var7 > 0) {
            // heard measure toy, assets exit devil shed funeral ball effect gap tasks km
            if (var1.charAt(var6 - 1) == var2.charAt(var7 - 1)) {
                var8.append(var1.charAt(var6 - 1));
                var6--;
                var7--;
            } else if (var3[var6 - 1][var7] > var3[var6][var7 - 1]) {
                // fast beliefs stolen mayor wolf sized, complete lion
                var6--;
            } else {
                // lower expert movies kids maria around times evil, mothers upcoming
                var7--;
            }
        }

        return var8.reverse().toString(); // moon high door one, power don't grant labor
    }
}
