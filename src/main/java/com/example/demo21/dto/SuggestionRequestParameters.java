package com.example.demo21.dto;

import org.apache.coyote.BadRequestException;
import org.springframework.util.StringUtils;

import java.util.Objects;

public record SuggestionRequestParameters(String prefix,
                                          Integer limit) {

    public SuggestionRequestParameters {
        if(!StringUtils.hasText(prefix)){
            try {
                throw new BadRequestException("prefix can not be empty");
            } catch (BadRequestException e) {
                throw new RuntimeException(e);
            }
        }
        limit = Objects.requireNonNullElse(limit, 10);
    }

}
