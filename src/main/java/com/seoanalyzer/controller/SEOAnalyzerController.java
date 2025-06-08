package com.seoanalyzer.controller;
import com.seoanalyzer.entity.SEOAnalysisResult;
import com.seoanalyzer.service.SEOAnalyzerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/seo")
public class SEOAnalyzerController {

    private final SEOAnalyzerService seoAnalyzerService;

    public SEOAnalyzerController(SEOAnalyzerService seoAnalyzerService) {
        this.seoAnalyzerService = seoAnalyzerService;
    }

    @PostMapping("/analyze")
    public ResponseEntity<SEOAnalysisResult> analyzeWebsite(@RequestParam String url) {
        try {
            SEOAnalysisResult result = seoAnalyzerService.analyzeUrl(url);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}