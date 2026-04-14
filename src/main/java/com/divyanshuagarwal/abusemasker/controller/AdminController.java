package com.divyanshuagarwal.abusemasker.controller;

import com.divyanshuagarwal.abusemasker.model.StatsResponse;
import com.divyanshuagarwal.abusemasker.service.AbuseMaskService;
import com.divyanshuagarwal.abusemasker.service.TrieService;
import com.divyanshuagarwal.abusemasker.service.ViewerCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AdminController {

    @Autowired private TrieService trieService;
    @Autowired private AbuseMaskService abuseMaskService;
    @Autowired private ViewerCountService viewerCountService;

    @GetMapping("/admin/words")
    public List<String> listWords() {
        return trieService.getAllWords().stream().sorted().toList();
    }

    @PostMapping("/admin/words")
    public ResponseEntity<String> addWord(@RequestBody Map<String, String> body) {
        String word = body.get("word");
        if (word == null || word.isBlank()) {
            return ResponseEntity.badRequest().body("Field 'word' is required.");
        }
        trieService.insert(word.trim().toLowerCase());
        return ResponseEntity.ok("Word added: " + word.trim().toLowerCase());
    }

    @DeleteMapping("/admin/words/{word}")
    public ResponseEntity<String> removeWord(@PathVariable String word) {
        trieService.delete(word.toLowerCase());
        return ResponseEntity.ok("Word removed: " + word.toLowerCase());
    }

    @GetMapping("/stats")
    public StatsResponse stats() {
        return new StatsResponse(
            abuseMaskService.getTotalMessagesProcessed(),
            abuseMaskService.getTotalWordsMasked(),
            trieService.getAllWords().size(),
            viewerCountService.getCount()
        );
    }
}
