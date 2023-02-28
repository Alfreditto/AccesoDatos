package com.example.accesodatos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XPathQueryService;


public class HelloController {

    public TextField txtNumDep;
    public TextField txtNombre;
    public TextField txtDep;
    public TextField txtLocalidad;
    Collection col;
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

            String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/Prueba";
            String usu = "admin";
            String usuPwd = "pw";
            col = DatabaseManager.getCollection(URI, usu, usuPwd);
            System.out.println(col.getName());
//            XPathQueryService service = (XPathQueryService) col.getServices()
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

    public void btnBuscarClick(ActionEvent actionEvent) {
        XPathQueryService service;
        try {
            service = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            String query = "/departamentos/DEP_ROW[DEPT_NO=" + txtNumDep.getText() + "]/DNOMBRE/text()";
            String query2 = "/departamentos/DEP_ROW[DEPT_NO=" + txtNumDep.getText() + "]/LOC/text()";
            ResourceSet resultNombre = service.query(query);
            ResourceSet resultLoc = service.query(query2);
            System.out.println(resultNombre.getSize());
            System.out.println(resultLoc.getSize());
            ResourceIterator i = resultNombre.getIterator();
            ResourceIterator j = resultLoc.getIterator();
            if (!i.hasMoreResources() && !j.hasMoreResources()) {
                System.out.println("Consulta nulla");
            } else {
                while (i.hasMoreResources() && j.hasMoreResources()) {
                    Resource r = i.nextResource();
                    Resource rs = j.nextResource();
                    txtNombre.setText((String) r.getContent());
                    txtLocalidad.setText((String) rs.getContent());
                    System.out.println((String) r.getContent());
                    System.out.println((String) rs.getContent());
                }
            }
        } catch (XMLDBException e) {
            throw new RuntimeException(e);
        }
    }
}