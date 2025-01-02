package com.example.demo21.service.Implementation;

import com.example.demo21.entity.SubscribeDocument;
import com.example.demo21.repository.SubscribeRepository;
import com.example.demo21.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Date;

@Service
public class SubscribeImpl implements SubscribeService {

    @Autowired
    private SubscribeRepository subscribeRepository;

    @Override
    public String getSubscriber (String email) {
        SubscribeDocument sub= subscribeRepository.findByEmail(email);
        OffsetDateTime currentDateTime = OffsetDateTime.now();
        Date date = Date.from(currentDateTime.toInstant());
        if(sub==null){
            SubscribeDocument subscribeDocument=new SubscribeDocument();
            subscribeDocument.setEmail(email);
            subscribeDocument.setSubscribedAt(date);
            subscribeRepository.save(subscribeDocument);
            return "Subscribed successfully";
        }
        return "Email is already subscribed";
    }
}
