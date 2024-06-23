/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ignacio
 */
public class EnteCorreo {
    
    private String nombre;
    private String direccion;
    private int telefono;
    private String encargado;
    //private ArrayList<Documento> enviado = new ArrayList<>();

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }
    
}
