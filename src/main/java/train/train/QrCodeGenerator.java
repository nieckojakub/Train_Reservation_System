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

public class QrCodeGenerator {

    String GenerateQRcode(User user, Train train) throws WriterException, IOException {

        String data = "Name: " + user.getFirstname() +" "+ user.getLastname() + "\n"+ "From: " + train.getOrigin() + "\n" + "To: " + train.getDestination() + "\n" +
                "Train number: " +train.getTrain_number() + "\n" + "Price: " + train.getPrice() + "\n" + "Ticket id: " + "id/dodac" ;


        String path = "image/workingFolder/qr.jpg";

        BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 500, 500);
        MatrixToImageWriter.writeToPath(matrix,"jpg", Paths.get(path));

        return path;
    }

}
