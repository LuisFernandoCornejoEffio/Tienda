/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Beans.bCategoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USUARIO
 */
public class mCategoria {
    Conexion cn = new Conexion();

    public bCategoria Extraer(int id) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM categoria WHERE idcategoria = ?";
        bCategoria bCat = new bCategoria();
        try {
            con = cn.Conectar();
            pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                bCat.setIdCategoria(rs.getInt("idcategoria"));
                bCat.setNombre(rs.getString("nombre"));
                bCat.setDescripcion(rs.getString("descripcion"));
                bCat.setCondicion(rs.getString("condicion"));
                return bCat;
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
    
    public boolean Insertar(bCategoria bCat) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        int N = 0;
        try {
            String sql = "INSERT INTO categoria (idcategoria, nombre, descripcion, condicion) "
                    + "VALUES (null,? , ?, 'ACTIVO')";
            pst = con.prepareStatement(sql);
            pst.setString(1, bCat.getNombre());
            pst.setString(2, bCat.getDescripcion());
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
        
    //Metodo Modificar
    public boolean Editar(bCategoria bCategory) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        int N = 0;
        try {
            String sql = "UPDATE categoria SET nombre = ?, descripcion = ? WHERE idcategoria = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, bCategory.getNombre());
            pst.setString(2, bCategory.getDescripcion());
            pst.setInt(3, bCategory.getIdCategoria());
            N = pst.executeUpdate();
            if (N != 0) {
                System.out.println("Modificado con exito");
                return true;
            } else {
                System.out.println("No se modifico");
                return false;
            }
        } finally {
            if (pst != null) {
                pst.close();
            }
            cn.Desconectar(con);
        }
    }
    
    //Metodo Listar
    public List Listar(String buscar, String campo) throws SQLException {
        Connection con = cn.Conectar();
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<bCategoria> ListaCategorias = new ArrayList();
        try {
            String sql = "";
            if(buscar.equals("")){
                sql = "SELECT * FROM categoria ORDER BY nombre ASC";
            }else{
                sql = "SELECT * FROM categoria WHERE "+campo+" LIKE '" + buscar + "%' ORDER BY nombre ASC";
            }            
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                bCategoria bCategoria = new bCategoria();
                bCategoria.setIdCategoria(rs.getInt("idcategoria"));
                bCategoria.setNombre(rs.getString("nombre"));
                bCategoria.setDescripcion(rs.getString("descripcion"));
                bCategoria.setCondicion(rs.getString("condicion"));
                ListaCategorias.add(bCategoria);
            }
            return ListaCategorias;
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
            String sql = "UPDATE categoria SET condicion = ? WHERE idcategoria = ?";
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
}
