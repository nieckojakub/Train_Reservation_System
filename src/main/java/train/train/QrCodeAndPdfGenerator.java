package train.train;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

public class QrCodeAndPdfGenerator {

    // dostepne z reservation
    String from = "Tarnow";
    String to = "Krakow";
    String train_number = "1234";
    String price  = "16.90";
    String ticket_id = "121234";
    String date = "27.12.2021";

    String FirstName = "Jakub";
    String user_password = "WlazKot133";
    String LastName = "Niecko";

    String timeTravel = "1.30";

    String GenerateQRcode(User user, Train train) throws WriterException, IOException {

        String data = "Name: " + user.getFirstname() +" "+ user.getLastname() + "\n"+ "From: " + train.getDepartureStation() + "\n" + "To: " + train.getDestinatonStation() + "\n" +
                "Train number: " +train.getTrainNumber() + "\n" + "Price: " + train.getPriceNormal() + "\n" + "Ticket id: " + "id/dodac" ;


        String path = "C:\\Users\\nieck\\Desktop\\PROJEKT-PO\\qr.jpg";


        BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 500, 500);
        MatrixToImageWriter.writeToPath(matrix,"jpg", Paths.get(path));

        return path;
    }

    public static void GeneratePdfTicket(User user,Train train){


        try {

            String dest = "C:\\Users\\nieck\\Desktop\\PROJEKT-PO\\testowy.pdf"; //
            com.itextpdf.kernel.pdf.PdfWriter writer = new PdfWriter(dest);

            PdfDocument pdfDoc = new PdfDocument(writer);


            Document document = new Document(pdfDoc);
            String imageFile = "";  //
            ImageData data_img = ImageDataFactory.create(imageFile);

            Image img = new Image(data_img);
            img.setFixedPosition(50,650);
            img.scaleToFit(150,150);


            String logoFile = "D:\\JAVA\\Train_Reservation_System_ALPHA\\image\\train_reservation.png";
            ImageData data_imageLogoFile = ImageDataFactory.create(logoFile);

            Image logo = new Image(data_imageLogoFile);
            logo.setFixedPosition(450,700);
            logo.scaleToFit(100,100);

            // time generator
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String pdf_generate_time = dtf.format(now);
            System.out.println(pdf_generate_time);

            //TEKST
            Paragraph date_upper = new Paragraph("Ticket " + train.getDate());
            Paragraph route_info_upper = new Paragraph(train.getDepartureStation() + " -> " + train.getDestinatonStation());
            Paragraph ticket_client_info = new Paragraph("The ticket is valid together with a document with a photo\n confirming identity." +
                    "The ticket must be presented for inspection\n at each request of the inspection body on board the train.");
            Paragraph travel_info = new Paragraph("Travel information");

            //Linia
            Paragraph line = new Paragraph("---------------------------------------------------------------------------------------------------------------------------");

            //Rezerwacja info tickets
            Paragraph client_name = new Paragraph("Passenger - " + user.getFirstname() + " " +  user.getLastname());

            Paragraph departure_station = new Paragraph("Departure Station - " + train.getDepartureStation());
            Paragraph destination_station = new Paragraph("Destination Station - "+ train.getDestinatonStation());
            Paragraph date_yourney = new Paragraph("Date - " + train.getDate());
            Paragraph train_number_info = new Paragraph("Train number - "+ train.getTrainNumber());
            Paragraph travel_time_info = new Paragraph("Travel time - "+ "!!!!!!!!!!!!!" + "h" );
            Paragraph ticket_price_info = new Paragraph("Price - " + train.getPriceNormal() + " zl");

            Paragraph pdf_time_generated = new Paragraph("Document generated: " + pdf_generate_time);

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


            pdf_time_generated.setFontSize(8).setFixedPosition(1,400,400,100);


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


            document.add(pdf_time_generated);


            ///Images
            document.add(img);
            document.add(logo);

            document.close();

            System.out.println("PDF GENERATED");

        }catch (Exception e){
            System.err.println(e);
        }


    }
}
