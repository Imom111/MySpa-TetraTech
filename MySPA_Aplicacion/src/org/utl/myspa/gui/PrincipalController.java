package org.utl.myspa.gui;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PrincipalController implements Initializable {

    @FXML private Button btnPerfil;
    @FXML private Button btnInicio;
    @FXML private Button btnCerrarSesion;
    @FXML private Button btnEmpleados;
    @FXML private Button btnClientes;
    @FXML private Button btnProductos;
    @FXML private Button btnSucursales;
    @FXML private Button btnHorarios;
    @FXML private Button btnReservaciones;
    @FXML private Button btnSalas;
    @FXML private Button btnServicios;

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }
    
    public void entrarSalas(){
        try {
            Parent principal = FXMLLoader.load(getClass().getResource("/org/utl/myspa/gui/fxml/ModuloSala.fxml"));
            Scene scene = new Scene(principal);
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Gestión de salas");
            primaryStage.setResizable(true);
            primaryStage.setScene(scene);
            primaryStage.show();
            Stage ventana = (Stage) btnSalas.getScene().getWindow();
            ventana.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al establecer conexión, inténtalo de nuevo");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    public void entrarReservaciones(){
        try {
            Parent principal = FXMLLoader.load(getClass().getResource("/org/utl/myspa/gui/fxml/ModuloReservacion.fxml"));
            Scene scene = new Scene(principal);
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Gestión de reservaciones");
            primaryStage.setResizable(true);
            primaryStage.setScene(scene);
            primaryStage.show();
            Stage ventana = (Stage) btnReservaciones.getScene().getWindow();
            ventana.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al establecer conexión, inténtalo de nuevo");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    public void entrarHorarios(){
        try {
            Parent principal = FXMLLoader.load(getClass().getResource("/org/utl/myspa/gui/fxml/ModuloHorario.fxml"));
            Scene scene = new Scene(principal);
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Gestión de horarios");
            primaryStage.setResizable(true);
            primaryStage.setScene(scene);
            primaryStage.show();
            Stage ventana = (Stage) btnHorarios.getScene().getWindow();
            ventana.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al establecer conexión, inténtalo de nuevo");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    public void entrarSucursales(){
        try {
            Parent principal = FXMLLoader.load(getClass().getResource("/org/utl/myspa/gui/fxml/ModuloSucursal.fxml"));
            Scene scene = new Scene(principal);
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Gestión de sucursales");
            primaryStage.setResizable(true);
            primaryStage.setScene(scene);
            primaryStage.show();
            Stage ventana = (Stage) btnSucursales.getScene().getWindow();
            ventana.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al establecer conexión, inténtalo de nuevo");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    public void entrarProductos(){
        try {
            Parent principal = FXMLLoader.load(getClass().getResource("/org/utl/myspa/gui/fxml/ModuloProducto.fxml"));
            Scene scene = new Scene(principal);
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Gestión de productos");
            primaryStage.setResizable(true);
            primaryStage.setScene(scene);
            primaryStage.show();
            Stage ventana = (Stage) btnProductos.getScene().getWindow();
            ventana.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al establecer conexión, inténtalo de nuevo");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    public void entrarClientes(){
        try {
            Parent principal = FXMLLoader.load(getClass().getResource("/org/utl/myspa/gui/fxml/ModuloCliente.fxml"));
            Scene scene = new Scene(principal);
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Gestión de clientes");
            primaryStage.setResizable(true);
            primaryStage.setScene(scene);
            primaryStage.show();
            Stage ventana = (Stage) btnClientes.getScene().getWindow();
            ventana.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al establecer conexión, inténtalo de nuevo");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    public void entrarEmpleados(){
        try {
            Parent principal = FXMLLoader.load(getClass().getResource("/org/utl/myspa/gui/fxml/ModuloEmpleado.fxml"));
            Scene scene = new Scene(principal);
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Gestión de empleados");
            primaryStage.setResizable(true);
            primaryStage.setScene(scene);
            primaryStage.show();
            Stage ventana = (Stage) btnEmpleados.getScene().getWindow();
            ventana.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al establecer conexión, inténtalo de nuevo");
            alert.showAndWait();
            e.printStackTrace();
        }
    }
    
    public void perfil(){
        try {
            Parent principal = FXMLLoader.load(getClass().getResource("/org/utl/myspa/gui/fxml/Perfil.fxml"));
            Scene scene = new Scene(principal);
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Perfil");
            primaryStage.setResizable(true);
            primaryStage.setScene(scene);
            primaryStage.show();
            Stage ventana = (Stage) btnPerfil.getScene().getWindow();
            ventana.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al establecer conexión, inténtalo de nuevo");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    public void inicio(){
        try {
            Parent principal = FXMLLoader.load(getClass().getResource("/org/utl/myspa/gui/fxml/Principal.fxml"));
            Scene scene = new Scene(principal);
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Inicio");
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
            Stage ventana = (Stage) btnInicio.getScene().getWindow();
            ventana.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al establecer conexión, inténtalo de nuevo");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    public void cerrarSesion(){
        try {
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

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al establecer conexión, inténtalo de nuevo");
            alert.showAndWait();
            e.printStackTrace();
        }
    }
}
