package com.example.demo21.utils;

import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

public class Constants {
    private Constants() {} // prevent instantiation

//    public static final class Index {
//        private Index() {}
//
//        // The name of the Elasticsearch index used for suggestions
//        public static final IndexCoordinates SUGGESTION = IndexCoordinates.of("products"); // replace "products" with your actual index name
//    }
//
//    public static final class Suggestion {
//        private Suggestion() {}
//
//        public static final String SUGGEST_NAME    = "product-suggest";
//        public static final String SEARCH_TERM     = "searchTerm"; // the completion field name in your ES mapping
//
//    }
public static class Index {
    public static final IndexCoordinates SUGGESTION = IndexCoordinates.of("suggestions");
    public static final IndexCoordinates BUSINESS = IndexCoordinates.of("businesses");
}

    public static class Suggestion {
        public static final String SEARCH_TERM = "search_term";
        public static final String SUGGEST_NAME = "search-term-suggest";
    }

    public static class Fuzzy {
        public static final String LEVEL = "0";
        public static final Integer PREFIX_LENGTH = 2;
    }
}
