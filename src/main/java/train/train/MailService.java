package train.train;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Properties;

public class MailService {

    public static void ReservationEmail(String user_mail, String FirstName, String LastName, String user_password) throws MessagingException {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.transport.protocol","smtp");


        String MyAccountEmail = "railway.reservation.system.ticket@gmail.com";
        String password = "snenbwlamimkawzh";


        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MyAccountEmail, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(MyAccountEmail));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(user_mail));
        message.setSubject("Registration completed successfully!!!");


        String msg1 = "<p style=\"text-align: center;\"><span style=\"font-family: Courier New, courier;\">" + FirstName + " it&apos;s a pleasure to have you on board!</span></p>\n" +
                "<p><br></p>\n" +
                "<p><span style='font-family: \"Courier New\", courier; font-size: 14px;'>You have just created an account on our Train Reservation System App.</span></p>\n" +
                "<p><span style='font-family: \"Courier New\", courier; font-size: 14px;'>Here are your credentials:</span></p>\n" +
                "<p><span style='font-family: \"Courier New\", courier; font-size: 14px;'>email - " + user_mail + "</span></p>\n" +
                "<p><span style='font-family: \"Courier New\", courier; font-size: 14px;'>passoword - "+ user_password + "</span></p>\n" +
                "<p><span style='font-family: \"Courier New\", courier; font-size: 14px;'><br></span></p>\n" +
                "<p><span style='font-family: \"Courier New\", courier; font-size: 14px;'>Thank you for traveling with us!</span></p>\n" +
                "<p><span style='font-family: \"Courier New\", courier; font-size: 14px;'><br></span></p>\n" +
                "<p><span style='font-family: \"Courier New\", courier; font-size: 14px;'>Train Reservation System Team</span></p>\n" +
                "<p><br></p>\n" +
                "<p><br></p>";


        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg1,"text/html;charet=utf-8");


        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);
        Transport.send(message);

    }

    public static void SendTicketMailService(String user_mail, String FirstName, String LastName, String user_password){

    }

}
