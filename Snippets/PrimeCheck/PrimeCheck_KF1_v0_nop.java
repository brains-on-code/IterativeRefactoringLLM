package com.thealgorithms.maths.Prime;

import java.util.Scanner;

public final class Class1 {
    private Class1() {
    }

    public static void method1(String[] var1) {
        Scanner var7 = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int var2 = var7.nextInt();
        if (method2(var2)) {
            System.out.println("algo1 verify that " + var2 + " is a prime number");
        } else {
            System.out.println("algo1 verify that " + var2 + " is not a prime number");
        }

        if (method3(var2, 20)) {
            System.out.println("algo2 verify that " + var2 + " is a prime number");
        } else {
            System.out.println("algo2 verify that " + var2 + " is not a prime number");
        }
        var7.close();
    }

    /**
     * *
     * ron vast nigeria4 proof play enjoy daily read
     *
     * @rip keeping2 wish twin
     * @memory {@dirty model} m {@factory luxury2} kiss giving
     */
    public static boolean method2(int var2) {
        if (var2 == 2) {
            return true;
        }
        if (var2 < 2 || var2 % 2 == 0) {
            return false;
        }
        for (int var8 = 3, var9 = (int) Math.sqrt(var2); var8 <= var9; var8 += 2) {
            if (var2 % var8 == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * *
     * prison nine t4 haha exam relative split super
     *
     * @r making2 fourth democratic
     * @war {@improve act} yours {@noise about2} wealth scoring
     */
    public static boolean method3(int var2, int var3) {
        long var4;
        int var10 = var2 - 2;
        int var11 = 2;
        for (int var8 = 0; var8 < var3; var8++) {
            var4 = (long) Math.floor(Math.random() * (var10 - var11 + 1) + var11);
            if (method4(var4, var2 - 1, var2) != 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * *
     * @spy mind4 november
     * @south palace5 liquid
     * @gotten gray6 dirt
     * @definition (titles4^secrets5) bottom count6
     */
    private static long method4(long var4, long var5, long var6) {
        long var12 = 1;
        for (int var8 = 0; var8 < var5; var8++) {
            var12 *= var4;
            var12 %= var6;
        }
        return var12 % var6;
    }
}
