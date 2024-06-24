package com.mycompany;

import com.mycompany.controllers.Controlador;
import com.mycompany.controllers.ControladorEnviarDocs;
import com.mycompany.models.Documento;
import com.mycompany.models.EnteCorreo;
import com.mycompany.models.Envio;
import com.mycompany.models.Persona;
import com.mycompany.models.Services.service;
import com.mycompany.models.repository.Repository;
import com.mycompany.view.BuscarPorPalabraClave;
import com.mycompany.view.CantidadEnEspera;
import com.mycompany.view.DatosCorreo_Empresa;
import com.mycompany.view.EmpleadoQueMasCofeccionoDocs;

import com.mycompany.view.EnviarDocs;
import com.mycompany.view.Menu;

public class ParcialProgramacion {

    public static void main(String[] args) {
        //Vistas    
        Menu vista = new Menu();
        DatosCorreo_Empresa enviarDocs2 = new DatosCorreo_Empresa();
        EnviarDocs enviarDocs = new EnviarDocs();
        BuscarPorPalabraClave buscar = new BuscarPorPalabraClave();
        CantidadEnEspera cantidad = new CantidadEnEspera();
        EmpleadoQueMasCofeccionoDocs emp = new EmpleadoQueMasCofeccionoDocs();
        
        service modelo = new service();
       
        ControladorEnviarDocs controladorEnviarDocs = new ControladorEnviarDocs(enviarDocs, enviarDocs2,modelo,vista);
        
        Controlador controlador = new Controlador(vista, modelo,buscar,cantidad,emp);
        controlador.iniciar();
        vista.setVisible(true);
                
    }
}
