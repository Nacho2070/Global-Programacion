package com.mycompany.models;

import java.util.ArrayList;
import javax.naming.InvalidNameException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.mycompany.models.repository.Repository;
import java.util.Date;

public class Documento {
    
    private String destinatario;
    private Date fecha_creacion;
    private String autor;
    private List<String> palabraClave;
    private ArrayList<Envio> enviado = new ArrayList<>();
    public  List<EnteCorreo> se_envia;
    private List<Persona> trabaja;
    
    public Documento() {
        this.palabraClave = new ArrayList<>();
        this.enviado = new ArrayList<>();
        this.se_envia = new ArrayList<>();
        this.trabaja = new ArrayList<>();
    }

    public String getDestinatario() {
        return destinatario;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public String getAutor() {
        return autor;
    }

    public List<String> getPalabra_clave() {
        return palabraClave;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setPalabraClave(List<String> palabraClave) {
        this.palabraClave = palabraClave;
    }

    public List<Envio> getEnviado() {
        return enviado;
    }

    public void setEnvio(Envio enviado) {
        this.enviado.add(enviado);
    }

    public void setSe_envia(EnteCorreo se_envia) {
        this.se_envia.add(se_envia);
    }

    public void setTrabaja(Persona trabaja) {
        this.trabaja.add(trabaja);
    }
    
}
