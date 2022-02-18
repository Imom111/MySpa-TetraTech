/*
    Proyecto: Sitio web – MySpa                                                         
    Equipo desarrollador: TetraNet
    Desarrollador(es):   Luis Ángel Gómez Álvarez
    Fecha de inicio: Lunes 19 de julio de 2021
    Versión: 5.0 (28 de noviembre de 2021 - Iván Moisés Ornelas Meza)
    Descripción general del archivo JavaScript: Este archivo JavaScript es el complemento para el uso de la interfaz
    de "login", contiene las instrucciones requeridas para su correcto funcionamiento al momento de gestionar
    usuarios.
*/

function registrarUsuario(){
    // Obtener RFC en mayúsculas
    var rfcEmp = $("#txtRfcRegistro").val();
    rfcEmp = rfcEmp.toUpperCase();
    
    // Obtener fecha para almacenarlo en número persona
    var fecha = new Date();
    var anio = fecha.getFullYear();
    var mes = 1+(fecha.getMonth());
    if(mes < 10){
        var mesCeros = "0"+mes;
    }else{
        var mesCeros = mes;
    }
    var dia = (fecha.getDate());
    if(dia < 10){
        var diaCeros = "0"+dia;
    }else{
        var diaCeros = dia;
    }
    var hora = fecha.getHours();
    if(hora < 10){
        var horaCeros = "0"+hora;
    }else{
        var horaCeros = hora;
    }
    var minutos = fecha.getMinutes();
    if(minutos < 10){
        var minCeros = "0"+minutos;
    }else{
        var minCeros = minutos;
    }
    var segundos = fecha.getSeconds();
    if(dia < 10){
        var segCeros = "0"+segundos;
    }else{
        var segCeros = segundos;
    }
    var hoyFecha = anio+mesCeros+diaCeros+horaCeros+minCeros+segCeros;
    
    // Se concatena todo para obtener el número de cliente
    var num = "";
    num += "C";
    num += rfcEmp.charAt(0);
    num += rfcEmp.charAt(1);
    num += rfcEmp.charAt(2);
    num += rfcEmp.charAt(3);
    num += hoyFecha;
    var numeroCl = num;
    
    var usuario;
    var idUsuario = 0;
    var nombreUsuario = $("#txtUsuarioRegistro").val();
    var contraseniaUsuario = $("#txtContraseniaRegistro").val();
    var rolUsuario = "Cliente";
    usuario = {"id": idUsuario, "nombreUsu": nombreUsuario, "contrasenia": contraseniaUsuario, "rol":rolUsuario};
    
    var persona;
    var idPersona = 0;
    var nombrePersona = $("#txtNombreRegistro").val();
    var apellidoPPersona = $("#txtApellidoPRegistro").val();
    var apellidoMPersona = $("#txtApellidoMRegistro").val();
    var generoPersona = $("#txtGeneroRegistro").val();
    var domicilioPersona = $("#txtDomicilioRegistro").val();
    var telefonoPersona = $("#txtTelefonoRegistro").val();
    var rfcPersona = rfcEmp;
    persona = {"id": idPersona, "nombre": nombrePersona, "apellidoP": apellidoPPersona, "apellidoM": apellidoMPersona,
    "genero": generoPersona, "domicilio": domicilioPersona, "telefono": telefonoPersona, "rfc": rfcPersona};
    
    var cliente;
    var idCliente = 0;
    var numeroUnico = numeroCl;
    var correoCliente = $("#txtCorreoRegistro").val();
    var estatusCliente = 1;
    var codigoFotoCliente = "";
    cliente = {"id": idCliente, "numUnico": numeroUnico, "correo": correoCliente,
        "estatus": estatusCliente, "foto": codigoFotoCliente, "rutaFoto": "",
        "persona": persona, "usuario": usuario};
    
    var data = {"cliente": JSON.stringify(cliente)};
    $.ajax({
        "url": "api/cliente/insert",
        "type": "POST",
        "async": true,
        "data": data
    }).done(function (data) {
        if (data.result != null) {
            Swal.fire("Completado", "Bienvenido a MySpa", "success");
            $("#txtUsuarioC").val(nombreUsuario);
            $("#txtContraseniaC").val(contraseniaUsuario);
            validarCliente();
        } else if (data.error != null) {
                Swal.fire("Error", "Ocurrió un error, verifica los datos o llama a soporte técnico", "warning");
            }
        }
    );
}

function anchoPage(){
    var formularioIniciar = document.querySelector(".formularioIniciar");
    var formularioRegistrar = document.querySelector(".formularioRegistrar");
    var contenedorIniciarRegistrar = document.querySelector(".contenedorIniciarRegistrar");
    var cajaTraseraIniciar = document.querySelector(".cajaTrasera-login");
    var cajaTraseraRegistrar = document.querySelector(".cajaTrasera-register");
    if(window.innerWidth > 850){
        cajaTraseraRegistrar.style.display = "block";
        cajaTraseraIniciar.style.display = "block";
    }else{
        cajaTraseraRegistrar.style.display = "block";
        cajaTraseraRegistrar.style.opacity = "1";
        cajaTraseraIniciar.style.display = "none";
        formularioIniciar.style.display = "block";
        contenedorIniciarRegistrar.style.left = "0px";
        formularioRegistrar.style.display = "none";   
    }
}

function animacionIniciarSesion(){
    var formularioIniciar = document.querySelector(".formularioIniciar");
    var formularioRegistrar = document.querySelector(".formularioRegistrar");
    var contenedorIniciarRegistrar = document.querySelector(".contenedorIniciarRegistrar");
    var cajaTraseraIniciar = document.querySelector(".cajaTrasera-login");
    var cajaTraseraRegistrar = document.querySelector(".cajaTrasera-register");
    if(window.innerWidth > 850){
        formularioIniciar.style.display = "block";
        contenedorIniciarRegistrar.style.left = "10px";
        formularioRegistrar.style.display = "none";
        cajaTraseraRegistrar.style.opacity = "1";
        cajaTraseraIniciar.style.opacity = "0";
    }else{
        formularioIniciar.style.display = "block";
        contenedorIniciarRegistrar.style.left = "0px";
        formularioRegistrar.style.display = "none";
        cajaTraseraRegistrar.style.display = "block";
        cajaTraseraIniciar.style.display = "none";
    }
}

function animacionRegistrarUsuario(){
    var formularioIniciar = document.querySelector(".formularioIniciar");
    var formularioRegistrar = document.querySelector(".formularioRegistrar");
    var contenedorIniciarRegistrar = document.querySelector(".contenedorIniciarRegistrar");
    var cajaTraseraIniciar = document.querySelector(".cajaTrasera-login");
    var cajaTraseraRegistrar = document.querySelector(".cajaTrasera-register");
    if(window.innerWidth > 850){
        formularioRegistrar.style.display = "block";
        contenedorIniciarRegistrar.style.left = "410px";
        formularioIniciar.style.display = "none";
        cajaTraseraRegistrar.style.opacity = "0";
        cajaTraseraIniciar.style.opacity = "1";
    }else{
        formularioRegistrar.style.display = "block";
        contenedorIniciarRegistrar.style.left = "0px";
        formularioIniciar.style.display = "none";
        cajaTraseraRegistrar.style.display = "none";
        cajaTraseraIniciar.style.display = "block";
        cajaTraseraIniciar.style.opacity = "1";
    }
}

function validarEmpleado(){
    var u = $("#txtUsuarioE").val();
    var p = $("#txtContraseniaE").val();
    var data = {"nU":u,"c":p};
    $.ajax({
        "url" : "api/log/inE",
        "type": "POST",
        "data": data,
        "async": true
    }
    ).done(function(data){
        if (data == null) {
            Swal.fire("Acceso denegado","El usuario o contraseña no coinciden","error");
            $("#txtUsuario").val("");
            $("#txtContrasenia").val("");
        }else if (data.error != null) {
            Swal.fire("Error", data.error,"error");
        }else if (data.id != 0) {
            sessionStorage.setItem("nombreUsuario",data.usuario.nombreUsu);
            sessionStorage.setItem("idEmpleado",data.id);
            sessionStorage.setItem("token",data.usuario.token);
            window.location = "empleados.html";
        }
    });
}

function validarCliente(){
    var u = $("#txtUsuarioC").val();
    var p = $("#txtContraseniaC").val();
    var data = {"nU":u,"c":p};
    $.ajax({
        "url" : "api/log/inC",
        "type": "POST",
        "data": data,
        "async": true
    }
    ).done(function(data){
        if (data == null) {
            Swal.fire("Acceso denegado","El usuario o contraseña no coinciden","error");
            $("#txtUsuario").val("");
            $("#txtContrasenia").val("");
        }else if (data.error != null) {
            Swal.fire("Error", data.error,"error");
        }else if (data.id != 0) {
            sessionStorage.setItem("nombreUsuario",data.usuario.nombreUsu);
            sessionStorage.setItem("idCliente",data.id);
            sessionStorage.setItem("token",data.usuario.token);
            window.location = "reservacionesCliente.html";
        }
    });
}

function loginEmpleados() {
    $("#loginEmpleados").show();
    $("#loginClientes").hide();
    $("#btnPerfil").attr("onclick","loginClientes()");
    $("#nombreUsuario").html("Empleados");
    $("#txtUsuarioC").val();
    $("#txtContraseniaC").val();
}

function loginClientes() {
    $("#loginClientes").show();
    $("#loginEmpleados").hide();
    $("#btnPerfil").attr("onclick","loginEmpleados()");
    $("#nombreUsuario").html("Clientes");
    $("#txtUsuarioE").val();
    $("#txtContraseniaE").val();
}