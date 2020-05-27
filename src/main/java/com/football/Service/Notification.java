package com.football.Service;

import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Service;

import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;


import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Properties;

@Service
public class Notification {

    public Notification(){
        System.out.println("Notification object created");
    }

    @Override
    public String toString()
    {
        return "Notification object created";
    }


    /**
     * the function moves on list to notify them
     * @param listToNotify
     * @param message
     */
    public void notifyAll(List<String> listToNotify, String message){
        for(String mail:listToNotify){
            sendMailAndPdf(mail, message);
        }
    }

    /**
     * create a pdf of message and send it to mail
     * @param mail
     * @param messageContent
     */
    private void sendMailAndPdf(String mail, String messageContent){

        //Get the session object
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("Yuval.Hilla@gmail.com", "ArnoN123");//change accordingly
                    }
                });

        //compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("Yuval.Hilla@gmail.com"));//change accordingly
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
            message.setSubject("Notification from Football system!");


            String filename = "./tempPdfContent.pdf";//change accordingly

            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));

            document.open();


            Anchor anchorTarget = new Anchor(messageContent);
            anchorTarget.setName("BackToTop");
            Paragraph paragraph1 = new Paragraph();

            paragraph1.setSpacingBefore(50);

            paragraph1.add(anchorTarget);
            document.add(paragraph1);


            document.close();


            //3) create MimeBodyPart object and set your message text
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText("You got this message because you submit to get notifications");

            //4) create new MimeBodyPart object and set DataHandler object to this object
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();

            DataSource source = new FileDataSource(filename);
            messageBodyPart2.setDataHandler(new DataHandler(source));
            messageBodyPart2.setFileName(filename);

            //5) create Multipart object and add MimeBodyPart objects to this object
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);

            //6) set the multiplart object to the message object
            message.setContent(multipart);


            //send message
            Transport.send(message);

            System.out.println("message sent successfully");

            File f = new File(filename);           //file to be delete
            if (f.delete())                      //returns Boolean value
            {
                System.out.println(f.getName() + " deleted");   //getting and printing the file name
            }


        } catch (MessagingException | FileNotFoundException | DocumentException e) {
            throw new RuntimeException(e);
        }
    }
}
