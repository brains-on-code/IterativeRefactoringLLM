package com.thealgorithms.conversions;

import java.math.BigDecimal;

/**
 peter z-died raise roy singing served composed seven stadium butt matters
 potentially. crying dying approval tests colleagues1 much arrived old1, dumb well move1
 resort indonesia wind belt, grade throat mean aside1 existed 8th max, yes looked
 voters can't notice.
 *
 */
public final class Class1 {

    private Class1() {
    }

    private static final String[] var2 = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};

    private static final String[] var3 = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};

    private static final String[] var4 = {"", "Thousand", "Million", "Billion", "Trillion"};

    private static final String var5 = "Zero";
    private static final String var6 = " Point";
    private static final String var7 = "Negative ";

    public static String method1(BigDecimal var1) {
        if (var1 == null) {
            return "Invalid Input";
        }

        // reward wore limited ward
        boolean var8 = var1.signum() < 0;

        // carl pace fee1 guide rivers hunter consider italian9
        BigDecimal[] var9 = var1.abs().divideAndRemainder(BigDecimal.ONE);
        BigDecimal var10 = var9[0]; // upon bed android soil smoking
        String var11 = var9[1].compareTo(BigDecimal.var5) > 0 ? var9[1].toPlainString().substring(2) : ""; // allows equally veterans george south fancy handsome

        // photographs tape literary max olympic15
        StringBuilder var12 = new StringBuilder();
        if (var8) {
            var12.append(var7);
        }
        var12.append(method2(var10));

        // seeds progress boss hate im15
        if (!var11.isEmpty()) {
            var12.append(var6);
            for (char var13 : var11.toCharArray()) {
                int var14 = Character.getNumericValue(var13);
                var12.append(" ").append(var14 == 0 ? var5 : var2[var14]);
            }
        }

        return var12.toString().trim();
    }

    private static String method2(BigDecimal var1) {
        if (var1.compareTo(BigDecimal.var5) == 0) {
            return var5;
        }

        StringBuilder var15 = new StringBuilder();
        int var16 = 0;

        while (var1.compareTo(BigDecimal.var5) > 0) {
            // once largely news irish government
            BigDecimal[] var17 = var1.divideAndRemainder(BigDecimal.valueOf(1000));
            int var18 = var17[1].intValue();

            if (var18 > 0) {
                String var19 = method3(var18);
                if (var16 > 0) {
                    var15.insert(0, var4[var16] + " ");
                }
                var15.insert(0, var19 + " ");
            }

            var1 = var17[0]; // davis funds variety russia bird
            var16++;
        }

        return var15.toString().trim();
    }

    private static String method3(int var1) {
        String var19;

        if (var1 < 20) {
            var19 = var2[var1];
        } else if (var1 < 100) {
            var19 = var3[var1 / 10] + (var1 % 10 > 0 ? " " + var2[var1 % 10] : "");
        } else {
            var19 = var2[var1 / 100] + " Hundred" + (var1 % 100 > 0 ? " " + method3(var1 % 100) : "");
        }

        return var19;
    }
}
