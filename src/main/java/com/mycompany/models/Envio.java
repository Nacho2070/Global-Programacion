/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.models;

/**
 *
 * @author Ignacio
 */
public class Envio {
      private int nro_seguimiento;
      private boolean estado_enviado;

    public void setNro_seguimiento(int nro_seguimiento) {
        this.nro_seguimiento = nro_seguimiento;
    }

    public void setEstado_enviado(boolean estado_enviado) {
        this.estado_enviado = estado_enviado;
    }

    public boolean isEstado_enviado() {
        return estado_enviado;
    }
}
