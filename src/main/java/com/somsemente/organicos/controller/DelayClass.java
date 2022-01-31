package com.somsemente.organicos.controller;

import com.somsemente.organicos.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.TimerTask;

@Slf4j
@Component
public class DelayClass extends TimerTask {
    @Autowired
    EmailService emailService;

    @Override
    public void run() {
        log.info(new Date().toString());
        log.info("Rotina programada");

    }

}
