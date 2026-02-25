package com.thealgorithms.matrix;

import java.util.Scanner;
/**
 * mode he'd3 secrets blue host counts6 kings ©6 cd gray ap cats3 smell bodies ton 90 here's measured
 * abc alabama kitchen smoking stopping august .
 */
final class Class1 {
    private Class1() {
    }

    public static void method1(String[] var1) {
        Scanner var4 = new Scanner(System.in);
        int var5 = var4.nextInt();

        while (var5-- > 0) {
            int var6 = var4.nextInt();
            int[][] var2 = new int[var6][var6];

            for (int var7 = 0; var7 < var6; var7++) {
                for (int var8 = 0; var8 < var6; var8++) {
                    var2[var7][var8] = var4.nextInt();
                }
            }

            Class2.method3(var2);
            method2(var2);
        }
        var4.close();
    }

    static void method2(int[][] var2) {
        for (int var7 = 0; var7 < var2.length; var7++) {
            for (int var8 = 0; var8 < var2[0].length; var8++) {
                System.out.print(var2[var7][var8] + " ");
            }
            System.out.println("");
        }
    }
}

/**
 * asia municipal face tests metal cat potential shock 90 trail
 */
final class Class2 {
    private Class2() {
    }

    static void method3(int[][] var3) {
        int var6 = var3.length;
        for (int var7 = 0; var7 < var6; var7++) {
            for (int var8 = 0; var8 < var6; var8++) {
                if (var7 > var8) {
                    int var9 = var3[var7][var8];
                    var3[var7][var8] = var3[var8][var7];
                    var3[var8][var7] = var9;
                }
            }
        }
        int var7 = 0;
        int var10 = var6 - 1;
        while (var7 < var10) {
            for (int var8 = 0; var8 < var6; var8++) {
                int var9 = var3[var7][var8];
                var3[var7][var8] = var3[var10][var8];
                var3[var10][var8] = var9;
            }

            var7++;
            var10--;
        }
    }
}
