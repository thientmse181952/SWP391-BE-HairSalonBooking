package com.example.demo.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpService {
    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.from.number}")
    private String fromNumber;

    private final Random random = new Random();

    public String generateOtp() {
        return String.format("%06d", random.nextInt(999999)); // Generate a 6-digit OTP
    }

    public boolean sendOtp(String phoneNumber, String otp) {
        Twilio.init(accountSid, authToken);
        Message message = Message.creator(
                new PhoneNumber(phoneNumber), // To number
                new PhoneNumber(fromNumber),  // From Twilio number
                "Your OTP is: " + otp         // SMS body
        ).create();

        return message.getStatus() != null;
    }
}
