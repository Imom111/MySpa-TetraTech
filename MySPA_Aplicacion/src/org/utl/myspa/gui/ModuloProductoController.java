package org.utl.myspa.gui;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import org.utl.myspa.core.Producto;
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

public class ModuloProductoController implements Initializable {
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
    @FXML private JFXTextField txtIdProducto;
    @FXML private JFXTextField txtNombre;
    @FXML private JFXTextField txtMarca;
    @FXML private JFXTextField txtPrecioUso;
    @FXML private RadioButton rbtnEstatusInactivo;
    @FXML private ToggleGroup rbtnEstatus;
    @FXML private RadioButton rbtnEstatusActivo;
    @FXML private TableView<Producto> tblProductos;
    @FXML private TableColumn<?, ?> colIdProducto;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, String> colMarca;
    @FXML private TableColumn<Producto, String> colPrecioUso;
    @FXML private TableColumn<Producto, String> colEstatus;
    //Definición de estructura de datos para guardar los registros de producto
    ObservableList<Producto> listaProductos;
    Producto productoSeleccionado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Client client = ClientBuilder.newClient();
        //Inicializamos nuestra estructura de datos para poder cargar elementos o items
        listaProductos = FXCollections.observableArrayList();
        
        //Se mapea las columnas de la tabla para que se muestren las propiedades del objeto
        this.colIdProducto.setCellValueFactory(new PropertyValueFactory("id"));
        this.colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colMarca.setCellValueFactory(new PropertyValueFactory("marca"));
        this.colPrecioUso.setCellValueFactory(new PropertyValueFactory("precioUso"));
        this.colEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
        cargarTablaProductos();
        client.close();
    }
    
    public void cargarTablaProductos(){
        try {
            Client client = ClientBuilder.newClient();
            String response = client.target("http://localhost:8084/MySpa/api/producto/getAll")
                    .request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(String.class);
            Gson gson = new Gson();
            ArrayList<Producto> lista = gson.fromJson(response, new TypeToken<List<Producto>>(){}.getType());
            listaProductos = FXCollections.observableArrayList(lista);
            tblProductos.setItems(listaProductos);
            client.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error al cargar el catalogo");
            alert.showAndWait();  
            e.printStackTrace();
        }
    }

    public void guardarProducto(){
        Producto p = new Producto();
        p.setNombre(txtNombre.getText());
        p.setMarca(txtMarca.getText());
        p.setPrecioUso(Float.parseFloat(txtPrecioUso.getText()));
        if (rbtnEstatusActivo.isSelected()) {
            p.setEstatus(1);
        } else {
            if (rbtnEstatusInactivo.isSelected()) {
                p.setEstatus(0);
            }
        }
        if (Integer.parseInt(txtIdProducto.getText()) == 0) {
            p.setId(listaProductos.size() + 1);
        }else{
            p.setId(Integer.parseInt(txtIdProducto.getText()));
        }
        try {
            Client client = ClientBuilder.newClient();
            Gson gson = new Gson();
            String objProducto = gson.toJson(p);
            if (Integer.parseInt(txtIdProducto.getText()) == 0) {
                WebTarget target = client.target("http://localhost:8084/MySpa/api/producto")
                                    .path("/insert")
                                    .queryParam("p", "{p}")
                                    .resolveTemplate("p", objProducto);
                Invocation.Builder invoBuil = target.request(MediaType.APPLICATION_JSON);
                Response response = invoBuil.get();
                response.close();
                limpiar();
            }else{
                WebTarget target = client.target("http://localhost:8084/MySpa/api/producto")
                                    .path("/update")
                                    .queryParam("p", "{p}")
                                    .resolveTemplate("p", objProducto);
                Invocation.Builder invoBuil = target.request(MediaType.APPLICATION_JSON);
                Response response = invoBuil.get();
                response.close();
            }
            client.close();
            cargarTablaProductos();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al guardar los datos");
            alert.showAndWait();
            e.printStackTrace();
        }
    }
    
    public void buscarProducto(){
        limpiar();
        seleccionProducto();
    }
    
    public void seleccionProducto(){
        productoSeleccionado = tblProductos.getSelectionModel().getSelectedItem();
        int id = 0;
        if (productoSeleccionado != null) {
            txtIdBusqueda.setText("");
            id = productoSeleccionado.getId();
        }else{
            id = Integer.parseInt(txtIdBusqueda.getText());
        }
        try {
            Client client = ClientBuilder.newClient();
            String response = client.target("http://localhost:8084/MySpa/api/producto/search?filter=" + id)
                    .request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(String.class);
            Gson gson = new Gson();
            Producto objProducto = gson.fromJson(response, Producto.class);
            if (objProducto.getId() != 0) {
                txtIdProducto.setText(String.valueOf(objProducto.getId()));
                txtNombre.setText(String.valueOf(objProducto.getNombre()));
                txtMarca.setText(String.valueOf(objProducto.getMarca()));
                txtPrecioUso.setText(String.valueOf(objProducto.getPrecioUso()));
                switch (objProducto.getEstatus()) {
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
                txtIdProducto.setText("null");
                txtNombre.setText("null");
                txtMarca.setText("null");
                txtPrecioUso.setText("null");
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
    
    public void eliminarProducto(){
        try {
            Client client = ClientBuilder.newClient();
            int id = Integer.parseInt(txtIdProducto.getText());
            client.target("http://localhost:8084/MySpa/api/producto/delete")
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
        cargarTablaProductos();
    }

    public void limpiar(){
        txtIdProducto.setText("0");
        txtNombre.setText("");
        txtMarca.setText("");
        txtPrecioUso.setText("");
        rbtnEstatusActivo.setSelected(false);
        rbtnEstatusInactivo.setSelected(false);
        cargarTablaProductos();
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
