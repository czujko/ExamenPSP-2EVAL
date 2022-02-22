import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        int port = 9000;
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Servidor UDP iniciado en el puerto " + port + "\nEsperando conexiones...");
            socket = serverSocket.accept();
        } catch (Exception e) {
            System.out.println("Error al crear el socket");
        }

        InputStream inputStream = null;
        DataInputStream dataInputStream = null;
        try {
            inputStream = socket.getInputStream();
            dataInputStream = new DataInputStream(inputStream);
            String mensaje = dataInputStream.readUTF();
            int numero = Integer.parseInt(mensaje);
            int respuesta = numero*numero;
            String respuestaString = String.valueOf(respuesta);
            System.out.println("Mensaje recibido: " + mensaje);

        OutputStream outputStream = null;
        DataOutputStream dataOutputStream = null;

            outputStream = socket.getOutputStream();
            dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeUTF(respuestaString);
            inputStream.close();
            dataInputStream.close();
            outputStream.close();
            dataOutputStream.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

