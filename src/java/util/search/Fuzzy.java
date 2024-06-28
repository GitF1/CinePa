/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author PC
 */
public class Fuzzy {

    private final Map<String, List<String>> soundexMap;

    public Fuzzy(Map<String, List<String>> soundexMap) {
        this.soundexMap = soundexMap;
    }

    public List<String> suggestSoundex(String input) {
        String encodedInput = Soundex.encode(input);
        return soundexMap.getOrDefault(encodedInput, List.of());
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

    public static List<String> suggest(String input, List<String> dictionary) {
        List<String> suggestions = new ArrayList<>();

        int threshold = 10; // You can adjust this threshold

        for (String word : dictionary) {
            int distance = LevenshteinDistance(input, word);
            System.out.println("distance: " + distance);
            if (distance <= threshold) {
                suggestions.add(word);
            }
        }

        return suggestions;
    }

    public static void main(String[] args) {

        List<String> dictionary = SoundexVN.movieTitles;

        String input = "lật mặt";

        // Apply Soundex to the dictionary
        //Fuzzy search = new Fuzzy(soundexMap);
        // Replace with any input word
        //List<String> suggestions = search.suggestSoundex(input);
        System.out.println("Suggestions for '" + input + "': " + suggest(input, dictionary));
    }
}

class Soundex {

    public static String encode(String word) {
        char[] soundex = new char[4];
        soundex[0] = Character.toUpperCase(word.charAt(0));

        char[] mapping = {'0', '1', '2', '3', '0', '1', '2', '0', '0', '2', '2', '4', '5', '5', '0', '1', '2', '6', '2', '3', '0', '1', '0', '2', '0', '2'};

        int soundexIndex = 1;
        char last = mapping[Character.toUpperCase(word.charAt(0)) - 'A'];

        for (int i = 1; i < word.length() && soundexIndex < 4; i++) {
            char mapped = mapping[Character.toUpperCase(word.charAt(i)) - 'A'];

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
}

class LargeDatasetGenerator {

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

    public static Map<String, List<String>> applySoundex(List<String> words) {
        Map<String, List<String>> soundexMap = new HashMap<>();

        for (String word : words) {
            String encoded = Soundex.encode(word);
            soundexMap.computeIfAbsent(encoded, k -> new ArrayList<>()).add(word);
        }

        return soundexMap;
    }

//    public static void main(String[] args) {
//        List<String> words = generateWords(1000000, 6);
//        Map<String, List<String>> soundexMap = applySoundex(words);
//
//        System.out.println("Generated " + words.size() + " words and encoded them using Soundex.");
//        System.out.println("Example encoded words for Soundex key 'A120': " + soundexMap.get("A120"));
//    }
}
