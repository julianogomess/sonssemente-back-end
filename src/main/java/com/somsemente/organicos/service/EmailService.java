package com.somsemente.organicos.service;

public interface EmailService {
    void sendSimpleMessage(
            String to, String subject, String text);
}
