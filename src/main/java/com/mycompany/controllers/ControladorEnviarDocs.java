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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class ControladorEnviarDocs implements ActionListener {

    private service modelo;
    private EnviarDocs enviarDocs;
    private DatosCorreo_Empresa enviarDocs2;
    private Menu vista;

    public ControladorEnviarDocs(EnviarDocs enviarDocs, DatosCorreo_Empresa enviarDocs2, service modelo, Menu vista) {
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
        try {
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
                    if (modelo.guardarEnBD(modelo.getDocumento(), modelo.getPersona(), modelo.getEnvio(), modelo.getEmpresa())) {
                        JOptionPane.showMessageDialog(null, "Datos guardados correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Ocurrió un error al guardar los datos.");
                    }
                    enviarDocs2.dispose();
                    vista.setVisible(true);
                }
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Error al parsear la fecha: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error: " + e.getMessage());
        }

    }

    private boolean validarCamposPanel1() {
        if (enviarDocs.destinatarioField.getText().isEmpty()
                || enviarDocs.autorField.getText().isEmpty()
                || enviarDocs.palabrasClavesField.getText().isEmpty()
                || enviarDocs.nombreTextField.getText().isEmpty()
                || enviarDocs.telefonoTextField.getText().isEmpty()
                || enviarDocs.dirreccionTextField.getText().isEmpty()
                || enviarDocs.CargoTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos del primer panel.");
            return false;
        }
        return true;
    }

    private void guardarDatosPanel1() throws ParseException {
        //Damos formato a la fecha
        SimpleDateFormat objSDF = new SimpleDateFormat("dd-mm-yyyy");
        Date objDate = new Date();

        Documento documentos = modelo.getDocumento();
        Persona persona = modelo.getPersona();
        EnteCorreo enteCorreo = modelo.getEnteCorreo();

        documentos.setDestinatario(enviarDocs.destinatarioField.getText());
        documentos.setAutor(enviarDocs.autorField.getText());
        documentos.setFecha_creacion(objSDF.parse(objSDF.format(objDate)));
        documentos.setPalabraClave(stringAList(enviarDocs.palabrasClavesField.getText()));

        persona.setNombre(enviarDocs.nombreTextField.getText());
        persona.setTelefono(enviarDocs.telefonoTextField.getText());
        persona.setFecha_ingreso(objSDF.parse(enviarDocs.fechIngreTextField.getText()));
        persona.setDireccion(enviarDocs.dirreccionTextField.getText());
        persona.setCargo(enviarDocs.CargoTextField.getText());

        enteCorreo.setNombre(enviarDocs.nombreEnteDocField.getText());
        enteCorreo.setDireccion(enviarDocs.dirreccionEnteDocField.getText());
        enteCorreo.setTelefono(Integer.parseInt(enviarDocs.telefonoEnteField.getText()));

    }

    private boolean validarCamposPanel2() {
        if (enviarDocs2.NombreEmpresaField.getText().isEmpty()
                || enviarDocs2.EmpresaDireccionField.getText().isEmpty()
                || enviarDocs2.EmpresaTelefonoField.getText().isEmpty()
                || enviarDocs2.EncargadoCorreoField.getText().isEmpty()
                || enviarDocs2.numSegTextField.getText().isEmpty()
                || enviarDocs2.nombreEmpresaEnviTextField.getText().isEmpty()
                || enviarDocs2.jComboBox.getSelectedItem() == null) {
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

    //funcion para convertir string a list
    private List<String> stringAList(String s) {
        //Convertimos la palabra a lista, sacandole la coma y el espacio en blanco
        List<String> lst = List.of(s.split("\\s*,\\s*"));
        return lst;
    }
} 