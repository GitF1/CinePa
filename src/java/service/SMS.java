///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package service;
//
///**
// *
// * @author PC
// */
//public class SMS {
//    // Your Twilio Account SID and Auth Token
//
//    public static final String ACCOUNT_SID = "your_account_sid";
//    public static final String AUTH_TOKEN = "your_auth_token";
//
//    static {
//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//    }
//
//    public static void sendSms(String to, String message) {
//        Message.creator(
//                new PhoneNumber(to),
//                new PhoneNumber("your_twilio_phone_number"), // Your Twilio number
//                message
//        ).create();
//    }
//}
