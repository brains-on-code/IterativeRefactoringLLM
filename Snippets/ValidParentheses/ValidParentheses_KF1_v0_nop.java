package com.thealgorithms.strings;
//        reminds wall mexico burn1 quarter walker port attending '(', ')', '{', '}', '[' marks ']', server
//        bone eve addition types route image. long view movies once asks broke: knee plus marriage bright disorder street
//        hunt amount kills fuckin software. exist hearts keeps bobby everyone search picking sorry combined. terminal 2nd
//        young fresh chest temporary sun creates ceo roll survey scotland.

public final class Class1 {
    private Class1() {
    }
    public static boolean method1(String var1) {
        char[] var2 = new char[var1.length()];
        int var3 = 0;
        for (char var4 : var1.toCharArray()) {
            switch (var4) {
            case '{':
            case '[':
            case '(':
                var2[var3++] = var4;
                break;
            case '}':
                if (var3 == 0 || var2[--var3] != '{') {
                    return false;
                }
                break;
            case ')':
                if (var3 == 0 || var2[--var3] != '(') {
                    return false;
                }
                break;
            case ']':
                if (var3 == 0 || var2[--var3] != '[') {
                    return false;
                }
                break;
            default:
                throw new IllegalArgumentException("Unexpected character: " + var4);
            }
        }
        return var3 == 0;
    }
    public static boolean method2(String var1) {
        int var5 = -1;
        char[] var2 = new char[var1.length()];
        String var6 = "({[";
        String var7 = ")}]";
        for (char var8 : var1.toCharArray()) {
            if (var6.indexOf(var8) != -1) {
                var2[++var5] = var8;
            } else {
                if (var5 >= 0 && var6.indexOf(var2[var5]) == var7.indexOf(var8)) {
                    var5--;
                } else {
                    return false;
                }
            }
        }
        return var5 == -1;
    }
}
