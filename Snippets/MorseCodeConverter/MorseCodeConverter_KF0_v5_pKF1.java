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

    private static final Map<Character, String> CHARACTER_TO_MORSE_MAP = new HashMap<>();
    private static final Map<String, Character> MORSE_TO_CHARACTER_MAP = new HashMap<>();

    private static final String LETTER_SEPARATOR = " ";
    private static final String MORSE_WORD_SPLIT_PATTERN = " \\| ";
    private static final String MORSE_WORD_SEPARATOR = "| ";

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
     * Converts text to Morse code.
     * Each letter is separated by a space and each word is separated by a pipe (|).
     *
     * @param text The text to convert to Morse code.
     * @return The Morse code representation of the text.
     */
    public static String textToMorse(String text) {
        StringBuilder morseCodeBuilder = new StringBuilder();
        String[] textWords = text.toUpperCase().split(LETTER_SEPARATOR);

        for (int wordIndex = 0; wordIndex < textWords.length; wordIndex++) {
            String textWord = textWords[wordIndex];

            for (char character : textWord.toCharArray()) {
                morseCodeBuilder
                    .append(CHARACTER_TO_MORSE_MAP.getOrDefault(character, ""))
                    .append(LETTER_SEPARATOR);
            }

            if (wordIndex < textWords.length - 1) {
                morseCodeBuilder.append(MORSE_WORD_SEPARATOR);
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
        String[] morseWords = morseCode.split(MORSE_WORD_SPLIT_PATTERN);

        for (int wordIndex = 0; wordIndex < morseWords.length; wordIndex++) {
            String morseWord = morseWords[wordIndex];

            for (String morseLetter : morseWord.split(LETTER_SEPARATOR)) {
                textBuilder.append(MORSE_TO_CHARACTER_MAP.getOrDefault(morseLetter, '?'));
            }

            if (wordIndex < morseWords.length - 1) {
                textBuilder.append(LETTER_SEPARATOR);
            }
        }

        return textBuilder.toString();
    }
}