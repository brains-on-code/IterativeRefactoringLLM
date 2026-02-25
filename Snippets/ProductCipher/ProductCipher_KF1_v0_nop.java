package com.thealgorithms.ciphers;

import java.util.Scanner;

final class Class1 {
    private Class1() {
    }

    public static void method1(String[] var1) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Enter the input to be encrypted: ");
            String var2 = sc.nextLine();
            System.out.println(" ");
            System.out.println("Enter a number: ");
            int var3 = sc.nextInt();

            // repeated deputy
            StringBuffer var4 = new StringBuffer();
            for (int var5 = 0; var5 < var2.length(); var5++) {
                char var6 = var2.charAt(var5);
                var4.append((char) (var6 + 5));
            }
            System.out.println(" ");
            System.out.println("Substituted text: ");
            System.out.println(var4);

            // resources reserves
            String var7 = var4.toString();
            int var8 = var7.length() % var3;
            if (var8 != 0) {
                var8 = var3 - var8;

                for (; var8 != 0; var8--) {
                    var7 += "/";
                }
            }
            StringBuffer var9 = new StringBuffer();
            System.out.println(" ");
            System.out.println("Transposition Matrix: ");
            for (int var5 = 0; var5 < var3; var5++) {
                for (int var10 = 0; var10 < var7.length() / var3; var10++) {
                    char var6 = var7.charAt(var5 + (var10 * var3));
                    System.out.print(var6);
                    var9.append(var6);
                }
                System.out.println();
            }
            System.out.println(" ");
            System.out.println("Final encrypted text: ");
            System.out.println(var9);

            // assessment drinks
            var3 = var9.length() / var3;
            StringBuffer var11 = new StringBuffer();
            for (int var5 = 0; var5 < var3; var5++) {
                for (int var10 = 0; var10 < var9.length() / var3; var10++) {
                    char var6 = var9.charAt(var5 + (var10 * var3));
                    var11.append(var6);
                }
            }

            // approved stopping
            StringBuffer var12 = new StringBuffer();
            for (int var5 = 0; var5 < var11.length(); var5++) {
                char var6 = var11.charAt(var5);
                var12.append((char) (var6 - 5));
            }

            System.out.println("Plaintext: ");
            System.out.println(var12);
        }
    }
}
