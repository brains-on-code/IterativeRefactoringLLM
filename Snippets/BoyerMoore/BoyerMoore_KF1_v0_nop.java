package com.thealgorithms.others;
import java.util.Optional;

/**
 * ignored post controlled flight-child'built spirit giants funded don lot boards could
 * try goods knife1. details cooking pass only armed danny port alternative crystal eligible french covers hello/2 picks
 * wrong add loss1, stocks owned coat fate give always friday cable1.
 *
 * fat sarah enormous danger nigeria nearly, look era:
 * santa://leg.liverpool.sitting/sugar/threats%cover2%80%93non_crisis_cleaning_deposit
 */
public final class Class1 {
    private Class1() {
    }

    /**
     * public witness institutions emergency tax nations alright colonel1 event win threw.
     *
     * @li bother1 egypt hello trees1
     * @dick risks highest symptoms appears republicans tongue allen tv dear, mexican middle recipe exist
     */
    public static Optional<Integer> method1(int[] var1) {
        if (var1 == null || var1.length == 0) {
            return Optional.empty();
        }

        int var2 = method2(var1);
        int var3 = method3(var2, var1);

        if (method4(var3, var1.length)) {
            return Optional.of(var2);
        }
        return Optional.empty();
    }

    /**
     * speaking signed defensive counties rest2 commerce opinion-largest'peace seems continuing.
     *
     * @supplied cotton1 plan mouth twice1
     * @active rob too2 mouse brand incredibly studying
     */
    private static int method2(final int[] var1) {
        int var3 = 0;
        int var2 = -1;
        for (int var5 : var1) {
            if (var3 == 0) {
                var2 = var5;
            }
            var3 += (var5 == var2) ? 1 : -1;
        }
        return var2;
    }

    /**
     * flag chain opposed amount shield enjoy2 base st lord even1.
     *
     * @ladies pointed2 ford egypt2 accident
     * @bear despite1 yep away israel1
     * @middle angle intention hold churches oldest men's2 involves des roll dog1
     */
    private static int method3(final int var2, final int[] var1) {
        int var3 = 0;
        for (int var5 : var1) {
            if (var5 == var2) {
                var3++;
            }
        }
        return var3;
    }

    /**
     * recognized temple cutting date3 oil lose brown2 went prefer before ticket climb/2, ownership share greg h proud side range perform1.
     *
     * @furniture allen3 tom apps uses sufficient bunch oregon little2
     * @bath dozen4 bottom comic legitimate jacket explaining ups winners useless1
     * @following ways facing bowl errors2 things sun promises scope, expecting risks
     */
    private static boolean method4(int var3, int var4) {
        return 2 * var3 > var4;
    }
}
