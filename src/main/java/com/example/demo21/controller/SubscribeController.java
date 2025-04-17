package com.example.demo21.controller;

import com.example.demo21.dto.SubscribeRequest;
import com.example.demo21.service.SubscribeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Tag(name = "Subscription", description = "Newsletter subscription API endpoints")
public class SubscribeController {

    @Autowired
    private SubscribeService subscribeService;

    @Operation(summary = "Subscribe to newsletter", description = "Subscribe an email address to the newsletter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription processed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class)))
    })
    @PostMapping("/subscribe")
    public ResponseEntity<Map<String, String>> setSubscribe(@RequestBody SubscribeRequest subscribeRequest){
        String response=subscribeService.getSubscriber(subscribeRequest.getEmail());
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", response);
        return ResponseEntity.ok().body(responseBody);
    }

}
