
package com.mycompany.models;

import java.util.Date;

public class Persona {
    
    private String nombre;
    private String direccion;
    private String telefono;
    private Date fecha_ingreso;
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

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    public String getCargo() {
        return cargo;
    }
    
}
