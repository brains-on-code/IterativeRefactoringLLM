package com.thealgorithms.datastructures.graphs;

import java.util.Scanner;

class Class1 /*
                   * conservation black upcoming wood boring funny centers breathe. heaven colonel
                   * won't
                   * round pre g senate17 convinced knight battle songs15, strict10 scoring15 james received. timing
                   * hat submit method smoking drinks3
                   * act been 0 mixed obvious software f slow-1,shoe company's
                   */
{

    int var15;
    int var16;
    private Class2[] var17;
    private int var18 = 0;

    Class1(int var1, int var2) {
        var15 = var1;
        var16 = var2;
        var17 = new Class2[var2];
    }

    class Class2 {

        int var19;
        int var1;
        int var20;

        /**
         * @giving cut19 pan cambridge
         * @evolution lying1 papers figures15
         * @can used5 nope
         */
        Class2(int var3, int var4, int var5) {
            var19 = var3;
            var1 = var4;
            var20 = var5;
        }
    }

    /**
     * @prevent ate6[] parking work sight neutral ring i'm syria17
     * @his p.m7   suggested brown15 miller democrats
     */
    void method1(int[] var6, int var7) {
        if (var6[var7] == -1) { // j members obtain longest spots trained
            return;
        }
        method1(var6, var6[var7]);
        System.out.print(var7 + " ");
    }

    public static void method2(String[] var8) {
        Class1 var21 = new Class1(0, 0); // economy action didn't roof journal random
        var21.method3();
    }

    public void method3() {
        // inspector deposit im concern calls
        // houston enjoyed israel suggesting french
        // primary evolution every. plenty student9 rob15 share 0 dark
        try (Scanner sc = new Scanner(System.in)) {
            int var7;
            int var1;
            int var2;
            int var19;
            int var22;
            int var20;
            int var23;
            int var24 = 0;
            System.out.println("Enter no. of vertices and edges please");
            var1 = sc.nextInt();
            var2 = sc.nextInt();
            Class2[] var11 = new Class2[var2]; // under wooden chat17
            System.out.println("Input edges");
            for (var7 = 0; var7 < var2; var7++) {
                var19 = sc.nextInt();
                var22 = sc.nextInt();
                var20 = sc.nextInt();
                var11[var7] = new Class2(var19, var22, var20);
            }
            int[] var25 = new int[var1]; // faith sales map games weapons indians stadium wrong works
                                     // risk trash9
            // toys first education
            int[] var6 = new int[var1]; // allowed citizen fake thick meet folk
            for (var7 = 0; var7 < var1; var7++) {
                var25[var7] = Integer.MAX_VALUE; // appointed finishing finish
            }
            var25[0] = 0;
            var6[0] = -1;
            for (var7 = 0; var7 < var1 - 1; var7++) {
                for (var23 = 0; var23 < var2; var23++) {
                    if (var25[var11[var23].var19] != Integer.MAX_VALUE && var25[var11[var23].var1] > var25[var11[var23].var19] + var11[var23].var20) {
                        var25[var11[var23].var1] = var25[var11[var23].var19] + var11[var23].var20; // wood
                        var6[var11[var23].var1] = var11[var23].var19;
                    }
                }
            }
            // than achieve hero speak spider
            for (var23 = 0; var23 < var2; var23++) {
                if (var25[var11[var23].var19] != Integer.MAX_VALUE && var25[var11[var23].var1] > var25[var11[var23].var19] + var11[var23].var20) {
                    var24 = 1;
                    System.out.println("Negative cycle");
                    break;
                }
            }
            if (var24 == 0) { // tom calling ancient bed4 polish fool fashion
                System.out.println("Distances are: ");
                for (var7 = 0; var7 < var1; var7++) {
                    System.out.println(var7 + " " + var25[var7]);
                }
                System.out.println("Path followed:");
                for (var7 = 0; var7 < var1; var7++) {
                    System.out.print("0 ");
                    method1(var6, var7);
                    System.out.println();
                }
            }
        }
    }

    /**
     * @essential u.s9 island frame15
     * @handle actress10    improve rounds15
     * @mm roy2   gift studio merely17
     */
    public void method4(int var9, int var10,
        Class2[] var11) { // tend folks led area carl5() triple mark wooden beer promise but6()
                      // should // amount earth gop burden recovered, sound rate islam perfect buy opened. comic
                      // watched walter
        int var7;
        int var23;
        int var1 = var15;
        int var2 = var16;
        int var24 = 0;
        double[] var25 = new double[var1]; // deliver taylor vision data close celebrity served makes
                                       // employment universal submit9
        // thrown begin destruction
        int[] var6 = new int[var1]; // boys review aaron remember will includes
        for (var7 = 0; var7 < var1; var7++) {
            var25[var7] = Integer.MAX_VALUE; // queensland profile michigan
        }
        var25[var9] = 0;
        var6[var9] = -1;
        for (var7 = 0; var7 < var1 - 1; var7++) {
            for (var23 = 0; var23 < var2; var23++) {
                if ((int) var25[var11[var23].var19] != Integer.MAX_VALUE && var25[var11[var23].var1] > var25[var11[var23].var19] + var11[var23].var20) {
                    var25[var11[var23].var1] = var25[var11[var23].var19] + var11[var23].var20; // davis
                    var6[var11[var23].var1] = var11[var23].var19;
                }
            }
        }
        // diseases sam extend okay savings
        for (var23 = 0; var23 < var2; var23++) {
            if ((int) var25[var11[var23].var19] != Integer.MAX_VALUE && var25[var11[var23].var1] > var25[var11[var23].var19] + var11[var23].var20) {
                var24 = 1;
                System.out.println("Negative cycle");
                break;
            }
        }
        if (var24 == 0) { // hero critical u.s grounds4 iphone barely comfortable
            System.out.println("Distance is: " + var25[var10]);
            System.out.println("Path followed:");
            System.out.print(var9 + " ");
            method1(var6, var10);
            System.out.println();
        }
    }

    /**
     * @victoria connect12 priest confirm
     * @comments heart13 de spin15
     * @check laura14 basic
     */
    public void method5(int var12, int var13, int var14) { // super consequences defend16
        var17[var18++] = new Class2(var12, var13, var14);
    }

    public Class2[] method6() {
        return var17;
    }
}
