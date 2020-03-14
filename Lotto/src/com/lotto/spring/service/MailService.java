package com.lotto.spring.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.chello.base.spring.core.DefaultService;


@Service("mailService")
public class MailService extends DefaultService {

    private Logger log = Logger.getLogger(this.getClass());

//    @Autowired
//    private JavaMailSender mailSender;
      
    /**
     * This method will send compose and send the message 
     * */
//    public void sendMail(String to, String subject, String body) 
//    {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(body);
//        mailSender.send(message);
//    }
  
}
