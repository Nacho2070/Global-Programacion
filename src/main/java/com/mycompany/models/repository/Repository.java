package com.mycompany.models.repository;

import com.mycompany.models.Documento;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Repository extends Conexion {


    public boolean insertarValoresDocumentosBD(Documento documento){
        Connection conexion = establecerConexion();

        try {
            PreparedStatement ps = conexion.prepareStatement("Insert into Documento (autor,destinatario,fecha_creacion,palabra_clave)values(?,?,?,?)");
            ps.setString(1,  documento.getAutor());
            ps.setString(2,  documento.getDestinatario());
            ps.setString(3,  documento.getFecha_creacion());
            ps.setString(4,  convertirListaAJson(documento.getPalabra_clave())); // Convertir la lista a JSON

            int resultado = ps.executeUpdate();
            if(resultado >0){
                System.out.println("biennnnnnn");
                return true;
            }else{
                return false;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error");
        }finally{
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("error");
            }
        }
        return false;
    }
    
      public List<Documento> buscarPorPalabraClave(String palabra) {
        List<Documento> documentos = new ArrayList<>();
        Connection conexion = establecerConexion();
        if (conexion == null) {
        System.err.println("No se pudo establecer conexión con la base de datos.");
        return documentos; 
        }
        try {
            
            String palabraJson = "\"" + palabra + "\"";

            
            PreparedStatement ps = conexion.prepareStatement("SELECT * FROM Documento WHERE JSON_CONTAINS(palabra_clave, ?)");
                
            ps.setString(1,palabraJson);
            ResultSet rs = ps.executeQuery();
            System.out.println(rs); 
            while (rs.next()) {
                Documento doc = new Documento();
                doc.setAutor(rs.getString("autor"));
                doc.setDestinatario(rs.getString("destinatario"));
                doc.setFecha_creacion(rs.getString("fecha_creacion"));

                String palabraClaveJson = rs.getString("palabra_clave");
                List<String> palabraClaveList = convertirJsonALista(palabraClaveJson);
                System.out.println(palabraClaveList);
                doc.setPalabraClave(palabraClaveList);
                 
                documentos.add(doc);
            }
        } catch (SQLException e) {
            e.printStackTrace();}
       return documentos;
      }
      
      
      public String autorMasProductivo(){
         
          Connection conexion = establecerConexion();
          
          String sql = "SELECT e.id_empleado, e.nombre, COUNT(d.id_documento) AS cantidad_documentos "
                       + "FROM Empleado e "
                       + "JOIN Documento d ON e.id_empleado = d.id_empleado "
                       + "GROUP BY e.id_empleado, e.nombre "
                       + "ORDER BY cantidad_documentos DESC "
                       + "LIMIT 1";
          
          try {
                 PreparedStatement ps = conexion.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery();
             if (rs.next()) {
                int idEmpleado = rs.getInt("id_empleado");
                String nombreEmpleado = rs.getString("nombre");
                int cantidadDocumentos = rs.getInt("cantidad_documentos");

                // Construir el resultado que deseas devolver
                String resultado = "Empleado más productivo:\n"
                             + "ID Empleado: " + idEmpleado + "\n"
                             + "Nombre: " + nombreEmpleado + "\n"
                             + "Cantidad de documentos: " + cantidadDocumentos;

            // Aquí puedes devolver el resultado o hacer cualquier otro procesamiento necesario
            return resultado;
            } 
             } catch (Exception e) {}
              
                return "No se encontraron resultados.";
      }         
             
               
            
            
      public Optional<Integer> cantidadEnEsperaConsulta() {
        String sql = "SELECT COUNT(*) FROM Envio WHERE estado_enviado = false";
        Connection conexion = establecerConexion();
        try{
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int cantidad = rs.getInt(1);
                return Optional.of(cantidad);
            } else {
                return Optional.empty();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
            return Optional.empty();
  }

      
      
     private String convertirListaAJson(List<String> lista) {
         StringBuilder json = new StringBuilder("[");
         for (int i = 0; i < lista.size(); i++) {
        json.append("\"").append(lista.get(i)).append("\"");
        if (i < lista.size() - 1) {
            json.append(",");
        }
        }
            json.append("]");
        return json.toString();
    }
     
     
     private List<String> convertirJsonALista(String json) {
        // Asumimos que el formato es ["clave1", "clave2", ...]
        json = json.replace("[", "").replace("]", "").replace("\"", "");
        String[] items = json.split(",");
        List<String> list = new ArrayList<>();
        for (String item : items) {
            list.add(item.trim());
        }
        return list;
    }
}
     
     

    /*
    public boolean insertarValoresPersonaBD(Persona persona){
        Connection conexion = establece_coneccion();

        try {
            PreparedStatement ps = conexion.prepareStatement("Insert into Documento (destino,fecha_creacion,autor,destinatario)values(?,?,?,?,?,?)");
            ps.setString(1, documento.getDestino());
            ps.setString(2, documento.getFecha_creacion());
            ps.setString(3, documento.getAutor());
            ps.setString(4, documento.getDestinatario());

            int resultado =ps.executeUpdate();
            if(resultado >0){
                return true;
            }else{
                return false;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error");
        }finally{
            try {
                conexion.close();
            } catch (Exception e) {
                System.err.println("error");
            }
        }
        return false;
    }

 */

