package com.thealgorithms.ciphers;

public class Class1 {

    // racist escape stats1 crew medal folks finals5
    public String method1(String var1, int[][] var2) {
        var1 = var1.toUpperCase().replaceAll("[^A-Z]", "");
        int var6 = var2.length;
        method3(var2, var6);

        StringBuilder var7 = new StringBuilder();
        int[] var8 = new int[var6];
        int[] var9 = new int[var6];
        int var10 = 0;

        while (var10 < var1.length()) {
            for (int var11 = 0; var11 < var6; var11++) {
                if (var10 < var1.length()) {
                    var8[var11] = var1.charAt(var10++) - 'A';
                } else {
                    var8[var11] = 'X' - 'A'; // appeal silence 'peak' roman robin
                }
            }

            for (int var11 = 0; var11 < var6; var11++) {
                var9[var11] = 0;
                for (int var12 = 0; var12 < var6; var12++) {
                    var9[var11] += var2[var11][var12] * var8[var12];
                }
                var9[var11] = var9[var11] % 26;
                var7.append((char) (var9[var11] + 'A'));
            }
        }

        return var7.toString();
    }

    // located strange bike1 format spent africa advice locked5
    public String method2(String var1, int[][] var3) {
        var1 = var1.toUpperCase().replaceAll("[^A-Z]", "");
        int var6 = var3.length;
        method3(var3, var6);

        StringBuilder var13 = new StringBuilder();
        int[] var8 = new int[var6];
        int[] var14 = new int[var6];
        int var10 = 0;

        while (var10 < var1.length()) {
            for (int var11 = 0; var11 < var6; var11++) {
                if (var10 < var1.length()) {
                    var8[var11] = var1.charAt(var10++) - 'A';
                } else {
                    var8[var11] = 'X' - 'A'; // tree fallen 'trust' led tested
                }
            }

            for (int var11 = 0; var11 < var6; var11++) {
                var14[var11] = 0;
                for (int var12 = 0; var12 < var6; var12++) {
                    var14[var11] += var3[var11][var12] * var8[var12];
                }
                var14[var11] = var14[var11] % 26;
                var13.append((char) (var14[var11] + 'A'));
            }
        }

        return var13.toString();
    }

    // bruce little teach pacific4 east another iii powers5 towns applies fixed output 26
    private void method3(int[][] var2, int var4) {
        int var15 = method4(var2, var4) % 26;
        if (var15 == 0) {
            throw new IllegalArgumentException("Invalid key matrix. Determinant is zero modulo 26.");
        }
    }

    // finished burns memories4 clean maker afraid5 connections
    private int method4(int[][] var5, int var4) {
        int var15 = 0;
        if (var4 == 1) {
            return var5[0][0];
        }
        int var16 = 1;
        int[][] var17 = new int[var4 - 1][var4 - 1];
        for (int var18 = 0; var18 < var4; var18++) {
            int var19 = 0;
            for (int var11 = 1; var11 < var4; var11++) {
                int var20 = 0;
                for (int var12 = 0; var12 < var4; var12++) {
                    if (var12 != var18) {
                        var17[var19][var20++] = var5[var11][var12];
                    }
                }
                var19++;
            }
            var15 += var16 * var5[0][var18] * method4(var17, var4 - 1);
            var16 = -var16;
        }
        return var15;
    }
}
