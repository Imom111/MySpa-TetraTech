/*
    Proyecto: Sitio web – MySpa                                                         
    Equipo desarrollador: TetraNet
    Desarrollador(es):   Alexis Alfredo Prado Ojeda
    Fecha de inicio: Lunes 19 de julio de 2021
    Versión: 5.0 (15 de noviembre de 2021 - Iván Moisés Ornelas Meza)
    Descripción general del archivo JavaScript: Este archivo JavaScript es el complemento para el uso de la interfaz
    de "gestión de salas", este archivo contiene las instrucciones para su correcto funcionamiento y gestionamiento de 
    salas.
*/

// Listado de salas por defecto
var salas;
var sucursales;
function extraerSalas() {
    salas = null;
    var data = {"t":sessionStorage.getItem("token")};
    $.ajax({
        "url" : "api/sala/getAll",
        "type" : "GET",
        "async" : false,
        "data" : data
    }
    ).done(function(data){
        if (data.error != null) {
            Swal.fire("Error", data.error, "warning");
        }else{
            salas = data;
        }
    });
}

function extraerSucursales() {
    sucursales = null;
    var data = {"estatus":1,"t":sessionStorage.getItem("token")};
    $.ajax({
        "url" : "api/sucursal/getAllStatus",
        "type" : "GET",
        "async" : false,
        "data" : data
    }
    ).done(function(data){
        if (data.error != null) {
            Swal.fire("Error", data.error, "warning");
        }else{
            sucursales = data;
        }
    });
}

// Sección de funciones
function eliminarSala(idEnviado) {
    var data = {"id":idEnviado,"t":sessionStorage.getItem("token")};
    $.ajax({
        "url": "api/sala/delete",
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
    actualizarListadoSalas();
    resultadoBusquedaSalas();
}

function cargarModuloAgregarSala(){
    $("#seccionGestionarSala").show();
    $("#tablaSalas").removeClass("col-md-12");
    $("#tablaSalas").addClass("col-md-8");
    $("#datosTablaBusquedaSalas").removeClass("col-md-12");
    $("#datosTablaBusquedaSalas").addClass("col-md-8");
    $.ajax(
        {
            "type" : "GET",
            "url" : "./modulos/agregarSala.html",
            "async" : true
        }
    ).done(
        function(data){
            var codigoMax = 0;
            for (var i = 0; i < salas.length; i++) {
                if (salas[i].id > codigoMax) {
                    codigoMax = salas[i].id;
                }
            }
            $("#seccionGestionarSala").html(data);
            $("#txtCodigoSala").val(codigoMax + 1);
            var opcSelect = llenarSelectSucursales();
            $("#txtSucursalSala").html(opcSelect);
        }
    );
}
function llenarSelectSucursales() {
    extraerSucursales();
    var opcSelect = "<option value='' selected>Selecciona una sucursal</option>";
    for (var i = 0; i < sucursales.length; i++) {
        opcSelect += "<option value='" + sucursales[i].id + "'>" + sucursales[i].nombre + "</option>";
    }
    return opcSelect;
}
function guardarSala(){
    var sala;
    var nombreSala = $("#txtNombreSala").val();
    var sucursalSala = $("#txtSucursalSala").val();
    var descripcionSala = $("#txtDescripcionSala").val();
    var estatusSala1 = document.getElementById("txtEstatusSala1").checked;
    var estatusSala2 = document.getElementById("txtEstatusSala2").checked;
    if(estatusSala1){
        var estatusSala = 1;
    }else{
        if(estatusSala2){
            var estatusSala = 0;
        }
    }
    var sucursal;
    sucursal = {"id":sucursalSala,"nombre":"","domicilio":"","latitud":0,"longitud":0,"estatus":0};
    sala = {"nombre":nombreSala,"descripcion":descripcionSala,"foto":"","rutaFoto":"","estatus":estatusSala,"sucursal":sucursal};
    existenciaSala = false;
    for(var i = 0; i < salas.length; i++){
        if((salas[i].nombre === sala.nombre) && (salas[i].sucursal === sala.sucursal)){
            existenciaSala = true;
        }
    }
    if(existenciaSala){
        Swal.fire("Error", "Esta sala ya existe", "warning");
    }else{
        if(nombreSala && sucursalSala && descripcionSala){
            var data = {"s": JSON.stringify(sala),"t":sessionStorage.getItem("token")};
            $.ajax({
                "url": "api/sala/insert",
                "type": "GET",
                "async": true,
                "data": data
            }).done(function (data) {
                if (data.result != null) {
                    Swal.fire("Ok", data.result, "success");
                    cargarModuloAgregarSala();
                    limpiarFormularioSalas();
                    actualizarListadoSalas();
                    resultadoBusquedaSalas();
                } else {
                    if (data.error != null) {
                        Swal.fire("Error", data.error, "warning");
                    }
                }
            });
        }
    }
}
function cargarModuloEditarSala(idEnviado){
    $("#seccionGestionarSala").show();
    $("#tablaSalas").removeClass("col-md-12");
    $("#tablaSalas").addClass("col-md-8");
    $("#datosTablaBusquedaSalas").removeClass("col-md-12");
    $("#datosTablaBusquedaSalas").addClass("col-md-8");
    $.ajax(
        {
            "type" : "GET",
            "url" : "./modulos/editarSala.html",
            "async" : true
        }
    ).done(
        function(data){
            for (var i = 0; i < salas.length; i++) {
                var auxId = salas[i].id;
                if (auxId == idEnviado) {
                    var posicionSala = i;
                }
            }
            $("#seccionGestionarSala").html(data);
            $("#txtCodigoSala").val(salas[posicionSala].id);
            $("#txtNombreSala").val(salas[posicionSala].nombre);
            var opcSelect = llenarSelectSucursales();
            $("#txtSucursalSala").html(opcSelect);
            $("#txtSucursalSala").val(salas[posicionSala].sucursal.id);
            $("#txtDescripcionSala").val(salas[posicionSala].descripcion);
            var activo = document.getElementById("txtEstatusSala1");
            var inactivo = document.getElementById("txtEstatusSala2");
            if(salas[posicionSala].estatus === 1){
                activo.setAttribute("checked",true);
            }else{
                if(salas[posicionSala].estatus === 0){
                    inactivo.setAttribute("checked",true);
                }
            }
        }
    );
}
function editarSala(){
    var sala;
    var codigoSala = $("#txtCodigoSala").val();
    var nombreSala = $("#txtNombreSala").val();
    var sucursalSala = $("#txtSucursalSala").val();
    var descripcionSala = $("#txtDescripcionSala").val();
    var estatusSala1 = document.getElementById("txtEstatusSala1").checked;
    var estatusSala2 = document.getElementById("txtEstatusSala2").checked;
    if(estatusSala1){
        var estatusSala = 1;
    }else{
        if(estatusSala2){
            var estatusSala = 0;
        }
    }
    var sucursal;
    sucursal = {"id":sucursalSala,"nombre":"","domicilio":"","latitud":0,"longitud":0,"estatus":0};
    sala = {"id":codigoSala,"nombre":nombreSala,"descripcion":descripcionSala,"foto":"","rutaFoto":"","estatus":estatusSala,"sucursal":sucursal};
    existenciaSala = false;
    for(var i = 0; i < salas.length; i++){
        if((salas[i].nombre === sala.nombre) && (salas[i].sucursal === sala.sucursal)){
            existenciaSala = true;
        }
    }
    if(existenciaSala){
        Swal.fire("Error", "Esta sala ya existe", "warning");
    }else{
        if(nombreSala && sucursalSala && descripcionSala){
            var data = {"s": JSON.stringify(sala),"t":sessionStorage.getItem("token")};
            $.ajax({
                "url": "api/sala/update",
                "type": "GET",
                "async": true,
                "data": data
            }).done(function (data) {
                if (data.result != null) {
                    Swal.fire("Ok", data.result, "success");
                    actualizarListadoSalas();
                    resultadoBusquedaSalas();
                } else {
                    if (data.error != null) {
                        Swal.fire("Error", data.error, "warning");
                    }
                }
            });
        }
    }
}

function limpiarFormularioSalas(){
    $("#txtNombreSala").val("");
    $("#txtSucursalSala").val("");
    $("#txtEstatusSala1").checked = true;
    $("#txtEstatusSala2").checked = false;
    $("#txtDescripcionSala").val("");
}

function regresar(){
    $("#seccionGestionarSala").hide();
    $("#tablaSalas").removeClass("col-md-8");
    $("#tablaSalas").addClass("col-md-12");
    $("#datosTablaBusquedaSalas").removeClass("col-md-8");
    $("#datosTablaBusquedaSalas").addClass("col-md-12");
}

function actualizarListadoSalas(){
    extraerSalas();
    var datosTabla = "";
    var data = {"estatus":1,"t":sessionStorage.getItem("token")};
    $.ajax({
        "url" : "api/sala/getAllStatus",
        "type" : "GET",
        "async" : false,
        "data" : data
    }
    ).done(function (data) {
        var registrosActivos = data;
        for (var i = 0; i < registrosActivos.length; i++){
            datosTabla += "<tr>";
            datosTabla += "<td scope='col' class='id_tabla'>" + registrosActivos[i].id + "</td>";
            datosTabla += "<td class='registro'>" + registrosActivos[i].nombre + "</td>";
            datosTabla += "<td class='registro'>" + registrosActivos[i].sucursal.nombre + "</td>";
            datosTabla += "<td class='registro'>" + registrosActivos[i].descripcion + "</td>";
            if (registrosActivos[i].estatus == 1){
                datosTabla += "<td class='registro' style='background-color: #c6dfc3;'>Activo</td>";
            } else {
                datosTabla += "<td class='registro' style='background-color: #ffa6a6;'>Inactivo</td>";
            }
            datosTabla += "<td class='registro'><button onclick='eliminarSala(" + registrosActivos[i].id + ");'><img src='./img/eliminar.svg' alt='Info.' class='icono'></button></td>";
            datosTabla += "<td class='registro'><button onclick='cargarModuloEditarSala(" + registrosActivos[i].id + ");'><img src='./img/editar.svg' alt='Edit.' class='icono'></button></td>";
            datosTabla += "</tr>";
        }
        $("#datosTablaSalas").html(datosTabla);
    });
    
    data = {"estatus":0,"t":sessionStorage.getItem("token")};
    $.ajax({
        "url" : "api/sala/getAllStatus",
        "type" : "GET",
        "async" : false,
        "data" : data
    }
    ).done(function (data) {
        var registrosInactivos = data;
        for (var i = 0; i < registrosInactivos.length; i++){
            datosTabla += "<tr>";
            datosTabla += "<td scope='col' class='id_tabla'>" + registrosInactivos[i].id + "</td>";
            datosTabla += "<td class='registro'>" + registrosInactivos[i].nombre + "</td>";
            datosTabla += "<td class='registro'>" + registrosInactivos[i].sucursal.nombre + "</td>";
            datosTabla += "<td class='registro'>" + registrosInactivos[i].descripcion + "</td>";
            if (registrosInactivos[i].estatus == 1){
                datosTabla += "<td class='registro' style='background-color: #c6dfc3;'>Activo</td>";
            } else {
                datosTabla += "<td class='registro' style='background-color: #ffa6a6;'>Inactivo</td>";
            }
            datosTabla += "<td class='registro'><button onclick='eliminarSala(" + registrosInactivos[i].id + ");'><img src='./img/eliminar.svg' alt='Info.' class='icono'></button></td>";
            datosTabla += "<td class='registro'><button onclick='cargarModuloEditarSala(" + registrosInactivos[i].id + ");'><img src='./img/editar.svg' alt='Edit.' class='icono'></button></td>";
            datosTabla += "</tr>";
        }
        $("#datosTablaSalas").html(datosTabla);
    });
}

function limpiarFormularioBuscar(){
    botonBuscar = document.getElementById("btnBuscar");
    botonBuscar.setAttribute("disabled",true);
    $("#txtBusquedaId").val("");
    $("#txtNombreBusqueda").val("");
    $("#txtSucursalBusqueda").val("");
    $("#txtDescripcionBusqueda").val("");
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
        case "nombre":
            nombreBuscado = $("#txtNombreBusqueda").val();
            habilitarBotonBusquedaCondicion(nombreBuscado);
            break;
        case "sucursal":
            sucursalBuscada = $("#txtSucursalBusqueda").val();
            habilitarBotonBusquedaCondicion(sucursalBuscada);
            break;
        case "descripcion":
            descripcionBuscada = $("#txtDescripcionBusqueda").val();
            habilitarBotonBusquedaCondicion(descripcionBuscada);
            break;
        case "estatus":
            botonBuscar.removeAttribute("disabled");
            break;
        default:
            
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
    campoBusquedaNombre = $("#seccionNombreBusqueda");
    campoBusquedaSucursal = $("#seccionSucursalBusqueda");
    campoBusquedaDescripcion = $("#seccionDescripcionBusqueda");
    campoBusquedaEstatus = $("#seccionEstatusBusqueda");
    
    campoBusquedaId.addClass("tipoBusquedaOculto");
    campoBusquedaNombre.addClass("tipoBusquedaOculto");
    campoBusquedaSucursal.addClass("tipoBusquedaOculto");
    campoBusquedaDescripcion.addClass("tipoBusquedaOculto");
    campoBusquedaEstatus.addClass("tipoBusquedaOculto");

    switch (tipoDeBusqueda) {
        case "id":
            campoBusquedaId.removeClass("tipoBusquedaOculto");
            break;
        case "nombre":
            campoBusquedaNombre.removeClass("tipoBusquedaOculto");
            break;
        case "sucursal":
            campoBusquedaSucursal.removeClass("tipoBusquedaOculto");
            var opcSelect = llenarSelectSucursales();
            $("#txtSucursalBusqueda").html(opcSelect);
            break;
        case "descripcion":
            campoBusquedaDescripcion.removeClass("tipoBusquedaOculto");
            break;
        case "estatus":
            campoBusquedaEstatus.removeClass("tipoBusquedaOculto");
            break;
        default:
            alert("Error al seleccionar un tipo de búsqueda");
            break;
    }
}

function validarPositivo(){
    $("#txtBusquedaId").on("input", function(){
        this.value = this.value.replace(/[^0-9]/g, '').replace(/,/g, '.');
    });
}

function resultadoBusquedaSalas(){
    extraerSalas();
    var tablaBusqueda = "";
    var tablaBusquedaInactivos = "";
    tipoDeBusqueda = $("#selcTipoDeBusqueda").val();
    switch (tipoDeBusqueda) {
        case "id":
            var valorBuscado = $("#txtBusquedaId").val();
            for (var i = 0; i < salas.length; i++){
                var idSala = salas[i].id;
                if (idSala == valorBuscado){
                    tablaBusqueda += llenarTablaBusqueda(i);
                }
            }
            break;
        case "nombre":
            var textoBuscado = $("#txtNombreBusqueda").val();
            var textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for(var i = 0; i < salas.length; i++){
                var nombreSala = salas[i].nombre.toLowerCase();
                if(nombreSala.indexOf(textoBuscadoMinusculas) !== -1){
                    if (salas[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (salas[i].estatus == 1) {
                            tablaBusqueda += llenarTablaBusqueda(i);
                        }
                    }
                }
            }
            break;
        case "sucursal":
            var textoBuscado = $("#txtSucursalBusqueda").val();
            for (var i = 0; i < salas.length; i++){
                var sucursalSala = salas[i].sucursal.id;
                if (sucursalSala == textoBuscado){
                    if (salas[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (salas[i].estatus == 1) {
                            tablaBusqueda += llenarTablaBusqueda(i);
                        }
                    }
                }
            }
            break;
        case "descripcion":
            var textoBuscado = $("#txtDescripcionBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for (var i = 0; i < salas.length; i++){
                var descripcionSala = salas[i].descripcion.toLowerCase();
                if (descripcionSala.indexOf(textoBuscadoMinusculas) !== -1){
                    if (salas[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (salas[i].estatus == 1) {
                            tablaBusqueda += llenarTablaBusqueda(i);
                        }
                    }
                }
            }
            break;
        case "estatus":
            var estatusActivoBuscado = document.getElementById("txtBusquedaEstatus1").checked;
            var estatusInactivoBuscado = document.getElementById("txtBusquedaEstatus2").checked;
            if (estatusActivoBuscado){
                var textoBuscado = 1;
            }else{
                if (estatusInactivoBuscado){
                    var textoBuscado = 0;
                }
            }
            for (var i = 0; i < salas.length; i++){
                var estatusSala = salas[i].estatus;
                if (estatusSala == textoBuscado){
                    tablaBusqueda += llenarTablaBusqueda(i);
                }
            }
            for (var i = 0; i < salas.length; i++){
                var descripcionSala = salas[i].descripcion.toLowerCase();
                if (descripcionSala.indexOf(textoBuscadoMinusculas) !== -1){
                    if (salas[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (salas[i].estatus == 1) {
                            tablaBusqueda += llenarTablaBusqueda(i);
                        }
                    }
                }
            }
            break;
        default:
            tablaBusqueda = "";
            break;
    }
    tablaBusqueda += tablaBusquedaInactivos;
    if(tablaBusqueda == ""){
        tablaBusqueda = "<tr><td colspan='7' class='registro text-center'><h4 class='py-4'>No hay resultados de búsqueda</h4></td></tr>";
    }
    $("#datosBusquedaTablaSalas").html(tablaBusqueda);
    actualizarListadoSalas();
}

function llenarTablaBusqueda(i){
    var parteTablaBusqueda = ""
    parteTablaBusqueda += "<tr>";
    parteTablaBusqueda += "<td scope='col' class='id_tabla'>"+salas[i].id+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+salas[i].nombre+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+salas[i].sucursal.nombre+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+salas[i].descripcion+"</td>";
    if(salas[i].estatus == 1){
        parteTablaBusqueda += "<td class='registro' style='background-color: #c6dfc3;'>Activo</td>";
    }else{
        if(salas[i].estatus == 0){
            parteTablaBusqueda += "<td class='registro' style='background-color: #ffa6a6;'>Inactivo</td>";
        }else{
            parteTablaBusqueda += "<td class='registro'>ERROR!!</td>";
        }
    }
    parteTablaBusqueda += "<td class='registro'><button onclick='eliminarSala(" + salas[i].id + ");'><img src='./img/eliminar.svg' alt='Elim.' class='icono'></button></td>";
    parteTablaBusqueda += "<td class='registro'><button onclick='cargarModuloEditarSala(" + salas[i].id + ");'><img src='./img/editar.svg' alt='Edit.' class='icono'></button></td>";
    parteTablaBusqueda += "</tr>";
    return parteTablaBusqueda;
}

function mostrarBusqueda(){
    $("#tablaSalas").hide();
    $("#datosTablaBusquedaSalas").show();
}

function ocultarBusqueda(){
    $("#tablaSalas").show();
    $("#datosTablaBusquedaSalas").hide();
}

