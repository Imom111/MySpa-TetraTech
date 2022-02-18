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
import org.utl.myspa.core.Cliente;
import com.jfoenix.controls.JFXTextField;
import java.sql.Timestamp;
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

public class ModuloClienteController implements Initializable {
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
    @FXML private JFXTextField txtIdCliente;
    @FXML private JFXTextField txtIdPersona;
    @FXML private JFXTextField txtIdUsuario;
    @FXML private JFXTextField txtNombre;
    @FXML private JFXTextField txtApePaterno;
    @FXML private JFXTextField txtApeMaterno;
    @FXML private JFXTextField txtTelefono;
    @FXML private JFXTextField txtRfc;
    @FXML private JFXTextField txtDomicilio;
    @FXML private JFXTextField txtNumUnico;
    @FXML private JFXTextField txtEstatus;
    // @FXML private JFXTextField txtRutaFoto;
    // @FXML private JFXTextField txtFoto;
    @FXML private JFXTextField txtUsuario;
    @FXML private JFXPasswordField txtPassword;
    @FXML private JFXTextField txtRol;
    // @FXML private JFXTextField txtToken;
    @FXML private JFXTextField txtCorreo;
    @FXML private RadioButton rbtnEstatusInactivo;
    @FXML private ToggleGroup rbtnEstatus;
    @FXML private RadioButton rbtnEstatusActivo;
    @FXML private ComboBox<String> cbbxGenero;
    @FXML private TableView<Cliente> tblClientes;
    @FXML private TableColumn<?, ?> colIdCliente;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colGenero;
    @FXML private TableColumn<Cliente, String> colRfc;
    @FXML private TableColumn<Cliente, String> colDireccion;
    @FXML private TableColumn<Cliente, String> colTelefono;
    @FXML private TableColumn<?, ?> colEstatus;
    @FXML private TableColumn<?, ?> colCorreo;
    
    //Definición de estructura de datos para guardar los registros de cliente
    ObservableList<Cliente> listaClientes;
    Cliente clienteSeleccionado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        cargarListas();
        Client client = ClientBuilder.newClient();
        listaClientes = FXCollections.observableArrayList();
        
        //Se mapea las columnas de la tabla para que se muestren las propiedades del objeto
        this.colIdCliente.setCellValueFactory(new PropertyValueFactory("id"));
        this.colNombre.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPersona().getNombre()+" "+
            param.getValue().getPersona().getApellidoP()+" "+ param.getValue().getPersona().getApellidoM()));
        this.colGenero.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPersona().getGenero()));
        this.colRfc.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPersona().getRfc()));
        this.colDireccion.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPersona().getRfc()));
        this.colTelefono.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getPersona().getTelefono()));
        this.colEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
        this.colCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        client.close();
        cargarTablaClientes();
    }
    
    public void cargarTablaClientes(){
        try {
            Client client = ClientBuilder.newClient();
            String response = client.target("http://localhost:8084/MySpa/api/cliente/getAll")
                    .request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(String.class);
            Gson gson = new Gson();
            ArrayList<Cliente> lista = gson.fromJson(response, new TypeToken<List<Cliente>>(){}.getType());
            listaClientes = FXCollections.observableArrayList(lista);
            tblClientes.setItems(listaClientes);
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
    
    public void guardarCliente(){
        Cliente cliente = new Cliente();
        Persona persona = new Persona();
        Usuario usuario = new Usuario();
        cliente.setNumUnico("");
        cliente.setCorreo(txtCorreo.getText());
        if (rbtnEstatusActivo.isSelected()) {
            cliente.setEstatus(1);
        }else{
            if (rbtnEstatusInactivo.isSelected()) {
                cliente.setEstatus(0);
            }
        }
        // cliente.setFoto(txtFoto.getText());
        cliente.setFoto("");
        // cliente.setRutaFoto(txtRutaFoto.getText());
        cliente.setRutaFoto("");
        
        Long datetime = System.currentTimeMillis();
        Timestamp timestampCompleto = new Timestamp(datetime);
        String timestamp = "" + timestampCompleto;
        timestamp = timestamp.substring(0,18);
        timestamp = timestamp.replace("-","");
        timestamp = timestamp.replace(" ","");
        timestamp = timestamp.replace(":","");
        timestamp = timestamp.replace(".","");
        cliente.setNumUnico("C" + timestamp);
        
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
            if (txtIdCliente.getText() == "0") {
                cliente.setId(0);
                persona.setId(0);
                usuario.setId(0);
            }else{
                cliente.setId(Integer.parseInt(txtIdCliente.getText()));
                persona.setId(Integer.parseInt(txtIdPersona.getText()));
                usuario.setId(Integer.parseInt(txtIdUsuario.getText()));
            }
            cliente.setPersona(persona);
            cliente.setUsuario(usuario);
            Gson gson = new Gson();
            String objCliente = gson.toJson(cliente);
            Form form = new Form().param("cliente", objCliente+"");
            if (cliente.getId() == 0) {
                Response response = client.target("http://localhost:8084/MySpa/api/cliente/insert")
                                    .request()
                                    .post(Entity.form(form));
                response.close();
                limpiar();
            }else{
                Response response = client.target("http://localhost:8084/MySpa/api/cliente/update")
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
        cargarTablaClientes();
    }
    
    public void buscarCliente(){
        limpiar();
        seleccionCliente();
    }
    
    public void seleccionCliente(){
        clienteSeleccionado = tblClientes.getSelectionModel().getSelectedItem();
        int id = 0;
        if (clienteSeleccionado != null) {
            txtIdBusqueda.setText("");
            id = clienteSeleccionado.getId();
        }else{
            id = Integer.parseInt(txtIdBusqueda.getText());
        }
        try {
            Client client = ClientBuilder.newClient();
            String response = client.target("http://localhost:8084/MySpa/api/cliente/search?filter=" + id)
                    .request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(String.class);
            Gson gson = new Gson();
            Cliente objCliente = gson.fromJson(response, Cliente.class);
            if (objCliente.getId() != 0) {
                txtIdCliente.setText(String.valueOf(objCliente.getId()));
                switch (objCliente.getEstatus()) {
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
                // txtRutaFoto.setText(String.valueOf(Cliente.getRutaFoto()));
                // txtFoto.setText(String.valueOf(Cliente.getFoto()));
                txtNumUnico.setText(String.valueOf(objCliente.getNumUnico()));
                txtCorreo.setText(String.valueOf(objCliente.getCorreo()));
                txtIdPersona.setText(String.valueOf(objCliente.getPersona().getId()));
                txtNombre.setText(String.valueOf(objCliente.getPersona().getNombre()));
                txtApePaterno.setText(String.valueOf(objCliente.getPersona().getApellidoP()));
                txtApeMaterno.setText(String.valueOf(objCliente.getPersona().getApellidoM()));
                switch (objCliente.getPersona().getGenero()) {
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
                txtRfc.setText(String.valueOf(objCliente.getPersona().getRfc()));
                txtDomicilio.setText(String.valueOf(objCliente.getPersona().getDomicilio()));
                txtTelefono.setText(String.valueOf(objCliente.getPersona().getTelefono()));

                txtIdUsuario.setText(String.valueOf(objCliente.getUsuario().getId()));
                txtUsuario.setText(String.valueOf(objCliente.getUsuario().getNombreUsu()));
                txtPassword.setText(String.valueOf(objCliente.getUsuario().getContrasenia()));
                txtRol.setText(String.valueOf(objCliente.getUsuario().getRol()));
                // txtToken.setText(String.valueOf(Cliente.getUsuario().getToken()));
            }else{
                txtIdCliente.setText("null");
                rbtnEstatusInactivo.setSelected(true);
                rbtnEstatusActivo.setSelected(false);
                // txtRutaFoto.setText("null");
                // txtFoto.setText(String.valueOf("null");
                txtNumUnico.setText("null");
                txtCorreo.setText("null");
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
    
    public void eliminarCliente(){
        try {
            Client client = ClientBuilder.newClient();
            int id = Integer.parseInt(txtIdCliente.getText());
            client.target("http://localhost:8084/MySpa/api/cliente/delete")
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
        cargarTablaClientes();
    }
    
    public void limpiar(){
        txtIdCliente.setText("0");
        txtIdPersona.setText("0");
        txtIdUsuario.setText("0");
        txtNombre.setText("");
        txtApePaterno.setText("");
        txtApeMaterno.setText("");
        cbbxGenero.getSelectionModel().select("");
        txtCorreo.setText("");
        txtRfc.setText("");
        txtDomicilio.setText("");
        txtTelefono.setText("");
        rbtnEstatusActivo.setSelected(false);
        rbtnEstatusInactivo.setSelected(false);
        txtNumUnico.setText("");
        // txtRutaFoto.setText("");
        // txtFoto.setText("");
        txtUsuario.setText("");
        txtPassword.setText("");
        txtCorreo.setText("");
        txtRol.setText("");
        // txtToken.setText("");
        cargarTablaClientes();
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
