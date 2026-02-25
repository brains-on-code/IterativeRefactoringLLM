package com.thealgorithms.dynamicprogramming;

public final class RegexMatching {

    private RegexMatching() {
        // Prevent instantiation
    }

    /**
     * Recursive wildcard matching using substring-based recursion.
     *
     * Supported pattern characters:
     *  - '?' matches exactly one character
     *  - '*' matches zero or more characters
     */
    public static boolean regexRecursion(String src, String pat) {
        // Both source and pattern are fully consumed
        if (src.length() == 0 && pat.length() == 0) {
            return true;
        }

        // Pattern exhausted but source still has characters
        if (src.length() != 0 && pat.length() == 0) {
            return false;
        }

        // Source exhausted but pattern remains:
        // valid only if the remaining pattern consists solely of '*'
        if (src.length() == 0) {
            for (int i = 0; i < pat.length(); i++) {
                if (pat.charAt(i) != '*') {
                    return false;
                }
            }
            return true;
        }

        char srcChar = src.charAt(0);
        char patChar = pat.charAt(0);

        String remainingSrc = src.substring(1);
        String remainingPat = pat.substring(1);

        if (srcChar == patChar || patChar == '?') {
            // Exact character match or single-character wildcard
            return regexRecursion(remainingSrc, remainingPat);
        } else if (patChar == '*') {
            // Try '*' as:
            //  - empty sequence
            //  - one-or-more characters
            boolean matchEmpty = regexRecursion(src, remainingPat);
            boolean matchMultiple = regexRecursion(remainingSrc, pat);
            return matchEmpty || matchMultiple;
        }

        return false;
    }

    /**
     * Recursive wildcard matching using indices (no substring allocations).
     */
    static boolean regexRecursion(String src, String pat, int srcIdx, int patIdx) {
        // Both source and pattern are fully consumed
        if (src.length() == srcIdx && pat.length() == patIdx) {
            return true;
        }

        // Pattern exhausted but source still has characters
        if (src.length() != srcIdx && pat.length() == patIdx) {
            return false;
        }

        // Source exhausted but pattern remains:
        // valid only if the remaining pattern consists solely of '*'
        if (src.length() == srcIdx) {
            for (int i = patIdx; i < pat.length(); i++) {
                if (pat.charAt(i) != '*') {
                    return false;
                }
            }
            return true;
        }

        char srcChar = src.charAt(srcIdx);
        char patChar = pat.charAt(patIdx);

        if (srcChar == patChar || patChar == '?') {
            // Exact character match or single-character wildcard
            return regexRecursion(src, pat, srcIdx + 1, patIdx + 1);
        } else if (patChar == '*') {
            // Try '*' as:
            //  - empty sequence
            //  - one-or-more characters
            boolean matchEmpty = regexRecursion(src, pat, srcIdx, patIdx + 1);
            boolean matchMultiple = regexRecursion(src, pat, srcIdx + 1, patIdx);
            return matchEmpty || matchMultiple;
        }

        return false;
    }

    /**
     * Recursive wildcard matching with memoization.
     *
     * strg[srcIdx][patIdx] encoding:
     *  0 = not computed
     *  1 = false
     *  2 = true
     */
    public static boolean regexRecursion(String src, String pat, int srcIdx, int patIdx, int[][] strg) {
        // Both source and pattern are fully consumed
        if (src.length() == srcIdx && pat.length() == patIdx) {
            return true;
        }

        // Pattern exhausted but source still has characters
        if (src.length() != srcIdx && pat.length() == patIdx) {
            return false;
        }

        // Source exhausted but pattern remains:
        // valid only if the remaining pattern consists solely of '*'
        if (src.length() == srcIdx) {
            for (int i = patIdx; i < pat.length(); i++) {
                if (pat.charAt(i) != '*') {
                    return false;
                }
            }
            return true;
        }

        // Use cached result if available
        if (strg[srcIdx][patIdx] != 0) {
            return strg[srcIdx][patIdx] == 2;
        }

        char srcChar = src.charAt(srcIdx);
        char patChar = pat.charAt(patIdx);

        boolean result;
        if (srcChar == patChar || patChar == '?') {
            // Exact character match or single-character wildcard
            result = regexRecursion(src, pat, srcIdx + 1, patIdx + 1, strg);
        } else if (patChar == '*') {
            // Try '*' as:
            //  - empty sequence
            //  - one-or-more characters
            boolean matchEmpty = regexRecursion(src, pat, srcIdx, patIdx + 1, strg);
            boolean matchMultiple = regexRecursion(src, pat, srcIdx + 1, patIdx, strg);
            result = matchEmpty || matchMultiple;
        } else {
            result = false;
        }

        strg[srcIdx][patIdx] = result ? 2 : 1;
        return result;
    }

    /**
     * Bottom-up dynamic programming implementation of wildcard matching.
     */
    static boolean regexBU(String src, String pat) {
        boolean[][] dp = new boolean[src.length() + 1][pat.length() + 1];

        // Empty source and empty pattern match
        dp[src.length()][pat.length()] = true;

        for (int row = src.length(); row >= 0; row--) {
            for (int col = pat.length() - 1; col >= 0; col--) {
                if (row == src.length()) {
                    // Source exhausted: remaining pattern must be all '*'
                    dp[row][col] = pat.charAt(col) == '*' && dp[row][col + 1];
                } else {
                    char srcChar = src.charAt(row);
                    char patChar = pat.charAt(col);

                    boolean result;
                    if (srcChar == patChar || patChar == '?') {
                        // Exact character match or single-character wildcard
                        result = dp[row + 1][col + 1];
                    } else if (patChar == '*') {
                        // Try '*' as:
                        //  - empty sequence
                        //  - one-or-more characters
                        boolean matchEmpty = dp[row][col + 1];
                        boolean matchMultiple = dp[row + 1][col];
                        result = matchEmpty || matchMultiple;
                    } else {
                        result = false;
                    }

                    dp[row][col] = result;
                }
            }
        }

        return dp[0][0];
    }
}