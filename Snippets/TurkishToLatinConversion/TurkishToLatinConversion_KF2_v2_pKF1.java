package com.thealgorithms.conversions;

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

    private static final char[] LATIN_EQUIVALENTS = {
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

    public static String convertTurkishToLatin(String input) {
        if (input == null) {
            return null;
        }

        String convertedText = input;
        for (int i = 0; i < TURKISH_LETTERS.length; i++) {
            convertedText =
                convertedText.replace(
                    TURKISH_LETTERS[i],
                    LATIN_EQUIVALENTS[i]
                );
        }
        return convertedText;
    }
}