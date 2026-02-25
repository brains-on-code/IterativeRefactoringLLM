package com.thealgorithms.conversions;

import java.util.HashMap;
import java.util.Map;

public final class MorseCodeConverter {

    private MorseCodeConverter() {}

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

    public static String textToMorse(String plainText) {
        StringBuilder morseTextBuilder = new StringBuilder();
        String[] words = plainText.toUpperCase().split(" ");

        for (int wordIndex = 0; wordIndex < words.length; wordIndex++) {
            String word = words[wordIndex];
            for (char letter : word.toCharArray()) {
                morseTextBuilder.append(LETTER_TO_MORSE.getOrDefault(letter, "")).append(" ");
            }
            if (wordIndex < words.length - 1) {
                morseTextBuilder.append("| ");
            }
        }
        return morseTextBuilder.toString().trim();
    }

    public static String morseToText(String morseText) {
        StringBuilder plainTextBuilder = new StringBuilder();
        String[] morseWords = morseText.split(" \\| ");

        for (int wordIndex = 0; wordIndex < morseWords.length; wordIndex++) {
            String morseWord = morseWords[wordIndex];
            for (String morseLetter : morseWord.split(" ")) {
                plainTextBuilder.append(MORSE_TO_LETTER.getOrDefault(morseLetter, '?'));
            }
            if (wordIndex < morseWords.length - 1) {
                plainTextBuilder.append(" ");
            }
        }
        return plainTextBuilder.toString();
    }
}