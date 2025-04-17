package com.example.demo21.controller;

import com.example.demo21.dto.BlogResponse;
import com.example.demo21.service.Blog;
import com.example.demo21.service.Implementation.BlogImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/blog")
@Tag(name = "Blog", description = "Blog management API endpoints")
public class BlogController {

    @Autowired
    Blog blog;

    @Operation(summary = "Get all blogs", description = "Retrieve a list of all blog posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved blog list",
                    content = @Content(schema = @Schema(implementation = BlogResponse.class)))
    })
    @GetMapping("")
    public ResponseEntity<List<BlogResponse>> fetchBlog(){
        List<BlogResponse> blogResponseList=blog.fetchAllBlog();
        return ResponseEntity.ok().body(blogResponseList);
    }

    @Operation(summary = "Get blog by ID", description = "Retrieve a blog post by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the blog post",
                    content = @Content(schema = @Schema(implementation = BlogResponse.class))),
            @ApiResponse(responseCode = "404", description = "Blog post not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<BlogResponse> fetchById(@PathVariable ("id") String id){
        BlogResponse blogResponse=blog.fetchById(id);
        return ResponseEntity.ok().body(blogResponse);
    }
}
