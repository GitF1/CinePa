package service;

import com.sun.mail.smtp.SMTPSendFailedException;
import java.util.Properties;
import java.util.Random;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.User;

/**
 * Sends an email to the user.
 */
public class SendEmail {

    private final String logo_url = "";

    public SendEmail() {
    }
//    private String EMAIL_PATTERN
//            = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
//
//    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
//
//    public static boolean isValid(final String email) {
//        if (email == null) {
//            return false;
//        }
//        Matcher matcher = pattern.matcher(email);
//        return matcher.matches();
//    }

    public String getRanDom() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }
    
    public boolean sendEmail(String email, String code) {
        User user = new User(email, code);
        return sendEmail(user);
    }

    public boolean sendEmail(User user) {
        boolean test = false;

        String toEmail = user.getEmail();
        String fromEmail = "cinepa.org@gmail.com";  // your email
        String password = "yknrqglbmlqumpwt";  // your app password

        try {

            Properties pr = new Properties();
            pr.setProperty("mail.smtp.host", "smtp.gmail.com");
            pr.setProperty("mail.smtp.port", "587");
            pr.setProperty("mail.smtp.auth", "true");
            pr.setProperty("mail.smtp.starttls.enable", "true");

            // SSL properties should not be used when using STARTTLS
            pr.remove("mail.smtp.ssl.enable");
            pr.remove("mail.smtp.socketFactory.port");
            pr.remove("mail.smtp.socketFactory");

            // Get session
            Session session = Session.getInstance(pr, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });

            Message mess = new MimeMessage(session);
            mess.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            mess.setFrom(new InternetAddress(fromEmail));
            mess.setSubject("Email Verification");

            // Create HTML content
            String htmlContent = "<html>"
                    + "<body style='font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f4;'>"
                    + "<table align='center' width='600' style='border-collapse: collapse; margin: 20px auto; background: #fff; padding: 20px; border: 1px solid #ddd; border-radius: 10px;'>"
                    + "    <tr>"
                    + "        <td align='center' style='padding: 20px 0;'>"
                    + "            <img src='" + logo_url + "' alt='Your Company Logo' style='height: 50px;'>"
                    + "        </td>"
                    + "    </tr>"
                    + "    <tr>"
                    + "        <td style='padding: 20px;'>"
                    + "            <h2 style='color: #333; text-align: center;'>Register Confirmation</h2>"
                    + "            <p style='color: #666; text-align: center;'>Thank you for registering. Please verify your account using the code below:</p>"
                    + "            <div style='text-align: center; margin: 20px 0;'>"
                    + "                <a href='#' style='background-color: #0066cc; color: #fff; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block; font-size: 18px;'>"
                    + user.getCode() + "</a>"
                    + "            </div>"
                    + "            <p style='color: #666; text-align: center;'>If you did not register for this account, please ignore this email.</p>"
                    + "            <p style='color: #666; text-align: center;'>Best regards,<br>Your Company</p>"
                    + "        </td>"
                    + "    </tr>"
                    + "    <tr>"
                    + "        <td align='center' style='padding: 20px; font-size: 12px; color: #999;'>"
                    + "            <p>&copy; 2024 CinePa. All rights reserved.</p>"
                    + "            <p>CinePa, [123 Your Street], Danang, 43</p>"
                    + "        </td>"
                    + "    </tr>"
                    + "</table>"
                    + "</body>"
                    + "</html>";

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(htmlContent, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            mess.setContent(multipart);
            Transport.send(mess);
            test = true;

        } catch (SMTPSendFailedException e) {
            e.printStackTrace();  // Specific SMTP error
        } catch (MessagingException e) {
            e.printStackTrace();  // General messaging error
        } catch (Exception e) {
            e.printStackTrace();  // Any other exception
        }
        return test;
    }
}
