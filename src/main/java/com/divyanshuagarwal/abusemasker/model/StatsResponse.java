package com.divyanshuagarwal.abusemasker.model;

public class StatsResponse {

    private final long totalMessagesProcessed;
    private final long totalWordsMasked;
    private final int totalBadWords;

    public StatsResponse(long totalMessagesProcessed, long totalWordsMasked, int totalBadWords) {
        this.totalMessagesProcessed = totalMessagesProcessed;
        this.totalWordsMasked = totalWordsMasked;
        this.totalBadWords = totalBadWords;
    }

    public long getTotalMessagesProcessed() { return totalMessagesProcessed; }
    public long getTotalWordsMasked()        { return totalWordsMasked; }
    public int  getTotalBadWords()           { return totalBadWords; }
}
