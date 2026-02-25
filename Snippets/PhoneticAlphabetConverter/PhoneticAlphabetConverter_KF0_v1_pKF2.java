package com.thealgorithms.conversions;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for converting text to the NATO phonetic alphabet.
 *
 * <p>Examples:
 * <ul>
 *   <li>{@code "ABC" -> "Alpha Bravo Charlie"}</li>
 *   <li>{@code "Hello" -> "Hotel Echo Lima Lima Oscar"}</li>
 *   <li>{@code "123" -> "One Two Three"}</li>
 *   <li>{@code "A1B2C3" -> "Alpha One Bravo Two Charlie Three"}</li>
 * </ul>
 */
public final class PhoneticAlphabetConverter {

    private PhoneticAlphabetConverter() {
        // Utility class; prevent instantiation
    }

    private static final Map<Character, String> PHONETIC_MAP = new HashMap<>();

    static {
        PHONETIC_MAP.put('A', "Alpha");
        PHONETIC_MAP.put('B', "Bravo");
        PHONETIC_MAP.put('C', "Charlie");
        PHONETIC_MAP.put('D', "Delta");
        PHONETIC_MAP.put('E', "Echo");
        PHONETIC_MAP.put('F', "Foxtrot");
        PHONETIC_MAP.put('G', "Golf");
        PHONETIC_MAP.put('H', "Hotel");
        PHONETIC_MAP.put('I', "India");
        PHONETIC_MAP.put('J', "Juliett");
        PHONETIC_MAP.put('K', "Kilo");
        PHONETIC_MAP.put('L', "Lima");
        PHONETIC_MAP.put('M', "Mike");
        PHONETIC_MAP.put('N', "November");
        PHONETIC_MAP.put('O', "Oscar");
        PHONETIC_MAP.put('P', "Papa");
        PHONETIC_MAP.put('Q', "Quebec");
        PHONETIC_MAP.put('R', "Romeo");
        PHONETIC_MAP.put('S', "Sierra");
        PHONETIC_MAP.put('T', "Tango");
        PHONETIC_MAP.put('U', "Uniform");
        PHONETIC_MAP.put('V', "Victor");
        PHONETIC_MAP.put('W', "Whiskey");
        PHONETIC_MAP.put('X', "X-ray");
        PHONETIC_MAP.put('Y', "Yankee");
        PHONETIC_MAP.put('Z', "Zulu");
        PHONETIC_MAP.put('0', "Zero");
        PHONETIC_MAP.put('1', "One");
        PHONETIC_MAP.put('2', "Two");
        PHONETIC_MAP.put('3', "Three");
        PHONETIC_MAP.put('4', "Four");
        PHONETIC_MAP.put('5', "Five");
        PHONETIC_MAP.put('6', "Six");
        PHONETIC_MAP.put('7', "Seven");
        PHONETIC_MAP.put('8', "Eight");
        PHONETIC_MAP.put('9', "Nine");
    }

    /**
     * Converts the given text to its NATO phonetic alphabet representation.
     *
     * <p>Whitespace characters are ignored. Characters without a defined
     * phonetic mapping are included as-is.
     *
     * @param text the text to convert; must not be {@code null}
     * @return a space-separated string of phonetic words
     */
    public static String textToPhonetic(String text) {
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