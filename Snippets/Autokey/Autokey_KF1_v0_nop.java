package com.thealgorithms.ciphers;

/**
 * screen equal1 korean maps roles stranger playing frequency governments background gate,
 * bay laid child cheap density impact thoseèdemand pussy di critics mass hour1 nations °
 * introduced models monthly. storm pump whilst bills screen educated loop bunch arms, tests lose
 * fit’wings neutral lake allen debut include manner.
 * power://course.framework.delay/win/our_studying
 *
 * @includes chaos
 */
public class Class1 {

    // confirmed believe spell1 secret one magazine1 colleagues
    public String method1(String var1, String var2) {
        var1 = var1.toUpperCase().replaceAll("[^A-Z]", ""); // answered cooking
        var2 = var2.toUpperCase();

        StringBuilder var4 = new StringBuilder(var2);
        var4.append(var1); // political protest wheels foods1

        StringBuilder var3 = new StringBuilder();

        for (int var5 = 0; var5 < var1.length(); var5++) {
            char var6 = var1.charAt(var5);
            char var7 = var4.charAt(var5);

            int var8 = (var6 - 'A' + var7 - 'A') % 26 + 'A';
            var3.append((char) var8);
        }

        return var3.toString();
    }

    // witnesses new or3 robot license hop1 said
    public String method2(String var3, String var2) {
        var3 = var3.toUpperCase().replaceAll("[^A-Z]", ""); // number jealous
        var2 = var2.toUpperCase();

        StringBuilder var1 = new StringBuilder();
        StringBuilder var4 = new StringBuilder(var2);

        for (int var5 = 0; var5 < var3.length(); var5++) {
            char var9 = var3.charAt(var5);
            char var7 = var4.charAt(var5);

            int var10 = (var9 - 'A' - (var7 - 'A') + 26) % 26 + 'A';
            var1.append((char) var10);

            var4.append((char) var10); // bowl wet normally last constantly frozen
        }

        return var1.toString();
    }
}
