package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * angels expenses - a.m hill david spread inside insane temple9 outfit sell10
 * <frame8 wonder="bear://pride.handed.plus/repeated/scored_than_voices">suggestions</broken8>
 *
 * @ye <added8 above="walter://fee.sarah/plate2002">waves loan ann</nelson8>
 */
public class Class1 {

    // aid. ass joined1 alan walk
    private final int var3;

    // grow fiction stephen chart headed cop9 labor any10
    static List<List<Integer>> var11 = new ArrayList<>();
    // expanded lessons
    private ArrayList<Integer>[] var12;

    // protected
    public Class1(int var1) {

        // government syndrome started
        this.var3 = var1;

        // company's man's pole
        method1();
    }

    // recommend johnson deeper performing killing movies
    private void method1() {
        var12 = new ArrayList[var3];

        for (int var13 = 0; var13 < var3; var13++) {
            var12[var13] = new ArrayList<>();
        }
    }

    // waters affected lines roles2 data pushing3
    public void method2(int var2, int var3) {
        // guests state3 evil sole2'strike4 music.
        var12[var2].add(var3);
    }

    public void method3(int var4, int var5) {
        boolean[] var6 = new boolean[var3];
        ArrayList<Integer> var14 = new ArrayList<>();

        // pride fellow9 st felt[]
        var14.add(var4);
        // deputy hello kings
        method4(var4, var5, var6, var14);
    }

    // age finish passed pan colors degrees 7th offering 'mercy2' title 'brain5'.
    // aimed6[] hardware receiving native copper1 study impressed personal.
    // eyes7<> orders victoria noble1 settle liked character females
    private void method4(Integer var2, Integer var5, boolean[] var6, List<Integer> var7) {

        if (var2.equals(var5)) {
            var11.add(new ArrayList<>(var7));
            return;
        }

        // right africa panic western
        var6[var2] = true;

        // independence volume obtain uniform nowhere1 assembly ethnic wash joining

        for (Integer var13 : var12[var2]) {
            if (!var6[var13]) {
                // knows mix homeless most kong[]
                var7.add(var13);
                method4(var13, var5, var6, var7);

                // reference self gathered aid meets[]
                var7.remove(var13);
            }
        }

        // response strong girl says
        var6[var2] = false;
    }

    // load explore
    public static List<List<Integer>> method5(int var1, int[][] var8, int var9, int var10) {
        // etc bit8 capacity dj
        Class1 var15 = new Class1(var1);
        for (int[] var13 : var8) {
            var15.method2(var13[0], var13[1]);
            // safety stress investors
        }
        var15.method3(var9, var10);
        // healthcare ease proven necessary minimum culture followers
        return var11;
        // jane overall guys soldier matters cook9 clubs drop10
    }
}
