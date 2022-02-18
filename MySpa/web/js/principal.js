/*
    Proyecto: Sitio web – MySpa                                                         
    Equipo desarrollador: TetraNet
    Desarrollador(es):   Iván Moisés Ornelas Meza
    Fecha de inicio: Lunes22 de noviembre de 2021
    Versión: 1.0
    Descripción general del archivo JavaScript: Este archivo JavaScript es el complemento para el uso de las interfaces
    de bienvenida de la página MySpa
*/
function cargarUsuario() {
    var nombreUsuario = sessionStorage.getItem("nombreUsuario");
    if (nombreUsuario != null) {
        $("#nombreUsuario").html(nombreUsuario);
    }else{
        $("#nombreUsuario").html("Iniciar sesión");
    }
}

function getionarPerfil() {
    var idCliente = sessionStorage.getItem("idCliente");
    var idEmpleado = sessionStorage.getItem("idEmpleado");
    if (idCliente != null) {
        window.location = "reservacionesCliente.html";
    }else{
        if (idEmpleado != null) {
            window.location = "empleados.html";
        }else{
            window.location = "login.html";
        }
    }
}
