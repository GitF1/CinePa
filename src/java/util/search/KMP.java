/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.search;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC
 */
public class KMP {

    public static int[] computeLPSArray(String pattern) {

        int length = 0;
        int i = 1;
        int M = pattern.length();
        int[] lps = new int[M];
        lps[0] = 0;

        while (i < M) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else {
                if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[i] = length;
                    i++;
                }
            }
        }

        return lps;
    }

    public static boolean KMP(String text, String pattern) {

        int N = text.length();
        int M = pattern.length();
        int[] lps = computeLPSArray(pattern);
        int i = 0;
        int j = 0;

        while (i < N) {
            if (pattern.charAt(j) == text.charAt(i)) {
                j++;
                i++;
            }

            if (j == M) {
                return true;
            } else if (i < N && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return false;
    }

    private String normalize(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .toLowerCase();
    }

    public List<String> patternSearch(String input, List<String> dictionary) {

        List<String> matches = new ArrayList<>();

        String normalized = normalize(input);

        for (String word : dictionary) {

            if (KMP(normalize(word), normalized)) {
                matches.add(word);
            }
        }

        return matches;
    }
}
