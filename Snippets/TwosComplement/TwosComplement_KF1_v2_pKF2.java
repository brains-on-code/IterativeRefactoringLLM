package com.thealgorithms.bitmanipulation;

/**
 * Utility class for operations on binary strings.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the two's complement of a binary string.
     *
     * <p>The input must be a non-empty string consisting only of the characters
     * '0' and '1'. The method returns the two's complement representation of
     * the input, preserving the bit-width unless an overflow requires an
     * additional leading bit.</p>
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
     * @return the two's complement of the input binary string
     * @throws IllegalArgumentException if the input contains characters other than '0' or '1'
     */
    public static String method1(String binaryString) {
        validateBinaryString(binaryString);

        String onesComplement = invertBits(binaryString);
        return addOne(onesComplement);
    }

    private static void validateBinaryString(String binaryString) {
        if (binaryString == null || binaryString.isEmpty() || !binaryString.matches("[01]+")) {
            throw new IllegalArgumentException("Input must be a non-empty string containing only '0' and '1'.");
        }
    }

    /**
     * Returns the one's complement of the given binary string
     * (all bits inverted).
     */
    private static String invertBits(String binaryString) {
        StringBuilder result = new StringBuilder(binaryString.length());
        for (char bit : binaryString.toCharArray()) {
            result.append(bit == '0' ? '1' : '0');
        }
        return result.toString();
    }

    /**
     * Adds 1 to the given binary string and returns the result.
     */
    private static String addOne(String binaryString) {
        StringBuilder result = new StringBuilder(binaryString);
        boolean carry = true;

        for (int i = result.length() - 1; i >= 0 && carry; i--) {
            if (result.charAt(i) == '1') {
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