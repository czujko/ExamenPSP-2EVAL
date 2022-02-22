import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

public class CheckMail {
    private String host;
    private String mailStoreType;
    private String user;
    private String password;

    public CheckMail() {
    }

    public CheckMail(String host, String mailStoreType, String user, String password) {
        this.host = host;
        this.mailStoreType = mailStoreType;
        this.user = user;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getMailStoreType() {
        return mailStoreType;
    }

    public void setMailStoreType(String mailStoreType) {
        this.mailStoreType = mailStoreType;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static void check(CheckMail c) {
        try {
            //Crea el objeto de propiedades necesario que debe coincidir con las de nuestro servidor de correo
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "pop3s");
            properties.put("mail.pop3.host", c.getHost());
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
            Session emailSession = Session.getDefaultInstance(properties);

            //Crea el objeto pop3 necesario y conecta con el servidor
            Store store = emailSession.getStore("pop3s");
            store.connect(c.getHost(), c.getUser(), c.getPassword());
            //Crea un objeto "carpeta" para guardar los correos
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            //Descarga los mensajes a un array y a continuación muestra su asunto y su contenido
            Message[] messages = emailFolder.getMessages();
            System.out.println("Número de mensajes---" + messages.length);

            String todosJuntos = "";

            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];
                System.out.println("---------------------------------");
                System.out.println("Número de correo " + (i + 1));
                System.out.println("Asunto: " + message.getSubject());
                System.out.println("De: " + message.getFrom()[0]);
                System.out.println("Contenido: " + message.getContent().toString());
                String contenido = "";

                if (message.isMimeType("text/plain")) {
                    System.out.println("This is plain text");
                    System.out.println("---------------------------");
                    System.out.println((String) message.getContent());
                    contenido = (String) message.getContent();
                } else if (message.isMimeType("multipart/*")) {
                    System.out.println("This is a Multipart");
                    System.out.println("---------------------------");
                    contenido = getTextFromMessage(message);
                    System.out.println(contenido);
                }

                todosJuntos += message.getSubject() + "\n" + message.getFrom()[0] + "\n" + contenido;

            }
            FileWriter fichero = null;
            PrintWriter pw = null;
            //guardo cada correo en un archivo
            try {
                fichero = new FileWriter("mensajes.txt");
                pw = new PrintWriter(fichero);
                pw.println("Todos los mensajes");
                pw.println("Contenido: " + todosJuntos);
                fichero.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

            //Cierra la carpeta y la conexión con el almacén del servidor
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws MessagingException, IOException {
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break; // without break same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
            }
        }
        return result;
    }
}
