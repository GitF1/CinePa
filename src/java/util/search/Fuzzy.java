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

    public static List<String> fuzzyMatch(String input, List<String> candidates, int threshold) {
        List<String> results = new ArrayList<>();
        for (String candidate : candidates) {
            int distance = LevenshteinDistance(input, candidate);
            if (distance <= threshold) {
                results.add(candidate);
            }
        }
        return results;
    }

}
