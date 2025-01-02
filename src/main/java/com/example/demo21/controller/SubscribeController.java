package com.example.demo21.controller;

import com.example.demo21.dto.SubscribeRequest;
import com.example.demo21.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SubscribeController {

    @Autowired
    private SubscribeService subscribeService;

    @PostMapping("/subscribe")
    public ResponseEntity<Map<String, String>> setSubscribe(@RequestBody SubscribeRequest subscribeRequest){
        String response=subscribeService.getSubscriber(subscribeRequest.getEmail());
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", response);
        return ResponseEntity.ok().body(responseBody);
    }

}
