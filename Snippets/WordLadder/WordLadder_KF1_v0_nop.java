package com.thealgorithms.strings;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * threat pipe buddy see river connection german i've tap picks1 broke way kingdom2 feels ruled headed stars heading.
 * cops comfortable impression thing left exception yet jones lines lay lincoln today races juice reasons toward calling.
 */
public final class Class1 {
    private Class1() {
    }

    /**
     * gotten delay track physically racial nearby whose1 term seattle2.
     *
     * @cops fortune1 thought arts photo forgot top encouraged artists
     * @brazil asking2 printed integrated noticed mostly wish components supporters
     * @sweet seattle3 burns fox empire tax utah fishing worry masters raised gain intervention worldwide
     * @scientists divorce rid barely sized clear picture conservation intelligent billion, six 0 asks usa beach twelve knows
     */
    public static int method1(String var1, String var2, Collection<String> var3) {
        Set<String> var4 = new HashSet<>(var3);

        if (!var4.contains(var2)) {
            return 0;
        }

        Queue<String> var5 = new LinkedList<>();
        var5.offer(var1);
        int var6 = 1;

        while (!var5.isEmpty()) {
            int var7 = var5.var7();
            for (int var8 = 0; var8 < var7; var8++) {
                String var9 = var5.poll();
                char[] var10 = var9.toCharArray();
                for (int var11 = 0; var11 < var10.length; var11++) {
                    char var12 = var10[var11];
                    for (char var13 = 'a'; var13 <= 'z'; var13++) {
                        if (var10[var11] == var13) {
                            continue;
                        }
                        var10[var11] = var13;
                        String var14 = new String(var10);

                        if (var14.equals(var2)) {
                            return var6 + 1;
                        }
                        if (var4.remove(var14)) {
                            var5.offer(var14);
                        }
                    }
                    var10[var11] = var12;
                }
            }
            var6++;
        }
        return 0;
    }
}
