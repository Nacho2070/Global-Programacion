
package com.mycompany.controllers;

import com.mycompany.models.xd.ConsultarPersona;
import com.mycompany.models.Persona;
import com.mycompany.view.VistaPersona;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;


//Implementamos para poder tener coneccion con la vista
public class Controlador_Persona implements ActionListener{
   //Atributos
   private VistaPersona vista;
   private Persona persona;
   private ConsultarPersona modelo;
   
    public Controlador_Persona(VistaPersona vista, Persona persona,ConsultarPersona modelo) {
        this.vista = vista;
        this.persona = persona;
        this.modelo = modelo;
        //Agregamos el boton guardar       
        vista.guardar.addActionListener(this);
        //Boton limpiar
        vista.Limpiar.addActionListener(this);
        //boton buscar
        vista.Buscarbutton.addActionListener(this);
        vista.Nombres.addActionListener((ActionListener) this);
        vista.Apellidos.addActionListener((ActionListener) this);
        
    }
   public void iniciar(){
       vista.setTitle("Crud");
       vista.setLocationRelativeTo(null);
   } 
    //Usamos el objeto ae para saber que boton se presionar
    public void actionPerformed(ActionEvent ae){
        
        if(ae.getSource()== vista.guardar){
            persona.setNombre(vista.Nombres.getText());
            persona.setApellido(vista.Apellidos.getText());
        
        
        //Mandamos la persona con los datos a la instancia modelo
        if(modelo.insertar(persona)){
            JOptionPane.showMessageDialog(null, "Registro ingresado con exito");
             limpiarCajas();

        }else{
            JOptionPane.showMessageDialog(null, "Error en la inserccion");
            limpiarCajas();
       
        }
    }
       
       
       //Accion por si el usuario presiona el boton buscar
       if(ae.getSource()== vista.Buscarbutton){
          // persona.setNombre(vista.buscarUser.getText());
           if(modelo.buscar(persona)){
               vista.Nombres.setText(persona.getNombre());
               vista.Apellidos.setText(persona.getApellido());
               
           }
       }else{
           JOptionPane.showMessageDialog(null, "Error en la busqueda");
       }
       
       //Accion por si el usuario presiona el boton limpiar     
       if(ae.getSource()== vista.Limpiar){    public void limpiarCajas(){
        vista.buscarUser.setText(null);

           limpiarCajas();
       }
       
    }
    public void limpiarCajas(){
        vista.buscarUser.setText(null);
        vista.Nombres.setText(null);
        vista.Apellidos.setText(null);

        
    }

}
