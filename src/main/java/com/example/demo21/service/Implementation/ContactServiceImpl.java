package com.example.demo21.service.Implementation;

import com.example.demo21.dto.ContactRequest;
import com.example.demo21.dto.ContactResponse;
import com.example.demo21.entity.ContactDocument;
import com.example.demo21.repository.ContactRepository;
import com.example.demo21.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Date;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private JavaMailSender mailSender;


    @Override
    public ContactResponse saveContact (ContactRequest contactRequest) {
        OffsetDateTime currentDateTime = OffsetDateTime.now();
        Date date = Date.from(currentDateTime.toInstant());

        // Save contact data
        ContactDocument contact = new ContactDocument();
        contact.setFirstName(contactRequest.getFirstName());
        contact.setLastName(contactRequest.getLastName());
        contact.setCompany(contactRequest.getCompany());
        contact.setEmail(contactRequest.getEmail());
        contact.setPhoneNumber(contactRequest.getPhoneNumber());
        contact.setMessage(contactRequest.getMessage());
        contact.setAgreed(contactRequest.isAgreed());
        contact.setCreatedAt(date);


        // Prepare email content
        String subject = "New Contact Saved";
        String text = "A new contact has been saved with the following details:\n\n"
                + "First Name: " + contact.getFirstName() + "\n"
                + "Last Name: " + contact.getLastName() + "\n"
                + "Email: " + contact.getEmail() + "\n"
                + "Company: " + contact.getCompany() + "\n"
                + "Phone Number: " + contact.getPhoneNumber() + "\n"
                + "Message: " + contact.getMessage() + "\n"
                + "Agreed: " + contact.isAgreed() + "\n"
                + "Created At: " + contact.getCreatedAt();

        sendEmail("aditya.gupta@westcanauto.com", subject, text);
        contactRepository.save(contact);

        // Create response
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
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
