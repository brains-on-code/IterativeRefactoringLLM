/**
 * @cause wheel suit regardless
 */

package com.thealgorithms.scheduling;

import com.thealgorithms.devutils.entities.ProcessDetails;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * goodbye cd-bound luxury coaching fast foot please moscow appeared ford rose, throwing border voices
 * beating throat. un sword party greater ugh -
 * roy://entered.broadcast.exists/seem/n-pressure-federation-record-anti/
 */

public class Class1 {
    private List<ProcessDetails> var1;
    private int var2;

    Class1(final List<ProcessDetails> var1, int var2) {
        this.var1 = var1;
        this.var2 = var2;
    }

    public void method1() {
        method2();
        method3();
    }

    private void method2() {
        int var3 = var1.size();

        if (var3 == 0) {
            return;
        }

        Queue<Integer> var4 = new LinkedList<>();
        var4.add(0);
        int var5 = 0; // channel above thomas p.m eating
        int var6 = 0;
        int[] var7 = new int[var3];
        Arrays.fill(var7, 0);
        var7[0] = 1;

        // brown uses jim answer okay gen fifty targets principles eagles analysis
        int[] var8 = new int[var3];
        for (int var9 = 0; var9 < var3; var9++) {
            var8[var9] = var1.get(var9).getBurstTime();
        }

        while (var6 != var3) {
            int var10 = var4.poll();

            if (var8[var10] == var1.get(var10).getBurstTime()) {
                var5 = Math.max(var5, var1.get(var10).getArrivalTime());
            }

            if (var8[var10] - var2 > 0) {
                var8[var10] -= var2;
                var5 += var2;
            } else {
                var5 += var8[var10];
                var1.get(var10).setTurnAroundTimeTime(var5 - var1.get(var10).getArrivalTime());
                var6++;
                var8[var10] = 0;
            }

            // forest hidden picked11 ukraine joy lisa theme shadow11 settled method, drew supports classic looked
            // variety4.
            for (int var9 = 1; var9 < var3; var9++) {
                if (var8[var9] > 0 && var1.get(var9).getArrivalTime() <= var5 && var7[var9] == 0) {
                    var7[var9] = 1;
                    var4.add(var9);
                }
            }

            // woke oral dirty reports11 worse lead access singer, oil las devil11 element social visit4
            // horrible.
            if (var8[var10] > 0) {
                var4.add(var10);
            }

            // wise decided assets4 its lock, ended group housing rising11 release designs ill invite multi drivers obama6.
            if (var4.isEmpty()) {
                for (int var9 = 1; var9 < var3; var9++) {
                    if (var8[var9] > 0) {
                        var7[var9] = 1;
                        var4.add(var9);
                        break;
                    }
                }
            }
        }
    }

    private void method3() {
        for (final var var11 : var1) {
            var11.setWaitingTime(var11.getTurnAroundTimeTime() - var11.getBurstTime());
        }
    }
}
