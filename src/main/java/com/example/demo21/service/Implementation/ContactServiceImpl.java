package com.example.demo21.service.Implementation;

import com.example.demo21.dto.ContactRequest;
import com.example.demo21.dto.ContactResponse;
import com.example.demo21.entity.ContactDocument;
import com.example.demo21.repository.ContactRepository;
import com.example.demo21.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Date;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public ContactResponse saveContact (ContactRequest contactRequest) {
        OffsetDateTime currentDateTime = OffsetDateTime.now();
        Date date = Date.from(currentDateTime.toInstant());
        ContactDocument contact = new ContactDocument();
        contact.setFirstName(contactRequest.getFirstName());
        contact.setLastName(contactRequest.getLastName());
        contact.setCompany(contactRequest.getCompany());
        contact.setEmail(contactRequest.getEmail());
        contact.setPhoneNumber(contactRequest.getPhoneNumber());
        contact.setMessage(contactRequest.getMessage());
        contact.setAgreed(contactRequest.isAgreed());
        contact.setCreatedAt(date);
        contactRepository.save(contact);
        ContactResponse response = new ContactResponse();
        response.setFirstName(contact.getFirstName());
        response.setLastName(contact.getLastName());
        response.setEmail(contact.getEmail());
        response.setCompany(contact.getCompany());
        response.setPhoneNumber(contact.getPhoneNumber());
        response.setMessage(contact.getMessage());
        response.setAgreed(contact.isAgreed());
        response.setCreatedAt(contact.getCreatedAt());
        return response;
    }
}
