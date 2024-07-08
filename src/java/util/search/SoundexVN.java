
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.search;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author PC
 */
public class SoundexVN {

    private final Map<String, List<String>> soundexMap;

    public SoundexVN(Map<String, List<String>> soundexMap) {
        this.soundexMap = soundexMap;
    }

    public List<String> suggestSoundex(String input) {
        String encodedInput = encode(input);
        return soundexMap.getOrDefault(encodedInput, List.of());
    }
    
  



    public static String encode(String word) {
        // Normalize and remove diacritics
        String normalized = Normalizer.normalize(word, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .toUpperCase();

        if (normalized.length() == 0) {
            return "";
        }

        char[] soundex = new char[4];
        soundex[0] = normalized.charAt(0);

        char[] mapping = {'0', '1', '2', '3', '0', '1', '2', '0', '0', '2', '2', '4', '5', '5', '0', '1', '2', '6', '2', '3', '0', '1', '0', '2', '0', '2'};

        int soundexIndex = 1;

        char last = mapCharacter(normalized.charAt(0), mapping);

        for (int i = 1; i < normalized.length() && soundexIndex < 4; i++) {
            
            char mapped = mapCharacter(normalized.charAt(i), mapping);

            if (mapped != '0' && mapped != last) {
                soundex[soundexIndex++] = mapped;
                last = mapped;
            }
            
        }


        for (int i = soundexIndex; i < 4; i++) {
            soundex[i] = '0';
        }

        return new String(soundex);
    }

    private static char mapCharacter(char character, char[] mapping) {
        if (character >= 'A' && character <= 'Z' && character >= 'a' && character <= 'z') {
            return mapping[character - 'A'];
        }
        // For characters outside 'A' to 'Z', return '0' or handle as needed
        return '0';
    }

  
    public static Map<String, List<String>> applySoundex(List<String> words) {
        Map<String, List<String>> soundexMap = new HashMap<>();

        for (String word : words) {
            String encoded = encode(word);
            soundexMap.computeIfAbsent(encoded, k -> new ArrayList<>()).add(word);
        }

        return soundexMap;
    }

    public static int LevenshteinDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = Math.min(dp[i - 1][j - 1]
                            + costOfSubstitution(s1.charAt(i - 1), s2.charAt(j - 1)),
                            Math.min(dp[i - 1][j] + 1,
                                    dp[i][j - 1] + 1));
                }
            }
        }

        return dp[s1.length()][s2.length()];
    }

    public static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    public List<String> suggest(String input) {
        List<String> candidates = suggestSoundex(input);
        List<String> suggestions = new ArrayList<>();

        int threshold = 10; // Adjust as needed

        for (String candidate : candidates) {
            int distance = LevenshteinDistance(input, candidate);
            System.out.println("candidate: " + candidate + " distance: " + distance );

            if (distance <= threshold) {
                suggestions.add(candidate);
            }
        }

        return suggestions;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            System.out.print("Enter a search term (or 'exit' to quit): ");
            input = scanner.nextLine().trim();

            if (!input.equalsIgnoreCase("exit")) {
                // Apply Soundex to the dictionary
                Map<String, List<String>> soundexMap = new HashMap<>();

                for (String word : VnCharacter.movieTitles) {
                    String encoded = SoundexVN.encode(word);
                    soundexMap.computeIfAbsent(encoded, k -> new ArrayList<>()).add(word);
                }

                SoundexVN search = new SoundexVN(soundexMap);

                List<String> suggestions = search.suggest(input);
                List<String> patternMatches = new KMP().patternSearch(input, VnCharacter.movieTitles);

                System.out.println("Suggestions for '" + input + "': " + suggestions);
                System.out.println("Pattern Search Matches for '" + input + "': " + patternMatches);
                System.out.println();
            }

        } while (!input.equalsIgnoreCase("exit"));

        System.out.println("Exiting the program. Goodbye!");
        scanner.close();

        // Apply Soundex to the dictionary
//        Map<String, List<String>> soundexMap = new HashMap<>();
//
//        for (String word : movieTitles) {
//            String encoded = SoundexVN.encode(word);
//            soundexMap.computeIfAbsent(encoded, k -> new ArrayList<>()).add(word);
//        }
//
//        SoundexVN search = new SoundexVN(soundexMap);
//
//        List<String> suggestions = search.suggest(input);
//        List<String> patternMatches = new KMP().patternSearch(input, movieTitles);
//
//        System.out.println("Suggestions for '" + input + "': " + suggestions);
//        System.out.println("Pattern Search Matches for '" + input + "': " + patternMatches);
    }
}
