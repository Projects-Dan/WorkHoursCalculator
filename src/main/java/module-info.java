module com.example.hourgenerator2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hourgenerator2 to javafx.fxml;
    exports com.example.hourgenerator2;
}