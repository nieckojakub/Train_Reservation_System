package train.train;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

    ////////// serwerpocztowy.pl - informacje o serwerach poczty, np porty, nazwa serwera np. dla gmaila 'smtp.gmail.com' a dla o2 'poczta.o2.pl'

    /*public static Properties getConfiguration() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        return properties;
    }

     */

    public static void sendConfirmationEmail(String recipient) throws MessagingException {
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true"); // uzyskanie autoryzacji dzieki argumentowi  true
        properties.put("mail.smtp.starttls.enable", "true"); //uzycie tls do zabezpieczenia
        properties.put("mail.smtp.host", "smtp.gmail.com");   //serwer SMTP, w tym przypadku gmail     ///// WPROWADZAMY DANE, KTORE SA POTRZEBNE DO NAWIAZANIA SESJI
        properties.put("mail.smtp.port", "587"); // port dla serwera smtp, dla gmaila 587.
        //properties.put("mail.transport.protocol", "smtp");

        String myAccountEmail = ".........";   ///////dane logowania do maila
        String password = "xxxxx";

        Session session = Session.getInstance(properties, new Authenticator() {  // TWORZYMY SESJE
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() { // getPasswordAuthentication jest potrzebne do uwierzytelnienia naszego konta
                        return new PasswordAuthentication(myAccountEmail, password);  //AUTHENTICATOR reprezentuje obiekt, który wie jak zdobyc info do połączenia
                    }
                }
        );

        Message message = prepareConfirmationMessage(session, myAccountEmail, recipient);

        Transport.send(message);
        System.out.println("Message sent succesfully");
    }

    private static Message prepareConfirmationMessage(Session session, String myAccountEmail, String recipient) { // WIADOMOSC Z POTWIERDZENIEM MAILA
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            //message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient2)); //////// DODAWANIE ODBIORCY
            String htmlCode = "<h1> Your account is activated </h1> <br/> <h2><b>You can log in now </b></h2>";
            message.setContent(htmlCode, "text/html");
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
