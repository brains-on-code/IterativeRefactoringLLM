package com.thealgorithms.conversions;


public final class HexToOct {
    private HexToOct() {
    }


    public static int hexToDecimal(String hex) {
        String hexDigits = "0123456789ABCDEF";
        hex = hex.toUpperCase();
        int decimalValue = 0;

        for (int i = 0; i < hex.length(); i++) {
            char hexChar = hex.charAt(i);
            int digitValue = hexDigits.indexOf(hexChar);
            decimalValue = 16 * decimalValue + digitValue;
        }

        return decimalValue;
    }


    public static int decimalToOctal(int decimal) {
        int octalValue = 0;
        int placeValue = 1;

        while (decimal > 0) {
            int remainder = decimal % 8;
            octalValue += remainder * placeValue;
            decimal /= 8;
            placeValue *= 10;
        }

        return octalValue;
    }


    public static int hexToOctal(String hex) {
        int decimalValue = hexToDecimal(hex);
        return decimalToOctal(decimalValue);
    }
}
