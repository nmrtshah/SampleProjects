/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finlogic.business.finstudio.licencegenerate;

import com.finlogic.util.Logger;
import com.finlogic.util.pdfsign.PDFSignEToken;
import com.finlogic.util.pdfsign.SignPDFConfigBean;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import javax.imageio.ImageIO;
import java.util.Base64;

/**
 *
 * @author njuser
 */
public class CommonLicenceCode
{

//    private final String utilityDirPath = new HardCodeProperty().getProperty("certificatepdfpath");
    public String secureExpDate(String expDate) throws IOException, DocumentException
    {

        byte[] salt = new byte[8];
        Random rand = new Random((new Date()).getTime());
        rand.nextBytes(salt);
      //String secureDate = encoder.encode(salt) + encoder.encode(expDate.getBytes());
        String secureDate = Base64.getEncoder().encodeToString(salt)+Base64.getEncoder().encodeToString(expDate.getBytes());
        return secureDate;
    }

    public void loadSecureImage(String sourceFolder, String logoFileName, String productDirPath, String secureExpDate) throws IOException, DocumentException
    {
        String file_name = sourceFolder + logoFileName;
        BufferedImage image_orig = getImage(file_name);

//        Logger.DataLogger("data : " + secureExpDate);
        BufferedImage image = user_space(image_orig);
        image = add_text(image, secureExpDate);

        setImage(image, new File(productDirPath + logoFileName));

    }

    public void createSecureDoc(String sourceFilePath, String secureImagePath, String resultSecureDocPath, String OWNER_PASS, String clientId, String folderName) throws IOException, DocumentException
    {

        try
        {

//#########################//add secure image            
            PdfReader pdfReader = new PdfReader(sourceFilePath);
            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(resultSecureDocPath));
            Image image = Image.getInstance(secureImagePath);

            PdfContentByte content = pdfStamper.getOverContent(1);
            image.setAbsolutePosition(50f, 650f);
            content.addImage(image);
            pdfStamper.close();

//####################// PDF Signing
            SignPDFConfigBean snConfig = new SignPDFConfigBean();
            String signedFilePath = resultSecureDocPath.replace(".pdf", "_signed.pdf");
            snConfig.setSrcPDFPath(resultSecureDocPath);
            snConfig.setDestPDFPath(signedFilePath);
            snConfig.setReason("product");
            snConfig.setLocation("Surat");
            snConfig.setLlx(36);
            snConfig.setLly(748);
            snConfig.setUrx(144);
            snConfig.setUry(780);

            PDFSignEToken pdfETokenObj = new PDFSignEToken();
            pdfETokenObj.signPDF(snConfig);

//#################// password protected
            String USER_PASS = "f82q=+4P";
//            String OWNER_PASS = PropertyReader.getProperty("ownerpassword");
//            System.out.println(signedFilePath);
            PdfReader reader1 = new PdfReader(signedFilePath);
            PdfStamper stamper = new PdfStamper(reader1, new FileOutputStream(signedFilePath.replace("_signed.pdf", "Certificate" + clientId + ".pdf")));
            stamper.setEncryption(USER_PASS.getBytes(), OWNER_PASS.getBytes(), PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128 | PdfWriter.DO_NOT_ENCRYPT_METADATA);
            stamper.close();
            reader1.close();

//            generateZip(folderName);
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
    }

    private BufferedImage user_space(BufferedImage image)
    {
        //create new_img with the attributes of image
        BufferedImage new_img = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D graphics = new_img.createGraphics();
        graphics.drawRenderedImage(image, null);
        graphics.dispose(); //release all allocated memory for this image
        return new_img;
    }

    private BufferedImage getImage(String f)
    {
        BufferedImage image = null;
        File file = new File(f);

        try
        {
            image = ImageIO.read(file);
        }
        catch (Exception ex)
        {
            Logger.ErrorLogger(ex);
        }
        return image;
    }

    private BufferedImage add_text(BufferedImage image, String text)
    {
        //convert all items to byte arrays: image, message, message length
        byte img[] = get_byte_data(image);
        byte msg[] = text.getBytes();
        byte len[] = bit_conversion(msg.length);
        try
        {
            encode_text(img, len, 0); //0 first positiong
            encode_text(img, msg, 32); //4 bytes of space for length: 4bytes*8bit = 32 bits
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
        }
        return image;
    }

    private byte[] get_byte_data(BufferedImage image)
    {
        WritableRaster raster = image.getRaster();
        DataBufferByte buffer = (DataBufferByte) raster.getDataBuffer();
        return buffer.getData();
    }

    private byte[] bit_conversion(int i)
    {
        byte byte3 = (byte) ((i & 0xFF000000) >>> 24); //0
        byte byte2 = (byte) ((i & 0x00FF0000) >>> 16); //0
        byte byte1 = (byte) ((i & 0x0000FF00) >>> 8); //0
        byte byte0 = (byte) ((i & 0x000000FF));

        return (new byte[]
        {
            byte3, byte2, byte1, byte0
        });
    }

    private byte[] encode_text(byte[] image, byte[] addition, int offset)
    {
        //check that the data + offset will fit in the image
        if (addition.length + offset > image.length)
        {
            throw new IllegalArgumentException("File not long enough!");
        }
        //loop through each addition byte
        for (int i = 0; i < addition.length; ++i)
        {
            //loop through the 8 bits of each byte
            int add = addition[i];
            for (int bit = 7; bit >= 0; --bit, ++offset) //ensure the new offset value carries on through both loops
            {
                //assign an integer to b, shifted by bit spaces AND 1
                //a single bit of the current byte
                int b = (add >>> bit) & 1;
                //assign the bit by taking: [(previous byte value) AND 0xfe] OR bit to add
                //changes the last bit of the byte in the image to be the bit of addition
                image[offset] = (byte) ((image[offset] & 0xFE) | b);
            }
        }
        return image;
    }

    private boolean setImage(BufferedImage image, File file)
    {
        try
        {
            file.delete(); //delete resources used by the File
            ImageIO.write(image, "png", file);
            return true;
        }
        catch (Exception e)
        {
            Logger.ErrorLogger(e);
            return false;
        }
    }

}