/*
    Proyecto: Sitio web – MySpa                                                         
    Equipo desarrollador: TetraNet
    Desarrollador(es): Víctor Manuel Fonseca Muñoz
    Fecha de inicio: Lunes 19 de julio de 2021
    Versión: 7.0 (18 de febrero de 2022 - Iván Moisés Ornelas Meza)
    Descripción general del archivo JavaScript: Este archivo JavaScript es el complemento para el uso de la interfaz
    de "gestión de clientes", este archivo contiene las instrucciones para su correcto funcionamiento y gestionamiento de 
    clientes.
*/

// Listado de clientes
var clientes;
function extraerClientes() {
    clientes = null;
    var data = {"t":sessionStorage.getItem("token")};
    $.ajax({
        "url" : "api/cliente/getAll",
        "type" : "GET",
        "async" : false,
        "data" : data
    }
    ).done(function(data){
        if (data.error != null) {
            Swal.fire("Error", data.error, "warning");
        }else{
            clientes = data;
        }
    });
}

// Sección de funciones
function eliminarCliente(idEnviado) {
    var data = {"id":idEnviado,"t":sessionStorage.getItem("token")};
    $.ajax({
        "url": "api/cliente/delete",
        "type": "GET",
        "async": true,
        "data": data
    }).done(function (data) {
        if (data.error != null) {
            Swal.fire("Error", data.error, "warning");
        }else{
            Swal.fire("Ok", data.result, "success");
        }
    });
    extraerClientes();
    actualizarListadoClientes();
    resultadoBusquedaCliente();
}

function cargarModuloAgregarCliente(){
    $("#seccionGestionarCliente").show();
    $("#tablaClientes").hide();
    $("#datosTablaBusquedaClientes").hide();
    $.ajax(
        {
            "type" : "GET",
            "url" : "./modulos/agregarCliente.html",
            "async" : true
        }
    ).done(
        function(data)
        {
            var codigoMax = 0;
            for (var i = 0; i < clientes.length; i++) {
                if (clientes[i].id > codigoMax) {
                    codigoMax = clientes[i].id;
                }
            }
            $("#seccionGestionarCliente").html(data);
            $("#txtCodigoCliente").val(clientes.length+1);
        }
    );
}

function guardarCliente(){
    // Obtener estatus 1 ó 0
    var estatusCl1 = document.getElementById("txtEstatusCliente1").checked;
    var estatusCl2 = document.getElementById("txtEstatusCliente2").checked;
    if(estatusCl1){
        var estatusCl = 1;
    }else{
        if(estatusCl2){
            var estatusCl = 0;
        }
    }
    
    // Obtener RFC en mayúsculas
    var rfcEmp = $("#txtRfcCliente").val();
    rfcEmp = rfcEmp.toUpperCase();
    
    // Obtener fecha para almacenarlo en número persona
    var fecha = new Date();
    var anio = fecha.getFullYear();
    var mes = 1+(fecha.getMonth());
    if(mes < 10){
        var mesCeros = "0"+mes;
    }else{
        var mesCeros = mes;
    }
    var dia = (fecha.getDate());
    if(dia < 10){
        var diaCeros = "0"+dia;
    }else{
        var diaCeros = dia;
    }
    var hora = fecha.getHours();
    if(hora < 10){
        var horaCeros = "0"+hora;
    }else{
        var horaCeros = hora;
    }
    var minutos = fecha.getMinutes();
    if(minutos < 10){
        var minCeros = "0"+minutos;
    }else{
        var minCeros = minutos;
    }
    var segundos = fecha.getSeconds();
    if(dia < 10){
        var segCeros = "0"+segundos;
    }else{
        var segCeros = segundos;
    }
    var hoyFecha = anio+mesCeros+diaCeros+horaCeros+minCeros+segCeros;
    
    // Se concatena todo para obtener el número de cliente
    var num = "";
    num += "C";
    num += rfcEmp.charAt(0);
    num += rfcEmp.charAt(1);
    num += rfcEmp.charAt(2);
    num += rfcEmp.charAt(3);
    num += hoyFecha;
    var numeroCl = num;
    
    var usuario;
    var idUsuario = 0;
    var nombreUsuario = $("#txtUsuarioCliente").val();
    var contraseniaUsuario = $("#txtContrasenaCliente").val();
    var rolUsuario = $("#txtRolCliente").val();
    usuario = {"id": idUsuario, "nombreUsu": nombreUsuario, "contrasenia": contraseniaUsuario, "rol":rolUsuario};
    
    var persona;
    var idPersona = 0;
    var nombrePersona = $("#txtNombreCliente").val();
    var apellidoPPersona = $("#txtApellidoPCliente").val();
    var apellidoMPersona = $("#txtApellidoMCliente").val();
    var generoPersona = $("#txtGeneroCliente").val();
    var domicilioPersona = $("#txtDomicilioCliente").val();
    var telefonoPersona = $("#txtTelefonoCliente").val();
    var rfcPersona = $("#txtRfcCliente").val();
    persona = {"id": idPersona, "nombre": nombrePersona, "apellidoP": apellidoPPersona, "apellidoM": apellidoMPersona,
    "genero": generoPersona, "domicilio": domicilioPersona, "telefono": telefonoPersona, "rfc": rfcPersona};
    
    var cliente;
    var idCliente = 0;
    var numeroUnico = numeroCl.toUpperCase();
    var correoCliente = $("#txtCorreoCliente").val();
    var estatusCliente = estatusCl;
    var codigoFotoCliente = $("#txtCodigoImagenCliente").val();
    cliente = {"id": idCliente, "numUnico": numeroUnico, "correo": correoCliente,
        "estatus": estatusCliente, "foto": codigoFotoCliente, "rutaFoto": "",
        "persona": persona, "usuario": usuario};
    
    var data = {"cliente": JSON.stringify(cliente),"t":sessionStorage.getItem("token")};
    $.ajax({
        "url": "api/cliente/insert",
        "type": "POST",
        "async": true,
        "data": data
    }).done(function (data) {
        if (data.result != null) {
            Swal.fire("Ok", data.result, "success");
            limpiarFormularioClientes();
            cargarModuloAgregarCliente();
            extraerClientes();
            actualizarListadoClientes();
            resultadoBusquedaClientes();
        } else {
            if (data.error != null) {
                Swal.fire("Error", data.error, "warning");
            }
        }
    });
}

function mostrarImagen(){
    var archivoF = document.getElementById("txtFotoCliente");
    var imagen = document.getElementById("imgFotoCliente");
    var textoB64;
    if (archivoF.files.length > 0) {
        // Objeto de lector de archivos
        var fr = new FileReader();
        fr.onload = function(){
            imagen.src = "";
            imagen.src = fr.result;
            textoB64 = fr.result.replace(/^data:image\/(png|jpg|jpeg);base64,/,"");
            $("#txtCodigoImagenCliente").val(textoB64);
        };
        fr.readAsDataURL(archivoF.files[0]);
    }
}

function eliminarImagen(){
    document.getElementById("txtFotoCliente").value = "";
    document.getElementById("imgFotoCliente").src = "";
    document.getElementById("txtCodigoImagenCliente").value = "";
}

function cargarModuloEditarCliente(idEnviado){
    $("#seccionGestionarCliente").show();
    $("#tablaClientes").hide();
    $("#datosTablaBusquedaClientes").hide();
    $.ajax(
        {
            "type" : "GET",
            "url" : "./modulos/editarCliente.html",
            "async" : true
        }
    ).done(
        function(data)
        {
            for (var i = 0; i < clientes.length; i++) {
                var auxId = clientes[i].id;
                if (auxId == idEnviado) {
                    var posicionCliente = i;
                }
            }
            $("#seccionGestionarCliente").html(data);
            $("#txtCodigoCliente").val(clientes[posicionCliente].id);
            $("#txtCodigoPersona").val(clientes[posicionCliente].persona.id);
            $("#txtCodigoUsuario").val(clientes[posicionCliente].usuario.id);
            $("#txtNumeroCliente").val(clientes[posicionCliente].numUnico);
            $("#txtNombreCliente").val(clientes[posicionCliente].persona.nombre);
            $("#txtApellidoPCliente").val(clientes[posicionCliente].persona.apellidoP);
            $("#txtApellidoMCliente").val(clientes[posicionCliente].persona.apellidoM);
            $("#txtGeneroCliente").val(clientes[posicionCliente].persona.genero);
            $("#txtTelefonoCliente").val(clientes[posicionCliente].persona.telefono);
            $("#txtRfcCliente").val(clientes[posicionCliente].persona.rfc);
            $("#txtDomicilioCliente").val(clientes[posicionCliente].persona.domicilio);
            $("#txtUsuarioCliente").val(clientes[posicionCliente].usuario.nombreUsu);
            $("#txtContrasenaCliente").val(clientes[posicionCliente].usuario.contrasenia);
            $("#txtRolCliente").val(clientes[posicionCliente].usuario.rol);
            $("#txtCorreoCliente").val(clientes[posicionCliente].correo);
            $("#imgFotoCliente").attr("src","data:image/jpg;base64," + clientes[posicionCliente].foto);
            var activo = document.getElementById("txtEstatusCliente1");
            var inactivo = document.getElementById("txtEstatusCliente2");
            if(clientes[posicionCliente].estatus  == 1){
                activo.setAttribute("checked",true);
            }else{
                if(clientes[posicionCliente].estatus  == 0){
                    inactivo.setAttribute("checked",true);
                }
            }
        }
    );
}

function editarCliente(){
    var estatusCl1 = document.getElementById("txtEstatusCliente1").checked;
    var estatusCl2 = document.getElementById("txtEstatusCliente2").checked;
    if(estatusCl1){
        var estatusCl = 1;
    }else{
        if(estatusCl2){
            var estatusCl = 0;
        }
    }
    
    var usuario;
    var idUsuario = $("#txtCodigoUsuario").val();
    var nombreUsuario = $("#txtUsuarioCliente").val();
    var contraseniaUsuario = $("#txtContrasenaCliente").val();
    var rolUsuario = $("#txtRolCliente").val();
    usuario = {"id": idUsuario, "nombreUsu": nombreUsuario, "contrasenia": contraseniaUsuario, "rol":rolUsuario};
    
    var persona;
    var idPersona = $("#txtCodigoPersona").val();
    var nombrePersona = $("#txtNombreCliente").val();
    var apellidoPPersona = $("#txtApellidoPCliente").val();
    var apellidoMPersona = $("#txtApellidoMCliente").val();
    var generoPersona = $("#txtGeneroCliente").val();
    var domicilioPersona = $("#txtDomicilioCliente").val();
    var telefonoPersona = $("#txtTelefonoCliente").val();
    var rfcPersona = $("#txtRfcCliente").val();
    persona = {"id": idPersona, "nombre": nombrePersona, "apellidoP": apellidoPPersona, "apellidoM": apellidoMPersona,
    "genero": generoPersona, "domicilio": domicilioPersona, "telefono": telefonoPersona, "rfc": rfcPersona};
    
    var cliente;
    var idCliente = $("#txtCodigoCliente").val();
    var numeroUnico = $("#txtNumeroCliente").val();
    var puestoCliente = $("#txtPuestoCliente").val();
    var correoCliente = $("#txtCorreoCliente").val();
    var estatusCliente = estatusCl;
    var fotoCliente = $("#txtCodigoImagenCliente").val();
    cliente = {"id": idCliente, "numUnico": numeroUnico, "puesto": puestoCliente,
        "correo": correoCliente, "estatus": estatusCliente, "foto": fotoCliente, "rutaFoto": "",
        "persona": persona, "usuario": usuario};
    
    var data = {"cliente": JSON.stringify(cliente),"t":sessionStorage.getItem("token")};
    $.ajax({
        "url": "api/cliente/update",
        "type": "POST",
        "async": true,
        "data": data
    }).done(function (data) {
        if (data.result != null) {
            Swal.fire("Ok", data.result, "success");
            extraerClientes();
            actualizarListadoClientes();
            resultadoBusquedaClientes();
        } else {
            if (data.error != null) {
                Swal.fire("Error", data.error, "warning");
            }
        }
    });
}

function validarPositivo(){
    $("#txtBusquedaId").on("input", function()
    {
        this.value = this.value.replace(/[^0-9]/g, '').replace(/,/g, '.');
    });
}

function limpiarFormularioClientes(){
    $("#txtCodigoCliente").val("");
    $("#txtCodigoPersona").val("");
    $("#txtCodigoUsuario").val("");
    $("#txtNombreCliente").val("");
    $("#txtNumeroCliente").val("");
    $("#txtApellidoPCliente").val("");
    $("#txtApellidoMCliente").val("");
    $("#txtGeneroCliente").val("");
    $("#txtTelefonoCliente").val("");
    $("#txtRfcCliente").val("");
    $("#txtDomicilioCliente").val("");
    $("#txtPuestoCliente").val("");
    $("#txtUsuarioCliente").val("");
    $("#txtContrasenaCliente").val("");
    $("#txtRolCliente").val("");
    $("#txtCorreoCliente").val("");
    $("#txtCodigoImagenCliente").val("");
    $("#txtRutaImagenCliente").val("");
}

function regresar(){
    $("#seccionGestionarCliente").hide();
    $("#datosTablaBusquedaClientes").hide();
    $("#tablaClientes").show();
}

function actualizarListadoClientes(){
    extraerClientes();
    var datosTabla = "";
    var data = {"estatus":1,"t":sessionStorage.getItem("token")};
    $.ajax({
        "url" : "api/cliente/getAllStatus",
        "type" : "GET",
        "async" : false,
        "data" : data
    }
    ).done(function (data) {
    var registrosActivos = data;
        for (var i = 0; i < registrosActivos.length; i++){
            datosTabla += "<tr>";
            datosTabla += "<td scope='col' class='id_tabla'>" + registrosActivos[i].id + "</td>";
            datosTabla += "<td class='registro'>" + registrosActivos[i].numUnico + "</td>";
            datosTabla += "<td class='registro'>" + registrosActivos[i].persona.nombre + "</td>";
            datosTabla += "<td class='registro'>" + registrosActivos[i].persona.apellidoP + "</td>";
            datosTabla += "<td class='registro'>" + registrosActivos[i].persona.apellidoM + "</td>";
            datosTabla += "<td class='registro'>" + registrosActivos[i].correo + "</td>";
            datosTabla += "<td class='registro'>" + registrosActivos[i].persona.telefono + "</td>";
            if (registrosActivos[i].estatus == 1){
                datosTabla += "<td class='registro' style='background-color: #c6dfc3;'>Activo</td>";
            } else {
                datosTabla += "<td class='registro' style='background-color: #ffa6a6;'>Inactivo</td>";
            }
            datosTabla += "<td class='registro'><button onclick='eliminarCliente(" + registrosActivos[i].id + ");'><img src='./img/eliminar.svg' alt='Info.' class='icono'></button></td>";
            datosTabla += "<td class='registro'><button onclick='cargarModuloEditarCliente(" + registrosActivos[i].id + ");'><img src='./img/editar.svg' alt='Edit.' class='icono'></button></td>";
            datosTabla += "</tr>";
        }
        $("#datosTablaClientes").html(datosTabla);
    });
    
    data = {"estatus":0,"t":sessionStorage.getItem("token")};
    $.ajax({
        "url": "api/cliente/getAllStatus",
        "type": "GET",
        "async": false,
        "data": data
    }).done(function (data) {
        var registrosInactivos = data;
        for (var i = 0; i < registrosInactivos.length; i++)
        {
            datosTabla += "<tr>";
            datosTabla += "<td scope='col' class='id_tabla'>" + registrosInactivos[i].id + "</td>";
            datosTabla += "<td class='registro'>" + registrosInactivos[i].numUnico + "</td>";
            datosTabla += "<td class='registro'>" + registrosInactivos[i].persona.nombre + "</td>";
            datosTabla += "<td class='registro'>" + registrosInactivos[i].persona.apellidoP + "</td>";
            datosTabla += "<td class='registro'>" + registrosInactivos[i].persona.apellidoM + "</td>";
            datosTabla += "<td class='registro'>" + registrosInactivos[i].correo + "</td>";
            datosTabla += "<td class='registro'>" + registrosInactivos[i].persona.telefono + "</td>";
            if (registrosInactivos[i].estatus == 1)
            {
                datosTabla += "<td class='registro' style='background-color: #c6dfc3;'>Activo</td>";
            } else
            {
                datosTabla += "<td class='registro' style='background-color: #ffa6a6;'>Inactivo</td>";
            }
            datosTabla += "<td class='registro'><button onclick='eliminarCliente(" + registrosInactivos[i].id + ");'><img src='./img/eliminar.svg' alt='Info.' class='icono'></button></td>";
            datosTabla += "<td class='registro'><button onclick='cargarModuloEditarCliente(" + registrosInactivos[i].id + ");'><img src='./img/editar.svg' alt='Edit.' class='icono'></button></td>";
            datosTabla += "</tr>";
        }
        $("#datosTablaClientes").html(datosTabla);
    });
}

function limpiarFormularioBuscar(){
    botonBuscar = document.getElementById("btnBuscar");
    botonBuscar.setAttribute("disabled",true);
    $("#txtBusquedaId").val("");
    $("#txtNumeroUBusqueda").val("");
    $("#txtNombreBusqueda").val("");
    $("#txtApellidoPBusqueda").val("");
    $("#txtApellidoMBusqueda").val("");
    $("#txtGeneroBusqueda").val("");
    $("#txtCorreoBusqueda").val("");
    $("#txtTelefonoBusqueda").val("");
    $("#txtRfcBusqueda").val("");
    $("#txtDomicilioBusqueda").val("");
    $("#txtUsuarioBusqueda").val("");
    $("#txtRolBusqueda").val("");
    document.querySelectorAll('[name=txtBusquedaEstatus]').forEach((x) => x.checked = false);
}

function habilitarBotonBusqueda(){
    tipoDeBusqueda = $("#selcTipoDeBusqueda").val();
    botonBuscar = document.getElementById("btnBuscar");
    switch (tipoDeBusqueda) {
        case "id":
            idBuscado = $("#txtBusquedaId").val();
            habilitarBotonBusquedaCondicion(idBuscado);
            break;
        case "numeroU":
            numeroBuscado = $("#txtNumeroUBusqueda").val();
            habilitarBotonBusquedaCondicion(numeroBuscado);
            break;
        case "nombre":
            nombreBuscado = $("#txtNombreBusqueda").val();
            habilitarBotonBusquedaCondicion(nombreBuscado);
            break;
        case "apellidoP":
            apellidoPBuscado = $("#txtApellidoPBusqueda").val();
            habilitarBotonBusquedaCondicion(apellidoPBuscado);
            break;
        case "apellidoM":
            apellidoMBuscado = $("#txtApellidoMBusqueda").val();
            habilitarBotonBusquedaCondicion(apellidoMBuscado);
            break;
        case "genero":
            generoBuscado = $("#txtGeneroBusqueda").val();
            habilitarBotonBusquedaCondicion(generoBuscado);
            break;
        case "correo":
            correoBuscado = $("#txtCorreoBusqueda").val();
            habilitarBotonBusquedaCondicion(correoBuscado);
            break;
        case "telefono":
            telefonoBuscado = $("#txtTelefonoBusqueda").val();
            habilitarBotonBusquedaCondicion(telefonoBuscado);
            break;
        case "rfc":
            rfcBuscado = $("#txtRfcBusqueda").val();
            habilitarBotonBusquedaCondicion(rfcBuscado);
            break;
        case "domicilio":
            estadoBuscado = $("#txtDomicilioBusqueda").val();
            habilitarBotonBusquedaCondicion(estadoBuscado);
            break;
        case "usuario":
            usuarioBuscado = $("#txtUsuarioBusqueda").val();
            habilitarBotonBusquedaCondicion(usuarioBuscado);
        case "rol":
            rolBuscado = $("#txtRolBusqueda").val();
            habilitarBotonBusquedaCondicion(rolBuscado);
        case "estatus":
            botonBuscar.removeAttribute("disabled");
            break;
        default:
            alert("Error");
            break;
    }
}

function habilitarBotonBusquedaCondicion(texto){
    if(texto != ""){
        botonBuscar.removeAttribute("disabled");
    }else{
        botonBuscar.setAttribute("disabled",true);
    }
}

function habilitarCampoBusqueda(){
    tipoDeBusqueda = $("#selcTipoDeBusqueda").val();
    campoBusquedaId = $("#seccionIdBusqueda");
    campoBusquedaNumero = $("#seccionNumeroUBusqueda");
    campoBusquedaNombre = $("#seccionNombreBusqueda");
    campoBusquedaApellidoP = $("#seccionApellidoPBusqueda");
    campoBusquedaApellidoM = $("#seccionApellidoMBusqueda");
    campoBusquedaGenero = $("#seccionGeneroBusqueda")
    campoBusquedaCorreo = $("#seccionCorreoBusqueda");
    campoBusquedaTelefono = $("#seccionTelefonoBusqueda");
    campoBusquedaRfc = $("#seccionRfcBusqueda");
    campoBusquedaDomicilio = $("#seccionDomicilioBusqueda");
    campoBusquedaUsuario = $("#seccionUsuarioBusqueda");
    campoBusquedaRol = $("#seccionRolBusqueda");
    campoBusquedaEstatus = $("#seccionEstatusBusqueda");
    
    campoBusquedaId.addClass("tipoBusquedaOculto");
    campoBusquedaNumero.addClass("tipoBusquedaOculto");
    campoBusquedaNombre.addClass("tipoBusquedaOculto");
    campoBusquedaApellidoP.addClass("tipoBusquedaOculto");
    campoBusquedaApellidoM.addClass("tipoBusquedaOculto");
    campoBusquedaGenero.addClass("tipoBusquedaOculto");
    campoBusquedaCorreo.addClass("tipoBusquedaOculto");
    campoBusquedaTelefono.addClass("tipoBusquedaOculto");
    campoBusquedaRfc.addClass("tipoBusquedaOculto");
    campoBusquedaDomicilio.addClass("tipoBusquedaOculto");
    campoBusquedaUsuario.addClass("tipoBusquedaOculto");
    campoBusquedaRol.addClass("tipoBusquedaOculto");
    campoBusquedaEstatus.addClass("tipoBusquedaOculto");
    switch (tipoDeBusqueda) {
        case "id":
            campoBusquedaId.removeClass("tipoBusquedaOculto");
            break;
        case "numeroU":
            campoBusquedaNumero.removeClass("tipoBusquedaOculto");
            break;
        case "nombre":
            campoBusquedaNombre.removeClass("tipoBusquedaOculto");
            break;
        case "apellidoP":
            campoBusquedaApellidoP.removeClass("tipoBusquedaOculto");
            break;
        case "apellidoM":
            campoBusquedaApellidoM.removeClass("tipoBusquedaOculto");
            break;
        case "genero":
            campoBusquedaGenero.removeClass("tipoBusquedaOculto");
            break;
        case "correo":
            campoBusquedaCorreo.removeClass("tipoBusquedaOculto");
            break;
        case "telefono":
            campoBusquedaTelefono.removeClass("tipoBusquedaOculto");
            break;
        case "rfc":
            campoBusquedaRfc.removeClass("tipoBusquedaOculto");
            break;
        case "domicilio":
            campoBusquedaDomicilio.removeClass("tipoBusquedaOculto");
            break;
        case "usuario":
            campoBusquedaUsuario.removeClass("tipoBusquedaOculto");
            break;
        case "rol":
            campoBusquedaRol.removeClass("tipoBusquedaOculto");
            break;
        case "estatus":
            campoBusquedaEstatus.removeClass("tipoBusquedaOculto");
            break;
        default:
            alert("Error");
            break;
    }
}

function resultadoBusquedaClientes(){
    extraerClientes();
    var tablaBusqueda = "";
    var tablaBusquedaInactivos = "";
    tipoDeBusqueda = $("#selcTipoDeBusqueda").val();
    switch (tipoDeBusqueda) {
        case "id":
            var valorBuscado = $("#txtBusquedaId").val();
            for(var i = 0; i < clientes.length; i++){
                var idCliente = clientes[i].id;
                if(idCliente == valorBuscado){
                    tablaBusqueda += llenarTablaBusqueda(i);
                }
            }
            break;
        case "numeroU":
            var textoBuscado = $("#txtNumeroUBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for(var i = 0; i < clientes.length; i++){
                var numeroCliente = clientes[i].numUnico.toLowerCase();
                if(numeroCliente.indexOf(textoBuscadoMinusculas) !==  -1){
                    if (clientes[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (clientes[i].estatus == 1) {
                            tablaBusqueda += llenarTablaBusqueda(i);
                        }
                    }
                }
            }
            break;
        case "nombre":
            var textoBuscado = $("#txtNombreBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for(var i = 0; i < clientes.length; i++){
                var nombreCliente = clientes[i].persona.nombre.toLowerCase();
                if(nombreCliente.indexOf(textoBuscadoMinusculas) !==  -1){
                    if (clientes[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (clientes[i].estatus == 1) {
                            tablaBusqueda += llenarTablaBusqueda(i);
                        }
                    }
                }
            }
            break;
        case "apellidoP":
            var textoBuscado = $("#txtApellidoPBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for(var i = 0; i < clientes.length; i++){
                var apellidoPCliente = clientes[i].persona.apellidoP.toLowerCase();
                if(apellidoPCliente.indexOf(textoBuscadoMinusculas) !==  -1){
                    if (clientes[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (clientes[i].estatus == 1) {
                            tablaBusqueda += llenarTablaBusqueda(i);
                        }
                    }
                }
            }
            break;
        case "apellidoM":
            var textoBuscado = $("#txtApellidoMBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for(var i = 0; i < clientes.length; i++){
                var apellidoMCliente = clientes[i].persona.apellidoM.toLowerCase();
                if(apellidoMCliente.indexOf(textoBuscadoMinusculas) !==  -1){
                    if (clientes[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (clientes[i].estatus == 1) {
                            tablaBusqueda += llenarTablaBusqueda(i);
                        }
                    }
                }
            }
            break;
        case "genero":
            var textoBuscado = $("#txtGeneroBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for(var i = 0; i < clientes.length; i++){
                var generoCliente = clientes[i].persona.genero.toLowerCase();
                if(generoCliente.indexOf(textoBuscadoMinusculas) !==  -1){
                    if (clientes[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (clientes[i].estatus == 1) {
                            tablaBusqueda += llenarTablaBusqueda(i);
                        }
                    }
                }
            }
            break;
        case "correo":
            var textoBuscado = $("#txtCorreoBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for(var i = 0; i < clientes.length; i++){
                var correoCliente = clientes[i].correo.toLowerCase();
                if(correoCliente.indexOf(textoBuscadoMinusculas) !==  -1){
                    if (clientes[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (clientes[i].estatus == 1) {
                            tablaBusqueda += llenarTablaBusqueda(i);
                        }
                    }
                }
            }
            break;
        case "telefono":
            var textoBuscado = $("#txtTelefonoBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            var textoBuscado = $("#txtTelefonoBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for(var i = 0; i < clientes.length; i++){
                var telefonoCliente = clientes[i].persona.telefono.toLowerCase();
                if(telefonoCliente.indexOf(textoBuscadoMinusculas) !==  -1){
                    if (clientes[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (clientes[i].estatus == 1) {
                            tablaBusqueda += llenarTablaBusqueda(i);
                        }
                    }
                }
            }
            break;
        case "rfc":
            var textoBuscado = $("#txtRfcBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for(var i = 0; i < clientes.length; i++){
                var rfcCliente = clientes[i].persona.rfc.toLowerCase();
                if(rfcCliente.indexOf(textoBuscadoMinusculas) !==  -1){
                    if (clientes[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (clientes[i].estatus == 1) {
                            tablaBusqueda += llenarTablaBusqueda(i);
                        }
                    }
                }
            }
            break;
        case "domicilio":
            var textoBuscado = $("#txtDomicilioBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for(var i = 0; i < clientes.length; i++){
                var estadoCliente = clientes[i].persona.domicilio.toLowerCase();
                if(estadoCliente.indexOf(textoBuscadoMinusculas) !==  -1){
                    if (clientes[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (clientes[i].estatus == 1) {
                            tablaBusqueda += llenarTablaBusqueda(i);
                        }
                    }
                }
            }
            break;
        case "usuario":
            var textoBuscado = $("#txtUsuarioBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for(var i = 0; i < clientes.length; i++){
                var usuarioCliente = clientes[i].usuario.nombreUsu.toLowerCase();
                if(usuarioCliente.indexOf(textoBuscadoMinusculas) !==  -1){
                    if (clientes[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (clientes[i].estatus == 1) {
                            tablaBusqueda += llenarTablaBusqueda(i);
                        }
                    }
                }
            }
            break;
        case "rol":
            var textoBuscado = $("#txtRolBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for(var i = 0; i < clientes.length; i++){
                var usuarioCliente = clientes[i].usuario.rol.toLowerCase();
                if(usuarioCliente.indexOf(textoBuscadoMinusculas) !==  -1){
                    if (clientes[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (clientes[i].estatus == 1) {
                            tablaBusqueda += llenarTablaBusqueda(i);
                        }
                    }
                }
            }
            break;
        case "estatus":
            var estatusActivoBuscado = document.getElementById("txtBusquedaEstatus1").checked;
            var estatusInactivoBuscado = document.getElementById("txtBusquedaEstatus2").checked;
            if(estatusActivoBuscado){
                var textoBuscado = 1;
            }else{
                if(estatusInactivoBuscado){
                    var textoBuscado = 0;
                }
            }
            for(var i = 0; i < clientes.length; i++){
                var estatusCliente = clientes[i].persona.estatus;
                if(estatusCliente == textoBuscado){
                    tablaBusqueda += llenarTablaBusqueda(i);
                }
            }
            break;
        default:
            tablaBusqueda = "";
            break;
    }
    tablaBusqueda += tablaBusquedaInactivos;
    if(tablaBusqueda == ""){
        tablaBusqueda = "<tr><td colspan='10' class='registro text-center'><h4 class='py-4'>No hay resultados de búsqueda</h4></td></tr>";
    }
    $("#datosBusquedaTablaCliente").html(tablaBusqueda);
    actualizarListadoClientes();
}

function llenarTablaBusqueda(i){
    var parteTablaBusqueda = ""
    parteTablaBusqueda += "<tr>";
    parteTablaBusqueda += "<td scope='col' class='id_tabla'>"+clientes[i].id+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+clientes[i].numUnico+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+clientes[i].persona.nombre+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+clientes[i].persona.apellidoP+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+clientes[i].persona.apellidoM+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+clientes[i].correo+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+clientes[i].persona.telefono+"</td>";
    if(clientes[i].estatus == 1){
        parteTablaBusqueda += "<td class='registro' style='background-color: #c6dfc3;'>Activo</td>";
    }else{
        if(clientes[i].estatus == 0){
            parteTablaBusqueda += "<td class='registro' style='background-color: #ffa6a6;'>Inactivo</td>";
        }else{
            parteTablaBusqueda += "<td class='registro'>ERROR!!</td>";
        }
    }
    parteTablaBusqueda += "<td class='registro'><button onclick='eliminarCliente("+clientes[i].id+");'><img src='./img/eliminar.svg' alt='Info.' class='icono'></button></td>";
    parteTablaBusqueda += "<td class='registro'><button onclick='cargarModuloEditarCliente("+clientes[i].id+");'><img src='./img/editar.svg' alt='Edit.' class='icono'></button></td>";
    parteTablaBusqueda += "</tr>";
    return parteTablaBusqueda;
}

function mostrarBusqueda(){
    $("#tablaClientes").hide();
    $("#seccionGestionarCliente").hide();
    $("#datosTablaBusquedaClientes").show();
}

function ocultarBusqueda(){
    $("#tablaClientes").show();
    $("#datosTablaBusquedaClientes").hide();
}
