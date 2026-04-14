package com.divyanshuagarwal.abusemasker.model;

public class StatsResponse {

    private final long totalMessagesProcessed;
    private final long totalWordsMasked;
    private final int totalBadWords;
    private final int connectedViewers;

    public StatsResponse(long totalMessagesProcessed, long totalWordsMasked, int totalBadWords, int connectedViewers) {
        this.totalMessagesProcessed = totalMessagesProcessed;
        this.totalWordsMasked = totalWordsMasked;
        this.totalBadWords = totalBadWords;
        this.connectedViewers = connectedViewers;
    }

    public long getTotalMessagesProcessed() { return totalMessagesProcessed; }
    public long getTotalWordsMasked()        { return totalWordsMasked; }
    public int  getTotalBadWords()           { return totalBadWords; }
    public int  getConnectedViewers()        { return connectedViewers; }
}
