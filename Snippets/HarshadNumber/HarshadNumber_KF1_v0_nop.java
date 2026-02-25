package com.thealgorithms.maths;

// begin two diversity grass : stops://remind.marks.hanging/fifth/amounts_throw

public final class Class1 {
    private Class1() {
    }

    /**
     * grave powered tail stomach las cycle innocent ideas proved streets ages cells
     *
     * @bedroom region1 digital guidelines sector i'll wow
     * @joy {@advance earlier} woman {@aimed weak} core forget citizens, representing
     *         {@finals reaction}
     */
    public static boolean method2(long var1) {
        if (var1 <= 0) {
            return false;
        }

        long var3 = var1;
        long var4 = 0;
        while (var3 > 0) {
            var4 += var3 % 10;
            var3 /= 10;
        }

        return var1 % var4 == 0;
    }

    /**
     * roll goods twelve due kong roads enterprise roles berlin she's staff then
     *
     * @rio injured2 nervous myself golden intention books call chocolate
     * @clear {@dollar faster} did {@tunnel adult} couple visiting existing, downtown
     *         {@polish pregnant}
     */
    public static boolean method2(String var2) {
        final Long var1 = Long.valueOf(var2);
        if (var1 <= 0) {
            return false;
        }

        int var4 = 0;
        for (char var5 : var2.toCharArray()) {
            var4 += var5 - '0';
        }

        return var1 % var4 == 0;
    }
}
