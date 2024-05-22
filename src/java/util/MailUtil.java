///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package util;
//
///**
// *
// * @author Admin
// */
//import jakarta.mail.*;
//import jakarta.mail.internet.*;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Properties;
//
//public class MailUtil {
//
//    public MailUtil() {
//
//    }
//
//    public void sendEmail(String to, String subject, String content) throws MessagingException, IOException {
//
//        Properties properties = new Properties();
//        
//        try (InputStream input = new FileInputStream("web/WEB-INF/config/public/mail.properties")) {
//            if (input == null) {
//                throw new IOException("Mail properties file not found");
//            }
//            properties.load(input);
//        } catch (IOException ex) {
//            throw new RuntimeException("Failed to load mail properties", ex);
//        }
//
//        Session session = Session.getInstance(properties, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(properties.getProperty("mail.smtp.user"), properties.getProperty("mail.smtp.password"));
//            }
//        });
//
//        Message message = new MimeMessage(session);
//        message.setFrom(new InternetAddress(properties.getProperty("mail.smtp.user")));
//        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//        message.setSubject(subject);
//        message.setText(content);
//
//        Transport.send(message);
//        
//    }
//
//}