/*
    Proyecto: Sitio web – MySpa                                                         
    Equipo desarrollador: TetraNet
    Desarrollador(es):   Alexis Alfredo Prado Ojeda
    Fecha de inicio: Lunes 19 de julio de 2021
    Versión: 5.0 (12 de noviembre de 2021 - Iván Moisés Ornelas Meza)
    Descripción general del archivo JavaScript: Este archivo JavaScript es el complemento para el uso de la interfaz
    de "gestión de tratamientos", este archivo contiene las instrucciones para su correcto funcionamiento y gestionamiento de 
    tratamientos.
*/

// Listado de tratamientos
var tratamientos;
function extraerTratamientos() {
    tratamientos = null;
    var data = {"tok":sessionStorage.getItem("token")};
    $.ajax({
        "url" : "api/tratamiento/getAll",
        "type" : "GET",
        "async" : false,
        "data" : data
    }
    ).done(function(data){
        if (data.error != null) {
            Swal.fire("Error", data.error, "warning");
        }else{
            tratamientos = data;
        }
    });
}

// Sección de funciones
function eliminarTratamiento(idEnviado) {
    var data = {"id":idEnviado,"tok":sessionStorage.getItem("token")};
    $.ajax({
        "url": "api/tratamiento/delete",
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
    actualizarListadoTratamientos();
    resultadoBusquedaTratamientos();
}

function cargarModuloAgregarTratamiento(){
    $("#seccionGestionarTratamiento").show();
    $("#tablaTratamientos").removeClass("col-md-12");
    $("#tablaTratamientos").addClass("col-md-8");
    $("#datosTablaBusquedaTratamientos").removeClass("col-md-12");
    $("#datosTablaBusquedaTratamientos").addClass("col-md-8");
    $.ajax(
        {
            "type" : "GET",
            "url" : "./modulos/agregarTratamiento.html",
            "async" : true
        }
    ).done(
        function(data){
            var codigoMax = 0;
            for (var i = 0; i < tratamientos.length; i++) {
                if (tratamientos[i].id > codigoMax) {
                    codigoMax = tratamientos[i].id;
                }
            }
            $("#seccionGestionarTratamiento").html(data);
            $("#txtCodigoTratamiento").val(codigoMax+1);
        }
    );
}

function guardarTratamiento(){
    var tratamiento;
    var nombreTrat = $("#txtNombreTratamiento").val();
    var descripcionTrat = $("#txtDescripcionTratamiento").val();
    var costoTrat = $("#txtCostoTratamiento").val();
    var estatusTrat1 = document.getElementById("txtEstatusTratamiento1").checked;
    var estatusTrat2 = document.getElementById("txtEstatusTratamiento2").checked;
    if(estatusTrat1){
        var estatusTrat = 1;
    }else{
        if(estatusTrat2){
            var estatusTrat = 0;
        }
    }
    tratamiento = {"nombre":nombreTrat,"descripcion":descripcionTrat,"costo":costoTrat,"estatus":estatusTrat};
    existenciaTratamiento = false;
    for(var i = 0; i < tratamientos.length; i++){
        if((tratamientos[i].nombre === tratamiento.nombre) && (tratamientos[i].descripcion === tratamiento.descripcion)){
            existenciaTratamiento = true;
        }
    }
    if(existenciaTratamiento){
        Swal.fire("Error", "Esta tratamiento ya existe", "warning");
    }else{
        if(nombreTrat && descripcionTrat && costoTrat){
            var data = {"t": JSON.stringify(tratamiento),"tok":sessionStorage.getItem("token")};
            $.ajax({
                "url": "api/tratamiento/insert",
                "type": "GET",
                "async": true,
                "data": data
            }).done(function (data) {
                if (data.result != null) {
                    Swal.fire("Ok", data.result, "success");
                    limpiarFormularioTratamientos();
                    actualizarListadoTratamientos();
                    resultadoBusquedaTratamientos();
                    cargarModuloAgregarTratamiento();
                } else {
                    if (data.error != null) {
                        Swal.fire("Error", data.error, "warning");
                    }
                }
            });
        }
    }
}

function cargarModuloEditarTratamiento(idEnviado){
    $("#seccionGestionarTratamiento").show();
    $("#tablaTratamientos").removeClass("col-md-12");
    $("#tablaTratamientos").addClass("col-md-8");
    $("#datosTablaBusquedaTratamientos").removeClass("col-md-12");
    $("#datosTablaBusquedaTratamientos").addClass("col-md-8");
    $.ajax(
        {
            "type" : "GET",
            "url" : "./modulos/editarTratamiento.html",
            "async" : true
        }
    ).done(
        function(data){
            for (var i = 0; i < tratamientos.length; i++) {
                var auxId = tratamientos[i].id;
                if (auxId == idEnviado) {
                    var posicionTratamiento = i;
                }
            }
            $("#seccionGestionarTratamiento").html(data);
            $("#txtCodigoTratamiento").val(tratamientos[posicionTratamiento].id);
            $("#txtNombreTratamiento").val(tratamientos[posicionTratamiento].nombre);
            $("#txtDescripcionTratamiento").val(tratamientos[posicionTratamiento].descripcion);
            $("#txtCostoTratamiento").val(tratamientos[posicionTratamiento].costo);
            var activo = document.getElementById("txtEstatusTratamiento1");
            var inactivo = document.getElementById("txtEstatusTratamiento2");
            if(tratamientos[posicionTratamiento].estatus === 1){
                activo.setAttribute("checked",true);
            }else{
                if(tratamientos[posicionTratamiento].estatus === 0){
                    inactivo.setAttribute("checked",true);
                }
            }
        }
    );
}

function editarTratamiento(){
    var tratamiento;
    var codigoTrat = parseInt($("#txtCodigoTratamiento").val());
    var nombreTrat = $("#txtNombreTratamiento").val();
    var descripcionTrat = $("#txtDescripcionTratamiento").val();
    var costoTrat = $("#txtCostoTratamiento").val();
    var estatusTrat1 = document.getElementById("txtEstatusTratamiento1").checked;
    var estatusTrat2 = document.getElementById("txtEstatusTratamiento2").checked;
    if(estatusTrat1){
        var estatusTrat = 1;
    }else{
        if(estatusTrat2){
            var estatusTrat = 0;
        }
    }
    if(nombreTrat && descripcionTrat && costoTrat){
        tratamiento = {"id":codigoTrat,"nombre":nombreTrat,"descripcion":descripcionTrat,"costo":costoTrat,"estatus":estatusTrat};
        var data = {"t": JSON.stringify(tratamiento),"tok":sessionStorage.getItem("token")};
        $.ajax({
            "url": "api/tratamiento/update",
            "type": "GET",
            "async": true,
            "data": data
        }).done(function (data) {
            if (data.result != null) {
                Swal.fire("Ok", data.result, "success");
                actualizarListadoTratamientos();
                resultadoBusquedaTratamientos();
            } else {
                if (data.error != null) {
                    Swal.fire("Error", data.error, "warning");
                }
            }
        });
    }
}

function limpiarFormularioTratamientos(){
    $("#txtNombreTratamiento").val("");
    $("#txtDescripcionTratamiento").val("");
    $("#txtCostoTratamiento").val("");
    $("#txtEstatusTratamiento1").checked = true;
    $("#txtEstatusTratamiento2").checked = false;
}

function regresar(){
    $("#seccionGestionarTratamiento").hide();
    $("#tablaTratamientos").removeClass("col-md-8");
    $("#tablaTratamientos").addClass("col-md-12");
    $("#datosTablaBusquedaTratamientos").removeClass("col-md-8");
    $("#datosTablaBusquedaTratamientos").addClass("col-md-12");
}

function actualizarListadoTratamientos(){
    extraerTratamientos();
    var datosTabla = "";
    var data = {"estatus":1,"tok":sessionStorage.getItem("token")};
    $.ajax({
        "url" : "api/tratamiento/getAllStatus",
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
            datosTabla += "<td class='registro'>" + registrosActivos[i].descripcion + "</td>";
            datosTabla += "<td class='registro'>$" + registrosActivos[i].costo + "</td>";
            if (registrosActivos[i].estatus == 1){
                datosTabla += "<td class='registro' style='background-color: #c6dfc3;'>Activo</td>";
            } else {
                datosTabla += "<td class='registro' style='background-color: #ffa6a6;'>Inactivo</td>";
            }
            datosTabla += "<td class='registro'><button onclick='eliminarTratamiento(" + registrosActivos[i].id + ");'><img src='./img/eliminar.svg' alt='Info.' class='icono'></button></td>";
            datosTabla += "<td class='registro'><button onclick='cargarModuloEditarTratamiento(" + registrosActivos[i].id + ");'><img src='./img/editar.svg' alt='Edit.' class='icono'></button></td>";
            datosTabla += "</tr>";
        }
        $("#datosTablaTratamientos").html(datosTabla);
    });
    
    data = {"estatus":0,"tok":sessionStorage.getItem("token")};
    $.ajax({
        "url": "api/tratamiento/getAllStatus",
        "type": "GET",
        "async": false,
        "data": data
    }).done(function (data) {
        var registrosInactivos = data;
        for (var i = 0; i < registrosInactivos.length; i++)
        {
            datosTabla += "<tr>";
            datosTabla += "<td scope='col' class='id_tabla'>" + registrosInactivos[i].id + "</td>";
            datosTabla += "<td class='registro'>" + registrosInactivos[i].nombre + "</td>";
            datosTabla += "<td class='registro'>" + registrosInactivos[i].descripcion + "</td>";
            datosTabla += "<td class='registro'>$" + registrosInactivos[i].costo + "</td>";
            if (registrosInactivos[i].estatus == 1){
                datosTabla += "<td class='registro' style='background-color: #c6dfc3;'>Activo</td>";
            } else {
                datosTabla += "<td class='registro' style='background-color: #ffa6a6;'>Inactivo</td>";
            }
            datosTabla += "<td class='registro'><button onclick='eliminarTratamiento(" + registrosInactivos[i].id + ");'><img src='./img/eliminar.svg' alt='Info.' class='icono'></button></td>";
            datosTabla += "<td class='registro'><button onclick='cargarModuloEditarTratamiento(" + registrosInactivos[i].id + ");'><img src='./img/editar.svg' alt='Edit.' class='icono'></button></td>";
            datosTabla += "</tr>";
        }
        $("#datosTablaTratamientos").html(datosTabla);
    });
}

function limpiarFormularioBuscar(){
    botonBuscar = document.getElementById("btnBuscar");
    botonBuscar.setAttribute("disabled",true);
    $("#txtBusquedaId").val("");
    $("#txtNombreBusqueda").val("");
    $("#txtDescripcionBusqueda").val("");
    $("#txtCostoMinBusqueda").val("");
    $("#txtCostoMaxBusqueda").val("");
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
        case "descripcion":
            descripcionBuscada = $("#txtDescripcionBusqueda").val();
            habilitarBotonBusquedaCondicion(descripcionBuscada);
            break;
        case "costo":
            costoBuscada = $("#txtCostoBusqueda").val();
            habilitarBotonBusquedaCondicion(costoBuscada);
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
    campoBusquedaCosto = $("#seccionCostoBusqueda");
    campoBusquedaDescripcion = $("#seccionDescripcionBusqueda");
    campoBusquedaEstatus = $("#seccionEstatusBusqueda");

    campoBusquedaId.addClass("tipoBusquedaOculto");
    campoBusquedaNombre.addClass("tipoBusquedaOculto");
    campoBusquedaDescripcion.addClass("tipoBusquedaOculto");
    campoBusquedaCosto.addClass("tipoBusquedaOculto");
    campoBusquedaEstatus.addClass("tipoBusquedaOculto");
    
    switch (tipoDeBusqueda) {
        case "id":
            campoBusquedaId.removeClass("tipoBusquedaOculto");
            break;
        case "nombre":
            campoBusquedaNombre.removeClass("tipoBusquedaOculto");
            break;
        case "descripcion":
            campoBusquedaDescripcion.removeClass("tipoBusquedaOculto");
            break;
        case "costo":
            campoBusquedaCosto.removeClass("tipoBusquedaOculto");
            break;
        case "estatus":
            campoBusquedaEstatus.removeClass("tipoBusquedaOculto");
            break;
        default:
            alert("Error");
            break;
    }
}

function validarPositivo(){
    $("#txtBusquedaId").on("input", function(){
        this.value = this.value.replace(/[^0-9]/g, '').replace(/,/g, '.');
    });
    $("#txtCostoMinBusqueda").on("input", function(){
        this.value = this.value.replace(/[^0-9,.]/g, '').replace(/,/g, '.');
    });
    $("#txtCostoMaxBusqueda").on("input", function(){
        this.value = this.value.replace(/[^0-9,.]/g, '').replace(/,/g, '.');
    });
}
    
function resultadoBusquedaTratamientos(){
    extraerTratamientos();
    var tablaBusqueda = "";
    var tablaBusquedaInactivos = "";
    tipoDeBusqueda = $("#selcTipoDeBusqueda").val();
    switch (tipoDeBusqueda) {
        case "id":
            var valorBuscado = $("#txtBusquedaId").val();
            for (var i = 0; i < tratamientos.length; i++) {
                var idTratamiento = tratamientos[i].id;
                if (idTratamiento == valorBuscado) {
                    tablaBusqueda += llenarTablaBusqueda(i);
                }
            }
            break;
        case "nombre":
            var textoBuscado = $("#txtNombreBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for(var i = 0; i < tratamientos.length; i++){
                var nombreTratamiento = tratamientos[i].nombre.toLowerCase();
                if(nombreTratamiento.indexOf(textoBuscadoMinusculas) !== -1){
                    if (tratamientos[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (tratamientos[i].estatus == 1) {
                            tablaBusqueda += llenarTablaBusqueda(i);
                        }
                    }
                }
            }
            break;
        case "descripcion":
            var textoBuscado = $("#txtDescripcionBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for (var i = 0; i < tratamientos.length; i++){
                var descripcionTratamiento = tratamientos[i].descripcion.toLowerCase();
                if (descripcionTratamiento.indexOf(textoBuscadoMinusculas) !== -1){
                    if (tratamientos[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    }else{
                        if (tratamientos[i].estatus == 1) {
                            tablaBusqueda += llenarTablaBusqueda(i);
                        }
                    }
                }
            }
            break;
        case "costo":
            var valor1 = ($("#txtCostoMinBusqueda").val()) * 1;
            var valor2 = ($("#txtCostoMaxBusqueda").val()) * 1;
            if (valor1 <= valor2){
                var valorInferior = valor1;
                var valorSuperior = valor2;
            } else {
                var valorInferior = valor2;
                var valorSuperior = valor1;
            }
            for (var i = 0; i < tratamientos.length; i++){
                var costoTratamiento = tratamientos[i].costo;
                if (costoTratamiento >= valorInferior && costoTratamiento <= valorSuperior){
                    if (tratamientos[i].estatus == 0) {
                        tablaBusquedaInactivos += llenarTablaBusqueda(i);
                    } else {
                        if (tratamientos[i].estatus == 1) {
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
            for (var i = 0; i < tratamientos.length; i++){
                var estatusTratamiento = tratamientos[i].estatus;
                if (estatusTratamiento == textoBuscado){
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
        tablaBusqueda = "<tr><td colspan='8' class='registro text-center'><h4 class='py-4'>No hay resultados de búsqueda</h4></td></tr>";
    }
    $("#datosBusquedaTablaTratamientos").html(tablaBusqueda);
    actualizarListadoTratamientos();
}

function llenarTablaBusqueda(i){
    var parteTablaBusqueda = ""
    parteTablaBusqueda += "<tr>";
    parteTablaBusqueda += "<td scope='col' class='id_tabla'>"+tratamientos[i].id+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+tratamientos[i].nombre+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+tratamientos[i].descripcion+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+tratamientos[i].costo+"</td>";
    if(tratamientos[i].estatus == 1) {
        parteTablaBusqueda += "<td class='registro' style='background-color: #c6dfc3;'>Activo</td>";
    } else {
        if(tratamientos[i].estatus == 0) {
            parteTablaBusqueda += "<td class='registro' style='background-color: #ffa6a6;'>Inactivo</td>";
        } else {
            parteTablaBusqueda += "<td class='registro'>ERROR!!</td>";
        }
    }
    parteTablaBusqueda += "<td class='registro'><button onclick='eliminarTratamiento("+tratamientos[i].id+");'><img src='./img/eliminar.svg' alt='Elim.' class='icono'></button></td>";
    parteTablaBusqueda += "<td class='registro'><button onclick='cargarModuloEditarTratamiento("+tratamientos[i].id+");'><img src='./img/editar.svg' alt='Edit.' class='icono'></button></td>";
    parteTablaBusqueda += "</tr>";
    return parteTablaBusqueda;
}

function mostrarBusqueda(){
    $("#tablaTratamientos").hide();
    $("#datosTablaBusquedaTratamientos").show();
}

function ocultarBusqueda(){
    $("#tablaTratamientos").show();
    $("#datosTablaBusquedaTratamientos").hide();
}
