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

    private static final Map<Character, String> LETTER_TO_MORSE = new HashMap<>();
    private static final Map<String, Character> MORSE_TO_LETTER = new HashMap<>();

    static {
        LETTER_TO_MORSE.put('A', ".-");
        LETTER_TO_MORSE.put('B', "-...");
        LETTER_TO_MORSE.put('C', "-.-.");
        LETTER_TO_MORSE.put('D', "-..");
        LETTER_TO_MORSE.put('E', ".");
        LETTER_TO_MORSE.put('F', "..-.");
        LETTER_TO_MORSE.put('G', "--.");
        LETTER_TO_MORSE.put('H', "....");
        LETTER_TO_MORSE.put('I', "..");
        LETTER_TO_MORSE.put('J', ".---");
        LETTER_TO_MORSE.put('K', "-.-");
        LETTER_TO_MORSE.put('L', ".-..");
        LETTER_TO_MORSE.put('M', "--");
        LETTER_TO_MORSE.put('N', "-.");
        LETTER_TO_MORSE.put('O', "---");
        LETTER_TO_MORSE.put('P', ".--.");
        LETTER_TO_MORSE.put('Q', "--.-");
        LETTER_TO_MORSE.put('R', ".-.");
        LETTER_TO_MORSE.put('S', "...");
        LETTER_TO_MORSE.put('T', "-");
        LETTER_TO_MORSE.put('U', "..-");
        LETTER_TO_MORSE.put('V', "...-");
        LETTER_TO_MORSE.put('W', ".--");
        LETTER_TO_MORSE.put('X', "-..-");
        LETTER_TO_MORSE.put('Y', "-.--");
        LETTER_TO_MORSE.put('Z', "--..");

        LETTER_TO_MORSE.forEach((letter, morseCode) -> MORSE_TO_LETTER.put(morseCode, letter));
    }

    /**
     * Encodes a plain text string into Morse code.
     * Words are separated by " | " and letters by a single space.
     */
    public static String encodeToMorse(String text) {
        StringBuilder encodedMorse = new StringBuilder();
        String[] words = text.toUpperCase().split(" ");

        for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
            String word = words[wordIndex];
            for (char letter : word.toCharArray()) {
                encodedMorse.append(LETTER_TO_MORSE.getOrDefault(letter, "")).append(" ");
            }
            if (wordIndex < words.length - 1) {
                encodedMorse.append("| ");
            }
        }
        return encodedMorse.toString().trim();
    }

    /**
     * Decodes a Morse code string into plain text.
     * Words are expected to be separated by " | " and letters by a single space.
     */
    public static String decodeFromMorse(String morseCodeText) {
        StringBuilder decodedText = new StringBuilder();
        String[] morseWords = morseCodeText.split(" \\| ");

        for (int wordIndex = 0; wordIndex < morseWords.length; wordIndex++) {
            String morseWord = morseWords[wordIndex];
            for (String morseLetter : morseWord.split(" ")) {
                decodedText.append(MORSE_TO_LETTER.getOrDefault(morseLetter, '?'));
            }
            if (wordIndex < morseWords.length - 1) {
                decodedText.append(" ");
            }
        }
        return decodedText.toString();
    }
}