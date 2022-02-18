package org.utl.myspa.gui;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import org.utl.myspa.core.Sala;
import com.jfoenix.controls.JFXTextField;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import org.utl.myspa.core.Sucursal;

public class ModuloSalaController implements Initializable {
    ObservableList listaSucursales = FXCollections.observableArrayList();
    ArrayList<Sucursal> listaALSucursal = new ArrayList<Sucursal>();
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
    @FXML private JFXTextField txtIdSala;
    @FXML private JFXTextField txtNombre;
    @FXML private JFXTextField txtDescripcion;
    @FXML private JFXTextField txtPrecioUso;
    @FXML private RadioButton rbtnEstatusInactivo;
    @FXML private ToggleGroup rbtnEstatus;
    @FXML private RadioButton rbtnEstatusActivo;
    @FXML private ComboBox<String> cbbxSucursal;
    @FXML private TableView<Sala> tblSalas;
    @FXML private TableColumn<?, ?> colIdSala;
    @FXML private TableColumn<Sala, String> colNombre;
    @FXML private TableColumn<Sala, String> colDescripcion;
    @FXML private TableColumn<Sala, String> colSucursal;
    @FXML private TableColumn<Sala, String> colEstatus;
    //Definición de estructura de datos para guardar los registros de sala
    ObservableList<Sala> listaSalas;
    Sala salaSeleccionada;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarListas();
        Client client = ClientBuilder.newClient();
        //Inicializamos nuestra estructura de datos para poder cargar elementos o items
        listaSalas = FXCollections.observableArrayList();
        
        //Se mapea las columnas de la tabla para que se muestren las propiedades del objeto
        this.colIdSala.setCellValueFactory(new PropertyValueFactory("id"));
        this.colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
        this.colSucursal.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getSucursal().getNombre()));
        this.colEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
        cargarTablaSalas();
        client.close();
    }
    
    public void cargarTablaSalas(){
        try {
            Client client = ClientBuilder.newClient();
            String response = client.target("http://localhost:8084/MySpa/api/sala/getAll")
                    .request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(String.class);
            Gson gson = new Gson();
            ArrayList<Sala> lista = gson.fromJson(response, new TypeToken<List<Sala>>(){}.getType());
            listaSalas = FXCollections.observableArrayList(lista);
            tblSalas.setItems(listaSalas);
            client.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error al cargar el catalogo");
            alert.showAndWait();  
            e.printStackTrace();
        }
    }
    
    public void cargarListas(){
        try {
            Client client = ClientBuilder.newClient();
            String response = client.target("http://localhost:8084/MySpa/api/sucursal/getAll")
                    .request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(String.class);
            Gson gson = new Gson();
            listaALSucursal = gson.fromJson(response, new TypeToken<List<Sucursal>>(){}.getType());
            listaSucursales = FXCollections.observableArrayList(listaALSucursal);
            client.close();
            ObservableList listaSucursalesOpciones = FXCollections.observableArrayList();
            for (int i = 0; i < listaALSucursal.size(); i++) {
                listaSucursalesOpciones.add(listaALSucursal.get(i).getNombre());
            }
            cbbxSucursal.getItems().addAll(listaSucursalesOpciones);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error al cargar el catalogo");
            alert.showAndWait();  
            e.printStackTrace();
        }
    }

    public void guardarSala(){
        Sala sala = new Sala();
        sala.setNombre(txtNombre.getText());
        sala.setDescripcion(txtDescripcion.getText());
        if (rbtnEstatusActivo.isSelected()) {
            sala.setEstatus(1);
        } else {
            if (rbtnEstatusInactivo.isSelected()) {
                sala.setEstatus(0);
            }
        }
        Sucursal s = new Sucursal();
        for (int i = 0; i < listaALSucursal.size(); i++) {
            if (listaALSucursal.get(i).getNombre() == cbbxSucursal.getValue()) {
                s = listaALSucursal.get(i);
            }
        }
        sala.setSucursal(s);
        if (Integer.parseInt(txtIdSala.getText()) == 0) {
            sala.setId(listaSalas.size() + 1);
        }else{
            sala.setId(Integer.parseInt(txtIdSala.getText()));
        }
        try {
            Client client = ClientBuilder.newClient();
            Gson gson = new Gson();
            String objSala = gson.toJson(sala);
            if (Integer.parseInt(txtIdSala.getText()) == 0) {
                WebTarget target = client.target("http://localhost:8084/MySpa/api/sala")
                                    .path("/insert")
                                    .queryParam("s", "{s}")
                                    .resolveTemplate("s", objSala);
                Invocation.Builder invoBuil = target.request(MediaType.APPLICATION_JSON);
                Response response = invoBuil.get();
                response.close();
                limpiar();
            }else{
                WebTarget target = client.target("http://localhost:8084/MySpa/api/sala")
                                    .path("/update")
                                    .queryParam("s", "{s}")
                                    .resolveTemplate("s", objSala);
                Invocation.Builder invoBuil = target.request(MediaType.APPLICATION_JSON);
                Response response = invoBuil.get();
                response.close();
            }
            client.close();
            cargarTablaSalas();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al guardar los datos");
            alert.showAndWait();
            e.printStackTrace();
        }
    }
    
    public void buscarSala(){
        limpiar();
        seleccionSala();
    }
    
    public void seleccionSala(){
        salaSeleccionada = tblSalas.getSelectionModel().getSelectedItem();
        int id = 0;
        if (salaSeleccionada != null) {
            txtIdBusqueda.setText("");
            id = salaSeleccionada.getId();
        }else{
            id = Integer.parseInt(txtIdBusqueda.getText());
        }
        try {
            Client client = ClientBuilder.newClient();
            String response = client.target("http://localhost:8084/MySpa/api/sala/search?filter=" + id)
                    .request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(String.class);
            Gson gson = new Gson();
            Sala objSala = gson.fromJson(response, Sala.class);
            if (objSala.getId() != 0) {
                txtIdSala.setText(String.valueOf(objSala.getId()));
                txtNombre.setText(String.valueOf(objSala.getNombre()));
                txtDescripcion.setText(String.valueOf(objSala.getDescripcion()));
                cbbxSucursal.getSelectionModel().select(objSala.getSucursal().getNombre());
                switch (objSala.getEstatus()) {
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
                txtIdSala.setText("null");
                txtNombre.setText("null");
                txtDescripcion.setText("null");
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
    
    public void eliminarSala(){
        try {
            Client client = ClientBuilder.newClient();
            int id = Integer.parseInt(txtIdSala.getText());
            client.target("http://localhost:8084/MySpa/api/sala/delete")
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
        cargarTablaSalas();
    }

    public void limpiar(){
        txtIdSala.setText("0");
        txtNombre.setText("");
        txtDescripcion.setText("");
        cbbxSucursal.getSelectionModel().select("");
        rbtnEstatusActivo.setSelected(false);
        rbtnEstatusInactivo.setSelected(false);
        cargarTablaSalas();
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
