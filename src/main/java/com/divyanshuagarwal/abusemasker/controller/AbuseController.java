package com.divyanshuagarwal.abusemasker.controller;

import com.divyanshuagarwal.abusemasker.model.MaskResult;
import com.divyanshuagarwal.abusemasker.service.AbuseMaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mask")
public class AbuseController {

    @Autowired
    private AbuseMaskService service;

    @GetMapping
    public ResponseEntity<?> maskText(@RequestParam(required = false) String text) {
        if (text == null || text.isBlank()) {
            return ResponseEntity.badRequest().body("Parameter 'text' must not be empty.");
        }
        return ResponseEntity.ok(new MaskResult(text, service.mask(text)));
    }
}
