package com.thealgorithms.dynamicprogramming;

public final class RegexMatching {

    private static final int MEMO_UNKNOWN = 0;
    private static final int MEMO_FALSE = 1;
    private static final int MEMO_TRUE = 2;

    private RegexMatching() {}

    public static boolean regexRecursion(String src, String pat) {
        if (src.isEmpty() && pat.isEmpty()) {
            return true;
        }
        if (!src.isEmpty() && pat.isEmpty()) {
            return false;
        }
        if (src.isEmpty()) {
            return isAllStars(pat, 0);
        }

        char srcChar = src.charAt(0);
        char patChar = pat.charAt(0);

        String remainingSrc = src.substring(1);
        String remainingPat = pat.substring(1);

        if (isCharMatch(srcChar, patChar)) {
            return regexRecursion(remainingSrc, remainingPat);
        }
        if (isStar(patChar)) {
            return regexRecursion(src, remainingPat) || regexRecursion(remainingSrc, pat);
        }
        return false;
    }

    static boolean regexRecursion(String src, String pat, int srcIndex, int patIndex) {
        if (isAtEndOfBoth(src, pat, srcIndex, patIndex)) {
            return true;
        }
        if (isPatternExhaustedBeforeSource(src, pat, srcIndex, patIndex)) {
            return false;
        }
        if (isSourceExhausted(src, srcIndex)) {
            return isAllStars(pat, patIndex);
        }

        char srcChar = src.charAt(srcIndex);
        char patChar = pat.charAt(patIndex);

        if (isCharMatch(srcChar, patChar)) {
            return regexRecursion(src, pat, srcIndex + 1, patIndex + 1);
        }
        if (isStar(patChar)) {
            return regexRecursion(src, pat, srcIndex, patIndex + 1)
                || regexRecursion(src, pat, srcIndex + 1, patIndex);
        }
        return false;
    }

    public static boolean regexRecursion(String src, String pat, int srcIndex, int patIndex, int[][] memo) {
        if (isAtEndOfBoth(src, pat, srcIndex, patIndex)) {
            return true;
        }
        if (isPatternExhaustedBeforeSource(src, pat, srcIndex, patIndex)) {
            return false;
        }
        if (isSourceExhausted(src, srcIndex)) {
            return isAllStars(pat, patIndex);
        }

        if (memo[srcIndex][patIndex] != MEMO_UNKNOWN) {
            return memo[srcIndex][patIndex] == MEMO_TRUE;
        }

        char srcChar = src.charAt(srcIndex);
        char patChar = pat.charAt(patIndex);

        boolean result;
        if (isCharMatch(srcChar, patChar)) {
            result = regexRecursion(src, pat, srcIndex + 1, patIndex + 1, memo);
        } else if (isStar(patChar)) {
            result =
                regexRecursion(src, pat, srcIndex, patIndex + 1, memo)
                    || regexRecursion(src, pat, srcIndex + 1, patIndex, memo);
        } else {
            result = false;
        }

        memo[srcIndex][patIndex] = result ? MEMO_TRUE : MEMO_FALSE;
        return result;
    }

    static boolean regexBU(String src, String pat) {
        int srcLen = src.length();
        int patLen = pat.length();
        boolean[][] dp = new boolean[srcLen + 1][patLen + 1];

        dp[srcLen][patLen] = true;

        for (int row = srcLen; row >= 0; row--) {
            for (int col = patLen - 1; col >= 0; col--) {
                char patChar = pat.charAt(col);

                if (row == srcLen) {
                    dp[row][col] = isStar(patChar) && dp[row][col + 1];
                    continue;
                }

                char srcChar = src.charAt(row);
                boolean result;

                if (isCharMatch(srcChar, patChar)) {
                    result = dp[row + 1][col + 1];
                } else if (isStar(patChar)) {
                    result = dp[row][col + 1] || dp[row + 1][col];
                } else {
                    result = false;
                }

                dp[row][col] = result;
            }
        }
        return dp[0][0];
    }

    private static boolean isAllStars(String pattern, int startIndex) {
        for (int i = startIndex; i < pattern.length(); i++) {
            if (!isStar(pattern.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isCharMatch(char srcChar, char patChar) {
        return srcChar == patChar || patChar == '?';
    }

    private static boolean isStar(char ch) {
        return ch == '*';
    }

    private static boolean isAtEndOfBoth(String src, String pat, int srcIndex, int patIndex) {
        return srcIndex == src.length() && patIndex == pat.length();
    }

    private static boolean isPatternExhaustedBeforeSource(String src, String pat, int srcIndex, int patIndex) {
        return srcIndex != src.length() && patIndex == pat.length();
    }

    private static boolean isSourceExhausted(String src, int srcIndex) {
        return srcIndex == src.length();
    }
}