package org.utl.myspa.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import org.utl.myspa.core.Reservacion;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class ModuloReservacionController implements Initializable {
    @FXML private Button btnEmpleados;
    @FXML private Button btnClientes;
    @FXML private Button btnProductos;
    @FXML private Button btnSucursales;
    @FXML private Button btnHorarios;
    @FXML private Button btnReservaciones;
    @FXML private Button btnSalas;
    @FXML private Button btnPerfil;
    @FXML private Button btnCerrarSesion;
    @FXML private Button btnInicio;
    @FXML private Button btnRegistrar;
    @FXML private Button btnBuscar;
    @FXML private Button btnModificar;
    @FXML private Button btnEliminar;
    @FXML private Button btnGenerarReporte;
    @FXML private JFXTextField txtID;
    @FXML private JFXTextField txtFecha;
    @FXML private JFXTextField txtEstatus;
    @FXML private JFXTextField txtHora;
    @FXML private Button btnLimpiar;
    @FXML private TableView<Reservacion> tblReservaciones;
    @FXML private TableColumn<?, ?> colIdReservacion;
    @FXML private TableColumn<?, ?> colFecha;
    @FXML private TableColumn<?, ?> colEstatus;
    
    //Definición de estructura de datos para guardar los registros de la reservacion
    ObservableList<Reservacion> listaReservaciones;
    Reservacion reservacionSeleccionada;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Inicializamos nuestra estructura de datos para poder cargar elementos o items
        listaReservaciones = FXCollections.observableArrayList();
        
        //Se mapea las columnas de la tabla para que se muestren las propiedades del objeto
        this.colIdReservacion.setCellValueFactory(new PropertyValueFactory("idReservacion"));
        this.colFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
        this.colEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
    }    
    
    public void guardarReservacion(){
        try {
                Reservacion reservacion = new Reservacion();
                reservacion.setIdReservacion(listaReservaciones.size()+1);
                reservacion.setFecha(txtFecha.getText());
                reservacion.setEstatus(txtEstatus.getText());
                
                txtFecha.setText("");
                txtEstatus.setText("");
                
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Datos guardados exitosamente");
                alert.showAndWait(); 
                
                listaReservaciones.add(reservacion);
                cargarTablaReservaciones();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error al guardar los datos");
            alert.showAndWait();  
            e.printStackTrace();
        }
    }
    
    public void colocarDetalle(){
        txtFecha.setText(reservacionSeleccionada.getFecha());
        txtEstatus.setText(reservacionSeleccionada.getEstatus());
    }
    
    public void cargarTablaReservaciones(){
        tblReservaciones.setItems(listaReservaciones);
        tblReservaciones.refresh();
    }
    
    public void seleccionReservacion(){
        limpiar();
        reservacionSeleccionada = tblReservaciones.getSelectionModel().getSelectedItem();
        txtID.setText(String.valueOf(reservacionSeleccionada.getIdReservacion()));
        txtFecha.setText(String.valueOf(reservacionSeleccionada.getFecha()));
        txtEstatus.setText(String.valueOf(reservacionSeleccionada.getEstatus()));
    }
    
    public void modificarReservacion(){
        try {
            reservacionSeleccionada.setFecha(txtFecha.getText());
            reservacionSeleccionada.setEstatus(txtEstatus.getText());
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Datos guardados exitosamente");
            alert.showAndWait();
            
            cargarTablaReservaciones();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error al guardar los datos");
            alert.showAndWait();  
            e.printStackTrace();
        }  
    }
    
    public void eliminarReservacion(){
        try {
            txtEstatus.setText("Inactivo");
            reservacionSeleccionada.setEstatus(txtEstatus.getText());
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Datos guardados exitosamente");
            alert.showAndWait();
            
            
            cargarTablaReservaciones();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error al guardar los datos");
            alert.showAndWait();  
            e.printStackTrace();
        }  
    }
    
    public void limpiar(){
                txtID.setText("");
                txtFecha.setText("");
                txtEstatus.setText("");
    }

    public void entrarSalas(){
        try {
            Parent principal = FXMLLoader.load(getClass().getResource("/org/utl/myspa/gui/fxml/ModuloSala.fxml"));
            Scene scene = new Scene(principal);
            Stage primaryStage = new Stage();
            scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
            primaryStage.setTitle("Login - MySpa");
            primaryStage.setResizable(false);
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
            primaryStage.setTitle("Login - MySpa");
            primaryStage.setResizable(true);
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
