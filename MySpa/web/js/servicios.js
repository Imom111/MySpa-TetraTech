/*
    Proyecto: Sitio web – MySpa                                                         
    Equipo desarrollador: TetraNet
    Desarrollador(es):   Alexis Alfredo Prado Ojeda
    Fecha de inicio: Lunes 19 de julio de 2021
    Versión: 4.0
    Descripción general del archivo JavaScript: Este archivo JavaScript es el complemento para el uso de la interfaz
    de "gestión de servicios", este archivo contiene las instrucciones para su correcto funcionamiento y gestionamiento de 
    servicios.
*/

// Listado de servicios por defecto
var servicios = [
    {
        "id" : 1,
        "fecha" : "2020-07-05",
        "hora" : "12:00:00-14:00:00",
        "reservacion" : 2,
        "tratamiento" : "Aromaterapia",
        "empleado" : "Marco Soto Gárnica"
    },
    {
        "id" : 2,
        "fecha" : "2020-08-15",
        "hora" : "10:00:00-12:00:00",
        "reservacion" : 3,
        "tratamiento" : "Exfoliacion corporal",
        "empleado" : "Diana Guadalupe Rodríguez Gómez"
    },
    {
        "id" : 3,
        "fecha" : "2020-02-07",
        "hora" : "08:00:00-10:00:00",
        "reservacion" : 4,
        "tratamiento" : "Hidromasaje",
        "empleado" : "Denisse Fonseca Hernández"
    },
    {
        "id" : 4,
        "fecha" : "2021-04-11",
        "hora" : "14:00:00-16:00:00",
        "reservacion" : 5,
        "tratamiento" : "Masaje hawaiano",
        "empleado" : "Juan Carlos Barrientos Pérez"
    },
    {
        "id" : 5,
        "fecha" : "2021-06-05",
        "hora" : "16:00:00-18:00:00",
        "reservacion" : 6,
        "tratamiento" : "Batido natural",
        "empleado" : "Carlos Eduardo López Romero"
    },
    {
        "id" : 6,
        "fecha" : "2021-09-15",
        "hora" : "08:00:00-10:00:00",
        "reservacion" : 7,
        "tratamiento" : "Ducha de sensaciones",
        "empleado" : "Margarita de Jesus Castañón Gómez"
    },
    {
        "id" : 7,
        "fecha" : "2020-04-01",
        "hora" : "12:00:00-14:00:00",
        "reservacion" : 8,
        "tratamiento" : "Ducha de sensaciones",
        "empleado" : "Juan Diego López Gómez"
    },
    {
        "id" : 8,
        "fecha" : "2020-10-22",
        "hora" : "14:00:00-16:00:00",
        "reservacion" : 9,
        "tratamiento" : "Exfoliacion facial",
        "empleado" : "Ana Fernanda López Mejía"
    }
];

// Sección de funciones
function cargarModuloInformarServicio(id_servicio)
{
    $("#seccionGestionarServicio").show();
    $("#tablaServicios").removeClass("col-md-12");
    $("#tablaServicios").addClass("col-md-8");
    $("#datosTablaBusquedaServicios").removeClass("col-md-12");
    $("#datosTablaBusquedaServicios").addClass("col-md-8");
    $.ajax(
        {
            "type" : "GET",
            "url" : "./modulos/informarServicio.html",
            "async" : true
        }
    ).done(
        function(data)
        {
            $("#seccionGestionarServicio").html(data);
            var posicionServicio = id_servicio-1;
            $("#txtCodigoServicio").val(servicios[posicionServicio].id);
            $("#txtFechaServicio").val(servicios[posicionServicio].fecha);
            $("#txtHoraServicio").val(servicios[posicionServicio].hora);
            $("#txtReservacionServicio").val(servicios[posicionServicio].reservacion);
            $("#txtTratamientoServicio").val(servicios[posicionServicio].tratamiento);
            $("#txtEmpleadoServicio").val(servicios[posicionServicio].empleado);
        }
    );  
}
function cargarModuloAgregarServicio()
{
    $("#seccionGestionarServicio").show();
    $("#tablaServicios").removeClass("col-md-12");
    $("#tablaServicios").addClass("col-md-8");
    $("#datosTablaBusquedaServicios").removeClass("col-md-12");
    $("#datosTablaBusquedaServicios").addClass("col-md-8");
    $.ajax(
        {
            "type" : "GET",
            "url" : "./modulos/agregarServicio.html",
            "async" : true
        }
    ).done(
        function(data)
        {
            $("#seccionGestionarServicio").html(data);
            $("#txtCodigoServicio").val(servicios.length+1);
        }
    );
}
function guardarServicio()
{
    var codigoServ = parseInt($("#txtCodigoServicio").val());
    var fechaServ = $("#txtFechaServicio").val();
    var horaServ = $("#txtHoraServicio").val();
    var reservacionServ = $("#txtReservacionServicio").val();
    var tratamientoServ = $("#txtTratamientoServicio").val();
    var empleadoServ = $("#txtEmpleadoServicio").val();
    var servicioNuevo =
    {
        "id" : codigoServ,
        "fecha" : fechaServ,
        "hora" : horaServ,
        "reservacion" : reservacionServ,
        "tratamiento" : tratamientoServ,
        "empleado" : empleadoServ
    };
    existenciaServicio = false;
    for(var i = 0; i < servicios.length; i++)
    {
        if((servicios[i].fecha === servicioNuevo.fecha) && (servicios[i].hora === servicioNuevo.hora))
        {
            existenciaServicio = true;
        }
    }
    if(existenciaServicio)
    {
        let timerInterval;
        Swal.fire(
        {
            title: "Este servicio ya existe",
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
        if(codigoServ && fechaServ && horaServ && reservacionServ && tratamientoServ && empleadoServ)
        {
            servicios.push(servicioNuevo);
            limpiarFormularioServicios();
            cargarModuloAgregarServicio();
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
        actualizarListadoServicios();
        resultadoBusquedaServicios();
    }
    /*
    La siguiente linea no es funcional en su totalidad,
    falta conectar la base de datos al sitio web.
    Swal.fire("Inserción correcta", "Servicio almacenado", "success");
     */
    regresar();
}
function cargarModuloEditarServicio(id_servicio)
{
    $("#seccionGestionarServicio").show();
    $("#tablaServicios").removeClass("col-md-12");
    $("#tablaServicios").addClass("col-md-8");
    $("#datosTablaBusquedaServicios").removeClass("col-md-12");
    $("#datosTablaBusquedaServicios").addClass("col-md-8");
    $.ajax(
        {
            "type" : "GET",
            "url" : "./modulos/editarServicio.html",
            "async" : true
        }
    ).done(
        function(data)
        {
            $("#seccionGestionarServicio").html(data);
            var posicionServicio = id_servicio-1;
            $("#txtCodigoServicio").val(servicios[posicionServicio].id);
            $("#txtFechaServicio").val(servicios[posicionServicio].fecha);
            $("#txtHoraServicio").val(servicios[posicionServicio].hora);
            $("#txtReservacionServicio").val(servicios[posicionServicio].reservacion);
            $("#txtTratamientoServicio").val(servicios[posicionServicio].tratamiento);
            $("#txtEmpleadoServicio").val(servicios[posicionServicio].empleado);
            var posicionServicio = id_servicio-1;
        }
    );
}
function editarServicio()
{
    var codigoServ = parseInt($("#txtCodigoServicio").val());
    var fechaServ = $("#txtFechaServicio").val();
    var horaServ = $("#txtHoraServicio").val();
    var reservacionServ = $("#txtReservacionServicio").val();
    var tratamientoServ = $("#txtTratamientoServicio").val();
    var empleadoServ = $("#txtEmpleadoServicio").val();
    
    if(codigoServ && fechaServ && horaServ && reservacionServ && tratamientoServ && empleadoServ)
    {
        var posicionServicio = codigoServ-1;
        servicios[posicionServicio].id = codigoServ;
        servicios[posicionServicio].fecha = fechaServ;
        servicios[posicionServicio].hora = horaServ;
        servicios[posicionServicio].reservacion = reservacionServ;
        servicios[posicionServicio].tratamiento = tratamientoServ;
        servicios[posicionServicio].empleado = empleadoServ;
        actualizarListadoServicios();
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
        cargarModuloEditarServicio(codigoServ);
        resultadoBusquedaServicio();
    }
    /*
    La siguiente linea no es funcional en su totalidad,
    falta conectar la base de datos al sitio web.
    Swal.fire("Inserción correcta", "Servicio almacenado", "success");
     */
}
function fechas(){
    var fecha = new Date();
    var anio = fecha.getFullYear();
    var mes = 1+(fecha.getMonth());
    if (mes < 10){
        var mesCeros = "0"+mes;
    }else{
        var mesCeros = mes;
    }
    var dia = (fecha.getDate());
    if (dia < 10){
        var diaCeros = "0"+dia;
    }else{
        var diaCeros = dia;
    }
    var hoyFecha = anio+"-"+mesCeros+"-"+diaCeros;
    return hoyFecha;
}
function validarFecha(){
    fechaLimite = fechas();
    var inputFecha = document.getElementById("txtFechaServicio");;
    inputFecha.setAttribute("min",fechaLimite);
}
function limpiarFormularioServicios()
{
    $("#txtCodigoServicio").val("");
    $("#txtFechaServicio").val("");
    $("#txtHoraServicio").val("");
    $("#txtTratamientoServicio").val("");
    $("#txtEmpleadoServicio").val("");
}
function regresar()
{
    $("#seccionGestionarServicio").hide();
    $("#tablaServicios").removeClass("col-md-8");
    $("#tablaServicios").addClass("col-md-12");
    $("#datosTablaBusquedaServicios").removeClass("col-md-8");
    $("#datosTablaBusquedaServicios").addClass("col-md-12");
}
function actualizarListadoServicios()
{
    datosTablaServ = "";
    if(servicios.length == 0)
    {
        datosTablaServ+="<tr><td colspan='8' class='registro text-cente'><h4 class='py-4'>No se encontro nada</h4></td></tr>" 
    }
    else
    {
        for(var i = 0; i < servicios.length; i++)
        {
            datosTablaServ += "<tr>";
            datosTablaServ += "<td scope='col' class='id_tabla'>"+servicios[i].id+"</td>";
            var fechaGuardada = servicios[i].fecha;
            fechaGuardada.split();
            var fechaMostrada = fechaGuardada[8]+fechaGuardada[9]+"/"+fechaGuardada[5]+fechaGuardada[6]+"/"+fechaGuardada[0]+fechaGuardada[1]+fechaGuardada[2]+fechaGuardada[3];
            datosTablaServ += "<td class='registro'>"+fechaMostrada+"</td>";
            datosTablaServ += "<td class='registro'>"+servicios[i].hora+"</td>";
            datosTablaServ += "<td class='registro'>"+servicios[i].tratamiento+"</td>";
            datosTablaServ += "<td class='registro'>"+servicios[i].empleado+"</td>";
            datosTablaServ += "<td class='registro'><button onclick='cargarModuloInformarServicio("+servicios[i].id+");'><img src='./img/info.svg' alt='Info.' class='icono'></button></td>";
            datosTablaServ += "<td class='registro'><button onclick='cargarModuloEditarServicio("+servicios[i].id+");'><img src='./img/editar.svg' alt='Edit.' class='icono'></button></td>";
            datosTablaServ += "</tr>";
        }
    }
    $("#datosTablaServicios").html(datosTablaServ);
}

function limpiarFormularioBuscar()
{
    botonBuscar = document.getElementById("btnBuscar");
    botonBuscar.setAttribute("disabled",true);
    $("#txtBusquedaId").val("");
    $("#txtFechaBusqueda").val("");
    $("#txtHoraBusqueda").val("");
    $("#txtReservacionBusqueda").val("");
    $("#txtTratamientoBusqueda").val("");
    $("#txtEmpleadoBusqueda").val("");
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
            if(tipoDeBusqueda == "hora")
            {
                horaBuscada = $("#txtHoraBusqueda").val();
                if(horaBuscada != "")
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
                if(tipoDeBusqueda == "reservacion")
                {
                    reservacionBuscada = $("#txtReservacionBusqueda").val();
                    if(reservacionBuscada != "")
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
                        tratamientoBuscada = $("#txtTratamientoBusqueda").val();
                        if(tratamientoBuscada != "")
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
                            empleadoBuscada = $("#txtEmpleadoBusqueda").val();
                            if(empleadoBuscada != "")
                            {
                                botonBuscar.removeAttribute("disabled");
                            }
                            else
                            {
                                botonBuscar.setAttribute("disabled",true);
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
    campoBusquedaFecha = $("#seccionFechaBusqueda");
    campoBusquedaHora = $("#seccionHoraBusqueda");
    campoBusquedaReservacion = $("#seccionReservacionBusqueda");
    campoBusquedaTratamiento = $("#seccionTratamientoBusqueda");
    campoBusquedaEmpleado = $("#seccionEmpleadoBusqueda");
    if(tipoDeBusqueda == "id")
    {
        campoBusquedaId.removeClass("tipoBusquedaOculto");
        campoBusquedaFecha.addClass("tipoBusquedaOculto");
        campoBusquedaHora.addClass("tipoBusquedaOculto");
        campoBusquedaReservacion.addClass("tipoBusquedaOculto");
        campoBusquedaTratamiento.addClass("tipoBusquedaOculto");
        campoBusquedaEmpleado.addClass("tipoBusquedaOculto");
    }
    else
    {
        if(tipoDeBusqueda == "fecha")
        {
            campoBusquedaFecha.removeClass("tipoBusquedaOculto");
            campoBusquedaId.addClass("tipoBusquedaOculto");
            campoBusquedaHora.addClass("tipoBusquedaOculto");
            campoBusquedaReservacion.addClass("tipoBusquedaOculto");
            campoBusquedaTratamiento.addClass("tipoBusquedaOculto");
            campoBusquedaEmpleado.addClass("tipoBusquedaOculto");
        }
        else
        {
            if(tipoDeBusqueda == "hora")
            {
                campoBusquedaHora.removeClass("tipoBusquedaOculto");
                campoBusquedaId.addClass("tipoBusquedaOculto");
                campoBusquedaFecha.addClass("tipoBusquedaOculto");
                campoBusquedaReservacion.addClass("tipoBusquedaOculto");
                campoBusquedaTratamiento.addClass("tipoBusquedaOculto");
                campoBusquedaEmpleado.addClass("tipoBusquedaOculto");
            }
            else
            {
                if(tipoDeBusqueda == "reservacion")
                {
                    campoBusquedaReservacion.removeClass("tipoBusquedaOculto");
                    campoBusquedaId.addClass("tipoBusquedaOculto");
                    campoBusquedaFecha.addClass("tipoBusquedaOculto");
                    campoBusquedaHora.addClass("tipoBusquedaOculto");
                    campoBusquedaTratamiento.addClass("tipoBusquedaOculto");
                    campoBusquedaEmpleado.addClass("tipoBusquedaOculto");
                }
                else
                {
                    if(tipoDeBusqueda == "tratamiento")
                    {
                        campoBusquedaTratamiento.removeClass("tipoBusquedaOculto");
                        campoBusquedaId.addClass("tipoBusquedaOculto");
                        campoBusquedaFecha.addClass("tipoBusquedaOculto");
                        campoBusquedaHora.addClass("tipoBusquedaOculto");
                        campoBusquedaReservacion.addClass("tipoBusquedaOculto");
                        campoBusquedaEmpleado.addClass("tipoBusquedaOculto");
                    }
                    else
                    {
                        if(tipoDeBusqueda == "empleado")
                        {
                            campoBusquedaEmpleado.removeClass("tipoBusquedaOculto");
                            campoBusquedaId.addClass("tipoBusquedaOculto");
                            campoBusquedaFecha.addClass("tipoBusquedaOculto");
                            campoBusquedaHora.addClass("tipoBusquedaOculto");
                            campoBusquedaReservacion.addClass("tipoBusquedaOculto");
                            campoBusquedaTratamiento.addClass("tipoBusquedaOculto");
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
function resultadoBusquedaServicio()
{
    var tablaBusqueda = "";
    tipoDeBusqueda = $("#selcTipoDeBusqueda").val();
    if(tipoDeBusqueda == "id")
    {
        var valorBuscado = $("#txtBusquedaId").val();
        for(var i = 0; i < servicios.length; i++)
        {
            var idServicio = servicios[i].id;
            if(idServicio == valorBuscado)
            {
                tablaBusqueda += llenarTablaBusquedaServicios(i);
            }
        }
    }
    else
    {
        if(tipoDeBusqueda == "fecha")
        {
            var fechaBuscado = $("#txtFechaBusqueda").val();
            for(var i = 0; i < servicios.length; i++)
            {
                var fechaServicio = servicios[i].fecha;
                if(fechaServicio == fechaBuscado)
                {
                    tablaBusqueda += llenarTablaBusquedaServicios(i);
                }
            }
        }
        else
        {
            if(tipoDeBusqueda == "hora")
            {
                var horaBuscado = $("#txtHoraBusqueda").val();
                for(var i = 0; i < servicios.length; i++)
                {
                    var horaServicio = servicios[i].hora;
                    if(horaServicio == horaBuscado)
                    {
                        tablaBusqueda += llenarTablaBusquedaServicios(i);
                    }
                }
            }
            else
            {
                if(tipoDeBusqueda == "reservacion")
                {
                    var reservacionBuscado = $("#txtReservacionBusqueda").val();
                    for(var i = 0; i < servicios.length; i++)
                    {
                        var reservacionServicio = servicios[i].reservacion;
                        if(reservacionServicio == reservacionBuscado)
                        {
                            tablaBusqueda += llenarTablaBusquedaServicios(i);
                        }
                    }
                }
                else
                {
                    if(tipoDeBusqueda == "tratamiento")
                    {
                        var textoBuscado = $("#txtTratamientoBusqueda").val();
                        textoBuscadoMinusculas = textoBuscado.toLowerCase();
                        for(var i = 0; i < servicios.length; i++)
                        {
                            var tratamientoServicio = servicios[i].tratamiento.toLowerCase();
                            if(tratamientoServicio.indexOf(textoBuscadoMinusculas) !== -1)
                            {
                                tablaBusqueda += llenarTablaBusquedaServicios(i);
                            }
                        }
                    }
                    else
                    {
                        if(tipoDeBusqueda == "empleado")
                        {
                            var textoBuscado = $("#txtEmpleadoBusqueda").val();
                            textoBuscadoMinusculas = textoBuscado.toLowerCase();
                            for(var i = 0; i < servicios.length; i++)
                            {
                                var empleadoServicio = servicios[i].empleado.toLowerCase();
                                if(empleadoServicio.indexOf(textoBuscadoMinusculas) !== -1)
                                {
                                    tablaBusqueda += llenarTablaBusquedaServicios(i);
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
    if(tablaBusqueda == "")
    {
        tablaBusqueda = "<tr><td colspan='8' class='registro text-center'><h4 class='py-4'>No hay resultados de búsqueda</h4></td></tr>";
    }
    $("#datosBusquedaTablaServicios").html(tablaBusqueda);
    actualizarListadoServicios();
}
function llenarTablaBusquedaServicios(i)
{
    var parteTablaBusqueda = ""
    parteTablaBusqueda += "<tr>";
    parteTablaBusqueda += "<td scope='col' class='id_tabla'>"+servicios[i].id+"</td>";
    var fechaGuardada = servicios[i].fecha;
    fechaGuardada.split();
    var fechaMostrada = fechaGuardada[8]+fechaGuardada[9]+"/"+fechaGuardada[5]+fechaGuardada[6]+"/"+fechaGuardada[0]+fechaGuardada[1]+fechaGuardada[2]+fechaGuardada[3];
    parteTablaBusqueda += "<td class='registro'>"+fechaMostrada+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+servicios[i].hora+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+servicios[i].tratamiento+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+servicios[i].empleado+"</td>";
    parteTablaBusqueda += "<td class='registro'><button onclick='cargarModuloInformarServicio("+servicios[i].id+");'><img src='./img/info.svg' alt='Info.' class='icono'></button></td>";
    parteTablaBusqueda += "<td class='registro'><button onclick='cargarModuloEditarServicio("+servicios[i].id+");'><img src='./img/editar.svg' alt='Edit.' class='icono'></button></td>";
    parteTablaBusqueda += "</tr>";
    return parteTablaBusqueda;
}
function mostrarBusqueda()
{
    $("#tablaServicios").hide();
    $("#datosTablaBusquedaServicios").show();
}
function ocultarBusqueda()
{
    $("#tablaServicios").show();
    $("#datosTablaBusquedaServicios").hide();
}
function eliminarServicio()
{
    var servicioEliminado = $("#txtCodigoServicio").val();
    servicios.splice(servicioEliminado-1,1);
    for(var i=servicioEliminado-1; i< servicios.length; i++)
    {
        var pos=i+1;
        servicios[i].id=pos;
        Swal.fire(
        {
            title: "Servicio eliminado exitosamente",
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
            willClose: () => {
                clearInterval(timerInterval);
            }
        });
    }
    regresar();
    actualizarListadoServicios();
}
