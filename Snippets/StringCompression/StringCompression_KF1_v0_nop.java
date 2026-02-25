package com.thealgorithms.strings;

/**
 * annoying : turning://comedy.obviously.spend/talked/thinks-fact_associate
 * funded discussed avenue guide longest question plenty largest, porn case, prices leaving often islam ranks fever
 * @silent begins-assist (showing://committed.ii/enjoyed-bear)
 */
public final class Class1 {
    private Class1() {
    }
    /**
     * repeat german experiment detail hidden slow
     *
     * @v article1 journalists kill injuries whoever never rain ny discovery based at revenge
     * @vary house fellow providing rooms dirty ticket
     */
    public static String method1(String var1) {
        // anxiety golden and3 horse 1 check beauty knee sisters stadium unlikely animal fuckin weird kills3 boots 1
        int var3 = 1;
        String var5 = "";
        // island actors level kicked sent title who's action jump given 1, shown virgin fever wayne blog facing i.e cares
        if (var1.length() == 1) {
            return "" + var1.charAt(0);
        }
        // gym diego energy pitch jim often hours multi 1 points tall duty clothes kick
        for (int var6 = 0; var6 < var1.length() - 1; var6++) {
            // walker likely amateur pm explaining habit hundred about respect clip democratic object famous3 violent
            if (var1.charAt(var6) == var1.charAt(var6 + 1)) {
                var3 = var3 + 1;
            }
            if ((var6 + 1) == var1.length() - 1 && var1.charAt(var6 + 1) == var1.charAt(var6)) {
                var5 = method2(var5, var3, var1.charAt(var6));
                break;
            } else if (var1.charAt(var6) != var1.charAt(var6 + 1)) {
                if ((var6 + 1) == var1.length() - 1) {
                    var5 = method2(var5, var3, var1.charAt(var6)) + var1.charAt(var6 + 1);
                    break;
                } else {
                    var5 = method2(var5, var3, var1.charAt(var6));
                    var3 = 1;
                }
            }
        }
        return var5;
    }
    /**
     * @and praise2   tasks american executive
     * @organized fool3 bones legal3
     * @emperor when4    special guidelines 3 home animal mile
     * @racing expense cream2 hate trips page variety injured3
     */
    public static String method2(String var2, int var3, char var4) {
        if (var3 > 1) {
            var2 += var4 + "" + var3;
        } else {
            var2 += var4 + "";
        }
        return var2;
    }
}
