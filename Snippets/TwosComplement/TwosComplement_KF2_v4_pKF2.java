package com.thealgorithms.bitmanipulation;

public final class TwosComplement {

    private TwosComplement() {
        // Prevent instantiation of utility class
    }

    /**
     * Computes the two's complement of a binary string.
     *
     * @param binary a non-empty string containing only '0' and '1'
     * @return the two's complement of {@code binary}
     * @throws IllegalArgumentException if {@code binary} is null, empty, or contains
     *                                  characters other than '0' or '1'
     */
    public static String twosComplement(String binary) {
        validateBinaryString(binary);

        String onesComplement = computeOnesComplement(binary);
        return addOneToBinary(onesComplement);
    }

    /**
     * Validates that the input is a non-empty binary string.
     *
     * @param binary the string to validate
     * @throws IllegalArgumentException if the string is null, empty, or contains
     *                                  characters other than '0' or '1'
     */
    private static void validateBinaryString(String binary) {
        if (binary == null || binary.isEmpty() || !binary.matches("[01]+")) {
            throw new IllegalArgumentException(
                "Input must be a non-empty string containing only '0' and '1'."
            );
        }
    }

    /**
     * Computes the one's complement of a binary string by flipping each bit.
     *
     * @param binary a valid binary string
     * @return the one's complement of {@code binary}
     */
    private static String computeOnesComplement(String binary) {
        StringBuilder onesComplement = new StringBuilder(binary.length());
        for (int i = 0; i < binary.length(); i++) {
            char bit = binary.charAt(i);
            onesComplement.append(bit == '0' ? '1' : '0');
        }
        return onesComplement.toString();
    }

    /**
     * Adds one to a binary string.
     *
     * @param binary a valid binary string
     * @return {@code binary} plus one, as a binary string
     */
    private static String addOneToBinary(String binary) {
        StringBuilder result = new StringBuilder(binary);
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