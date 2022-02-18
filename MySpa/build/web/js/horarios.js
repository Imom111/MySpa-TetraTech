/*
    Proyecto: Sitio web – MySpa                                                         
    Equipo desarrollador: TetraNet
    Desarrollador(es):   Iván Moisés Ornelas Meza
    Fecha de inicio: Viernes 3 de diciembre de 2021
    Versión: 1.0
    Descripción general del archivo JavaScript: Este archivo JavaScript es el complemento para el uso de la interfaz
    de "gestión de horarios", este archivo contiene las instrucciones para su correcto funcionamiento y gestionamiento de 
    horarios.
*/

// Listado de horarios
var horarios;
function extraerHorarios() {
    horarios = null;
    var data = {"t":sessionStorage.getItem("token")};
    $.ajax({
        "url" : "api/horario/getAll",
        "type" : "GET",
        "async" : false,
        "data" : data
    }
    ).done(function(data){
        if (data.error != null) {
            Swal.fire("Error", data.error, "warning");
        }else{
            horarios = data;
        }
    });
}

// Sección de funciones
function eliminarHorario(idEnviado) {
    var data = {"id":idEnviado,"t":sessionStorage.getItem("token")};
    $.ajax({
        "url": "api/horario/delete",
        "type": "GET",
        "async": true,
        "data": data
    }).done(function (data) {
        if (data.error != null) {
            Swal.fire("Error", data.error, "warning");
        }else{
            Swal.fire("Ok", data.result, "success");
            regresar();
            actualizarListadoHorarios();
            resultadoBusquedaHorarios();
        }
    });
}

function cargarModuloAgregarHorario(){
    $("#seccionGestionarHorario").show();
    $("#tablaHorarios").removeClass("col-md-12");
    $("#tablaHorarios").addClass("col-md-8");
    $("#datosTablaBusquedaHorarios").removeClass("col-md-12");
    $("#datosTablaBusquedaHorarios").addClass("col-md-8");
    $.ajax(
        {
            "type" : "GET",
            "url" : "./modulos/agregarHorario.html",
            "async" : true
        }
    ).done(
        function(data){
            var codigoMax = 0;
            for (var i = 0; i < horarios.length; i++) {
                if (horarios[i].id > codigoMax) {
                    codigoMax = horarios[i].id;
                }
            }
            $("#seccionGestionarHorario").html(data);
            $("#txtCodigoHorarios").val(codigoMax+1);
        }
    );
}

function guardarHorario(){
    var horario;
    var horaInicio = $("#txtHoraInicio").val();
    var horaFin = $("#txtHoraFin").val();
    horario = {"horaInicio":horaInicio,"horaFin":horaFin};
    existenciaHorario = false;
    for(var i = 0; i < horarios.length; i++){
        if((horarios[i].horaInicio === horario.horaInicio) && (horarios[i].horaFin === horarios.horaFin)){
            existenciaHorario = true;
        }
    }
    if(existenciaHorario){
        Swal.fire("Error", "Esta horario ya existe", "warning");
    }else{
        if(horaInicio && horaFin){
            var data = {"h": JSON.stringify(horario),"t":sessionStorage.getItem("token")};
            $.ajax({
                "url": "api/horario/insert",
                "type": "GET",
                "async": true,
                "data": data
            }).done(function (data) {
                if (data.result != null) {
                    Swal.fire("Ok", data.result, "success");
                    limpiarFormularioHorarios();
                    actualizarListadoHorarios();
                    resultadoBusquedaHorarios();
                    cargarModuloAgregarHorario();
                } else {
                    if (data.error != null) {
                        Swal.fire("Error", data.error, "warning");
                    }
                }
            });
        }
    }
}

function cargarModuloEditarHorario(idEnviado){
    $("#seccionGestionarHorario").show();
    $("#tablaHorarios").removeClass("col-md-12");
    $("#tablaHorarios").addClass("col-md-8");
    $("#datosTablaBusquedaHorarios").removeClass("col-md-12");
    $("#datosTablaBusquedaHorarios").addClass("col-md-8");
    $.ajax(
        {
            "type" : "GET",
            "url" : "./modulos/editarHorario.html",
            "async" : true
        }
    ).done(
        function(data){
            for (var i = 0; i < horarios.length; i++) {
                var auxId = horarios[i].id;
                if (auxId == idEnviado) {
                    var posicionHorario = i;
                }
            }
            $("#seccionGestionarHorario").html(data);
            $("#txtCodigoHorario").val(horarios[posicionHorario].id);
            $("#txtHoraInicio").val(horarios[posicionHorario].horaInicio);
            $("#txtHoraFin").val(horarios[posicionHorario].horaFin);
        }
    );
}

function editarHorario(){
    var horario;
    var codigoTrat = parseInt($("#txtCodigoHorario").val());
    var horaInicio = $("#txtHoraInicio").val();
    var horaFin = $("#txtHoraFin").val();
    horario = {"id":codigoTrat,"horaInicio":horaInicio,"horaFin":horaFin};
    if(horaInicio && horaFin){
        var data = {"h": JSON.stringify(horario),"t":sessionStorage.getItem("token")};
        $.ajax({
            "url": "api/horario/update",
            "type": "GET",
            "async": true,
            "data": data
        }).done(function (data) {
            if (data.result != null) {
                Swal.fire("Ok", data.result, "success");
                actualizarListadoHorarios();
                resultadoBusquedaHorarios();
            } else {
                if (data.error != null) {
                    Swal.fire("Error", data.error, "warning");
                }
            }
        });
    }
}

function limpiarFormularioHorarios(){
    $("#txtHoraInicio").val("");
    $("#txtHoraFin").val("");
}

function regresar(){
    $("#seccionGestionarHorario").hide();
    $("#tablaHorarios").removeClass("col-md-8");
    $("#tablaHorarios").addClass("col-md-12");
    $("#datosTablaBusquedaHorarios").removeClass("col-md-8");
    $("#datosTablaBusquedaHorarios").addClass("col-md-12");
}

function actualizarListadoHorarios(){
    extraerHorarios();
    var datosTabla = "";
    for (var i = 0; i < horarios.length; i++){
        datosTabla += "<tr>";
        datosTabla += "<td scope='col' class='id_tabla'>" + horarios[i].id + "</td>";
        datosTabla += "<td class='registro'>" + horarios[i].horaInicio + "</td>";
        datosTabla += "<td class='registro'>" + horarios[i].horaFin + "</td>";
        datosTabla += "<td class='registro'><button onclick='eliminarHorario(" + horarios[i].id + ");'><img src='./img/eliminar.svg' alt='Info.' class='icono'></button></td>";
        datosTabla += "<td class='registro'><button onclick='cargarModuloEditarHorario(" + horarios[i].id + ");'><img src='./img/editar.svg' alt='Edit.' class='icono'></button></td>";
        datosTabla += "</tr>";
    }
    if (horarios.length == 0) {
        datosTabla = "<tr><td colspan='5' class='registro text-center'><h4 class='py-4'>El catálogo de horarios se encuentra vacío</h4></td></tr>";
    }
    $("#datostablaHorarios").html(datosTabla);
}

function limpiarFormularioBuscar(){
    botonBuscar = document.getElementById("btnBuscar");
    botonBuscar.setAttribute("disabled",true);
    $("#txtBusquedaId").val("");
    $("#txtHoraInicio").val("");
    $("#txtHoraFin").val("");
}

function habilitarBotonBusqueda(){
    tipoDeBusqueda = $("#selcTipoDeBusqueda").val();
    botonBuscar = document.getElementById("btnBuscar");
    switch (tipoDeBusqueda) {
        case "id":
            idBuscado = $("#txtBusquedaId").val();
            habilitarBotonBusquedaCondicion(idBuscado);
            break;
        case "horaInicio":
            horaInicioBuscada = $("#txtHoraInicio").val();
            habilitarBotonBusquedaCondicion(horaInicioBuscada);
            break;
        case "horaFin":
            horaFinBuscada = $("#txtHoraFin").val();
            habilitarBotonBusquedaCondicion(horaFinBuscada);
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
    campoBusquedaHoraInicio = $("#seccionHoraInicioBusqueda");
    campoBusquedaHoraFin = $("#seccionHoraFinBusqueda");

    campoBusquedaId.addClass("tipoBusquedaOculto");
    campoBusquedaHoraInicio.addClass("tipoBusquedaOculto");
    campoBusquedaHoraFin.addClass("tipoBusquedaOculto");
    
    switch (tipoDeBusqueda) {
        case "id":
            campoBusquedaId.removeClass("tipoBusquedaOculto");
            break;
        case "horaInicio":
            campoBusquedaHoraInicio.removeClass("tipoBusquedaOculto");
            break;
        case "horaFin":
            campoBusquedaHoraFin.removeClass("tipoBusquedaOculto");
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
}
    
function resultadoBusquedaHorarios(){
    extraerHorarios();
    var tablaBusqueda = "";
    tipoDeBusqueda = $("#selcTipoDeBusqueda").val();
    switch (tipoDeBusqueda) {
        case "id":
            var valorBuscado = $("#txtBusquedaId").val();
            for (var i = 0; i < horarios.length; i++) {
                var idHorario = horarios[i].id;
                if (idHorario == valorBuscado) {
                    tablaBusqueda += llenarTablaBusquedaHorarios(i);
                }
            }
            break;
        case "horaInicio":
            var textoBuscado = $("#txtHoraInicioBusqueda").val();
            for(var i = 0; i < horarios.length; i++){
                var horaInicio = horarios[i].horaInicio;
                if(horaInicio == textoBuscado){
                    tablaBusqueda += llenarTablaBusquedaHorarios(i);
                }
            }
            break;
        case "horaFin":
            var textoBuscado = $("#txtHoraFinBusqueda").val();
            for (var i = 0; i < horarios.length; i++){
                var horaFin = horarios[i].horaFin;
                if (horaFin == textoBuscado){
                    tablaBusqueda += llenarTablaBusquedaHorarios(i);
                }
            }
            break;
        default:
            tablaBusqueda = "";
            break;
        }
    if(tablaBusqueda == ""){
        tablaBusqueda = "<tr><td colspan='5' class='registro text-center'><h4 class='py-4'>No hay resultados de búsqueda</h4></td></tr>";
    }
    $("#datosBusquedatablaHorarios").html(tablaBusqueda);
    actualizarListadoHorarios();
}

function llenarTablaBusquedaHorarios(i){
    var parteTablaBusqueda = ""
    parteTablaBusqueda += "<tr>";
    parteTablaBusqueda += "<td scope='col' class='id_tabla'>"+horarios[i].id+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+horarios[i].horaInicio+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+horarios[i].horaFin+"</td>";
    parteTablaBusqueda += "<td class='registro'><button onclick='eliminarHorario("+horarios[i].id+");'><img src='./img/eliminar.svg' alt='Elim.' class='icono'></button></td>";
    parteTablaBusqueda += "<td class='registro'><button onclick='cargarModuloEditarHorario("+horarios[i].id+");'><img src='./img/editar.svg' alt='Edit.' class='icono'></button></td>";
    parteTablaBusqueda += "</tr>";
    return parteTablaBusqueda;
}

function mostrarBusqueda(){
    $("#tablaHorarios").hide();
    $("#datosTablaBusquedaHorarios").show();
}

function ocultarBusqueda(){
    $("#tablaHorarios").show();
    $("#datosTablaBusquedaHorarios").hide();
}
