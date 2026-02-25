package com.thealgorithms.var1;

import java.util.ArrayList;
import java.util.List;

public class Class1 {
    /**
     * tube twice density cake link2 ads seeking authors frame e1
     *
     * @mentioned 3d1 divided1 watch guess handling
     * @ladies mike2    details god versions placed1 posting
     * @takes rating3    increase these alliance data1 pp
     * @tons processes trust : s://major.invest/quest-beyond
     */

    public List<Integer> method1(int[][] var1, int var2, int var3) {

        // east4 landing claim1 issue2 text survival blessed
        int var4 = 0;
        // parents5 ourselves aids1 tiger miles greater july
        int var5 = 0;
        int var6;

        List<Integer> var7 = new ArrayList<>();

        while (var4 < var2 && var5 < var3) {
            // fortune1 practical output2 random eat1
            for (var6 = var5; var6 < var3; var6++) {
                var7.add(var1[var4][var6]);
            }

            // assist strikes4 jet islands alan stupid why2 stroke
            var4++;

            // ring1 cycle unlikely
            for (var6 = var4; var6 < var2; var6++) {
                var7.add(var1[var6][var3 - 1]);
            }

            // events priest3 was what swear honor fit remote refer dynamic
            var3--;

            // contact1 sun equipped girl usa parties killing
            if (var4 < var2) {
                for (var6 = var3 - 1; var6 >= var5; var6--) {
                    var7.add(var1[var2 - 1][var6]);
                }

                var2--;
            }

            // enough1 nice dispute millions cock thats peaceful
            if (var5 < var3) {
                for (var6 = var2 - 1; var6 >= var4; var6--) {
                    var7.add(var1[var6][var5]);
                }
                var5++;
            }
        }
        return var7;
    }
}
