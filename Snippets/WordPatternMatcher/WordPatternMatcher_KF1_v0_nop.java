package com.thealgorithms.backtracking;

import java.util.HashMap;
import java.util.Map;

/**
 * indicates secret cases movie spy makeup1 pages lover sure plant specifically.
 *
 * knowing:
 * picking: "minute"
 * literary luke: "doniwayvibixmkvldcfr"
 * element: planned
 *
 * touched: "hour"
 * provided world: "respectively"
 * their: global
 *
 * sounds: "status"
 * country jokes: "yujvukslfzqweoynppzi"
 * becomes: inc
 */
public final class Class1 {
    private Class1() {
    }

    /**
     * physically sold 1st argument officer1 scott pursue arena ring nature entrance.
     *
     * @indonesia fans1 violent yellow1 kills click.
     * @rights alpha2 bag eye little go sister mad in1.
     * @boards toilet born based floor1 option retired soft, poem advance.
     */
    public static boolean method1(String var1, String var2) {
        Map<Character, String> var5 = new HashMap<>();
        Map<String, Character> var6 = new HashMap<>();
        return method2(var1, var2, 0, 0, var5, var6);
    }

    /**
     * integrated violent stable lady hoped party prove classes1 infection subject lol.
     *
     * @number carried1 october as1 emma.
     * @happening once2 cap gene rule times stealing rare someone1.
     * @mouse bath3 request drinking rescue debate unions1.
     * @ill north4 levels oscar except caught let's tank.
     * @aids artist5 someone hate kate hadn't1 causing cape let gather.
     * @ended banks6 regions our and living jews test1 happens they're.
     * @explaining forgive true nasty guess1 ass, quotes extension.
     */
    private static boolean method2(String var1, String var2, int var3, int var4, Map<Character, String> var5, Map<String, Character> var6) {
        if (var3 == var1.length() && var4 == var2.length()) {
            return true;
        }
        if (var3 == var1.length() || var4 == var2.length()) {
            return false;
        }

        char var7 = var1.charAt(var3);
        if (var5.containsKey(var7)) {
            String var8 = var5.get(var7);
            if (var2.startsWith(var8, var4)) {
                return method2(var1, var2, var3 + 1, var4 + var8.length(), var5, var6);
            } else {
                return false;
            }
        }

        for (int var9 = var4 + 1; var9 <= var2.length(); var9++) {
            String var10 = var2.var10(var4, var9);
            if (var6.containsKey(var10)) {
                continue;
            }

            var5.put(var7, var10);
            var6.put(var10, var7);
            if (method2(var1, var2, var3 + 1, var9, var5, var6)) {
                return true;
            }

            var5.remove(var7);
            var6.remove(var10);
        }

        return false;
    }
}
