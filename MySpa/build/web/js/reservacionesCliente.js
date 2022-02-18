/*
    Proyecto: Sitio web – MySpa                                                         
    Equipo desarrollador: TetraNet
    Desarrollador(es):   Víctor Manuel Fonseca Muñoz e Iván Moisés Ornelas Meza
    Fecha de inicio: Lunes 19 de julio de 2021
    Versión: 6.0
    Descripción general del archivo JavaScript: Este archivo JavaScript es el complemento para el uso de la interfaz
    de "gestión de reservaciones para clientes", este archivo contiene las instrucciones para su correcto funcionamiento y gestionamiento de 
    reservaciones.
*/

// Listado de reservaciones por defecto, sólo se mostrarán las del cliente "Jennifer Margarita"
var reservacionesCliente = [
    {
        "id" : 1,
        "cliente" : "Jennifer Margarita",
        "tratamiento" : "Batido natural",
        "empleado" : "Margarita de Jesús",
        "sucursal" : "León Boulevard Hidalgo 4009",
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
        "sucursal" : "León del moral 658",
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
    if(mes < 10)
    {
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
function cargarModuloinformarReservacionCliente(id_reservacion)
{
    $("#seccionGestionarReservacionCliente").show();
    $("#tablaReservacionesCliente").removeClass("col-md-12");
    $("#tablaReservacionesCliente").addClass("col-md-8");
    $("#datosTablaBusquedaReservacionesCliente").removeClass("col-md-12");
    $("#datosTablaBusquedaReservacionesCliente").addClass("col-md-8");
    $.ajax(
        {
            "type" : "GET",
            "url" : "./modulos/informarReservacionCliente.html",
            "async" : true
        }
    ).done(
        function(data)
        {
            $("#seccionGestionarReservacionCliente").html(data);
            var posicionReservacion = id_reservacion-1;
            $("#txtCodigoReser").val(reservacionesCliente[posicionReservacion].id);
            $("#txtClienteReser").val(reservacionesCliente[posicionReservacion].cliente);
            $("#txtTratamientoReser").val(reservacionesCliente[posicionReservacion].tratamiento);
            $("#txtEmpleadoReser").val(reservacionesCliente[posicionReservacion].empleado);
            $("#txtSucursalReser").val(reservacionesCliente[posicionReservacion].sucursal);
            $("#txtSalaReser").val(reservacionesCliente[posicionReservacion].sala);
            $("#txtHorarioReser").val(reservacionesCliente[posicionReservacion].horario);
            $("#txtFechaReser").val(reservacionesCliente[posicionReservacion].fecha);
            $("#txtEstatusReser").val(reservacionesCliente[posicionReservacion].estatus);
        }
    );  
}
function cargarModuloAgregarReservacionCliente()
{
    $("#seccionGestionarReservacionCliente").show();
    $("#tablaReservacionesCliente").removeClass("col-md-12");
    $("#tablaReservacionesCliente").addClass("col-md-8");
    $("#datosTablaBusquedaReservacionesCliente").removeClass("col-md-12");
    $("#datosTablaBusquedaReservacionesCliente").addClass("col-md-8");
    $.ajax(
        {
            "type" : "GET",
            "url" : "./modulos/agregarReservacionCliente.html",
            "async" : true
        }
    ).done(
        function(data)
        {
            $("#seccionGestionarReservacionCliente").html(data);
            /*
                La siguiente linea de código hace que se muestre siempre el mismo cliente al
                ingresar una nueva reservación.
            */
            $("#txtClienteReser").val("Jennifer Margarita");
        }
    );
}
function guardarReservacion()
{
    var codigoRes = reservacionesCliente.length+1;
    var clienteRes = $("#txtClienteReser").val();
    var tratamientoRes = $("#txtTratamientoReser").val();
    var elegirEmpleado = parseInt((Math.random())*4);
    switch (elegirEmpleado)
    {
        case 0:
            var empleadoRes = "Diana Guadalupe";
            break;
        case 1:
            var empleadoRes = "Denisse";
            break;
        case 2:
            var empleadoRes = "Margarita de Jesús";
            break;
        case 3:
            var empleadoRes = "Juan Diego";
            break;
        case 4:
            var empleadoRes = "Ana Fernanda";
            break;
        default:
            var empleadoRes = "Empleado no encontrado";
            break;
    }
    var sucursalRes = $("#txtSucursalReser").val();
    var salaRes = $("#txtSalaReser").val();
    var horarioRes = $("#txtHorarioReser").val();
    var fechaRes = $("#txtFechaReser").val();
    var reservacionNueva =
    {
        "id" : codigoRes,
        "cliente" : clienteRes,
        "tratamiento" : tratamientoRes,
        "empleado" : empleadoRes,
        "sucursal" : sucursalRes,
        "sala" : salaRes,
        "horario" : horarioRes,
        "fecha" : fechaRes,
        "estatus" : "Pendiente",
    };
    existenciaReservacion = false;
    for(var i = 0; i < reservacionesCliente.length; i++)
    {
        if((reservacionesCliente[i].horario === reservacionNueva.horario) && (reservacionesCliente[i].fecha === reservacionNueva.fecha) && (reservacionesCliente[i].estatus === "Pendiente"))
        {
            existenciaReservacion = true;
        }
    }
    if(existenciaReservacion)
    {
        let timerInterval;
        Swal.fire(
        {
            title: "Esta reservacion ya existe",
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
        if(clienteRes && tratamientoRes && empleadoRes && sucursalRes && salaRes && horarioRes)
        {
            reservacionesCliente.push(reservacionNueva);
            limpiarFormularioReservaciones();
            cargarModuloAgregarReservacionCliente();
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
function cargarModuloeditarReservacionCliente(id_reservacion)
{
    $("#seccionGestionarReservacionCliente").show();
    $("#tablaReservacionesCliente").removeClass("col-md-12");
    $("#tablaReservacionesCliente").addClass("col-md-8");
    $("#datosTablaBusquedaReservacionesCliente").removeClass("col-md-12");
    $("#datosTablaBusquedaReservacionesCliente").addClass("col-md-8");
    $.ajax(
        {
            "type" : "GET",
            "url" : "./modulos/editarReservacionCliente.html",
            "async" : true
        }
    ).done(
        function(data)
        {
            $("#seccionGestionarReservacionCliente").html(data);
            var posicionReservacion = id_reservacion-1;
            $("#txtCodigoReser").val(reservacionesCliente[posicionReservacion].id);
            $("#txtClienteReser").val(reservacionesCliente[posicionReservacion].cliente);
            $("#txtTratamientoReser").val(reservacionesCliente[posicionReservacion].tratamiento);
            $("#txtEmpleadoReser").val(reservacionesCliente[posicionReservacion].empleado);
            $("#txtSucursalReser").val(reservacionesCliente[posicionReservacion].sucursal);
            $("#txtSalaReser").val(reservacionesCliente[posicionReservacion].sala);
            $("#txtHorarioReser").val(reservacionesCliente[posicionReservacion].horario);
            $("#txtFechaReser").val(reservacionesCliente[posicionReservacion].fecha);
            var pendiente = document.getElementById("txtEstatusReser1");
            var cancelada = document.getElementById("txtEstatusReser2");
            if(reservacionesCliente[posicionReservacion].estatus === "Pendiente")
            {
                pendiente.setAttribute("checked",true);
            }
            else
            {
                if(reservacionesCliente[posicionReservacion].estatus === "Cancelada")
                {
                    cancelada.setAttribute("checked",true);
                }
            }
        }
    );
}
function editarReservacionCliente()
{
    var codigoRes = parseInt($("#txtCodigoReser").val());
    var estatusRes1 = document.getElementById("txtEstatusReser1").checked;
    var estatusRes2 = document.getElementById("txtEstatusReser2").checked;
    if(estatusRes1)
    {
        var estatusRes = "Pendiente";
    }
    else
    {
        if(estatusRes2)
        {
            var estatusRes = "Cancelada";
        }
    }
    var posicionReservacion = codigoRes-1;
    reservacionesCliente[posicionReservacion].estatus = estatusRes;
    actualizarListadoReservaciones();
    if(estatusRes == "Cancelada")
    {
        regresar();
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
    else
    {
        if(estatusRes == "Pendiente")
        {
            regresar();
        }
    }
    resultadoBusquedaReservacion();
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
    $("#txtCodigoReser").val(reservacionesCliente.length);
    /*
    Un cliente solamente puede agendar reservaciones para sí mismo.
    La siguiente variable "nombre", es para guardar el nombre del cliente de la sesión actual.
    */
    var nombre = "Jennifer Margarita";
    $("#txtClienteReser").val(nombre);
    $("#txtTratamientoReser").val("");
    $("#txtEmpleadoReser").val("");
    $("#txtSucursalReser").val("");
    $("#txtSalaReser").val("");
    $("#txtFechaReser").val("");
    $("#txtHorarioReser").val("");
}
function regresar()
{
    $("#seccionGestionarReservacionCliente").hide();
    $("#datosTablaBusquedaReservacionesCliente").removeClass("col-md-8");
    $("#datosTablaBusquedaReservacionesCliente").addClass("col-md-12");
    $("#tablaReservacionesCliente").removeClass("col-md-8");
    $("#tablaReservacionesCliente").addClass("col-md-12");
}
function actualizarListadoReservaciones()
{
    datosTablaRes = "";
    for(var i = (reservacionesCliente.length)-1; i >= 0; i--)
    {
        /*
        Un cliente solamente puede ver su información en reservaciones, por la ausencia de la conexión con la base de datos,
        se usará el nombre del cliente para identificarlo de forma única asignando, el siguiente "if" se encarga de esto,
        para solo mostrar las reservaciones de "Jennifer Margarita".
        */
        if(reservacionesCliente[i].cliente == "Jennifer Margarita")
        {
            datosTablaRes += "<tr>";
            datosTablaRes += "<td scope='col' class='id_tabla'>"+reservacionesCliente[i].id+"</td>";
            datosTablaRes += "<td class='registro'>"+reservacionesCliente[i].tratamiento+"</td>";
            datosTablaRes += "<td class='registro'>"+reservacionesCliente[i].sucursal+"</td>";
            datosTablaRes += "<td class='registro'>"+reservacionesCliente[i].sala+"</td>";
            datosTablaRes += "<td class='registro'>"+reservacionesCliente[i].horario+"</td>";
            var fechaGuardada = reservacionesCliente[i].fecha;
            fechaGuardada.split();
            var fechaMostrada = fechaGuardada[8]+fechaGuardada[9]+"/"+fechaGuardada[5]+fechaGuardada[6]+"/"+fechaGuardada[0]+fechaGuardada[1]+fechaGuardada[2]+fechaGuardada[3];
            datosTablaRes += "<td class='registro'>"+fechaMostrada+"</td>";
            if(reservacionesCliente[i].estatus == "Pendiente")
            {
                datosTablaRes += "<td class='registro' style='background-color: #f3e13dc9;'>"+reservacionesCliente[i].estatus+"</td>";
                datosTablaRes += "<td class='registro'><button onclick='cargarModuloinformarReservacionCliente("+reservacionesCliente[i].id+");'><img src='./img/info.svg' alt='Info.' class='icono'></button></td>";
                datosTablaRes += "<td class='registro'><button onclick='cargarModuloeditarReservacionCliente("+reservacionesCliente[i].id+");'><img src='./img/editar.svg' alt='Edit.' class='icono'></button></td>";
            }
            else
            {
                if(reservacionesCliente[i].estatus == "Realizada")
                {
                    datosTablaRes += "<td class='registro' style='background-color: #c6dfc3;'>"+reservacionesCliente[i].estatus+"</td>";
                    datosTablaRes += "<td class='registro' colspan='2'><button onclick='cargarModuloinformarReservacionCliente("+reservacionesCliente[i].id+");'><img src='./img/info.svg' alt='Info.' class='icono'></button></td>";
                }
                else
                {
                    if(reservacionesCliente[i].estatus == "Cancelada")
                    {
                        datosTablaRes += "<td class='registro' style='background-color: #ffa6a6;'>"+reservacionesCliente[i].estatus+"</td>";
                        datosTablaRes += "<td class='registro' colspan='2'><button onclick='cargarModuloinformarReservacionCliente("+reservacionesCliente[i].id+");'><img src='./img/info.svg' alt='Info.' class='icono'></button></td>";
                    }
                    else
                    {
                        datosTablaRes += "<td class='registro'>ERROR!!</td>";
                    }
                }
            }
            datosTablaRes += "</tr>";
        }
    }
    $("#datostablaReservacionesCliente").html(datosTablaRes);
}
function limpiarFormularioBuscar()
{
    botonBuscar = document.getElementById("btnBuscar");
    botonBuscar.setAttribute("disabled",true);
    $("#txtBusquedaId").val("");
    $("#txtTratamientoBusqueda").val("");
    $("#txtEmpleadoBusqueda").val("");
    $("#txtSucursalBusqueda").val("");
    $("#txtSalaBusqueda").val("");
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
        for(var i = (reservacionesCliente.length)-1; i >= 0; i--)
        {
            var idReservacion = reservacionesCliente[i].id;
            if(idReservacion == valorBuscado)
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
            for(var i = (reservacionesCliente.length)-1; i >= 0; i--)
            {
                var nombreReservacion = reservacionesCliente[i].tratamiento.toLowerCase();
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
                for(var i = (reservacionesCliente.length)-1; i >= 0; i--)
                {
                    var empleadoReservacion = reservacionesCliente[i].empleado.toLowerCase();
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
                    for(var i = (reservacionesCliente.length)-1; i >= 0; i--)
                    {
                        var sucursalReservacion = reservacionesCliente[i].sucursal.toLowerCase();
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
                        for(var i = (reservacionesCliente.length)-1; i >= 0; i--)
                        {
                            var salaReservacion = reservacionesCliente[i].sala.toLowerCase();
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
                            for(var i = (reservacionesCliente.length)-1; i >= 0; i--)
                            {
                                var horarioReservacion = reservacionesCliente[i].horario.toLowerCase();
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
                                for(var i = 0; i < reservaciones.length; i++)
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
                                    for(var i = (reservacionesCliente.length)-1; i >= 0; i--)
                                    {
                                        var estatusEmpleado = reservacionesCliente[i].estatus;
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
    if(tablaBusqueda == "")
    {
        tablaBusqueda = "<tr><td colspan='10' class='registro text-center'><h4 class='py-4'>No hay resultados de búsqueda</h4></td></tr>";
    }
    $("#datosBusquedatablaReservacionesCliente").html(tablaBusqueda);
    actualizarListadoReservaciones();
}
function llenarTablaBusquedaReservaciones(i)
{
    var parteTablaBusqueda = ""
    if(reservacionesCliente[i].cliente == "Jennifer Margarita")
    {
        parteTablaBusqueda += "<tr>";
        parteTablaBusqueda += "<td scope='col' class='id_tabla'>"+reservacionesCliente[i].id+"</td>";
        parteTablaBusqueda += "<td class='registro'>"+reservacionesCliente[i].tratamiento+"</td>";
        parteTablaBusqueda += "<td class='registro'>"+reservacionesCliente[i].sucursal+"</td>";
        parteTablaBusqueda += "<td class='registro'>"+reservacionesCliente[i].sala+"</td>";
        parteTablaBusqueda += "<td class='registro'>"+reservacionesCliente[i].horario+"</td>";
        var fechaGuardada = reservacionesCliente[i].fecha;
        fechaGuardada.split();
        var fechaMostrada = fechaGuardada[8]+fechaGuardada[9]+"/"+fechaGuardada[5]+fechaGuardada[6]+"/"+fechaGuardada[0]+fechaGuardada[1]+fechaGuardada[2]+fechaGuardada[3];
        parteTablaBusqueda += "<td class='registro'>"+fechaMostrada+"</td>";
        if(reservacionesCliente[i].estatus == "Pendiente")
        {
            parteTablaBusqueda += "<td class='registro' style='background-color: #f3e13dc9;'>"+reservacionesCliente[i].estatus+"</td>";
            parteTablaBusqueda += "<td class='registro'><button onclick='cargarModuloinformarReservacionCliente("+reservacionesCliente[i].id+");'><img src='./img/info.svg' alt='Info.' class='icono'></button></td>";
            parteTablaBusqueda += "<td class='registro'><button onclick='cargarModuloeditarReservacionCliente("+reservacionesCliente[i].id+");'><img src='./img/editar.svg' alt='Edit.' class='icono'></button></td>";
        }
        else
        {
            if(reservacionesCliente[i].estatus == "Realizada")
            {
                parteTablaBusqueda += "<td class='registro' style='background-color: #c6dfc3;'>"+reservacionesCliente[i].estatus+"</td>";
                parteTablaBusqueda += "<td class='registro' colspan='2'><button onclick='cargarModuloinformarReservacionCliente("+reservacionesCliente[i].id+");'><img src='./img/info.svg' alt='Info.' class='icono'></button></td>";
            }
            else
            {
                if(reservacionesCliente[i].estatus == "Cancelada")
                {
                    parteTablaBusqueda += "<td class='registro' style='background-color: #ffa6a6;'>"+reservacionesCliente[i].estatus+"</td>";
                    parteTablaBusqueda += "<td class='registro' colspan='2'><button onclick='cargarModuloinformarReservacionCliente("+reservacionesCliente[i].id+");'><img src='./img/info.svg' alt='Info.' class='icono'></button></td>";
                }
                else
                {
                    parteTablaBusqueda += "<td class='registro'>ERROR!!</td>";
                }
            }
        }
        parteTablaBusqueda += "</tr>";
    }
    return parteTablaBusqueda;
}
function mostrarBusqueda()
{
    $("#tablaReservacionesCliente").hide();
    $("#datosTablaBusquedaReservacionesCliente").show();
}
function ocultarBusqueda()
{
    $("#tablaReservacionesCliente").show();
    $("#datosTablaBusquedaReservacionesCliente").hide();
}
