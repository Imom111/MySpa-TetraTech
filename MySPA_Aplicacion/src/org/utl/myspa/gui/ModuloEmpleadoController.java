package org.utl.myspa.gui;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXPasswordField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import org.utl.myspa.core.Empleado;
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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.utl.myspa.core.Persona;
import org.utl.myspa.core.Usuario;

public class ModuloEmpleadoController implements Initializable {
    ObservableList listaGenero = FXCollections.observableArrayList();
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
    @FXML private ImageView imgFotografia;
    @FXML private JFXTextField txtIdBusqueda;
    @FXML private JFXTextField txtIdEmpleado;
    @FXML private JFXTextField txtIdPersona;
    @FXML private JFXTextField txtIdUsuario;
    @FXML private JFXTextField txtNombre;
    @FXML private JFXTextField txtApePaterno;
    @FXML private JFXTextField txtApeMaterno;
    @FXML private JFXTextField txtTelefono;
    @FXML private JFXTextField txtRfc;
    @FXML private JFXTextField txtDomicilio;
    @FXML private JFXTextField txtNumEmpleado;
    @FXML private JFXTextField txtEstatus;
    // @FXML private JFXTextField txtRutaFoto;
    // @FXML private JFXTextField txtFoto;
    @FXML private JFXTextField txtUsuario;
    @FXML private JFXPasswordField txtPassword;
    @FXML private JFXTextField txtRol;
    // @FXML private JFXTextField txtToken;
    @FXML private JFXTextField txtPuesto;
    @FXML private RadioButton rbtnEstatusInactivo;
    @FXML private ToggleGroup rbtnEstatus;
    @FXML private RadioButton rbtnEstatusActivo;
    @FXML private ComboBox<String> cbbxGenero;
    @FXML private TableView<Empleado> tblEmpleados;
    @FXML private TableColumn<?, ?> colIdEmpleado;
    @FXML private TableColumn<Empleado, String> colNombre;
    @FXML private TableColumn<Empleado, String> colGenero;
    @FXML private TableColumn<Empleado, String> colRfc;
    @FXML private TableColumn<Empleado, String> colDireccion;
    @FXML private TableColumn<Empleado, String> colTelefono;
    @FXML private TableColumn<?, ?> colEstatus;
    @FXML private TableColumn<?, ?> colPuesto;
    
    //Definición de estructura de datos para guardar los registros de empleado
    ObservableList<Empleado> listaEmpleados;
    Empleado empleadoSeleccionado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        cargarListas();
        Client client = ClientBuilder.newClient();
        //Inicializamos nuestra estructura de datos para poder cargar elementos o items
        listaEmpleados = FXCollections.observableArrayList();
        
        //Se mapea las columnas de la tabla para que se muestren las propiedades del objeto
        this.colIdEmpleado.setCellValueFactory(new PropertyValueFactory("id"));
        this.colNombre.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPersona().getNombre()+" "+
            param.getValue().getPersona().getApellidoP()+" "+ param.getValue().getPersona().getApellidoM()));
        this.colGenero.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPersona().getGenero()));
        this.colRfc.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPersona().getRfc()));
        this.colDireccion.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPersona().getRfc()));
        this.colTelefono.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPersona().getTelefono()));
        this.colEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
        this.colPuesto.setCellValueFactory(new PropertyValueFactory("puesto"));
        client.close();
        cargarTablaEmpleados();
    }
    
    public void cargarTablaEmpleados(){
        try {
            Client client = ClientBuilder.newClient();
            String response = client.target("http://localhost:8084/MySpa/api/empleado/getAll")
                    .request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(String.class);
            Gson gson = new Gson();
            ArrayList<Empleado> lista = gson.fromJson(response, new TypeToken<List<Empleado>>(){}.getType());
            listaEmpleados = FXCollections.observableArrayList(lista);
            tblEmpleados.setItems(listaEmpleados);
            client.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error al cargar el catalogo");
            alert.showAndWait();  
            e.printStackTrace();
        }
    }
    
    public void cargarListas(){
        listaGenero.removeAll(listaGenero);
        String masculino = "Masculino";
        String femenino = "Femenino";
        String otro = "Otro";
        listaGenero.addAll(masculino, femenino, otro);
        cbbxGenero.getItems().addAll(listaGenero);
    }
    
    public void guardarEmpleado(){
        Empleado empleado = new Empleado();
        Persona persona = new Persona();
        Usuario usuario = new Usuario();
        empleado.setNumEmpleado("");
        empleado.setPuesto(txtPuesto.getText());
        if (rbtnEstatusActivo.isSelected()) {
            empleado.setEstatus(1);
        }else{
            if (rbtnEstatusInactivo.isSelected()) {
                empleado.setEstatus(0);
            }
        }
        // empleado.setFoto(txtFoto.getText());
        empleado.setFoto("");
        // empleado.setRutaFoto(txtRutaFoto.getText());
        empleado.setRutaFoto("");

        persona.setNombre(txtNombre.getText());
        persona.setApellidoP(txtApePaterno.getText());
        persona.setApellidoM(txtApeMaterno.getText());
        String genero = cbbxGenero.getValue();
        switch (genero) {
            case "Masculino":
                persona.setGenero("M");
                break;
            case "Femenino":
                persona.setGenero("F");
                break;
            case "Otro":
                persona.setGenero("O");
                break;
            default:
                persona.setGenero("");
        }
        persona.setDomicilio(txtDomicilio.getText());
        persona.setTelefono(txtTelefono.getText());
        persona.setRfc(txtRfc.getText());
        usuario.setNombreUsu(txtUsuario.getText());
        usuario.setContrasenia(txtPassword.getText());
        usuario.setRol(txtRol.getText());
        // usuario.setToken(txtToken.getText());
        try {
            Client client = ClientBuilder.newClient();
            if (txtIdEmpleado.getText() == "0") {
                empleado.setId(0);
                persona.setId(0);
                usuario.setId(0);
            }else{
                empleado.setId(Integer.parseInt(txtIdEmpleado.getText()));
                persona.setId(Integer.parseInt(txtIdPersona.getText()));
                usuario.setId(Integer.parseInt(txtIdUsuario.getText()));
            }
            empleado.setPersona(persona);
            empleado.setUsuario(usuario);
            Gson gson = new Gson();
            String objEmpleado = gson.toJson(empleado);
            Form form = new Form().param("empleado", objEmpleado+"");
            if (empleado.getId() == 0) {
                Response response = client.target("http://localhost:8084/MySpa/api/empleado/insert")
                                    .request()
                                    .post(Entity.form(form));
                response.close();
                limpiar();
            }else{
                Response response = client.target("http://localhost:8084/MySpa/api/empleado/update")
                                    .request()
                                    .post(Entity.form(form));
                response.close();
            }
            client.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al guardar los datos");
            alert.showAndWait();
            e.printStackTrace();
        }
        cargarTablaEmpleados();
    }
    
    public void buscarEmpleado(){
        limpiar();
        seleccionEmpleado();
    }
    
    public void seleccionEmpleado(){
        empleadoSeleccionado = tblEmpleados.getSelectionModel().getSelectedItem();
        int id = 0;
        if (empleadoSeleccionado != null) {
            txtIdBusqueda.setText("");
            id = empleadoSeleccionado.getId();
        }else{
            id = Integer.parseInt(txtIdBusqueda.getText());
        }
        try {
            Client client = ClientBuilder.newClient();
            String response = client.target("http://localhost:8084/MySpa/api/empleado/search?filter=" + id)
                    .request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(String.class);
            Gson gson = new Gson();
            Empleado objEmpleado = gson.fromJson(response, Empleado.class);
            if (objEmpleado.getId() != 0) {
                txtIdEmpleado.setText(String.valueOf(objEmpleado.getId()));
                switch (objEmpleado.getEstatus()) {
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
                // txtRutaFoto.setText(String.valueOf(objEmpleado.getRutaFoto()));
                // txtFoto.setText(String.valueOf(objEmpleado.getFoto()));
                txtNumEmpleado.setText(String.valueOf(objEmpleado.getNumEmpleado()));
                txtPuesto.setText(String.valueOf(objEmpleado.getPuesto()));
                txtIdPersona.setText(String.valueOf(objEmpleado.getPersona().getId()));
                txtNombre.setText(String.valueOf(objEmpleado.getPersona().getNombre()));
                txtApePaterno.setText(String.valueOf(objEmpleado.getPersona().getApellidoP()));
                txtApeMaterno.setText(String.valueOf(objEmpleado.getPersona().getApellidoM()));
                switch (objEmpleado.getPersona().getGenero()) {
                    case "M":
                        cbbxGenero.getSelectionModel().select("Masculino");
                        break;
                    case "F":
                        cbbxGenero.getSelectionModel().select("Femenino");
                        break;
                    case "O":
                        cbbxGenero.getSelectionModel().select("Otro");
                        break;
                    default:
                        cbbxGenero.getSelectionModel().select("");
                }
                txtRfc.setText(String.valueOf(objEmpleado.getPersona().getRfc()));
                txtDomicilio.setText(String.valueOf(objEmpleado.getPersona().getDomicilio()));
                txtTelefono.setText(String.valueOf(objEmpleado.getPersona().getTelefono()));

                txtIdUsuario.setText(String.valueOf(objEmpleado.getUsuario().getId()));
                txtUsuario.setText(String.valueOf(objEmpleado.getUsuario().getNombreUsu()));
                txtPassword.setText(String.valueOf(objEmpleado.getUsuario().getContrasenia()));
                txtRol.setText(String.valueOf(objEmpleado.getUsuario().getRol()));
                // txtToken.setText(String.valueOf(objEmpleado.getUsuario().getToken()));
            }else{
                txtIdEmpleado.setText("null");
                rbtnEstatusInactivo.setSelected(true);
                rbtnEstatusActivo.setSelected(false);
                // txtRutaFoto.setText("null");
                // txtFoto.setText(String.valueOf("null");
                txtNumEmpleado.setText("null");
                txtPuesto.setText("null");
                txtIdPersona.setText("null");
                txtNombre.setText("null");
                txtApePaterno.setText("null");
                txtApeMaterno.setText("null");
                cbbxGenero.getSelectionModel().select("");
                txtRfc.setText("null");
                txtDomicilio.setText("null");
                txtTelefono.setText("null");
                txtIdUsuario.setText("null");
                txtUsuario.setText("null");
                txtPassword.setText("null");
                txtRol.setText("null");
                // txtToken.setText("null");
            }
            client.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al buscar los datos");
            alert.showAndWait();
            e.printStackTrace();
        }
    }
    
    public void eliminarEmpleado(){
        try {
            Client client = ClientBuilder.newClient();
            int id = Integer.parseInt(txtIdEmpleado.getText());
            client.target("http://localhost:8084/MySpa/api/empleado/delete")
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
        cargarTablaEmpleados();
    }
    
    public void limpiar(){
        txtIdEmpleado.setText("0");
        txtIdPersona.setText("0");
        txtIdUsuario.setText("0");
        txtNombre.setText("");
        txtApePaterno.setText("");
        txtApeMaterno.setText("");
        cbbxGenero.getSelectionModel().select("");
        txtPuesto.setText("");
        txtRfc.setText("");
        txtDomicilio.setText("");
        txtTelefono.setText("");
        rbtnEstatusActivo.setSelected(false);
        rbtnEstatusInactivo.setSelected(false);
        txtNumEmpleado.setText("");
        // txtRutaFoto.setText("");
        // txtFoto.setText("");
        txtUsuario.setText("");
        txtPassword.setText("");
        txtPuesto.setText("");
        txtRol.setText("");
        // txtToken.setText("");
        cargarTablaEmpleados();
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
