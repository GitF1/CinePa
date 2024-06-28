/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.search;

import util.search.Soundex;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author PC
 */
public class MetaPhone {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz";

    public static List<String> generateWords(int numberOfWords, int wordLength) {

        List<String> words = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numberOfWords; i++) {
            StringBuilder word = new StringBuilder();
            for (int j = 0; j < wordLength; j++) {
                word.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
            }
            words.add(word.toString());
        }

        return words;
    }

    public static String encode(String word) {
        
        if (word == null || word.length() == 0) {
            return "";
        }

        char[] input = word.toUpperCase().toCharArray();
        
        StringBuilder metaphone = new StringBuilder();

        // Ignore non-alphabetic characters
        for (int i = 0; i < input.length; i++) {
            if (!Character.isLetter(input[i])) {
                continue;
            }
            char current = input[i];

            // Apply Metaphone rules
            if (current == 'A' || current == 'E' || current == 'I' || current == 'O' || current == 'U' || current == 'Y') {
                if (i == 0) {
                    metaphone.append(current);
                }
            } else if (current == 'B') {
                if (!(i > 0 && input[i - 1] == 'M') || (i + 1 != input.length)) {
                    metaphone.append(current);
                }
            } else if (current == 'C') {
                if (i > 0 && input[i - 1] == 'S' && i + 1 < input.length && "EIY".indexOf(input[i + 1]) != -1) {
                    continue;
                }
                if (i + 1 < input.length && input[i + 1] == 'I' && i + 2 < input.length && input[i + 2] == 'A') {
                    metaphone.append('X');
                } else if (i + 1 < input.length && "IEY".indexOf(input[i + 1]) != -1) {
                    metaphone.append('S');
                } else {
                    metaphone.append('K');
                }
            } else if (current == 'D') {
                if (i + 1 < input.length && input[i + 1] == 'G' && i + 2 < input.length && "EIY".indexOf(input[i + 2]) != -1) {
                    metaphone.append('J');
                    i++;
                } else {
                    metaphone.append('T');
                }
            } else if (current == 'G') {
                if (i + 1 < input.length && input[i + 1] == 'H') {
                    if (!(i > 0 && "BDH".indexOf(input[i - 1]) != -1) && (i + 2 >= input.length || "AEIOU".indexOf(input[i + 2]) == -1)) {
                        i++;
                    } else {
                        metaphone.append('K');
                    }
                } else if (i + 1 < input.length && input[i + 1] == 'N') {
                    if (i + 2 == input.length || (i + 2 < input.length && "S".indexOf(input[i + 2]) != -1)) {
                        metaphone.append('K');
                    }
                } else if (i > 0 && input[i - 1] == 'D' && "EIY".indexOf(input[i + 1]) != -1) {
                    metaphone.append('J');
                } else if (i + 1 < input.length && "EIY".indexOf(input[i + 1]) != -1) {
                    metaphone.append('J');
                } else {
                    metaphone.append('K');
                }
            } else if (current == 'H') {
                if (i == 0 || "AEIOUY".indexOf(input[i - 1]) == -1) {
                    if (i + 1 < input.length && "AEIOU".indexOf(input[i + 1]) != -1) {
                        metaphone.append('H');
                    }
                }
            } else if (current == 'K') {
                if (i == 0 || input[i - 1] != 'C') {
                    metaphone.append('K');
                }
            } else if (current == 'P') {
                if (i + 1 < input.length && input[i + 1] == 'H') {
                    metaphone.append('F');
                } else {
                    metaphone.append('P');
                }
            } else if (current == 'Q') {
                metaphone.append('K');
            } else if (current == 'S') {
                if (i + 1 < input.length && input[i + 1] == 'H') {
                    metaphone.append('X');
                } else if (i + 1 < input.length && input[i + 1] == 'I' && (i + 2 < input.length && (input[i + 2] == 'O' || input[i + 2] == 'A'))) {
                    metaphone.append('X');
                } else {
                    metaphone.append('S');
                }
            } else if (current == 'T') {
                if (i + 1 < input.length && input[i + 1] == 'I' && (i + 2 < input.length && (input[i + 2] == 'O' || input[i + 2] == 'A'))) {
                    metaphone.append('X');
                } else if (i + 1 < input.length && input[i + 1] == 'H') {
                    metaphone.append('0');
                } else if (i + 1 < input.length && input[i + 1] != 'C' && input[i + 1] != 'H') {
                    metaphone.append('T');
                }
            } else if (current == 'V') {
                metaphone.append('F');
            } else if (current == 'W' || current == 'Y') {
                if (i + 1 < input.length && "AEIOU".indexOf(input[i + 1]) != -1) {
                    metaphone.append(current);
                }
            } else if (current == 'X') {
                metaphone.append('K');
                metaphone.append('S');
            } else if (current == 'Z') {
                metaphone.append('S');
            } else {
                metaphone.append(current);
            }
        }

        return metaphone.toString();
    }

    public static Map<String, List<String>> applySoundex(List<String> words) {
        
        Map<String, List<String>> soundexMap = new HashMap<>();

        for (String word : words) {
            String encoded = Soundex.encode(word);
            soundexMap.computeIfAbsent(encoded, k -> new ArrayList<>()).add(word);
        }

        return soundexMap;
        
    }

    public static Map<String, List<String>> applyMetaphone(List<String> words) {
        Map<String, List<String>> metaphoneMap = new HashMap<>();

        for (String word : words) {
            String encoded = encode(word);
            metaphoneMap.computeIfAbsent(encoded, k -> new ArrayList<>()).add(word);
        }

        return metaphoneMap;
    }
      public static void main(String[] args) {
        List<String> words = generateWords(1000000, 6);

        // Apply Soundex
        Map<String, List<String>> soundexMap = applySoundex(words);
        System.out.println("Generated " + words.size() + " words and encoded them using Soundex.");

        // Apply Metaphone
        Map<String, List<String>> metaphoneMap = applyMetaphone(words);
        System.out.println("Generated " + words.size() + " words and encoded them using Metaphone.");

        // Example encoded words
        System.out.println("Example encoded words for Soundex key 'A120': " + soundexMap.get("A120"));
        System.out.println("Example encoded words for Metaphone key 'TSTN': " + metaphoneMap.get("TSTN"));
    }
}
