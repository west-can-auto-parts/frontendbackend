package com.example.demo21.service.Implementation;

import com.example.demo21.dto.BlogResponse;
import com.example.demo21.entity.BlogDocument;
import com.example.demo21.repository.BlogRepository;
import com.example.demo21.service.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlogImpl implements Blog {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public List<BlogResponse> fetchAllBlog () {
        List<BlogDocument> blogDocData=blogRepository.findAll();
        List<BlogResponse> blogResponseList=new ArrayList<>();
        for(BlogDocument bg:blogDocData){
            BlogResponse bgRes=new BlogResponse();
            bgRes.setId(bg.getId());
            bgRes.setTitle(bg.getTitle());
            bgRes.setContent(bg.getContent());
            bgRes.setImageUrl(bg.getImageUrl());
            bgRes.setCategories(bg.getCategories());
            bgRes.setTags(bg.getTags());
            bgRes.setAuthorName(bg.getAuthorName());
            bgRes.setAuthorLinkedin(bg.getAuthorLinkedin());
            bgRes.setCreatedAt(bg.getCreatedAt());
            bgRes.setFeatured(bg.isFeatured());
            bgRes.setNewBlog(bg.isNewBlog());
            blogResponseList.add(bgRes);
        }
        return blogResponseList;
    }

    @Override
    public BlogResponse fetchById (String id) {
        Optional<BlogDocument> blogDocument=blogRepository.findById(id);
        if(blogDocument.isEmpty()){
            return null;
        }
        BlogDocument bgDocmunet=blogDocument.get();
        BlogResponse blogResponse=new BlogResponse();
        blogResponse.setId(bgDocmunet.getId());
        blogResponse.setNewBlog(bgDocmunet.isNewBlog());
        blogResponse.setContent(bgDocmunet.getContent());
        blogResponse.setCategories(bgDocmunet.getCategories());
        blogResponse.setTags(bgDocmunet.getTags());
        blogResponse.setTitle(bgDocmunet.getTitle());
        blogResponse.setAuthorName(bgDocmunet.getAuthorName());
        blogResponse.setAuthorLinkedin(bgDocmunet.getAuthorLinkedin());
        blogResponse.setCreatedAt(bgDocmunet.getCreatedAt());
        blogResponse.setFeatured(bgDocmunet.isFeatured());
        return blogResponse;
    }
}
