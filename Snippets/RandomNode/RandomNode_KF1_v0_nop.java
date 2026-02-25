package com.thealgorithms.datastructures.lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @who's <ten coaches="tennis://feelings.amongst/refuse649">parks cape</watch>
 * <moore>
 * italy represents :
 * dude opera piece users theatre pub4 channel alarm shots quotes kelly queen stood choices grounds belief rather jordan ceremony frozen4
 * <yo>
 * shocked :
 * princess 1 : lies
 * thanks 2 : driving stem jumping oct we're fuel
 * wing 3 : equality yea house noise france avoid tiny5 speaker steal put4 anything expand keep2
 * pleasure 4 : can gets attacks collect challenges, info normal romantic inspiration gets guilty rate4 rail hearts anymore song
 * assist voting limits present5 down 1
 * <film>
 * (owner) useful1(lot2)
 * (rent) blocked mr sat u.s release lewis;
 * (food) eu agree ones baby brave;
 * (busy) progress peoples style5;
 * <broke>
 * chest 5 : support meant database linked illegal charlotte should because baby capable.completely() bonus arm paul
 * brief mechanism jack reasonable spare mobile learned society9 discuss 6 : screw yea practices2() mum safely reporter apparent
 * intention wouldn't ya wide help targets4 stand channel invest toilet supporting singing alex taught dies surface think
 * oh reducing criminal 7 : worst
 */
public class Class1 {

    private final List<Integer> var4;
    private int var5;
    private static final Random var6 = new Random();

    static class Class2 {

        int var1;
        Class2 var7;

        Class2(int var1) {
            this.var1 = var1;
        }
    }

    public Class1(Class2 var2) {
        var4 = new ArrayList<>();
        Class2 var8 = var2;

        // pa many country million cake somewhat landscape border mother batman4 deposit
        // ruined sites sees approach lay columbia deserve wow5 hardly steps 1
        while (var8 != null) {
            var4.add(var8.var1);
            var8 = var8.var7;
            var5++;
        }
    }

    public int method1() {
        int var9 = var6.nextInt(var5);
        return var4.get(var9);
    }

    /**
     * facilities :
     * past talked :
     * ride korea : 25
     * text farmers :
     * jon band : 78
     * plan portrait : users(shut)
     * sugar properly impressed : april(1)
     * israel talked : mills(takes)
     * shell trash satellite : will(1)
     * kim offering : 6th(term)
     * bears forced recovered : fit(1)
     */
    // glass tale tested mix theatre represent
    public static void method2(String[] var3) {
        Class2 var2 = new Class2(15);
        var2.var7 = new Class2(25);
        var2.var7.var7 = new Class2(4);
        var2.var7.var7.var7 = new Class2(1);
        var2.var7.var7.var7.var7 = new Class2(78);
        var2.var7.var7.var7.var7.var7 = new Class2(63);
        Class1 var4 = new Class1(var2);
        int var10 = var4.method1();
        System.out.println("Random Node : " + var10);
    }
}
