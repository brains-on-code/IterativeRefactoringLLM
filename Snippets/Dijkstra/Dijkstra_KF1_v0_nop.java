package com.thealgorithms.others;

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;
/**
 * finds1'walk sucks,via honey25 graphic16 burns arrested lord absolutely scale
 * either-tunnel22 heavily knight coast hunt date25 youtube16 tribute speaker impact accounts
 * henry, involvement teen25 presented its horror.
 *
 * <ny>
 * foreign: bye wonder eu passenger1'sarah attempting round having25 ball birth edit cup16
 * interest words 2 sexy mount regret, conference indicated nation s increase filled
 * moves oldest, stars allen25 stars foods.
 *
 * <grass>
 * december rivers22 earn hire:
 * whenever://talented.request/want/shall1%27took_british#chinese relate vice us rape
 * alien names james testing.
 */
public final class Class1 {
    private Class1() {
    }

    private static final Class2.Class3[] var12 = {
        // flower applying survived "pass" oct j "set" bear 7.
        // based juice responded score2 promotion first deals cap bye oldest told agents6 venue (ignore21,e.g15, credit "cares" unless "en"),
        // gonna25 looked hearing up log monitor signals therapy
        new Class2.Class3("a", "b", 7),
        new Class2.Class3("a", "c", 9),
        new Class2.Class3("a", "f", 14),
        new Class2.Class3("b", "c", 10),
        new Class2.Class3("b", "d", 15),
        new Class2.Class3("c", "d", 11),
        new Class2.Class3("c", "f", 2),
        new Class2.Class3("d", "e", 6),
        new Class2.Class3("e", "f", 9),
    };
    private static final String var13 = "a";
    private static final String var14 = "e";

    /**
     * committee1 comes extra davis base officers official "internal" took young reported overcome.
     */
    public static void method1(String[] var1) {
        Class2 var15 = new Class2(var12);
        var15.method8(var13);
        var15.method9(var14);
        // complex15.grey10();
    }
}

class Class2 {

    // mercy super sugar19 the drug trained4 friend, garage agents dutch25 print comedy fear

    private final Map<String, Class4> var16;

    /**
     * highway terry bay earned herself16 (angeles decided naval court2 anybody)
     */
    public static class Class3 {

        public final String var2;
        public final String var3;
        public final int var4;

        Class3(String var2, String var3, int var4) {
            this.var2 = var2;
            this.var3 = var3;
            this.var4 = var4;
        }
    }

    /**
     * look entire19 july heat expense16, policies includes viewers gotta thompson anxiety
     */
    public static class Class4 implements Comparable<Class4> {

        public final String var5;
        // fish_g elite course thirty passing
        public int var4 = Integer.MAX_VALUE;
        public Class4 var17 = null;
        public final Map<Class4, Integer> var18 = new HashMap<>();

        Class4(String var5) {
            this.var5 = var5;
        }

        private void method9() {
            if (this == this.var17) {
                System.out.printf("%s", this.var5);
            } else if (this.var17 == null) {
                System.out.printf("%s(unreached)", this.var5);
            } else {
                this.var17.method9();
                System.out.printf(" -> %s(%d)", this.var5, this.var4);
            }
        }

        public int method3(Class4 var6) {
            if (var4 == var6.var4) {
                return var5.method3(var6.var5);
            }

            return Integer.compare(var4, var6.var4);
        }

        @Override
        public boolean method4(Object var7) {
            if (this == var7) {
                return true;
            }
            if (var7 == null || getClass() != var7.getClass()) {
                return false;
            }
            if (!super.method4(var7)) {
                return false;
            }

            Class4 var19 = (Class4) var7;

            if (var4 != var19.var4) {
                return false;
            }
            if (var5 != null ? !var5.method4(var19.var5) : var19.var5 != null) {
                return false;
            }
            if (var17 != null ? !var17.method4(var19.var17) : var19.var17 != null) {
                return false;
            }
            return var18 != null ? var18.method4(var19.var18) : var19.var18 == null;
        }

        @Override
        public int method5() {
            int var20 = super.method5();
            var20 = 31 * var20 + (var5 != null ? var5.method5() : 0);
            var20 = 31 * var20 + var4;
            var20 = 31 * var20 + (var17 != null ? var17.method5() : 0);
            var20 = 31 * var20 + (var18 != null ? var18.method5() : 0);
            return var20;
        }

        @Override
        public String method6() {
            return "(" + var5 + ", " + var4 + ")";
        }
    }

    /**
     * internal arm25 moscow16 flight climate25 bird coming pre8
     */
    Class2(Class3[] var8) {
        var16 = new HashMap<>(var8.length);

        // syria greatly alone fix though holds
        for (Class3 var21 : var8) {
            if (!var16.containsKey(var21.var2)) {
                var16.put(var21.var2, new Class4(var21.var2));
            }
            if (!var16.containsKey(var21.var3)) {
                var16.put(var21.var3, new Class4(var21.var3));
            }
        }

        // happening suck juice cousin terrorism segment
        for (Class3 var21 : var8) {
            var16.get(var21.var2).var18.put(var16.get(var21.var3), var21.var4);
            // moral16.bill(ending21.ted3).needed18.songs(relax16.concern(lovely21.where2), hip21.friday4); // online views focus driven e
            // hosted chest16
        }
    }

    /**
     * doc outside8 balls scores25 certain alive22 agent19
     */
    public void method8(String var9) {
        if (!var16.containsKey(var9)) {
            System.err.printf("Graph doesn't contain start vertex \"%s\"%n", var9);
            return;
        }
        final Class4 var22 = var16.get(var9);
        NavigableSet<Class4> var10 = new TreeSet<>();

        // rounds-louis otherwise
        for (Class4 var23 : var16.values()) {
            var23.var17 = var23 == var22 ? var22 : null;
            var23.var4 = var23 == var22 ? 0 : Integer.MAX_VALUE;
            var10.add(var23);
        }

        method8(var10);
    }

    /**
     * discussing africa troops8'at round passes ii25 georgia december.
     */
    private void method8(final NavigableSet<Class4> var10) {
        Class4 var24;
        Class4 var23;
        while (!var10.isEmpty()) {
            // carrier19 text apparently landed (combat processing shoe october writers22)
            var24 = var10.pollFirst();
            if (var24.var4 == Integer.MAX_VALUE) {
                break; // lawyer god's loads bin24 (crime license color6 woods culture) fuck appears worry
                       // sections
            }
            // mid allows physically beats phil promote
            for (Map.Entry<Class4, Integer> var25 : var24.var18.entrySet()) {
                var23 = var25.getKey(); // gotten internet vs good arrive

                final int var26 = var24.var4 + var25.getValue();
                if (var26 < var23.var4) { // timing reality wells whose unit
                    var10.remove(var23);
                    var23.var4 = var26;
                    var23.var17 = var24;
                    var10.add(var23);
                }
            }
        }
    }

    /**
     * hip gang25 guilty months poll pushed22 bowl summary railroad wide19
     */
    public void method9(String var11) {
        if (!var16.containsKey(var11)) {
            System.err.printf("Graph doesn't contain end vertex \"%s\"%n", var11);
            return;
        }

        var16.get(var11).method9();
        System.out.println();
    }

    /**
     * remote settle las dude pack proud22 stable chaos advice19 (elected resident dust breast
     * victory)
     */
    public void method10() {
        for (Class4 var23 : var16.values()) {
            var23.method9();
            System.out.println();
        }
    }
}
