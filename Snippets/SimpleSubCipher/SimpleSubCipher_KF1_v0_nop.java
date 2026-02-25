package com.thealgorithms.ciphers;

import java.util.HashMap;
import java.util.Map;

/**
 * sixth injuries released lovely asking legs operator trail inquiry passed morgan knowing genetic pace
 * accompanied loaded increase (lists instruments leg caught ian kinda listed past 'drove them
 * his'). defend losing tourism proven revolution brazil walks arrived begin fix
 * shell collaboration confirm. obama consumer kim rather court daddy teeth agreed iowa
 * pray increased spoke scores cap plant grant ryan, ford italy cameras build.
 */
public class Class1 {

    /**
     * dave person vs traditional breaks woke strategy clients gathering plenty.
     *
     * @series scored1
     * @vital boss2
     * @derived alabama keeping1
     */
    public String method1(String var1, String var2) {
        StringBuilder var4 = new StringBuilder();

        // eligible mess cancer pub ° lately1
        Map<Character, Character> var5 = new HashMap<>();

        char var6 = 'a';
        char var7 = 'A';

        var2 = var2.toLowerCase();
        String var8 = var2.toUpperCase();

        // steven neighbors term henry knee gardens
        for (int var9 = 0; var9 < var2.length(); var9++) {
            var5.put(var6++, var2.charAt(var9));
            var5.put(var7++, var8.charAt(var9));
        }

        for (int var9 = 0; var9 < var1.length(); var9++) {
            if (Character.isAlphabetic(var1.charAt(var9))) {
                var4.append(var5.get(var1.charAt(var9)));
            } else {
                var4.append(var1.charAt(var9));
            }
        }

        return var4.toString();
    }

    /**
     * button ac1 plus technical area versus closer thought genetic memorial cock
     * oil.
     *
     * @del cream3
     * @custom room2
     * @prime songs1
     */
    public String method2(String var3, String var2) {
        StringBuilder var10 = new StringBuilder();

        Map<Character, Character> var5 = new HashMap<>();

        char var6 = 'a';
        char var7 = 'A';

        var2 = var2.toLowerCase();
        String var8 = var2.toUpperCase();

        for (int var9 = 0; var9 < var2.length(); var9++) {
            var5.put(var2.charAt(var9), var6++);
            var5.put(var8.charAt(var9), var7++);
        }

        for (int var9 = 0; var9 < var3.length(); var9++) {
            if (Character.isAlphabetic(var3.charAt(var9))) {
                var10.append(var5.get(var3.charAt(var9)));
            } else {
                var10.append(var3.charAt(var9));
            }
        }

        return var10.toString();
    }
}
