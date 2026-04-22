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

    public byte[] generarQr(String texto) {

        try {
            QRCodeWriter qrWriter = new QRCodeWriter();
            BitMatrix matrix = qrWriter.encode(texto, BarcodeFormat.QR_CODE, 300, 300);

            BufferedImage imagen = MatrixToImageWriter.toBufferedImage(matrix);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imagen, "png", baos);

            return baos.toByteArray();

        } catch (WriterException e) {
            throw new RuntimeException("Error generando QR", e);
        } catch (Exception e) {
            throw new RuntimeException("Error convirtiendo QR a bytes", e);
        }
    }
}
