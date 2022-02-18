/*
    Proyecto: Sitio web – MySpa                                                         
    Equipo desarrollador: TetraNet
    Desarrollador(es):   Víctor Manuel Fonseca Muñoz
    Fecha de inicio: Lunes 19 de julio de 2021
    Versión: 5.0 (11 de noviembre de 2021 - Iván Moisés Ornelas Meza)
    Descripción general del archivo JavaScript: Este archivo JavaScript es el complemento para el uso de la interfaz
    de "gestión de empleados", este archivo contiene las instrucciones para su correcto funcionamiento y gestionamiento de 
    empleados.
*/

// Listado de empleados
var empleados;
function extraerEmpleados() {
    empleados = null;
    var data = {"t":sessionStorage.getItem("token")};
    $.ajax({
        "url" : "api/empleado/getAll",
        "type" : "GET",
        "async" : false,
        "data" : data
    }
    ).done(function(data){
        if (data.error != null) {
            Swal.fire("Error", data.error, "warning");
        }else{
            empleados = data;
        }
    });
}

// Sección de funciones
function eliminarEmpleado(idEnviado) {
    var data = {"id":idEnviado,"t":sessionStorage.getItem("token")};
    $.ajax({
        "url": "api/empleado/delete",
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
    extraerEmpleados();
    actualizarListadoEmpleados();
    resultadoBusquedaEmpleado();
}

function cargarModuloAgregarEmpleado(){
    $("#seccionGestionarEmpleado").show();
    $("#tablaEmpleados").hide();
    $("#datosTablaBusquedaEmpleados").hide();
    $.ajax(
        {
            "type" : "GET",
            "url" : "./modulos/agregarEmpleado.html",
            "async" : true
        }
    ).done(
        function(data)
        {
            var codigoMax = 0;
            for (var i = 0; i < empleados.length; i++) {
                if (empleados[i].id > codigoMax) {
                    codigoMax = empleados[i].id;
                }
            }
            $("#seccionGestionarEmpleado").html(data);
            $("#txtCodigoEmpleado").val(codigoMax+1);
        }
    );
}

function guardarEmpleado(){
    // Obtener estatus 1 ó 0
    var estatusEmp1 = document.getElementById("txtEstatusEmpleado1").checked;
    var estatusEmp2 = document.getElementById("txtEstatusEmpleado2").checked;
    if(estatusEmp1){
        var estatusEmp = 1;
    }else{
        if(estatusEmp2){
            var estatusEmp = 0;
        }
    }
    
    // Obtener RFC en mayúsculas
    var rfcEmp = $("#txtRfcEmpleado").val();
    
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
    
    // Se concatena todo para obtener el número de empleado
    var num = "";
    num += "E";
    num += rfcEmp.charAt(0);
    num += rfcEmp.charAt(1);
    num += rfcEmp.charAt(2);
    num += rfcEmp.charAt(3);
    num += hoyFecha;
    var numeroEmp = num.toUpperCase();;
    
    /*
    var usuario;
    usuario.id = 0;
    usuario.nombreUsu = $("#txtUsuarioEmpleado").val();
    usuario.contrasenia = $("#txtContrasenaEmpleado").val();
    usuario.rol = $("#txtRolEmpleado").val();
    */
    var usuario;
    var idUsuario = 0;
    var nombreUsuario = sanitizar($("#txtUsuarioEmpleado").val());
    var contraseniaUsuario = sanitizar($("#txtContrasenaEmpleado").val());
    var rolUsuario = sanitizar($("#txtRolEmpleado").val());
    usuario = {"id": idUsuario, "nombreUsu": nombreUsuario, "contrasenia": contraseniaUsuario, "rol":rolUsuario};
    /*
    var persona;
    persona.id = 0;
    persona.nombre = $("#txtNombreEmpleado").val();
    persona.apellidoP = $("#txtApellidoPEmpleado").val();
    persona.apellidoM = $("#txtApellidoMEmpleado").val();
    persona.genero = $("#txtGeneroEmpleado").val();
    persona.domicilio = $("#txtDomicilioEmpleado").val();
    persona.telefono = $("#txtTelefonoEmpleado").val();
    persona.rfc = $("#txtRfcEmpleado").val();
    */
    var persona;
    var idPersona = 0;
    var nombrePersona = sanitizar($("#txtNombreEmpleado").val());
    var apellidoPPersona = sanitizar($("#txtApellidoPEmpleado").val());
    var apellidoMPersona = sanitizar($("#txtApellidoMEmpleado").val());
    var generoPersona = $("#txtGeneroEmpleado").val();
    var domicilioPersona = sanitizar($("#txtDomicilioEmpleado").val());
    var telefonoPersona = sanitizar($("#txtTelefonoEmpleado").val());
    var rfcPersona = sanitizar($("#txtRfcEmpleado").val());
    persona = {"id": idPersona, "nombre": nombrePersona, "apellidoP": apellidoPPersona, "apellidoM": apellidoMPersona,
    "genero": generoPersona, "domicilio": domicilioPersona, "telefono": telefonoPersona, "rfc": rfcPersona};
    
    /*
    var empleado;
    empleado.id = 0;
    empleado.numEmpleado = "";
    empleado.puesto = $("#txtPuestoEmpleado").val();
    empleado.estatus = estatusEmp;
    empleado.foto = $("#txtCodigoImagenEmpleado").val();
    empleado.rutaFoto = $("#txtRutaImagenEmpleado").val();
    empleado.persona = persona;
    empleado.usuario = usuario; 
    */
    
    var empleado;
    var idEmpleado = 0;
    var numeroEmpleado = numeroEmp.toUpperCase();
    var puestoEmpleado = sanitizar($("#txtPuestoEmpleado").val());
    var estatusEmpleado = estatusEmp;
    var codigoFotoEmpleado = $("#txtCodigoImagenEmpleado").val();
    empleado = {"id": idEmpleado, "numEmpleado": numeroEmpleado, "puesto": puestoEmpleado,
        "estatus": estatusEmpleado, "foto": codigoFotoEmpleado, "rutaFoto": "",
        "persona": persona, "usuario": usuario};
    
    var data = {"empleado":JSON.stringify(empleado),"t":sessionStorage.getItem("token")};
            $.ajax({
                "url" : "api/empleado/insert",
                "type" : "POST",
                "async" : true,
                "data" : data
            }).done(function(data){
                if (data.result != null) {
                    Swal.fire("Ok", data.result, "success");
                    limpiarFormularioEmpleados();
                    cargarModuloAgregarEmpleado();
                    extraerEmpleados();
                    actualizarListadoEmpleados();
                    resultadoBusquedaEmpleado();
                }else{
                    if (data.error != null) {
                        Swal.fire("Error", data.error, "warning");
                    }
                }
            });
}

function mostrarImagen(){
    var archivoF = document.getElementById("txtFotoEmpleado");
    var imagen = document.getElementById("imgFotoEmpleado");
    var textoB64;
    if (archivoF.files.length > 0) {
        // Objeto de lector de archivos
        var fr = new FileReader();
        fr.onload = function(){
            imagen.src = "";
            imagen.src = fr.result;
            textoB64 = fr.result.replace(/^data:image\/(png|jpg|jpeg);base64,/,"");
            $("#txtCodigoImagenEmpleado").val(textoB64);
        };
        fr.readAsDataURL(archivoF.files[0]);
    }
}

function eliminarImagen(){
    document.getElementById("txtFotoEmpleado").value = "";
    document.getElementById("imgFotoEmpleado").src = "";
    document.getElementById("txtCodigoImagenEmpleado").value = "";
}

function cargarModuloEditarEmpleado(idEnviado){
    $("#seccionGestionarEmpleado").show();
    $("#tablaEmpleados").hide();
    $("#datosTablaBusquedaEmpleados").hide();
    $.ajax(
        {
            "type" : "GET",
            "url" : "./modulos/editarEmpleado.html",
            "async" : true
        }
    ).done(
        function(data)
        {
            for (var i = 0; i < empleados.length; i++) {
                var auxId = empleados[i].id;
                if (auxId == idEnviado) {
                    var posicionEmpleado = i;
                }
            }
            $("#seccionGestionarEmpleado").html(data);
            $("#txtCodigoEmpleado").val(empleados[posicionEmpleado].id);
            $("#txtCodigoPersona").val(empleados[posicionEmpleado].persona.id);
            $("#txtCodigoUsuario").val(empleados[posicionEmpleado].usuario.id);
            $("#txtNumeroEmpleado").val(empleados[posicionEmpleado].numEmpleado);
            $("#txtNombreEmpleado").val(empleados[posicionEmpleado].persona.nombre);
            $("#txtApellidoPEmpleado").val(empleados[posicionEmpleado].persona.apellidoP);
            $("#txtApellidoMEmpleado").val(empleados[posicionEmpleado].persona.apellidoM);
            $("#txtGeneroEmpleado").val(empleados[posicionEmpleado].persona.genero);
            $("#txtTelefonoEmpleado").val(empleados[posicionEmpleado].persona.telefono);
            $("#txtRfcEmpleado").val(empleados[posicionEmpleado].persona.rfc);
            $("#txtDomicilioEmpleado").val(empleados[posicionEmpleado].persona.domicilio);
            $("#txtPuestoEmpleado").val(empleados[posicionEmpleado].puesto);
            $("#txtUsuarioEmpleado").val(empleados[posicionEmpleado].usuario.nombreUsu);
            $("#txtContrasenaEmpleado").val(empleados[posicionEmpleado].usuario.contrasenia);
            $("#txtRolEmpleado").val(empleados[posicionEmpleado].usuario.rol);
            $("#imgFotoEmpleado").attr("src","data:image/jpg;base64," + empleados[posicionEmpleado].foto);
            var activo = document.getElementById("txtEstatusEmpleado1");
            var inactivo = document.getElementById("txtEstatusEmpleado2");
            if (empleados[posicionEmpleado].estatus == 1){
                activo.setAttribute("checked", true);
            }else{
                if (empleados[posicionEmpleado].estatus == 0) {
                    inactivo.setAttribute("checked", true);
                }
            }
        }
    );
}

function editarEmpleado(){
    var estatusEmp1 = document.getElementById("txtEstatusEmpleado1").checked;
    var estatusEmp2 = document.getElementById("txtEstatusEmpleado2").checked;
    if(estatusEmp1){
        var estatusEmp = 1;
    }else{
        if(estatusEmp2){
            var estatusEmp = 0;
        }
    }
    /*
    var usuario;
    usuario.id = 0;
    usuario.nombreUsu = $("#txtUsuarioEmpleado").val();
    usuario.contrasenia = $("#txtContrasenaEmpleado").val();
    usuario.rol = $("#txtRolEmpleado").val();
    */
    var usuario;
    var idUsuario = $("#txtCodigoUsuario").val();
    var nombreUsuario = sanitizar($("#txtUsuarioEmpleado").val());
    var contraseniaUsuario = sanitizar($("#txtContrasenaEmpleado").val());
    var rolUsuario = sanitizar($("#txtRolEmpleado").val());
    usuario = {"id": idUsuario, "nombreUsu": nombreUsuario, "contrasenia": contraseniaUsuario, "rol":rolUsuario};
    //usuario = "{\"id\":\""+idUsuario+"\",\"nombreUsu\":\""+nombreUsuario+"\",\"contrasenia\":\""+contraseniaUsuario+"\",\"rol\":\""+rolUsuario+"}";
    /*
    var persona;
    persona.id = 0;
    persona.nombre = $("#txtNombreEmpleado").val();
    persona.apellidoP = $("#txtApellidoPEmpleado").val();
    persona.apellidoM = $("#txtApellidoMEmpleado").val();
    persona.genero = $("#txtGeneroEmpleado").val();
    persona.domicilio = $("#txtDomicilioEmpleado").val();
    persona.telefono = $("#txtTelefonoEmpleado").val();
    persona.rfc = $("#txtRfcEmpleado").val();
    */
    var persona;
    var idPersona = $("#txtCodigoPersona").val();
    var nombrePersona = sanitizar($("#txtNombreEmpleado").val());
    var apellidoPPersona = sanitizar($("#txtApellidoPEmpleado").val());
    var apellidoMPersona = sanitizar($("#txtApellidoMEmpleado").val());
    var generoPersona = $("#txtGeneroEmpleado").val();
    var domicilioPersona = sanitizar($("#txtDomicilioEmpleado").val());
    var telefonoPersona = sanitizar($("#txtTelefonoEmpleado").val());
    var rfcPersona = sanitizar($("#txtRfcEmpleado").val());
    persona = {"id": idPersona, "nombre": nombrePersona, "apellidoP": apellidoPPersona, "apellidoM": apellidoMPersona,
    "genero": generoPersona, "domicilio": domicilioPersona, "telefono": telefonoPersona, "rfc": rfcPersona};
    /*
    var empleado;
    empleado.id = 0;
    empleado.numEmpleado = "";
    empleado.puesto = $("#txtPuestoEmpleado").val();
    empleado.estatus = estatusEmp;
    empleado.foto = $("#txtCodigoImagenEmpleado").val();
    empleado.rutaFoto = $("#txtRutaImagenEmpleado").val();
    empleado.persona = persona;
    empleado.usuario = usuario; 
    */
    var empleado;
    var idEmpleado = $("#txtCodigoEmpleado").val();
    var numeroEmpleado = sanitizar($("#txtNumeroEmpleado").val());
    var puestoEmpleado = sanitizar($("#txtPuestoEmpleado").val());
    var estatusEmpleado = estatusEmp;
    var codigoFotoEmpleado = $("#txtCodigoImagenEmpleado").val();
    empleado = {"id": idEmpleado, "numEmpleado": numeroEmpleado, "puesto": puestoEmpleado,
        "estatus": estatusEmpleado, "foto": codigoFotoEmpleado, "rutaFoto": "",
        "persona": persona, "usuario": usuario};
    var data = {"empleado":JSON.stringify(empleado),"t":sessionStorage.getItem("token")};
            $.ajax({
                "url" : "api/empleado/update",
                "type" : "POST",
                "async" : true,
                "data" : data
            }).done(function(data){
                if (data.result != null) {
                    Swal.fire("Ok", data.result, "success");
                    extraerEmpleados();
                    actualizarListadoEmpleados();
                    resultadoBusquedaEmpleado();
                }else{
                    if (data.error != null) {
                        Swal.fire("Error", data.error, "warning");
                    }
                }
            });
}

function limpiarFormularioEmpleados(){
    $("#txtCodigoEmpleado").val("");
    $("#txtCodigoPersona").val("");
    $("#txtCodigoUsuario").val("");
    $("#txtNombreEmpleado").val("");
    $("#txtApellidoPEmpleado").val("");
    $("#txtApellidoMEmpleado").val("");
    $("#txtGeneroEmpleado").val("");
    $("#txtTelefonoEmpleado").val("");
    $("#txtRfcEmpleado").val("");
    $("#txtDomicilioEmpleado").val("");
    $("#txtPuestoEmpleado").val("");
    $("#txtUsuarioEmpleado").val("");
    $("#txtContrasenaEmpleado").val("");
    $("#txtRolEmpleado").val("");
    $("#txtRutaImagenEmpleado").val("");
    $("#txtCodigoImagenEmpleado").val("");
}

function regresar(){
    $("#seccionGestionarEmpleado").hide();
    $("#datosTablaBusquedaEmpleados").hide();
    $("#tablaEmpleados").show();
}

function actualizarListadoEmpleados(){
    extraerEmpleados();
    var datosTabla = "";
    var data = {"estatus":1,"t":sessionStorage.getItem("token")};
    $.ajax({
        "url" : "api/empleado/getAllStatus",
        "type" : "GET",
        "async" : false,
        "data" : data
    }
    ).done(function(data){
        var registrosActivos = data;
        for (var i = 1; i < registrosActivos.length; i++)
        {
            datosTabla += "<tr>";
            datosTabla += "<td scope='col' class='id_tabla'>" + registrosActivos[i].id + "</td>";
            datosTabla += "<td class='registro'>" + registrosActivos[i].numEmpleado + "</td>";
            datosTabla += "<td class='registro'>" + registrosActivos[i].persona.nombre + "</td>";
            datosTabla += "<td class='registro'>" + registrosActivos[i].persona.apellidoP + "</td>";
            datosTabla += "<td class='registro'>" + registrosActivos[i].persona.apellidoM + "</td>";
            datosTabla += "<td class='registro'>" + registrosActivos[i].puesto + "</td>";
            datosTabla += "<td class='registro'>" + registrosActivos[i].persona.telefono + "</td>";
            if (registrosActivos[i].estatus == 1)
            {
                datosTabla += "<td class='registro' style='background-color: #c6dfc3;'>Activo</td>";
            } else
            {
                datosTabla += "<td class='registro' style='background-color: #ffa6a6;'>Inactivo</td>";
            }
            datosTabla += "<td class='registro'><button onclick='eliminarEmpleado(" + registrosActivos[i].id + ");'><img src='./img/eliminar.svg' alt='Info.' class='icono'></button></td>";
            datosTabla += "<td class='registro'><button onclick='cargarModuloEditarEmpleado(" + registrosActivos[i].id + ");'><img src='./img/editar.svg' alt='Edit.' class='icono'></button></td>";
            datosTabla += "</tr>";
        }
        $("#datosTablaEmpleados").html(datosTabla);
    });
    
    data = {"estatus":0,"t":sessionStorage.getItem("token")};
    $.ajax({
        "url": "api/empleado/getAllStatus",
        "type": "GET",
        "async": false,
        "data": data
    }).done(function (data) {
        var registrosInactivos = data;
        for (var i = 0; i < registrosInactivos.length; i++)
        {
            datosTabla += "<tr>";
            datosTabla += "<td scope='col' class='id_tabla'>" + registrosInactivos[i].id + "</td>";
            datosTabla += "<td class='registro'>" + registrosInactivos[i].numEmpleado + "</td>";
            datosTabla += "<td class='registro'>" + registrosInactivos[i].persona.nombre + "</td>";
            datosTabla += "<td class='registro'>" + registrosInactivos[i].persona.apellidoP + "</td>";
            datosTabla += "<td class='registro'>" + registrosInactivos[i].persona.apellidoM + "</td>";
            datosTabla += "<td class='registro'>" + registrosInactivos[i].puesto + "</td>";
            datosTabla += "<td class='registro'>" + registrosInactivos[i].persona.telefono + "</td>";
            if (registrosInactivos[i].estatus == 1)
            {
                datosTabla += "<td class='registro' style='background-color: #c6dfc3;'>Activo</td>";
            } else
            {
                datosTabla += "<td class='registro' style='background-color: #ffa6a6;'>Inactivo</td>";
            }
            datosTabla += "<td class='registro'><button onclick='eliminarEmpleado(" + registrosInactivos[i].id + ");'><img src='./img/eliminar.svg' alt='Info.' class='icono'></button></td>";
            datosTabla += "<td class='registro'><button onclick='cargarModuloEditarEmpleado(" + registrosInactivos[i].id + ");'><img src='./img/editar.svg' alt='Edit.' class='icono'></button></td>";
            datosTabla += "</tr>";
        }
        $("#datosTablaEmpleados").html(datosTabla);
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
    $("#txtPuestoBusqueda").val("");
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
        case "puesto":
            puestoBuscado = $("#txtPuestoBusqueda").val();
            habilitarBotonBusquedaCondicion(puestoBuscado);
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
    campoBusquedaPuesto = $("#seccionPuestoBusqueda");
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
    campoBusquedaPuesto.addClass("tipoBusquedaOculto");
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
        case "puesto":
            campoBusquedaPuesto.removeClass("tipoBusquedaOculto");
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

function resultadoBusquedaEmpleado(){
    extraerEmpleados();
    var tablaBusqueda = "";
    var tablaBusquedaInactivos = "";
    tipoDeBusqueda = $("#selcTipoDeBusqueda").val();
    switch (tipoDeBusqueda) {
        case "id":
            var valorBuscado = $("#txtBusquedaId").val();
            for(var i = 1; i < empleados.length; i++){
                var idEmpleado = empleados[i].id;
                if(idEmpleado == valorBuscado){
                    tablaBusqueda += llenarTablaBusqueda(i);
                }
            }
            break;
        case "numeroU":
            var textoBuscado = $("#txtNumeroUBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for(var i = 1; i < empleados.length; i++){
                var numeroEmpleado = empleados[i].numEmpleado.toLowerCase();
                if(numeroEmpleado.indexOf(textoBuscadoMinusculas) !==  -1){
                    if (empleados[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (empleados[i].estatus == 1) {
                            tablaBusqueda += llenarTablaBusqueda(i);
                        }
                    }
                }
            }
            break;
        case "nombre":
            var textoBuscado = $("#txtNombreBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for(var i = 1; i < empleados.length; i++){
                var nombreEmpleado = empleados[i].persona.nombre.toLowerCase();
                if(nombreEmpleado.indexOf(textoBuscadoMinusculas) !==  -1){
                    if (empleados[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (empleados[i].estatus == 1) {
                            tablaBusqueda += llenarTablaBusqueda(i);
                        }
                    }
                }
            }
            break;
        case "apellidoP":
            var textoBuscado = $("#txtApellidoPBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for(var i = 1; i < empleados.length; i++){
                var apellidoPEmpleado = empleados[i].persona.apellidoP.toLowerCase();
                if(apellidoPEmpleado.indexOf(textoBuscadoMinusculas) !==  -1){
                    if (empleados[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (empleados[i].estatus == 1) {
                            tablaBusqueda += llenarTablaBusqueda(i);
                        }
                    }
                }
            }
            break;
        case "apellidoM":
            var textoBuscado = $("#txtApellidoMBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for(var i = 1; i < empleados.length; i++){
                var apellidoMEmpleado = empleados[i].persona.apellidoM.toLowerCase();
                if(apellidoMEmpleado.indexOf(textoBuscadoMinusculas) !==  -1){
                    if (empleados[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (empleados[i].estatus == 1) {
                            tablaBusqueda += llenarTablaBusqueda(i);
                        }
                    }
                }
            }
            break;
        case "genero":
            var textoBuscado = $("#txtGeneroBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for(var i = 1; i < empleados.length; i++){
                var generoEmpleado = empleados[i].persona.genero.toLowerCase();
                if(generoEmpleado.indexOf(textoBuscadoMinusculas) !==  -1){
                    if (empleados[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (empleados[i].estatus == 1) {
                            tablaBusqueda += llenarTablaBusqueda(i);
                        }
                    }
                }
            }
            break;
        case "puesto":
            var textoBuscado = $("#txtPuestoBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for(var i = 1; i < empleados.length; i++){
                var puestoEmpleado = empleados[i].puesto.toLowerCase();
                if(puestoEmpleado.indexOf(textoBuscadoMinusculas) !==  -1){
                    if (empleados[i].estatus == 0) {
                        tablaBusqueda += llenarTablaBusqueda(i);
                    }else{
                        if (empleados[i].estatus == 1) {
                            tablaBusqueda = llenarTablaBusqueda(i) + tablaBusqueda;
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
            for(var i = 1; i < empleados.length; i++){
                var telefonoEmpleado = empleados[i].persona.telefono.toLowerCase();
                if(telefonoEmpleado.indexOf(textoBuscadoMinusculas) !==  -1){
                    if (empleados[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (empleados[i].estatus == 1) {
                            tablaBusqueda += llenarTablaBusqueda(i);
                        }
                    }
                }
            }
            break;
        case "rfc":
            var textoBuscado = $("#txtRfcBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for(var i = 1; i < empleados.length; i++){
                var rfcEmpleado = empleados[i].persona.rfc.toLowerCase();
                if(rfcEmpleado.indexOf(textoBuscadoMinusculas) !==  -1){
                    if (empleados[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (empleados[i].estatus == 1) {
                            tablaBusqueda += llenarTablaBusqueda(i);
                        }
                    }
                }
            }
            break;
        case "domicilio":
            var textoBuscado = $("#txtDomicilioBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for(var i = 1; i < empleados.length; i++){
                var estadoEmpleado = empleados[i].persona.domicilio.toLowerCase();
                if(estadoEmpleado.indexOf(textoBuscadoMinusculas) !==  -1){
                    if (empleados[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (empleados[i].estatus == 1) {
                            tablaBusqueda += llenarTablaBusqueda(i);
                        }
                    }
                }
            }
            break;
        case "usuario":
            var textoBuscado = $("#txtUsuarioBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for(var i = 1; i < empleados.length; i++){
                var usuarioEmpleado = empleados[i].usuario.nombreUsu.toLowerCase();
                if(usuarioEmpleado.indexOf(textoBuscadoMinusculas) !==  -1){
                    if (empleados[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (empleados[i].estatus == 1) {
                            tablaBusqueda += llenarTablaBusqueda(i);
                        }
                    }
                }
            }
            break;
        case "rol":
            var textoBuscado = $("#txtRolBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for(var i = 1; i < empleados.length; i++){
                var usuarioEmpleado = empleados[i].usuario.rol.toLowerCase();
                if(usuarioEmpleado.indexOf(textoBuscadoMinusculas) !==  -1){
                    if (empleados[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (empleados[i].estatus == 1) {
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
            for(var i = 1; i < empleados.length; i++){
                var estatusEmpleado = empleados[i].estatus;
                if(estatusEmpleado == textoBuscado){
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
    $("#datosBusquedaTablaEmpleados").html(tablaBusqueda);
    actualizarListadoEmpleados();
}

function llenarTablaBusqueda(i){
    var parteTablaBusqueda = "";
    parteTablaBusqueda += "<tr>";
    parteTablaBusqueda += "<td scope='col' class='id_tabla'>"+empleados[i].id+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+empleados[i].numEmpleado+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+empleados[i].persona.nombre+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+empleados[i].persona.apellidoP+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+empleados[i].persona.apellidoM+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+empleados[i].puesto+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+empleados[i].persona.telefono+"</td>";
    if(empleados[i].estatus == 1)
    {
        parteTablaBusqueda += "<td class='registro' style='background-color: #c6dfc3;'>Activo</td>";
    }
    else
    {
        if(empleados[i].estatus == 0)
        {
            parteTablaBusqueda += "<td class='registro' style='background-color: #ffa6a6;'>Inativo</td>";
        }
        else
        {
            parteTablaBusqueda += "<td class='registro'>ERROR!!</td>";
        }
    }
    parteTablaBusqueda += "<td class='registro'><button onclick='eliminarEmpleado(" + empleados[i].id + ");'><img src='./img/eliminar.svg' alt='Info.' class='icono'></button></td>";
    parteTablaBusqueda += "<td class='registro'><button onclick='cargarModuloEditarEmpleado(" + empleados[i].id + ");'><img src='./img/editar.svg' alt='Edit.' class='icono'></button></td>";
    parteTablaBusqueda += "</tr>";
    return parteTablaBusqueda;
}

function mostrarBusqueda(){
    $("#tablaEmpleados").hide();
    $("#seccionGestionarEmpleado").hide();
    $("#datosTablaBusquedaEmpleados").show();
}

function ocultarBusqueda(){
    $("#tablaEmpleados").show();
    $("#datosTablaBusquedaEmpleados").hide();
}

function validar(){
    tipoDeBusqueda = $("#selcTipoDeBusqueda").val();
    var texto = "";
    switch (tipoDeBusqueda) {
        case "id":
            texto = $("#txtBusquedaId").val();
            for (var i = 0; i < texto.length; i++) {
                texto = texto.replace("e","");
                texto = texto.replace(".","");
            }
            $("#txtBusquedaId").val(texto);
            break;
        case "numeroU":
            texto = $("#txtNumeroUBusqueda").val();
            texto = sanitizar(texto);
            $("#txtNumeroUBusqueda").val(texto);
            break;
        case "nombre":
            texto = $("#txtNombreBusqueda").val();
            texto = sanitizar(texto);
            $("#txtNombreBusqueda").val(texto);
            break;
        case "apellidoP":
            texto = $("#txtApellidoPBusqueda").val();
            texto = sanitizar(texto);
            $("#txtApellidoPBusqueda").val(texto);
            break;
        case "apellidoM":
            texto = $("#txtApellidoMBusqueda").val();
            texto = sanitizar(texto);
            $("#txtApellidoMBusqueda").val(texto);
            break;
        case "puesto":
            texto = $("#txtPuestoBusqueda").val();
            texto = sanitizar(texto);
            $("#txtPuestoBusqueda").val(texto);
            break;
        case "telefono":
            texto = $("#txtTelefonoBusqueda").val();
            texto = sanitizar(texto);
            $("#txtTelefonoBusqueda").val(texto);
            break;
        case "rfc":
            texto = $("#txtRfcBusqueda").val();
            texto = sanitizar(texto);
            $("#txtRfcBusqueda").val(texto);
            break;
        case "domicilio":
            texto = $("#txtDomicilioBusqueda").val();
            texto = sanitizar(texto);
            $("#txtDomicilioBusqueda").val(texto);
            break;
        case "usuario":
            texto = $("#txtUsuarioBusqueda").val();
            texto = sanitizar(texto);
            $("#txtUsuarioBusqueda").val(texto);
            break;
        case "rol":
            texto = $("#txtRolBusqueda").val();
            texto = sanitizar(texto);
            $("#txtRolBusqueda").val(texto);
            break;
        default:
            alert("Error");
            break;
    }
}

function sanitizar(texto){
    for (var i = 0; i < texto.length; i++) {
        texto = texto.replace("(","");
        texto = texto.replace(")","");
        texto = texto.replace('”',"");
        texto = texto.replace('"',"");
        texto = texto.replace(".","");
        texto = texto.replace("'","");
        texto = texto.replace(";","");
        texto = texto.replace("-","");
        texto = texto.replace("*","");
        texto = texto.replace("%","");
        texto = texto.replace("[","");
        texto = texto.replace("]","");
        texto = texto.replace("{","");
        texto = texto.replace("}","");
    }
    return texto;
}
