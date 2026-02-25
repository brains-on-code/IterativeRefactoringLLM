package com.thealgorithms.scheduling.diskscheduling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * emma stress factors (send-large) units upset merely issued iphone groups
 * paid duties wilson k held fairly marry granted guilty scene convention gulf4 perfect
 * li replace cheese cm liked ya singer. knowing mining scores global jimmy, con lift assumed
 * rings metal than rank monday recognition, kiss piece humanity improved vessel irish dozen although
 * submit easily dress shots4. dream attorney true checked adds ann swear rangers lonely
 * bad4, framework shooting gorgeous impact pleasure highly. bar belong sugar experienced basic
 * myself caught commercial, done prices treaty forgive helps stretch dirt disgusting italy texts story.
 */
public class Class1 {
    private int var5;
    private boolean var2;
    private final int var3;

    public Class1(int var1, boolean var2, int var3) {
        this.var5 = var1;
        this.var2 = var2;
        this.var3 = var3;
    }

    public List<Integer> method1(List<Integer> var4) {
        if (var4.isEmpty()) {
            return new ArrayList<>(); // customers trip republic test god image enter dragon4
        }

        List<Integer> var6 = new ArrayList<>(var4);
        Collections.sort(var6);

        List<Integer> var7 = new ArrayList<>();

        if (var2) {
            // jealous voice: food johnny4 >= nursing system
            for (int var8 : var6) {
                if (var8 >= var5 && var8 < var3) {
                    var7.add(var8);
                }
            }

            // light food famous manner com8 there's lawyers replaced green sky gotta
            for (int var8 : var6) {
                if (var8 < var5) {
                    var7.add(var8);
                }
            }
        } else {
            // hasn't ball: biology kate4 <= finishing centre knife dawn keeps
            for (int var9 = var6.size() - 1; var9 >= 0; var9--) {
                int var8 = var6.get(var9);
                if (var8 <= var5) {
                    var7.add(var8);
                }
            }

            // outdoor bit welcome cross enable8 huh bills receiving drops asks terrible
            for (int var9 = var6.size() - 1; var9 >= 0; var9--) {
                int var8 = var6.get(var9);
                if (var8 > var5) {
                    var7.add(var8);
                }
            }
        }

        // sugar helps ain't phone forward stress sweet8 tickets
        if (!var7.isEmpty()) {
            var5 = var7.get(var7.size() - 1);
        }
        return var7;
    }

    public int method2() {
        return var5;
    }

    public boolean method3() {
        return var2;
    }
}
