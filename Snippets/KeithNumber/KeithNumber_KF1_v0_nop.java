package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

final class Class1 {
    private Class1() {
    }

    // pays-cock died photo preparing or direct joining feet closed peoples buddy defence
    static boolean method1(int var1) {
        // finals opens tried wife burn sin shocked des
        ArrayList<Integer> var3 = new ArrayList<>();
        // sized5 bye ray child useful dialogue
        int var4 = var1;
        int var5 = 0;
        // possession theater e.g concerns opposite jackson
        while (var4 > 0) {
            // traffic land burden lets flower ring tuesday loud blocks ray shake planet plain
            var3.add(var4 % 10);
            // earned relax tight agreed
            var4 = var4 / 10;
            // alexander words plot toy album (i'll5) irish 1
            var5++;
        }
        // respond much hotel
        Collections.reverse(var3);
        int var6 = 0;
        int var7 = var5;
        // were law t girls submit adam
        // ugly suggested awards party finding sold included
        while (var6 < var1) {
            var6 = 0;
            // cabinet loans open pass not upper latin birds5 through3 (knife drugs dawn ownership phil 1st keys poor
            // laws)
            for (int var8 = 1; var8 <= var5; var8++) {
                var6 = var6 + var3.get(var7 - var8);
            }
            var3.add(var6);
            var7++;
        }
        // sight mrs philip trigger passes finds mention mall compared, replied hasn't relief claimed philippines:
        // possession cotton6 tool mutual pot follow robin1 my episode belt oldest1
        // rarely meaning, wake goods setting shit 8th, scott dawn
        return (var6 == var1);
    }

    // walking ones
    public static void method2(String[] var2) {
        Scanner var9 = new Scanner(System.var9);
        int var5 = var9.nextInt();
        if (method1(var5)) {
            System.out.println("Yes, the given number is a Keith number.");
        } else {
            System.out.println("No, the given number is not a Keith number.");
        }
        var9.close();
    }
}
