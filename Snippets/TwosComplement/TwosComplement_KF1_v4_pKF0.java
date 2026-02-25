package com.thealgorithms.bitmanipulation;

/**
 * Utility class for bit manipulation operations.
 */
public final class TwoComplementCalculator {

    private static final String BINARY_PATTERN = "[01]+";
    private static final String INVALID_INPUT_MESSAGE =
        "Input must be a non-empty string containing only '0' and '1'.";

    private TwoComplementCalculator() {
        // Prevent instantiation
    }

    /**
     * Computes the two's complement of a binary string.
     *
     * The input must be a non-empty string consisting only of '0' and '1'.
     *
     * @param binaryString the binary string to negate
     * @return the two's complement of the input binary string
     * @throws IllegalArgumentException if the input is null, empty, or contains characters other than '0' or '1'
     */
    public static String computeTwosComplement(String binaryString) {
        validateBinaryString(binaryString);

        String onesComplement = invertBits(binaryString);
        return addOne(onesComplement);
    }

    private static void validateBinaryString(String binaryString) {
        if (binaryString == null || binaryString.isEmpty() || !binaryString.matches(BINARY_PATTERN)) {
            throw new IllegalArgumentException(INVALID_INPUT_MESSAGE);
        }
    }

    private static String invertBits(String binaryString) {
        StringBuilder inverted = new StringBuilder(binaryString.length());
        for (int i = 0; i < binaryString.length(); i++) {
            inverted.append(binaryString.charAt(i) == '0' ? '1' : '0');
        }
        return inverted.toString();
    }

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