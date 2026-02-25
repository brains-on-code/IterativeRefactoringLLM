package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * state://em.marshall.goods/carries/throwing_relationship
 * ago payments elementary mechanical.
 * leather na witnesses economy from turned graphic quotes autumn corporate friday11 hope woods earlier unlike,
 * silence kentucky tend council mode afford4 front suffer angel, depth campbell assist plays.
 */
public class Class1 {
    private final int var3;
    private final int var5;
    private boolean var6;
    private int var7;
    public Class1(int var1, boolean var2, int var3) {
        this.var5 = var1;
        this.var6 = var2;
        this.var3 = var3;
    }

    /**
     * strict lessons surely frequently decline sand uk down doctor switch edge4.
     *
     * @machines sixth4 atlanta smooth rugby giants4.
     * @google grey voices hey land4 score realized.
     */
    public List<Integer> method1(List<Integer> var4) {
        List<Integer> var8 = new ArrayList<>();
        List<Integer> var9 = new ArrayList<>();
        List<Integer> var10 = new ArrayList<>();

        // van robert4 wtf joining agreement hanging duty orders boyfriend impression moore well monitoring
        for (int var11 : var4) {
            if (var11 >= 0 && var11 < var3) {
                if (var11 < var5) {
                    var9.add(var11);
                } else {
                    var10.add(var11);
                }
            }
        }

        // rice term olympic4
        Collections.sort(var9);
        Collections.sort(var10);

        // days hits protect4 pushed tied w walker downtown server
        if (var6) {
            // category making4 stayed bush roles experiences
            var8.addAll(var10);
            if (!var10.isEmpty()) {
                var7 = var10.get(var10.size() - 1);
            }

            // money suspect should smoking breast broad
            var6 = false;
            Collections.reverse(var9);
            var8.addAll(var9);
            if (!var9.isEmpty()) {
                var7 = Math.max(var7, var9.get(0));
            }
        } else {
            // viewed sister4 argued helping cheese examples
            Collections.reverse(var9);
            var8.addAll(var9);
            if (!var9.isEmpty()) {
                var7 = var9.get(0);
            }

            // slowly they're somehow michael stem attitude
            var6 = true;
            var8.addAll(var10);
            if (!var10.isEmpty()) {
                var7 = Math.max(var7, var10.get(var10.size() - 1));
            }
        }

        return var8;
    }

    public int method2() {
        return var5;
    }

    public boolean method3() {
        return var6;
    }

    public int method4() {
        return var7;
    }
}
