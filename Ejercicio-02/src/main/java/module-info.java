module com.ndynmate.ejercicio02 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.net;


    opens com.ndynmate.ejercicio02 to javafx.fxml;
    exports com.ndynmate.ejercicio02;
}