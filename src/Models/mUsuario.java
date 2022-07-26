/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Beans.bUsuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USUARIO
 */
public class mUsuario {

    Conexion cn = new Conexion();

    public bUsuario Extraer(int id) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM usuario WHERE idusuario = ?";
        bUsuario bUser = new bUsuario();
        try {
            con = cn.Conectar();
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                bUser.setIdUsuario(rs.getInt("idusuario"));
                bUser.setNombre(rs.getString("nombre"));
                bUser.setTipo_documento(rs.getString("tipo_documento"));
                bUser.setNum_documento(rs.getString("num_documento"));
                bUser.setDireccion(rs.getString("direccion"));
                bUser.setTelefono(rs.getString("telefono"));
                bUser.setCargo(rs.getString("cargo"));
                bUser.setEmail(rs.getString("email"));
                bUser.setImagen(rs.getString("imagen"));
                bUser.setCondicion(rs.getString("condicion"));
                return bUser;
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

    public int Insertar(bUsuario bUser) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        ResultSet rs = null;
        int id = 0;
        try {
            String sql = "INSERT INTO usuario (idusuario, nombre, tipo_documento, num_documento, direccion, telefono, cargo, email, clave, condicion) "
                    + "VALUES (null,? , ?, ?, ?, ?, ?, ?, ?, 'ACTIVO')";
            pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, bUser.getNombre());
            pst.setString(2, bUser.getTipo_documento());
            pst.setString(3, bUser.getNum_documento());
            pst.setString(4, bUser.getDireccion());
            pst.setString(5, bUser.getTelefono());
            pst.setString(6, bUser.getCargo());
            pst.setString(7, bUser.getEmail());
            pst.setString(8, bUser.getClave());
            pst.executeUpdate();
            
            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
                System.out.println(id);
                return id;
            } else {
                System.out.println("Error - registro");
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
        return id;
    }
    
    public boolean InsertarImg(int id, String img) throws SQLException{
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        ResultSet rs = null;
        int N = 0;
        try {
            String sql = "UPDATE usuario SET imagen = ? WHERE idusuario = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, img);
            pst.setInt(2, id);
            pst.executeUpdate();
            
            N = pst.executeUpdate();
            if (N != 0) {
                System.out.println("IMG MODIFICADO con exito");
                return true;
            } else {
                System.out.println("Error - Modificar IMG");
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
        return false;
    }
    
    public boolean Editar(bUsuario bUser) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        int N = 0;
        try {
            String sql = "UPDATE usuario SET nombre = ?, tipo_documento = ?, num_documento = ?, direccion = ?, telefono = ?, cargo = ?, email = ?, imagen = ? WHERE idusuario = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, bUser.getNombre());
            pst.setString(2, bUser.getTipo_documento());
            pst.setString(3, bUser.getNum_documento());
            pst.setString(4, bUser.getDireccion());
            pst.setString(5, bUser.getTelefono());
            pst.setString(6, bUser.getCargo());
            pst.setString(7, bUser.getEmail());
            pst.setString(8, bUser.getImagen());
            pst.setInt(9, bUser.getIdUsuario());
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
    public List Listar(String buscar, String campo) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<bUsuario> ListaUsuarios = new ArrayList();
        try {
            String sql = "";
            if(buscar.equals("")){
                sql = "SELECT * FROM usuario ORDER BY nombre ASC";
            }else{
                sql = "SELECT * FROM usuario WHERE "+campo+" LIKE '" + buscar + "%' ORDER BY nombre ASC";
            }            
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                bUsuario bUsuario = new bUsuario();
                bUsuario.setIdUsuario(rs.getInt("idusuario"));
                bUsuario.setNombre(rs.getString("nombre"));
                bUsuario.setTipo_documento(rs.getString("tipo_documento"));
                bUsuario.setNum_documento(rs.getString("num_documento"));
                bUsuario.setDireccion(rs.getString("direccion"));
                bUsuario.setTelefono(rs.getString("telefono"));
                bUsuario.setCargo(rs.getString("cargo"));
                bUsuario.setEmail(rs.getString("email"));
                bUsuario.setImagen(rs.getString("imagen"));
                bUsuario.setCondicion(rs.getString("condicion"));
                ListaUsuarios.add(bUsuario);
            }
            return ListaUsuarios;
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
            String sql = "UPDATE usuario SET condicion = ? WHERE idusuario = ?";
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
    
    public int totalUsuarios() throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        ResultSet rs = null;
        int total;
        try {
            String sql = "select count(*) as total from usuario WHERE condicion like 'ACTIVO'";
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
