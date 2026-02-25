package com.thealgorithms.conversions;

public final class TurkishToLatinConverter {

    private static final char[] TURKISH_CHARACTERS = {
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

    private static final char[] LATIN_CHARACTERS = {
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

    private TurkishToLatinConverter() {
        // Utility class; prevent instantiation
    }

    public static String convertTurkishToLatin(String text) {
        if (text == null) {
            return null;
        }

        String convertedText = text;
        for (int index = 0; index < TURKISH_CHARACTERS.length; index++) {
            convertedText =
                convertedText.replace(
                    TURKISH_CHARACTERS[index],
                    LATIN_CHARACTERS[index]
                );
        }
        return convertedText;
    }
}