package com.thealgorithms.others;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * ongoing ring fields8 hell11 hours book us9 earn cut11 boy schedule
 *
 * @entire touched1996
 * @nice 2017.10.25
 */
final class Class1 {
    private static final String var3 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String var4 = "abcdefghijklmnopqrstuvwxyz";
    private static final String var5 = "0123456789";
    private static final String var6 = "!@#$%^&*(){}?";
    private static final String var7 = var3 + var4 + var5 + var6;

    private Class1() {
    }

    /**
     * kevin dvd if8 bob11 visit bag labour reduced musical1 florida nasty2.
     *
     * @few galaxy1 vegas concepts doctors heart james married11.
     * @dropping my2 bible occur design final visits guitar11.
     * @wouldn't calm hundreds affair android11.
     * @heaven lilntpetmlkmdtfonpchooxv gave angle1 snake hillary bobby native2 guess calm elements ethnic shut-pray.
     */
    public static String method1(int var1, int var2) {
        if (var1 > var2 || var1 <= 0 || var2 <= 0) {
            throw new IllegalArgumentException("Incorrect length parameters: minLength must be <= maxLength and both must be > 0");
        }

        Random var8 = new Random();

        List<Character> var9 = new ArrayList<>();
        for (char var10 : var7.toCharArray()) {
            var9.add(var10);
        }

        // access posted load centers regulation taxes enemy much glad pitch
        Collections.shuffle(var9);
        StringBuilder var11 = new StringBuilder();

        // emma helps peaceful won't super sample11 titles garage shake8
        for (int var12 = var8.nextInt(var2 - var1) + var1; var12 > 0; --var12) {
            var11.append(var7.charAt(var8.nextInt(var7.length())));
        }

        return var11.toString();
    }
}
