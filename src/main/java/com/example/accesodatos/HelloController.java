package com.example.accesodatos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;


public class HelloController {


    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private void initialize() {
        try {
            String driver = "org.exist.xmldb.DatabaseImpl";
            Class cl = null;
            cl = Class.forName(driver);
            Database database = (Database) cl.newInstance();
            DatabaseManager.registerDatabase(database);

            String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/Pruebas";
            String usu = "admin";
            String usuPwd = "pw";
            Collection col = DatabaseManager.getCollection(URI, usu, usuPwd);
            System.out.println(col.getName());
        } catch (ClassNotFoundException | XMLDBException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnAltaClick(ActionEvent actionEvent) {
    }

    public void btnBajaClick(ActionEvent actionEvent) {
    }

    public void btnModificacionClick(ActionEvent actionEvent) {
    }

    public void btnLimpiarClick(ActionEvent actionEvent) {
    }

    public void btnBuscarClick(ActionEvent actionEvent) throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {

    }
}