package com.seoanalyzer.service.impl;

import com.seoanalyzer.service.SEOAnalyzerService;

import com.seoanalyzer.entity.SEOAnalysisResult;
import com.seoanalyzer.repository.SEOAnalysisResultRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class SEOAnalyzerServiceImpl implements SEOAnalyzerService {

    private final SEOAnalysisResultRepository repository;

    public SEOAnalyzerServiceImpl(SEOAnalysisResultRepository repository) {
        this.repository = repository;
    }

    @Override
    public SEOAnalysisResult analyzeUrl(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();

        boolean titlePresent = doc.title() != null && !doc.title().isEmpty();

        Elements metaDescription = doc.select("meta[name=description]");
        boolean descriptionPresent = !metaDescription.isEmpty();

        Elements h1Tags = doc.select("h1");
        boolean h1Present = !h1Tags.isEmpty();

        Elements images = doc.select("img");
        long missingAltTagsCount = images.stream()
                .filter(img -> img.attr("alt").isEmpty())
                .count();

        int score = 0;
        score += titlePresent ? 20 : 0;
        score += descriptionPresent ? 20 : 0;
        score += h1Present ? 20 : 0;
        score += missingAltTagsCount == 0 ? 40 : (int)(40 - missingAltTagsCount * 5);
        if (score < 0) score = 0;

        StringBuilder suggestions = new StringBuilder();
        if (!titlePresent) suggestions.append("Add a title tag. ");
        if (!descriptionPresent) suggestions.append("Add a meta description. ");
        if (!h1Present) suggestions.append("Add at least one H1 tag. ");
        if (missingAltTagsCount > 0) suggestions.append("Add alt text to images missing it. ");

        SEOAnalysisResult result = SEOAnalysisResult.builder()
                .url(url)
                .seoScore(score)
                .titlePresent(titlePresent)
                .descriptionPresent(descriptionPresent)
                .h1Present(h1Present)
                .missingAltTags((int) missingAltTagsCount)
                .suggestions(suggestions.toString().trim())
                .analyzedAt(LocalDateTime.now())
                .build();

        repository.save(result);

        return result;
    }
}
