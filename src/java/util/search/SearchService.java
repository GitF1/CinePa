/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author PC
 */
public class SearchService {

    private SoundexVN soundexVN;
    private AcronymSearch acronymSearchService;
    private TrieSearch trieSearchService;

    public SearchService(Map<String, List<String>> soundexMap, List<String> movieTitles) {
        soundexVN = new SoundexVN(soundexMap);
        acronymSearchService = new AcronymSearch(movieTitles);
        trieSearchService = new TrieSearch(movieTitles);
    }

    public List<String> searchPrefix(String input) {
        return trieSearchService.searchByPrefix(input);
    }

    public List<String> acronymSearch(String input) {
        return acronymSearchService.searchByAcronym(input);
    }

    public List<String> suggest(String input) {
        return soundexVN.suggest(input);
    }

    public static void main(String[] args) {
        List<String> movieTitles = VnCharacter.movieTitles;
        Scanner scanner = new Scanner(System.in);

        Map<String, List<String>> soundexMap = SoundexVN.applySoundex(movieTitles);
        SearchService searchService = new SearchService(soundexMap, movieTitles);

        String input;
        do {
            System.out.print("Enter a search term (or 'exit' to quit): ");
            input = scanner.nextLine().trim();

            if (!input.equalsIgnoreCase("exit")) {
                // Test the search suggestions
                List<String> suggestions = searchService.suggest(input);
                List<String> prefixSuggestions = searchService.searchPrefix(input);
                List<String> acronymSearch = searchService.acronymSearch(input);
                //
                System.out.println("prefix search for '" + input + "': " + prefixSuggestions);
                System.out.println("Fuzzy Search for '" + input + "': " + suggestions);
                System.out.println("Acronym Search for '" + input + "': " + acronymSearch);
                System.out.println();
            }

        } while (!input.equalsIgnoreCase("exit"));

    }

}
