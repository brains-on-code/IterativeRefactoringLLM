package com.thealgorithms.dynamicprogramming;

/**
 * Given a text and wildcard pattern implement a wildcard pattern matching
 * algorithm that finds if wildcard is matched with text. The matching should
 * cover the entire text ?-> matches single characters *-> match the sequence of
 * characters
 *
 * For calculation of Time and Space Complexity. Let N be length of src and M be length of pat
 *
 * Memoization vs Tabulation : https://www.geeksforgeeks.org/tabulation-vs-memoization/
 * Question Link : https://practice.geeksforgeeks.org/problems/wildcard-pattern-matching/1
 */
public final class RegexMatching {
    private RegexMatching() {
    }

    /**
     * Method 1: Determines if the given source string matches the given pattern using a recursive approach.
     * This method directly applies recursion to check if the source string matches the pattern, considering
     * the wildcards '?' and '*'.
     *
     * Time Complexity: O(2^(N+M)), where N is the length of the source string and M is the length of the pattern.
     * Space Complexity: O(N + M) due to the recursion stack.
     *
     * @param source The source string to be matched against the pattern.
     * @param pattern The pattern containing wildcards ('*' matches a sequence of characters, '?' matches a single character).
     * @return {@code true} if the source string matches the pattern, {@code false} otherwise.
     */
    public static boolean regexRecursion(String source, String pattern) {
        if (source.length() == 0 && pattern.length() == 0) {
            return true;
        }
        if (source.length() != 0 && pattern.length() == 0) {
            return false;
        }
        if (source.length() == 0 && pattern.length() != 0) {
            for (int patternIndex = 0; patternIndex < pattern.length(); patternIndex++) {
                if (pattern.charAt(patternIndex) != '*') {
                    return false;
                }
            }
            return true;
        }

        char currentSourceChar = source.charAt(0);
        char currentPatternChar = pattern.charAt(0);

        String remainingSource = source.substring(1);
        String remainingPattern = pattern.substring(1);

        boolean isMatch;
        if (currentSourceChar == currentPatternChar || currentPatternChar == '?') {
            isMatch = regexRecursion(remainingSource, remainingPattern);
        } else if (currentPatternChar == '*') {
            boolean matchEmpty = regexRecursion(source, remainingPattern);
            boolean matchMultiple = regexRecursion(remainingSource, pattern);
            isMatch = matchEmpty || matchMultiple;
        } else {
            isMatch = false;
        }
        return isMatch;
    }

    /**
     * Method 2: Determines if the given source string matches the given pattern using recursion.
     * This method utilizes a virtual index for both the source string and the pattern to manage the recursion.
     *
     * Time Complexity: O(2^(N+M)) where N is the length of the source string and M is the length of the pattern.
     * Space Complexity: O(N + M) due to the recursion stack.
     *
     * @param source The source string to be matched against the pattern.
     * @param pattern The pattern containing wildcards ('*' matches a sequence of characters, '?' matches a single character).
     * @param sourceIndex The current index in the source string.
     * @param patternIndex The current index in the pattern.
     * @return {@code true} if the source string matches the pattern, {@code false} otherwise.
     */
    static boolean regexRecursion(String source, String pattern, int sourceIndex, int patternIndex) {
        if (source.length() == sourceIndex && pattern.length() == patternIndex) {
            return true;
        }
        if (source.length() != sourceIndex && pattern.length() == patternIndex) {
            return false;
        }
        if (source.length() == sourceIndex && pattern.length() != patternIndex) {
            for (int currentPatternIndex = patternIndex; currentPatternIndex < pattern.length(); currentPatternIndex++) {
                if (pattern.charAt(currentPatternIndex) != '*') {
                    return false;
                }
            }
            return true;
        }

        char currentSourceChar = source.charAt(sourceIndex);
        char currentPatternChar = pattern.charAt(patternIndex);

        boolean isMatch;
        if (currentSourceChar == currentPatternChar || currentPatternChar == '?') {
            isMatch = regexRecursion(source, pattern, sourceIndex + 1, patternIndex + 1);
        } else if (currentPatternChar == '*') {
            boolean matchEmpty = regexRecursion(source, pattern, sourceIndex, patternIndex + 1);
            boolean matchMultiple = regexRecursion(source, pattern, sourceIndex + 1, patternIndex);
            isMatch = matchEmpty || matchMultiple;
        } else {
            isMatch = false;
        }
        return isMatch;
    }

    /**
     * Method 3: Determines if the given source string matches the given pattern using top-down dynamic programming (memoization).
     * This method utilizes memoization to store intermediate results, reducing redundant computations and improving efficiency.
     *
     * Time Complexity: O(N * M), where N is the length of the source string and M is the length of the pattern.
     * Space Complexity: O(N * M) for the memoization table, plus additional space for the recursion stack.
     *
     * @param source The source string to be matched against the pattern.
     * @param pattern The pattern containing wildcards ('*' matches a sequence of characters, '?' matches a single character).
     * @param sourceIndex The current index in the source string.
     * @param patternIndex The current index in the pattern.
     * @param memo A 2D array used for memoization to store the results of subproblems.
     * @return {@code true} if the source string matches the pattern, {@code false} otherwise.
     */
    public static boolean regexRecursion(String source, String pattern, int sourceIndex, int patternIndex, int[][] memo) {
        if (source.length() == sourceIndex && pattern.length() == patternIndex) {
            return true;
        }
        if (source.length() != sourceIndex && pattern.length() == patternIndex) {
            return false;
        }
        if (source.length() == sourceIndex && pattern.length() != patternIndex) {
            for (int currentPatternIndex = patternIndex; currentPatternIndex < pattern.length(); currentPatternIndex++) {
                if (pattern.charAt(currentPatternIndex) != '*') {
                    return false;
                }
            }
            return true;
        }
        if (memo[sourceIndex][patternIndex] != 0) {
            return memo[sourceIndex][patternIndex] != 1;
        }

        char currentSourceChar = source.charAt(sourceIndex);
        char currentPatternChar = pattern.charAt(patternIndex);

        boolean isMatch;
        if (currentSourceChar == currentPatternChar || currentPatternChar == '?') {
            isMatch = regexRecursion(source, pattern, sourceIndex + 1, patternIndex + 1, memo);
        } else if (currentPatternChar == '*') {
            boolean matchEmpty = regexRecursion(source, pattern, sourceIndex, patternIndex + 1, memo);
            boolean matchMultiple = regexRecursion(source, pattern, sourceIndex + 1, patternIndex, memo);
            isMatch = matchEmpty || matchMultiple;
        } else {
            isMatch = false;
        }
        memo[sourceIndex][patternIndex] = isMatch ? 2 : 1;
        return isMatch;
    }

    /**
     * Method 4: Determines if the given source string matches the given pattern using bottom-up dynamic programming (tabulation).
     * This method builds a solution iteratively by filling out a table, where each cell represents whether a substring
     * of the source string matches a substring of the pattern.
     *
     * Time Complexity: O(N * M), where N is the length of the source string and M is the length of the pattern.
     * Space Complexity: O(N * M) for the table used in the tabulation process.
     *
     * @param source The source string to be matched against the pattern.
     * @param pattern The pattern containing wildcards ('*' matches a sequence of characters, '?' matches a single character).
     * @return {@code true} if the source string matches the pattern, {@code false} otherwise.
     */
    static boolean regexBU(String source, String pattern) {
        boolean[][] matchTable = new boolean[source.length() + 1][pattern.length() + 1];
        matchTable[source.length()][pattern.length()] = true;

        for (int sourceIndex = source.length(); sourceIndex >= 0; sourceIndex--) {
            for (int patternIndex = pattern.length() - 1; patternIndex >= 0; patternIndex--) {
                if (sourceIndex == source.length()) {
                    if (pattern.charAt(patternIndex) == '*') {
                        matchTable[sourceIndex][patternIndex] = matchTable[sourceIndex][patternIndex + 1];
                    } else {
                        matchTable[sourceIndex][patternIndex] = false;
                    }
                } else {
                    char currentSourceChar = source.charAt(sourceIndex);
                    char currentPatternChar = pattern.charAt(patternIndex);

                    boolean isMatch;
                    if (currentSourceChar == currentPatternChar || currentPatternChar == '?') {
                        isMatch = matchTable[sourceIndex + 1][patternIndex + 1];
                    } else if (currentPatternChar == '*') {
                        boolean matchEmpty = matchTable[sourceIndex][patternIndex + 1];
                        boolean matchMultiple = matchTable[sourceIndex + 1][patternIndex];
                        isMatch = matchEmpty || matchMultiple;
                    } else {
                        isMatch = false;
                    }
                    matchTable[sourceIndex][patternIndex] = isMatch;
                }
            }
        }
        return matchTable[0][0];
    }
}