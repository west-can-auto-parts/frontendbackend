package com.example.demo21.service;

import com.example.demo21.dto.ContactRequest;
import com.example.demo21.dto.ContactResponse;

public interface ContactService {
    public ContactResponse saveContact(ContactRequest contactRequest);
}
