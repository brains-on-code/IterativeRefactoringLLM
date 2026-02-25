package com.thealgorithms.conversions;

import java.util.HashMap;
import java.util.Map;

/**
 * Converts text to Morse code and vice-versa.
 * Text to Morse code: Each letter is separated by a space and each word is separated by a pipe (|).
 * Example: "HELLO WORLD" -> ".... . .-.. .-.. --- | .-- --- .-. .-.. -.."
 *
 * Morse code to text: Each letter is separated by a space and each word is separated by a pipe (|).
 * Example: ".... . .-.. .-.. --- | .-- --- .-. .-.. -.." -> "HELLO WORLD"
 *
 * Applications: Used in radio communications and algorithmic challenges.
 *
 * @author Hardvan
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

        // Build reverse map for decoding
        LETTER_TO_MORSE.forEach((letter, morse) -> MORSE_TO_LETTER.put(morse, letter));
    }

    /**
     * Converts text to Morse code.
     * Each letter is separated by a space and each word is separated by a pipe (|).
     *
     * @param text The text to convert to Morse code.
     * @return The Morse code representation of the text.
     */
    public static String textToMorse(String text) {
        StringBuilder morseCodeBuilder = new StringBuilder();
        String[] words = text.toUpperCase().split(" ");

        for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
            String word = words[wordIndex];

            for (char letter : word.toCharArray()) {
                morseCodeBuilder
                    .append(LETTER_TO_MORSE.getOrDefault(letter, ""))
                    .append(" ");
            }

            if (wordIndex < words.length - 1) {
                morseCodeBuilder.append("| ");
            }
        }

        return morseCodeBuilder.toString().trim();
    }

    /**
     * Converts Morse code to text.
     * Each letter is separated by a space and each word is separated by a pipe (|).
     *
     * @param morseCode The Morse code to convert to text.
     * @return The text representation of the Morse code.
     */
    public static String morseToText(String morseCode) {
        StringBuilder textBuilder = new StringBuilder();
        String[] morseWords = morseCode.split(" \\| ");

        for (int wordIndex = 0; wordIndex < morseWords.length; wordIndex++) {
            String morseWord = morseWords[wordIndex];

            for (String morseLetter : morseWord.split(" ")) {
                textBuilder.append(MORSE_TO_LETTER.getOrDefault(morseLetter, '?'));
            }

            if (wordIndex < morseWords.length - 1) {
                textBuilder.append(" ");
            }
        }

        return textBuilder.toString();
    }
}