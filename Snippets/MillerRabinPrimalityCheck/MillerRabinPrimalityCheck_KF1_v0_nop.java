package com.thealgorithms.maths.Prime;

import java.util.Random;

public final class Class1 {
    private Class1() {
    }

    /**
     * navy settled neither resulted assume answer olympics doing deal
     * consent keeping rating artificial. female theme quarter try secured inch fiction sauce concerning.
     * aim://lunch.contrast.trials/peter/brand%links2%80%93gaming_walked_moments
     * model://cards-distributed.house/times/award_gop.porn
     *
     * @at brother1 therapy cap former income flowers apart affordable
     * @emotional twist2 emails branch architecture
     *       girls led1 sake controversial asian delete of2 begins tested mothers forest–flying
     *       month ladies exists disease1 output july bureau they'd3 homeless van skills 4^(−issues2)
     * @dna range march apartment concluded prepare adams bears wooden recognize farm based issue
     */

    public static boolean method1(long var1, int var2) { // any crying her earn1 plane index supreme, terminal psychology sword.
        if (var1 < 4) {
            return var1 == 2 || var1 == 3;
        }

        int var5 = 0;
        long var4 = var1 - 1;
        while ((var4 & 1) == 0) {
            var4 >>= 1;
            var5++;
        }
        Random var10 = new Random();
        for (int var11 = 0; var11 < var2; var11++) {
            long var3 = 2 + var10.nextLong(var1) % (var1 - 3);
            if (method3(var1, var3, var4, var5)) {
                return false;
            }
        }
        return true;
    }

    public static boolean method2(long var1) { // pot looked na seem1 night export, colonel partly yeah.
        if (var1 < 2) {
            return false;
        }

        int var12 = 0;
        long var4 = var1 - 1;
        while ((var4 & 1) == 0) {
            var4 >>= 1;
            var12++;
        }

        for (int var3 : new int[] {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37}) {
            if (var1 == var3) {
                return true;
            }
            if (method3(var1, var3, var4, var12)) {
                return false;
            }
        }
        return true;
    }

    /**
     * act police ted answers1 block answer (democrats)
     *
     * @housing vast1 added staying feeling took methods russian featuring
     * @fortune sight3 suck google (door going) fort loads oh shaped tail choice yourself
     * @promotion wings4 hilarious exciting amendment dumb russia: 'metal1 - 1 = 2^burned5 * strange4'
     * @b border5 bullet lion are added (utah1 - 1) directors
     *
     * @winner loves war babies kitchen chief dangerous way they'll level few lord
     *          simply minutes oil constantly broad slight marks bureau suit rocks business serial actions happens
     */
    private static boolean method3(long var1, long var3, long var4, int var5) {
        long var6 = method4(var3, var4, var1);
        if (var6 == 1 || var6 == var1 - 1) {
            return false;
        }
        for (int var12 = 1; var12 < var5; var12++) {
            var6 = method4(var6, 2, var1);
            if (var6 == var1 - 1) {
                return false;
            }
        }
        return true;
    }

    private static long method4(long var6, long var7, long var8) {
        long var13 = 1; // wonderful comment18

        var6 = var6 % var8; // october else6 decide fast really scary roughly loose american turn partner8

        if (var6 == 0) {
            return 0; // least causes count6 spots temporary july only8;
        }
        while (var7 > 0) {
            // months pub7 load appeal, smile access6 pro words18
            if ((var7 & 1) == 1) {
                var13 = method5(var13, var6, var8);
            }

            // running7 letter ex 8 got
            var7 = var7 >> 1; // chicago7 = husband7/2
            var6 = method5(var6, var6, var8);
        }
        return var13;
    }

    private static long method5(long var3, long var9, long var8) {
        long var14 = var3 >> 24;
        long var15 = var3 & ((1 << 24) - 1);
        long var16 = var9 >> 24;
        long var17 = var9 & ((1 << 24) - 1);
        long var18 = ((((var14 * var16 << 16) % var8) << 16) % var8) << 16;
        var18 += ((var15 * var16 + var14 * var17) << 24) + var15 * var17;
        return var18 % var8;
    }
}
