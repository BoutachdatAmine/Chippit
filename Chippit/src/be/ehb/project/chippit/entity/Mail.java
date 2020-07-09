package be.ehb.project.chippit.entity;

import be.ehb.project.chippit.db.PersonDAO;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.Scanner;

public class Mail {

    public Mail() {
    }

    public void sendEmails(String typePerson, String path) {

        String content = null;

        //Insert the location to the file you want to send in
        String subject = "Chippit";
        File file = new File(path);
        Scanner scan = null;
        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scan.hasNextLine()) {
            content = content + scan.nextLine() + "\n";
        }


        PersonDAO persondAO = new PersonDAO();
        HashSet<String> returns = null;
        if (typePerson.equals("users")) {
            returns = persondAO.getEmails("users");
        }
        if (typePerson.equals("customer")) {
            returns = persondAO.getEmails("customer");
        }
        ArrayList<String> emails = new ArrayList<>();

        for (String string : returns) {
            emails.add(string);
        }


        String[] bcc = new String[emails.size()];

        for (int i = 0; i < emails.size(); i++) {
            bcc[i] = emails.get(i);
        }


        InternetAddress[] bccAddress = new InternetAddress[bcc.length];


        //If you want to send it via Outlook
        // Insert your Outlook mail(Student.ehb also works)
        final String username = "chippit.mail@gmail.com";
        final String password = "iqacumqsurrycjxs";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });


        try {

            Message message = new MimeMessage(session);
            //Reinsert your mail from your username in InternetAddress
            message.setFrom(new InternetAddress(username));

            // To get the array of bccaddresses
            for (int i = 0; i < bcc.length; i++) {
                bccAddress[i] = new InternetAddress(bcc[i]);
            }

            // Set bcc: header field of the header.
            for (int i = 0; i < bccAddress.length; i++) {
                message.addRecipient(Message.RecipientType.BCC, bccAddress[i]);
            }

            message.setSubject(subject);
            //setContent method is used for sending HTML newsletter templates
            message.setContent(content, "text/html");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
