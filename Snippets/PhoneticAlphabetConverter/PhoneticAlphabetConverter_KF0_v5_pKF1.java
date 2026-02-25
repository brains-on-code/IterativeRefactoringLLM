package com.thealgorithms.conversions;

import java.util.HashMap;
import java.util.Map;

/**
 * Converts text to the NATO phonetic alphabet.
 * Examples:
 * "ABC" -> "Alpha Bravo Charlie"
 * "Hello" -> "Hotel Echo Lima Lima Oscar"
 * "123" -> "One Two Three"
 * "A1B2C3" -> "Alpha One Bravo Two Charlie Three"
 *
 * @author Hardvan
 */
public final class PhoneticAlphabetConverter {

    private PhoneticAlphabetConverter() {
    }

    private static final Map<Character, String> NATO_PHONETIC_ALPHABET = new HashMap<>();

    static {
        NATO_PHONETIC_ALPHABET.put('A', "Alpha");
        NATO_PHONETIC_ALPHABET.put('B', "Bravo");
        NATO_PHONETIC_ALPHABET.put('C', "Charlie");
        NATO_PHONETIC_ALPHABET.put('D', "Delta");
        NATO_PHONETIC_ALPHABET.put('E', "Echo");
        NATO_PHONETIC_ALPHABET.put('F', "Foxtrot");
        NATO_PHONETIC_ALPHABET.put('G', "Golf");
        NATO_PHONETIC_ALPHABET.put('H', "Hotel");
        NATO_PHONETIC_ALPHABET.put('I', "India");
        NATO_PHONETIC_ALPHABET.put('J', "Juliett");
        NATO_PHONETIC_ALPHABET.put('K', "Kilo");
        NATO_PHONETIC_ALPHABET.put('L', "Lima");
        NATO_PHONETIC_ALPHABET.put('M', "Mike");
        NATO_PHONETIC_ALPHABET.put('N', "November");
        NATO_PHONETIC_ALPHABET.put('O', "Oscar");
        NATO_PHONETIC_ALPHABET.put('P', "Papa");
        NATO_PHONETIC_ALPHABET.put('Q', "Quebec");
        NATO_PHONETIC_ALPHABET.put('R', "Romeo");
        NATO_PHONETIC_ALPHABET.put('S', "Sierra");
        NATO_PHONETIC_ALPHABET.put('T', "Tango");
        NATO_PHONETIC_ALPHABET.put('U', "Uniform");
        NATO_PHONETIC_ALPHABET.put('V', "Victor");
        NATO_PHONETIC_ALPHABET.put('W', "Whiskey");
        NATO_PHONETIC_ALPHABET.put('X', "X-ray");
        NATO_PHONETIC_ALPHABET.put('Y', "Yankee");
        NATO_PHONETIC_ALPHABET.put('Z', "Zulu");
        NATO_PHONETIC_ALPHABET.put('0', "Zero");
        NATO_PHONETIC_ALPHABET.put('1', "One");
        NATO_PHONETIC_ALPHABET.put('2', "Two");
        NATO_PHONETIC_ALPHABET.put('3', "Three");
        NATO_PHONETIC_ALPHABET.put('4', "Four");
        NATO_PHONETIC_ALPHABET.put('5', "Five");
        NATO_PHONETIC_ALPHABET.put('6', "Six");
        NATO_PHONETIC_ALPHABET.put('7', "Seven");
        NATO_PHONETIC_ALPHABET.put('8', "Eight");
        NATO_PHONETIC_ALPHABET.put('9', "Nine");
    }

    /**
     * Converts text to the NATO phonetic alphabet.
     *
     * @param inputText the text to convert
     * @return the NATO phonetic alphabet representation of the input text
     */
    public static String textToPhonetic(String inputText) {
        StringBuilder phoneticResult = new StringBuilder();

        for (char character : inputText.toUpperCase().toCharArray()) {
            if (Character.isWhitespace(character)) {
                continue;
            }
            String phoneticWord = NATO_PHONETIC_ALPHABET.getOrDefault(character, String.valueOf(character));
            phoneticResult.append(phoneticWord).append(" ");
        }

        return phoneticResult.toString().trim();
    }
}