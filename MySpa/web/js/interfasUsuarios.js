/*
    Proyecto: Sitio web – MySpa                                                         
    Equipo desarrollador: TetraNet
    Desarrollador(es):   Luis Ángel Gómez Álvarez
    Fecha de inicio: Lunes 19 de julio de 2021
    Versión: 1.0
    Descripción general del archivo JavaScript: Este archivo JavaScript es el complemento para el uso de la interfaz
    de "login", contiene las instrucciones requeridas para su correcto funcionamiento enfocado a la validación de datos.
*/

// Sección de eventos
document.querySelector("#botonIniciarRegistrar").addEventListener("click",iniciarSesionValidacion);

// Sección de funciones
function iniciarSesionValidacion()
{
    var sUsuario = "";
    var sContrasena = "";
    var bAcceso = false;
    sUsuario = document.querySelector("#txtUsuario").value;
    sContrasena = document.querySelector("#txtContrasena").value;
    bAcceso = validarCredenciales(sUsuario,sContrasena);
    if(bAcceso == true)
    {
        ingresar();
    }
}
function ingresar()
{
    var puesto =  sessionStorage.getItem("puestoUsuarioActivo");
    switch(puesto)
    {
        case "Empleado":
            window.location.href = "./empleados.html";
        break;
        case "Cliente":
            window.location.href = "./reservacionesCliente.html";
        break;
    }
}