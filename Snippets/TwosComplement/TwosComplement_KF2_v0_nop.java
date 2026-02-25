package com.thealgorithms.bitmanipulation;


public final class TwosComplement {
    private TwosComplement() {
    }


    public static String twosComplement(String binary) {
        if (!binary.matches("[01]+")) {
            throw new IllegalArgumentException("Input must contain only '0' and '1'.");
        }

        StringBuilder onesComplement = new StringBuilder();
        for (char bit : binary.toCharArray()) {
            onesComplement.append(bit == '0' ? '1' : '0');
        }

        StringBuilder twosComplement = new StringBuilder(onesComplement);
        boolean carry = true;

        for (int i = onesComplement.length() - 1; i >= 0 && carry; i--) {
            if (onesComplement.charAt(i) == '1') {
                twosComplement.setCharAt(i, '0');
            } else {
                twosComplement.setCharAt(i, '1');
                carry = false;
            }
        }

        if (carry) {
            twosComplement.insert(0, '1');
        }

        return twosComplement.toString();
    }
}
