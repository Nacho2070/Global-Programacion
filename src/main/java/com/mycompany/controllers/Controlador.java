package com.mycompany.controllers;

import com.mycompany.models.Documento;
import com.mycompany.models.Envio;
import com.mycompany.models.repository.Repository;
import com.mycompany.view.Vista;

import javax.naming.InvalidNameException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import com.mycompany.models.EnteCorreo;
import com.mycompany.models.Persona;
import com.mycompany.models.Services.Modelo;

public class Controlador implements ActionListener {
    private Vista vista;
    private Modelo modelo;
    private Repository repository;
    private Documento documentos;
    
    public Controlador(Vista vista, Modelo modelo,Repository repository,Documento documento) {
        this.vista = vista;
        this.modelo = modelo;
        this.repository = repository;
        this.documentos = new Documento();
        //Buscar boton
        vista.consultarPorPalabraButton.addActionListener( this);
        //Enviar boton
        vista.enviarButton.addActionListener(this);
        //Cantidad en espera boton
        vista.cantidadEnEsperaButton.addActionListener( this);
        //Limpiar campos boton
        vista.limpiarButton.addActionListener(this);
        //Boton para el empleado que mas confecciono docs
         vista.cantidadConfeccButton.addActionListener(this);
        // ComboBox
        vista.jComboBox.addActionListener(this);
        
        //Fields
        vista.palabraClaveTextField.addActionListener(this);
        vista.telefonoTextField.addActionListener(this);
        vista.nombreTextField.addActionListener(this);
        vista.CargoTextField.addActionListener(this);
        vista.dirreccionTextField.addActionListener(this);
        vista.fechIngreTextField.addActionListener(this);
        vista.autorField.addActionListener(this);
        vista.telefonoEnteField.addActionListener(this);
        vista.numSegTextField.addActionListener(this);
        vista.palabrasClavesField.addActionListener(this);

        
        
          }
    public void iniciar(){
        vista.setTitle("Documentos");
        vista.setLocationRelativeTo(null);
    }
    //Usamos el objeto ae para saber que boton se presionar

    public void actionPerformed(ActionEvent ae) {

        // Boton para buscar con palabra ingresada
            if (ae.getSource() == vista.consultarPorPalabraButton) {
        try {
            String palabra = vista.palabraClaveTextField.getText(); 
            //Buscamos en base de datos la palabra ingresada
            List<Documento> resultados = this.modelo.documentoQueIncluyen(palabra);
            System.out.println(resultados);
            if (resultados.isEmpty()) {
                vista.mostrarError("No se encontraron usuarios con ese nombre");
                limpiarCajas();
            } else {
                vista.mostrarDocumentos(resultados);
                limpiarCajas();
            }
        } catch (InvalidNameException e) {
            throw new RuntimeException(e);
        }
    }
    

        //Si se presiona el boton enviar vamos a guardar los datos a una bd
        System.out.println(ae.getSource() == vista.enviarButton);
        if (ae.getSource() == vista.enviarButton) {

            // Validamos si no hay campos vacios
            if (!vista.validarCampos()){
                vista.mostrarError("Error faltan campos por rellenar");
               // limpiarCajas();
            }
            
            Envio envio = modelo.getEnvio();
            Persona persona = modelo.getPersona();
            EnteCorreo enteCorreo = modelo.getEnteCorreo();
            
            
            //Guardamos en Envio
            envio.setEstado_enviado(false);
            //if("Enviado".equals(vista.jComboBox.getSelectedItem())){
              //  envio.setEstado_enviado(true);
            //}
            envio.setNro_seguimiento(Integer.parseInt(vista.numSegTextField.getText()));
            
            //Guardamos en persona
            persona.setNombre(vista.nombreTextField.getText());
            persona.setTelefono(vista.telefonoTextField.getText());
            persona.setFecha_ingreso(vista.CargoTextField.getText());
            persona.setDireccion(vista.dirreccionTextField.getText());
            persona.setCargo(vista.CargoTextField.getText());
            
            //Guardamos en EnteCorreo
            enteCorreo.setNombre(vista.nombreEnteDocField.getText());
            enteCorreo.setDireccion(vista.dirreccionEnteDocField.getText());
            enteCorreo.setTelefono(Integer.parseInt(vista.telefonoEnteField.getText()));
            enteCorreo.setEncargado(vista.EncargadoEnteField.getText());
            enteCorreo.setEnviado(documentos);
            
            //Guardamos en documentos
            documentos.setDestinatario(vista.destinatarioField.getText());
            documentos.setAutor(vista.autorField.getText());
            documentos.setFecha_creacion(String.valueOf(LocalTime.now()));
            documentos.setPalabraClave(stringAList(vista.palabrasClavesField.getText()));
            documentos.setEnvio(envio);            
            documentos.setSe_envia(enteCorreo);
            documentos.setTrabaja(persona);
            System.out.println(documentos);
            
            //Guardamos en base de datos
            if (this.repository.insertarValoresDocumentosBD(documentos)) {
                vista.mensajeExito("Usuario guardado en la base de datos");
                limpiarCajas();
            } else {
                vista.mostrarError("Ocurrio un error al guardar en base de datos");
            }
        }
        //Metodo cantidad en espera
        if (ae.getSource() == vista.cantidadEnEsperaButton) {

            Optional<Integer> cantidadDocs = modelo.cantidadEnEspera();
            if (cantidadDocs.isPresent()){
                String docs = String.valueOf(cantidadDocs);
                vista.mostrarCantDocs(docs);
            }else {
                vista.mostrarError("No se encontraror documentos en espera");
            }
        }
        //Metodo Empleado que mas confecciono documentos
       if (ae.getSource() == vista.cantidadConfeccButton) {
            
              vista.cantdocsConfeccTextArea.setText(repository.autorMasProductivo());
         
        }
        
        //Metodo para limpiar cajas         
        if(ae.getSource()== vista.limpiarButton){
            limpiarCajas();
        }
    
    }
    private List<String>stringAList(String s){
        List<String> lst = List.of(s.split(","));
        System.out.println(lst);
    return lst;
    }
    public void limpiarCajas() {
        vista.palabraClaveTextField.setText("");
        vista.telefonoTextField.setText("");
        vista.nombreTextField.setText("");
        vista.CargoTextField.setText("");
        vista.dirreccionTextField.setText("");
        vista.fechIngreTextField.setText("");
        vista.autorField.setText("");
        vista.telefonoEnteField.setText("");
        vista.numSegTextField.setText("");
        vista.palabrasClavesField.setText("");
        vista.cantdocsConfeccTextArea.setText("");
        }
    
    
}
        //Si se presiona el boton limpiar se limpian los campos
        

        //Metodo para limpiar cajas
    /*  
    
    if(ae.getSource()== vista.limpiarButton){
            limpiarCajas();
        }
    public void limpiarCajas() {
            vista.palabraClaveTextField.setText(null);
            vista.autorField.setText(null);
            vista.destinatarioField.setText(null);
            vista.resultadoTemp.setText(null);

        }
*/
    
    // Por las dudas
    /*
    private void autorMasProductivo() {
        String autor = Documento.autorMasProductivo(documentos);
        if (autor == null) {
            vista.mostrarError("No hay documentos registrados.");
        } else {
            vista.mostrarAutorMasProductivo(autor);
        }
    }
     */

// Método para manejar la acción de mostrar la cantidad en espera
    /*
    private void cantidadEnEspera() {
        Optional<Integer> cantidad = Optional.of(documentos.stream()
                .mapToInt(doc -> doc.cantidadEnEspera().orElse(0))
                .sum());
        vistas.mostrarCantidadEnEspera(cantidad.map(Object::toString).orElse("0"));
    }
    */

// Método para manejar la acción de buscar documentos por palabra clave
   /*
    private void buscarPorPalabra() throws InvalidNameException {
        String palabraClave = vistas.obtenerPalabraClave();
        List<Documento> resultados = Documento.documentoQueIncluyen(documentos, palabraClave);
        if (resultados.isEmpty()) {
            vistas.mostrarError("No se encontraron documentos con la palabra clave: " + palabraClave);
        } else {
            vistas.mostrarDocumentos(resultados);
        }
    }

    */