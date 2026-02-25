package com.thealgorithms.bitmanipulation;

/**
 * Utility methods for working with binary strings.
 */
public final class BinaryStringUtils {

    private static final String BINARY_PATTERN = "[01]+";
    private static final String INVALID_BINARY_MESSAGE =
        "Input must be a non-empty string containing only '0' and '1'.";

    private BinaryStringUtils() {
        // Utility class; prevent instantiation.
    }

    /**
     * Computes the two's complement of a binary string.
     *
     * <p>The input must be a non-empty string of '0' and '1' characters.
     * The result preserves the original bit-width unless an overflow
     * requires an additional leading bit.</p>
     *
     * <p>Examples:
     * <ul>
     *   <li>"0"    -> "0"</li>
     *   <li>"1"    -> "1"</li>
     *   <li>"01"   -> "11"</li>
     *   <li>"0001" -> "1111"</li>
     * </ul>
     * </p>
     *
     * @param binaryString the input binary string
     * @return the two's complement of {@code binaryString}
     * @throws IllegalArgumentException if {@code binaryString} is null, empty,
     *                                  or contains characters other than '0' or '1'
     */
    public static String twosComplement(String binaryString) {
        validateBinaryString(binaryString);
        String onesComplement = onesComplement(binaryString);
        return addOne(onesComplement);
    }

    /**
     * Ensures the given string is a non-empty binary string.
     *
     * @param binaryString the string to validate
     * @throws IllegalArgumentException if the string is null, empty, or not binary
     */
    private static void validateBinaryString(String binaryString) {
        if (binaryString == null || binaryString.isEmpty() || !binaryString.matches(BINARY_PATTERN)) {
            throw new IllegalArgumentException(INVALID_BINARY_MESSAGE);
        }
    }

    /**
     * Returns the one's complement (bitwise inversion) of the given binary string.
     *
     * @param binaryString the input binary string
     * @return the one's complement of {@code binaryString}
     */
    private static String onesComplement(String binaryString) {
        StringBuilder result = new StringBuilder(binaryString.length());
        for (char bit : binaryString.toCharArray()) {
            result.append(bit == '0' ? '1' : '0');
        }
        return result.toString();
    }

    /**
     * Adds 1 to the given binary string.
     *
     * @param binaryString the input binary string
     * @return {@code binaryString} plus 1, as a binary string
     */
    private static String addOne(String binaryString) {
        StringBuilder result = new StringBuilder(binaryString);
        boolean carry = true;

        for (int i = result.length() - 1; i >= 0 && carry; i--) {
            char bit = result.charAt(i);
            if (bit == '1') {
                result.setCharAt(i, '0');
            } else {
                result.setCharAt(i, '1');
                carry = false;
            }
        }

        if (carry) {
            result.insert(0, '1');
        }

        return result.toString();
    }
}