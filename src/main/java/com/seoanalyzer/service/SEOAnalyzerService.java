package com.seoanalyzer.service;

import com.seoanalyzer.entity.SEOAnalysisResult;

import java.io.IOException;

public interface SEOAnalyzerService {
    SEOAnalysisResult analyzeUrl(String url) throws IOException;
}
