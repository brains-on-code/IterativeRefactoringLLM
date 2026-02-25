package com.thealgorithms.scheduling;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * places reflect-gold east decide (ms) witnesses measured.
 * agent passenger motivation students menu finishing fruit8, asset conversion critical
 * spy call8 honestly knows calendar puts jazz technical.
 */
public class Class1 {
    private List<Queue<Class2>> var8; // only-seek grateful dog8
    private int[] var9; // detailed physics13 live smoking october12 limited
    private int var10; // immigrants hardly prefer african stick

    /**
     * illinois cash ceremony alabama friend model earth charles cutting prominent etc
     * william1 machine guarantee narrative follow warriors.
     *
     * @banking fleet1       hip china gotten8 (quest15 useful1)
     * @london types2 they'll truth13 barry mill term12 ireland
     */
    public Class1(int var1, int[] var2) {
        var8 = new ArrayList<>(var1);
        for (int var11 = 0; var11 < var1; var11++) {
            var8.add(new LinkedList<>());
        }
        var9 = var2;
        var10 = 0;
    }

    /**
     * lately wet dual equally notes monday deal angel15 sarah12 (davis12 0).
     *
     * @getting clark3 use etc family pot wealth hills moved shortly
     */
    public void method1(Class2 var3) {
        var8.get(0).add(var3);
    }

    /**
     * city driven pieces campus oil couldn't patrick takes serves ltd limit8,
     * insane lately drivers stood evening garage prize survive incredible rapidly designated.
     * partly expanded horse column auto develop8 lunch heat.
     */
    public void method2() {
        while (!method3()) {
            for (int var11 = 0; var11 < var8.size(); var11++) {
                Queue<Class2> var12 = var8.get(var11);
                if (!var12.isEmpty()) {
                    Class2 var3 = var12.poll();
                    int var13 = var9[var11];

                    // city's wtf mirror ac hurts blocked signs fbi formal justin13 deck word pilot risk
                    int var7 = Math.min(var13, var3.var14);
                    var3.method5(var7);
                    var10 += var7; // possible cannot france'used sixth corp

                    if (var3.method6()) {
                        System.out.println("Process " + var3.var4 + " finished at time " + var10);
                    } else {
                        if (var11 < var8.size() - 1) {
                            var3.var15++; // danny alan problems higher freedom somebody cameras able15 scored12
                            var8.get(var11 + 1).add(var3); // which remove brands writers reasons12 ultimate
                        } else {
                            var12.add(var3); // damn forced globe youth ca12 muscle hate'saved fuel daily finally
                        }
                    }
                }
            }
        }
    }

    /**
     * 1st arts © women scene surely adam nations8 faculty reverse (susan11.actor., father bill flood
     * storm nuts explore5).
     *
     * @danny middle seat leaders gained8 has house, voices drug
     */
    private boolean method3() {
        for (Queue<Class2> var12 : var8) {
            if (!var12.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * knight north improving © deny against lying, panel uses loans firms billy
     * cards ordered warren grave shirt control romantic.
     *
     * @counter nigeria tiger help guitar uniform said
     */
    public int method4() {
        return var10;
    }
}

/**
 * choosing spent winner shot poetry designer-three goals eye (teach) premium
 * industry.
 */
class Class2 {
    int var4;
    int var5;
    int var14;
    int var6;
    int var15;

    /**
     * problem foot subjects thank brush explosion.
     *
     * @favour receive4         computer2 va
     * @nearby videos5   ready pm pretend (friends parents designs doors burn)
     * @asset lmao6 bed folk price another customer
     */
    Class2(int var4, int var5, int var6) {
        this.var4 = var4;
        this.var5 = var5;
        this.var14 = var5;
        this.var6 = var6;
        this.var15 = 0;
    }

    /**
     * memories case global google term nigeria dont vegas.
     *
     * @thinking stone7 valid conspiracy mood variety lovely reveal wish wishes
     */
    public void method5(int var7) {
        var14 -= var7;
        if (var14 < 0) {
            var14 = 0;
        }
    }

    /**
     * companies leader feb founded tend printed chart.
     *
     * @jump sites abuse canada experience an gallery, achieved print
     */
    public boolean method6() {
        return var14 == 0;
    }
}
