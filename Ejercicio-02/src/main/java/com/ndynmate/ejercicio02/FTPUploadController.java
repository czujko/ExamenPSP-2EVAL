package com.ndynmate.ejercicio02;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;

public class FTPUploadController {
    @FXML
    public TextArea txtArea;
    @FXML
    public Button btnGuardar;
    @FXML
    public Button btnUpload;

    public void guardarTxt(ActionEvent actionEvent) {
        File file = new File("archivo.txt");
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
            fw.write(txtArea.getText());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        txtArea.setText("");
    }

    public void enviarFTP(ActionEvent actionEvent) {
        FTPClient cliente = new FTPClient();

        String servidor = "127.0.0.1";
        String user = "manolito";
        String passw = "almacendonmanolo";

        try {
            System.out.println("Conectándose a " + servidor);
            cliente.connect(servidor);
            boolean login = cliente.login(user, passw);

            cliente.setFileType(FTP.BINARY_FILE_TYPE);
            cliente.enterLocalPassiveMode();

            if (login) {
                System.out.println("Login correcto");

                System.out.println("Directorio actual: " + cliente.printWorkingDirectory());

                String archivo = "archivo.txt";
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(archivo));

                if (cliente.storeFile(archivo, in))
                    System.out.println(archivo + " Subido correctamente... ");
                else
                    System.out.println("No se ha podido subir el fichero... ");

                in.close(); // Cerrar flujo
                cliente.logout();
                cliente.disconnect();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}