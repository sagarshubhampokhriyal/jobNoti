package com.jobhelp.newopening.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.bulkexports.v1.export.Job;
import com.twilio.type.PhoneNumber;

@Service
public class WhatsAppSender {

    // Replace with your actual Account SID and Auth Token
    @Value("${twilio.account.sid}")
    private String ACCOUNT_SID;

    @Value("${twilio.auth.token}")
    private String AUTH_TOKEN;

    @Value("${twilio.phone.number}")
    private String fromNumber;

    public void sendMessage(JobListing job, String toPhoneNumber) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(new PhoneNumber("whatsapp:" + toPhoneNumber), // To (your number)
                new PhoneNumber("whatsapp:" + fromNumber), // From (Twilio's sandbox number)
                "Hello from jobHelp!\n job Title:**" + job.getTitle() + "** \n url: " + job.getUrl()).create();

        System.out.println("Message sent with SID: " + message.getSid());
    }
}
