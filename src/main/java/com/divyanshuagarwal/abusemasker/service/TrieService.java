package com.divyanshuagarwal.abusemasker.service;

import com.divyanshuagarwal.abusemasker.model.TrieNode;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TrieService {

    private final TrieNode root = new TrieNode();

    @Value("${badwords.file:badwords.txt}")
    private String badWordsFile;

    @PostConstruct
    private void loadWordsFromFile() {
        try {
            ClassPathResource resource = new ClassPathResource(badWordsFile);
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream()))) {
                reader.lines()
                      .map(String::trim)
                      .filter(line -> !line.isEmpty() && !line.startsWith("#"))
                      .map(String::toLowerCase)
                      .forEach(this::insert);
            }
        } catch (Exception e) {
            // fallback — keeps service alive if file is missing
            insert("badword");
        }
    }

    public TrieNode getRoot() {
        return root;
    }

    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
        }
        node.isEndOfWord = true;
    }

    public boolean search(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            if (!node.children.containsKey(c)) {
                return false;
            }
            node = node.children.get(c);
        }
        return node.isEndOfWord;
    }

    public void delete(String word) {
        TrieNode node = root;
        for (char c : word.toLowerCase().toCharArray()) {
            if (!node.children.containsKey(c)) return;
            node = node.children.get(c);
        }
        node.isEndOfWord = false;
    }

    public List<String> getAllWords() {
        List<String> result = new ArrayList<>();
        collectWords(root, new StringBuilder(), result);
        return result;
    }

    private void collectWords(TrieNode node, StringBuilder prefix, List<String> result) {
        if (node.isEndOfWord) result.add(prefix.toString());
        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            prefix.append(entry.getKey());
            collectWords(entry.getValue(), prefix, result);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }
}
