package com.entralia.entralia.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;

@Service
public class QrService {

    // Genera un QR a partir de un texto y lo devuelve como un array de bytes (PNG)

    public byte[] generarQr(String texto) {

        try {
            // Crea el escritor de QR
            QRCodeWriter qrWriter = new QRCodeWriter();

            // Genera la matriz del QR (300x300 px)
            BitMatrix matrix = qrWriter.encode(texto, BarcodeFormat.QR_CODE, 300, 300);

            // Convierte la matriz en una imagen
            BufferedImage imagen = MatrixToImageWriter.toBufferedImage(matrix);

            // Convierte la imagen a bytes PNG
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imagen, "png", baos);

            return baos.toByteArray(); // Devuelve el QR en bytes

        } catch (WriterException e) {
            throw new RuntimeException("Error generando QR", e);
        } catch (Exception e) {
            throw new RuntimeException("Error convirtiendo QR a bytes", e);
        }
    }
}
