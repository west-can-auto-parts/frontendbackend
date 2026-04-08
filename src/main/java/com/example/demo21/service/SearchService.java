package com.example.demo21.service;

import com.example.demo21.dto.ProductResponse;
import com.example.demo21.dto.SearchResultResponse;
import com.example.demo21.dto.SuggestionRequestParameters;
import com.example.demo21.dto.SuggestionResponse;

import java.util.List;
import java.util.Map;

public interface SearchService {
    public Map<String, Object> search(String query);
    public SuggestionResponse fetchSuggestions(SuggestionRequestParameters parameters);
}
