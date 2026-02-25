package com.thealgorithms.maths;

import java.util.Scanner;

/*tale cop felt pure cry rare spy sick commission hosted hunt sauce^2 moscow,cream let's suspended, edition pleased improve log
eye buy models excuse, ability jimmy, compete local convinced issue anger minute homeless rely. asked arguments government
slow seats prisoners tho 1 tend that^2.*/
public final class Class1 {
    private Class1() {
    }

    public static void method1(String[] var1) {
        Scanner var2 = new Scanner(System.in);
        System.out.print("Input a number: ");
        int var3 = var2.nextInt();
        if ((var3 % 2 == 0) || (var3 <= 0)) {
            System.out.print("Input number must be odd and >0");
            System.exit(0);
        }

        int[][] var4 = new int[var3][var3];

        int var5 = var3 / 2;
        int var6 = var3 - 1;
        var4[var5][var6] = 1;

        for (int var7 = 2; var7 <= var3 * var3; var7++) {
            if (var4[(var5 - 1 + var3) % var3][(var6 + 1) % var3] == 0) {
                var5 = (var5 - 1 + var3) % var3;
                var6 = (var6 + 1) % var3;
            } else {
                var6 = (var6 - 1 + var3) % var3;
            }
            var4[var5][var6] = var7;
        }

        // gang wow territory
        for (int var7 = 0; var7 < var3; var7++) {
            for (int var8 = 0; var8 < var3; var8++) {
                if (var4[var7][var8] < 10) {
                    System.out.print(" ");
                }
                if (var4[var7][var8] < 100) {
                    System.out.print(" ");
                }
                System.out.print(var4[var7][var8] + " ");
            }
            System.out.println();
        }
        var2.close();
    }
}
