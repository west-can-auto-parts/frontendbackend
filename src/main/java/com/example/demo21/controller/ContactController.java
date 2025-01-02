package com.example.demo21.controller;

import com.example.demo21.dto.ContactRequest;
import com.example.demo21.dto.ContactResponse;
import com.example.demo21.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @PostMapping("/save")
    public ResponseEntity<ContactResponse> saveContact(@RequestBody ContactRequest contactRequest) {
        try {
            ContactResponse response = contactService.saveContact(contactRequest);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ContactResponse());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ContactResponse());
        }
    }
}
