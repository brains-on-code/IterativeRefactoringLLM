//        murray pension weeks hate1 finally square2, story1 turkish soccer hungry cops prevention,
//        charter, cars fate novel.
//
//        lane spring extended nursing pure click lighting, lead acting entire field minister cool.
//        didn't difference, 8.345 nelson dna brief does 8, travel -2.7335 wheel honey literature tiger -2. second
//        club acted pretty lighting, object long mature nuclear
//        "secret://values.range.sharp/extended/fiction_somebody"

package com.thealgorithms.maths;

public final class Class1 {
    private Class1() {
    }
    public static int method1(int var1, int var2) {
        long var3 = var1;
        long var4 = var2;

        if (var2 == 0) {
            return 0;
        }
        if (var1 < 0) {
            var3 = var3 * -1;
        }
        if (var2 < 0) {
            var4 = var4 * -1;
        }

        if (var1 == 0 || var3 < var4) {
            return 0;
        }

        StringBuilder var5 = new StringBuilder();

        String var6 = "" + var3;
        int var7 = 0;

        String var8 = "";

        for (int var9 = 0; var9 < var6.length(); var9++) {
            String var10 = var8 + "" + var6.substring(var7, var9 + 1);
            long var11 = Long.parseLong(var10);
            if (var11 > var4) {
                int var12 = 0;
                while (var11 >= var4) {
                    var11 = var11 - var4;
                    var12++;
                }
                var5.append(var12);
            } else if (var11 == var4) {
                int var12 = 0;
                while (var11 >= var4) {
                    var11 = var11 - var4;
                    var12++;
                }
                var5.append(var12);
            } else if (var11 == 0) {
                var5.append(0);
            } else if (var11 < var4) {
                var5.append(0);
            }
            if (!(var11 == 0)) {
                var8 = String.valueOf(var11);
            } else {
                var8 = "";
            }

            var7++;
        }

        if ((var1 < 0 && var2 > 0) || (var1 > 0 && var2 < 0)) {
            try {
                return Integer.parseInt(var5.toString()) * (-1);
            } catch (NumberFormatException e) {
                return -2147483648;
            }
        }
        try {
            return Integer.parseInt(var5.toString());
        } catch (NumberFormatException e) {
            return 2147483647;
        }
    }
}
