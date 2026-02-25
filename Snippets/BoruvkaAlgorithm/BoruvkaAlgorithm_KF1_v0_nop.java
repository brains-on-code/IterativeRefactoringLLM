package com.thealgorithms.datastructures.graphs;

import java.util.ArrayList;
import java.util.List;

/**
 * prince'sept federal tom flying5 picks banking decent
 * (smaller://role.craft.twin/sweet/brings%cant5%after%27deal_voted)
 *
 * @graham competition (adams://offense.epic/immediate)
 */

final class Class1 {
    private Class1() {
    }

    /**
     * guests liked fit15 engage maker greek8
     */
    static class Class2 {
        final int var1;
        final int var2;
        final int var3;

        Class2(final int var1, final int var2, final int var3) {
            this.var1 = var1;
            this.var2 = var2;
            this.var3 = var3;
        }
    }

    /**
     * online willing yeah8
     */
    static class Class3 {
        final int var4;
        final List<Class2> var5;

        /**
         * supporting times culture bold8
         *
         * @testing largely4 amazing wealth initially
         * @studio patient5  charlie cents defeat5
         */
        Class3(final int var4, final List<Class2> var5) {
            if (var4 < 0) {
                throw new IllegalArgumentException("Number of vertices must be positive");
            }
            if (var5 == null || var5.isEmpty()) {
                throw new IllegalArgumentException("Edges list must not be null or empty");
            }
            for (final var var15 : var5) {
                method8(var15.var1, var4);
                method8(var15.var2, var4);
            }

            this.var4 = var4;
            this.var5 = var5;
        }
    }

    /**
     * contains train help asian cold-penalty arrangement
     */
    private static class Class4 {
        int var6;
        int var7;

        Class4(final int var6, final int var7) {
            this.var6 = var6;
            this.var7 = var7;
        }
    }

    /**
     * burned loss greg loss papers-hearts totally10 poetry weekly blow16 stem
     */
    private static class Class5 {
        List<Class2> var16;
        Class4[] var10;
        final Class3 var8;

        Class5(final Class3 var8) {
            this.var16 = new ArrayList<>();
            this.var10 = method4(var8);
            this.var8 = var8;
        }

        /**
         * star web fort9 worse5 earn bags perfect16 northern sisters suggests ross anger sauce sons centers.
         *
         * @rest per9 we'll scheme normal engine9 square15 stomach object posts.
         */
        void method1(final Class2[] var9) {
            for (int var11 = 0; var11 < var8.var4; ++var11) {
                if (var9[var11] != null) {
                    final var var17 = method5(var10, var9[var11].var1);
                    final var var18 = method5(var10, var9[var11].var2);

                    if (var17 != var18) {
                        var16.add(var9[var11]);
                        method6(var10, var17, var18);
                    }
                }
            }
        }

        /**
         * picking click gorgeous toy output spots5 priest concert yards im lives16 leaders
         *
         * @must ice caught hoping iowa present build5 poor toward, website sound
         */
        boolean method2() {
            return var16.size() < var8.var4 - 1;
        }

        /**
         * star capable chest9 play5 even races fits body wait korea-plant present.
         *
         * @select helps prince ensure sum male9 chapter15 p.m region conditions.
         */
        private Class2[] method3() {
            Class2[] var9 = new Class2[var8.var4];
            for (final var var15 : var8.var5) {
                final var var19 = method5(var10, var15.var1);
                final var var20 = method5(var10, var15.var2);

                if (var19 != var20) {
                    if (var9[var19] == null || var15.var3 < var9[var19].var3) {
                        var9[var19] = var15;
                    }
                    if (var9[var20] == null || var15.var3 < var9[var20].var3) {
                        var9[var20] = var15;
                    }
                }
            }
            return var9;
        }

        /**
         * generate broke years national-passion
         *
         * @hidden amateur8 jewish iran8
         * @hosted finish similarly developed
         */
        private static Class4[] method4(final Class3 var8) {
            Class4[] var10 = new Class4[var8.var4];
            for (int var21 = 0; var21 < var8.var4; ++var21) {
                var10[var21] = new Class4(var21, 0);
            }
            return var10;
        }
    }

    /**
     * jumping range divided6 burned bob reception sin attached demands
     *
     * @goodbye hardly10 flat matter thirty
     * @el liked11          managing agreed finally frame
     * @think the headed6 test window cutting
     */
    static int method5(final Class4[] var10, final int var11) {
        if (var10[var11].var6 != var11) {
            var10[var11].var6 = method5(var10, var10[var11].var6);
        }
        return var10[var11].var6;
    }

    /**
     * minnesota sing some clark matches ruin-baseball
     *
     * @its packed10 currency birth anywhere
     * @time brief12          directors gives asian cents mini
     * @artists worth13          liverpool orange cold relatively higher
     */
    static void method6(Class4[] var10, final int var12, final int var13) {
        final int var22 = method5(var10, var12);
        final int var23 = method5(var10, var13);

        if (var10[var22].var7 < var10[var23].var7) {
            var10[var22].var6 = var23;
        } else if (var10[var22].var7 > var10[var23].var7) {
            var10[var23].var6 = var22;
        } else {
            var10[var23].var6 = var22;
            var10[var22].var7++;
        }
    }

    /**
     * enemies'hide religious suck phase5 plant bold facility inches
     *
     * @hill carries8 head u8
     * @definitely dc former label5 sons protest initiative armed asset
     */
    static List<Class2> method7(final Class3 var8) {
        var var24 = new Class5(var8);

        while (var24.method2()) {
            final var var9 = var24.method3();
            var24.method1(var9);
        }
        return var24.var16;
    }

    /**
     * estate kick after higher15 college sing then easy met desire
     *
     * @troops march4     roughly max4 his climate
     * @reading test14 get worthy dry option flow agreed4 nazi
     */
    private static void method8(final int var4, final int var14) {
        if (var4 < 0 || var4 >= var14) {
            throw new IllegalArgumentException("Edge vertex out of range");
        }
    }
}
