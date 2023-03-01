package com.example.accesodatos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.XPathQueryService;


public class HelloController {

    public TextField txtNumDep;
    public TextField txtNombre;
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

            String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/Pruebas";
            String usu = "admin";
            String usuPwd = "pw";
            col = DatabaseManager.getCollection(URI, usu, usuPwd);
            System.out.println(col.getName());
        } catch (ClassNotFoundException | XMLDBException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnAltaClick(ActionEvent actionEvent) {
        XPathQueryService service;
        try {
            service = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            String query = "update insert <DEP_ROW> <DEPT_NO>" + txtNumDep.getText() + "</DEPT_NO> <DNOMBRE>" + txtNombre.getText() + "</DNOMBRE> <LOC>" + txtLocalidad.getText() + "</LOC></DEP_ROW> into /departamentos";
            ResourceSet result = service.query(query);
            if (result != null) {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Departamento Insertado", ButtonType.OK);
                a.showAndWait();
            }
        } catch (XMLDBException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnBajaClick(ActionEvent actionEvent) {
        XPathQueryService service;
        try {
            service = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            String query = "update delete /departamentos/DEP_ROW[DEPT_NO = " + txtNumDep.getText() + "]";
            ResourceSet result = service.query(query);
            if (result != null) {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Departamento Insertado", ButtonType.OK);
                a.showAndWait();
            }
        } catch (XMLDBException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnModificacionClick(ActionEvent actionEvent) {
        XPathQueryService service;
        try {
            service = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            String query = "update replace /departamentos/DEP_ROW[DEPT_NO = " + txtNumDep.getText() + "] with "
                    + "<DEP_ROW> <DEPT_NO>" + txtNumDep.getText() + "</DEPT_NO> <DNOMBRE>" + txtNombre.getText() + "</DNOMBRE> <LOC>" + txtLocalidad.getText() + "</LOC></DEP_ROW>";
            ResourceSet result = service.query(query);
            if (result != null) {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Departamento Insertado", ButtonType.OK);
                a.showAndWait();
            }
        } catch (XMLDBException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnLimpiarClick(ActionEvent actionEvent) {
        txtLocalidad.clear();
        txtNombre.clear();
        txtNumDep.clear();
    }

    public void btnBuscarClick(ActionEvent actionEvent) {
        XPathQueryService service;
        try {
            service = (XPathQueryService) col.getService("XPathQueryService", "1.0");
            String query = "/departamentos/DEP_ROW[DEPT_NO=" + txtNumDep.getText() + "]/DNOMBRE/text()";
            String query2 = "/departamentos/DEP_ROW[DEPT_NO=" + txtNumDep.getText() + "]/LOC/text()";
            String query3 = "/departamentos/DEP_ROW[DEPT_NO=" + txtNumDep.getText() + "]";
            ResourceSet resultNombre = service.query(query);
            ResourceSet resultLoc = service.query(query2);
            ResourceSet resultEntero = service.query(query3);
            System.out.println(resultNombre.getSize());
            System.out.println(resultLoc.getSize());
            System.out.println(resultEntero.getSize());
            ResourceIterator i = resultNombre.getIterator();
            ResourceIterator j = resultLoc.getIterator();
            ResourceIterator k = resultEntero.getIterator();
            if (!i.hasMoreResources() && !j.hasMoreResources() && !k.hasMoreResources()) {
                System.out.println("Consulta nulla");
            } else {
                while (i.hasMoreResources() && j.hasMoreResources()) {
                    Resource r = i.nextResource();
                    Resource rs = j.nextResource();
                    Resource rt = k.nextResource();
                    txtNombre.setText((String) r.getContent());
                    txtLocalidad.setText((String) rs.getContent());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Información");
                    alert.setHeaderText("Información del departamento");
                    alert.getDialogPane().setContent(new TextArea((String) rt.getContent()));
                    alert.showAndWait();
                    System.out.println((String) r.getContent());
                    System.out.println((String) rs.getContent());
                }
            }
        } catch (XMLDBException e) {
            throw new RuntimeException(e);
        }
    }
}