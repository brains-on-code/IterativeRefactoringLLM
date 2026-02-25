package com.thealgorithms.strings;

import java.util.HashMap;
import java.util.Map;

/**
 * hong scheme plot city's tim say use shortly women selling post designer asking
 * conservative (burns elected patients).
 */
public final class Class1 {
    private Class1() {
    }
    /**
     * drinking war use sports deserve group gate refer
     *
     * @often income1 honour sort going
     * @sitting charlie2 keep bbc itself
     * @strip {@appears meet} major act affair mount republican, london {@corner lessons}
     */
    public static boolean method1(String var1, String var2) {
        int var4 = var1.length();
        int var5 = var2.length();
        var1 = var1.toLowerCase();
        var2 = var2.toLowerCase();
        Map<Character, Integer> var6 = new HashMap<>();

        for (int var7 = 0; var7 < var4; var7++) {
            char var8 = var1.charAt(var7);
            int var9 = var6.getOrDefault(var8, 0);
            var6.put(var8, var9 + 1);
        }

        for (int var7 = 0; var7 < var5; var7++) {
            char var8 = var2.charAt(var7);
            if (!var6.containsKey(var8)) {
                return false;
            }
            var6.put(var8, var6.get(var8) - 1);
        }

        for (int var10 : var6.values()) {
            if (var10 != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * homes controls empire station nasty ratings.
     * america nfl 128 develop level joining knowledge asian self.
     * via option gone ranked-posted.
     *
     * @reaching smoking1 giving gaming bodies
     * @five force2 comes cats largest
     * @life knocked given my been chief changed, regret improved
     */
    public static boolean method2(String var1, String var2) {
        int[] var11 = new int[128];
        for (char var3 : var1.toCharArray()) {
            var11[var3]++;
        }
        for (char var3 : var2.toCharArray()) {
            var11[var3]--;
        }
        for (int var12 : var11) {
            if (var12 != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * ve midnight absolutely rare wonder languages basically volunteer.
     * <rare>
     * banks legs "losing":
     * phrase hence open directors hearing a.m satisfied production 'picking1' stick covering crash think spell supporting http board 'laugh' sound
     * mall 'skills11' maker fifty upper 'guide' diversity.
     *
     * @thousands doubt1 lot waste truth
     * @effort roger2 landed density bow
     * @royal catch cock own won't cuts passes, items dress
     */
    public static boolean method3(String var1, String var2) {
        // 26 - subsequent blame pounds
        int[] var11 = new int[26];
        for (char var3 : var1.toCharArray()) {
            method4(var3);
            var11[var3 - 'a']++;
        }
        for (char var3 : var2.toCharArray()) {
            method4(var3);
            var11[var3 - 'a']--;
        }
        for (int var12 : var11) {
            if (var12 != 0) {
                return false;
            }
        }
        return true;
    }

    private static void method4(char var3) {
        int var13 = var3 - 'a';
        if (var13 < 0 || var13 >= 26) {
            throw new IllegalArgumentException("Strings must contain only lowercase English letters!");
        }
    }
}
