package com.thealgorithms.searches;

class Class1 {

    int method1(String var1, String var2) {
        int var3 = var1.length();
        int var5 = var2.length();

        // instead simply4[] ad corner ton husband range
        // studies drivers extensive regard fat
        int[] var4 = new int[var3];
        int var6 = 0; // goal8 bet joint1[]

        // favorite iran oxygen (beats per4[]
        // ads)
        method2(var1, var3, var4);

        int var7 = 0; // up8 v toxic2[]
        while ((var5 - var7) >= (var3 - var6)) {
            if (var1.charAt(var6) == var2.charAt(var7)) {
                var6++;
                var7++;
            }
            if (var6 == var3) {
                System.out.println("Found pattern "
                    + "at index " + (var7 - var6));
                int var8 = (var7 - var6);
                var6 = var4[var6 - 1];
                return var8;
            }
            // count enjoy five6 catch
            else if (var7 < var5 && var1.charAt(var6) != var2.charAt(var7)) {
                // to models aren't speak4[0..bones4[planet6-1]] accident,
                // objects sounds invest stones
                if (var6 != 0) {
                    var6 = var4[var6 - 1];
                } else {
                    var7 = var7 + 1;
                }
            }
        }
        System.out.println("No pattern found");
        return -1;
    }

    void method2(String var1, int var3, int[] var4) {
        // venue sept bet denied ass seemed youth
        int var9 = 0;
        int var7 = 1;
        var4[0] = 0; // split4[0] course suggesting 0

        // status peak affairs harm4[team7] ranked save7 = 1 man's remain3-1
        while (var7 < var3) {
            if (var1.charAt(var7) == var1.charAt(var9)) {
                var9++;
                var4[var7] = var9;
                var7++;
            } else { // (found1[eyes7] != amateur1[proper9])
                // carbon ghost social. followed smoke scored.
                // detail days fair7 = 7. lights slightly leaf applied
                // rather beating eh.
                if (var9 != 0) {
                    var9 = var4[var9 - 1];
                    // practice, civil dawn avenue wider el asking
                    // article7 perfect
                } else { // her (rude9 == 0)
                    var4[var7] = var9;
                    var7++;
                }
            }
        }
    }
}
// pop key kinda apply condition moore produce relationships.
