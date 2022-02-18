package org.utl.myspa.gui;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import org.utl.myspa.core.Horario;
import com.jfoenix.controls.JFXTextField;
import java.util.ArrayList;
import java.util.List;
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

public class ModuloHorarioController implements Initializable {
    ObservableList listaHoraInicio = FXCollections.observableArrayList();
    ObservableList listaHoraFin = FXCollections.observableArrayList();
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
    @FXML private JFXTextField txtIdHorario;
    @FXML private ComboBox<String> cbbxHoraInicio;
    @FXML private ComboBox<String> cbbxHoraFin;
    @FXML private TableView<Horario> tblHorarios;
    @FXML private TableColumn<?, ?> colIdHorario;
    @FXML private TableColumn<Horario, String> colHoraInicio;
    @FXML private TableColumn<Horario, String> colHoraTermino;
    //Definición de estructura de datos para guardar los registros de horario
    ObservableList<Horario> listaHorarios;
    Horario horarioSeleccionado;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarListasInicio();
        cargarListasFin();
        Client client = ClientBuilder.newClient();
        //Inicializamos nuestra estructura de datos para poder cargar elementos o items
        listaHorarios = FXCollections.observableArrayList();
        
        //Se mapea las columnas de la tabla para que se muestren las propiedades del objeto
        this.colIdHorario.setCellValueFactory(new PropertyValueFactory("id"));
        this.colHoraInicio.setCellValueFactory(new PropertyValueFactory("horaInicio"));
        this.colHoraTermino.setCellValueFactory(new PropertyValueFactory("horaFin"));
        cargarTablaHorarios();
        client.close();
    }
    
    public void cargarTablaHorarios(){
        try {
            Client client = ClientBuilder.newClient();
            String response = client.target("http://localhost:8084/MySpa/api/horario/getAll")
                    .request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(String.class);
            Gson gson = new Gson();
            ArrayList<Horario> lista = gson.fromJson(response, new TypeToken<List<Horario>>(){}.getType());
            listaHorarios = FXCollections.observableArrayList(lista);
            tblHorarios.setItems(listaHorarios);
            client.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error al cargar el catalogo");
            alert.showAndWait();  
            e.printStackTrace();
        }
    }
    
    public void cargarListasInicio(){
        listaHoraInicio.removeAll(listaHoraInicio);
        String horaI1 = "09:00:00";
        String horaI2 = "09:30:00";
        String horaI3 = "10:00:00";
        String horaI4 = "10:30:00";
        String horaI5 = "11:00:00";
        String horaI6 = "11:30:00";
        String horaI7 = "12:00:00";
        String horaI8 = "12:30:00";
        String horaI9 = "13:00:00";
        String horaI10 = "13:30:00";
        String horaI11 = "14:00:00";
        String horaI12 = "14:30:00";
        String horaI13 = "15:00:00";
        String horaI14 = "15:30:00";
        String horaI15 = "16:00:00";
        String horaI16 = "16:30:00";
        String horaI17 = "17:00:00";
        String horaI18 = "17:30:00";
        String horaI19 = "18:00:00";
        String horaI20 = "18:30:00";
        String horaI21 = "19:00:00";
        String horaI22 = "19:30:00";
        String horaI23 = "20:00:00";
        String horaI24 = "20:30:00";
        listaHoraInicio.addAll(horaI1, horaI2, horaI3, horaI4, horaI5, horaI6, horaI7, horaI8, horaI9, horaI10,
                horaI11, horaI12, horaI13, horaI14, horaI15, horaI16, horaI17, horaI18, horaI19, horaI20,
                horaI21, horaI22, horaI23, horaI24);
        cbbxHoraInicio.getItems().addAll(listaHoraInicio);
    }
    
    public void cargarListasFin(){
        listaHoraFin.removeAll(listaHoraFin);
        String horaF1 = "09:30:00";
        String horaF2 = "10:00:00";
        String horaF3 = "10:30:00";
        String horaF4 = "11:00:00";
        String horaF5 = "11:30:00";
        String horaF6 = "12:00:00";
        String horaF7 = "12:30:00";
        String horaF8 = "13:00:00";
        String horaF9 = "13:30:00";
        String horaF10 = "14:00:00";
        String horaF11 = "14:30:00";
        String horaF12 = "15:00:00";
        String horaF13 = "15:30:00";
        String horaF14 = "16:00:00";
        String horaF15 = "16:30:00";
        String horaF16 = "17:00:00";
        String horaF17 = "17:30:00";
        String horaF18 = "18:00:00";
        String horaF19 = "18:30:00";
        String horaF20 = "19:00:00";
        String horaF21 = "19:30:00";
        String horaF22 = "20:00:00";
        String horaF23 = "20:30:00";
        String horaF24 = "21:00:00";
        listaHoraFin.addAll(horaF1, horaF2, horaF3, horaF4, horaF5, horaF6, horaF7, horaF8, horaF9, horaF10,
                horaF11, horaF12, horaF13, horaF14, horaF15, horaF16, horaF17, horaF18, horaF19, horaF20,
                horaF21, horaF22, horaF23, horaF24);
        cbbxHoraFin.getItems().addAll(listaHoraFin);
    }

    public void guardarHorario(){
        Horario h = new Horario();
        String horaInicio = cbbxHoraInicio.getValue();
        switch (horaInicio) {
            case "09:00:00":
                h.setHoraInicio("09:00:00");
                break;
            case "09:30:00":
                h.setHoraInicio("09:30:00");
                break;
            case "10:00:00":
                h.setHoraInicio("10:00:00");
                break;
            case "10:30:00":
                h.setHoraInicio("10:30:00");
                break;
            case "11:00:00":
                h.setHoraInicio("11:00:00");
                break;
            case "11:30:00":
                h.setHoraInicio("11:30:00");
                break;
            case "12:00:00":
                h.setHoraInicio("12:00:00");
                break;
            case "12:30:00":
                h.setHoraInicio("12:30:00");
                break;
            case "13:00:00":
                h.setHoraInicio("13:00:00");
                break;
            case "13:30:00":
                h.setHoraInicio("13:30:00");
                break;
            case "14:00:00":
                h.setHoraInicio("14:00:00");
                break;
            case "14:30:00":
                h.setHoraInicio("14:30:00");
                break;
            case "15:00:00":
                h.setHoraInicio("15:00:00");
                break;
            case "15:30:00":
                h.setHoraInicio("15:30:00");
                break;
            case "16:00:00":
                h.setHoraInicio("16:00:00");
                break;
            case "16:30:00":
                h.setHoraInicio("16:30:00");
                break;
            case "17:00:00":
                h.setHoraInicio("17:00:00");
                break;
            case "17:30:00":
                h.setHoraInicio("17:30:00");
                break;
            case "18:00:00":
                h.setHoraInicio("18:00:00");
                break;
            case "18:30:00":
                h.setHoraInicio("18:30:00");
                break;
            case "19:00:00":
                h.setHoraInicio("19:00:00");
                break;
            case "19:30:00":
                h.setHoraInicio("19:30:00");
                break;
            case "20:00:00":
                h.setHoraInicio("20:00:00");
                break;
            case "20:30:00":
                h.setHoraInicio("20:30:00");
                break;
            default:
                h.setHoraInicio("");
        } 
        String horaFin = cbbxHoraFin.getValue();
        switch (horaFin){
            case "09:30:00":
                h.setHoraFin("09:30:00");
                break;
            case "10:00:00":
                h.setHoraFin("10:00:00");
                break;
            case "10:30:00":
                h.setHoraFin("10:30:00");
                break;
            case "11:00:00":
                h.setHoraFin("11:00:00");
                break;
            case "11:30:00":
                h.setHoraFin("11:30:00");
                break;
            case "12:00:00":
                h.setHoraFin("12:00:00");
                break;
            case "12:30:00":
                h.setHoraFin("12:30:00");
                break;
            case "13:00:00":
                h.setHoraFin("13:00:00");
                break;
            case "13:30:00":
                h.setHoraFin("13:30:00");
                break;
            case "14:00:00":
                h.setHoraFin("14:00:00");
                break;
            case "14:30:00":
                h.setHoraFin("14:30:00");
                break;
            case "15:30:00":
                h.setHoraFin("15:00:00");
                break;
            case "16:00:00":
                h.setHoraFin("16:00:00");
                break;
            case "16:30:00":
                h.setHoraFin("16:30:00");
                break;
            case "17:00:00":
                h.setHoraFin("17:00:00");
                break;
            case "17:30:00":
                h.setHoraFin("17:30:00");
                break;
            case "18:00:00":
                h.setHoraFin("18:00:00");
                break;
            case "18:30:00":
                h.setHoraFin("18:30:00");
                break;
            case "19:00:00":
                h.setHoraFin("19:00:00");
                break;
            case "19:30:00":
                h.setHoraFin("19:30:00");
                break;
            case "20:00:00":
                h.setHoraFin("20:00:00");
                break;
            case "20:30:00":
                h.setHoraFin("20:30:00");
                break;
            case "21:00:00":
                h.setHoraFin("21:00:00");
                break;
            default:
                h.setHoraFin("");
        }
        if (Integer.parseInt(txtIdHorario.getText()) == 0) {
            h.setId(listaHorarios.size() + 1);
        }else{
            h.setId(Integer.parseInt(txtIdHorario.getText()));
        }
        try {
            Client client = ClientBuilder.newClient();
            Gson gson = new Gson();
            String objHorario = gson.toJson(h);
            if (Integer.parseInt(txtIdHorario.getText()) == 0) {
                WebTarget target = client.target("http://localhost:8084/MySpa/api/horario")
                                    .path("/insert")
                                    .queryParam("h", "{h}")
                                    .resolveTemplate("h", objHorario);
                Invocation.Builder invoBuil = target.request(MediaType.APPLICATION_JSON);
                Response response = invoBuil.get();
                response.close();
                limpiar();
            }else{
                WebTarget target = client.target("http://localhost:8084/MySpa/api/horario")
                                    .path("/update")
                                    .queryParam("h", "{h}")
                                    .resolveTemplate("h", objHorario);
                Invocation.Builder invoBuil = target.request(MediaType.APPLICATION_JSON);
                Response response = invoBuil.get();
                response.close();
            }
            client.close();
            cargarTablaHorarios();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al guardar los datos");
            alert.showAndWait();
            e.printStackTrace();
        }
    }
    
    public void buscarHorario(){
        limpiar();
        seleccionHorario();
    }
    
    public void seleccionHorario(){
        horarioSeleccionado = tblHorarios.getSelectionModel().getSelectedItem();
        int id = 0;
        if (horarioSeleccionado != null) {
            txtIdBusqueda.setText("");
            id = horarioSeleccionado.getId();
        }else{
            id = Integer.parseInt(txtIdBusqueda.getText());
        }
        try {
            Client client = ClientBuilder.newClient();
            String response = client.target("http://localhost:8084/MySpa/api/horario/search?filter=" + id)
                    .request(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(String.class);
            Gson gson = new Gson();
            Horario objHorario = gson.fromJson(response, Horario.class);
            if (objHorario.getId() != 0) {
                txtIdHorario.setText(String.valueOf(objHorario.getId()));
                switch (objHorario.getHoraInicio()){
                    case "09:00:00":
                        cbbxHoraInicio.getSelectionModel().select("09:00:00");
                        break;
                    case "09:30:00":
                        cbbxHoraInicio.getSelectionModel().select("09:30:00");
                        break;
                    case "10:00:00":
                        cbbxHoraInicio.getSelectionModel().select("10:00:00");
                        break;
                    case "10:30:00":
                        cbbxHoraInicio.getSelectionModel().select("10:30:00");
                        break;
                    case "11:00:00":
                        cbbxHoraInicio.getSelectionModel().select("11:00:00");
                        break;
                    case "11:30:00":
                        cbbxHoraInicio.getSelectionModel().select("11:30:00");
                        break;
                    case "12:00:00":
                        cbbxHoraInicio.getSelectionModel().select("12:00:00");
                        break;
                    case "12:30:00":
                        cbbxHoraInicio.getSelectionModel().select("12:30:00");
                        break;
                    case "13:00:00":
                        cbbxHoraInicio.getSelectionModel().select("13:00:00");
                        break;
                    case "13:30:00":
                        cbbxHoraInicio.getSelectionModel().select("13:30:00");
                        break;
                    case "14:00:00":
                        cbbxHoraInicio.getSelectionModel().select("14:00:00");
                        break;
                    case "14:30:00":
                        cbbxHoraInicio.getSelectionModel().select("14:30:00");
                        break;
                    case "15:00:00":
                        cbbxHoraInicio.getSelectionModel().select("15:00:00");
                        break;
                    case "15:30:00":
                        cbbxHoraInicio.getSelectionModel().select("15:30:00");
                        break;
                    case "16:00:00":
                        cbbxHoraInicio.getSelectionModel().select("16:00:00");
                        break;
                    case "16:30:00":
                        cbbxHoraInicio.getSelectionModel().select("16:30:00");
                        break;
                    case "17:00:00":
                        cbbxHoraInicio.getSelectionModel().select("17:00:00");
                        break;
                    case "17:30:00":
                        cbbxHoraInicio.getSelectionModel().select("17:30:00");
                        break;
                    case "18:00:00":
                        cbbxHoraInicio.getSelectionModel().select("18:00:00");
                        break;
                    case "18:30:00":
                        cbbxHoraInicio.getSelectionModel().select("18:30:00");
                        break;
                    case "19:00:00":
                        cbbxHoraInicio.getSelectionModel().select("19:00:00");
                        break;
                    case "19:30:00":
                        cbbxHoraInicio.getSelectionModel().select("19:30:00");
                        break;
                    case "20:00:00":
                        cbbxHoraInicio.getSelectionModel().select("20:00:00");
                        break;
                    case "20:30:00":
                        cbbxHoraInicio.getSelectionModel().select("20:30:00");
                        break;
                    default:
                        cbbxHoraInicio.getSelectionModel().select("");
                }
                switch (objHorario.getHoraFin()){
                    case "09:30:00":
                        cbbxHoraFin.getSelectionModel().select("09:30:00");
                        break;
                    case "10:00:00":
                        cbbxHoraFin.getSelectionModel().select("10:00:00");
                        break;
                    case "10:30:00":
                        cbbxHoraFin.getSelectionModel().select("10:30:00");
                        break;
                    case "11:00:00":
                        cbbxHoraFin.getSelectionModel().select("11:00:00");
                        break;
                    case "11:30:00":
                        cbbxHoraFin.getSelectionModel().select("11:30:00");
                        break;
                    case "12:00:00":
                        cbbxHoraFin.getSelectionModel().select("12:00:00");
                        break;
                    case "12:30:00":
                        cbbxHoraFin.getSelectionModel().select("12:30:00");
                        break;
                    case "13:00:00":
                        cbbxHoraFin.getSelectionModel().select("13:00:00");
                        break;
                    case "13:30:00":
                        cbbxHoraFin.getSelectionModel().select("13:30:00");
                        break;
                    case "14:00:00":
                        cbbxHoraFin.getSelectionModel().select("14:00:00");
                        break;
                    case "14:30:00":
                        cbbxHoraFin.getSelectionModel().select("14:30:00");
                        break;
                    case "15:00:00":
                        cbbxHoraFin.getSelectionModel().select("15:00:00");
                        break;
                    case "15:30:00":
                        cbbxHoraFin.getSelectionModel().select("15:30:00");
                        break;
                    case "16:00:00":
                        cbbxHoraFin.getSelectionModel().select("16:00:00");
                        break;
                    case "16:30:00":
                        cbbxHoraFin.getSelectionModel().select("16:30:00");
                        break;
                    case "17:00:00":
                        cbbxHoraFin.getSelectionModel().select("17:00:00");
                        break;
                    case "17:30:00":
                        cbbxHoraFin.getSelectionModel().select("17:30:00");
                        break;
                    case "18:00:00":
                        cbbxHoraFin.getSelectionModel().select("18:00:00");
                        break;
                    case "18:30:00":
                        cbbxHoraFin.getSelectionModel().select("18:30:00");
                        break;
                    case "19:00:00":
                        cbbxHoraFin.getSelectionModel().select("19:00:00");
                        break;
                    case "19:30:00":
                        cbbxHoraFin.getSelectionModel().select("19:30:00");
                        break;
                    case "20:00:00":
                        cbbxHoraFin.getSelectionModel().select("20:00:00");
                        break;
                    case "20:30:00":
                        cbbxHoraFin.getSelectionModel().select("20:30:00");
                        break;
                    case "21:00:00":
                        cbbxHoraFin.getSelectionModel().select("21:00:00");
                        break;
                    default:cbbxHoraFin.getSelectionModel().select("");
                }
            }else{
                txtIdHorario.setText("null");
                cbbxHoraInicio.getSelectionModel().select("");
                cbbxHoraFin.getSelectionModel().select("");
            }
            client.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al buscar los datos");
            alert.showAndWait();
            e.printStackTrace();
        } 
    }
    
    public void eliminarHorario(){
        try {
            Client client = ClientBuilder.newClient();
            int id = Integer.parseInt(txtIdHorario.getText());
            client.target("http://localhost:8084/MySpa/api/horario/delete")
                        .queryParam("id", id)
                        .request()
                        .get();
            limpiar();
            client.close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error al eliminar del catalogo");
            alert.showAndWait();  
            e.printStackTrace();
        }
        cargarTablaHorarios(); 
    }

    public void limpiar(){
        txtIdHorario.setText("0");
        cbbxHoraInicio.getSelectionModel().select("");
        cbbxHoraFin.getSelectionModel().select("");
        cargarTablaHorarios(); 
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

