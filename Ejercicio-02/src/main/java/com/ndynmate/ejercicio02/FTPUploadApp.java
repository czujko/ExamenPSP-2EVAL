package com.ndynmate.ejercicio02;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FTPUploadApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FTPUploadApp.class.getResource("FTPUpload-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("FTPUpload v1.0");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}