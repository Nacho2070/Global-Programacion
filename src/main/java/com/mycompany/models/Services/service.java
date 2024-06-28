package com.mycompany.models.Services;

import java.util.List;
import java.util.Optional;
import javax.naming.InvalidNameException;
import com.mycompany.models.Documento;
import com.mycompany.models.EmpresaCorreo;
import com.mycompany.models.EnteCorreo;
import com.mycompany.models.Envio;
import com.mycompany.models.Persona;
import com.mycompany.models.repository.Repository;
import com.mycompany.view.Menu;

public class service {

    private Repository repository;
    private Documento documentos;
    private Envio envio;
    private Persona persona;
    private EnteCorreo enteCorreo;
    private EmpresaCorreo empresa;
    private Menu vista;
    
    public service() {
        this.documentos = new Documento();
        this.envio = new Envio();
        this.persona = new Persona();
        this.enteCorreo = new EnteCorreo();
        this.empresa = new EmpresaCorreo();
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
    public EmpresaCorreo getEmpresa(){
        return empresa;
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
       
        return repository.cantidadEnEsperaConsulta();
       
    }
    
    public boolean guardarEnBD(Documento documento, Persona persona, Envio envio,EmpresaCorreo empresa){
            //Llamamos al repository para guardar los objetos en base de datos
            if(this.repository.insertarValoresDocumentosBD(documentos,persona,envio,empresa)){
                return true;
            }
           return false;
    }
    
    
    }
    

