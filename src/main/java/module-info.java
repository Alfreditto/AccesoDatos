module com.example.accesodatos {
    requires javafx.controls;
    requires javafx.fxml;
    requires xmldb;


    opens com.example.accesodatos to javafx.fxml;
    exports com.example.accesodatos;
}