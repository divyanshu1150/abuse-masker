package com.divyanshuagarwal.abusemasker.model;

public class MaskResult {

    private final String original;
    private final String masked;

    public MaskResult(String original, String masked) {
        this.original = original;
        this.masked = masked;
    }

    public String getOriginal() { return original; }
    public String getMasked()   { return masked; }
}
