package com.thealgorithms.conversions;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for converting between text and Morse code.
 *
 * Example:
 * "HELLO WORLD" -> ".... . .-.. .-.. --- | .-- --- .-. .-.. -.."
 * ".... . .-.. .-.. --- | .-- --- .-. .-.. -.." -> "HELLO WORLD"
 */
public final class MorseCodeConverter {

    private MorseCodeConverter() {
    }

    private static final Map<Character, String> CHARACTER_TO_MORSE_MAP = new HashMap<>();
    private static final Map<String, Character> MORSE_TO_CHARACTER_MAP = new HashMap<>();

    static {
        CHARACTER_TO_MORSE_MAP.put('A', ".-");
        CHARACTER_TO_MORSE_MAP.put('B', "-...");
        CHARACTER_TO_MORSE_MAP.put('C', "-.-.");
        CHARACTER_TO_MORSE_MAP.put('D', "-..");
        CHARACTER_TO_MORSE_MAP.put('E', ".");
        CHARACTER_TO_MORSE_MAP.put('F', "..-.");
        CHARACTER_TO_MORSE_MAP.put('G', "--.");
        CHARACTER_TO_MORSE_MAP.put('H', "....");
        CHARACTER_TO_MORSE_MAP.put('I', "..");
        CHARACTER_TO_MORSE_MAP.put('J', ".---");
        CHARACTER_TO_MORSE_MAP.put('K', "-.-");
        CHARACTER_TO_MORSE_MAP.put('L', ".-..");
        CHARACTER_TO_MORSE_MAP.put('M', "--");
        CHARACTER_TO_MORSE_MAP.put('N', "-.");
        CHARACTER_TO_MORSE_MAP.put('O', "---");
        CHARACTER_TO_MORSE_MAP.put('P', ".--.");
        CHARACTER_TO_MORSE_MAP.put('Q', "--.-");
        CHARACTER_TO_MORSE_MAP.put('R', ".-.");
        CHARACTER_TO_MORSE_MAP.put('S', "...");
        CHARACTER_TO_MORSE_MAP.put('T', "-");
        CHARACTER_TO_MORSE_MAP.put('U', "..-");
        CHARACTER_TO_MORSE_MAP.put('V', "...-");
        CHARACTER_TO_MORSE_MAP.put('W', ".--");
        CHARACTER_TO_MORSE_MAP.put('X', "-..-");
        CHARACTER_TO_MORSE_MAP.put('Y', "-.--");
        CHARACTER_TO_MORSE_MAP.put('Z', "--..");

        CHARACTER_TO_MORSE_MAP.forEach((character, morseCode) -> MORSE_TO_CHARACTER_MAP.put(morseCode, character));
    }

    /**
     * Encodes a plain text string into Morse code.
     * Words are separated by " | " and letters by a single space.
     */
    public static String encodeToMorse(String text) {
        StringBuilder morseBuilder = new StringBuilder();
        String[] words = text.toUpperCase().split(" ");

        for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
            String word = words[wordIndex];
            for (char character : word.toCharArray()) {
                morseBuilder.append(CHARACTER_TO_MORSE_MAP.getOrDefault(character, "")).append(" ");
            }
            if (wordIndex < words.length - 1) {
                morseBuilder.append("| ");
            }
        }
        return morseBuilder.toString().trim();
    }

    /**
     * Decodes a Morse code string into plain text.
     * Words are expected to be separated by " | " and letters by a single space.
     */
    public static String decodeFromMorse(String morseCodeText) {
        StringBuilder textBuilder = new StringBuilder();
        String[] morseWords = morseCodeText.split(" \\| ");

        for (int wordIndex = 0; wordIndex < morseWords.length; wordIndex++) {
            String morseWord = morseWords[wordIndex];
            for (String morseCharacter : morseWord.split(" ")) {
                textBuilder.append(MORSE_TO_CHARACTER_MAP.getOrDefault(morseCharacter, '?'));
            }
            if (wordIndex < morseWords.length - 1) {
                textBuilder.append(" ");
            }
        }
        return textBuilder.toString();
    }
}