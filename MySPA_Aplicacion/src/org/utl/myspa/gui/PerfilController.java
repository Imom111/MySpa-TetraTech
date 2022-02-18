/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.utl.myspa.gui;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alexis
 */
public class PerfilController implements Initializable {
    @FXML private Button btnPerfil;
    @FXML private Button btnCerrarSesion;
    @FXML private Button btnRegresar;
    @FXML private JFXTextField txtNombre;
    @FXML private JFXTextField txtApellidoP;
    @FXML private JFXTextField txtApellidoM;
    @FXML private JFXTextField txtRFC;
    @FXML private JFXTextField txtTelefono;
    @FXML private ComboBox cmbGenero;
    @FXML private JFXTextField txtUsuario;
    @FXML private JFXTextField txtNumExt;
    @FXML private JFXTextField txtCalle;
    @FXML private JFXTextField txtCP;
    @FXML private JFXTextField txtColonia;
    @FXML private JFXTextField txtCiudad;
    @FXML private ComboBox cmbEstado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> estados = FXCollections.observableArrayList("Aguascalientes", "Baja California", "Baja California Sur",
             "Campeche", "Coahuila", "Colima", "Chiapas", "Chihuahua",  "Distrito Federal", "Durango",
             "Guanajuato", "Guerrero", "Hidalgo", "Jalisco", "México", "Michoacán", "Morelos", "Nayarit", "Nuevo León",
             "Oaxaca", "Puebla", "Querétaro", "Quintana Roo", "San Luis Potosi", "Sinaloa", "Sonora", "Tabasco", "Tamaulipas",
             "Tlaxcala", "Veracruz", "Yucatán", "Zacatecas", "Nacido Extranjero");
        cmbEstado.setItems(estados);
        
        ObservableList<String> sexo = FXCollections.observableArrayList("Hombre", "Mujer");
        cmbGenero.setItems(sexo);
    }    
    
    public void inicio()
    {
        try{
                Parent principal = FXMLLoader.load(getClass().getResource("/org/utl/myspa/gui/fxml/Principal.fxml"));
                Scene scene = new Scene(principal);
                Stage primaryStage = new Stage();
                primaryStage.setTitle("Inicio");
                primaryStage.setResizable(false);
                primaryStage.setScene(scene);
                primaryStage.show();
                Stage ventana = (Stage) btnRegresar.getScene().getWindow();
                ventana.close();
            } catch(Exception e){
              Alert alert = new Alert(Alert.AlertType.ERROR,"Error al establecer conexión, Intentalo de nuevo");
              alert.showAndWait();  
              e.printStackTrace();
            }
    }
    
    public void cerrarSesion(){
        try{
                Parent principal = FXMLLoader.load(getClass().getResource("/org/utl/myspa/gui/fxml/Login.fxml"));
                Scene scene = new Scene(principal);
                Stage primaryStage = new Stage();
                scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
                primaryStage.setTitle("Login - MySpa");
                primaryStage.setResizable(false);
                primaryStage.setScene(scene);
                primaryStage.show();
                Stage ventana = (Stage) btnCerrarSesion.getScene().getWindow();
                ventana.close();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Se ha cerrado la sesión exitosamente");
                alert.showAndWait(); 
            } catch(Exception e){
              Alert alert = new Alert(Alert.AlertType.ERROR,"Error al establecer conexión, Intentalo de nuevo");
              alert.showAndWait();  
              e.printStackTrace();
            }
    }
    
}
