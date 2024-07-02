package com.mycompany.models.repository;

import com.mycompany.models.Documento;
import com.mycompany.models.EmpresaCorreo;
import com.mycompany.models.Envio;
import com.mycompany.models.Persona;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Repository extends Conexion {

    public boolean insertarValoresDocumentosBD(Documento documento, Persona persona, Envio envio, EmpresaCorreo empresa) {
        Connection conexion = establecerConexion();

        try {
            conexion.setAutoCommit(false);

            // Insertar en Empleados
            String sqlPersona = "INSERT INTO Empleados (nombre, direccion, telefono, fecha_ingreso, cargo) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement psPersona = conexion.prepareStatement(
                    sqlPersona,
                    Statement.RETURN_GENERATED_KEYS
            );
            psPersona.setString(1, persona.getNombre());
            psPersona.setString(2, persona.getDireccion());
            psPersona.setString(3, persona.getTelefono());
            //Date a String
            SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");

            String fechaCadenaPersona = sdf.format(persona.getFecha_ingreso());

            psPersona.setString(4, fechaCadenaPersona);
            psPersona.setString(5, persona.getCargo());

            int personaResultado = psPersona.executeUpdate();
            if (personaResultado == 0) {
                conexion.rollback();
                return false;
            }

            // Obtener el ID generado para la tabla Empleados
            ResultSet personaKeys = psPersona.getGeneratedKeys();
            int personaId = 0;
            if (personaKeys.next()) {
                personaId = personaKeys.getInt(1);
            } else {
                throw new SQLException("No se generó el ID de Empleados");
            }

            // Insertar en Envio
            String sqlEnvio = "INSERT INTO Envio (estado_enviado, nro_seguimiento) VALUES (?, ?)";
            PreparedStatement psEnvio = conexion.prepareStatement(
                    sqlEnvio,
                    Statement.RETURN_GENERATED_KEYS
            );
            psEnvio.setBoolean(1, envio.isEstado_enviado());
            psEnvio.setInt(2, envio.getNro_seguimiento());

            int envioResultado = psEnvio.executeUpdate();
            if (envioResultado == 0) {
                conexion.rollback();
                return false;
            }

            // Insertar en EmpresaCorreo
            String sqlEmpresa = "INSERT INTO EmpresaCorreo (nombre, direccion, telefono, encargado) VALUES (?, ?, ?, ?)";
            PreparedStatement psEmpresa = conexion.prepareStatement(
                    sqlEmpresa
            );
            psEmpresa.setString(1, empresa.getNombre());
            psEmpresa.setString(2, empresa.getDireccion());
            psEmpresa.setString(3, empresa.getTelefono());
            psEmpresa.setString(4, empresa.getEncargado());

            int empresaResultado = psEmpresa.executeUpdate();
            if (empresaResultado == 0) {
                conexion.rollback();
                return false;
            }

            // Insertar en Documento
            String sqlDocumento = "INSERT INTO Documento (autor, destinatario, fecha_creacion, palabra_clave, id_empleado) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement psDocumento = conexion.prepareStatement(
                    sqlDocumento
            );
            psDocumento.setString(1, documento.getAutor());
            psDocumento.setString(2, documento.getDestinatario());

            String fechaCadDoc = sdf.format(documento.getFecha_creacion());

            psDocumento.setString(3, fechaCadDoc);
            psDocumento.setString(4, convertirListaAJson(documento.getPalabra_clave()));
            psDocumento.setInt(5, personaId);

            int documentoResultado = psDocumento.executeUpdate();
            if (documentoResultado == 0) {
                conexion.rollback();
                return false;
            }

            // Confirmar la transacción
            conexion.commit();
            return true;

        } catch (SQLException e) {
            try {
                if (conexion != null) {
                    conexion.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conexion != null) {
                    conexion.setAutoCommit(true);
                    conexion.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
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
            SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");

            ps.setString(1, palabraJson);
            ResultSet rs = ps.executeQuery();
            System.out.println(rs);
            while (rs.next()) {
                Documento doc = new Documento();
                doc.setAutor(rs.getString("autor"));
                doc.setDestinatario(rs.getString("destinatario"));

                try {
                    doc.setFecha_creacion(sdf.parse(rs.getString("fecha_creacion")));
                } catch (ParseException e) {
                }

                String palabraClaveJson = rs.getString("palabra_clave");
                List<String> palabraClaveList = convertirJsonALista(palabraClaveJson);
                System.out.println(palabraClaveList);
                doc.setPalabraClave(palabraClaveList);

                documentos.add(doc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documentos;
    }

    public String autorMasProductivo() {

        Connection conexion = establecerConexion();

        String sql = "SELECT e.id_empleado, e.nombre, COUNT(d.id_documento) AS cantidad_documentos "
                + "FROM Empleados e "
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

                String resultado = "Nombre: " + nombreEmpleado + "\n"
                        + ", Cantidad de documentos confeccionados: " + cantidadDocumentos;
                return resultado;
            }
        } catch (Exception e) {
        }

        return "No se encontraron resultados";
    }

    public Optional<Integer> cantidadEnEsperaConsulta() {
        String sql = "SELECT COUNT(*) FROM Envio WHERE estado_enviado = true";
        Connection conexion = establecerConexion();
        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int cantidad = rs.getInt(1);
                return Optional.of(cantidad);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private static String convertirListaAJson(List<String> lista) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < lista.size(); i++) {
            json.append("[\"").append(lista.get(i)).append("\"]");
            if (i < lista.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

    private List<String> convertirJsonALista(String json) {
        json = json.replace("[", "").replace("]", "").replace("\"", "");
        String[] items = json.split(",");
        List<String> list = new ArrayList<>();
        for (String item : items) {
            list.add(item.trim());
        }
        return list;
    }
}
