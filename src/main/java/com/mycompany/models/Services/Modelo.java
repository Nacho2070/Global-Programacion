package com.mycompany.models.Services;

import java.util.List;
import java.util.Optional;
import javax.naming.InvalidNameException;
import com.mycompany.models.Documento;
import com.mycompany.models.EnteCorreo;
import com.mycompany.models.Envio;
import com.mycompany.models.Persona;
import com.mycompany.models.repository.Repository;
import com.mycompany.view.Vista;
import java.time.LocalTime;

public class Modelo {

    private Repository repository;
    private Documento documentos;
    private Envio envio;
    private Persona persona;
    private EnteCorreo enteCorreo;
    private Vista vista;
    
    public Modelo() {
        this.documentos = new Documento();
        this.envio = new Envio();
        this.persona = new Persona();
        this.enteCorreo = new EnteCorreo();
        this.repository = new Repository();
    }

    public Documento getDocumento() {
        return documentos;
    }

    public Envio getEnvio() {
        return envio;
    }

    public Persona getPersona() {
        return persona;
    }

    public EnteCorreo getEnteCorreo() {
        return enteCorreo;
    }

    public List<Documento> documentoQueIncluyen(String palabra) throws InvalidNameException {
        List<Documento> documentos = repository.buscarPorPalabraClave(palabra);
        if (documentos.isEmpty()) {
            throw new InvalidNameException("No se encontraron documentos con la palabra clave proporcionada.");
        }
        return documentos;
    }
    public String autorMasProductivo() {
            return repository.autorMasProductivo();
        }
    public Optional<Integer> cantidadEnEspera() {
        //Si en el envio esta vacio retornamos Optional.empty
       
        //Filtramos todos los envios buscado todos los estados en falso
        //Con count() contamos todos los elementos del stream
        return repository.cantidadEnEsperaConsulta();
       }
    
    public boolean guardarEnBD(Documento documento, Persona persona, Envio envio){
            //Llamamos al repository para guardar los objetos en base de datos
            if(this.repository.insertarValoresDocumentosBD(documentos,persona,envio)){
                return true;
            }
           return false;
    }
    
    
    }
    

