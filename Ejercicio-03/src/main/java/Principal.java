import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class Principal {
    public static void main(String[] args) {
        //Haz un programa que te permita elegir archivos con JFileChooser y los
        //vaya subiendo al directorio "superarchivos" del servidor microinformáticos.com. El
        //programa acabará cuando se pulse "Cancelar" en la ventana de JFileChooser.

        FTPClient ftpClient = new FTPClient();

        String host = "ftp.microinformaticos.com";
        String user = "gustavogarcia";
        String pass = "Murcia2022";

        try {
            System.out.println("Connecting to " + host + "...");
            ftpClient.connect(host);
            boolean login = ftpClient.login(user, pass);

            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();

            if (login) {
                System.out.println("Connected to " + host);

                FTPFile[] files = ftpClient.listFiles();
                String fileNames = "";
                for (int i = 0; i < files.length; i++) {
                    if (!(files[i].getName().equals(".")) && !(files[i].getName().equals(".."))) {
                        String f = files[i].getName();
                        if (files[i].isDirectory()) {
                            fileNames += files[i].getName() + "/";
                        }
                    }
                }

                String directorio = JOptionPane.showInputDialog("Directorios (" + fileNames + ") Introduzca el directorio: ");

                boolean success = ftpClient.changeWorkingDirectory(directorio);
                if (!success) {
                    if (ftpClient.makeDirectory(directorio)) {
                        System.out.println("Directorio creado");
                        success = ftpClient.changeWorkingDirectory(directorio);
                    } else {
                        System.out.println("Error al crear directorio");
                        System.exit(0);
                    }
                }

                System.out.println("Directorio actual: " + ftpClient.printWorkingDirectory());

                String fileName = "";
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setDialogTitle("Selecciona el archivo a subir");
                int returnValue = fileChooser.showDialog(null, "Subir");
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    fileName = selectedFile.getName();
                    String path = selectedFile.getAbsolutePath();
                    System.out.println("Subiendo " + path + "...");

                    BufferedInputStream buffIn = new BufferedInputStream(new FileInputStream(path));

                    if (ftpClient.storeFile(fileName, buffIn)) {
                        System.out.println("Archivo subido correctamente");
                    } else {
                        System.out.println("Error al subir archivo");
                    }
                    buffIn.close();
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

