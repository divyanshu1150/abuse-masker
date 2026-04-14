package com.divyanshuagarwal.abusemasker.service;

import com.divyanshuagarwal.abusemasker.model.TrieNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class AbuseMaskService {

    private static final Logger log = LoggerFactory.getLogger(AbuseMaskService.class);

    @Autowired
    private TrieService trieService;

    @Value("${masker.word-boundary:false}")
    private boolean wordBoundary;

    private final AtomicLong totalMessagesProcessed = new AtomicLong(0);
    private final AtomicLong totalWordsMasked = new AtomicLong(0);

    private void maskRange(char[] arr, int start, int end) {
        if (end - start < 2) return;
        for (int i = start + 1; i < end; i++) {
            arr[i] = '*';
        }
    }

    private boolean isBoundary(String lower, int start, int end) {
        boolean beforeOk = (start == 0) || !Character.isLetter(lower.charAt(start - 1));
        boolean afterOk  = (end + 1 >= lower.length()) || !Character.isLetter(lower.charAt(end + 1));
        return beforeOk && afterOk;
    }

    public String mask(String input) {
        log.debug("Masking input: [{}]", input);
        String lower = input.toLowerCase();
        char[] result = input.toCharArray();
        int maskedWordCount = 0;

        for (int i = 0; i < lower.length(); i++) {
            TrieNode node = trieService.getRoot();
            int j = i;
            while (j < lower.length() && node.children.containsKey(lower.charAt(j))) {
                node = node.children.get(lower.charAt(j));
                if (node.isEndOfWord) {
                    boolean boundaryOk = !wordBoundary || isBoundary(lower, i, j);
                    if (boundaryOk) {
                        maskRange(result, i, j);
                        maskedWordCount++;
                    }
                }
                j++;
            }
        }

        totalMessagesProcessed.incrementAndGet();
        totalWordsMasked.addAndGet(maskedWordCount);

        String masked = new String(result);
        if (maskedWordCount > 0) {
            log.info("Masked {} word(s). Original=[{}] Masked=[{}]", maskedWordCount, input, masked);
        }
        return masked;
    }

    public long getTotalMessagesProcessed() { return totalMessagesProcessed.get(); }
    public long getTotalWordsMasked()        { return totalWordsMasked.get(); }
}
