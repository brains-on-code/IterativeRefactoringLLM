package com.thealgorithms.bitmanipulation;

/**
 * Utility class for computing the two's complement of a binary number.
 *
 * <p>In two's complement representation, the negative of a binary number is obtained by:
 * <ol>
 *   <li>Computing the one's complement (inverting all bits).</li>
 *   <li>Adding 1 to the one's complement.</li>
 * </ol>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Two%27s_complement">Wikipedia - Two's Complement</a>
 */
public final class TwosComplement {

    private TwosComplement() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the two's complement of the given binary string.
     *
     * @param binary a non-empty string containing only '0' and '1'
     * @return the two's complement of {@code binary}
     * @throws IllegalArgumentException if {@code binary} contains characters other than '0' or '1'
     */
    public static String twosComplement(String binary) {
        validateBinaryString(binary);

        String onesComplement = computeOnesComplement(binary);
        return addOne(onesComplement);
    }

    /**
     * Validates that the input string consists only of binary digits.
     *
     * @param binary the string to validate
     * @throws IllegalArgumentException if the string is not a valid binary string
     */
    private static void validateBinaryString(String binary) {
        if (binary == null || binary.isEmpty() || !binary.matches("[01]+")) {
            throw new IllegalArgumentException("Input must be a non-empty string containing only '0' and '1'.");
        }
    }

    /**
     * Computes the one's complement of a binary string by flipping each bit.
     *
     * @param binary the original binary string
     * @return the one's complement of {@code binary}
     */
    private static String computeOnesComplement(String binary) {
        StringBuilder result = new StringBuilder(binary.length());
        for (char bit : binary.toCharArray()) {
            result.append(bit == '0' ? '1' : '0');
        }
        return result.toString();
    }

    /**
     * Adds one to a binary string.
     *
     * @param bits the binary string to which one will be added
     * @return the result of adding one to {@code bits}
     */
    private static String addOne(String bits) {
        StringBuilder result = new StringBuilder(bits);
        boolean carry = true;

        for (int i = bits.length() - 1; i >= 0 && carry; i--) {
            char currentBit = bits.charAt(i);
            if (currentBit == '1') {
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