/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.controllers;

import com.mycompany.models.Documento;
import com.mycompany.models.EmpresaCorreo;
import com.mycompany.models.EnteCorreo;
import com.mycompany.models.Envio;
import com.mycompany.models.Persona;
import com.mycompany.models.Services.service;
import com.mycompany.view.DatosCorreo_Empresa;
import com.mycompany.view.EnviarDocs;
import com.mycompany.view.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Ignacio
 */
public class ControladorEnviarDocs implements ActionListener{
    private service modelo;
    private EnviarDocs enviarDocs;
    private DatosCorreo_Empresa enviarDocs2;
    private Menu vista;
    
    public ControladorEnviarDocs(EnviarDocs enviarDocs, DatosCorreo_Empresa enviarDocs2,service modelo, Menu vista) {
        this.enviarDocs = enviarDocs;
        this.enviarDocs2 = enviarDocs2;
        this.modelo = modelo;
        this.vista = vista;

        // Agregar ActionListeners
        vista.EnviarDocsFlotButton.addActionListener(this);
        enviarDocs.siguienteButton.addActionListener(this);
        enviarDocs2.TerminarButton.addActionListener(this);
    }
             
    
    public void actionPerformed(ActionEvent ae) {
        //Si se preciona el boton enviar en el menu
        if (ae.getSource() == vista.EnviarDocsFlotButton) {
            vista.dispose();
            enviarDocs.setVisible(true);
            //Si se preciona el boton siguiente
        } else if (ae.getSource() == enviarDocs.siguienteButton) {
            if (validarCamposPanel1()) {
                guardarDatosPanel1();
                enviarDocs.dispose();
                enviarDocs2.setVisible(true);
            }
            //Si se preciona el boton Terminar
        } else if (ae.getSource() == enviarDocs2.TerminarButton) {
            //validamos que no haya campos vacios
            if (validarCamposPanel2()) {                
                guardarDatosPanel2();
               //Guardamos en base de datos
                if (modelo.guardarEnBD(modelo.getDocumento(), modelo.getPersona(), modelo.getEnvio(), modelo.getEmpresa() )) {
                    JOptionPane.showMessageDialog(null, "Datos guardados correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Ocurrió un error al guardar los datos.");
                }
                enviarDocs2.dispose();
                vista.setVisible(true);
            }
        } 
    }
private boolean validarCamposPanel1() {
        if (enviarDocs.destinatarioField.getText().isEmpty() ||
            enviarDocs.autorField.getText().isEmpty() ||
            enviarDocs.palabrasClavesField.getText().isEmpty() ||
            enviarDocs.nombreTextField.getText().isEmpty() ||
            enviarDocs.telefonoTextField.getText().isEmpty() ||
            enviarDocs.dirreccionTextField.getText().isEmpty() ||
            enviarDocs.CargoTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos del primer panel.");
            return false;
        }
        return true;
    }

    private void guardarDatosPanel1() {
        Documento documentos = modelo.getDocumento();
        Persona persona = modelo.getPersona();
        EnteCorreo enteCorreo = modelo.getEnteCorreo();

        documentos.setDestinatario(enviarDocs.destinatarioField.getText());
        documentos.setAutor(enviarDocs.autorField.getText());
        documentos.setFecha_creacion(String.valueOf(LocalTime.now()));
        documentos.setPalabraClave(stringAList(enviarDocs.palabrasClavesField.getText()));

        persona.setNombre(enviarDocs.nombreTextField.getText());
        persona.setTelefono(enviarDocs.telefonoTextField.getText());
        persona.setFecha_ingreso(Date.valueOf(LocalDate.now()));  // Ajustar fecha según necesidad
        persona.setDireccion(enviarDocs.dirreccionTextField.getText());
        persona.setCargo(enviarDocs.CargoTextField.getText());
        

        enteCorreo.setNombre(enviarDocs.nombreEnteDocField.getText());
        enteCorreo.setDireccion(enviarDocs.dirreccionEnteDocField.getText());
        enteCorreo.setTelefono(Integer.parseInt(enviarDocs.telefonoEnteField.getText()));
       
    }

    private boolean validarCamposPanel2() {
        if (enviarDocs2.NombreEmpresaField.getText().isEmpty() ||
            enviarDocs2.EmpresaDireccionField.getText().isEmpty() ||
            enviarDocs2.EmpresaTelefonoField.getText().isEmpty() ||
            enviarDocs2.EncargadoCorreoField.getText().isEmpty() ||
            enviarDocs2.numSegTextField.getText().isEmpty() ||
            enviarDocs2.nombreEmpresaEnviTextField.getText().isEmpty() ||
            enviarDocs2.jComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos del segundo panel.");
            return false;
        }
        return true;
    }

    private void guardarDatosPanel2() {
       
        EmpresaCorreo empresaCorreo = modelo.getEmpresa();
           
           empresaCorreo.setNombre(enviarDocs2.NombreEmpresaField.getText());
           empresaCorreo.setEncargado(enviarDocs2.EncargadoCorreoField.getText());
           empresaCorreo.setDireccion(enviarDocs2.EmpresaDireccionField.getText());
           empresaCorreo.setTelefono(enviarDocs2.EmpresaTelefonoField.getText());
        
        Envio envio = modelo.getEnvio();

        envio.setEstado_enviado("Enviado".equals((String) enviarDocs2.jComboBox.getSelectedItem()));
        envio.setNro_seguimiento(Integer.parseInt(enviarDocs2.numSegTextField.getText()));

        Documento documentos = modelo.getDocumento();
        EnteCorreo enteCorreo = modelo.getEnteCorreo();
        Persona persona = modelo.getPersona();

        documentos.setEnvio(envio);
        documentos.setSe_envia(enteCorreo);
        documentos.setTrabaja(persona);
    }


    private List<String>stringAList(String s){
        List<String> lst = List.of(s.split(","));
        System.out.println(lst);
    return lst;
}
}       
 