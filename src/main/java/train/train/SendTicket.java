package train.train;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class SendTicket {

    private static User loggedInUser;
    private static Train selectedTrain;


    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public static void TicketviaEmail(User user, Train train) throws MessagingException, WriterException, IOException {


        loggedInUser = user;
        selectedTrain=train;

        ////////////////////////// PDF GENERATOR //////////////////////

        // time generator
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH.mm.ss");
        LocalDateTime now = LocalDateTime.now();
        String pdf_generate_time = dtf.format(now);


        ///////////////////GENERATE QR CODE /////////////////////////
        String data = "Name: " + user.getFirstname() +" "+ user.getLastname() + "\n"+ "From: " + train.getOrigin() + "\n" + "To: " + train.getDestination() + "\n" +
                "Train number: " +train.getTrain_number() + "\n" + "Price: " + train.getPrice() + "\n" + "Ticket id: " + pdf_generate_time ;


        String path = "qr.jpg";

        BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 500, 500);
        MatrixToImageWriter.writeToPath(matrix,"jpg", Paths.get(path));
        //////////////////////////////////////////////////////////////////


        try {


            String file ="ticket"+ pdf_generate_time +".pdf";

            com.itextpdf.kernel.pdf.PdfWriter writer = new PdfWriter(file);

            PdfDocument pdfDoc = new PdfDocument(writer);


            Document document = new Document(pdfDoc);

            String imageFile = "qr.jpg"; //QRCODE GENERATOR ///

            ImageData data_img = ImageDataFactory.create(imageFile);

            Image img = new Image(data_img);
            img.setFixedPosition(50,650);
            img.scaleToFit(150,150);


            String logoFile = "image/train_reservation.png";
            ImageData data_imageLogoFile = ImageDataFactory.create(logoFile);

            Image logo = new Image(data_imageLogoFile);
            logo.setFixedPosition(450,700);
            logo.scaleToFit(100,100);


            // time generator 2
            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH.mm.ss");
            LocalDateTime now2 = LocalDateTime.now();
            String pdf_generate_time2 = dtf2.format(now2);


            //TEKST
            Paragraph date_upper = new Paragraph("Ticket " + train.getDate());
            Paragraph route_info_upper = new Paragraph(train.getOrigin() + " -> " + train.getOrigin());
            Paragraph ticket_client_info = new Paragraph("The ticket is valid together with a document with a photo\n confirming identity." +
                    "The ticket must be presented for inspection\n at each request of the inspection body on board the train.");
            Paragraph travel_info = new Paragraph("Travel information");

            //Linia
            Paragraph line = new Paragraph("---------------------------------------------------------------------------------------------------------------------------");

            //Rezerwacja info tickets
            Paragraph client_name = new Paragraph("Passenger - " + user.getFirstname() + " " +  user.getLastname());

            Paragraph departure_station = new Paragraph("Departure Station - " + train.getOrigin());
            Paragraph destination_station = new Paragraph("Destination Station - "+ train.getDestination());
            Paragraph date_yourney = new Paragraph("Date - " + train.getDate());
            Paragraph train_number_info = new Paragraph("Train number - "+ train.getTrain_number());




            StringBuilder time_departure = new StringBuilder(train.getDeparture_time());
            time_departure.setCharAt(2, '.');

            StringBuilder time_arrival = new StringBuilder(train.getArival_time());
            time_arrival.setCharAt(2, '.');


            Double time_travel_count =  Double.parseDouble(String.valueOf(time_arrival)) -  Double.parseDouble(String.valueOf(time_departure));

            String time_travel_count_string = Double. toString(round(time_travel_count,2));

            Paragraph travel_time_info = new Paragraph("Travel time - "+ time_travel_count_string + "h" );

            Paragraph ticket_price_info = new Paragraph("Price - " + train.getPrice() + " zl");
            Paragraph time_departure_info = new Paragraph("Departure time - " + train.getDeparture_time() + "h");
            Paragraph time_arrival_info = new Paragraph("Arrival tine - " + train.getArival_time()+ "h");


            Paragraph pdf_time_generated = new Paragraph("Document generated: " + pdf_generate_time2);

            date_upper.setFixedPosition(1,230,765,100);
            route_info_upper.setFixedPosition(1,230,740,100);
            ticket_client_info.setFontSize(8).setFixedPosition(1,230,650,1000);
            travel_info.setFixedPosition(1,250,600,100);


            line.setFixedPosition(1,50,580,1000);

            client_name.setFixedPosition(1,70,550,1000);
            departure_station.setFixedPosition(1,70,520,1000);
            destination_station.setFixedPosition(1,70,490,1000);
            date_yourney.setFixedPosition(1,400,520,1000);
            train_number_info.setFixedPosition(1,400,490,1000);
            travel_time_info.setFixedPosition(1,400,460,1000);
            ticket_price_info.setFixedPosition(1,400,430,1000);
            time_departure_info.setFixedPosition(1,400,400,1000);
            time_arrival_info.setFixedPosition(1,400,370,1000);


            pdf_time_generated.setFontSize(8).setFixedPosition(1,400,350,100);


            //Text

            document.add(date_upper);
            document.add(route_info_upper);
            document.add(ticket_client_info);
            document.add(travel_info);
            document.add(line);
            document.add(client_name);

            document.add(departure_station);
            document.add(destination_station);
            document.add(date_yourney);
            document.add(train_number_info);
            document.add(travel_time_info);
            document.add(ticket_price_info);
            document.add(time_departure_info);
            document.add(time_arrival_info);

            document.add(pdf_time_generated);

            ///Images
            document.add(img);
            document.add(logo);

            document.close();
        }catch (Exception e){
            System.err.println(e);
        }




        /////////////SEND EMAIL ///////////////////////////


        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.transport.protocol", "smtp");

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
        message.setSubject("Your Ticket from " + selectedTrain.getOrigin() + " to " + selectedTrain.getDestination());

        String msg1 = "<p><span style=\"font-family: Courier New, courier;\">Dear, "+ loggedInUser.getFirstname() + " " + loggedInUser.getLastname() +",</span></p>\n" +
                "<p><span style=\"font-family: Courier New, courier;\">you just booked your train ride, which will take place on "+train.getDate() +".</span></p>\n" +
                "<p><span style=\"font-family: Courier New, courier;\"><br class=\"Apple-interchange-newline\">Please save the attached pdf file with the ticket on your mobile device or print it out for inspection on the train. &nbsp;</span></p>\n" +
                "<p><span style=\"font-family: Courier New, courier;\">Our crew has QR code readers, thanks to which checking the ticket is quick and without contact, which is important during the pandemic.</span></p>\n" +
                "<p><span style=\"font-family: Courier New, courier;\"><br></span></p>\n" +
                "<p><span style=\"font-family: Courier New, courier;\"><br></span></p>\n" +
                "<p><span style=\"font-family: Courier New, courier;\">We wish you a pleasant journey</span></p>\n" +
                "<p><span style=\"font-family: Courier New, courier;\">Train Reservation team</span></p>";

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg1, "text/html;charet=utf-8");

        MimeBodyPart pdfAttachment = new MimeBodyPart();

        pdfAttachment.attachFile("ticket"+ pdf_generate_time +".pdf"); // PDF ATTACHMENT TICKET ///

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        multipart.addBodyPart(pdfAttachment);


        message.setContent(multipart);

        Transport.send(message);
    }
}

