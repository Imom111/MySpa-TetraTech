/*
    Proyecto: Sitio web – MySpa                                                         
    Equipo desarrollador: TetraNet
    Desarrollador(es):   Iván Moisés Ornelas Meza
    Fecha de inicio: Lunes 22 de noviembre de 2021
    Versión: 1.0
    Descripción general del archivo JavaScript: Este archivo JavaScript es el complemento para el
    majejo de usuarios en todas las interfaces de gestiones.
 */

function cargarUsuarioGestion(){
    if (sessionStorage.getItem("idCliente") || sessionStorage.getItem("idEmpleado")) {
        $("#nombreUsuario").html(nombreUsuario);
    }else{
        window.location = "login.html";
    }
    var nombreUsuario = sessionStorage.getItem("nombreUsuario");
    if (nombreUsuario == null) {
        $("#nombreUsuario").html("null");
    }else{
        $("#nombreUsuario").html(nombreUsuario);
    }
}

function mostrarPerfil(){
    var idCliente = sessionStorage.getItem("idCliente");
    var idEmpleado = sessionStorage.getItem("idEmpleado");
    if (idCliente != null) {
        window.location = "perfilCliente.html";
    }else{
        if (idEmpleado != null) {
            window.location = "perfil.html";
        }
    }
}
