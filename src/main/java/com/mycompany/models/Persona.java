/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.models;

import java.sql.Date;


public class Persona {
    
    private String nombre;
    private String direccion;
    private String telefono;
    private String fecha_ingreso;
    private String cargo;

    public Persona() {
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setFecha_ingreso(String fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    
}
