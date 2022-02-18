/*
    Proyecto: Sitio web – MySpa                                                         
    Equipo desarrollador: TetraNet
    Desarrollador(es):  Alexis Alfredo Prado Ojeda
    Fecha de inicio: Jueves 11 de noviembre de 2021
    Versión: 1.0
    Descripción general del archivo JavaScript: Este archivo JavaScript es el complemento para el uso de la interfaz
    de "gestión de productos", este archivo contiene las instrucciones para su correcto funcionamiento y gestionamiento de 
    productos.
*/

// Listado de productos por defecto
var productos;
function extraerProductos() {
    productos = null;
    var data = {"t":sessionStorage.getItem("token")};
    $.ajax({
        "url" : "api/producto/getAll",
        "type" : "GET",
        "async" : false,
        "data" : data
    }
    ).done(function(data){
        if (data.error != null) {
            Swal.fire("Error", data.error, "warning");
        }else{
            productos = data;
        }
    });
}

// Sección de funciones
function eliminarProducto(idEnviado) {
    var data = {"id":idEnviado,"t":sessionStorage.getItem("token")};
    $.ajax({
        "url": "api/producto/delete",
        "type": "GET",
        "async": true,
        "data": data
    }).done(function (data) {
        if (data.error != null) {
            Swal.fire("Error", data.error, "warning");
        }else{
            Swal.fire("Ok", data.result, "success");
        }
    });
    extraerProductos();
    actualizarListadoProductos();
    resultadoBusquedaProducto();
}

function cargarModuloAgregarProducto()
{
    $("#seccionGestionarProducto").show();
    $("#tablaProductos").removeClass("col-md-12");
    $("#tablaProductos").addClass("col-md-8");
    $("#datosTablaBusquedaProductos").removeClass("col-md-12");
    $("#datosTablaBusquedaProductos").addClass("col-md-8");
    $.ajax(
        {
            "type" : "GET",
            "url" : "./modulos/agregarProducto.html",
            "async" : true
        }
    ).done(
        function(data)
        {
            $("#seccionGestionarProducto").html(data);
            $("#txtCodigoProducto").val(productos.length+1);
        }
    );
}
function guardarProducto(){
   var producto={};
   producto.nombre = $("#txtNombreProducto").val();
   producto.marca = $("#txtMarcaProducto").val();
   var estatusProd1 = document.getElementById("txtEstatusProducto1").checked;
    var estatusProd2 = document.getElementById("txtEstatusProducto2").checked;
    if(estatusProd1){
        var estatusProd = 1;
    }else{
        if(estatusProd2){
            var estatusProd = 0;
        }
    }
   producto.estatus = estatusProd;
   producto.precioUso = ($("#txtPrecioProducto").val())*1;
    existenciaProducto = false;
    for(var i = 0; i < productos.length; i++){
        if((productos[i].nombre === producto.nombre) && (productos[i].marca === producto.marca)){
            existenciaProducto = true;
        }
    }
    if(existenciaProducto){
        Swal.fire("Error", "Este producto ya existe ", "warning");
    }else{
        if(producto.nombre && producto.marca && producto.precioUso){
            var data = {"p": JSON.stringify(producto),"t":sessionStorage.getItem("token")};
            $.ajax({
                "url" : "api/producto/insert",
                "type" : "GET",
                "async" : true,
                "data" : data
            }).done(function(data){
               if (data.result != null) {
                    Swal.fire("Ok", "Producto guardado exitosamente", "success");
                    limpiarFormularioProductos();
                    actualizarListadoProductos();
                    resultadoBusquedaProducto();
                    cargarModuloAgregarProducto();
                } else {
                    if (data.error != null) {
                        Swal.fire("Error", data.error, "warning");
                    }
                } 
            });
        }
    }
}
function cargarModuloEditarProducto(id_producto)
{
    $("#seccionGestionarProducto").show();
    $("#tablaProductos").removeClass("col-md-12");
    $("#tablaProductos").addClass("col-md-8");
    $("#datosTablaBusquedaProductos").removeClass("col-md-12");
    $("#datosTablaBusquedaProductos").addClass("col-md-8");
    $.ajax(
        {
            "type" : "GET",
            "url" : "./modulos/editarProducto.html",
            "async" : true
        }
    ).done(
        function(data)
        {
            $("#seccionGestionarProducto").html(data);
            var posicionProducto = id_producto-1;
            $("#txtCodigoProducto").val(productos[posicionProducto].id);
            $("#txtNombreProducto").val(productos[posicionProducto].nombre);
            $("#txtMarcaProducto").val(productos[posicionProducto].marca);
            $("#txtPrecioProducto").val(productos[posicionProducto].precioUso);
            var posicionProducto = id_producto-1;
            var estatusActivo = document.getElementById("txtEstatusProducto1");
            var estatusInactivo = document.getElementById("txtEstatusProducto2");
            if(productos[posicionProducto].estatus === 1){
                estatusActivo.setAttribute("checked",true);
            }else{
                if(productos[posicionProducto].estatus === 0){
                    estatusInactivo.setAttribute("checked",true);
                }
            }
        }
    );
}

function editarProducto() {
    var producto = {};
    producto.id = $("#txtCodigoProducto").val();
    producto.nombre = $("#txtNombreProducto").val();
    producto.marca = $("#txtMarcaProducto").val();
    var estatusProd1 = document.getElementById("txtEstatusProducto1").checked;
    var estatusProd2 = document.getElementById("txtEstatusProducto2").checked;
    if (estatusProd1) {
        var estatusProd = 1;
    } else {
        if (estatusProd2) {
            var estatusProd = 0;
        }
    }
    producto.estatus = estatusProd;
    producto.precioUso = ($("#txtPrecioProducto").val()) * 1;
    if (producto.nombre && producto.marca && producto.precioUso) {
        var data = {"p": JSON.stringify(producto),"t":sessionStorage.getItem("token")};
        $.ajax({
            "url": "api/producto/update",
            "type": "GET",
            "async": true,
            "data": data
        }).done(function (data) {
            if (data.result != null) {
                Swal.fire("Ok", data.result, "success");
                actualizarListadoProductos();
                resultadoBusquedaProducto();
            } else {
                if (data.error != null) {
                    Swal.fire("Error", data.error, "warning");
                }
            }
        });
    }
}

function validarPositivo()
{
    $("#txtBusquedaId").on("input", function()
    {
        this.value = this.value.replace(/[^0-9]/g, '').replace(/,/g, '.');
    });
    $("#txtPrecioMinBusqueda").on("input", function()
    {
        this.value = this.value.replace(/[^0-9]/g, '').replace(/,/g, '.');
    });
    $("#txtPrecioMaxBusqueda").on("input", function()
    {
        this.value = this.value.replace(/[^0-9]/g, '').replace(/,/g, '.');
    });
}
function limpiarFormularioProductos()
{
    $("#txtNombreProducto").val("");
    $("#txtMarcaProducto").val("");
    $("#txtPrecioProducto").val("");
    $("#txtEstatusProducto1").checked = true;
    $("#txtEstatusProducto2").checked = false;
}
function regresar()
{
    $("#seccionGestionarProducto").hide();
    $("#tablaProductos").removeClass("col-md-8");
    $("#tablaProductos").addClass("col-md-12");
    $("#datosTablaBusquedaProductos").removeClass("col-md-8");
    $("#datosTablaBusquedaProductos").addClass("col-md-12");
}
function actualizarListadoProductos(){
    extraerProductos();
    var datosTablaProd = "";
    var data = {"estatus":1,"t":sessionStorage.getItem("token")};
    $.ajax({
        "url" : "api/producto/getAllStatus",
        "type" : "GET",
        "async" : false,
        "data" : data
    }
    ).done(function(data){
        var productosActivos = data;
        for(var i = 0; i < productosActivos.length; i++)
        {
            datosTablaProd += "<tr>";
            datosTablaProd += "<td scope='col' class='id_tabla'>"+productosActivos[i].id+"</td>";
            datosTablaProd += "<td class='registro'>"+productosActivos[i].nombre+"</td>";
            datosTablaProd += "<td class='registro'>"+productosActivos[i].marca+"</td>";
            datosTablaProd += "<td class='registro'>$"+productosActivos[i].precioUso+"</td>";
            if(productosActivos[i].estatus == 1){
                datosTablaProd += "<td class='registro' style='background-color: #c6dfc3;'>Activo</td>";
            } else {
                    datosTablaProd += "<td class='registro' style='background-color: #ffa6a6;'>Inactivo</td>";
            }
            datosTablaProd += "<td class='registro'><button onclick='eliminarProducto("+productosActivos[i].id+");'><img src='./img/eliminar.svg' alt='Info.' class='icono'></button></td>";
            datosTablaProd += "<td class='registro'><button onclick='cargarModuloEditarProducto("+productosActivos[i].id+");'><img src='./img/editar.svg' alt='Edit.' class='icono'></button></td>";
            datosTablaProd += "</tr>";
        }
        $("#datosTablaProductos").html(datosTablaProd);
    });
    
    data = {"estatus":0,"t":sessionStorage.getItem("token")};
    $.ajax({
        "url": "api/producto/getAllStatus",
        "type": "GET",
        "async": false,
        "data": data
    }).done(function (data) {
        var productosInactivas = data;
        for (var i = 0; i < productosInactivas.length; i++)
        {
            datosTablaProd += "<tr>";
            datosTablaProd += "<td scope='col' class='id_tabla'>" + productosInactivas[i].id + "</td>";
            datosTablaProd += "<td class='registro'>" + productosInactivas[i].nombre+ "</td>";
            datosTablaProd += "<td class='registro'>" + productosInactivas[i].marca+ "</td>";
            datosTablaProd += "<td class='registro'>$" + productosInactivas[i].precioUso+ "</td>";
            if (productosInactivas[i].estatus == 1){
                datosTablaProd += "<td class='registro' style='background-color: #c6dfc3;'>Activo</td>";
            } else {
                datosTablaProd += "<td class='registro' style='background-color: #ffa6a6;'>Inactivo</td>";
            }
            datosTablaProd += "<td class='registro'><button onclick='eliminarProducto(" + productosInactivas[i].id + ");'><img src='./img/eliminar.svg' alt='Info.' class='icono'></button></td>";
            datosTablaProd += "<td class='registro'><button onclick='cargarModuloEditarProducto(" + productosInactivas[i].id + ");'><img src='./img/editar.svg' alt='Edit.' class='icono'></button></td>";
            datosTablaProd += "</tr>";
        }
        $("#datosTablaProductos").html(datosTablaProd);
    });
}
function limpiarFormularioBuscar()
{
    botonBuscar = document.getElementById("btnBuscar");
    botonBuscar.setAttribute("disabled",true);
    $("#txtBusquedaId").val("");
    $("#txtNombreBusqueda").val("");
    $("#txtMarcaBusqueda").val("");
    $("#txtPrecioMinBusqueda").val("");
    $("#txtPrecioMaxBusqueda").val("");
    document.querySelectorAll('[name=txtBusquedaEstatus]').forEach((x) => x.checked = false);
}
function habilitarBotonBusqueda()
{
    tipoDeBusqueda = $("#selcTipoDeBusqueda").val();
    botonBuscar = document.getElementById("btnBuscar");
    switch (tipoDeBusqueda){
        case "id":
            idBuscado = $("#txtBusquedaId").val();
            habilitarBotonBusquedaCondicion(idBuscado);
            break;
        case "nombre": 
            nombreBuscado = $("#txtNombreBusqueda").val();
            habilitarBotonBusquedaCondicion(nombreBuscado);
            break;
        case "marca":
            marcaBuscado = $("#txtMarcaBusqueda").val();
            habilitarBotonBusquedaCondicion(marcaBuscado);
            break;
        case "precio":
            precioBuscado = $("#txtPrecioBusqueda").val();
            habilitarBotonBusquedaCondicion(precioBuscado);
            break;
        case "estatus":
            botonBuscar.removeAttribute("disabled");
            break;
        default :
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

function habilitarCampoBusqueda()
{
    tipoDeBusqueda = $("#selcTipoDeBusqueda").val();
    campoBusquedaId = $("#seccionIdBusqueda");
    campoBusquedaNombre = $("#seccionNombreBusqueda");
    campoBusquedaMarca = $("#seccionMarcaBusqueda");
    campoBusquedaPrecio = $("#seccionPrecioBusqueda");
    campoBusquedaEstatus = $("#seccionEstatusBusqueda");
    
    campoBusquedaId.addClass("tipoBusquedaOculto");
    campoBusquedaNombre.addClass("tipoBusquedaOculto");
    campoBusquedaMarca.addClass("tipoBusquedaOculto");
    campoBusquedaPrecio.addClass("tipoBusquedaOculto");
    campoBusquedaEstatus.addClass("tipoBusquedaOculto");
    
    switch (tipoDeBusqueda){
        case "id":
            campoBusquedaId.removeClass("tipoBusquedaOculto");
            break;
        case "nombre":
            campoBusquedaNombre.removeClass("tipoBusquedaOculto");
            break;
        case "marca":
            campoBusquedaMarca.removeClass("tipoBusquedaOculto");
            break;
        case "precio":
            campoBusquedaPrecio.removeClass("tipoBusquedaOculto");
            break;
        case "estatus":
            campoBusquedaEstatus.removeClass("tipoBusquedaOculto");
            break;
        default :
            alert("Error");
            break;
    }
}
function resultadoBusquedaProducto()
{
    extraerProductos();
    var tablaBusqueda = "";
    var tablaBusquedaInactivos = "";
    tipoDeBusqueda = $("#selcTipoDeBusqueda").val();
    switch (tipoDeBusqueda){
        case "id":
            var valorBuscado = $("#txtBusquedaId").val();
            for(var i = 0; i < productos.length; i++){
                var idProducto = productos[i].id;
                if(idProducto == valorBuscado){
                    tablaBusqueda += llenarTablaBusquedaProductos(i);
                }
            }
            break;
        case "nombre":
            var textoBuscado = $("#txtNombreBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for(var i = 0; i < productos.length; i++){
                var nombreProducto = productos[i].nombre.toLowerCase();
                if(nombreProducto.indexOf(textoBuscadoMinusculas) !== -1){
                    if(productos[i].estatus == 0){
                        tablaBusquedaInactivos += llenarTablaBusquedaProductos(i);
                    }else{
                        if(productos[i].estatus == 1){
                            tablaBusqueda += llenarTablaBusquedaProductos(i);
                        }
                    }
                }
            }
            break;
        case "marca":
            var textoBuscado = $("#txtMarcaBusqueda").val();
            textoBuscadoMinusculas = textoBuscado.toLowerCase();
            for(var i = 0; i < productos.length; i++){
                var marcaProducto = productos[i].marca.toLowerCase();
                if(marcaProducto.indexOf(textoBuscadoMinusculas) !== -1){
                    if(productos[i].estatus == 0){
                        tablaBusquedaInactivos += llenarTablaBusquedaProductos(i);
                    }else{
                        if(productos[i].estatus == 1){
                            tablaBusqueda += llenarTablaBusquedaProductos(i);
                        }
                    }
                }
            }
            break;
        case "precio":
            var valor1 = ($("#txtPrecioMinBusqueda").val()) *1;
            var valor2 = ($("#txtPrecioMaxBusqueda").val()) *1;
            if(valor1 <= valor2){
                var valorInferior = valor1;
                var valorSuperior = valor2;
            } else {
                var valorInferior = valor2;
                var valorSuperior = valor1;
            }
            for(var i = 0; i< productos.length; i++){
                var precioProducto = productos[i].precioUso;
                if(precioProducto >= valorInferior && precioProducto <= valorSuperior){
                    if(productos[i].estatus == 0){
                        tablaBusquedaInactivos += llenarTablaBusquedaProductos(i);
                    } else {
                        if(productos[i].estatus == 1){
                            tablaBusqueda += llenarTablaBusquedaProductos(i);
                        }
                    }
                }
            }
            break;
            case "estatus":
                var estatusActivoBuscado = document.getElementById("txtBusquedaEstatus1").checked;
                var estatusInactivoBuscado = document.getElementById("txtBusquedaEstatus2").checked;
                if (estatusActivoBuscado){
                    var textoBuscado = 1;
                }else{
                    if (estatusInactivoBuscado){
                        var textoBuscado = 0;
                    }
                }
                for (var i = 0; i < productos.length; i++){
                    var estatusProducto = productos[i].estatus;
                    if (estatusProducto == textoBuscado){
                        tablaBusqueda += llenarTablaBusquedaProductos(i);
                    }
                }
                break;
            default :
                tablaBusqueda = "";
                break; 
    }
    tablaBusqueda += tablaBusquedaInactivos;
    if(tablaBusqueda == "")
    {
        tablaBusqueda = "<tr><td colspan='8' class='registro text-center'><h4 class='py-4'>No hay resultados de búsqueda</h4></td></tr>";
    }
    $("#datosBusquedaTablaProductos").html(tablaBusqueda);
    actualizarListadoProductos();
}

function llenarTablaBusquedaProductos(i)
{
    var parteTablaBusqueda = ""
    parteTablaBusqueda += "<tr>";
    parteTablaBusqueda += "<td scope='col' class='id_tabla'>"+productos[i].id+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+productos[i].nombre+"</td>";
    parteTablaBusqueda += "<td class='registro'>"+productos[i].marca+"</td>";
    parteTablaBusqueda += "<td class='registro'>$"+productos[i].precioUso+"</td>";
    if(productos[i].estatus == 1){
        parteTablaBusqueda += "<td class='registro' style='background-color: #c6dfc3;'>Activo</td>";
    } else {
        parteTablaBusqueda += "<td class='registro' style='background-color: #ffa6a6;'>Inactivo</td>";
    }
    parteTablaBusqueda += "<td class='registro'><button onclick='eliminarProducto("+productos[i].id+");'><img src='./img/eliminar.svg' alt='Info.' class='icono'></button></td>";
    parteTablaBusqueda += "<td class='registro'><button onclick='cargarModuloEditarProducto("+productos[i].id+");'><img src='./img/editar.svg' alt='Edit.' class='icono'></button></td>";
    parteTablaBusqueda += "</tr>";
    return parteTablaBusqueda;
}
function mostrarBusqueda()
{
    $("#tablaProductos").hide();
    $("#datosTablaBusquedaProductos").show();
}
function ocultarBusqueda()
{
    $("#tablaProductos").show();
    $("#datosTablaBusquedaProductos").hide();
}