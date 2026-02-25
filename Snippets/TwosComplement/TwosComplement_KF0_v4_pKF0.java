package com.thealgorithms.bitmanipulation;

/**
 * This class provides a method to compute the Two's Complement of a given binary number.
 *
 * <p>In two's complement representation, a binary number's negative value is obtained
 * by taking the one's complement (inverting all bits) and then adding 1 to the result.
 *
 * <p>For more information on Two's Complement:
 * @see <a href="https://en.wikipedia.org/wiki/Two%27s_complement">Wikipedia - Two's Complement</a>
 */
public final class TwosComplement {

    private static final String BINARY_PATTERN = "[01]+";
    private static final String INVALID_INPUT_MESSAGE =
        "Input must be a non-empty string containing only '0' and '1'.";

    private TwosComplement() {
        // Utility class; prevent instantiation.
    }

    /**
     * Computes the Two's Complement of the given binary string.
     *
     * <ol>
     *     <li>Validate the input.</li>
     *     <li>Compute the one's complement (invert all bits).</li>
     *     <li>Add 1 to the one's complement to get the two's complement.</li>
     * </ol>
     *
     * @param binary The binary number as a string (only '0' and '1' characters allowed).
     * @return The two's complement of the input binary string as a new binary string.
     * @throws IllegalArgumentException If the input contains non-binary characters or is null/empty.
     */
    public static String twosComplement(String binary) {
        validateBinaryInput(binary);
        String onesComplement = computeOnesComplement(binary);
        return addOne(onesComplement);
    }

    private static void validateBinaryInput(String binary) {
        if (binary == null || binary.isEmpty() || !binary.matches(BINARY_PATTERN)) {
            throw new IllegalArgumentException(INVALID_INPUT_MESSAGE);
        }
    }

    private static String computeOnesComplement(String binary) {
        StringBuilder onesComplement = new StringBuilder(binary.length());
        for (char bit : binary.toCharArray()) {
            onesComplement.append(bit == '0' ? '1' : '0');
        }
        return onesComplement.toString();
    }

    private static String addOne(String bits) {
        StringBuilder result = new StringBuilder(bits.length());
        boolean carry = true;

        for (int i = bits.length() - 1; i >= 0; i--) {
            char bit = bits.charAt(i);
            if (carry) {
                if (bit == '1') {
                    result.append('0');
                } else {
                    result.append('1');
                    carry = false;
                }
            } else {
                result.append(bit);
            }
        }

        if (carry) {
            result.append('1');
        }

        return result.reverse().toString();
    }
}