package com.example.demo21.dto;

import java.util.List;

public record SuggestionResponse(
        List<SuggestionItem> ProductCategory,
        List<SuggestionItem> SubCategory
) {
    public static SuggestionResponse empty() {
        return new SuggestionResponse(List.of(), List.of());
    }
}
