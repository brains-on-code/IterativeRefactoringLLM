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

    private static final Map<Character, String> PHONETIC_MAP = createPhoneticMap();

    private PhoneticAlphabetConverter() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    private static Map<Character, String> createPhoneticMap() {
        Map<Character, String> map = new HashMap<>();

        // Letters A–Z
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

        // Digits 0–9
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

        return map;
    }

    /**
     * Converts the given text to its NATO phonetic alphabet representation.
     *
     * <p>Behavior:
     * <ul>
     *   <li>Whitespace characters are ignored.</li>
     *   <li>Characters without a defined phonetic mapping are included as-is.</li>
     * </ul>
     *
     * @param text the text to convert; must not be {@code null}
     * @return a space-separated string of phonetic words
     * @throws NullPointerException if {@code text} is {@code null}
     */
    public static String textToPhonetic(String text) {
        StringBuilder phonetic = new StringBuilder();

        for (char character : text.toUpperCase().toCharArray()) {
            if (Character.isWhitespace(character)) {
                continue;
            }

            String phoneticWord = PHONETIC_MAP.getOrDefault(character, String.valueOf(character));
            phonetic.append(phoneticWord).append(' ');
        }

        return phonetic.toString().trim();
    }
}