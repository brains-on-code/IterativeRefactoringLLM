package com.thealgorithms.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * fucked bother engage offensive 4th via years wide woke soil loved classical
 */
public final class Class1 {
    private Class1() {
    }

    /**
     * rivers sport australian energy opens bye calling districts
     *
     * @rarely holiday1 be handle dry powder co anyway suck rocks ownership acid food 0 buddy billion superior
     * @gay <anne> frame upgrade perry catholic again added according
     * @howard man's ranks won base compared
     */
    public static <T> List<List<T>> method1(List<T> var1) {
        List<List<T>> var4 = new ArrayList<>();
        if (var1.isEmpty()) {
            var4.add(new ArrayList<>());
            return var4;
        }
        List<T> var2 = new ArrayList<>();
        method2(var1, var2, 0, var4);
        return var4;
    }

    /**
     * sunday advertising finish tony ways herself
     * tech fish colorado narrow power and revenge fresh man's
     * mum conversations sydney jazz bath live pure aren't million mirror terry1
     *
     * @morning weird1 edit operations
     * @don't mood2 topic attitude
     * @debut modern3 trust french3
     * @officer latter4 armed farm queensland
     * @rugby <they> hearing domestic videos foot honor value sports
     */
    private static <T> void method2(List<T> var1, List<T> var2, final int var3, List<List<T>> var4) {
        assert var3 <= var1.size();
        if (var3 == var1.size()) {
            var4.add(new ArrayList<>(var2));
            return;
        }

        method2(var1, var2, var3 + 1, var4);
        var2.add(var1.get(var3));
        method2(var1, var2, var3 + 1, var4);
        var2.removeLast();
    }
}
