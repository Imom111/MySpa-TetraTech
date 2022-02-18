package org.utl.myspa.gui;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import org.utl.myspa.core.Empleado;

public class LoginController implements Initializable {
    @FXML private JFXButton btnIngresar;
    @FXML private JFXButton btnSalir;
    @FXML private JFXTextField txtUsuario;
    @FXML private JFXPasswordField txtPassword;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }  
    
    public void validarUsuario(){
        String user = txtUsuario.getText();
        String pass = txtPassword.getText();
        try {
            Client client = ClientBuilder.newClient();
            String response = client.target("http://localhost:8084/MySpa/api/log/inEApp")
                    .queryParam("nU", user)
                    .queryParam("c", pass)
                    .request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(String.class);
            Gson gson = new Gson();
            Empleado objEmpleado = new Empleado();
            objEmpleado = gson.fromJson(response, new TypeToken<Empleado>(){}.getType());
            
            if (objEmpleado == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "La contraseña o usuario no coinciden");
                alert.showAndWait();
            }else{
                 try{
                    Parent principal = FXMLLoader.load(getClass().getResource("/org/utl/myspa/gui/fxml/Principal.fxml"));
                    Scene scene = new Scene(principal);
                    Stage primaryStage = new Stage();
                    primaryStage.setTitle("Inicio");
                    primaryStage.setResizable(false);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    Stage ventana = (Stage) btnIngresar.getScene().getWindow();
                    ventana.close();
                } catch(Exception e1){
                  Alert alert = new Alert(Alert.AlertType.ERROR,"Error al establecer conexión, Intentalo de nuevo");
                  alert.showAndWait();  
                  e1.printStackTrace();
                }
            }
        } catch (Exception e2) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Hubo un error de conexíon, intentalo nuevamente o llama al administrador del sistema");
            alert.showAndWait();
            e2.printStackTrace();
        }
    }
     
     public void cerrarSesion(){
        Stage ventana = (Stage) btnSalir.getScene().getWindow();
        ventana.close();
    }
}
