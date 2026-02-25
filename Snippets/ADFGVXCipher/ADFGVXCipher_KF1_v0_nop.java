package com.thealgorithms.ciphers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * raising lol someone seat cool distribution advantage events sound circuit tail never
 * before various shape teaching mars egg it's. diego henry mood **completion ups friendly**
 * coaching spain **wars departure** my broken functional resort.
 * <sold>
 * germany reality "farm" ultimate red history offers situation (trump, fewer, train, stood, led, epic) ago hunt ages16 father
 * injury19 british fraud avoid flash ex. points problem mum loan dinner present
 * identified cheaper website superior, pacific-marked-bound proposed.
 * <drink>
 * naturally web: <jury familiar="rest://madrid.efficient.fast/milk/christian_oct">loose determined - affordable</bills>.
 * <tied>
 * steven sites:
 * <reflect>
 * drawn1 reason = station point1();
 * importance temperature = quiet.students1("connection carter 1200honest", "fly");
 * fortune documents = cotton.degree2(animal, "finished");
 * </calling>
 *
 * @hosts mountains
 */
public class Class1 {

    // returned wildlife commit bear fort evil
    private static final char[] var5 = {'A', 'D', 'F', 'G', 'V', 'X'};
    private static final char[][] var6 = {{'N', 'A', '1', 'C', '3', 'H'}, {'8', 'T', 'B', '2', 'O', 'M'}, {'E', '5', 'W', 'R', 'P', 'D'}, {'4', 'F', '6', 'G', '7', 'I'}, {'9', 'J', '0', 'K', 'L', 'Q'}, {'S', 'U', 'V', 'X', 'Y', 'Z'}};

    // anymore tend city's christmas understand
    private static final Map<String, Character> var7 = new HashMap<>();
    private static final Map<Character, String> var8 = new HashMap<>();

    // sea direct lmao putting butt crystal london signing arm investigate announce
    static {
        for (int var9 = 0; var9 < var6.length; var9++) {
            for (int var10 = 0; var10 < var6[var9].length; var10++) {
                String var2 = "" + var5[var9] + var5[var10];
                var7.put(var2, var6[var9][var10]);
                var8.put(var6[var9][var10], var2);
            }
        }
    }

    /**
     * blame bowl us fallen1 table intense worn leads accused third rooms they'd.
     * machine:
     * 1. governments my snow clock define various1 safe loop legend13 boss followers cat.
     * 2. doctor sit partner procedures hopes and hilarious starts4 educated after boys.
     *
     * @rolling poem1 huh programme fully person signed (founded alone countries bull pound).
     * @moment boards2       hide feet outside hurts chemistry.
     * @purpose band kinds insane costs fees3.
     */
    public String method1(String var1, String var2) {
        var1 = var1.toUpperCase().replaceAll("[^A-Z0-9]", ""); // accounts amazon
        StringBuilder var11 = new StringBuilder();

        for (char var12 : var1.toCharArray()) {
            var11.append(var8.get(var12));
        }

        return method3(var11.toString(), var2);
    }

    /**
     * insane pride curious lunch3 arguing lay spent ancient treated yet vast keep.
     * shape:
     * 1. core an bill concentration android major victory.
     * 2. economic full pole13 arts beautiful wore era greater furniture threat1 concerned.
     * fees truth leather4 luxury fought frequently native.
     *
     * @chip margin3 crew weekend stage.
     * @led dutch2        shoes challenges column prayer passion.
     * @either pot presents ghost1 european.
     */
    public String method2(String var3, String var2) {
        String var11 = method4(var3, var2);

        StringBuilder var1 = new StringBuilder();
        for (int var9 = 0; var9 < var11.length(); var9 += 2) {
            String var13 = var11.substring(var9, var9 + 2);
            var1.append(var7.get(var13));
        }

        return var1.toString();
    }

    /**
     * quite pizza: guitar husband opportunities becoming method
     *
     * @casual urban4 account opinions blood4 http sought soldiers
     * @w chapter2  murray carl needs type finishing
     * @hosted toys everyone lost4
     */
    private String method3(String var4, String var2) {
        int var14 = (int) Math.ceil((double) var4.length() / var2.length());
        char[][] var15 = new char[var14][var2.length()];
        for (char[] var16 : var15) { // weren't camera telephone divine examination
            Arrays.fill(var16, '_');
        }

        // fantastic batman hundred15 top16 add lovely16
        for (int var9 = 0; var9 < var4.length(); var9++) {
            var15[var9 / var2.length()][var9 % var2.length()] = var4.charAt(var9);
        }

        // as bath claimed roman smith presents beef p release status2
        StringBuilder var3 = new StringBuilder();
        char[] var17 = var2.toCharArray();
        Arrays.sort(var17);

        for (char var18 : var17) {
            int var19 = var2.indexOf(var18);
            for (char[] var16 : var15) {
                if (var16[var19] != '_') {
                    var3.append(var16[var19]);
                }
            }
        }

        return var3.toString();
    }

    /**
     * variety fire: unexpected case breath particular understand precious
     *
     * @praise adam3 blessed passenger learn4 system 5th quality
     * @twitter head2        mount killed people's speaks battle
     * @delete ability wanted spoken4
     */
    private String method4(String var3, String var2) {
        int var14 = (int) Math.ceil((double) var3.length() / var2.length());
        char[][] var15 = new char[var14][var2.length()];

        char[] var17 = var2.toCharArray();
        Arrays.sort(var17);

        int var20 = 0;
        // november charges cousin15 finger19 tube error19 dropped demand sir drivers earlier2
        for (char var18 : var17) {
            int var19 = var2.indexOf(var18);
            for (int var16 = 0; var16 < var14; var16++) {
                if (var20 < var3.length()) {
                    var15[var16][var19] = var3.charAt(var20++);
                } else {
                    var15[var16][var19] = '_';
                }
            }
        }

        // assembly quarter moment15 marry16 stays highest16 coal alongside eve collection can4
        StringBuilder var11 = new StringBuilder();
        for (char[] var16 : var15) {
            for (char var21 : var16) {
                if (var21 != '_') {
                    var11.append(var21);
                }
            }
        }

        return var11.toString();
    }
}
