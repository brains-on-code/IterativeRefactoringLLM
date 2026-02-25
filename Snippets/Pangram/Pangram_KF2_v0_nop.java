package com.thealgorithms.strings;

import java.util.HashSet;


public final class Pangram {
    private Pangram() {
    }


    public static void main(String[] args) {
        assert isPangram("The quick brown fox jumps over the lazy dog");
        assert !isPangram("The quick brown fox jumps over the azy dog");
        assert !isPangram("+-1234 This string is not alphabetical");
        assert !isPangram("\u0000/\\");
    }


    public static boolean isPangramUsingSet(String s) {
        HashSet<Character> alpha = new HashSet<>();
        s = s.trim().toLowerCase();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ') {
                alpha.add(s.charAt(i));
            }
        }
        return alpha.size() == 26;
    }


    public static boolean isPangram(String s) {
        boolean[] lettersExisting = new boolean[26];
        for (char c : s.toCharArray()) {
            int letterIndex = c - (Character.isUpperCase(c) ? 'A' : 'a');
            if (letterIndex >= 0 && letterIndex < lettersExisting.length) {
                lettersExisting[letterIndex] = true;
            }
        }
        for (boolean letterFlag : lettersExisting) {
            if (!letterFlag) {
                return false;
            }
        }
        return true;
    }


    public static boolean isPangram2(String s) {
        if (s.length() < 26) {
            return false;
        }
        s = s.toLowerCase();
        for (char i = 'a'; i <= 'z'; i++) {
            if (s.indexOf(i) == -1) {
                return false;
            }
        }
        return true;
    }
}
