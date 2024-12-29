package com.example.demo21.controller;

import com.example.demo21.dto.BlogResponse;
import com.example.demo21.service.Blog;
import com.example.demo21.service.Implementation.BlogImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/blog")
public class BlogController {

    @Autowired
    Blog blog;

    @GetMapping("")
    public ResponseEntity<List<BlogResponse>> fetchBlog(){
        List<BlogResponse> blogResponseList=blog.fetchAllBlog();
        return ResponseEntity.ok().body(blogResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogResponse> fetchById(@PathVariable ("id") String id){
        BlogResponse blogResponse=blog.fetchById(id);
        return ResponseEntity.ok().body(blogResponse);
    }
}
