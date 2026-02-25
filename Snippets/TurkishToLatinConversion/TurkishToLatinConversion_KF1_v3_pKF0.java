package com.thealgorithms.conversions;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for character conversions.
 */
public final class TurkishCharacterNormalizer {

    private static final char[] TURKISH_CHARS = {
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

    private static final Map<Character, Character> TURKISH_TO_LATIN_MAP = buildTurkishToLatinMap();

    private TurkishCharacterNormalizer() {
        // Prevent instantiation
    }

    /**
     * Replaces specific Turkish characters in the input string with their
     * closest Latin equivalents.
     *
     * @param input the string to normalize
     * @return a new string with Turkish characters replaced
     */
    public static String normalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder normalized = new StringBuilder(input.length());

        for (char ch : input.toCharArray()) {
            normalized.append(TURKISH_TO_LATIN_MAP.getOrDefault(ch, ch));
        }

        return normalized.toString();
    }

    private static Map<Character, Character> buildTurkishToLatinMap() {
        Map<Character, Character> map = new HashMap<>(TURKISH_CHARS.length);
        for (int i = 0; i < TURKISH_CHARS.length; i++) {
            map.put(TURKISH_CHARS[i], LATIN_EQUIVALENTS[i]);
        }
        return map;
    }
}