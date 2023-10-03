module com.example.laborator_4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.laborator_4 to javafx.fxml;
    opens com.example.laborator_4.Domain to javafx.base;
    exports com.example.laborator_4;
}