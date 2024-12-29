package com.example.demo21.service;

import com.example.demo21.dto.BlogResponse;

import java.util.List;

public interface Blog {
    public List<BlogResponse> fetchAllBlog();
    public BlogResponse fetchById(String id);
}
