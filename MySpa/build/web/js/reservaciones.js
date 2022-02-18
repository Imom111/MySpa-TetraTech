/*
    Proyecto: Sitio web – MySpa                                                         
    Equipo desarrollador: TetraNet
    Desarrollador(es):   Víctor Manuel Fonseca Muñoz
    Fecha de inicio: Lunes 19 de julio de 2021
    Versión: 5.0
    Descripción general del archivo JavaScript: Este archivo JavaScript es el complemento para el uso de la interfaz
    de "gestión de reservaciones", este archivo contiene las instrucciones para su correcto funcionamiento y gestionamiento de 
    reservaciones.
*/

// Listado de reservaciones por defecto
var reservaciones = [
    {
        "id" : 1,
        "cliente" : "Jennifer Margarita",
        "tratamiento" : "Batido natural",
        "empleado" : "Margarita de Jesús",
        "sucursal" : "León boulevard Hidalgo 4009",
        "sala" : "Aqua",
        "horario" : "12:00:00-14:00:00",
        "fecha" : "2021-09-01",
        "estatus" : "Realizada"
    },
    {
        "id" : 2,
        "cliente" : "Marisol",
        "tratamiento" : "Aromaterapia",
        "empleado" : "Denisse",
        "sucursal" : "León del Moral 658",
        "sala" : "Flamma",
        "horario" : "08:00:00-10:00:00",
        "fecha" : "2021-10-01",
        "estatus" : "Pendiente"
    }
];

// Sección de funciones
function fechas()
{
    var fecha = new Date();
    var anio = fecha.getFullYear();
    var mes = 1+(fecha.getMonth());
    if(mes < 10){
        var mesCeros = "0"+mes;
    }
    else
    {
        var mesCeros = mes;
    }
    var dia = (fecha.getDate());
    if(dia < 10)
    {
        var diaCeros = "0"+dia;
    }
    else
    {
        var diaCeros = dia;
    }
    var hoyFecha = anio+"-"+mesCeros+"-"+diaCeros;
    return hoyFecha;
}
function cargarModuloInformarReservacion(id_reservacion)
{
    $("#seccionGestionarReservacion").show();
    $("#tablaReservaciones").removeClass("col-md-12");
    $("#tablaReservaciones").addClass("col-md-8");
    $("#datosTablaBusquedaReservaciones").removeClass("col-md-12");
    $("#datosTablaBusquedaReservaciones").addClass("col-md-8");
    $.ajax(
        {
            "type" : "GET",
            "url" : "./modulos/informarReservacion.html",
            "async" : true
        }
    ).done(
        function(data)
        {
            $("#seccionGestionarReservacion").html(data);
            var posicionReservacion = id_reservacion-1;
            $("#txtCodigoReser").val(reservaciones[posicionReservacion].id);
            $("#txtClienteReser").val(reservaciones[posicionReservacion].cliente);
            $("#txtTratamientoReser").val(reservaciones[posicionReservacion].tratamiento);
            $("#txtEmpleadoReser").val(reservaciones[posicionReservacion].empleado);
            $("#txtSucursalReser").val(reservaciones[posicionReservacion].sucursal);
            $("#txtSalaReser").val(reservaciones[posicionReservacion].sala);
            $("#txtHorarioReser").val(reservaciones[posicionReservacion].horario);
            $("#txtFechaReser").val(reservaciones[posicionReservacion].fecha);
            $("#txtEstatusReser").val(reservaciones[posicionReservacion].estatus);
        }
    );  
}
function cargarModuloAgregarReservacion()
{
    $("#seccionGestionarReservacion").show();
    $("#tablaReservaciones").removeClass("col-md-12");
    $("#tablaReservaciones").addClass("col-md-8");
    $("#datosTablaBusquedaReservaciones").removeClass("col-md-12");
    $("#datosTablaBusquedaReservaciones").addClass("col-md-8");
    $.ajax(
        {
            "type" : "GET",
            "url" : "./modulos/agregarReservacion.html",
            "async" : true
        }
    ).done(
        function(data)
        {
            $("#seccionGestionarReservacion").html(data);
            $("#txtCodigoReser").val(reservaciones.length+1);
        }
    );
}
function guardarReservacion()
{
    var codigoRes = parseInt($("#txtCodigoReser").val());
    var clienteRes = $("#txtClienteReser").val();
    var tratamientoRes = $("#txtTratamientoReser").val();
    var empleadoRes = $("#txtEmpleadoReser").val();
    var sucursalRes = $("#txtSucursalReser").val();
    var salaRes = $("#txtSalaReser").val();
    var fechaRes = $("#txtFechaReser").val();
    var horarioRes = $("#txtHorarioReser").val();
    var estatusRes1 = document.getElementById("txtEstatusReser1").checked;
    var estatusRes2 = document.getElementById("txtEstatusReser2").checked;
    var estatusRes3 = document.getElementById("txtEstatusReser3").checked;
    if(estatusRes1)
    {
        var estatusRes = "Pendiente";
    }
    else
    {
        if(estatusRes2)
        {
            var estatusRes = "Realizada";
        }
        else
        {
            if(estatusRes3)
            {
                var estatusRes = "Cancelada";
            }
        }
    }
    var reservacionNueva =
    {
        "id" : codigoRes,
        "cliente" : clienteRes,
        "tratamiento" : tratamientoRes,
        "empleado" : empleadoRes,
        "sucursal" : sucursalRes,
        "sala" : salaRes,
        "fecha" : fechaRes,
        "horario" : horarioRes,
        "estatus" : estatusRes,
    };
    existenciaReservacion = false;
    for(var i = 0; i < reservaciones.length; i++)
    {
        if((reservaciones[i].cliente === reservacionNueva.cliente) && (reservaciones[i].horario === reservacionNueva.horario) && (reservaciones[i].fecha === reservacionNueva.fecha))
        {
            existenciaReservacion = true;
        }
    }
    if(existenciaReservacion)
    {
        let timerInterval;
        Swal.fire(
        {
            title: "Esta reservación ya existe",
            timer: 1500,
            timerProgressBar: true,
            didOpen: () =>
            {
                Swal.showLoading()
                timerInterval = setInterval(() =>
                {
                    const content = Swal.getHtmlContainer()
                    if(content)
                    {
                        const b = content.querySelector("b");
                        if(b)
                        {
                            b.textContent = Swal.getTimerLeft();
                        }
                    }
                }, 100);
            },
            willClose: () =>
            {
                clearInterval(timerInterval);
            }
        });
    }
    else
    {
        if(codigoRes && clienteRes && tratamientoRes && empleadoRes && sucursalRes && salaRes && horarioRes && estatusRes)
        {
            reservaciones.push(reservacionNueva);
            limpiarFormularioReservaciones();
            cargarModuloAgregarReservacion();
            let timerInterval;
            Swal.fire(
            {
                title: "Datos guardados exitosamente",
                timer: 1500,
                timerProgressBar: true,
                didOpen: () =>
                {
                    Swal.showLoading()
                    timerInterval = setInterval(() =>
                    {
                        const content = Swal.getHtmlContainer()
                        if(content)
                        {
                            const b = content.querySelector("b");
                            if(b)
                            {
                                b.textContent = Swal.getTimerLeft();
                            }
                        }
                    }, 100);
                },
                willClose: () =>
                {
                    clearInterval(timerInterval);
                }
            });
        }
        actualizarListadoReservaciones();
        resultadoBusquedaReservacion();
    }
    /*
    La siguiente linea no es funcional en su totalidad,
    falta conectar la base de datos al sitio web.
    Swal.fire("Inserción correcta", "Reservacion almacenada", "success");
     */
}
function cargarModuloEditarReservacion(id_reservacion)
{
    $("#seccionGestionarReservacion").show();
    $("#tablaReservaciones").removeClass("col-md-12");
    $("#tablaReservaciones").addClass("col-md-8");
    $("#datosTablaBusquedaReservaciones").removeClass("col-md-12");
    $("#datosTablaBusquedaReservaciones").addClass("col-md-8");
    $.ajax(
        {
            "type" : "GET",
            "url" : "./modulos/editarReservacion.html",
            "async" : true
        }
    ).done(
        function(data)
        {
            $("#seccionGestionarReservacion").html(data);
            var posicionReservacion = id_reservacion-1;
            $("#txtCodigoReser").val(reservaciones[posicionReservacion].id);
            $("#txtClienteReser").val(reservaciones[posicionReservacion].cliente);
            $("#txtTratamientoReser").val(reservaciones[posicionReservacion].tratamiento);
            $("#txtEmpleadoReser").val(reservaciones[posicionReservacion].empleado);
            $("#txtSucursalReser").val(reservaciones[posicionReservacion].sucursal);
            $("#txtSalaReser").val(reservaciones[posicionReservacion].sala);
            $("#txtFechaReser").val(reservaciones[posicionReservacion].fecha);
            $("#txtHorarioReser").val(reservaciones[posicionReservacion].horario);
            var posicionReservacion = id_reservacion-1;
            var pendiente = document.getElementById("txtEstatusReser1");
            var realizada = document.getElementById("txtEstatusReser2");
            var cancelada = document.getElementById("txtEstatusReser3");
            if(reservaciones[posicionReservacion].estatus === "Pendiente")
            {
                pendiente.setAttribute("checked",true);
            }
            else
            {
                if(reservaciones[posicionReservacion].estatus === "Realizada")
                {
                    realizada.setAttribute("checked",true);
                }
                else
                {
                    if(reservaciones[posicionReservacion].estatus === "Cancelada")
                    {
                        cancelada.setAttribute("checked",true);
                    }
                }
            }
        }
    );
}
function editarReservacion()
{
    var codigoRes = parseInt($("#txtCodigoReser").val());
    var clienteRes = $("#txtClienteReser").val();
    var tratamientoRes = $("#txtTratamientoReser").val();
    var empleadoRes = $("#txtEmpleadoReser").val();
    var sucursalRes = $("#txtSucursalReser").val();
    var salaRes = $("#txtSalaReser").val();
    var fechaRes = $("#txtFechaReser").val();
    var horarioRes = $("#txtHorarioReser").val();
    var estatusRes1 = document.getElementById("txtEstatusReser1").checked;
    var estatusRes2 = document.getElementById("txtEstatusReser2").checked;
    var estatusRes3 = document.getElementById("txtEstatusReser3").checked;
    if(estatusRes1)
    {
        var estatusRes = "Pendiente";
    }
    else
    {
        if(estatusRes2)
        {
            var estatusRes = "Realizada";
        }
        else
        {
            if(estatusRes3)
            {
                var estatusRes = "Cancelada";
            }
        }
    }
    if(codigoRes && clienteRes && tratamientoRes && empleadoRes && sucursalRes && salaRes && horarioRes && estatusRes)
    {
        var posicionReservacion = codigoRes-1;
        reservaciones[posicionReservacion].id = codigoRes;
        reservaciones[posicionReservacion].cliente = clienteRes;
        reservaciones[posicionReservacion].tratamiento = tratamientoRes;
        reservaciones[posicionReservacion].empleado = empleadoRes;
        reservaciones[posicionReservacion].sucursal = sucursalRes;
        reservaciones[posicionReservacion].sala = salaRes;
        reservaciones[posicionReservacion].horario = horarioRes;
        reservaciones[posicionReservacion].fecha = fechaRes;
        reservaciones[posicionReservacion].estatus = estatusRes;
        actualizarListadoReservaciones();
        let timerInterval;
        Swal.fire(
        {
            title: "Datos guardados exitosamente",
            timer: 1500,
            timerProgressBar: true,
            didOpen: () =>
            {
                Swal.showLoading()
                timerInterval = setInterval(() =>{
                    const content = Swal.getHtmlContainer()
                    if(content)
                    {
                        const b = content.querySelector("b");
                        if(b)
                        {
                            b.textContent = Swal.getTimerLeft();
                        }
                    }
                }, 100);
            },
            willClose: () =>
            {
                clearInterval(timerInterval);
            }
        });
        cargarModuloEditarReservacion(codigoRes);
        resultadoBusquedaReservacion();
    }
    /*
    La siguiente linea no es funcional en su totalidad,
    falta conectar la base de datos al sitio web.
    Swal.fire("Inserción correcta", "Reservacion almacenado", "success");
     */
}
function validarPositivo()
{
    $("#txtBusquedaId").on("input", function()
    {
        this.value = this.value.replace(/[^0-9]/g, '').replace(/,/g, '.');
    });
}
function validarFecha()
{
    fechaLimite = fechas();
    var inputFecha = document.getElementById("txtFechaReser");;
    inputFecha.setAttribute("min",fechaLimite);
}
function limpiarFormularioReservaciones()
{
    $("#txtCodigoReser").val(reservaciones.length);
    $("#txtClienteReser").val("");
    $("#txtTratamientoReser").val("");
    $("#txtEmpleadoReser").val("");
    $("#txtSucursalReser").val("");
    $("#txtSalaReser").val("");
    $("#txtHorarioReser").val("");
    $("#txtEstatusReser1").checked = true;
    $("#txtEstatusReser2").checked = false;
    $("#txtEstatusReser3").checked = false;
}
function regresar()
{
    $("#seccionGestionarReservacion").hide();
    $("#datosTablaBusquedaReservaciones").removeClass("col-md-8");
    $("#datosTablaBusquedaReservaciones").addClass("col-md-12");
    $("#tablaReservaciones").removeClass("col-md-8");
    $("#tablaReservaciones").addClass("col-md-12");
}
function actualizarListadoReservaciones()
{
    datosTablaRes = "";
    for(var i = (reservaciones.length)-1; i >= 0; i--)
    {
        datosTablaRes += "<tr>";
        datosTablaRes += "<td scope='col' class='id_tabla'>"+reservaciones[i].id+"</td>";
        datosTablaRes += "<td class='registro'>"+reservaciones[i].tratamiento+"</td>";
        datosTablaRes += "<td class='registro'>"+reservaciones[i].sucursal+"</td>";
        datosTablaRes += "<td class='registro'>"+reservaciones[i].sala+"</td>";
        datosTablaRes += "<td class='registro'>"+reservaciones[i].horario+"</td>";
        var fechaGuardada = reservaciones[i].fecha;
        fechaGuardada.split();
        var fechaMostrada = fechaGuardada[8]+fechaGuardada[9]+"/"+fechaGuardada[5]+fechaGuardada[6]+"/"+fechaGuardada[0]+fechaGuardada[1]+fechaGuardada[2]+fechaGuardada[3];
        datosTablaRes += "<td class='registro'>"+fechaMostrada+"</td>";
        if(reservaciones[i].estatus == "Pendiente")
        {
            datosTablaRes += "<td class='registro' style='background-color: #f3e13dc9;'>"+reservaciones[i].estatus+"</td>";
        }
        else
        {
            if(reservaciones[i].estatus == "Realizada")
            {
                datosTablaRes += "<td class='registro' style='background-color: #c6dfc3;'>"+reservaciones[i].estatus+"</td>";
            }
            else
            {
                if(reservaciones[i].estatus == "Cancelada")
                {
                    datosTablaRes += "<td class='registro' style='background-color: #ffa6a6;'>"+reservaciones[i].estatus+"</td>";
                }
                else
                {
                    datosTablaRes += "<td class='registro'>ERROR!!</td>";
                }
            }
        }
        datosTablaRes += "<td class='registro'><button onclick='cargarModuloInformarReservacion("+reservaciones[i].id+");'><img src='./img/info.svg' alt='Info.' class='icono'></button></td>";
        datosTablaRes += "<td class='registro'><button onclick='cargarModuloEditarReservacion("+reservaciones[i].id+");'><img src='./img/editar.svg' alt='Edit.' class='icono'></button></td>";
        datosTablaRes += "</tr>";
    }
    $("#datostablaReservaciones").html(datosTablaRes);
}
function limpiarFormularioBuscar()
{
    botonBuscar = document.getElementById("btnBuscar");
    botonBuscar.setAttribute("disabled",true);
    $("#txtBusquedaId").val("");
    $("#txtClienteBusqueda").val("");
    $("#txtTratamientoBusqueda").val("");
    $("#txtEmpleadoBusqueda").val("");
    $("#txtSucursalBusqueda").val("");
    $("#txtSalaBusqueda").val("");
    $("#txtFechaBusqueda").val("");
    $("#txtHorarioBusqueda").val("");
    document.querySelectorAll('[name=txtBusquedaEstatus]').forEach((x) => x.checked = false);
}
function habilitarBotonBusqueda()
{
    tipoDeBusqueda = $("#selcTipoDeBusqueda").val();
    botonBuscar = document.getElementById("btnBuscar");
    if(tipoDeBusqueda == "id")
    {
        idBuscado = $("#txtBusquedaId").val();
        if(idBuscado != "")
        {
            botonBuscar.removeAttribute("disabled");
        }
        else
        {
            botonBuscar.setAttribute("disabled",true);
        }
    }
    else
    {
        if(tipoDeBusqueda == "cliente")
        {
            clienteBuscado = $("#txtClienteBusqueda").val();
            if(clienteBuscado != "")
            {
                botonBuscar.removeAttribute("disabled");
            }
            else
            {
                botonBuscar.setAttribute("disabled",true);
            }
        }
        else
        {
            if(tipoDeBusqueda == "tratamiento")
            {
                tratamientoBuscado = $("#txtTratamientoBusqueda").val();
                if(tratamientoBuscado != "")
                {
                    botonBuscar.removeAttribute("disabled");
                }
                else
                {
                    botonBuscar.setAttribute("disabled",true);
                }
            }
            else
            {
                if(tipoDeBusqueda == "empleado")
                {
                    empleadoBuscado = $("#txtEmpleadoBusqueda").val();
                    if(empleadoBuscado != "")
                    {
                        botonBuscar.removeAttribute("disabled");
                    }
                    else
                    {
                        botonBuscar.setAttribute("disabled",true);
                    }
                }
                else
                {
                    if(tipoDeBusqueda == "sucursal")
                    {
                        sucursalBuscado = $("#txtSucursalBusqueda").val();
                        if(sucursalBuscado != "")
                        {
                            botonBuscar.removeAttribute("disabled");
                        }
                        else
                        {
                            botonBuscar.setAttribute("disabled",true);
                        }
                    }
                    else
                    {
                        if(tipoDeBusqueda == "sala")
                        {
                            salaBuscado = $("#txtSalaBusqueda").val();
                            if(salaBuscado != "")
                            {
                                botonBuscar.removeAttribute("disabled");
                            }
                            else
                            {
                                botonBuscar.setAttribute("disabled",true);
                            }
                        }
                        else
                        {
                            if(tipoDeBusqueda == "fecha")
                            {
                                fechaBuscado = $("#txtFechaBusqueda").val();
                                if(fechaBuscado != "")
                                {
                                    botonBuscar.removeAttribute("disabled");
                                }
                                else
                                {
                                    botonBuscar.setAttribute("disabled",true);
                                }
                            }
                            else
                            {
                                if(tipoDeBusqueda == "horario")
                                {
                                    horarioBuscado = $("#txtHorarioBusqueda").val();
                                    if(horarioBuscado != "")
                                    {
                                        botonBuscar.removeAttribute("disabled");
                                    }
                                    else
                                    {
                                        botonBuscar.setAttribute("disabled",true);
                                    }
                                }
                                else
                                {
                                    if(tipoDeBusqueda == "estatus")
                                    {
                                        botonBuscar.removeAttribute("disabled");
                                    }
                                    else
                                    {
                                        alert("Error");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
function habilitarCampoBusqueda()
{
    tipoDeBusqueda = $("#selcTipoDeBusqueda").val();
    campoBusquedaId = $("#seccionIdBusqueda");
    campoBusquedaCliente = $("#seccionClienteBusqueda");
    campoBusquedaTratamiento = $("#seccionTratamientoBusqueda");
    campoBusquedaEmpleado = $("#seccionEmpleadoBusqueda");
    campoBusquedaSucursal = $("#seccionSucursalBusqueda");
    campoBusquedaSala = $("#seccionSalaBusqueda");
    campoBusquedaHorario = $("#seccionHorarioBusqueda");
    campoBusquedaFecha = $("#seccionFechaBusqueda");
    campoBusquedaEstatus = $("#seccionEstatusBusqueda");
    if(tipoDeBusqueda == "id")
    {
        campoBusquedaId.removeClass("tipoBusquedaOculto");
        campoBusquedaCliente.addClass("tipoBusquedaOculto");
        campoBusquedaTratamiento.addClass("tipoBusquedaOculto");
        campoBusquedaEmpleado.addClass("tipoBusquedaOculto");
        campoBusquedaSucursal.addClass("tipoBusquedaOculto");
        campoBusquedaSala.addClass("tipoBusquedaOculto");
        campoBusquedaFecha.addClass("tipoBusquedaOculto");
        campoBusquedaHorario.addClass("tipoBusquedaOculto");
        campoBusquedaEstatus.addClass("tipoBusquedaOculto");
    }
    else
    {
        if(tipoDeBusqueda == "cliente")
        {
            campoBusquedaCliente.removeClass("tipoBusquedaOculto");
            campoBusquedaId.addClass("tipoBusquedaOculto");
            campoBusquedaTratamiento.addClass("tipoBusquedaOculto");
            campoBusquedaEmpleado.addClass("tipoBusquedaOculto");
            campoBusquedaSucursal.addClass("tipoBusquedaOculto");
            campoBusquedaSala.addClass("tipoBusquedaOculto");
            campoBusquedaFecha.addClass("tipoBusquedaOculto");
            campoBusquedaHorario.addClass("tipoBusquedaOculto");
            campoBusquedaEstatus.addClass("tipoBusquedaOculto");
        }
        else
        {
            if(tipoDeBusqueda == "tratamiento")
            {
                campoBusquedaTratamiento.removeClass("tipoBusquedaOculto");
                campoBusquedaId.addClass("tipoBusquedaOculto");
                campoBusquedaCliente.addClass("tipoBusquedaOculto");
                campoBusquedaEmpleado.addClass("tipoBusquedaOculto");
                campoBusquedaSucursal.addClass("tipoBusquedaOculto");
                campoBusquedaSala.addClass("tipoBusquedaOculto");
                campoBusquedaFecha.addClass("tipoBusquedaOculto");
                campoBusquedaHorario.addClass("tipoBusquedaOculto");
                campoBusquedaEstatus.addClass("tipoBusquedaOculto");
            }
            else
            {
                if(tipoDeBusqueda == "empleado")
                {
                    campoBusquedaEmpleado.removeClass("tipoBusquedaOculto");
                    campoBusquedaId.addClass("tipoBusquedaOculto");
                    campoBusquedaCliente.addClass("tipoBusquedaOculto");
                    campoBusquedaTratamiento.addClass("tipoBusquedaOculto");
                    campoBusquedaSucursal.addClass("tipoBusquedaOculto");
                    campoBusquedaSala.addClass("tipoBusquedaOculto");
                    campoBusquedaFecha.addClass("tipoBusquedaOculto");
                    campoBusquedaHorario.addClass("tipoBusquedaOculto");
                    campoBusquedaEstatus.addClass("tipoBusquedaOculto");
                }
                else
                {
                    if(tipoDeBusqueda == "sucursal")
                    {
                        campoBusquedaSucursal.removeClass("tipoBusquedaOculto");
                        campoBusquedaId.addClass("tipoBusquedaOculto");
                        campoBusquedaCliente.addClass("tipoBusquedaOculto");
                        campoBusquedaTratamiento.addClass("tipoBusquedaOculto");
                        campoBusquedaEmpleado.addClass("tipoBusquedaOculto");
                        campoBusquedaSala.addClass("tipoBusquedaOculto");
                        campoBusquedaFecha.addClass("tipoBusquedaOculto");
                        campoBusquedaHorario.addClass("tipoBusquedaOculto");
                        campoBusquedaEstatus.addClass("tipoBusquedaOculto");
                    }
                    else
                    {
                        if(tipoDeBusqueda == "sala")
                        {
                            campoBusquedaSala.removeClass("tipoBusquedaOculto");
                            campoBusquedaId.addClass("tipoBusquedaOculto");
                            campoBusquedaCliente.addClass("tipoBusquedaOculto");
                            campoBusquedaTratamiento.addClass("tipoBusquedaOculto");
                            campoBusquedaEmpleado.addClass("tipoBusquedaOculto");
                            campoBusquedaSucursal.addClass("tipoBusquedaOculto");
                            campoBusquedaFecha.addClass("tipoBusquedaOculto");
                            campoBusquedaHorario.addClass("tipoBusquedaOculto");
                            campoBusquedaEstatus.addClass("tipoBusquedaOculto");
                        }
                        else
                        {
                            if(tipoDeBusqueda == "fecha")
                            {
                                campoBusquedaFecha.removeClass("tipoBusquedaOculto");
                                campoBusquedaId.addClass("tipoBusquedaOculto");
                                campoBusquedaCliente.addClass("tipoBusquedaOculto");
                                campoBusquedaTratamiento.addClass("tipoBusquedaOculto");
                                campoBusquedaEmpleado.addClass("tipoBusquedaOculto");
                                campoBusquedaSucursal.addClass("tipoBusquedaOculto");
                                campoBusquedaSala.addClass("tipoBusquedaOculto");
                                campoBusquedaHorario.addClass("tipoBusquedaOculto");
                                campoBusquedaEstatus.addClass("tipoBusquedaOculto");
                            }
                            else
                            {
                                if(tipoDeBusqueda == "horario")
                                {
                                    campoBusquedaHorario.removeClass("tipoBusquedaOculto");
                                    campoBusquedaId.addClass("tipoBusquedaOculto");
                                    campoBusquedaCliente.addClass("tipoBusquedaOculto");
                                    campoBusquedaTratamiento.addClass("tipoBusquedaOculto");
                                    campoBusquedaEmpleado.addClass("tipoBusquedaOculto");
                                    campoBusquedaSucursal.addClass("tipoBusquedaOculto");
                                    campoBusquedaSala.addClass("tipoBusquedaOculto");
                                    campoBusquedaFecha.addClass("tipoBusquedaOculto");
                                    campoBusquedaEstatus.addClass("tipoBusquedaOculto");
                                }
                                else
                                {
                                    if(tipoDeBusqueda == "estatus")
                                    {
                                        campoBusquedaEstatus.removeClass("tipoBusquedaOculto");
                                        campoBusquedaId.addClass("tipoBusquedaOculto");
                                        campoBusquedaCliente.addClass("tipoBusquedaOculto");
                                        campoBusquedaTratamiento.addClass("tipoBusquedaOculto");
                                        campoBusquedaEmpleado.addClass("tipoBusquedaOculto");
                                        campoBusquedaSucursal.addClass("tipoBusquedaOculto");
                                        campoBusquedaFecha.addClass("tipoBusquedaOculto");
                                        campoBusquedaHorario.addClass("tipoBusquedaOculto");
                                        campoBusquedaSala.addClass("tipoBusquedaOculto");
                                    }
                                    else
                                    {
                                        alert("Error");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
function resultadoBusquedaReservacion()
{
    var tablaBusqueda = "";
    tipoDeBusqueda = $("#selcTipoDeBusqueda").val();
    if(tipoDeBusqueda == "id")
    {
        var valorBuscado = $("#txtBusquedaId").val();
        for(var i = (reservaciones.length)-1; i >= 0; i--)
        {
            var idReservacion = reservaciones[i].id;
            if(idReservacion == valorBuscado)
            {
                tablaBusqueda += llenarTablaBusquedaReservaciones(i);
            }
        }
    }
    else
    {
        if(tipoDeBusqueda == "cliente")
        {
            var textoBuscado = $("#txtClienteBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for(var i = (reservaciones.length)-1; i >= 0; i--)
            {
                var clienteReservacion = reservaciones[i].cliente.toLowerCase();
                if(clienteReservacion.indexOf(textoBuscadoMinusculas) !== -1)
                {
                    tablaBusqueda += llenarTablaBusquedaReservaciones(i);
                }
            }
        }
        else
        {
            if(tipoDeBusqueda == "tratamiento")
            {
                var textoBuscado = $("#txtTratamientoBusqueda").val();
                textoBuscadoMinusculas = textoBuscado.toLowerCase();
                for(var i = (reservaciones.length)-1; i >= 0; i--)
                {
                    var nombreReservacion = reservaciones[i].tratamiento.toLowerCase();
                    if(nombreReservacion.indexOf(textoBuscadoMinusculas) !== -1)
                    {
                        tablaBusqueda += llenarTablaBusquedaReservaciones(i);
                    }
                }
            }
            else
            {
                if(tipoDeBusqueda == "empleado")
                {
                    var textoBuscado = $("#txtEmpleadoBusqueda").val();
                    textoBuscadoMinusculas = textoBuscado.toLowerCase();
                    for(var i = (reservaciones.length)-1; i >= 0; i--)
                    {
                        var empleadoReservacion = reservaciones[i].empleado.toLowerCase();
                        if(empleadoReservacion.indexOf(textoBuscadoMinusculas) !== -1)
                        {
                            tablaBusqueda += llenarTablaBusquedaReservaciones(i);
                        }
                    }
                }
                else
                {
                    if(tipoDeBusqueda == "sucursal")
                    {
                        var textoBuscado = $("#txtSucursalBusqueda").val();
                        textoBuscadoMinusculas = textoBuscado.toLowerCase();
                        for(var i = (reservaciones.length)-1; i >= 0; i--)
                        {
                            var sucursalReservacion = reservaciones[i].sucursal.toLowerCase();
                            if(sucursalReservacion.indexOf(textoBuscadoMinusculas) !== -1)
                            {
                                tablaBusqueda += llenarTablaBusquedaReservaciones(i);
                            }
                        }
                    }
                    else
                    {
                        if(tipoDeBusqueda == "sala")
                        {
                            var textoBuscado = $("#txtSalaBusqueda").val();
                            textoBuscadoMinusculas = textoBuscado.toLowerCase();
                            for(var i = (reservaciones.length)-1; i >= 0; i--)
                            {
                                var salaReservacion = reservaciones[i].sala.toLowerCase();
                                if(salaReservacion.indexOf(textoBuscadoMinusculas) !== -1)
                                {
                                    tablaBusqueda += llenarTablaBusquedaReservaciones(i);
                                }
                            }
                        }
                        else
                        {
                            if(tipoDeBusqueda == "horario")
                            {
                                var textoBuscado = $("#txtHorarioBusqueda").val();
                                textoBuscadoMinusculas = textoBuscado.toLowerCase();
                                for(var i = (reservaciones.length)-1; i >= 0; i--)
                                {
                                    var horarioReservacion = reservaciones[i].horario.toLowerCase();
                                    if(horarioReservacion.indexOf(textoBuscadoMinusculas) !== -1)
                                    {
                                        tablaBusqueda += llenarTablaBusquedaReservaciones(i);
                                    }
                                }
                            }
                            else
                            {
                                if(tipoDeBusqueda == "fecha")
                                {
                                    var textoBuscado = $("#txtFechaBusqueda").val();
                                    for(var i = (reservaciones.length)-1; i >= 0; i--)
                                    {
                                        var fechaReservacion = reservaciones[i].fecha;
                                        if(fechaReservacion.indexOf(textoBuscado) !== -1)
                                        {
                                            tablaBusqueda += llenarTablaBusquedaReservaciones(i);
                                        }
                                    }
                                }
                                else
                                {
                                    if(tipoDeBusqueda == "estatus")
                                    {
                                        var estatusPendienteBuscado = document.getElementById("txtBusquedaEstatus1").checked;
                                        var estatusRealizadaBuscado = document.getElementById("txtBusquedaEstatus2").checked;
                                        var estatusCanceladaBuscado = document.getElementById("txtBusquedaEstatus3").checked;
                                        if(estatusPendienteBuscado)
                                        {
                                            var textoBuscado = "Pendiente";
                                        }
                                        else
                                        {
                                            if(estatusRealizadaBuscado)
                                            {
                                                var textoBuscado = "Realizada";
                                            }
                                            else
                                            {
                                                if(estatusCanceladaBuscado)
                                                {
                                                    var textoBuscado = "Cancelada";
                                                }
                                            }
                                        }
                                        for(var i = (reservaciones.length)-1; i >= 0; i--)
                                        {
                                            var estatusEmpleado = reservaciones[i].estatus;
                                            if(estatusEmpleado == textoBuscado)
                                            {
                                                tablaBusqueda += llenarTablaBusquedaReservaciones(i);
                                            }
                                        }
                                    }
                                    else
                                    {
                                        tablaBusqueda = "";
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    if(tablaBusqueda == "")
    {
        tablaBusqueda = "<tr><td colspan='10' class='registro text-center'><h4 class='py-4'>No hay resultados de búsqueda</h4></td></tr>";
    }
    $("#datosBusquedatablaReservaciones").html(tablaBusqueda);
    actualizarListadoReservaciones();
}
function llenarTablaBusquedaReservaciones(i)
{
    var parteTablaBusqueda = ""
    parteTablaBusqueda += "<tr>";
    parteTablaBusqueda += "<td scope='col' class='id_tabla'>"+reservaciones[i].id+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+reservaciones[i].tratamiento+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+reservaciones[i].sucursal+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+reservaciones[i].sala+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+reservaciones[i].horario+"</td>";
    var fechaGuardada = reservaciones[i].fecha;
    fechaGuardada.split();
    var fechaMostrada = fechaGuardada[8]+fechaGuardada[9]+"/"+fechaGuardada[5]+fechaGuardada[6]+"/"+fechaGuardada[0]+fechaGuardada[1]+fechaGuardada[2]+fechaGuardada[3];
    parteTablaBusqueda += "<td class='registro'>"+fechaMostrada+"</td>";
    if(reservaciones[i].estatus == "Pendiente")
    {
        parteTablaBusqueda += "<td class='registro' style='background-color: #f3e13dc9;'>"+reservaciones[i].estatus+"</td>";
    }
    else
    {
        if(reservaciones[i].estatus == "Realizada")
        {
            parteTablaBusqueda += "<td class='registro' style='background-color: #c6dfc3;'>"+reservaciones[i].estatus+"</td>";
        }
        else
        {
            if(reservaciones[i].estatus == "Cancelada")
            {
                parteTablaBusqueda += "<td class='registro' style='background-color: #ffa6a6;'>"+reservaciones[i].estatus+"</td>";
            }
            else
            {
                parteTablaBusqueda += "<td class='registro'>ERROR!!</td>";
            }
        }
    }
    parteTablaBusqueda += "<td class='registro'><button onclick='cargarModuloInformarReservacion("+reservaciones[i].id+");'><img src='./img/info.svg' alt='Info.' class='icono'></button></td>";
    parteTablaBusqueda += "<td class='registro'><button onclick='cargarModuloEditarReservacion("+reservaciones[i].id+");'><img src='./img/editar.svg' alt='Edit.' class='icono'></button></td>";
    parteTablaBusqueda += "</tr>";
    return parteTablaBusqueda;
}
function mostrarBusqueda()
{
    $("#tablaReservaciones").hide();
    $("#datosTablaBusquedaReservaciones").show();
}
function ocultarBusqueda()
{
    $("#tablaReservaciones").show();
    $("#datosTablaBusquedaReservaciones").hide();
}

