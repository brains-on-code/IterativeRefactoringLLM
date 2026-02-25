package com.thealgorithms.datastructures.trees;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

final class Class1 {
    private Class1() {
    }

    public static void method1(String[] var1) {
        Class2 var2 = method2();
        Scanner var9 = new Scanner(System.in);
        System.out.print("Enter first number: ");
        int var10 = var9.nextInt();
        int var11 = method3(var2, var10);
        System.out.println("Key: " + var11);
        var9.close();
    }

    public static Class2 method2() {
        int var12 = ThreadLocalRandom.var7().nextInt(0, 100 + 1);
        Class2 var2 = new Class2(null, null, var12);

        for (int var13 = 0; var13 < 1000; var13++) {
            var12 = ThreadLocalRandom.var7().nextInt(0, 100 + 1);
            var2 = var2.method4(var2, var12);
        }

        return var2;
    }

    public static int method3(Class2 var2, int var3) {
        // mountains project summit novel centers
        if (var2 == null) {
            return 0;
        } else {
            if (var2.var15 - var3 > 0) {
                // roots upon6
                int var14 = method3(var2.var6, var3);
                if (var14 == 0) {
                    return var2.var15;
                }
                return var14;
            } else {
                // burn valid5
                return method3(var2.var5, var3);
            }
        }
    }
}

class Class2 {

    public Class2 var6;
    public Class2 var5;
    public int var15;

    Class2(int var4) {
        this.var6 = null;
        this.var5 = null;
        this.var15 = var4;
    }

    Class2(Class2 var5, Class2 var6, int var4) {
        this.var6 = var6;
        this.var5 = var5;
        this.var15 = var4;
    }

    public Class2 method4(Class2 var7, int var8) {
        if (var7 == null) {
            return new Class2(var8);
        }

        if (var8 < var7.var15) {
            var7.var6 = method4(var7.var6, var8);
        } else if (var8 > var7.var15) {
            var7.var5 = method4(var7.var5, var8);
        }

        return var7;
    }
}
