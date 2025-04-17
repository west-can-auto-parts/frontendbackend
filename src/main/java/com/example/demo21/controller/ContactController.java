package com.example.demo21.controller;

import com.example.demo21.dto.ContactRequest;
import com.example.demo21.dto.ContactResponse;
import com.example.demo21.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/contact")
@Tag(name = "Contact", description = "Contact management API endpoints")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @Operation(summary = "Save contact information", description = "Save customer contact information and send notification email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Contact information saved successfully",
                    content = @Content(schema = @Schema(implementation = ContactResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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
