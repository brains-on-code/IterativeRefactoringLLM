package com.thealgorithms.conversions;

/**
 * Converts Turkish characters to Latin characters.
 *
 * @author Özgün Gökşenli
 */
public final class TurkishToLatinConversion {

    private TurkishToLatinConversion() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts all Turkish characters in the given string to their Latin equivalents.
     *
     * Steps:
     * 1. Define Turkish characters and their corresponding Latin characters.
     * 2. Replace all Turkish characters with their corresponding Latin characters.
     * 3. Return the converted string.
     *
     * @param input the input string that may contain Turkish characters
     * @return a new string with Turkish characters converted to Latin
     */
    public static String convertTurkishToLatin(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        char[] turkishCharacters = new char[] {
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

        char[] latinCharacters = new char[] {
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
        for (int index = 0; index < turkishCharacters.length; index++) {
            result =
                result.replace(
                    String.valueOf(turkishCharacters[index]),
                    String.valueOf(latinCharacters[index])
                );
        }

        return result;
    }
}