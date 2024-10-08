package com.mycompany.controllers;

import com.mycompany.models.Documento;
import com.mycompany.view.Menu;
import javax.naming.InvalidNameException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;
import com.mycompany.models.Services.service;
import com.mycompany.view.BuscarPorPalabraClave;
import com.mycompany.view.CantidadEnEspera;
import com.mycompany.view.EmpleadoQueMasCofeccionoDocs;
import javax.swing.JOptionPane;

//Controlador para los metodos BuscarPorPalabraClave, CantidadEnEspera, EmpleadoQueMasCofeccionoDocs
public class Controlador implements ActionListener {

    private Menu vista;
    private BuscarPorPalabraClave buscar;
    private CantidadEnEspera cantidadEspera;
    private EmpleadoQueMasCofeccionoDocs empMasConfecciono;
    private service modelo;

    public Controlador(Menu vista, service modelo, BuscarPorPalabraClave buscar, CantidadEnEspera cantidadEspera, EmpleadoQueMasCofeccionoDocs empMasConfecciono) {

        this.vista = vista;
        this.buscar = buscar;
        this.cantidadEspera = cantidadEspera;
        this.empMasConfecciono = empMasConfecciono;
        this.modelo = modelo;
        //Buscar boton
        vista.consultarPorPalabraButtonModal.addActionListener(this);
        //Cantidad en espera boton
        vista.cantidadEnEsperaButtonModal.addActionListener(this);
        //Boton para el empleado que mas confecciono docs
        vista.cantidadConfeccButtonModal.addActionListener(this);
        //Boton para buscar por palabras claves
        buscar.consultarPorPalabraButton.addActionListener(this);
        //Botones para abrir las ventanas externas
        buscar.salirButton.addActionListener(this);
        empMasConfecciono.salirButtonEmpleado.addActionListener(this);
        cantidadEspera.salirButtonEspera.addActionListener(this);
    }

    public void iniciar() {
        vista.setTitle("Documentos");
        vista.setLocationRelativeTo(null);
    }

    //Usamos el objeto ae para saber que boton se presiona
    @Override
    public void actionPerformed(ActionEvent ae) {

        // Ventana para buscar por palabra clave ingresada
        if (ae.getSource() == vista.consultarPorPalabraButtonModal) {
            vista.dispose();
            buscar.setVisible(true);
        } else if (ae.getSource() == buscar.consultarPorPalabraButton) {
            try {
                String palabra = buscar.palabraClaveTextField.getText();
                List<Documento> resultados = modelo.documentoQueIncluyen(palabra);
                //Si la palabra no se encuentra mostramos error
                if (resultados.isEmpty()) {
                    System.out.println("No se encontraron documentos con esa palabra clave");
                    limpiarCajasBuscar();
                    //sino se muestra en la vista el resultado
                } else {
                    buscar.mostrarDocumentos(resultados);
                }
            } catch (InvalidNameException e) {
                JOptionPane.showMessageDialog(vista, "Nombre inválido: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        //Boton salir
        } else if (ae.getSource() == buscar.salirButton) {
            buscar.dispose();
            vista.setVisible(true);
        }

        // Ventana cantidad en espera
        if (ae.getSource() == vista.cantidadEnEsperaButtonModal) {
            vista.dispose();
            cantidadEspera.setVisible(true);
            Optional<Integer> cantidadDocs = modelo.cantidadEnEspera();
            if (cantidadDocs.isPresent()) {
                String docs = String.valueOf(cantidadDocs.get());
                cantidadEspera.mostrarCantDocs(docs);
            } else {
                cantidadEspera.mostrarError("No se encontraron documentos en espera");
            }
            //Boton salir
        } else if (ae.getSource() == cantidadEspera.salirButtonEspera) {
            cantidadEspera.dispose();
            vista.setVisible(true);
        }

        // Método empleado que más confeccionó documentos
        if (ae.getSource() == vista.cantidadConfeccButtonModal) {
            vista.dispose();
            empMasConfecciono.setVisible(true);
            empMasConfecciono.cantdocsConfeccTextArea.setText(modelo.autorMasProductivo());
            //Boton salir
        } else if (ae.getSource() == empMasConfecciono.salirButtonEmpleado) {
            empMasConfecciono.dispose();
            vista.setVisible(true);
        }

    }

    public void limpiarCajasBuscar() {
        buscar.palabraClaveTextField.setText("");
    }
}
