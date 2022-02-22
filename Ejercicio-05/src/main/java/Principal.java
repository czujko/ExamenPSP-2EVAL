public class Principal {
    public static void main(String[] args) {
        CheckMail checkMail = new CheckMail();
        checkMail.setHost("mail.microinformaticos.com");
        checkMail.setUser("gustavogarcia@microinformaticos.com");
        checkMail.setPassword("Murcia2022");

        CheckMail.check(checkMail);

    }
}
