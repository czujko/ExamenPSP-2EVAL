import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class Principal {
    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", true);
        prop.put("mail.smtp.host", "smtp-mail.outlook.com");
        prop.put("mail.smtp.port", "587");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("gustavoalberto.ma43049@cesurformacion.com", "zS9qHHCwiXN7M-r");
            }
        });


        try {
            Message message = new javax.mail.internet.MimeMessage(session);
            message.setFrom(new InternetAddress("gustavoalberto.ma43049@cesurformacion.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("antonio.salinas.garcia@gmail.com"));
            message.setSubject("Fecha actual: " + java.time.LocalDate.now());
            message.setText("Hola, esta es la fecha actual: " + java.time.LocalDate.now());

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent("<h1>Hola, esta es la fecha actual: " + java.time.LocalDate.now() + "</h1>", "text/html");
            mimeBodyPart.setHeader("Content-Type", "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);
            Transport.send(message);
            System.out.println("Correo enviado");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        try {
            Message message = new javax.mail.internet.MimeMessage(session);
            message.setFrom(new InternetAddress("gustavoalberto.ma43049@cesurformacion.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("gustavoalberto.ma43049@cesurformacion.com"));
            message.setSubject("Fecha actual: " + java.time.LocalDate.now());
            message.setText("Hola, esta es la fecha actual: " + java.time.LocalDate.now());

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent("<h1>Hola, esta es la fecha actual: " + java.time.LocalDate.now() + "</h1>", "text/html");
            mimeBodyPart.setHeader("Content-Type", "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);
            Transport.send(message);
            System.out.println("Correo enviado");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
