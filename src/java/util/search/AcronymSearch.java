/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.search;

import java.util.List;

/**
 *
 * @author PC
 */
public class AcronymSearch {

    private Trie trie;

    public AcronymSearch(List<String> movieTitles) {
        trie = new Trie();
        for (String title : movieTitles) {
            trie.insertAbbreviation(title);
        }
    }
    // Function to convert title to its abbreviation



    // Function to generate an acronym from a movie title
    private String generateAcronym(String title) {
        StringBuilder acronym = new StringBuilder();
        for (String word : title.split(" ")) {
            acronym.append(word.charAt(0));
        }
        return acronym.toString().toUpperCase();
    }

    // Method to search for movie titles using an acronym
    public List<String> searchByAcronym(String acronym) {
        return trie.searchAbbreviation(acronym.toUpperCase());
    }
    
}
