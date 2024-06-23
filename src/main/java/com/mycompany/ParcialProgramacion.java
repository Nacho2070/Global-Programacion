package com.mycompany;

import com.mycompany.controllers.Controlador;
import com.mycompany.models.Documento;
import com.mycompany.models.EnteCorreo;
import com.mycompany.models.Envio;
import com.mycompany.models.Persona;
import com.mycompany.models.Services.Modelo;
import com.mycompany.models.repository.Repository;
import com.mycompany.view.Vista;

public class ParcialProgramacion {

    public static void main(String[] args) {
            
        Vista vista = new Vista();
        Modelo modelo = new Modelo();
        
        Controlador controlador = new Controlador(vista, modelo);

        controlador.iniciar();
        vista.setVisible(true);
                
    }
}
