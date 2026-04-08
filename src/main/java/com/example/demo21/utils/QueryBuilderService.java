package com.example.demo21.utils;

import com.example.demo21.dto.SuggestionRequestParameters;

import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;

public class QueryBuilderService {

    public static NativeQuery toSuggestQuery(SuggestionRequestParameters parameters) {
        var suggester = ElasticsearchUtil.buildCompletionSuggester(
                Constants.Suggestion.SUGGEST_NAME,
                Constants.Suggestion.SEARCH_TERM,
                parameters.prefix(),
                parameters.limit()
        );
        return NativeQuery.builder()
                .withSuggester(suggester)
                .withMaxResults(0) // We do not want any results object
                .build();
    }
}
