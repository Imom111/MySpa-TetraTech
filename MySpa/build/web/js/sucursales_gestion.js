/*
    Proyecto: Sitio web – MySpa                                                         
    Equipo desarrollador: TetraNet
    Desarrollador(es):   Alexis Alfredo Prado Ojeda
    Fecha de inicio: Jueves 28 de octubre de 2021
    Versión: 1.0
    Descripción general del archivo JavaScript: Este archivo JavaScript es el complemento para el uso de la interfaz
    de "gestión de sucursales", este archivo contiene las instrucciones para su correcto funcionamiento y gestionamiento de 
    sucursales.
*/

// Listado de sucursales
var sucursales;
function extraerSucursales(){
    sucursales = null;
    var data = {"t":sessionStorage.getItem("token")};
    $.ajax({
        "url" : "api/sucursal/getAll",
        "type" : "GET",
        "async" : false,
        "data" : data
    }
    ).done(function(data){
        if (data.error != null){
            Swal.fire("Error", data.error, "warning");
        }else{
        sucursales = data;
        }
    });
}

// Sección de funciones
function eliminarSucursal(idEnviado){
    var data = {"id":idEnviado,"t":sessionStorage.getItem("token")};
    $.ajax({
        "url": "api/sucursal/delete",
        "type": "GET",
        "async": true,
        "data": data
    }).done(function (data){
        if (data.error != null) {
            Swal.fire("Error", data.error, "warning");
        }else{
            Swal.fire("Ok", data.result, "success");
        }
    });
    actualizarListadoSucursales();
    resultadoBusquedaSucursal();
}

function cargarModuloAgregarSucursal(){
    $("#seccionGestionarSucursal").show();
    $("#tablaSucursales").removeClass("col-md-12");
    $("#tablaSucursales").addClass("col-md-8");
    $("#datosTablaBusquedaSucursales").removeClass("col-md-12");
    $("#datosTablaBusquedaSucursales").addClass("col-md-8");
    $.ajax(
        {
            "type" : "GET",
            "url" : "./modulos/agregarSucursal.html",
            "async" : true
        }
    ).done(
        function(data){
            var codigoMax = 0;
            for (var i = 0; i < sucursales.length; i++) {
                if (sucursales[i].id > codigoMax) {
                    codigoMax = sucursales[i].id;
                }
            }
            $("#seccionGestionarSucursal").html(data);
            $("#txtCodigoSucursal").val(codigoMax+1);
        }
    );
}

function guardarSucursal(){
    var sucursal = {};
    sucursal.id = ($("#txtCodigoSucursal").val())*1;
    sucursal.nombre = $("#txtNombreSucursal").val();
    sucursal.domicilio = $("#txtDomicilioSucursal").val();
    sucursal.latitud = ($("#txtLatitudSucursal").val())*1;
    sucursal.longitud = ($("#txtLongitudSucursal").val())*1;
    var estatusSuc1 = document.getElementById("txtEstatusSucursal1").checked;
    var estatusSuc2 = document.getElementById("txtEstatusSucursal2").checked;
    if(estatusSuc1){
        sucursal.estatus = 1;
    }else if(estatusSuc2){
        sucursal.estatus = 0;
    }
    existenciaSucursal = false;
    for(var i = 0; i < sucursales.length; i++){
        if((sucursales[i].nombre == sucursal.nombre) && (sucursales[i].domicilio == sucursal.domicilio)){
            existenciaSucursal = true;
        }
    }
    if(existenciaSucursal){
        Swal.fire("Error", "Esta sucursal ya existe", "warning");
    }else{
        if(sucursal.nombre && sucursal.domicilio && sucursal.latitud && sucursal.longitud){
            var data = {"s": JSON.stringify(sucursal),"t":sessionStorage.getItem("token")};
            $.ajax({
                "url" : "api/sucursal/insert",
                "type" : "GET",
                "async" : true,
                "data" : data
            }).done(function(data){
                if (data.idGerado != null) {
                    Swal.fire("Ok", data.result, "success");
                    limpiarFormularioSucursales();
                    actualizarListadoSucursales();
                    resultadoBusquedaSucursal();
                    cargarModuloAgregarSucursal();
                } else {
                    if (data.error != null) {
                        Swal.fire("Error", data.error, "warning");
                    }
                }
            });
        }
    }
}

function cargarModuloEditarSucursal(idEnviado){
    $("#seccionGestionarSucursal").show();
    $("#tablaSucursales").removeClass("col-md-12");
    $("#tablaSucursales").addClass("col-md-8");
    $("#datosTablaBusquedaSucursales").removeClass("col-md-12");
    $("#datosTablaBusquedaSucursales").addClass("col-md-8");
    $.ajax(
        {
            "type" : "GET",
            "url" : "./modulos/editarSucursal.html",
            "async" : true
        }
    ).done(
        function(data){
            for (var i = 0; i < sucursales.length; i++) {
                var auxId = sucursales[i].id;
                if (auxId == idEnviado) {
                    var posicionSucursal = i;
                }
            }
            $("#seccionGestionarSucursal").html(data);
            $("#txtCodigoSucursal").val(sucursales[posicionSucursal].id);
            $("#txtNombreSucursal").val(sucursales[posicionSucursal].nombre);
            $("#txtDomicilioSucursal").val(sucursales[posicionSucursal].domicilio);
            $("#txtLatitudSucursal").val(sucursales[posicionSucursal].latitud);
            $("#txtLongitudSucursal").val(sucursales[posicionSucursal].longitud);
            var estatusActivo = document.getElementById("txtEstatusSucursal1");
            var estatusInactivo = document.getElementById("txtEstatusSucursal2");
            if(sucursales[posicionSucursal].estatus == 1){
                estatusActivo.setAttribute("checked",true);
            }else if(sucursales[posicionSucursal].estatus == 0){
                estatusInactivo.setAttribute("checked",true);
            }
        }
    );
}

function editarSucursal(){
    var sucursal = {};
    sucursal.id = ($("#txtCodigoSucursal").val())*1;
    sucursal.nombre = $("#txtNombreSucursal").val();
    sucursal.domicilio = $("#txtDomicilioSucursal").val();
    sucursal.latitud = ($("#txtLatitudSucursal").val())*1;
    sucursal.longitud = ($("#txtLongitudSucursal").val())*1;
    var estatusSuc1 = document.getElementById("txtEstatusSucursal1").checked;
    var estatusSuc2 = document.getElementById("txtEstatusSucursal2").checked;
    if(estatusSuc1){
        sucursal.estatus = 1;
    }else if(estatusSuc2){
        sucursal.estatus = 0;
    }
    if(sucursal.nombre && sucursal.domicilio && sucursal.latitud && sucursal.longitud){
        var data = {"s": JSON.stringify(sucursal),"t":sessionStorage.getItem("token")};
        $.ajax({
            "url": "api/sucursal/update",
            "type": "GET",
            "async": true,
            "data": data
        }).done(function (data) {
            if (data.result != null) {
                Swal.fire("Ok", data.result, "success");
                actualizarListadoSucursales();
                resultadoBusquedaSucursal();
            } else {
                if (data.error != null) {
                    Swal.fire("Error", data.error, "warning");
                }
            }
        });
    }
}

function limpiarFormularioSucursales(){
    $("#txtCodigoSucursal").val(sucursales.length);
    $("#txtNombreSucursal").val("");
    $("#txtDomicilioSucursal").val("");
    $("#txtLatitudSucursal").val("");
    $("#txtLongitudSucursal").val("");
    $("#txtEstatusSucursal1").checked = true;
    $("#txtEstatusSucursal2").checked = false;
}

function regresar(){
    $("#seccionGestionarSucursal").hide();
    $("#datosTablaBusquedaSucursales").removeClass("col-md-8");
    $("#datosTablaBusquedaSucursales").addClass("col-md-12");
    $("#tablaSucursales").removeClass("col-md-8");
    $("#tablaSucursales").addClass("col-md-12");
}

function actualizarListadoSucursales(){
    extraerSucursales();
    var datosTablaRes = "";
    var data = {"estatus":1,"t":sessionStorage.getItem("token")};
    $.ajax({
        "url" : "api/sucursal/getAllStatus",
        "type" : "GET",
        "async" : false,
        "data" : data
    }
    ).done(function(data){
        var sucursalesActivas = data;
        for (var i = 0; i < sucursalesActivas.length; i++)
        {
            datosTablaRes += "<tr>";
            datosTablaRes += "<td scope='col' class='id_tabla'>" + sucursalesActivas[i].id + "</td>";
            datosTablaRes += "<td class='registro'>" + sucursalesActivas[i].nombre + "</td>";
            datosTablaRes += "<td class='registro'>" + sucursalesActivas[i].domicilio + "</td>";
            datosTablaRes += "<td class='registro'>" + sucursalesActivas[i].latitud + "</td>";
            datosTablaRes += "<td class='registro'>" + sucursalesActivas[i].longitud + "</td>";
            if (sucursalesActivas[i].estatus == 1)
            {
                datosTablaRes += "<td class='registro' style='background-color: #c6dfc3;'>Activo</td>";
            } else
            {
                datosTablaRes += "<td class='registro' style='background-color: #ffa6a6;'>Inactivo</td>";
            }
            datosTablaRes += "<td class='registro'><button onclick='eliminarSucursal(" + sucursalesActivas[i].id + ");'><img src='./img/eliminar.svg' alt='Info.' class='icono'></button></td>";
            datosTablaRes += "<td class='registro'><button onclick='cargarModuloEditarSucursal(" + sucursalesActivas[i].id + ");'><img src='./img/editar.svg' alt='Edit.' class='icono'></button></td>";
            datosTablaRes += "</tr>";
        }
        $("#datostablaSucursales").html(datosTablaRes);
    });
    
    data = {"estatus":0,"t":sessionStorage.getItem("token")};
    $.ajax({
        "url": "api/sucursal/getAllStatus",
        "type": "GET",
        "async": false,
        "data": data
    }).done(function (data){
        var sucursalesInactivas = data;
        for (var i = 0; i < sucursalesInactivas.length; i++)
        {
            datosTablaRes += "<tr>";
            datosTablaRes += "<td scope='col' class='id_tabla'>" + sucursalesInactivas[i].id + "</td>";
            datosTablaRes += "<td class='registro'>" + sucursalesInactivas[i].nombre + "</td>";
            datosTablaRes += "<td class='registro'>" + sucursalesInactivas[i].domicilio + "</td>";
            datosTablaRes += "<td class='registro'>" + sucursalesInactivas[i].latitud + "</td>";
            datosTablaRes += "<td class='registro'>" + sucursalesInactivas[i].longitud + "</td>";
            if (sucursalesInactivas[i].estatus == 1)
            {
                datosTablaRes += "<td class='registro' style='background-color: #c6dfc3;'>Activo</td>";
            } else
            {
                datosTablaRes += "<td class='registro' style='background-color: #ffa6a6;'>Inactivo</td>";
            }
            datosTablaRes += "<td class='registro'><button onclick='eliminarSucursal(" + sucursalesInactivas[i].id + ");'><img src='./img/eliminar.svg' alt='Info.' class='icono'></button></td>";
            datosTablaRes += "<td class='registro'><button onclick='cargarModuloEditarSucursal(" + sucursalesInactivas[i].id + ");'><img src='./img/editar.svg' alt='Edit.' class='icono'></button></td>";
            datosTablaRes += "</tr>";
        }
        $("#datostablaSucursales").html(datosTablaRes);
    });
}
function limpiarFormularioBuscar(){
    botonBuscar = document.getElementById("btnBuscar");
    botonBuscar.setAttribute("disabled",true);
    $("#txtBusquedaId").val("");
    $("#txtNombreBusqueda").val("");
    $("#txtDomicilioBusqueda").val("");
    $("#txtLatitudBusqueda").val("");
    $("#txtLongitudBusqueda").val("");
    document.querySelectorAll('[name=txtEstatusBusqueda]').forEach((x) => x.checked = false);
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
            nombreSucursalBuscada = $("#txtNombreBusqueda").val();
            habilitarBotonBusquedaCondicion(nombreSucursalBuscada);
            break;
        case "domicilio":
            domicilioBuscado = $("#txtDomicilioBusqueda").val();
            habilitarBotonBusquedaCondicion(domicilioBuscado);
            break;
        case "coordenadas":
            latitudBuscada = $("#txtLatitudBusqueda").val();
            longitudBuscada = $("#txtLongitudBusqueda").val();
            if (latitudBuscada && longitudBuscada) {
                habilitarBotonBusquedaCondicion("texto");
            }else{
                habilitarBotonBusquedaCondicion("");
            }
            break;
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
    campoBusquedaNombre = $("#seccionNombreBusqueda");
    campoBusquedaDomicilio = $("#seccionDomicilioBusqueda");
    campoBusquedaCoordenada = $("#seccionCoordenadaBusqueda");
    campoBusquedaEstatus = $("#seccionEstatusBusqueda");

    campoBusquedaId.addClass("tipoBusquedaOculto");
    campoBusquedaNombre.addClass("tipoBusquedaOculto");
    campoBusquedaDomicilio.addClass("tipoBusquedaOculto");
    campoBusquedaCoordenada.addClass("tipoBusquedaOculto");
    campoBusquedaEstatus.addClass("tipoBusquedaOculto");
    switch (tipoDeBusqueda) {
        case "id":
            campoBusquedaId.removeClass("tipoBusquedaOculto");
            break;
        case "nombre":
            campoBusquedaNombre.removeClass("tipoBusquedaOculto");
            break;
        case "domicilio":
            campoBusquedaDomicilio.removeClass("tipoBusquedaOculto");
            break;
        case "coordenadas":
            campoBusquedaCoordenada.removeClass("tipoBusquedaOculto");
            break;
        case "estatus":
            campoBusquedaEstatus.removeClass("tipoBusquedaOculto");
            break;
        default:
            alert("Error al habilitar los campos de búsqueda");
            break;
    }
}

function resultadoBusquedaSucursal(){
    extraerSucursales();
    var tablaBusqueda = "";
    var tablaBusquedaInactivos = "";
    tipoDeBusqueda = $("#selcTipoDeBusqueda").val();
    switch (tipoDeBusqueda) {
        case "id":
            var valorBuscado = $("#txtBusquedaId").val();
            for (var i = 0; i < sucursales.length; i++){
                var idSucursal = sucursales[i].id;
                if (idSucursal == valorBuscado){
                    tablaBusqueda += llenarTablaBusquedaSucursales(i);
                }
            }
            break;
        case "nombre":
            var textoBuscado = $("#txtNombreBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for(var i = 0; i < sucursales.length; i++){
                var clienteSucursal = sucursales[i].nombre.toLowerCase();
                if(clienteSucursal.indexOf(textoBuscadoMinusculas) !== -1){
                    if (sucursales[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (sucursales[i].estatus == 1) {
                            tablaBusqueda += llenarTablaBusquedaSucursales(i);
                        }
                    }
                }
            }
            break;
        case "domicilio":
            var textoBuscado = $("#txtDomicilioBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for (var i = 0; i < sucursales.length; i++){
                var nombreSucursal = sucursales[i].domicilio.toLowerCase();
                if (nombreSucursal.indexOf(textoBuscadoMinusculas) !== -1){
                    if (sucursales[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (sucursales[i].estatus == 1) {
                            tablaBusqueda += llenarTablaBusquedaSucursales(i);
                        }
                    }
                }
            }
            break;
        case "coordenadas":
            var longitudBuscado = $("#txtLongitudBusqueda").val();
            var latitudBuscado = $("#txtLatitudBusqueda").val();
            for (var i = 0; i < sucursales.length; i++){
                var longitudSucursal = sucursales[i].longitud;
                var latitudSucursal = sucursales[i].latitud;
                if ((longitudSucursal == longitudBuscado) && (latitudSucursal == latitudBuscado)){
                    if (sucursales[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (sucursales[i].estatus == 1) {
                            tablaBusqueda += llenarTablaBusquedaSucursales(i);
                        }
                    }
                }
            }
            break;
        case "estatus":
            var estatusActivoBuscado = document.getElementById("txtEstatusBusqueda1").checked;
            var estatusInactivoBuscado = document.getElementById("txtEstatusBusqueda2").checked;
            if (estatusActivoBuscado){
                var textoBuscado = 1;
            }else{
                if (estatusInactivoBuscado){
                    var textoBuscado = 0;
                }
            }
            for (var i = 0; i < sucursales.length; i++){
                var estatusSucursal = sucursales[i].estatus;
                if (estatusSucursal == textoBuscado){
                    tablaBusqueda += llenarTablaBusquedaSucursales(i);
                }
            }
        break;
        default:
            tablaBusqueda = "";
            break;
    }
    tablaBusqueda += tablaBusquedaInactivos;
    if(tablaBusqueda == ""){
        tablaBusqueda = "<tr><td colspan='8' class='registro text-center'><h4 class='py-4'>No hay resultados de búsqueda</h4></td></tr>";
    }
    $("#datosBusquedatablaSucursales").html(tablaBusqueda);
    actualizarListadoSucursales();
}
function llenarTablaBusquedaSucursales(i){
    var parteTablaBusqueda = "";
    parteTablaBusqueda += "<tr>";
    parteTablaBusqueda += "<td scope='col' class='id_tabla'>"+sucursales[i].id+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+sucursales[i].nombre+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+sucursales[i].domicilio+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+sucursales[i].latitud+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+sucursales[i].longitud+"</td>";
    if (sucursales[i].estatus == 1){
        parteTablaBusqueda += "<td class='registro' style='background-color: #c6dfc3;'>Activo</td>";
    }else{
        parteTablaBusqueda += "<td class='registro' style='background-color: #ffa6a6;'>Inactivo</td>";
    }
    parteTablaBusqueda += "<td class='registro'><button onclick='eliminarSucursal("+sucursales[i].id+");'><img src='./img/eliminar.svg' alt='Info.' class='icono'></button></td>";
    parteTablaBusqueda += "<td class='registro'><button onclick='cargarModuloEditarSucursal("+sucursales[i].id+");'><img src='./img/editar.svg' alt='Edit.' class='icono'></button></td>";
    parteTablaBusqueda += "</tr>";
    return parteTablaBusqueda;
}

function mostrarBusqueda(){
    $("#tablaSucursales").hide();
    $("#datosTablaBusquedaSucursales").show();
}

function ocultarBusqueda(){
    $("#tablaSucursales").show();
    $("#datosTablaBusquedaSucursales").hide();
}
