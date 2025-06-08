package com.seoanalyzer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SEOAnalysisResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private int seoScore;
    private int missingAltTags;
    private boolean titlePresent;
    private boolean descriptionPresent;
    private boolean h1Present;

    @Column(columnDefinition = "TEXT")
    private String suggestions; // comma separated or JSON string

    private LocalDateTime analyzedAt;
}
