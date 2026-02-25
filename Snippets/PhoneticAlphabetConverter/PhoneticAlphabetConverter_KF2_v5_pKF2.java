package com.thealgorithms.conversions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for converting alphanumeric text to its NATO phonetic alphabet
 * representation (plus English digit names).
 *
 * <p>Example:
 * <pre>{@code
 * PhoneticAlphabetConverter.textToPhonetic("AB1");
 * // returns "Alpha Bravo One"
 * }</pre>
 */
public final class PhoneticAlphabetConverter {

    /**
     * Immutable mapping from characters to their phonetic representations.
     *
     * <p>Letters: NATO phonetic alphabet (A → "Alpha", B → "Bravo", ...)<br>
     * Digits: English names ("0" → "Zero", "1" → "One", ...)
     */
    private static final Map<Character, String> PHONETIC_MAP;

    static {
        Map<Character, String> map = new HashMap<>();

        // NATO phonetic alphabet (letters)
        map.put('A', "Alpha");
        map.put('B', "Bravo");
        map.put('C', "Charlie");
        map.put('D', "Delta");
        map.put('E', "Echo");
        map.put('F', "Foxtrot");
        map.put('G', "Golf");
        map.put('H', "Hotel");
        map.put('I', "India");
        map.put('J', "Juliett");
        map.put('K', "Kilo");
        map.put('L', "Lima");
        map.put('M', "Mike");
        map.put('N', "November");
        map.put('O', "Oscar");
        map.put('P', "Papa");
        map.put('Q', "Quebec");
        map.put('R', "Romeo");
        map.put('S', "Sierra");
        map.put('T', "Tango");
        map.put('U', "Uniform");
        map.put('V', "Victor");
        map.put('W', "Whiskey");
        map.put('X', "X-ray");
        map.put('Y', "Yankee");
        map.put('Z', "Zulu");

        // Digits (English names)
        map.put('0', "Zero");
        map.put('1', "One");
        map.put('2', "Two");
        map.put('3', "Three");
        map.put('4', "Four");
        map.put('5', "Five");
        map.put('6', "Six");
        map.put('7', "Seven");
        map.put('8', "Eight");
        map.put('9', "Nine");

        PHONETIC_MAP = Collections.unmodifiableMap(map);
    }

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private PhoneticAlphabetConverter() {
        // Intentionally empty.
    }

    /**
     * Converts the given text to a space-separated phonetic representation.
     *
     * <p>Conversion rules:
     * <ul>
     *   <li>Letters → NATO phonetic words</li>
     *   <li>Digits → English word equivalents</li>
     *   <li>Whitespace → removed</li>
     *   <li>Other characters → kept as-is</li>
     * </ul>
     *
     * @param text input text; if {@code null} or empty, returns an empty string
     * @return space-separated phonetic representation of the input
     */
    public static String textToPhonetic(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        StringBuilder phonetic = new StringBuilder();

        for (char c : text.toUpperCase().toCharArray()) {
            if (Character.isWhitespace(c)) {
                continue;
            }
            String phoneticWord = PHONETIC_MAP.getOrDefault(c, String.valueOf(c));
            phonetic.append(phoneticWord).append(' ');
        }

        return phonetic.toString().trim();
    }
}