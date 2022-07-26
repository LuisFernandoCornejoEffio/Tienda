/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Beans.bPersona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USUARIO
 */
public class mPersona {
    Conexion cn = new Conexion();

    public bPersona Extraer(int id) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM persona WHERE idpersona = ?";
        bPersona bPersona = new bPersona();
        try {
            con = cn.Conectar();
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                bPersona.setIdPersona(rs.getInt("idpersona"));
                bPersona.setNombre(rs.getString("nombre"));
                bPersona.setTipo_persona(rs.getString("tipo_persona"));
                bPersona.setTipo_documento(rs.getString("tipo_documento"));
                bPersona.setNum_documento(rs.getString("num_documento"));
                bPersona.setDireccion(rs.getString("direccion"));
                bPersona.setTelefono(rs.getString("telefono"));
                bPersona.setEmail(rs.getString("email"));
                bPersona.setEstado(rs.getString("estado"));
                return bPersona;
            }
            return null;
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pst != null) {
                pst.close();
            }
            cn.Desconectar(con);
        }
    }
    
    public boolean Insertar(bPersona bPers) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        int N = 0;
        try {
            String sql = "INSERT INTO persona (idpersona, nombre, tipo_persona, tipo_documento, num_documento, direccion, telefono, email, estado) "
                    + "VALUES (null, ? , ?, ?, ?, ?, ?, ?, 'ACTIVO')";
            pst = con.prepareStatement(sql);
            pst.setString(1, bPers.getNombre());
            pst.setString(2, bPers.getTipo_persona());
            pst.setString(3, bPers.getTipo_documento());
            pst.setString(4, bPers.getNum_documento());
            pst.setString(5, bPers.getDireccion());     
            pst.setString(6, bPers.getTelefono());
            pst.setString(7, bPers.getEmail());       
            N = pst.executeUpdate();        
            if (N != 0) {
                System.out.println("Registrado con exito");
                return true;
            } else {
                System.out.println("Error - registro");
            }
        } finally {
            if (pst != null) {
                pst.close();
            }
            cn.Desconectar(con);
        }
        return false;
    }
    
    public boolean Editar(bPersona bPers) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        int N = 0;
        try {
            String sql = "UPDATE persona SET nombre = ?, tipo_documento = ?, num_documento = ?, direccion = ?, telefono = ?, email = ? WHERE idpersona = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, bPers.getNombre());
            pst.setString(2, bPers.getTipo_documento());
            pst.setString(3, bPers.getNum_documento());
            pst.setString(4, bPers.getDireccion());     
            pst.setString(5, bPers.getTelefono());
            pst.setString(6, bPers.getEmail());  
            pst.setInt(7, bPers.getIdPersona());
            N = pst.executeUpdate();
            if (N != 0) {
                System.out.println("Modificado con exito");
                return true;
            } else {
                System.out.println("Error - Modificar");
            }
        } finally {
            if (pst != null) {
                pst.close();
            }
            cn.Desconectar(con);
        }
        return false;
    }

    //Metodo Listar
    public List Listar(String buscar, String campo, String tipo) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<bPersona> ListaPersonas = new ArrayList();
        try {
            String sql = "";
            if(buscar.equals("")){
                sql = "SELECT * FROM persona WHERE tipo_persona = '"+tipo+"' ORDER BY nombre ASC";
            }else{
                sql = "SELECT * FROM persona WHERE tipo_persona = '"+tipo+"' AND "+campo+" LIKE '" + buscar + "%' ORDER BY nombre ASC";
            }               
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                bPersona bPersona = new bPersona();
                bPersona.setIdPersona(rs.getInt("idpersona"));
                bPersona.setNombre(rs.getString("nombre"));
                bPersona.setTipo_persona(rs.getString("tipo_persona"));
                bPersona.setTipo_documento(rs.getString("tipo_documento"));
                bPersona.setNum_documento(rs.getString("num_documento"));
                bPersona.setDireccion(rs.getString("direccion"));
                bPersona.setTelefono(rs.getString("telefono"));
                bPersona.setEmail(rs.getString("email"));
                bPersona.setEstado(rs.getString("estado"));             
                ListaPersonas.add(bPersona);
            }
            return ListaPersonas;
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pst != null) {
                pst.close();
            }
            cn.Desconectar(con);
        }
    }

    public boolean estado(int id, String estado) {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        try {
            String sql = "UPDATE persona SET estado = ? WHERE idpersona = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, estado);
            pst.setInt(2, id);
            pst.execute();
            return true;
        } catch (SQLException e) {
            return false;
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException e) {
            }
        }
    }    
    
    public int totalClientes() throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        ResultSet rs = null;
        int total;
        try {
            String sql = "select count(*) as total from persona where tipo_persona like 'CLIENTE'";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                total = Integer.parseInt(rs.getString("total"));
                return total;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pst != null) {
                pst.close();
            }
            cn.Desconectar(con);
        }
        return 0;
    }
    
    public int totalProveedores() throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        ResultSet rs = null;
        int total;
        try {
            String sql = "select count(*) as total from persona where tipo_persona like 'PROVEEDOR'";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                total = Integer.parseInt(rs.getString("total"));
                return total;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pst != null) {
                pst.close();
            }
            cn.Desconectar(con);
        }
        return 0;
    }
}
