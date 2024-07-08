/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.search;

/**
 *
 * @author PC
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Trie {

    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    // Method to insert a word into the trie
    public void insert(String word, String originalWord) {
        TrieNode currentNode = root;

        for (char ch : word.toCharArray()) {
            currentNode = currentNode.children.computeIfAbsent(ch, c -> new TrieNode());
            currentNode.words.add(originalWord);
        }
        currentNode.isEndOfWord = true;
    }

    // Method to search for words with the given prefix
    public List<String> searchWithPrefix(String prefix) {
        List<String> results = new ArrayList<>();
        TrieNode currentNode = root;

        for (char ch : prefix.toCharArray()) {
            currentNode = currentNode.children.get(ch);
            if (currentNode == null) {
                return results; // No words with this prefix
            }
        }
        results.addAll(currentNode.words);
        return results;
    }

    //
    private String getAbbreviation(String title) {
        StringBuilder abbreviation = new StringBuilder();
        boolean newWord = true;
        for (char c : title.toCharArray()) {
            if (newWord && Character.isLetter(c)) {
                abbreviation.append(Character.toUpperCase(c));
                newWord = false;
            } else if (c == ' ') {
                newWord = true;
            }
        }
        return abbreviation.toString();
    }

    // Insert a title into the Trie
    public void insertAbbreviation(String title) {
        String abbreviation = getAbbreviation(title);
        TrieNode node = root;
        for (char c : abbreviation.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
            node.words.add(title);
        }
    }
    //
    // Search for titles matching the abbreviation
    public List<String> searchAbbreviation(String abbreviation) {
        TrieNode node = root;
        for (char c : abbreviation.toCharArray()) {
            node = node.children.get(c);
            if (node == null) {
                return new ArrayList<>(); // No matching titles
            }
        }
        return node.words;
    }
}
