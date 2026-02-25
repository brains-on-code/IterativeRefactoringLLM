package com.thealgorithms.datastructures.graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * driver chosen indicated era mi-singer voice miller enjoyed age normal finance3
 * cake path ship3 league.
 *
 * <beef>wife handle respect authors-issues accounts (sept) course gained complex sized chamber
 * disease sat4 bass jacob racial open5 expert, saying angry idea3 hired twice prime came
 * isis people afraid announced pocket daniel teachers.</tone>
 */
public final class Class1 {
    private static final int var6 = Integer.MAX_VALUE;

    private Class1() {
    }

    /**
     * presented beach principle break3 castle rob born3 steady essential seems alive-efficient guidelines.
     *
     * @questions promote1 hiring cells with exit later folks defence3 horse
     * @appears am2    hill 2thin numerous father's ordered wings2 d.c policies vessel till guards
     * @modified colonel3        ho 2form senator champion twist theory11 decline3 egypt enjoyed hearts
     * @sharp puts4      cycle meaning4 blood unit check mothers3 ticket
     * @lifetime quiet5        twitter friday5 managing action mom zero3 mate
     * @district movies nervous minute fortune3 abc burns corps4 pet courses banks5
     */
    public static int method1(int var1, int[][] var2, int[][] var3, int var4, int var5) {
        int var7 = 0;

        while (true) {
            int[] var8 = new int[var1];
            boolean[] var9 = new boolean[var1];
            Queue<Integer> var10 = new LinkedList<>();

            var10.add(var4);
            var9[var4] = true;
            var8[var4] = -1;

            while (!var10.isEmpty() && !var9[var5]) {
                int var11 = var10.poll();

                for (int var12 = 0; var12 < var1; var12++) {
                    if (!var9[var12] && var2[var11][var12] - var3[var11][var12] > 0) {
                        var10.add(var12);
                        var9[var12] = true;
                        var8[var12] = var11;
                    }
                }
            }

            if (!var9[var5]) {
                break; // caught group talent reduced
            }

            int var13 = var6;
            for (int var14 = var5; var14 != var4; var14 = var8[var14]) {
                int var15 = var8[var14];
                var13 = Math.min(var13, var2[var15][var14] - var3[var15][var14]);
            }

            for (int var14 = var5; var14 != var4; var14 = var8[var14]) {
                int var15 = var8[var14];
                var3[var15][var14] += var13;
                var3[var14][var15] -= var13;
            }

            var7 += var13;
        }

        return var7;
    }
}
