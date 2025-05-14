package com.esprit.microservice.offrestage.Services;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    @Value("${twilio.accountSid}")
    private String ACCOUNT_SID;

    @Value("${twilio.authToken}")
    private String AUTH_TOKEN;

    @Value("${twilio.fromPhone}")
    private String FROM_PHONE_NUMBER;


    // Initialisation de Twilio après l'injection des propriétés
    @PostConstruct
    public void init() {
        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            System.out.println("Twilio initialized successfully.");
        } catch (Exception e) {
            System.err.println("Error initializing Twilio: " + e.getMessage());
        }
    }

    public void sendSms(String toPhoneNumber, String messageBody) {
        try {
            // Création et envoi du message
            Message message = Message.creator(
                            new com.twilio.type.PhoneNumber(toPhoneNumber),
                            new com.twilio.type.PhoneNumber(FROM_PHONE_NUMBER),
                            messageBody)
                    .create();
            // Affichage du SID du message envoyé
            System.out.println("SMS sent successfully: " + message.getSid());
        } catch (ApiException e) {
            // En cas d'erreur lors de l'envoi du SMS
            System.err.println("Error sending SMS: " + e.getMessage());
        } catch (Exception e) {
            // En cas d'autres erreurs
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}
