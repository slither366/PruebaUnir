package svb.fact_electronica.pdf.generadorCode;

import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class CodeQR {

    public static void main(String[] args) {

        CodeQR qr = new CodeQR();
        
        String text = "Texto de prueba para verificar la generacion de Codigo QR";

        try {
            String rutaQR = qr.generateQR(text, 300, 300);
            System.out.println("rutaQR: " + rutaQR);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public String generateQR(String text, int h, int w) throws Exception {
        
        String rutaQR = System.getProperty("user.dir") + "/QR/";
        File directorio = new File(rutaQR);
        if(!directorio.exists()){
            directorio.mkdirs();
        }
        File file = new File(rutaQR + "CODEQR.png");
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = writer.encode(text, com.google.zxing.BarcodeFormat.QR_CODE, w, h);

        BufferedImage image = new BufferedImage(matrix.getWidth(), matrix.getHeight(), BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrix.getWidth(), matrix.getHeight());
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < matrix.getWidth(); i++) {
            for (int j = 0; j < matrix.getHeight(); j++) {
                if (matrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }

        ImageIO.write(image, "png", file);

        return file.getAbsolutePath();

    }
    
}