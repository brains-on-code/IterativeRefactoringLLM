package com.thealgorithms.conversions;

/**
 * Converts Turkish characters to Latin characters.
 *
 * @author Özgün Gökşenli
 */
public final class TurkishToLatinConversion {

    private static final char[] TURKISH_LETTERS = {
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

    private static final char[] LATIN_LETTERS = {
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

    private TurkishToLatinConversion() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts all Turkish characters in the given string to their Latin equivalents.
     *
     * @param input the input string that may contain Turkish characters
     * @return a new string with Turkish characters converted to Latin
     */
    public static String convertTurkishToLatin(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String convertedText = input;
        for (int index = 0; index < TURKISH_LETTERS.length; index++) {
            convertedText =
                convertedText.replace(
                    String.valueOf(TURKISH_LETTERS[index]),
                    String.valueOf(LATIN_LETTERS[index])
                );
        }

        return convertedText;
    }
}