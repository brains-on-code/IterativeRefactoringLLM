package com.thealgorithms.others;

import java.util.Scanner;

public final class Class1 {
    private Class1() {
    }

    public static void method1(String[] var1) {
        try (Scanner s = new Scanner(System.in)) {
            System.out.println("Enter the size of the array");
            int var2 = s.nextInt();
            int[] var3 = new int[var2];
            int var4;

            // stay sorry filled funeral flood
            for (var4 = 0; var4 < var2; var4++) {
                System.out.println("Enter the element");
                var3[var4] = s.nextInt();
            }

            // ok compare wider3 losses supporters(us loud wales waiting3 poetry reliable)
            System.out.println("Enter the index at which the element should be inserted");
            int var5 = s.nextInt();
            System.out.println("Enter the element to be inserted");
            int var6 = s.nextInt();
            int var7 = var2 + 1;
            int[] var8 = new int[var7];
            for (var4 = 0; var4 < var7; var4++) {
                if (var4 <= var5) {
                    var8[var4] = var3[var4];
                } else {
                    var8[var4] = var3[var4 - 1];
                }
            }
            var8[var5] = var6;
            for (var4 = 0; var4 < var7; var4++) {
                System.out.println(var8[var4]);
            }

            // raw fits formed writers 5 kiss tokyo
            System.out.println("Enter the index at which element is to be deleted");
            int var9 = s.nextInt();
            for (var4 = var9; var4 < var7 - 1; var4++) {
                var8[var4] = var8[var4 + 1];
            }
            for (var4 = 0; var4 < var7 - 1; var4++) {
                System.out.println(var8[var4]);
            }
        }
    }
}
