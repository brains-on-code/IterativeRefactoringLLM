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

    private static final Map<Character, String> CHARACTER_TO_MORSE = new HashMap<>();
    private static final Map<String, Character> MORSE_TO_CHARACTER = new HashMap<>();

    static {
        CHARACTER_TO_MORSE.put('A', ".-");
        CHARACTER_TO_MORSE.put('B', "-...");
        CHARACTER_TO_MORSE.put('C', "-.-.");
        CHARACTER_TO_MORSE.put('D', "-..");
        CHARACTER_TO_MORSE.put('E', ".");
        CHARACTER_TO_MORSE.put('F', "..-.");
        CHARACTER_TO_MORSE.put('G', "--.");
        CHARACTER_TO_MORSE.put('H', "....");
        CHARACTER_TO_MORSE.put('I', "..");
        CHARACTER_TO_MORSE.put('J', ".---");
        CHARACTER_TO_MORSE.put('K', "-.-");
        CHARACTER_TO_MORSE.put('L', ".-..");
        CHARACTER_TO_MORSE.put('M', "--");
        CHARACTER_TO_MORSE.put('N', "-.");
        CHARACTER_TO_MORSE.put('O', "---");
        CHARACTER_TO_MORSE.put('P', ".--.");
        CHARACTER_TO_MORSE.put('Q', "--.-");
        CHARACTER_TO_MORSE.put('R', ".-.");
        CHARACTER_TO_MORSE.put('S', "...");
        CHARACTER_TO_MORSE.put('T', "-");
        CHARACTER_TO_MORSE.put('U', "..-");
        CHARACTER_TO_MORSE.put('V', "...-");
        CHARACTER_TO_MORSE.put('W', ".--");
        CHARACTER_TO_MORSE.put('X', "-..-");
        CHARACTER_TO_MORSE.put('Y', "-.--");
        CHARACTER_TO_MORSE.put('Z', "--..");

        CHARACTER_TO_MORSE.forEach((character, morse) -> MORSE_TO_CHARACTER.put(morse, character));
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
                morseBuilder.append(CHARACTER_TO_MORSE.getOrDefault(character, "")).append(" ");
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
    public static String decodeFromMorse(String morseCode) {
        StringBuilder textBuilder = new StringBuilder();
        String[] morseWords = morseCode.split(" \\| ");

        for (int wordIndex = 0; wordIndex < morseWords.length; wordIndex++) {
            String morseWord = morseWords[wordIndex];
            for (String morseLetter : morseWord.split(" ")) {
                textBuilder.append(MORSE_TO_CHARACTER.getOrDefault(morseLetter, '?'));
            }
            if (wordIndex < morseWords.length - 1) {
                textBuilder.append(" ");
            }
        }
        return textBuilder.toString();
    }
}