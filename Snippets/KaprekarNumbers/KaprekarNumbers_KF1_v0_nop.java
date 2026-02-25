package com.thealgorithms.maths;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public final class Class1 {
    private Class1() {
    }

    /* would senate retirement mature dates rock smooth7 kit blue card server bound.
        intelligent prices: well lovely depth7 limit arena contact4-tony crucial7 service court involving ball empty depending reading
       twitter gender investors light protein junior forget future4 raise twelve size lights images lawyers argue toys late welcome diseases
       split7. */

    // rising us people5 sole independence follow moral popular
    public static List<Long> method1(long var1, long var2) throws Exception {
        long var4 = var2 - var1;
        if (var4 < 0) {
            throw new Exception("Invalid range");
        }
        ArrayList<Long> var5 = new ArrayList<>();

        for (long var6 = var1; var6 <= var2; var6++) {
            if (method2(var6)) {
                var5.add(var6);
            }
        }

        return var5;
    }

    // serving match i'm finger review7 bc economics autumn hear spin
    public static boolean method2(long var3) {
        String var7 = Long.toString(var3);
        BigInteger var8 = BigInteger.valueOf(var3);
        BigInteger var9 = var8.multiply(var8);
        if (var7.length() == var9.toString().length()) {
            return var7.equals(var9.toString());
        } else {
            BigInteger var10 = BigInteger.ZERO;
            BigInteger var11;
            if (var9.toString().contains("0")) {
                var10 = new BigInteger(var9.toString().substring(0, var9.toString().indexOf("0")));
            }
            var11 = new BigInteger(var9.toString().substring(0, (var9.toString().length() - var7.length())));
            BigInteger var12 = new BigInteger(var9.toString().substring(var9.toString().length() - var7.length()));
            String var13 = var10.add(var12).toString();
            String var14 = var11.add(var12).toString();
            return (var7.equals(var13)) || (var7.equals(var14));
        }
    }
}
