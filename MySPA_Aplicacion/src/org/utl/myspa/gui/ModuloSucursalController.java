package org.utl.myspa.gui;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import org.utl.myspa.core.Sucursal;
import com.jfoenix.controls.JFXTextField;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ModuloSucursalController implements Initializable {
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
    @FXML private Button btnGuardar;
    @FXML private Button btnBuscar;
    @FXML private Button btnEliminar;
    @FXML private Button btnGenerarReporte;
    @FXML private Button btnLimpiar;
    @FXML private JFXTextField txtIdBusqueda;
    @FXML private JFXTextField txtIdSucursal;
    @FXML private JFXTextField txtNombre;
    @FXML private JFXTextField txtDomicilio;
    @FXML private JFXTextField txtLongitud;
    @FXML private JFXTextField txtLatitud;
    @FXML private RadioButton rbtnEstatusInactivo;
    @FXML private ToggleGroup rbtnEstatus;
    @FXML private RadioButton rbtnEstatusActivo;
    @FXML private TableView<Sucursal> tblSucursales;
    @FXML private TableColumn<?, ?> colIdSucursal;
    @FXML private TableColumn<Sucursal, String> colNombre;
    @FXML private TableColumn<Sucursal, String> colDomicilio;
    @FXML private TableColumn<Sucursal, String> colLatitud;
    @FXML private TableColumn<Sucursal, String> colLongitud;
    @FXML private TableColumn<Sucursal, String> colEstatus;
    //Definición de estructura de datos para guardar los registros de sucursal
    ObservableList<Sucursal> listaSucursales;
    Sucursal sucursalSeleccionada;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Client client = ClientBuilder.newClient();
        //Inicializamos nuestra estructura de datos para poder cargar elementos o items
        listaSucursales = FXCollections.observableArrayList();
        
        //Se mapea las columnas de la tabla para que se muestren las propiedades del objeto
        this.colIdSucursal.setCellValueFactory(new PropertyValueFactory("id"));
        this.colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colDomicilio.setCellValueFactory(new PropertyValueFactory("domicilio"));
        this.colLatitud.setCellValueFactory(new PropertyValueFactory("latitud"));
        this.colLongitud.setCellValueFactory(new PropertyValueFactory("longitud"));
        this.colEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
        cargarTablaSucursales();
        client.close();
    }
    
    public void cargarTablaSucursales(){
        try {
            Client client = ClientBuilder.newClient();
            String response = client.target("http://localhost:8084/MySpa/api/sucursal/getAll")
                    .request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(String.class);
            Gson gson = new Gson();
            ArrayList<Sucursal> lista = gson.fromJson(response, new TypeToken<List<Sucursal>>(){}.getType());
            listaSucursales = FXCollections.observableArrayList(lista);
            tblSucursales.setItems(listaSucursales);
            client.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error al cargar el catalogo");
            alert.showAndWait();  
            e.printStackTrace();
        }
    }

    public void guardarSucursal(){
        Sucursal sucursal = new Sucursal();
        sucursal.setNombre(txtNombre.getText());
        sucursal.setDomicilio(txtDomicilio.getText());
        sucursal.setLatitud(Double.parseDouble(txtLatitud.getText()));
        sucursal.setLongitud(Double.parseDouble(txtLongitud.getText()));
        if (rbtnEstatusActivo.isSelected()) {
            sucursal.setEstatus(1);
        } else {
            if (rbtnEstatusInactivo.isSelected()) {
                sucursal.setEstatus(0);
            }
        }
        if (Integer.parseInt(txtIdSucursal.getText()) == 0) {
            sucursal.setId(listaSucursales.size() + 1);
        }else{
            sucursal.setId(Integer.parseInt(txtIdSucursal.getText()));
        }
        try {
            Client client = ClientBuilder.newClient();
            Gson gson = new Gson();
            String objSucursal = gson.toJson(sucursal);
            if (Integer.parseInt(txtIdSucursal.getText()) == 0) {
                WebTarget target = client.target("http://localhost:8084/MySpa/api/sucursal")
                                    .path("/insert")
                                    .queryParam("s", "{s}")
                                    .resolveTemplate("s", objSucursal);
                Invocation.Builder invoBuil = target.request(MediaType.APPLICATION_JSON);
                Response response = invoBuil.get();
                response.close();
                limpiar();
            }else{
                WebTarget target = client.target("http://localhost:8084/MySpa/api/sucursal")
                                    .path("/update")
                                    .queryParam("s", "{s}")
                                    .resolveTemplate("s", objSucursal);
                Invocation.Builder invoBuil = target.request(MediaType.APPLICATION_JSON);
                Response response = invoBuil.get();
                response.close();
            }
            client.close();
            cargarTablaSucursales();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al guardar los datos");
            alert.showAndWait();
            e.printStackTrace();
        }
    }
    
    public void buscarSucursal(){
        limpiar();
        seleccionSucursal();
    }
    
    public void seleccionSucursal(){
        sucursalSeleccionada = tblSucursales.getSelectionModel().getSelectedItem();
        int id = 0;
        if (sucursalSeleccionada != null) {
            txtIdBusqueda.setText("");
            id = sucursalSeleccionada.getId();
        }else{
            id = Integer.parseInt(txtIdBusqueda.getText());
        }
        try {
            Client client = ClientBuilder.newClient();
            String response = client.target("http://localhost:8084/MySpa/api/sucursal/search?filter=" + id)
                    .request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(String.class);
            Gson gson = new Gson();
            Sucursal objSucursal = gson.fromJson(response, Sucursal.class);
            if (objSucursal.getId() != 0) {
                txtIdSucursal.setText(String.valueOf(objSucursal.getId()));
                txtNombre.setText(String.valueOf(objSucursal.getNombre()));
                txtDomicilio.setText(String.valueOf(objSucursal.getDomicilio()));
                txtLatitud.setText(String.valueOf(objSucursal.getLatitud()));
                txtLongitud.setText(String.valueOf(objSucursal.getLongitud()));
                switch (objSucursal.getEstatus()) {
                    case 1:
                        rbtnEstatusActivo.setSelected(true);
                        rbtnEstatusInactivo.setSelected(false);
                        break;
                    case 0:
                        rbtnEstatusInactivo.setSelected(true);
                        rbtnEstatusActivo.setSelected(false);
                        break;
                    default:
                        rbtnEstatusActivo.setSelected(false);
                        rbtnEstatusInactivo.setSelected(false);
                }
            }else{
                txtIdSucursal.setText("null");
                txtNombre.setText("null");
                txtDomicilio.setText("null");
                txtLatitud.setText("null");
                txtLongitud.setText("null");
                rbtnEstatusInactivo.setSelected(true);
                rbtnEstatusActivo.setSelected(false);
            }
            client.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al buscar los datos");
            alert.showAndWait();
            e.printStackTrace();
        }
    }
    
    public void eliminarSucursal(){
        try {
            Client client = ClientBuilder.newClient();
            int id = Integer.parseInt(txtIdSucursal.getText());
            client.target("http://localhost:8084/MySpa/api/sucursal/delete")
                        .queryParam("id", id)
                        .request()
                        .get();
            rbtnEstatusActivo.setSelected(false);
            rbtnEstatusInactivo.setSelected(true);
            client.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error al eliminar del catalogo");
            alert.showAndWait();  
            e.printStackTrace();
        }
        cargarTablaSucursales();
    }

    public void limpiar(){
        txtIdSucursal.setText("0");
        txtNombre.setText("");
        txtDomicilio.setText("");
        txtLatitud.setText("");
        txtLongitud.setText("");
        rbtnEstatusActivo.setSelected(false);
        rbtnEstatusInactivo.setSelected(false);
        cargarTablaSucursales();
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
