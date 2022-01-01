package train.train;

import com.google.zxing.WriterException;

import javax.mail.*;
import javax.mail.internet.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

public class MailService {

    private static User loggedInUser;
    private Train selectedTrain;


    public static void RegisterEmail(User user) throws MessagingException {
        loggedInUser = user;


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
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(loggedInUser.getEmail()));
        message.setSubject("Registration completed successfully!!!");


        String msg1 = "<p style=\"text-align: center;\"><span style=\"font-family: Courier New, courier;\">" + loggedInUser.getFirstname() + " it&apos;s a pleasure to have you on board!</span></p>\n" +
                "<p><br></p>\n" +
                "<p><span style='font-family: \"Courier New\", courier; font-size: 14px;'>You have just created an account on our Train Reservation System App.</span></p>\n" +
                "<p><span style='font-family: \"Courier New\", courier; font-size: 14px;'>Here are your credentials:</span></p>\n" +
                "<p><span style='font-family: \"Courier New\", courier; font-size: 14px;'>email - " + loggedInUser.getEmail() + "</span></p>\n" +
                "<p><span style='font-family: \"Courier New\", courier; font-size: 14px;'>passoword - "+ loggedInUser.getPassword() + "</span></p>\n" +
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

    public void SendTicketMailService(Train train, User user,String attachment)
            throws MessagingException, IOException, WriterException {

        loggedInUser = user;
        selectedTrain=train;

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

        String email_odbiorcy = loggedInUser.getEmail();


        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(MyAccountEmail, password);
            }
        });


        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(MyAccountEmail));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email_odbiorcy));
        message.setSubject("Your Ticket from "+ selectedTrain.getOrigin() + " to " + selectedTrain.getDestination());

        String msg1 = "";

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg1,"text/html;charet=utf-8");

        MimeBodyPart pdfAttachment = new MimeBodyPart();

        pdfAttachment.attachFile(attachment); // PDF ATTACHMENT TICKET ///

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        multipart.addBodyPart(pdfAttachment);


        message.setContent(multipart);

        Transport.send(message);
    }

}
