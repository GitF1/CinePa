/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author PC
 */
public class TrieNode {

    Map<Character, TrieNode> children;
    boolean isEndOfWord;
    List<String> words;

    public TrieNode() {
        children = new HashMap<>();
        isEndOfWord = false;
        words = new ArrayList<>();
    }
}
