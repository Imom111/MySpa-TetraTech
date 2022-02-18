/*
    Proyecto: Sitio web – MySpa                                                         
    Equipo desarrollador: TetraNet
    Desarrollador(es):   Luis Ángel Gómez Álvarez
    Fecha de inicio: Lunes 19 de julio de 2021
    Versión: 4.0 (24 de noviembre de 2021 - Iván Moisés Ornelas Meza)
    Descripción general del archivo JavaScript: Este archivo JavaScript es el complemento para el uso de la interfaz
    de "perfil para empleado", contiene las instrucciones requeridas para su correcto funcionamiento al momento de gestionar
    el perfil del empleado.
*/

var empleado;
function extraerEmpleado() {
    empleado = null;
    var data = {"filter":sessionStorage.getItem("idEmpleado"),"t":sessionStorage.getItem("token")};
    $.ajax({
        "url" : "api/empleado/search",
        "type" : "GET",
        "async" : false,
        "data": data
    }
    ).done(function(data){
        if (data.error != null) {
            Swal.fire("Error", data.error, "warning");
        }else{
            empleado = data;
        }
    });
}

function cerrarSesion(){
    var data;
    var url;
    if (sessionStorage.getItem("idEmpleado")) {
        data = {"idEmpleado":sessionStorage.getItem("idEmpleado")};
        url = "api/log/ouE";
    }else if(sessionStorage.getItem("idCliente")){
        data = {"idCliente":sessionStorage.getItem("idCliente")};
        url = "api/log/ouC";
    }else{
        data = "";
        url = "";
    }
    $.ajax({
        "url" : url,
        "type" : "POST",
        "async" : false,
        "data": data
    }
    ).done(function(data){
        if (data.error != null) {
            Swal.fire("Error", data.error, "warning");
        }else{
            sessionStorage.clear();
            window.location="./login.html";
        }
    });
}

function cargarDatos(){
    extraerEmpleado();
    $("#imgFotoPerfil").attr("src","data:image/jpg;base64," + empleado.foto);
    $("#nombreCompletoPerfil").html(empleado.persona.nombre + " " + empleado.persona.apellidoP + " " + empleado.persona.apellidoM);
    $("#txtNombrePerfil").val(empleado.persona.nombre);
    $("#txtApellidoPPerfil").val(empleado.persona.apellidoP);
    $("#txtApellidoMPerfil").val(empleado.persona.apellidoM);
    $("#txtGeneroPerfil").val(empleado.persona.genero)
    $("#txtDomicilioPerfil").val(empleado.persona.domicilio);;
    $("#txtRfcPerfil").val(empleado.persona.rfc);
    $("#txtTelefonoPerfil").val(empleado.persona.telefono);
    
    $("#txtUsuarioPerfil").val(empleado.usuario.nombreUsu);
    $("#txtContraseniaPerfil").val(empleado.usuario.contrasenia);
    $("#txtRolPerfil").val(empleado.usuario.rol);
    $("#txtTokenPerfil").val(empleado.usuario.token);
    
    $("#txtNumEmpleadoPerfil").val(empleado.numEmpleado);
    $("#txtPuestoPerfil").val(empleado.puesto);
    if (empleado.estatus == 1) {
        $("#txtEstatusPerfil").val("Activo");
    }else if (empleado.estatus == 0) {
        $("#txtEstatusPerfil").val("Activo");
    }
}

function guardarPerfil(){
    var objEmpleado = {};
    objEmpleado.id = empleado.id;
    objEmpleado.numEmpleado = empleado.numEmpleado;
    objEmpleado.puesto = empleado.puesto;
    objEmpleado.estatus = empleado.estatus;
    objEmpleado.foto = $("#txtCodigoImagenCargadaPerfil").val();
    objEmpleado.rutaFoto = "";
    var objPersona = {};
    objPersona.id = empleado.persona.id;
    objPersona.nombre = $("#txtNombrePerfil").val();
    objPersona.apellidoP = $("#txtApellidoPPerfil").val();
    objPersona.apellidoM = $("#txtApellidoMPerfil").val();
    objPersona.genero = $("#txtGeneroPerfil").val();
    objPersona.domicilio = $("#txtDomicilioPerfil").val();
    objPersona.telefono = $("#txtTelefonoPerfil").val();
    objPersona.rfc = $("#txtRfcPerfil").val();
    var objUsuario = {};
    objUsuario.id = empleado.usuario.id;
    objUsuario.nombreUsu = empleado.usuario.nombreUsu;
    objUsuario.contrasenia = $("#txtContraseniaPerfil").val();
    objUsuario.rol = empleado.usuario.rol;
    // objUsuario.token = empleado.usuario.token;
    
    objEmpleado.persona = objPersona;
    objEmpleado.usuario = objUsuario;
    var data = {"empleado": JSON.stringify(objEmpleado)};
    $.ajax({
        "url": "api/empleado/update",
        "type": "POST",
        "async": true,
        "data": data
    }).done(function (data) {
        if (data.result != null) {
            Swal.fire("Ok", data.result, "success");
            cargarDatos();
        } else {
            if (data.error != null) {
                Swal.fire("Error", data.error, "warning");
            }
        }
    });
}

function irGestiones() {
    window.location = "empleados.html";
}

function recargarImagen() {
    var codigoFotoPerfil = $("#txtCodigoImagenCargadaPerfil").val();
    $("#imgFotoPerfil").attr("src","data:image/jpg;base64," + codigoFotoPerfil);
}

function limpiarModalIngresarImagen() {
    document.getElementById("txtFotoPerfil").value = "";
    document.getElementById("imgFotoCargadaPerfil").src = "";
    document.getElementById("txtCodigoImagenCargadaPerfil").value = "";
}

function mostrarImagen() {
    var archivoF = document.getElementById("txtFotoPerfil");
    var imagen = document.getElementById("imgFotoCargadaPerfil");
    var textoB64;
    if (archivoF.files.length > 0) {
        var fr = new FileReader();
        fr.onload = function(){
            imagen.src = "";
            imagen.src = fr.result;
            textoB64 = fr.result.replace(/^data:image\/(png|jpg|jpeg);base64,/,"");
            $("#txtCodigoImagenCargadaPerfil").val(textoB64);
        };
        fr.readAsDataURL(archivoF.files[0]);
    }
}

function cargarInfoImagen() {
    $("#imgFotoCargadaPerfil").attr("src","data:image/jpg;base64," + empleado.foto);
}