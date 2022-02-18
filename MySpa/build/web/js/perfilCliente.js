/*
    Proyecto: Sitio web – MySpa                                                         
    Equipo desarrollador: TetraNet
    Desarrollador(es):   Luis Ángel Gómez Álvarez
    Fecha de inicio: Lunes 19 de julio de 2021
    Versión: 3.0 (24 de noviembre de 2021 - Iván Moisés Ornelas Meza)
    Descripción general del archivo JavaScript: Este archivo JavaScript es el complemento para el uso de la interfaz
    de "perfil para cliente", contiene las instrucciones requeridas para su correcto funcionamiento al momento de gestionar
    el perfil del cliente.
*/

var cliente;
function extraerCliente() {
    cliente = null;
    var data = {"filter":sessionStorage.getItem("idCliente"),"t":sessionStorage.getItem("token")};
    $.ajax({
        "url" : "api/cliente/search",
        "type" : "GET",
        "async" : false,
        "data": data
    }
    ).done(function(data){
        if (data.error != null) {
            Swal.fire("Error", data.error, "warning");
        }else{
            cliente = data;
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
    extraerCliente();
    $("#imgFotoPerfil").attr("src","data:image/jpg;base64," + cliente.foto);
    $("#nombreCompletoPerfil").html(cliente.persona.nombre + " " + cliente.persona.apellidoP + " " + cliente.persona.apellidoM);
    $("#txtNombrePerfil").val(cliente.persona.nombre);
    $("#txtApellidoPPerfil").val(cliente.persona.apellidoP);
    $("#txtApellidoMPerfil").val(cliente.persona.apellidoM);
    $("#txtGeneroPerfil").val(cliente.persona.genero)
    $("#txtDomicilioPerfil").val(cliente.persona.domicilio);;
    $("#txtRfcPerfil").val(cliente.persona.rfc);
    $("#txtTelefonoPerfil").val(cliente.persona.telefono);
    
    $("#txtUsuarioPerfil").val(cliente.usuario.nombreUsu);
    $("#txtContraseniaPerfil").val(cliente.usuario.contrasenia);
    $("#txtRolPerfil").val(cliente.usuario.rol);
    $("#txtTokenPerfil").val(cliente.usuario.token);
    
    $("#txtNumClientePerfil").val(cliente.numUnico);
    $("#txtCorreoPerfil").val(cliente.correo);
    if (cliente.estatus == 1) {
        $("#txtEstatusPerfil").val("Activo");
    }else if (cliente.estatus == 0) {
        $("#txtEstatusPerfil").val("Activo");
    }
}

function guardarPerfil(){
    var objCliente = {};
    objCliente.id = cliente.id;
    objCliente.numUnico = cliente.numUnico;
    objCliente.correo = cliente.correo;
    objCliente.estatus = cliente.estatus;
    objCliente.foto = $("#txtCodigoImagenCargadaPerfil").val();
    objCliente.rutaFoto = "";
    var objPersona = {};
    objPersona.id = cliente.persona.id;
    objPersona.nombre = $("#txtNombrePerfil").val();
    objPersona.apellidoP = $("#txtApellidoPPerfil").val();
    objPersona.apellidoM = $("#txtApellidoMPerfil").val();
    objPersona.genero = $("#txtGeneroPerfil").val();
    objPersona.domicilio = $("#txtDomicilioPerfil").val();
    objPersona.telefono = $("#txtTelefonoPerfil").val();
    objPersona.rfc = $("#txtRfcPerfil").val();
    var objUsuario = {};
    objUsuario.id = cliente.usuario.id;
    objUsuario.nombreUsu = cliente.usuario.nombreUsu;
    objUsuario.contrasenia = $("#txtContraseniaPerfil").val();
    objUsuario.rol = cliente.usuario.rol;
    // objUsuario.token = cliente.usuario.token;
    
    objCliente.persona = objPersona;
    objCliente.usuario = objUsuario;
    var data = {"cliente": JSON.stringify(objCliente)};
    $.ajax({
        "url": "api/cliente/update",
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
    window.location = "reservacionesCliente.html";
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
    $("#imgFotoCargadaPerfil").attr("src","data:image/jpg;base64," + cliente.foto);
}