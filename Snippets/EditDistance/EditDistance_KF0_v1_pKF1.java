package com.thealgorithms.dynamicprogramming;

/**
 * A DynamicProgramming based solution for Edit Distance problem In Java
 * Description of Edit Distance with an Example:
 *
 * <p>
 * Edit distance is a way of quantifying how dissimilar two strings (e.g.,
 * words) are to one another, by counting the minimum number of operations
 * required to transform one string into the other. The distance operations are
 * the removal, insertion, or substitution of a character in the string.
 *
 * <p>
 *
 * <p>
 * The Distance between "kitten" and "sitting" is 3. A minimal edit script that
 * transforms the former into the latter is:
 *
 * <p>
 * kitten → sitten (substitution of "s" for "k") sitten → sittin (substitution
 * of "i" for "e") sittin → sitting (insertion of "g" at the end).
 *
 * @author SUBHAM SANGHAI
 */
public final class EditDistance {

    private EditDistance() {
    }

    public static int minDistance(String source, String target) {
        int sourceLength = source.length();
        int targetLength = target.length();

        // +1 because we include the empty-prefix row/column
        int[][] distanceTable = new int[sourceLength + 1][targetLength + 1];

        // If target string is empty, remove all characters from source
        for (int sourceIndex = 0; sourceIndex <= sourceLength; sourceIndex++) {
            distanceTable[sourceIndex][0] = sourceIndex;
        }

        // If source string is empty, insert all characters of target
        for (int targetIndex = 0; targetIndex <= targetLength; targetIndex++) {
            distanceTable[0][targetIndex] = targetIndex;
        }

        // Fill DP table
        for (int sourceIndex = 0; sourceIndex < sourceLength; sourceIndex++) {
            char sourceChar = source.charAt(sourceIndex);
            for (int targetIndex = 0; targetIndex < targetLength; targetIndex++) {
                char targetChar = target.charAt(targetIndex);

                if (sourceChar == targetChar) {
                    distanceTable[sourceIndex + 1][targetIndex + 1] = distanceTable[sourceIndex][targetIndex];
                } else {
                    int replaceCost = distanceTable[sourceIndex][targetIndex] + 1;
                    int insertCost = distanceTable[sourceIndex][targetIndex + 1] + 1;
                    int deleteCost = distanceTable[sourceIndex + 1][targetIndex] + 1;

                    int minCost = Math.min(replaceCost, insertCost);
                    minCost = Math.min(deleteCost, minCost);
                    distanceTable[sourceIndex + 1][targetIndex + 1] = minCost;
                }
            }
        }

        return distanceTable[sourceLength][targetLength];
    }

    // edit distance problem (recursive with memoization)
    public static int editDistance(String source, String target) {
        int[][] memo = new int[source.length() + 1][target.length() + 1];
        return editDistance(source, target, memo);
    }

    public static int editDistance(String source, String target, int[][] memo) {
        int sourceLength = source.length();
        int targetLength = target.length();

        if (memo[sourceLength][targetLength] > 0) {
            return memo[sourceLength][targetLength];
        }

        if (sourceLength == 0) {
            memo[sourceLength][targetLength] = targetLength;
            return memo[sourceLength][targetLength];
        }

        if (targetLength == 0) {
            memo[sourceLength][targetLength] = sourceLength;
            return memo[sourceLength][targetLength];
        }

        if (source.charAt(0) == target.charAt(0)) {
            memo[sourceLength][targetLength] =
                    editDistance(source.substring(1), target.substring(1), memo);
        } else {
            int insertCost = editDistance(source, target.substring(1), memo);
            int deleteCost = editDistance(source.substring(1), target, memo);
            int replaceCost = editDistance(source.substring(1), target.substring(1), memo);
            memo[sourceLength][targetLength] = 1 + Math.min(insertCost, Math.min(deleteCost, replaceCost));
        }

        return memo[sourceLength][targetLength];
    }
}