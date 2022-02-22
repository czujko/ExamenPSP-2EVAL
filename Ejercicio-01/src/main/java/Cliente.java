import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 9000;

        System.out.println("Cliente UDP Iniciado...");
        Socket socket = null;
        try {
            socket = new Socket(host, port);
            System.out.println("Cliente UDP Conectado a Servidor");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Introduce un número: ");
            String mensaje = sc.nextLine();

            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(mensaje);
            dataOutputStream.flush();

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            System.out.println("Mensaje respuesta del Servidor: " + dataInputStream.readUTF());

            dataInputStream.close();
            dataOutputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
