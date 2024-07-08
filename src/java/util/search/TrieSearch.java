/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.search;

import java.text.Normalizer;
import java.util.List;

/**
 *
 * @author PC
 */
public class TrieSearch {

    private Trie trie;

    public TrieSearch(List<String> movieTitles) {
        trie = new Trie();
        for (String title : movieTitles) {
            String normalizedTitle = normalizeAndRemoveDiacritics(title).toLowerCase();
            trie.insert(normalizedTitle, title);
            trie.insert(generateAcronym(normalizedTitle), title);
        }
    }

    private String normalizeAndRemoveDiacritics(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .replaceAll("\\s+", " ").trim();
    }

    // Function to generate an acronym from a movie title
    private String generateAcronym(String title) {
        StringBuilder acronym = new StringBuilder();
        for (String word : title.split(" ")) {
            if (!word.isEmpty()) {
                acronym.append(word.charAt(0));
            }
        }
        return acronym.toString();
    }

    // Method to search for movie titles using a prefix
    public List<String> searchByPrefix(String prefix) {
        return trie.searchWithPrefix(prefix.toLowerCase());
    }
}
