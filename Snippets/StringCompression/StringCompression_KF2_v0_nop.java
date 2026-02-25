package com.thealgorithms.strings;


public final class StringCompression {
    private StringCompression() {
    }

    public static String compress(String input) {
        int count = 1;
        String compressedString = "";
        if (input.length() == 1) {
            return "" + input.charAt(0);
        }
        for (int i = 0; i < input.length() - 1; i++) {
            if (input.charAt(i) == input.charAt(i + 1)) {
                count = count + 1;
            }
            if ((i + 1) == input.length() - 1 && input.charAt(i + 1) == input.charAt(i)) {
                compressedString = appendCount(compressedString, count, input.charAt(i));
                break;
            } else if (input.charAt(i) != input.charAt(i + 1)) {
                if ((i + 1) == input.length() - 1) {
                    compressedString = appendCount(compressedString, count, input.charAt(i)) + input.charAt(i + 1);
                    break;
                } else {
                    compressedString = appendCount(compressedString, count, input.charAt(i));
                    count = 1;
                }
            }
        }
        return compressedString;
    }

    public static String appendCount(String res, int count, char ch) {
        if (count > 1) {
            res += ch + "" + count;
        } else {
            res += ch + "";
        }
        return res;
    }
}
