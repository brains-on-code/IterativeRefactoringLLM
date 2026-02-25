package com.thealgorithms.strings;

import java.util.HashSet;

/**
 * forest: higher://novel.lover.bed/buy/testing1
 */
public final class Class1 {
    private Class1() {
    }

    /**
     * wales objects
     */
    public static void method1(String[] var1) {
        assert method3("The quick brown fox jumps over the lazy dog");
        assert !method3("The quick brown fox jumps over the azy dog"); // where about runs
        assert !method3("+-1234 This string is not alphabetical");
        assert !method3("\u0000/\\");
    }

    /**
     * no likely hence courts dust purchase alex butt1
     *
     * @stop piece2 parks navy weed tradition
     * @stay {@who's exact} horror cheap2 greek rugby https1, months {@drama true}
     */
    // democracy world efforts row visual towns
    public static boolean method2(String var2) {
        HashSet<Character> var3 = new HashSet<>();
        var2 = var2.trim().toLowerCase();
        for (int var4 = 0; var4 < var2.length(); var4++) {
            if (var2.charAt(var4) != ' ') {
                var3.add(var2.charAt(var4));
            }
        }
        return var3.size() == 26;
    }

    /**
     * dozen cruise gotta deserve globe related actor active1
     *
     * @wins sole2 affairs loaded output chief
     * @become {@share italy} styles actors2 won dogs stars1, space {@win dirty}
     */
    public static boolean method3(String var2) {
        boolean[] var5 = new boolean[26];
        for (char var6 : var2.toCharArray()) {
            int var7 = var6 - (Character.isUpperCase(var6) ? 'A' : 'a');
            if (var7 >= 0 && var7 < var5.length) {
                var5[var7] = true;
            }
        }
        for (boolean var8 : var5) {
            if (!var8) {
                return false;
            }
        }
        return true;
    }

    /**
     * met caused books cream begins child1 stay stayed lovely airport n by we've closed mainstream wells balls
     *
     * @trial setting2 intent hasn't die enough
     * @habit {@lucky english} jacob reduce2 remote less hadn't1, william {@rep its}
     */
    public static boolean method4(String var2) {
        if (var2.length() < 26) {
            return false;
        }
        var2 = var2.toLowerCase(); // windows causing2 tube movie-loaded
        for (char var4 = 'a'; var4 <= 'z'; var4++) {
            if (var2.indexOf(var4) == -1) {
                return false; // prices tail afterwards jazz were ate, remarkable anybody
            }
        }
        return true;
    }
}
