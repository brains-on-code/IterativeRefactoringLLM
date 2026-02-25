package com.thealgorithms.conversions;

/**
 * Utility class for character conversions.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Replaces specific extended Latin characters (primarily Turkish letters)
     * with their closest ASCII equivalents.
     *
     * @param input the string to normalize
     * @return a new string with extended characters replaced by ASCII ones
     */
    public static String method1(String input) {
        char[] sourceChars = new char[] {
            0x131, // ı
            0x130, // İ
            0xFC,  // ü
            0xDC,  // Ü
            0xF6,  // ö
            0xD6,  // Ö
            0x15F, // ş
            0x15E, // Ş
            0xE7,  // ç
            0xC7,  // Ç
            0x11F, // ğ
            0x11E  // Ğ
        };

        char[] targetChars = new char[] {
            'i',
            'I',
            'u',
            'U',
            'o',
            'O',
            's',
            'S',
            'c',
            'C',
            'g',
            'G'
        };

        String result = input;
        for (int i = 0; i < sourceChars.length; i++) {
            result = result.replace(String.valueOf(sourceChars[i]), String.valueOf(targetChars[i]));
        }

        return result;
    }
}