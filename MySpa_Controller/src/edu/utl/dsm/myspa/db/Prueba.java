package edu.utl.dsm.myspa.db;

import edu.utl.dsm.myspa.controller.*;
import edu.utl.dsm.myspa.model.*;
import java.util.List;

public class Prueba {
    
    public static void main(String[] args)
    {
        //probarCon();
        //probarInsert();
        //probarGetAll();
        //probarGetAllStatus();
        //probarDelete();
        //probarUpdate();
        //probarSearch();
        //probarLogin();
    }
    public static void probarCon()
    {
        ConexionMySQL objCon= new ConexionMySQL();
        try {
            
           objCon.open();
            System.out.println(objCon.toString());
            objCon.close();     
            
        } catch (Exception ex) {
            //Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    public static void probarLogin()
    {
        try {
            ControllerLogeo a = new ControllerLogeo();
            Empleado e = a.LoginEmpleado("Admin", "ADmin");
            System.out.println(e.toString());
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    public static void probarInsert()
    {
         try {
            //Persona per = new Persona("Ivan", "Ornelas", "Meza", "M", "Calle2", "477213", "ABC123");
            //Usuario usu = new Usuario("UsuarioIvan", "ContraseñaIvan", "Jeve", "ABCCCC");
            //Cliente cl = new Cliente("12345678901", "puesto", 1, "foto", "rutafoto", per, usu);
            //ControllerCliente objCC = new ControllerCliente();
            //Tratamiento a = new Tratamiento("nombre", "Descripcion", 100, 1);
            //Sucursal su = new Sucursal(3,"Nombresucursal", "DomSucursal", 11, 8, 1);
            //Sala sa = new Sala("nombre", "descripcion", "foto", "rutaFoto", 0, su);
            Horario objH = new Horario("nuevo", "nuveo2");
            ControllerHorario objA = new ControllerHorario();
            int idG = objA.insert(objH);
            System.out.println(idG);
            
        } catch (Exception ex) {
            //Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    public static void probarSearch() {
        try {
            Horario a = new Horario();
            ControllerHorario objA = new ControllerHorario();
            a = objA.search(4);
            System.out.println(a.toString());
        } catch (Exception ex) {
            //Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    public static void probarUpdate()
    {
         try {
            //Persona per = new Persona(134,"PEPE", "actualizacionapellido", "Meza", "M", "Calle2", "477213", "ABC123");
            //Usuario usu = new Usuario(134,"UsuarioIvan", "ContraseñaIvan", "Jeve", "ABCCCC");
            //Cliente cl = new Cliente("12345678901", "puesto", 1, "foto", "rutafoto", per, usu);
            //Sucursal su = new Sucursal(2,"", "", 0, 0, 0);
            //Sala sa = new Sala(11,"NOMBREEEEEE", "descripcion", "foto", "rutaFoto", 0, su);
            Horario ho = new Horario(1,"aaa", "ee");
            ControllerHorario objA = new ControllerHorario();
            objA.update(ho);
            
        } catch (Exception ex) {
            //Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    
    public static void probarDelete()
    {
        try
        {
            //ControllerCliente objCS = new ControllerCliente();
            ControllerHorario objA = new ControllerHorario();
            System.out.println(objA.delete(6));
        }
        catch (Exception e)
        {
            e.printStackTrace();     
        }
    }
    
    public static void probarGetAll(){
        try {
            ControllerHorario objA = new ControllerHorario();
            List<Horario> a = objA.getAll();
            for (int i = 0; i < a.size(); i++) {
                System.out.println(a.get(i).toString());
            }
        } catch (Exception ex) {
            //Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    
    public static void probarGetAllStatus(){
        try {
            ControllerSala objA = new ControllerSala();
            List<Sala> a = objA.getAllStatus(0);
            for (int i = 0; i < a.size(); i++) {
                System.out.println(a.get(i).toString());
            }
        } catch (Exception ex) {
            //Logger.getLogger(Prueba.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
}
